package ru.nsu.fit.crocodile.message.Server;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class NewPlayerMessage extends ServerMessage{
    private final Long playerId;
}
