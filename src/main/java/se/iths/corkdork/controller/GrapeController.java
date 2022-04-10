package se.iths.corkdork.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import se.iths.corkdork.dtos.Country;
import se.iths.corkdork.dtos.Grape;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.corkdork.entity.GrapeEntity;
import se.iths.corkdork.exception.BadRequestException;
import se.iths.corkdork.exception.EntityNotFoundException;
import se.iths.corkdork.service.CountryService;
import se.iths.corkdork.service.GrapeService;

import java.util.List;

@RestController
@RequestMapping("grapes")
public class GrapeController {

    private final GrapeService grapeService;
    private final CountryService countryService;

    public GrapeController(GrapeService grapeService, CountryService countryService) {
        this.grapeService = grapeService;
        this.countryService = countryService;
    }

    @PostMapping("")
    public ResponseEntity<Grape> createGrape(@Validated @RequestBody Grape grape, BindingResult errors) {

        if(errors.hasErrors())
            throw new BadRequestException("Name and color fields are mandatory", errors);

        Grape createdGrape = grapeService.createGrape(grape);

        return new ResponseEntity<>(createdGrape, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        grapeService.deleteGrape(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity<Grape> updateGrape(@PathVariable Long id, @Validated @RequestBody Grape grape, BindingResult errors) {

        if (errors.hasErrors())
            throw new EntityNotFoundException(notFound(id));

        grapeService.updateGrape(id, grape);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Grape> findGrapeById(@PathVariable Long id) {

        Grape grape = grapeService.findById(id);

        return new ResponseEntity<>(grape, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Grape>> findAllGrapes() {

        List<Grape> grapes = grapeService.getAllGrapes();

        if (grapes.isEmpty())
            throw new EntityNotFoundException("Failed to find any grapes.");

        return new ResponseEntity<>(grapes, HttpStatus.OK);

    }

    @PostMapping("/{name}/{id}")
    public ResponseEntity<GrapeEntity> addCountryToGrape(@PathVariable String name, @PathVariable Long id) {

        Country foundCountry = countryService.findCountryById(id);

        GrapeEntity updatedGrape = grapeService.addCountry(name, foundCountry);

        return new ResponseEntity<>(updatedGrape, HttpStatus.OK);

    }
    private String notFound(Long id) {
        return "User with ID: " + id + " was not found.";
    }
}
