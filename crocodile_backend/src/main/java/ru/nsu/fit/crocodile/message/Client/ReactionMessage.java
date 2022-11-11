package ru.nsu.fit.crocodile.message.Client;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.fit.crocodile.model.ChatMessage;

@Data
public class ReactionMessage {
    private final Integer messageId;
    private final ChatMessage.Reaction reaction;
}
