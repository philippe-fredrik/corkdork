package se.iths.corkdork.controller;

import se.iths.corkdork.entity.CountryEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.corkdork.service.CountryService;

import java.util.Optional;

@RestController
@RequestMapping("countries")
public class CountryController {

    CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("")
    public ResponseEntity<CountryEntity> createCountry(@RequestBody CountryEntity countryEntity){
        CountryEntity createdCountry = countryService.createCountry(countryEntity);

        return new ResponseEntity<>(createdCountry, HttpStatus.CREATED);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id){
        countryService.deleteCountry(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<CountryEntity>> findCountryById(@PathVariable Long id){
        Optional<CountryEntity> foundCountry = countryService.findCountryById(id);
        return new ResponseEntity<>(foundCountry, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Iterable<CountryEntity>> findAllCountries(){
        Iterable<CountryEntity> allCountries = countryService.findAllCountries();
        return new ResponseEntity<>(allCountries, HttpStatus.OK);
    }


}