package ru.nsu.fit.crocodile.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.security.Principal;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class Player {
    private Principal principal;
    private Long id;
    private String username;
    private AtomicInteger score;

    public Player(Long id, String username, Principal principal){
        this.id = id;
        this.username = username;
        this.principal = principal;
        this.score = new AtomicInteger(0);
    }
}
