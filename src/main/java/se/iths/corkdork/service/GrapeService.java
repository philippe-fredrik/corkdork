package se.iths.corkdork.service;

import se.iths.corkdork.entity.GrapeEntity;
import org.springframework.stereotype.Service;
import se.iths.corkdork.entity.UserEntity;
import se.iths.corkdork.repository.GrapeRepository;

import javax.transaction.Transactional;
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
        grapeRepository.deleteById(id);
   }

    @Transactional
    public void updateGrape(Long id, GrapeEntity grapeEntity) {
        GrapeEntity foundGrape = grapeRepository.findById(id).orElseThrow();
        grapeRepository.save(foundGrape);
    }
}
