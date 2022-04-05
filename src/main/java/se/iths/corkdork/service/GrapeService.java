package se.iths.corkdork.service;


import org.modelmapper.ModelMapper;
import se.iths.corkdork.dtos.Grape;
import se.iths.corkdork.entity.GrapeEntity;
import org.springframework.stereotype.Service;
import se.iths.corkdork.repository.GrapeRepository;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class GrapeService {

    private final GrapeRepository grapeRepository;
    private final ModelMapper modelMapper;

    public GrapeService(GrapeRepository grapeRepository, ModelMapper modelMapper) {
        this.grapeRepository = grapeRepository;
        this.modelMapper = modelMapper;
    }

    public Grape createGrape(Grape grape) {

        GrapeEntity grapeEntity = modelMapper.map(grape, GrapeEntity.class);

        return modelMapper.map(grapeRepository.save(grapeEntity), Grape.class);
    }

    public void updateGrape(Long id, Grape grape) {

        GrapeEntity grapeEntity = modelMapper.map(grape, GrapeEntity.class);

        grapeEntity.setId(id);

        grapeRepository.save(grapeEntity);
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
}
