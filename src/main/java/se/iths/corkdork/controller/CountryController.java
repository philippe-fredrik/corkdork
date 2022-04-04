package se.iths.corkdork.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.validation.BindingResult;
import se.iths.corkdork.dtos.Country;
import se.iths.corkdork.entity.CountryEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.corkdork.exception.BadRequestException;
import se.iths.corkdork.exception.EntityNotFoundException;
import se.iths.corkdork.service.CountryService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("countries")
public class CountryController {

    private final CountryService countryService;
    private final ModelMapper modelMapper;

    public CountryController(CountryService countryService, ModelMapper modelMapper) {
        this.countryService = countryService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/admin/create")
    public ResponseEntity<Country> createCountry(@Valid @RequestBody Country country, BindingResult errors){
        if(errors.hasErrors())
            throw new BadRequestException("Name field is mandatory", errors);

        CountryEntity countryEntity = countryService.createCountry(modelMapper.map(country, CountryEntity.class));
        Country response = modelMapper.map(countryEntity, Country.class);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("admin/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable Long id, @RequestBody Country country){
        if(countryService.findCountryById(id).isEmpty())
            throw new EntityNotFoundException(notFound(id));

        CountryEntity countryEntity = countryService.createCountry(modelMapper.map(country, CountryEntity.class));
        Country response = modelMapper.map(countryEntity, Country.class);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("admin/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id){
        if(countryService.findCountryById(id).isEmpty())
            throw new EntityNotFoundException(notFound(id));

        countryService.deleteCountry(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("public/{id}")
    public ResponseEntity<Country> findCountryById(@PathVariable Long id){
        Optional<CountryEntity> foundCountry = countryService.findCountryById(id);

        if(foundCountry.isEmpty()) {
            throw new EntityNotFoundException(notFound(id));
        }

        Country country = modelMapper.map(foundCountry.get(), Country.class);

        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    @GetMapping("public")
    public ResponseEntity<Iterable<Country>> findAllCountries(){
        Iterable<CountryEntity> allCountrieEntities = countryService.findAllCountries();

        if(!allCountrieEntities.iterator().hasNext())
            throw new EntityNotFoundException("Failed to find any wines.");

        Iterable<Country> allCountries = modelMapper.map(
                allCountrieEntities,
                new TypeToken<Iterable<Country>>() {
                }.getType());


        return new ResponseEntity<>(allCountries, HttpStatus.OK);
    }

    private String notFound(Long id) {
        return "Country with ID: " +id+ " was not found.";
    }
}