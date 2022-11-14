package ru.nsu.fit.crocodile.message.Server;

import lombok.Data;

@Data
public class PlayerImageRequest extends ServerMessage{
    private final String request = "request";
}
