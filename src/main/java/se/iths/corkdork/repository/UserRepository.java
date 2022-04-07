package se.iths.corkdork.repository;

import org.jetbrains.annotations.NotNull;
import se.iths.corkdork.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Override
    @NotNull
    Optional<UserEntity> findById(@NotNull Long id);
}
