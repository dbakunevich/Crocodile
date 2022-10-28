package ru.nsu.fit.crocodile.message.Server;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class RoundStartMessage {
    private final String start = "start";
}
