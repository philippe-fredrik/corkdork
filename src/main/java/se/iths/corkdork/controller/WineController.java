package se.iths.corkdork.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import se.iths.corkdork.dtos.Wine;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.corkdork.exception.BadRequestException;
import se.iths.corkdork.exception.EntityNotFoundException;
import se.iths.corkdork.service.WineService;

@RestController
@RequestMapping("wines")
public class WineController {

    private final WineService wineService;

    public WineController(WineService wineService) {
        this.wineService = wineService;
    }


    @PostMapping("")
    public ResponseEntity<Wine> createWine(@Validated @RequestBody Wine wine, BindingResult errors) {

        if (errors.hasErrors())
            throw new BadRequestException("Invalid input", errors);

        Wine createdWine = wineService.createWine(wine);

        return new ResponseEntity<>(createdWine, HttpStatus.CREATED);
    }


    @PutMapping("{id}")
    public ResponseEntity<Wine> updateWine(@PathVariable Long id, @Validated @RequestBody Wine wine, BindingResult errors) {

        if (errors.hasErrors())
            throw new EntityNotFoundException(notFound(id));

        wineService.updateWine(id, wine);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping({"{id}"})
    public ResponseEntity<Void> deleteWine(@PathVariable Long id) {

        wineService.deleteWine(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}")
    public ResponseEntity<Wine> findWineById(@PathVariable Long id) {

        Wine wine = wineService.findWineById(id);

        return new ResponseEntity<>(wine, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Iterable<Wine>> findAllWines() {

        Iterable<Wine> allWinesEntities = wineService.findAllWines();

        if (!allWinesEntities.iterator().hasNext())
            throw new EntityNotFoundException("Failed to find any wines.");

        return new ResponseEntity<>(allWinesEntities, HttpStatus.OK);

    }

    private String notFound(Long id) {
        return "User with ID: " + id + " was not found.";
    }
}
