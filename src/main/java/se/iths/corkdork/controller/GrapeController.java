package se.iths.corkdork.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import se.iths.corkdork.dtos.Grape;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.corkdork.exception.BadRequestException;
import se.iths.corkdork.exception.EntityNotFoundException;
import se.iths.corkdork.service.GrapeService;
@RestController
@RequestMapping("grapes")
public class GrapeController {

    private final GrapeService grapeService;

    public GrapeController(GrapeService grapeService) {
        this.grapeService = grapeService;
    }

    @PostMapping("/admin/create")
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

    @PutMapping("admin/{id}")
    public ResponseEntity<Grape> updateGrape(@PathVariable Long id, @RequestBody Grape grape) {

        grapeService.updateGrape(id, grape);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Grape> findGrapeById(@PathVariable Long id) {
        Grape grape = grapeService.findById(id);

        return new ResponseEntity<>(grape, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<Grape>> findAllGrapes() {

        Iterable<Grape> allGrapeEntities = grapeService.getAllGrapes();

        if (!allGrapeEntities.iterator().hasNext())
            throw new EntityNotFoundException("Failed to find any wines.");

        return new ResponseEntity<>(allGrapeEntities, HttpStatus.OK);
    }
}
