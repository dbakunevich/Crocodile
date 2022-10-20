package ru.nsu.fit.crocodile.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.crocodile.model.UserData;
import ru.nsu.fit.crocodile.request.ChangeNameRequest;
import ru.nsu.fit.crocodile.request.ChangePasswordRequest;
import ru.nsu.fit.crocodile.request.RegistrationRequest;
import ru.nsu.fit.crocodile.service.UserDataService;

import javax.management.InstanceAlreadyExistsException;
import javax.servlet.http.HttpServletRequest;
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

}
