package se.iths.corkdork.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import se.iths.corkdork.dtos.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.corkdork.exception.BadRequestException;
import se.iths.corkdork.exception.EntityNotFoundException;
import se.iths.corkdork.messaging.MessagePublisher;
import se.iths.corkdork.service.UserService;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final MessagePublisher messagePublisher;

    public UserController(UserService userService, MessagePublisher messagePublisher) {
        this.userService = userService;
        this.messagePublisher = messagePublisher;
    }

    @PostMapping("signup")
    public ResponseEntity<User> createUser(@Validated @RequestBody User user, BindingResult errors) {

        if (errors.hasErrors())
            throw new BadRequestException("Invalid input", errors);
        User createdUser = userService.createUser(user);
        messagePublisher.sendMessage(createdUser.getUsername());

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Validated @RequestBody User user, BindingResult errors) {

        if (errors.hasErrors())
            throw new EntityNotFoundException(notFound(id));

        userService.updateUser(id, user);

        return new ResponseEntity<>(HttpStatus.OK);
    }



    @GetMapping("{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {

        User user = userService.findUserById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @GetMapping("")
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = userService.findAllUsers();

        if(users.isEmpty())
            throw new EntityNotFoundException("Failed to find any users");

        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private String notFound(Long id) {
        return "User with ID: " + id + " was not found.";
    }

}
