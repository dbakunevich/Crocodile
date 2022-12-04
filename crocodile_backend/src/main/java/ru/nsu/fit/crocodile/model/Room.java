package ru.nsu.fit.crocodile.model;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.fit.crocodile.exception.PlayerAlreadyInTheRoomException;

import java.security.Principal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
@Setter
public class Room {

    private Long id;
    private String name;
    private ConcurrentHashMap<Long, Player> players = new ConcurrentHashMap<>();
    private Long masterId = 0L;
    private String answer = "default";
    private Chat chat;
    private AtomicBoolean paused = new AtomicBoolean(false);
    private Set<String> answerSet;
    private final long roundLength = 180000; //Этому тут не место, но пока пусть побудет тут.
    private final Timer timer = new Timer();
    private long startTime;

    public Room(Long id, String name) {
        this.id = id;
        this.name = name;
        this.chat = new Chat();
    }

    public void start(String answer){
        this.answer = answer;
        paused.set(false);
        startTime = System.currentTimeMillis();
    }

    public Player getMaster() {
        return players.get(masterId);
    }
}
