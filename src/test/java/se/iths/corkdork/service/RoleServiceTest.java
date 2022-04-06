package se.iths.corkdork.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import se.iths.corkdork.entity.RoleEntity;
import se.iths.corkdork.repository.RoleRepository;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    void createShouldReturnCreatedRole() {
        RoleEntity role = new RoleEntity();
        role.setRole("ROLE_TEST");
        role.setId(1L);

        when(roleRepository.save(role)).thenReturn(role);

        RoleEntity roleCreatedWithService = roleService.createRole(role);

        assertThat(roleCreatedWithService).isEqualTo(role);
    }

    @Test
    void findByIdShouldReturnOptionalRoleWithIdOne() {
        RoleEntity role = new RoleEntity();
        role.setRole("ROLE_TEST");
        role.setId(1L);

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        Optional<RoleEntity> expectedResult = roleService.findRoleById(1L);

        assertThat(expectedResult).isEqualTo(Optional.of(role));

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


}
