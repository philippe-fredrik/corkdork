package se.iths.corkdork.service;

import se.iths.corkdork.entity.UserEntity;
import org.springframework.stereotype.Service;
import se.iths.corkdork.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public void deleteUser(Long id) {
        UserEntity foundUser = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        userRepository.deleteById(foundUser.getId());
    }

    public Optional<UserEntity> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public Iterable<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void updateUser(Long id, UserEntity userEntity) {
        UserEntity foundUser = userRepository.findById(id).orElseThrow();
        userRepository.save(foundUser);
    }
}
