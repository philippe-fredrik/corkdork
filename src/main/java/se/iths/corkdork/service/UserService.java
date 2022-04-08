package se.iths.corkdork.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.iths.corkdork.dtos.User;
import se.iths.corkdork.entity.RoleEntity;
import se.iths.corkdork.entity.UserEntity;
import org.springframework.stereotype.Service;
import se.iths.corkdork.repository.RoleRepository;
import se.iths.corkdork.repository.UserRepository;
import se.iths.corkdork.exception.EntityNotFoundException;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       ModelMapper modelMapper) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public User createUser(User user) {

        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        RoleEntity roleToAdd = roleRepository.findByRole("USER");
        userEntity.setRole(roleToAdd);

        return modelMapper.map(userRepository.save(userEntity), User.class);
    }

    public void updateUser(Long id, User user) {

        Optional<UserEntity> foundUser = userRepository.findById(id);
        if (foundUser.isEmpty())
            throw new EntityNotFoundException("No user with id "+id+" was found.");

        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        userEntity.setId(id);

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        userRepository.save(userEntity);
    }

    public User findUserById(Long id) {

        Optional<UserEntity> foundUser = userRepository.findById(id);
        if (foundUser.isEmpty())
            throw new EntityNotFoundException("No user with id "+id+" was found.");

        return modelMapper.map(foundUser.get(), User.class);
    }

    public Iterable<User> findAllUsers() {

        Iterable<UserEntity> allUserEntities = userRepository.findAll();

        return modelMapper.map(allUserEntities, new TypeToken<Iterable<User>>() {
        }.getType());
    }

    public void deleteUser(Long id) {

        UserEntity foundUser = userRepository.findById(id).orElseThrow(javax.persistence.EntityNotFoundException::new);

        userRepository.deleteById(foundUser.getId());
    }
}
