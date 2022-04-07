package se.iths.corkdork;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import se.iths.corkdork.entity.*;
import se.iths.corkdork.repository.CountryRepository;
import se.iths.corkdork.repository.UserRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void entityRegistrationWorksThroughAllLayers() throws Exception {

        UserEntity entity = new UserEntity("frege",
                "eriksson",
                "frege",
                "123",
                "test@test.se",
                new RoleEntity());

        mockMvc.perform(post("/users/signup", 42L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(entity)))
                .andExpect(status().isCreated());

        UserEntity userEntity = userRepository.findByUsername("frege");
        assertThat(userEntity.getEmail()).isEqualTo("test@test.se");
    }

    @WithMockUser("spring")
    @Test
    void countryEntityRegistrationWorksThroughAllLayers() throws Exception {

        GrapeEntity grape = new GrapeEntity();
        WineEntity wine = new WineEntity();
        wine.setGrape(grape);
        wine.setName("wineTest");
        grape.setName("grapeTest");
        grape.setColor("colorTest");

        CountryEntity entity = new CountryEntity();

        entity.setId(1L);
        entity.setName("countryTest");
        entity.addGrape(grape);
        entity.addWine(wine);

        mockMvc.perform(post("/countries", 42L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(entity)))
                .andExpect(status().isCreated());

        CountryEntity CountryEntity = countryRepository.findByName("Frankrike");
        assertThat(CountryEntity.getName()).isEqualTo("Frankrike");

    }

}