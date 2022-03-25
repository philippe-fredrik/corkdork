package se.iths.corkdork.controller;

import org.jetbrains.annotations.NotNull;
import se.iths.corkdork.entity.WineEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.corkdork.exception.BadRequestException;
import se.iths.corkdork.exception.EntityNotFoundException;
import se.iths.corkdork.service.WineService;

import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("wines")
public class WineController {

    WineService wineService;

    public WineController(WineService wineService){
        this.wineService = wineService;
    }

    @PostMapping("admin/create")
    public ResponseEntity<WineEntity> createWine(@RequestBody WineEntity wineEntity){
        if(wineEntity.getName().isEmpty())
            throw new BadRequestException("Name cannot be empty.");

        WineEntity createdWine = wineService.createWine(wineEntity);
        return new ResponseEntity<>(createdWine, HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    public ResponseEntity<WineEntity> updateWineName(@PathVariable Long id, @RequestBody String name) {
        return new ResponseEntity<>(wineService.updateWineName(id, name), OK);
    }

    @PutMapping("admin/{id}")
    public ResponseEntity<Object> updateWine(@PathVariable Long id, @RequestBody WineEntity wineEntity) {
        if(wineService.findWineById(id).isEmpty())
            throw new EntityNotFoundException(notFound(id));

        wineService.updateWine(id, wineEntity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping({"admin/{id}"})
    public ResponseEntity<Void> deleteWine(@PathVariable Long id){
        if(wineService.findWineById(id).isEmpty())
            throw new EntityNotFoundException(notFound(id));

        wineService.deleteWine(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("public/{id}")
    public ResponseEntity<Optional<WineEntity>> findWineById(@PathVariable Long id) {
        if(wineService.findWineById(id).isEmpty())
            throw new EntityNotFoundException(notFound(id));

        Optional<WineEntity> foundWine = wineService.findWineById(id);
        return new ResponseEntity<>(foundWine, OK);
    }

    @GetMapping("public")
    public ResponseEntity<Iterable<WineEntity>> findAllWines(){
        Iterable<WineEntity> allWines = wineService.findAllWines();
        if(!allWines.iterator().hasNext())
            throw new EntityNotFoundException("Failed to find any wines.");

        return new ResponseEntity<>(allWines, OK);
    }

    @NotNull
    private String notFound(Long id) {
        return "Wine with ID: " + id + "was not found.";
    }
}
