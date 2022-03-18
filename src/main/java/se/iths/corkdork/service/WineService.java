package se.iths.corkdork.service;

import se.iths.corkdork.entity.WineEntity;
import org.springframework.stereotype.Service;
import se.iths.corkdork.repository.WineRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
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

    @Transactional
    public WineEntity updateWineName(Long id, String name) {
        WineEntity fromDatabase = wineRepository.findById(id).orElseThrow();
        fromDatabase.setName(name);

        return wineRepository.save(fromDatabase);
    }

    @Transactional
    public void updateWine(Long id, WineEntity wineEntity) {
        WineEntity foundWine = wineRepository.findById(id).orElseThrow();
        wineRepository.save(foundWine);
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
