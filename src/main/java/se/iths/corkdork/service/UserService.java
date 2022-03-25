package se.iths.corkdork.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import se.iths.corkdork.entity.RoleEntity;
import se.iths.corkdork.entity.UserEntity;
import org.springframework.stereotype.Service;
import se.iths.corkdork.repository.RoleRepository;
import se.iths.corkdork.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public UserEntity createUser(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        RoleEntity roleToAdd = roleRepository.findByRole("USER");
        userEntity.setRole(roleToAdd);
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

    public void updateUser(Long id, UserEntity userEntity) {
        userEntity.setId(id);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
    }
}
