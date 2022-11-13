package ru.nsu.fit.crocodile.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.nsu.fit.crocodile.model.Role;
import ru.nsu.fit.crocodile.model.Status;
import ru.nsu.fit.crocodile.model.UserData;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Getter
public class UserSecurity implements UserDetails {
    private final String username;

    private final String password;

    private final boolean isActive;

    private final List<Role> roles;

    public UserSecurity(String username, String password, boolean isActive, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.isActive = isActive;
        this.roles = roles;
    }

    public UserSecurity(UserData userData) {
        this.username = userData.getUsername();
        this.password = userData.getPassword();
        this.isActive = userData.getStatus() == Status.ACTIVE;
        this.roles = userData.getRoles();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new LinkedList<>();
        for(Role role: roles){
            authorities.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
        }
        return authorities;
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
