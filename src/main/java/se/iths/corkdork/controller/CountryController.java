package se.iths.corkdork.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import se.iths.corkdork.dtos.Country;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.corkdork.exception.BadRequestException;
import se.iths.corkdork.exception.EntityNotFoundException;
import se.iths.corkdork.service.CountryService;

@RestController
@RequestMapping("countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;

    }

    @PostMapping("/admin/create")
    public ResponseEntity<Country> createCountry(@Validated @RequestBody Country country, BindingResult errors){
        if (errors.hasErrors())
            throw new BadRequestException("Invalid input", errors);

        Country createdCountry = countryService.createCountry(country);

        return new ResponseEntity<>(createdCountry, HttpStatus.CREATED);
    }

    @PutMapping("admin/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable Long id, @RequestBody Country country){

        countryService.updateCountry(id, country);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("admin/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id){

        countryService.deleteCountry(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("public/{id}")
    public ResponseEntity<Country> findCountryById(@PathVariable Long id){

        Country country = countryService.findCountryById(id);

        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    @GetMapping("public")
    public ResponseEntity<Iterable<Country>> findAllCountries(){
        Iterable<Country> allCountryEntities = countryService.findAllCountries();

        if (!allCountryEntities.iterator().hasNext())
            throw new EntityNotFoundException("Failed to find any countries.");

        return new ResponseEntity<>(allCountryEntities, HttpStatus.OK);
    }
}