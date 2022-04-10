package se.iths.corkdork.service;

import org.modelmapper.ModelMapper;
import se.iths.corkdork.dtos.Country;
import se.iths.corkdork.dtos.Grape;
import se.iths.corkdork.entity.CountryEntity;
import se.iths.corkdork.entity.GrapeEntity;
import org.springframework.stereotype.Service;
import se.iths.corkdork.repository.GrapeRepository;
import se.iths.corkdork.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GrapeService {

    private final GrapeRepository grapeRepository;
    private final ModelMapper modelMapper;
    private static final String NOGRAPEID = "No grape with id ";
    private static final String WASFOUND = " was found";

    public GrapeService(GrapeRepository grapeRepository, ModelMapper modelMapper) {
        this.grapeRepository = grapeRepository;
        this.modelMapper = modelMapper;
    }

    public Grape createGrape(Grape grape) {

        GrapeEntity grapeEntity = modelMapper.map(grape, GrapeEntity.class);

        return modelMapper.map(grapeRepository.save(grapeEntity), Grape.class);
      
    }

    public void updateGrape(Long id, Grape grape) {

        Optional<GrapeEntity> foundGrape = grapeRepository.findById(id);
        if (foundGrape.isEmpty())
            throw new EntityNotFoundException(NOGRAPEID+id+WASFOUND);

        GrapeEntity grapeEntity = modelMapper.map(grape, GrapeEntity.class);

        grapeEntity.setId(id);

        grapeRepository.save(grapeEntity);
    }

    public Grape findById(Long id) {
        Optional<GrapeEntity> foundGrape = grapeRepository.findById(id);

        if (foundGrape.isEmpty())
            throw new EntityNotFoundException(NOGRAPEID+id+WASFOUND);

        return modelMapper.map(foundGrape, Grape.class);
    }

   public List<Grape> getAllGrapes() {
       Iterable<GrapeEntity> allGrapeEntities = grapeRepository.findAll();

       List<Grape> allGrapes = new ArrayList<>();
       allGrapeEntities.forEach(grape -> allGrapes.add(modelMapper.map(grape, Grape.class)));
       return allGrapes;
   }

   public void deleteGrape(Long id) {
       Optional<GrapeEntity> foundGrape = grapeRepository.findById(id);
       if (foundGrape.isEmpty())
           throw new EntityNotFoundException(NOGRAPEID+ id +WASFOUND);

        grapeRepository.deleteById(id);
   }


    public GrapeEntity addCountry(String name, Country country) {

        CountryEntity countryEntity = modelMapper.map(country, CountryEntity.class);

        GrapeEntity grapeToUpdate = grapeRepository.findByName(name);

        grapeToUpdate.setCountry(countryEntity);

        return grapeRepository.save(grapeToUpdate);
    }
}
