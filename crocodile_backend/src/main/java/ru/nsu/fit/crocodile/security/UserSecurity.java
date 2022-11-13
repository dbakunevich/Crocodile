package ru.nsu.fit.crocodile.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.nsu.fit.crocodile.model.Status;
import ru.nsu.fit.crocodile.model.UserData;

import java.util.Collection;

@Getter
public class UserSecurity implements UserDetails {
    private final String username;

    private final String password;

    private final boolean isActive;

    public UserSecurity(Long id, String username, String password, boolean isActive) {
        this.username = username;
        this.password = password;
        this.isActive = isActive;
    }

    public UserSecurity(UserData userData) {
        this.username = userData.getUsername();
        this.password = userData.getPassword();
        this.isActive = userData.getStatus() == Status.ACTIVE;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
