package controller;

import entity.ColorEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ColorService;

import java.util.Optional;

@RestController
@RequestMapping("countries")
public class ColorController {

    ColorService colorService;

    public ColorController(ColorService colorService){
        this.colorService = colorService;
    }

    @PostMapping("create")
    public ResponseEntity<ColorEntity> createColor(@RequestBody ColorEntity colorEntity){
        ColorEntity createdColor = colorService.createColor(colorEntity);

        return new ResponseEntity<>(createdColor, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteColor(@PathVariable Long id){
        colorService.deleteColor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<ColorEntity>> findColorById(@PathVariable Long id){
        Optional<ColorEntity> foundColor = colorService.findColorById(id);

        return new ResponseEntity<>(foundColor,HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Iterable<ColorEntity>> findAllColors(){
        Iterable<ColorEntity> allColors = colorService.findAllCountries();
        return new ResponseEntity<>(allColors, HttpStatus.OK);
    }

}
