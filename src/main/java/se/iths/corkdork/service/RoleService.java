package se.iths.corkdork.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import se.iths.corkdork.dtos.Role;
import se.iths.corkdork.entity.RoleEntity;
import se.iths.corkdork.repository.RoleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleService(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    public Role createRole(Role role) {
        RoleEntity roleEntity = modelMapper.map(role, RoleEntity.class);

        roleRepository.save(roleEntity);

        return modelMapper.map(roleEntity, Role.class);
    }

    public Role findRoleById(Long id) {
        Optional<RoleEntity> foundRole = roleRepository.findById(id);
        if(foundRole.isEmpty())
            throw new se.iths.corkdork.exception.EntityNotFoundException("Role with ID: " + id + " was not found");

        return modelMapper.map(foundRole, Role.class);
    }

    public List<Role> findAllRoles() {
        Iterable<RoleEntity> allRoles = roleRepository.findAll();
        if (!allRoles.iterator().hasNext()) {
            throw new se.iths.corkdork.exception.EntityNotFoundException("Failed to find any roles");
        }
        List<Role> roles = new ArrayList<>();
        allRoles.forEach(role -> roles.add(modelMapper.map(role, Role.class)));
        return roles;
    }

    public void updateRole(Long id, Role role) {
        role.setId(id);
        RoleEntity roleEntity = modelMapper.map(role, RoleEntity.class);
        roleRepository.save(roleEntity);
    }

    public void deleteRole(Long id) {
        RoleEntity foundRole = roleRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        roleRepository.deleteById(foundRole.getId());
    }
}
