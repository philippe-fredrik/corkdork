package se.iths.corkdork.service;


import se.iths.corkdork.entity.GrapeEntity;
import org.springframework.stereotype.Service;
import se.iths.corkdork.repository.GrapeRepository;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class GrapeService {

    private final GrapeRepository grapeRepository;


    public GrapeService(GrapeRepository grapeRepository) {
        this.grapeRepository = grapeRepository;
    }

    public GrapeEntity createGrape(GrapeEntity grapeEntity) {
        return grapeRepository.save(grapeEntity);
    }

    public Optional<GrapeEntity> findById(Long id) {
        return grapeRepository.findById(id);
    }

   public Iterable<GrapeEntity> getAllGrapes() {
        return grapeRepository.findAll();
   }

   public void deleteGrape(Long id) {
        GrapeEntity foundGrape = grapeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        grapeRepository.deleteById(foundGrape.getId());
   }

    public GrapeEntity updateGrape(Long id, GrapeEntity grapeEntity) {
        grapeEntity.setId(id);
        grapeRepository.save(grapeEntity);
        return grapeEntity;
    }
}
