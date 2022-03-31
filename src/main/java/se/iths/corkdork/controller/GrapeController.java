package se.iths.corkdork.controller;

import org.jetbrains.annotations.NotNull;
import se.iths.corkdork.dtos.Grape;
import se.iths.corkdork.entity.GrapeEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.corkdork.exception.BadRequestException;
import se.iths.corkdork.exception.EntityNotFoundException;
import se.iths.corkdork.service.GrapeService;

import java.util.Optional;

@RestController
@RequestMapping("grapes")
public class GrapeController {

    private final GrapeService grapeService;

    public GrapeController(GrapeService grapeService) {
        this.grapeService = grapeService;
    }

    @PostMapping("/admin/create")
    public ResponseEntity<Grape> createGrape(@RequestBody Grape grape) {
        if(grape.getName().isEmpty() || grape.getColor().isEmpty())
            throw new BadRequestException("Name and color fields are mandatory");

        Grape createdGrape = grapeService.createGrape(grape);
        return new ResponseEntity<>(createdGrape, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<GrapeEntity>> findGrapeById(@PathVariable Long id) {
        if(grapeService.findById(id).isEmpty())
            throw new EntityNotFoundException(notFound(id));

        Optional<GrapeEntity> foundGrape = grapeService.findById(id);
        return new ResponseEntity<>(foundGrape, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<GrapeEntity> updateGrape(@PathVariable Long id, @RequestBody Grape grape) {
        if(grapeService.findById(id).isEmpty())
            throw new EntityNotFoundException(notFound(id));

        grapeService.updateGrape(id, grape);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<GrapeEntity>> findAllGrapes() {
        Iterable<GrapeEntity> allGrapes = grapeService.getAllGrapes();
        if(!allGrapes.iterator().hasNext())
            throw new EntityNotFoundException("Failed to find any grapes.");

        return new ResponseEntity<>(allGrapes, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if(grapeService.findById(id).isEmpty())
            throw new EntityNotFoundException(notFound(id));

        grapeService.deleteGrape(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @NotNull
    private String notFound(Long id) {
        return "Grape with ID: " + id + " was not found.";
    }
}
