package se.iths.corkdork.security;


import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.iths.corkdork.entity.RoleEntity;
import se.iths.corkdork.entity.UserEntity;
import se.iths.corkdork.repository.RoleRepository;
import se.iths.corkdork.repository.UserRepository;

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


    @Override
    public void run(String... args) {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        UserEntity admin = new UserEntity(
                "Test",
                "Test",
                "Admin",
                passwordEncoder.encode("admin123"),
                "admin@corkdork.se");
        RoleEntity adminRole = new RoleEntity("ROLE_ADMIN");
        admin.setRole(adminRole);
        this.roleRepository.save(adminRole);
        this.userRepository.save(admin);
    }
}
