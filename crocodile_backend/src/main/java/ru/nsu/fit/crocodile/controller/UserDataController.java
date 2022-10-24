package ru.nsu.fit.crocodile.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.crocodile.model.UserData;
import ru.nsu.fit.crocodile.request.*;
import ru.nsu.fit.crocodile.service.UserDataService;

import javax.management.InstanceAlreadyExistsException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class UserDataController {
    private final UserDataService userDataService;

    public UserDataController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @GetMapping("getByEmail/{email}")
    public UserData getUserByEmail(@PathVariable String email) {
        return userDataService.getUserByEmail(email);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegistrationRequest request) {
        try {
            Long userId = userDataService.saveUser(request.getEmail(), request.getUsername(), request.getPassword());
            return new ResponseEntity<>(userId, HttpStatus.OK);
        } catch (InstanceAlreadyExistsException e) {
            return new ResponseEntity<>("User with this email already exist", HttpStatus.CONFLICT);
        }
    }

    //TODO я в запросе почту отправляю, и в контексте проверок авторизации работает нормально, но как-то вроде id обычно используют
    @PostMapping("/changeName")
    public HttpStatus changeName(@RequestBody ChangeNameRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = request.getEmail();
        String newName = request.getNewName();
        //TODO: когда будут роли добавить админа
        if (email.equals(auth.getName())) {
            try {
                userDataService.changeName(email, newName);
                return HttpStatus.OK;
            } catch (Exception e) {
                e.printStackTrace();
                return HttpStatus.CONFLICT;
            }
        }
        return HttpStatus.FORBIDDEN;
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = request.getEmail();
        String oldPassword = request.getOldPassword();
        String newPassword = request.getNewPassword();
        if (email.equals(auth.getName())) {
            try {
                userDataService.changePassword(email, oldPassword, newPassword);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (NoSuchElementException e) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>("Old password field doesn't match existing password", HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/logout")
    public String logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:/";
    }

    @PostMapping("/sendFriendRequest")
    public HttpStatus sendFriendRequest(@RequestBody FriendRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String src = request.getFromEmail();
        String rcv = request.getToEmail();
        if (src.equals(auth.getName())) {
            try {
                userDataService.sendFriendRequest(src, rcv);
                return HttpStatus.OK;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                return HttpStatus.CONFLICT;
            }
        }
        return HttpStatus.FORBIDDEN;
    }

    @PostMapping("/acceptFriendRequest")
    public HttpStatus acceptFriendRequest(@RequestBody AcceptFriendRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String accepting = request.getAcceptingEmail();
        String accepted = request.getAcceptedEmail();
        if (accepting.equals(auth.getName())) {
            try {
                userDataService.acceptFriendRequest(accepting, accepted);
                return HttpStatus.OK;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                return HttpStatus.BAD_REQUEST;
            } catch (Exception e) {
                e.printStackTrace();
                return HttpStatus.CONFLICT;
            }
        }
        return HttpStatus.FORBIDDEN;
    }

    @GetMapping("/getFriends/{email}")
    public ResponseEntity<Object> getFriends(@PathVariable String email) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (email.equals(auth.getName())) {
            try {
                return new ResponseEntity<>(userDataService.getFriends(email), HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/getIncomingRequests/{email}")
    public ResponseEntity<Object> getIncomingFriendRequests(@PathVariable String email) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (email.equals(auth.getName())) {
            try {
                return new ResponseEntity<>(userDataService.getIncomingFriendRequests(email), HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/getOutcomingRequests/{email}")
    public ResponseEntity<Object> getOutcomingFriendRequests(@PathVariable String email) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (email.equals(auth.getName())) {
            try {
                return new ResponseEntity<>(userDataService.getOutcomingFriendRequests(email), HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/deleteFriend")
    public HttpStatus deleteFriend(@RequestBody DeleteFriendRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String deleting = request.getDeletingEmail();
        String deleted = request.getDeletedEmail();
        if (deleting.equals(auth.getName())) {
            try {
                userDataService.deleteFriend(deleting, deleted);
                return HttpStatus.OK;
            } catch (Exception e) {
                e.printStackTrace();
                return HttpStatus.CONFLICT;
            }
        }
        return HttpStatus.FORBIDDEN;
    }
}
