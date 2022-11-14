package ru.nsu.fit.crocodile.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.crocodile.Utils;
import ru.nsu.fit.crocodile.dto.UserDto;
import ru.nsu.fit.crocodile.model.UserData;
import ru.nsu.fit.crocodile.request.admin_request.*;
import ru.nsu.fit.crocodile.service.UserDataService;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
    private final UserDataService userDataService;

    public AdminUserController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @GetMapping("getByEmail/{email}")
    public ResponseEntity<Object> getUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(Utils.userdataToDto(userDataService.getUserByEmail(email)), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody AdminRegistrationRequest request) {
        try {
            Long userId = userDataService.saveUser(request.getEmail(), request.getUsername(),
                    request.getPassword(), request.getRoles());
            return new ResponseEntity<>(userId, HttpStatus.OK);
        } catch (InstanceAlreadyExistsException e) {
            return new ResponseEntity<>("User with this email already exist", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/changeName")
    public HttpStatus changeName(@RequestBody AdminChangeNameRequest request) {
        try {
            userDataService.changeName(request.getEmail(), request.getNewName());
            return HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.CONFLICT;
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Object> changePassword(@RequestBody AdminChangePasswordRequest request) {
        String oldPassword = request.getOldPassword();
        String newPassword = request.getNewPassword();
        try {
            userDataService.changePassword(request.getEmail(), oldPassword, newPassword);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Old password field doesn't match existing password", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/sendFriendRequest")
    public HttpStatus sendFriendRequest(@RequestBody AdminFriendRequest request) {
        try {
            userDataService.sendFriendRequest(request.getFromEmail(), request.getToEmail());
            return HttpStatus.OK;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return HttpStatus.CONFLICT;
        }
    }

    @PostMapping("/acceptFriendRequest")
    public HttpStatus acceptFriendRequest(@RequestBody AdminAcceptFriendRequest request) {
        try {
            userDataService.acceptFriendRequest(request.getAcceptingEmail(), request.getAcceptedEmail());
            return HttpStatus.OK;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return HttpStatus.BAD_REQUEST;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.CONFLICT;
        }

    }

    @GetMapping("/getFriends/{email}")
    public ResponseEntity<Object> getFriends(@PathVariable String email) {
        try {
            return new ResponseEntity<>(userDataService.getFriends(email), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getIncomingRequests/{email}")
    public ResponseEntity<Object> getIncomingFriendRequests(@PathVariable String email) {
        try {
            return new ResponseEntity<>(userDataService.getIncomingFriendRequests(email), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getOutcomingRequests/{email}")
    public ResponseEntity<Object> getOutcomingFriendRequests(@PathVariable String email) {
        try {
            List<UserDto> list = userDataService.getOutcomingFriendRequests(email);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/deleteFriend")
    public HttpStatus deleteFriend(@RequestBody AdminDeleteFriendRequest request) {
        try {
            userDataService.deleteFriend(request.getDeletingEmail(), request.getDeletedEmail());
            return HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.CONFLICT;
        }
    }
}
