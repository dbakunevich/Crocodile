package ru.nsu.fit.crocodile.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AcceptFriendRequest {
    private String acceptingEmail;
    private String acceptedEmail;
}
