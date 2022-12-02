package ru.nsu.fit.crocodile.message.Server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import ru.nsu.fit.crocodile.model.Chat;
import ru.nsu.fit.crocodile.model.Player;

import java.util.List;
import java.util.Set;

@Getter
public class GreetingMessage extends ServerMessage{
    private int code;
    private String message;
    private List<Player> players;
    private Chat chat;
    private long master;
    private long roundTime;
    private long timeLeft;

    public GreetingMessage(int code, String message, List<Player> players, Chat chat, long master, long roundTime, long timeLeft) {
        this();
        this.code = code;
        this.message = message;
        this.players = players;
        this.chat = chat;
        this.master = master;
        this.roundTime = roundTime;
        this.timeLeft = timeLeft;
    }

    public GreetingMessage(){
        super(ServerMessageType.GREETING);
    }
}
