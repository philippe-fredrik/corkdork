package se.iths.corkdork.security;


import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.iths.corkdork.entity.RoleEntity;
import se.iths.corkdork.entity.UserEntity;
import se.iths.corkdork.repository.RoleRepository;
import se.iths.corkdork.repository.UserRepository;

import java.util.List;

@Service
public class UserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        userRepository.deleteAll();
        roleRepository.deleteAll();

        RoleEntity adminRole = new RoleEntity("ADMIN");
        RoleEntity userRole = new RoleEntity("USER");

        UserEntity user = new UserEntity(
                "Test",
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

        this.roleRepository.saveAll(List.of(adminRole, userRole));
        this.userRepository.saveAll(List.of(admin, user));
    }
}
