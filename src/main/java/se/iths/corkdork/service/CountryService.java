package se.iths.corkdork.service;

import org.modelmapper.ModelMapper;
import se.iths.corkdork.dtos.Country;
import se.iths.corkdork.entity.CountryEntity;
import org.springframework.stereotype.Service;
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

        return modelMapper.map(countryRepository.save(countryEntity), Country.class);
    }

    public void deleteCountry(Long id) {
        CountryEntity foundCountry = countryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        countryRepository.deleteById(foundCountry.getId());
    }

    public Optional<CountryEntity> findCountryById(Long id) {
        return countryRepository.findById(id);
    }

    public Iterable<CountryEntity> findAllCountries() {
        return countryRepository.findAll();
    }

    @Transactional
    public void updateCountry(Long id, Country country) {
        CountryEntity foundCountry = countryRepository.findById(id).orElseThrow();
        countryRepository.save(foundCountry);
    }
}