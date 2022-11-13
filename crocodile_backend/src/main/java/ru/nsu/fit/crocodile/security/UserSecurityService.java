package ru.nsu.fit.crocodile.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nsu.fit.crocodile.model.Role;
import ru.nsu.fit.crocodile.model.UserData;
import ru.nsu.fit.crocodile.repository.UserRepository;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service("user_security_service")
public class UserSecurityService implements UserDetailsService {

    UserRepository userRepository;

    @Autowired
    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserData> userOpt = userRepository.findByEmail(email);
        if (!userOpt.isPresent()) {
            throw new UsernameNotFoundException(email);
        }
        UserData user = userOpt.get();
        return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(getAuthorities(user)).build();
    }

    public Collection<? extends GrantedAuthority> getAuthorities(UserData user) {
        List<Role> roles = user.getRoles();
        List<GrantedAuthority> authorities = new LinkedList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
        }
        return authorities;
    }
}
