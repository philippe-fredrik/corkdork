package se.iths.corkdork.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import se.iths.corkdork.dtos.Country;
import se.iths.corkdork.dtos.Grape;
import se.iths.corkdork.entity.CountryEntity;
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

    public Grape findById(Long id) {
        Optional<GrapeEntity> foundGrape = grapeRepository.findById(id);

        return modelMapper.map(foundGrape.get(), Grape.class);
    }

   public Iterable<Grape> getAllGrapes() {
       Iterable<GrapeEntity> allGrapeEntities = grapeRepository.findAll();

       return modelMapper.map(allGrapeEntities, new TypeToken<Iterable<Grape>>() {
       }.getType());
   }

   public void deleteGrape(Long id) {
        GrapeEntity foundGrape = grapeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        grapeRepository.deleteById(foundGrape.getId());
   }


    public GrapeEntity addCountry(String name, Country country) {

        CountryEntity countryEntity = modelMapper.map(country, CountryEntity.class);

        GrapeEntity grapeToUpdate = grapeRepository.findByName(name);

        grapeToUpdate.setCountry(countryEntity);

        return grapeRepository.save(grapeToUpdate);
    }
}
