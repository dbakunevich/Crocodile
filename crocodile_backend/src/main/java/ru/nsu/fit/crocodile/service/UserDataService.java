package ru.nsu.fit.crocodile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.fit.crocodile.Utils;
import ru.nsu.fit.crocodile.dto.UserDto;
import ru.nsu.fit.crocodile.model.Status;
import ru.nsu.fit.crocodile.model.UserData;
import ru.nsu.fit.crocodile.repository.UserRepository;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserDataService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    public UserData getUserByEmail(String email) {
        Optional<UserData> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) return userOpt.get();
        throw new NoSuchElementException();
    }

    public Long saveUser(String email, String name, String password, String... roles) throws InstanceAlreadyExistsException {
        Optional<UserData> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) throw new InstanceAlreadyExistsException();
        UserData user = new UserData();
        user.setEmail(email);
        user.setPassword(Utils.passwordEncoder().encode(password));
        user.setUsername(name);
        user.setStatus(Status.ACTIVE);
        user.setRoles(roleService.mapStringToRoles(roles));
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

    public void sendFriendRequest(String srcEmail, String rcvEmail) {
        UserData srcUser = getUserByEmail(srcEmail);
        UserData rcvUser = getUserByEmail(rcvEmail);
        if (userRepository.findByIncomingFriendRequests(srcUser) != null) return;
        if (userRepository.findByOutcomingFriendRequests(rcvUser) != null)
            throw new IllegalArgumentException("you already have incoming request from this user");
        srcUser.getOutcomingFriendRequests().add(rcvUser);
        userRepository.save(srcUser);
    }

    public void acceptFriendRequest(String acceptingEmail, String acceptedEmail) {
        UserData acceptingUser = getUserByEmail(acceptingEmail);
        UserData acceptedUser = getUserByEmail(acceptedEmail);
        if (userRepository.findByIncomingFriendRequests(acceptingUser) == null)
            throw new IllegalArgumentException("nothing to accept");
        acceptingUser.getFriends().add(acceptedUser);
        acceptedUser.getOutcomingFriendRequests().remove(acceptingUser);
        acceptedUser.getFriends().add(acceptingUser);

        userRepository.save(acceptingUser);
        userRepository.save(acceptedUser);
    }

    public void deleteFriend(String deletingEmail, String deletedEmail) {
        UserData deletingUser = getUserByEmail(deletingEmail);
        UserData deletedUser = getUserByEmail(deletedEmail);
        deletingUser.getFriends().remove(deletedUser);
        deletedUser.getFriends().remove(deletingUser);
        deletedUser.getOutcomingFriendRequests().add(deletingUser);

        userRepository.save(deletingUser);
        userRepository.save(deletedUser);
    }

    public List<UserDto> getFriends(String email) {
        UserData user = getUserByEmail(email);
        return Utils.userdataToDto(userRepository.findAllByFriendsContains(user));
    }

    public List<UserDto> getIncomingFriendRequests(String email) {
        UserData user = getUserByEmail(email);
        return Utils.userdataToDto(userRepository.findAllByOutcomingFriendRequestsContains(user));
    }

    public List<UserDto> getOutcomingFriendRequests(String email) {
        UserData user = getUserByEmail(email);
        return Utils.userdataToDto(userRepository.findAllByIncomingFriendRequestsContains(user));
    }
}
