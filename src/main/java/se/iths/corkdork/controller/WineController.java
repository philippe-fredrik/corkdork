package se.iths.corkdork.controller;

import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import se.iths.corkdork.dtos.Wine;
import se.iths.corkdork.entity.WineEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.corkdork.exception.BadRequestException;
import se.iths.corkdork.exception.EntityNotFoundException;
import se.iths.corkdork.service.WineService;

import java.util.Optional;


@RestController
@RequestMapping("wines")
public class WineController {

    private final WineService wineService;
    private final ModelMapper modelMapper;

    public WineController(WineService wineService, ModelMapper modelMapper) {
        this.wineService = wineService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("")
    public ResponseEntity<Wine> createWine(@RequestBody Wine wine) {
        if (wine.getName().isEmpty())
            throw new BadRequestException("Name cannot be empty.");

        WineEntity createdWine = wineService.createWine(modelMapper.map(wine, WineEntity.class));
        Wine response = modelMapper.map(createdWine, Wine.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PutMapping("{id}")
    public ResponseEntity<Wine> updateWine(@PathVariable Long id, @RequestBody Wine wine) {
        if (wineService.findWineById(id).isEmpty())
            throw new EntityNotFoundException(notFound(id));

        WineEntity updatedWine = wineService.updateWine(id, modelMapper.map(wine, WineEntity.class));
        Wine response = modelMapper.map(updatedWine, Wine.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping({"{id}"})
    public ResponseEntity<Void> deleteWine(@PathVariable Long id) {
        if (wineService.findWineById(id).isEmpty())
            throw new EntityNotFoundException(notFound(id));

        wineService.deleteWine(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Wine> findWineById(@PathVariable Long id) {
        Optional<WineEntity> foundWine = wineService.findWineById(id);

        if (foundWine.isEmpty()) {
            throw new EntityNotFoundException(notFound(id));
        }

        Wine wine = modelMapper.map(foundWine.get(), Wine.class);

        return new ResponseEntity<>(wine, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Iterable<Wine>> findAllWines() {
        Iterable<WineEntity> allWinesEntities = wineService.findAllWines();

        if (!allWinesEntities.iterator().hasNext())
            throw new EntityNotFoundException("Failed to find any wines.");

        Iterable<Wine> allWines = modelMapper.map(
                allWinesEntities,
                new TypeToken<Iterable<Wine>>() {
                }.getType());

        return new ResponseEntity<>(allWines, HttpStatus.OK);
    }

    @NotNull
    private String notFound(Long id) {
        return "Wine with ID: " + id + "was not found.";
    }
}
