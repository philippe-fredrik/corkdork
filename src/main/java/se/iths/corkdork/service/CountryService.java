package se.iths.corkdork.service;

import org.modelmapper.ModelMapper;
import se.iths.corkdork.dtos.Country;
import se.iths.corkdork.dtos.Wine;
import se.iths.corkdork.entity.CountryEntity;
import org.springframework.stereotype.Service;
import se.iths.corkdork.entity.WineEntity;
import se.iths.corkdork.repository.CountryRepository;
import se.iths.corkdork.exception.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private static final String NOCOUNTRYID = "No country with id ";
    private static final String WASFOUND = " was found";

    public CountryService(CountryRepository countryRepository, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }

    public Country createCountry(Country country) {

        CountryEntity countryEntity = modelMapper.map(country, CountryEntity.class);

        return modelMapper.map(countryRepository.save(countryEntity),Country.class);
    }

    @Transactional
    public void updateCountry(Long id, Country country) {

        Optional<CountryEntity> foundCountry = countryRepository.findById(id);
        if (foundCountry.isEmpty())
            throw new EntityNotFoundException(NOCOUNTRYID+id+WASFOUND);

        CountryEntity countryEntity = modelMapper.map(country, CountryEntity.class);

        countryEntity.setId(id);

        countryRepository.save(countryEntity);

    }

    public Country findCountryById(Long id) {
        Optional<CountryEntity> foundCountry = countryRepository.findById(id);

        if (foundCountry.isEmpty())
            throw new EntityNotFoundException(NOCOUNTRYID+id+WASFOUND);

        return modelMapper.map(foundCountry, Country.class);
    }

    public List<Country> findAllCountries() {
        Iterable<CountryEntity> allCountryEntities = countryRepository.findAll();

        List<Country> countries = new ArrayList<>();
        allCountryEntities.forEach(country -> countries.add(modelMapper.map(country, Country.class)));
        return countries;


    }

    public void deleteCountry(Long id) {

        Optional<CountryEntity> foundCountry = countryRepository.findById(id);
        if (foundCountry.isEmpty())
            throw new EntityNotFoundException(NOCOUNTRYID+id+WASFOUND);

        countryRepository.deleteById(id);
    }

    public CountryEntity addWine(String name, Wine wine) {

        WineEntity wineEntity = modelMapper.map(wine, WineEntity.class);

        CountryEntity countryToUpdate = countryRepository.findByName(name);

        countryToUpdate.addWine(wineEntity);

        return countryRepository.save(countryToUpdate);
    }
}