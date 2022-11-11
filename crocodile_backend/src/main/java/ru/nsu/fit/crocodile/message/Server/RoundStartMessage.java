package ru.nsu.fit.crocodile.message.Server;

import lombok.Data;

@Data
public class RoundStartMessage {
    private final String start = "start";
}
