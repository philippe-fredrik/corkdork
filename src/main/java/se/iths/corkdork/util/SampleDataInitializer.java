package se.iths.corkdork.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import se.iths.corkdork.entity.*;
import se.iths.corkdork.repository.*;

import javax.transaction.Transactional;
import java.util.List;
@Component
public class SampleDataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final CountryRepository countryRepository;
    private final GrapeRepository grapeRepository;
    private final WineRepository wineRepository;

    @Autowired
    public SampleDataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                 RoleRepository roleRepository, CountryRepository countryRepository,
                                 GrapeRepository grapeRepository, WineRepository wineRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.countryRepository = countryRepository;
        this.grapeRepository = grapeRepository;
        this.wineRepository = wineRepository;
    }

    @Transactional
    public void run(ApplicationArguments args) {

        RoleEntity adminRole = new RoleEntity("ADMIN");
        RoleEntity userRole = new RoleEntity("USER");

        UserEntity user = new UserEntity(
                "UserTest",
                "Test",
                "User",
                passwordEncoder.encode("user123"),
                "user@corkdork.se",
                userRole);

        UserEntity admin = new UserEntity(
                "Test",
                "Test",
                "Admin",
                passwordEncoder.encode("admin123"),
                "admin@corkdork.se",
                adminRole);

        CountryEntity country1 = new CountryEntity().setName("France");
        CountryEntity country2 = new CountryEntity().setName("Italy");
        CountryEntity country3 = new CountryEntity().setName("Spain");

        GrapeEntity grape1 = new GrapeEntity().setName("Merlot").setColor("Red");
        GrapeEntity grape2 = new GrapeEntity().setName("Barbera").setColor("Red");
        GrapeEntity grape3 = new GrapeEntity().setName("Graciano").setColor("Red");

        WineEntity wine1 = new WineEntity().setName("Le Ruse Merlot 2019");
        WineEntity wine2 = new WineEntity().setName("Barbera d'Asti Superiore Nizza DOCG 1999");
        WineEntity wine3 = new WineEntity().setName("Muga Torre 2018");

        wine1.setGrape(grape1);
        wine1.setCountry(country1);

        wine2.setGrape(grape2);
        wine2.setCountry(country2);

        wine3.setGrape(grape3);
        wine3.setCountry(country3);

        this.countryRepository.saveAll(List.of(country1,country2,country3));
        this.grapeRepository.saveAll(List.of(grape1,grape2,grape3));
        this.wineRepository.saveAll(List.of(wine1,wine2,wine3));
        this.roleRepository.saveAll(List.of(adminRole, userRole));
        this.userRepository.saveAll(List.of(admin, user));
    }
}
