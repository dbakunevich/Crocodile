package ru.nsu.fit.crocodile.model;

import lombok.*;
import ru.nsu.fit.crocodile.message.Server.ServerMessage;
import ru.nsu.fit.crocodile.message.Server.ServerMessageType;

@Getter
@Setter
public class ChatMessage extends ServerMessage {

    @ToString
    public enum Reaction{
        NO_REACTION, LIKE, DISLIKE, RIGHT
    }

    private String message;
    private Long userId;
    private Integer MessageId;
    private Reaction reaction;

    public ChatMessage(String message, Long userId, Integer messageId, Reaction reaction) {
        this();
        this.message = message;
        this.userId = userId;
        MessageId = messageId;
        this.reaction = reaction;
    }

    public ChatMessage() {
        super(ServerMessageType.CHAT_MESSAGE);
    }
}
