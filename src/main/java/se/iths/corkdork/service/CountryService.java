package se.iths.corkdork.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import se.iths.corkdork.dtos.Country;
import se.iths.corkdork.dtos.User;
import se.iths.corkdork.dtos.Wine;
import se.iths.corkdork.entity.CountryEntity;
import org.springframework.stereotype.Service;
import se.iths.corkdork.entity.WineEntity;
import se.iths.corkdork.repository.CountryRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CountryService {

    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;

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
            throw new se.iths.corkdork.exception.EntityNotFoundException("No country with id "+id+" was found.");

        CountryEntity countryEntity = modelMapper.map(country, CountryEntity.class);

        countryEntity.setId(id);

        countryRepository.save(countryEntity);

    }

    public Country findCountryById(Long id) {
        Optional<CountryEntity> foundCountry = countryRepository.findById(id);

        if (foundCountry.isEmpty())
            throw new se.iths.corkdork.exception.EntityNotFoundException("No country with id "+id+" was found.");

        return modelMapper.map(foundCountry.get(), Country.class);
    }

    public Iterable<Country> findAllCountries() {
        Iterable<CountryEntity> allCountryEntities = countryRepository.findAll();

        return modelMapper.map(allCountryEntities, new TypeToken<Iterable<User>>() {
        }.getType());


    }

    public void deleteCountry(Long id) {
        CountryEntity foundCountry = countryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        countryRepository.deleteById(foundCountry.getId());
    }

    public CountryEntity addWine(String name, Wine wine) {

        WineEntity wineEntity = modelMapper.map(wine, WineEntity.class);

        CountryEntity countryToUpdate = countryRepository.findByName(name);

        countryToUpdate.addWine(wineEntity);

        return countryRepository.save(countryToUpdate);
    }
}