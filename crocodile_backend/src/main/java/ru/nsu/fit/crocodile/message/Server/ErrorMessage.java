package ru.nsu.fit.crocodile.message.Server;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ErrorMessage extends ServerMessage {
    private final String error;
}
