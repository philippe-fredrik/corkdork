package se.iths.corkdork.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import se.iths.corkdork.dtos.Country;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.corkdork.dtos.Wine;
import se.iths.corkdork.entity.CountryEntity;
import se.iths.corkdork.exception.BadRequestException;
import se.iths.corkdork.exception.EntityNotFoundException;
import se.iths.corkdork.service.CountryService;
import se.iths.corkdork.service.WineService;

import java.util.List;

@RestController
@RequestMapping("countries")
public class CountryController {

    private final CountryService countryService;
    private final WineService wineService;
  
    public CountryController(CountryService countryService, WineService wineService) {
        this.countryService = countryService;
        this.wineService = wineService;

    }

    @PostMapping()
    public ResponseEntity<Country> createCountry(@Validated @RequestBody Country country, BindingResult errors){
        if (errors.hasErrors())
            throw new BadRequestException("Invalid input", errors);

        Country createdCountry = countryService.createCountry(country);

        return new ResponseEntity<>(createdCountry, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable Long id, @Validated @RequestBody Country country, BindingResult errors){

        if (errors.hasErrors())
            throw new EntityNotFoundException(notFound(id));

        countryService.updateCountry(id, country);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id){

        countryService.deleteCountry(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("{id}")
    public ResponseEntity<Country> findCountryById(@PathVariable Long id){

        Country country = countryService.findCountryById(id);

        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Country>> findAllCountries() {

        List<Country> countries = countryService.findAllCountries();

        if (countries.isEmpty())
            throw new EntityNotFoundException("Failed to find any countries.");

        return new ResponseEntity<>(countries, HttpStatus.OK);
    }
       
    @PostMapping("/{name}/{id}")
    public ResponseEntity<CountryEntity> addWineToCountry(@PathVariable String name, @PathVariable Long id) {

        Wine foundWine = wineService.findWineById(id);

        CountryEntity updatedCountry = countryService.addWine(name, foundWine);

        return new ResponseEntity<>(updatedCountry, HttpStatus.OK);
    
    }
    private String notFound(Long id) {
        return "User with ID: " + id + " was not found.";
    }
}
