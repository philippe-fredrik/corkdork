package se.iths.corkdork.service;


import org.modelmapper.ModelMapper;
import se.iths.corkdork.dtos.Wine;
import se.iths.corkdork.entity.WineEntity;
import org.springframework.stereotype.Service;
import se.iths.corkdork.repository.WineRepository;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class WineService {

    private final WineRepository wineRepository;
    private final ModelMapper modelMapper;

    public WineService(WineRepository wineRepository, ModelMapper modelMapper) {
        this.wineRepository = wineRepository;
        this.modelMapper = modelMapper;
    }

    public Wine createWine(Wine wine){

        WineEntity wineEntity = modelMapper.map(wine, WineEntity.class);

        return modelMapper.map(wineRepository.save(wineEntity), Wine.class);
    }

    public void updateWine(Long id, Wine wine) {

        WineEntity wineEntity = modelMapper.map(wine, WineEntity.class);

        wineEntity.setId(id);

        wineRepository.save(wineEntity);

        }

    public void deleteWine(Long id){
        WineEntity foundWine = wineRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        wineRepository.deleteById(foundWine.getId());
    }

    public Optional<WineEntity> findWineById(Long id) {
        return wineRepository.findById(id);
    }

    public Iterable<WineEntity> findAllWines(){
        return wineRepository.findAll();
    }
}
