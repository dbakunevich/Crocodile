package ru.nsu.fit.crocodile.message.Server;

import lombok.Data;
import lombok.Getter;

@Getter
public class RoundStartMessage extends ServerMessage{
    public RoundStartMessage() {
        super(ServerMessageType.ROUND_START);
    }
}
