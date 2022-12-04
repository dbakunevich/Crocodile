package ru.nsu.fit.crocodile.message.Server;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class ServerMessage {
    private ServerMessageType type;

    public ServerMessage(ServerMessageType type) {
        this.type = type;
    }
}
