package se.iths.corkdork.service;

import se.iths.corkdork.entity.ColorEntity;
import se.iths.corkdork.entity.CountryEntity;
import org.springframework.stereotype.Service;
import se.iths.corkdork.entity.GrapeEntity;
import se.iths.corkdork.repository.CountryRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.awt.*;
import java.util.Optional;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public CountryEntity createCountry(CountryEntity countryEntity) {
        return countryRepository.save(countryEntity);
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
    public void updateCountry(Long id, CountryEntity countryEntity) {
        CountryEntity foundCountry = countryRepository.findById(id).orElseThrow();
        countryRepository.save(foundCountry);
    }
}