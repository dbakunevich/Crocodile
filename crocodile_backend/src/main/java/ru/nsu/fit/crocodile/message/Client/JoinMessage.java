package ru.nsu.fit.crocodile.message.Client;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class JoinMessage {
    private final Long id;
    private final String username;
}
