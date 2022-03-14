package se.iths.corkdork.controller;

import se.iths.corkdork.entity.GrapeEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.corkdork.controller.service.GrapeService;

import java.util.Optional;

@RestController
@RequestMapping("grapes")
public class GrapeController {

    private final GrapeService grapeService;

    public GrapeController(GrapeService grapeService) {
        this.grapeService = grapeService;
    }

    @PostMapping("")
    public ResponseEntity<GrapeEntity> createGrape(@RequestBody GrapeEntity grapeEntity) {
        GrapeEntity createdGrape = grapeService.createGrape(grapeEntity);
        return new ResponseEntity<>(createdGrape, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<GrapeEntity>> findGrapeById(@PathVariable Long id) {
        Optional<GrapeEntity> foundGrape = grapeService.findById(id);
        return new ResponseEntity<>(foundGrape, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Iterable<GrapeEntity>> findAllGrapes() {
        Iterable<GrapeEntity> allGrapes = grapeService.getAllGrapes();
        return new ResponseEntity<>(allGrapes, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        grapeService.deleteGrape(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
