package se.iths.corkdork;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import se.iths.corkdork.controller.RoleController;
import se.iths.corkdork.dtos.Role;
import se.iths.corkdork.entity.*;
import se.iths.corkdork.repository.RoleRepository;
import se.iths.corkdork.repository.UserRepository;
import se.iths.corkdork.security.SecurityConfig;
import se.iths.corkdork.service.RoleService;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({RoleService.class, SecurityConfig.class})
@WebMvcTest(RoleController.class)
@AutoConfigureMockMvc
class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private ModelMapper modelMapper;


    @WithMockUser(roles = {"ADMIN"})
    @Test
    void createRoleShouldReturnResponseCode201() throws Exception{
        RoleEntity roleEntity = new RoleEntity().setRole("ADMIN").setId(1L);

        Role role = new Role().setRoleName("ADMIN").setId(1L);

        when(modelMapper.map(any(Role.class), RoleEntity.class)).thenReturn(roleEntity);
        when(roleRepository.save(roleEntity)).thenReturn(roleEntity);
        when(modelMapper.map(any(RoleEntity.class), Role.class)).thenReturn(role);

        mockMvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(role)))
                .andExpect(status().isCreated());

    }

}