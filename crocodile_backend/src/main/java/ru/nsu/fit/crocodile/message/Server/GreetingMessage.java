package ru.nsu.fit.crocodile.message.Server;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.nsu.fit.crocodile.model.Chat;
import ru.nsu.fit.crocodile.model.Player;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class GreetingMessage {
    private int code;
    private String message;
    private List<Player> players;
    private Chat chat;
    private long master;
}
