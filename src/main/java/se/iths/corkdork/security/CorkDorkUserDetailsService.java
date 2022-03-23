package se.iths.corkdork.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.iths.corkdork.entity.UserEntity;
import se.iths.corkdork.repository.UserRepository;

@Service
public class CorkDorkUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CorkDorkUserDetailsService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("Can't find user with username: " + username);
        }
        return new UserPrincipal(userEntity);
    }
}
