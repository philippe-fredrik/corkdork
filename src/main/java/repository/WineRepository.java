package repository;

import entity.WineEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WineRepository extends CrudRepository<WineEntity, Long> {

}