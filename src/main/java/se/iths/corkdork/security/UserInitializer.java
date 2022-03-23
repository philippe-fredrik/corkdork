package se.iths.corkdork.security;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.iths.corkdork.entity.RoleEntity;
import se.iths.corkdork.entity.UserEntity;
import se.iths.corkdork.repository.RoleRepository;
import se.iths.corkdork.repository.UserRepository;

import java.util.List;

@Service
public class UserInitializer implements CommandLineRunner{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    public UserInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Bean
    public CommandLineRunner setUpRole(RoleRepository roleRepository) {
        return args -> {
            roleRepository.save(new RoleEntity("ADMIN"));
            roleRepository.save(new RoleEntity("USER"));
        };
    }

    @Override
    public void run(String... args) {
        userRepository.deleteAll();
        UserEntity user = new UserEntity(
                "UserTest",
                "Lastname",
                "User",
                passwordEncoder.encode("password123"),
                "user@corkdork.se");
        UserEntity admin = new UserEntity(
                "Admin",
                "Lastname",
                "Admin",
                passwordEncoder.encode("admin123"),
                "admin@corkdork.se");

        RoleEntity roleToAdd = roleRepository.findByRole("USER");
        user.setRole(roleToAdd);
        roleToAdd = roleRepository.findByRole("ADMIN");
        admin.setRole(roleToAdd);
        this.userRepository.saveAll(List.of(user, admin));
    }
}
