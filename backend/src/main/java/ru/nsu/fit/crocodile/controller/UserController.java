package ru.nsu.fit.crocodile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.crocodile.UserDataUtils;
import ru.nsu.fit.crocodile.dto.UserDto;
import ru.nsu.fit.crocodile.event.OnPasswordResetEvent;
import ru.nsu.fit.crocodile.exception.BadTokenException;
import ru.nsu.fit.crocodile.exception.ChangePasswordException;
import ru.nsu.fit.crocodile.exception.ElementAlreadyExistException;
import ru.nsu.fit.crocodile.exception.NoSuchElementException;
import ru.nsu.fit.crocodile.model.RoleEnum;
import ru.nsu.fit.crocodile.model.Status;
import ru.nsu.fit.crocodile.model.UserData;
import ru.nsu.fit.crocodile.request.*;
import ru.nsu.fit.crocodile.event.OnRegistrationCompleteEvent;
import ru.nsu.fit.crocodile.service.UserDataService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserDataService userDataService;

    private final ApplicationEventPublisher eventPublisher;


    @GetMapping("/get-info")
    public ResponseEntity<UserDto> getInfo() throws NoSuchElementException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(UserDataUtils.userdataToDto(userDataService.getUserByEmail(auth.getName())), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody RegistrationRequest request) throws ElementAlreadyExistException {
        UserData user = userDataService.createUser(request.getEmail(), request.getUsername(),
                request.getPassword(), Status.NOT_ENABLED, Collections.singletonList(RoleEnum.USER.name()));

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user));

        return new ResponseEntity<>(user.getId(), HttpStatus.OK);
    }

    @GetMapping("/registration-confirm")
    public ResponseEntity<HttpStatus> confirmRegistration(@RequestParam("token") String token) throws BadTokenException {
        userDataService.activateUser(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/password-reset")
    public ResponseEntity<HttpStatus> resetPassword(@RequestParam("email") String userEmail) throws NoSuchElementException {
        UserData user = userDataService.getUserByEmail(userEmail);

        eventPublisher.publishEvent(new OnPasswordResetEvent(user));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/password-reset-confirm")
    public ResponseEntity<HttpStatus> confirmPasswordReset(@RequestBody PasswordResetRequest request) throws BadTokenException, ChangePasswordException {
        userDataService.confirmPasswordReset(request.getToken(), request.getOldPassword(), request.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/change-name")
    public ResponseEntity<HttpStatus> changeName(@RequestBody ChangeNameRequest request) throws NoSuchElementException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userDataService.changeName(auth.getName(), request.getNewName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<HttpStatus> changePassword(@RequestBody ChangePasswordRequest request) throws NoSuchElementException, ChangePasswordException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userDataService.changePassword(auth.getName(), request.getOldPassword(), request.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/send-friend-request")
    public ResponseEntity<HttpStatus> sendFriendRequest(@RequestBody FriendRequest request) throws ElementAlreadyExistException, NoSuchElementException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userDataService.sendFriendRequest(auth.getName(), request.getToEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/accept-friend-request")
    public ResponseEntity<HttpStatus> acceptFriendRequest(@RequestBody AcceptFriendRequest request) throws NoSuchElementException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userDataService.acceptFriendRequest(auth.getName(), request.getWhoSentRequestEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get-friends")
    public ResponseEntity<List<UserDto>> getFriends() throws NoSuchElementException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(userDataService.getFriends(auth.getName()), HttpStatus.OK);
    }

    @GetMapping("/get-incoming-requests")
    public ResponseEntity<List<UserDto>> getIncomingFriendRequests() throws NoSuchElementException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(userDataService.getIncomingFriendRequests(auth.getName()), HttpStatus.OK);
    }

    @GetMapping("/get-outcoming-requests")
    public ResponseEntity<List<UserDto>> getOutcomingFriendRequests() throws NoSuchElementException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(userDataService.getOutcomingFriendRequests(auth.getName()), HttpStatus.OK);
    }

    @PostMapping("/delete-friend")
    public ResponseEntity<HttpStatus> deleteFriend(@RequestBody DeleteFriendRequest request) throws NoSuchElementException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userDataService.deleteFriend(auth.getName(), request.getFriendEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
