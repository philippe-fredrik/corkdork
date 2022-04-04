package se.iths.corkdork.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import se.iths.corkdork.dtos.Country;
import se.iths.corkdork.entity.CountryEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.corkdork.entity.WineEntity;
import se.iths.corkdork.exception.BadRequestException;
import se.iths.corkdork.exception.EntityNotFoundException;
import se.iths.corkdork.service.CountryService;
import se.iths.corkdork.service.WineService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("countries")
public class CountryController {

    private final CountryService countryService;
    private final ModelMapper modelMapper;
    private final WineService wineService;

    public CountryController(CountryService countryService, ModelMapper modelMapper, WineService wineService) {
        this.countryService = countryService;
        this.modelMapper = modelMapper;
        this.wineService = wineService;
    }

    @PostMapping("")
    public ResponseEntity<Country> createCountry(@RequestBody Country country){
        if(country.getName().isEmpty())
            throw new BadRequestException("Name field is mandatory");

        CountryEntity countryEntity = countryService.createCountry(modelMapper.map(country, CountryEntity.class));
        Country response = modelMapper.map(countryEntity, Country.class);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable Long id, @RequestBody Country country){
        if(countryService.findCountryById(id).isEmpty())
            throw new EntityNotFoundException(notFound(id));

        CountryEntity countryEntity = countryService.createCountry(modelMapper.map(country, CountryEntity.class));
        Country response = modelMapper.map(countryEntity, Country.class);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id){
        if(countryService.findCountryById(id).isEmpty())
            throw new EntityNotFoundException(notFound(id));

        countryService.deleteCountry(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("{id}")
    public ResponseEntity<Country> findCountryById(@PathVariable Long id){
        Optional<CountryEntity> foundCountry = countryService.findCountryById(id);

        if(foundCountry.isEmpty()) {
            throw new EntityNotFoundException(notFound(id));
        }

        Country country = modelMapper.map(foundCountry.get(), Country.class);

        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Iterable<Country>> findAllCountries(){
        Iterable<CountryEntity> allCountriesEntity = countryService.findAllCountries();

        if(!allCountriesEntity.iterator().hasNext())
            throw new EntityNotFoundException("Failed to find any countries.");

        Iterable<Country> allCountries = modelMapper.map(allCountriesEntity,
                new TypeToken<Iterable<Country>>() {
                }.getType());


        return new ResponseEntity<>(allCountries, HttpStatus.OK);
    }

    @PostMapping("/{name}/{id}")
    public ResponseEntity<CountryEntity> addWineToCountry(@PathVariable String name, @PathVariable Long id) {
        Optional<WineEntity> foundWine = wineService.findWineById(id);
        if(foundWine.isEmpty())
            throw new EntityNotFoundException("Wine with ID: "+id+" was not found");
        CountryEntity updatedCountry = countryService.addWine(name, foundWine.get());

        return new ResponseEntity<>(updatedCountry, HttpStatus.OK);
    }


    private String notFound(Long id) {
        return "Country with ID: " +id+ " was not found.";
    }
}