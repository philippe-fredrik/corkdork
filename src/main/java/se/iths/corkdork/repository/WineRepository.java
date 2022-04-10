package se.iths.corkdork.repository;

import se.iths.corkdork.entity.WineEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WineRepository extends CrudRepository<WineEntity, Long> {
    Optional<WineEntity> findByName(String name);
}
