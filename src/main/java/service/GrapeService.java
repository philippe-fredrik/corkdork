package service;

import entity.GrapeEntity;
import org.springframework.stereotype.Service;
import repository.GrapeRepository;

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

}
