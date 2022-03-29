package se.iths.corkdork.repository;

import se.iths.corkdork.entity.GrapeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrapeRepository extends CrudRepository<GrapeEntity, Long> {

}
