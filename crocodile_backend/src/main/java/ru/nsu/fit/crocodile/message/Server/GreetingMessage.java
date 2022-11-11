package ru.nsu.fit.crocodile.message.Server;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.nsu.fit.crocodile.model.Chat;
import ru.nsu.fit.crocodile.model.Player;

import java.util.List;
import java.util.Set;

@Data
public class GreetingMessage {
    private final int code;
    private final String message;
    private final List<Player> players;
    private final Chat chat;
    private final long master;
    private final long roundTime;
    private final long timeLeft;
}
