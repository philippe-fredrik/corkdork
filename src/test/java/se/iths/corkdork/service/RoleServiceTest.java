package se.iths.corkdork.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.modelmapper.ModelMapper;
import se.iths.corkdork.dtos.Role;
import se.iths.corkdork.entity.RoleEntity;
import se.iths.corkdork.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @Mock
    private ModelMapper modelMapper;


    @Test
    void createShouldReturnCreatedRole() {
        Role role = new Role().setRoleName("ROLE_TEST").setId(1L);

        when(roleService.createRole(role)).thenReturn(role);

        Role createdRole = roleService.createRole(role);

        assertThat(createdRole).isEqualTo(role);

    }

    @Test
    void findByIdShouldReturnOptionalRoleWithIdOne() {
        //RoleEntity roleEntity = modelMapper.map(role, RoleEntity.class);
        RoleEntity roleEntity = new RoleEntity();
        Role role = new Role().setRoleName("ROLE_TEST").setId(1L);
        role.setRoleName("ROLE_TEST").setId(1L);

        when(modelMapper.map(any(), any())).thenReturn(role);
        when(roleRepository.findById(role.getId())).thenReturn(Optional.of(roleEntity));

        Role expectedResult = roleService.findRoleById(role.getId());

        assertThat(expectedResult).isEqualTo(role);

    }

    @Test
    void findAllShouldReturnAllRoles() {
        List<RoleEntity> roles = List.of(
                new RoleEntity().setRole("ROLE_TEST").setId(1L),
                new RoleEntity().setRole("ROLE_USER").setId(2L),
                new RoleEntity().setRole("ROLE_ADMIN").setId(3L)
        );

        when(roleRepository.findAll()).thenReturn(roles);

        Iterable<RoleEntity> foundRoles = roleService.findAllRoles();

        assertThat(foundRoles).isEqualTo(roles);

    }

    @Test
    void deleteRoleShouldDeleteRoleWithIdOne() {
        RoleEntity roleEntity = new RoleEntity();

        final Long id = 1L;
        roleEntity.setRole("ROLE_TEST").setId(id);

        when(roleRepository.findById(id)).thenReturn(Optional.of(roleEntity));

        roleService.deleteRole(roleEntity.getId());

        verify(roleRepository, times(1)).deleteById(id);
    }

    @Test
    void updateRoleShouldReturnRoleWithNewRoleUser() {
        Role role = new Role().setRoleName("TEST_ROLE").setId(1L);
        roleService.updateRole(role.getId(), role);

        RoleEntity roleEntity = modelMapper.map(role, RoleEntity.class);

        verify(roleRepository, times(1)).save(roleEntity);

    }


}
