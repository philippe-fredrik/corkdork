package se.iths.corkdork.repository;

import se.iths.corkdork.entity.CountryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends CrudRepository<CountryEntity, Long> {
    CountryEntity findByCountryName(String name);
}