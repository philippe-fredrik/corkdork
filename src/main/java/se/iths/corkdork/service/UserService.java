package se.iths.corkdork.service;

import org.modelmapper.ModelMapper;
import se.iths.corkdork.dtos.User;
import se.iths.corkdork.entity.UserEntity;
import org.springframework.stereotype.Service;
import se.iths.corkdork.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public User createUser(User user) {

        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        return modelMapper.map(userRepository.save(userEntity), User.class);
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
    public void updateUser(Long id, User user) {
        UserEntity foundUser = userRepository.findById(id).orElseThrow();
        userRepository.save(foundUser);
    }
}
