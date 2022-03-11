package service;

import entity.WineEntity;
import org.springframework.stereotype.Service;
import repository.WineRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class WineService {

    private final WineRepository wineRepository;

    public WineService(WineRepository wineRepository) {
        this.wineRepository = wineRepository;
    }

    public WineEntity createWine(WineEntity wineEntity){
        return wineRepository.save(wineEntity);
    }

    public void deleteWine(Long id){
        WineEntity foundWine = wineRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        wineRepository.deleteById(foundWine.getId());
    }
    public Optional<WineEntity> findWineById(Long id) { return wineRepository.findById(id);}

    public Iterable<WineEntity> findAllWines(){
        return wineRepository.findAll();
    }
}
