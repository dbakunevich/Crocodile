package ru.nsu.fit.crocodile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nsu.fit.crocodile.Utils;
import ru.nsu.fit.crocodile.model.Status;
import ru.nsu.fit.crocodile.model.UserData;
import ru.nsu.fit.crocodile.repository.UserRepository;

import javax.management.InstanceAlreadyExistsException;
import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserDataService {

    @Autowired
    UserRepository userRepository;

    public UserData getUserByEmail(String email) {
        Optional<UserData> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) return userOpt.get();
        throw new NoSuchElementException();
    }

    public Long saveUser(String email, String name, String password) throws InstanceAlreadyExistsException {
        Optional<UserData> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) throw new InstanceAlreadyExistsException();
        UserData user = new UserData();
        user.setEmail(email);
        user.setPassword(Utils.passwordEncoder().encode(password));
        user.setUsername(name);
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
        return user.getId();
    }

    public void changeName(String email, String newName) {
        UserData user = getUserByEmail(email);
        user.setUsername(newName);
        userRepository.save(user);
    }

    public void changePassword(String email, String oldPassword, String newPassword) {
        UserData user = getUserByEmail(email);
        if (Utils.passwordEncoder().matches(oldPassword, user.getPassword())) {
            user.setPassword(Utils.passwordEncoder().encode(newPassword));
        } else {
            throw new IllegalArgumentException();
        }
        userRepository.save(user);
    }
}
