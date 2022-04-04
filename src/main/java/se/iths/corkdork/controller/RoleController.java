package se.iths.corkdork.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import se.iths.corkdork.entity.RoleEntity;
import se.iths.corkdork.exception.BadRequestException;
import se.iths.corkdork.exception.EntityNotFoundException;
import se.iths.corkdork.service.RoleService;

import java.util.Optional;

@RestController
@Secured("ADMIN") //Only admin permitted to use requests on roles.
@RequestMapping("roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;

    }

    @PostMapping()
    public ResponseEntity<RoleEntity> createRole(@RequestBody RoleEntity roleEntity, BindingResult errors) {
        if (errors.hasErrors())
            throw new BadRequestException("Role field is mandatory", errors);


        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<RoleEntity> findRoleById(@PathVariable Long id) {
        Optional<RoleEntity> foundRole = roleService.findRoleById(id);

        if (foundRole.isEmpty()) {
            throw new EntityNotFoundException(responseMessage(id));
        }
        return new ResponseEntity<>(HttpStatus.FOUND);
    }

    @GetMapping()
    public ResponseEntity<Iterable<RoleEntity>> findAllRoles() {
        Iterable<RoleEntity> allRoles = roleService.findAllRoles();

        if (!allRoles.iterator().hasNext()) {
            throw new EntityNotFoundException("There are no roles registered in the database");
        }

        return new ResponseEntity<>(allRoles, HttpStatus.FOUND);
    }

    @PutMapping()
    public ResponseEntity<RoleEntity> updateRole(@RequestBody RoleEntity roleEntity) {
        if (roleService.findRoleById(roleEntity.getId()).isEmpty()) {
            throw new EntityNotFoundException(responseMessage(roleEntity.getId()));
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        if (roleService.findRoleById(id).isEmpty()) {
            throw new EntityNotFoundException(responseMessage(id));
        }

        roleService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private String responseMessage(Long id) {
        return "No role with ID: " +id+ " was found.";
    }
}
