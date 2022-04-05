package se.iths.corkdork.controller;

import org.springframework.validation.BindingResult;
import se.iths.corkdork.dtos.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.corkdork.exception.BadRequestException;
import se.iths.corkdork.exception.EntityNotFoundException;
import se.iths.corkdork.service.UserService;
import javax.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("signup")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user, BindingResult errors) {

        if (errors.hasErrors())
            throw new BadRequestException("Invalid input", errors);

        User createdItem = userService.createUser(user);

        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @PutMapping("admin/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {

        userService.updateUser(id, user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("admin/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("public/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {

        User user = userService.findUserById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("public")
    public ResponseEntity<Iterable<User>> findAllUsers() {
        Iterable<User> allUserEntities = userService.findAllUsers();
        if (!allUserEntities.iterator().hasNext())
            throw new EntityNotFoundException("Failed to find any wines.");

        return new ResponseEntity<>(allUserEntities, HttpStatus.OK);

    }

}
