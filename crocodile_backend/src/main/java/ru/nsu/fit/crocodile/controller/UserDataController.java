package ru.nsu.fit.crocodile.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.crocodile.Utils;
import ru.nsu.fit.crocodile.dto.UserDto;
import ru.nsu.fit.crocodile.request.ChangePasswordRequest;
import ru.nsu.fit.crocodile.request.RegistrationRequest;
import ru.nsu.fit.crocodile.service.UserDataService;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class UserDataController {
    private final UserDataService userDataService;

    public UserDataController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @GetMapping("getInfo")
    public ResponseEntity<Object> getInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(Utils.userdataToDto(userDataService.getUserByEmail(auth.getName())), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegistrationRequest request) {
        try {
            Long userId = userDataService.saveUser(request.getEmail(), request.getUsername(),
                    request.getPassword(), "user");
            return new ResponseEntity<>(userId, HttpStatus.OK);
        } catch (InstanceAlreadyExistsException e) {
            return new ResponseEntity<>("User with this email already exist", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/changeName/{newName}")
    public HttpStatus changeName(@PathVariable String newName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            userDataService.changeName(auth.getName(), newName);
            return HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.CONFLICT;
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String oldPassword = request.getOldPassword();
        String newPassword = request.getNewPassword();
        try {
            userDataService.changePassword(auth.getName(), oldPassword, newPassword);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Old password field doesn't match existing password", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/sendFriendRequest/{rcvEmail}")
    public HttpStatus sendFriendRequest(@PathVariable String rcvEmail) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            userDataService.sendFriendRequest(auth.getName(), rcvEmail);
            return HttpStatus.OK;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return HttpStatus.CONFLICT;
        }
    }

    @PostMapping("/acceptFriendRequest/{acceptedEmail}")
    public HttpStatus acceptFriendRequest(@PathVariable String acceptedEmail) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            userDataService.acceptFriendRequest(auth.getName(), acceptedEmail);
            return HttpStatus.OK;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return HttpStatus.BAD_REQUEST;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.CONFLICT;
        }

    }

    @GetMapping("/getFriends")
    public ResponseEntity<Object> getFriends() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            return new ResponseEntity<>(userDataService.getFriends(auth.getName()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getIncomingRequests")
    public ResponseEntity<Object> getIncomingFriendRequests() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            return new ResponseEntity<>(userDataService.getIncomingFriendRequests(auth.getName()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getOutcomingRequests")
    public ResponseEntity<Object> getOutcomingFriendRequests() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            List<UserDto> list = userDataService.getOutcomingFriendRequests(auth.getName());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/deleteFriend/{deletedEmail}")
    public HttpStatus deleteFriend(@PathVariable String deletedEmail) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            userDataService.deleteFriend(auth.getName(), deletedEmail);
            return HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.CONFLICT;
        }
    }
}
