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
public class RoleServiceTest {

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
    void findByIdShouldReturnOptionalRoleWithId1() {
        RoleEntity role = new RoleEntity();
        role.setRole("ROLE_TEST");
        role.setId(1L);

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        Optional<RoleEntity> expectedResult = roleService.findRoleById(1L);

        assertThat(expectedResult).isEqualTo(Optional.of(role));

    }
}
