package se.iths.corkdork.controller;

import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.validation.BindingResult;
import se.iths.corkdork.dtos.Grape;
import se.iths.corkdork.entity.GrapeEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.corkdork.exception.BadRequestException;
import se.iths.corkdork.exception.EntityNotFoundException;
import se.iths.corkdork.service.GrapeService;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("grapes")
public class GrapeController {

    private final GrapeService grapeService;
    private final ModelMapper modelMapper;

    public GrapeController(GrapeService grapeService, ModelMapper modelMapper) {
        this.grapeService = grapeService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/admin/create")
    public ResponseEntity<Grape> createGrape(@Valid @RequestBody Grape grape, BindingResult errors) {
        if(errors.hasErrors())
            throw new BadRequestException("Name and color fields are mandatory", errors);

        GrapeEntity createdGrape = grapeService.createGrape(modelMapper.map(grape, GrapeEntity.class));
        Grape response = modelMapper.map(createdGrape, Grape.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if(grapeService.findById(id).isEmpty())
            throw new EntityNotFoundException(notFound(id));

        grapeService.deleteGrape(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("admin/{id}")
    public ResponseEntity<Grape> updateGrape(@PathVariable Long id, @RequestBody Grape grape) {
        if(grapeService.findById(id).isEmpty())
            throw new EntityNotFoundException(notFound(id));

        GrapeEntity updatedGrape = grapeService.updateGrape(id, modelMapper.map(grape, GrapeEntity.class));
        Grape response = modelMapper.map(updatedGrape, Grape.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Grape> findGrapeById(@PathVariable Long id) {
        Optional<GrapeEntity> foundGrape = grapeService.findById(id);
        if(foundGrape.isEmpty())
            throw new EntityNotFoundException(notFound(id));

        Grape grape = modelMapper.map(foundGrape.get(), Grape.class);
        return new ResponseEntity<>(grape, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<Grape>> findAllGrapes() {
        Iterable<GrapeEntity> allGrapeEntities = grapeService.getAllGrapes();

        if(!allGrapeEntities.iterator().hasNext())
            throw new EntityNotFoundException("Failed to find any grapes.");

        Iterable<Grape> allGrapes = modelMapper.map(
                allGrapeEntities,
                new TypeToken<Iterable<Grape>>() {
                }.getType());

        return new ResponseEntity<>(allGrapes, HttpStatus.OK);
    }

    @NotNull
    private String notFound(Long id) {
        return "Grape with ID: " + id + " was not found.";
    }
}
