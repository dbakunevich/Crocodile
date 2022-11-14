package ru.nsu.fit.crocodile.request.admin_request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdminAcceptFriendRequest {
    private String acceptingEmail;
    private String acceptedEmail;
}
