package se.iths.corkdork.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import se.iths.corkdork.dtos.User;
import se.iths.corkdork.entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.corkdork.exception.EntityNotFoundException;
import se.iths.corkdork.service.UserService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PutMapping("admin/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        if (userService.findUserById(id).isEmpty())
            throw new EntityNotFoundException(notFound(id));

        UserEntity updatedUser = userService.updateUser(id, modelMapper.map(user, UserEntity.class));
        User response = modelMapper.map(updatedUser, User.class);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("admin/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.findUserById(id).isEmpty())
            throw new EntityNotFoundException(notFound(id));

        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("public/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        Optional<UserEntity> foundUser = userService.findUserById(id);

        if (foundUser.isEmpty()) {
            throw new EntityNotFoundException(notFound(id));
        }

        User user = modelMapper.map(foundUser.get(), User.class);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("signup")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {

        UserEntity createdUser = userService.createUser(modelMapper.map(user, UserEntity.class));
        User response = modelMapper.map(createdUser, User.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("public")
    public ResponseEntity<Iterable<User>> findAllUsers() {
        Iterable<UserEntity> allUserEntities = userService.findAllUsers();
        if (!allUserEntities.iterator().hasNext())
            throw new EntityNotFoundException("Failed to find any wines.");
      
        Iterable<User> allUsers = modelMapper.map(allUserEntities, new TypeToken<Iterable<User>>() {
        }.getType());

        return new ResponseEntity<>(allUsers, HttpStatus.OK);

    }

    private String notFound(Long id) {
        return "User with ID: " + id + " was not found.";
    }

}
