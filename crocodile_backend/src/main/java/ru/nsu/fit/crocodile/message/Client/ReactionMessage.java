package ru.nsu.fit.crocodile.message.Client;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.fit.crocodile.model.ChatMessage;

@Data
@NoArgsConstructor
public class ReactionMessage {
    Integer messageId;
    ChatMessage.Reaction reaction;
}
