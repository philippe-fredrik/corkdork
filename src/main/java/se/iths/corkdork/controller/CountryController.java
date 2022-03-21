package se.iths.corkdork.controller;

import se.iths.corkdork.entity.CountryEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.corkdork.exception.BadRequestException;
import se.iths.corkdork.exception.EntityNotFoundException;
import se.iths.corkdork.service.CountryService;

import java.util.Optional;

@RestController
@RequestMapping("countries")
public class CountryController {

    CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping
    public ResponseEntity<CountryEntity> createCountry(@RequestBody CountryEntity countryEntity){
        if(countryEntity.getCountryName().isEmpty())
            throw new BadRequestException("Name field is mandatory");

        CountryEntity createdCountry = countryService.createCountry(countryEntity);
        return new ResponseEntity<>(createdCountry, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id){
        if(countryService.findCountryById(id).isEmpty())
            throw new EntityNotFoundException(notFound(id));

        countryService.deleteCountry(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<CountryEntity>> findCountryById(@PathVariable Long id){
        if(countryService.findCountryById(id).isEmpty())
            throw new EntityNotFoundException(notFound(id));

        Optional<CountryEntity> foundCountry = countryService.findCountryById(id);
        return new ResponseEntity<>(foundCountry, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<CountryEntity>> findAllCountries(){
        Iterable<CountryEntity> allCountries = countryService.findAllCountries();
        if(!allCountries.iterator().hasNext())
            throw new EntityNotFoundException("Failed to find any wines.");
        return new ResponseEntity<>(allCountries, HttpStatus.OK);
    }

    private String notFound(Long id) {
        return "Country with ID: " +id+ " was not found.";
    }
}