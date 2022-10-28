package ru.nsu.fit.crocodile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
public class ChatMessage {

    @ToString
    public enum Reaction{
        NO_REACTION, LIKE, DISLIKE
    }

    private String message;
    private Long userId;
    private Integer MessageId;
    private Reaction reaction;
}
