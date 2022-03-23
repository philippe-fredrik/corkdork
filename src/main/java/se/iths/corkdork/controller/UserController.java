package se.iths.corkdork.controller;

import org.jetbrains.annotations.NotNull;
import se.iths.corkdork.entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.corkdork.exception.BadRequestException;
import se.iths.corkdork.exception.EntityNotFoundException;
import se.iths.corkdork.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("signup")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity){
        if(userEntity.getFirstName().isEmpty() || userEntity.getLastName().isEmpty() || userEntity.getUsername().isEmpty()
        || userEntity.getPassword().isEmpty() || userEntity.getEmail().isEmpty())
            throw new BadRequestException("Every user credential is mandatory");

        UserEntity createdUser = userService.createUser(userEntity);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserEntity userEntity) {
        if(userService.findUserById(id).isEmpty())
            throw new EntityNotFoundException(notFound(id));

        userService.updateUser(id, userEntity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        if(userService.findUserById(id).isEmpty())
            throw new EntityNotFoundException(notFound(id));

        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<UserEntity>> findUserById(@PathVariable Long id){
        if(userService.findUserById(id).isEmpty())
            throw new EntityNotFoundException(notFound(id));

        Optional<UserEntity> foundUser = userService.findUserById(id);
        return new ResponseEntity<>(foundUser, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<UserEntity>> findAllUsers(){
        Iterable<UserEntity> allUsers = userService.findAllUsers();
        if(!allUsers.iterator().hasNext())
            throw new EntityNotFoundException("Failed to find any wines.");

        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @NotNull
    private String notFound(Long id) {
        return "User with ID: " + id + " was not found.";
    }

}
