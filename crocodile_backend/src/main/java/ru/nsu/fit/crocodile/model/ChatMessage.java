package ru.nsu.fit.crocodile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import ru.nsu.fit.crocodile.message.Server.ServerMessage;

@Data
@AllArgsConstructor
public class ChatMessage extends ServerMessage {

    @ToString
    public enum Reaction{
        NO_REACTION, LIKE, DISLIKE, RIGHT
    }

    private String message;
    private Long userId;
    private Integer MessageId;
    private Reaction reaction;
}
