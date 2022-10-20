package ru.nsu.fit.crocodile.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nsu.fit.crocodile.model.UserData;
import ru.nsu.fit.crocodile.repository.UserRepository;

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
                .roles("USER").build();
    }
}
