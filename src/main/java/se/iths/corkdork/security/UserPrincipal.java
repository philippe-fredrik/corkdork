package se.iths.corkdork.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import se.iths.corkdork.entity.RoleEntity;
import se.iths.corkdork.entity.UserEntity;

import java.util.ArrayList;
import java.util.Collection;

public class UserPrincipal implements UserDetails {

    private final UserEntity userEntity;

    public UserPrincipal(UserEntity userEntity) {
        super();
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        RoleEntity role = userEntity.getRole();
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole().toUpperCase()));

        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
