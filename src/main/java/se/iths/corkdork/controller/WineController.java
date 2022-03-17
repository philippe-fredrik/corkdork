package se.iths.corkdork.controller;

import se.iths.corkdork.entity.WineEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.corkdork.service.WineService;

import java.util.Optional;

@RestController
@RequestMapping("wines")
public class WineController {

    WineService wineService;

    public WineController(WineService wineService){
        this.wineService = wineService;
    }

    @PostMapping
    public ResponseEntity<WineEntity> createWine(@RequestBody WineEntity wineEntity){
        WineEntity createdWine = wineService.createWine(wineEntity);

        return new ResponseEntity<>(createdWine, HttpStatus.CREATED);
    }

    @DeleteMapping({"{id}"})
    public ResponseEntity<Void> deleteWine(@PathVariable Long id){
        wineService.deleteWine(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<WineEntity>> findWineById(@PathVariable Long id) {
        Optional<WineEntity> foundWine = wineService.findWineById(id);

        return new ResponseEntity<>(foundWine, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<WineEntity>> findAllWines(){
        Iterable<WineEntity> allWines = wineService.findAllWines();
        return new ResponseEntity<>(allWines, HttpStatus.OK);
    }
}
