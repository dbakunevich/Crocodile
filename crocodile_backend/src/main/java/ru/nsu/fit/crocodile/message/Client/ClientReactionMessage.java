package ru.nsu.fit.crocodile.message.Client;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.fit.crocodile.message.Server.ServerMessage;
import ru.nsu.fit.crocodile.message.Server.ServerMessageType;
import ru.nsu.fit.crocodile.model.ChatMessage;

@Data
public class ClientReactionMessage {
    private final Integer messageId;
    private final ChatMessage.Reaction reaction;
}
