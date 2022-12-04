package ru.nsu.fit.crocodile.message.Server;

import lombok.Data;
import lombok.Getter;

@Getter
public class PlayerImageRequest extends ServerMessage{
    public PlayerImageRequest() {
        super(ServerMessageType.IMAGE_REQUEST);
    }
}
