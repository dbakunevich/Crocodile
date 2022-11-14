package ru.nsu.fit.crocodile.message.Client;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.fit.crocodile.message.Server.ServerMessage;
import ru.nsu.fit.crocodile.model.ChatMessage;

@Data
public class ReactionMessage extends ServerMessage {
    private final Integer messageId;
    private final ChatMessage.Reaction reaction;
}
