package se.iths.corkdork.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import se.iths.corkdork.dtos.Role;
import se.iths.corkdork.entity.RoleEntity;
import se.iths.corkdork.exception.BadRequestException;
import se.iths.corkdork.exception.EntityNotFoundException;
import se.iths.corkdork.service.RoleService;


@RestController
@Secured("ADMIN")
@RequestMapping("roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;

    }

    @PostMapping("")
    public ResponseEntity<Role> createRole(@RequestBody Role role, BindingResult errors) {
        if (errors.hasErrors())
            throw new BadRequestException("Role field is mandatory", errors);

        Role createdRole = roleService.createRole(role);

        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Role> findRoleById(@PathVariable Long id) {

        Role foundRole = roleService.findRoleById(id);

        return new ResponseEntity<>(foundRole, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Iterable<RoleEntity>> findAllRoles() {
        Iterable<RoleEntity> allRoles = roleService.findAllRoles();

        if (!allRoles.iterator().hasNext()) {
            throw new EntityNotFoundException("There are no roles registered in the database");
        }

        return new ResponseEntity<>(allRoles, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role role) {

        roleService.updateRole(id, role);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
