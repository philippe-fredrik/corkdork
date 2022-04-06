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
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {

        userService.updateUser(id, user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {

        User user = userService.findUserById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @GetMapping("")
    public ResponseEntity<Iterable<User>> findAllUsers() {

        Iterable<User> allUserEntities = userService.findAllUsers();

        if (!allUserEntities.iterator().hasNext())
            throw new EntityNotFoundException("Failed to find any wines.");

        return new ResponseEntity<>(allUserEntities, HttpStatus.OK);

    }

}
