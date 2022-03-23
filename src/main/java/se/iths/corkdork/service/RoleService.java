package se.iths.corkdork.service;

import org.springframework.stereotype.Service;
import se.iths.corkdork.entity.RoleEntity;
import se.iths.corkdork.repository.RoleRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleEntity createRole(RoleEntity role) {
        return roleRepository.save(role);
    }

    public Optional<RoleEntity> findRoleById(Long id) {
        return roleRepository.findById(id);
    }

    public Iterable<RoleEntity> findAllRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    public void updateRole(Long id, String role) {
        RoleEntity foundRole = roleRepository.findById(id).orElseThrow();
        roleRepository.save(foundRole);
    }
}
