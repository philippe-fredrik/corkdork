package se.iths.corkdork.controller;

import se.iths.corkdork.entity.ColorEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.corkdork.exception.BadRequestException;
import se.iths.corkdork.exception.EntityNotFoundException;
import se.iths.corkdork.service.ColorService;

import java.util.Optional;

@RestController
@RequestMapping("colors")
public class ColorController {

    ColorService colorService;

    public ColorController(ColorService colorService){
        this.colorService = colorService;
    }

    @PostMapping
    public ResponseEntity<ColorEntity> createColor(@RequestBody ColorEntity colorEntity){
        if(colorEntity.getColor().isEmpty())
            throw new BadRequestException("Color field is mandatory.");

        ColorEntity createdColor = colorService.createColor(colorEntity);
        return new ResponseEntity<>(createdColor, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ColorEntity> updateColor(@PathVariable Long id, @RequestBody ColorEntity colorEntity) {
        if(colorService.findColorById(id).isEmpty())
            throw new EntityNotFoundException(notFound(id));

        colorService.updateColor(id, colorEntity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteColor(@PathVariable Long id){
        if(colorService.findColorById(id).isEmpty())
            throw new EntityNotFoundException(notFound(id));

        colorService.deleteColor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<ColorEntity>> findColorById(@PathVariable Long id){
        if(colorService.findColorById(id).isEmpty())
            throw new EntityNotFoundException(notFound(id));

        Optional<ColorEntity> foundColor = colorService.findColorById(id);
        return new ResponseEntity<>(foundColor,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<ColorEntity>> findAllColors(){
        Iterable<ColorEntity> allColors = colorService.findAllCountries();
        if(!allColors.iterator().hasNext())
            throw new EntityNotFoundException("Failed to find any colors.");

        return new ResponseEntity<>(allColors, HttpStatus.OK);
    }

    private String notFound(Long id) {
        return "Color with ID: " +id+ " was not found.";
    }
}
