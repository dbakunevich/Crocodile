package ru.nsu.fit.crocodile.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminRegistrationRequest {
    private String email;
    private String username;
    private String password;
    private String[] roles;
}
