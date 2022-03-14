package se.iths.corkdork.controller.service;

import se.iths.corkdork.entity.ColorEntity;
import org.springframework.stereotype.Service;
import se.iths.corkdork.repository.ColorRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ColorService {

    private final ColorRepository colorRepository;

    public ColorService(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    public ColorEntity createColor(ColorEntity colorEntity){
        return colorRepository.save(colorEntity);
    }

    public void deleteColor(Long id){
        ColorEntity foundColor = colorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        colorRepository.deleteById(foundColor.getId());
    }

    public Optional<ColorEntity> findColorById(Long id){
        return colorRepository.findById(id);
    }
    public Iterable<ColorEntity> findAllCountries(){
        return colorRepository.findAll();
    }
}
