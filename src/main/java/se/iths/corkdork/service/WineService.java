package se.iths.corkdork.service;


import org.modelmapper.ModelMapper;
import se.iths.corkdork.dtos.Wine;
import se.iths.corkdork.entity.WineEntity;
import org.springframework.stereotype.Service;
import se.iths.corkdork.repository.WineRepository;
import se.iths.corkdork.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WineService {

    private final WineRepository wineRepository;
    private final ModelMapper modelMapper;
    private static final String NOWINEID = "No wine with id ";
    private static final String WASFOUND = " was found";

    public WineService(WineRepository wineRepository, ModelMapper modelMapper) {
        this.wineRepository = wineRepository;
        this.modelMapper = modelMapper;
    }

    public Wine createWine(Wine wine){

        WineEntity wineEntity = modelMapper.map(wine, WineEntity.class);

        return modelMapper.map(wineRepository.save(wineEntity), Wine.class);
    }

    public void updateWine(Long id, Wine wine) {

        Optional<WineEntity> foundWine = wineRepository.findById(id);

        if (foundWine.isEmpty())
            throw new EntityNotFoundException(NOWINEID+id+WASFOUND);

        WineEntity wineEntity = modelMapper.map(wine, WineEntity.class);

        wineEntity.setId(id);

        wineRepository.save(wineEntity);

        }

    public void deleteWine(Long id){

        Optional<WineEntity> foundWine = wineRepository.findById(id);
        if (foundWine.isEmpty())
            throw new EntityNotFoundException(NOWINEID+ id +WASFOUND);

        wineRepository.deleteById(id);
    }

    public Wine findWineById(Long id) {

        Optional<WineEntity> foundWine = wineRepository.findById(id);

        if (foundWine.isEmpty())
            throw new EntityNotFoundException(NOWINEID+id+WASFOUND);

        return modelMapper.map(foundWine, Wine.class);
    }

    public List<Wine> findAllWines(){
        Iterable<WineEntity> allWinesEntities = wineRepository.findAll();

        List<Wine> allWines = new ArrayList<>();
        allWinesEntities.forEach(wine -> allWines.add(modelMapper.map(wine, Wine.class)));
        return allWines;
    }

}
