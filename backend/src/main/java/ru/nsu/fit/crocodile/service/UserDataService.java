/*package ru.nsu.fit.crocodile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nsu.fit.crocodile.UserDataUtils;
import ru.nsu.fit.crocodile.dto.UserDto;
import ru.nsu.fit.crocodile.exception.ChangePasswordException;
import ru.nsu.fit.crocodile.exception.ElementAlreadyExistException;
import ru.nsu.fit.crocodile.exception.NoSuchElementException;
import ru.nsu.fit.crocodile.model.Status;
import ru.nsu.fit.crocodile.model.UserData;
//import ru.nsu.fit.crocodile.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UserDataService {

    //private final UserRepository userRepository;

    private final RoleService roleService;

    private final BCryptPasswordEncoder encoder;

    private final Logger logger = Logger.getLogger(UserDataService.class.getName());

    public UserData getUserByEmail(String email) throws NoSuchElementException {
        return userRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    @Transactional
    public Long saveUser(String email, String name, String password, Status status, List<String> roles) throws ElementAlreadyExistException {
        if (userRepository.existsByEmail(email)) {
            logger.info("User with such email already exists");
            throw new ElementAlreadyExistException("User with such email already exists");
        }
        UserData user = new UserData();
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setUsername(name);
        user.setStatus(status);
        user.setUserRoles(roleService.mapStringToRoles(roles));
        userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public void changeName(String email, String newName) throws NoSuchElementException {
        UserData user = getUserByEmail(email);
        user.setUsername(newName);
        userRepository.save(user);
    }

    @Transactional
    public void changePassword(String email, String oldPassword, String newPassword) throws ChangePasswordException, NoSuchElementException {
        UserData user = getUserByEmail(email);
        if (encoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(encoder.encode(newPassword));
        } else {
            logger.info("Old password doesn't match existing");
            throw new ChangePasswordException("Old password doesn't match existing");
        }
        userRepository.save(user);
    }

    @Transactional
    public void sendFriendRequest(String srcEmail, String rcvEmail) throws ElementAlreadyExistException, NoSuchElementException {
        UserData srcUser = getUserByEmail(srcEmail);
        UserData rcvUser = getUserByEmail(rcvEmail);
        List<UserData> incReq = userRepository.findAllByOutcomingFriendRequestsContains(srcUser);
        List<UserData> outcReq = userRepository.findAllByIncomingFriendRequestsContains(srcUser);
        if (incReq.contains(rcvUser)) return;
        if (outcReq.contains(rcvUser)) {
            logger.info("Already have incoming request from this user");
            throw new ElementAlreadyExistException("Already have incoming request from this user");
        }
        srcUser.getOutcomingFriendRequests().add(rcvUser);
        userRepository.save(srcUser);
    }

    @Transactional
    public void acceptFriendRequest(String whoReceivedRequestEmail, String whoSentRequestEmail) throws NoSuchElementException {
        UserData whoReceivedRequestUser = getUserByEmail(whoReceivedRequestEmail);
        UserData whoSentRequestUser = getUserByEmail(whoSentRequestEmail);
        List<UserData> incReq = userRepository.findAllByOutcomingFriendRequestsContains(whoReceivedRequestUser);
        if (!incReq.contains(whoSentRequestUser)) {
            logger.info("No request from this user");
            throw new NoSuchElementException("No request from this user");
        }
        whoReceivedRequestUser.getFriends().add(whoSentRequestUser);
        whoSentRequestUser.getOutcomingFriendRequests().remove(whoReceivedRequestUser);
        whoSentRequestUser.getFriends().add(whoReceivedRequestUser);

        userRepository.save(whoReceivedRequestUser);
        userRepository.save(whoSentRequestUser);
    }

    @Transactional
    public void deleteFriend(String userEmail, String friendEmail) throws NoSuchElementException {
        UserData user = getUserByEmail(userEmail);
        UserData friend = getUserByEmail(friendEmail);
        user.getFriends().remove(friend);
        friend.getFriends().remove(user);
        friend.getOutcomingFriendRequests().add(user);

        userRepository.save(user);
        userRepository.save(friend);
    }

    public List<UserDto> getFriends(String email) throws NoSuchElementException {
        UserData user = getUserByEmail(email);
        return UserDataUtils.userdataToDto(userRepository.findAllByFriendsContains(user));
    }

    public List<UserDto> getIncomingFriendRequests(String email) throws NoSuchElementException {
        UserData user = getUserByEmail(email);
        return UserDataUtils.userdataToDto(userRepository.findAllByOutcomingFriendRequestsContains(user));
    }

    public List<UserDto> getOutcomingFriendRequests(String email) throws NoSuchElementException {
        UserData user = getUserByEmail(email);
        return UserDataUtils.userdataToDto(userRepository.findAllByIncomingFriendRequestsContains(user));
    }
}
*/