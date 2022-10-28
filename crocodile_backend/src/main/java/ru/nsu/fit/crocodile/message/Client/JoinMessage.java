package ru.nsu.fit.crocodile.message.Client;

import lombok.Data;

@Data
public class JoinMessage {
    private Long id;
    private String username;
}
