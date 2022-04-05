package se.iths.corkdork.controller;

import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

    @PostMapping("admin/create")
    public ResponseEntity<Wine> createWine(@Validated @RequestBody Wine wine, BindingResult errors) {

        if (errors.hasErrors())
            throw new BadRequestException("Invalid input", errors);

        Wine createdWine = wineService.createWine(wine);

        return new ResponseEntity<>(createdWine, HttpStatus.CREATED);
    }


    @PutMapping("admin/{id}")
    public ResponseEntity<Wine> updateWine(@PathVariable Long id, @RequestBody Wine wine) {

        if (wineService.findWineById(id).isEmpty())
            throw new se.iths.corkdork.exception.EntityNotFoundException(notFound(id));

        wineService.updateWine(id, wine);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping({"admin/{id}"})
    public ResponseEntity<Void> deleteWine(@PathVariable Long id) {
        if (wineService.findWineById(id).isEmpty())
            throw new EntityNotFoundException(notFound(id));

        wineService.deleteWine(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("public/{id}")
    public ResponseEntity<Wine> findWineById(@PathVariable Long id) {
        Optional<WineEntity> foundWine = wineService.findWineById(id);

        if (foundWine.isEmpty()) {
            throw new EntityNotFoundException(notFound(id));
        }

        Wine wine = modelMapper.map(foundWine.get(), Wine.class);

        return new ResponseEntity<>(wine, HttpStatus.OK);
    }

    @GetMapping("public")
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
