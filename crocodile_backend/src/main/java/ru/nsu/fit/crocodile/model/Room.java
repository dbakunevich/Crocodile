package ru.nsu.fit.crocodile.model;

import lombok.Getter;
import ru.nsu.fit.crocodile.exception.PlayerAlreadyInTheRoomException;

import java.security.Principal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class Room {

    private Long id;
    private String name;
    private ConcurrentHashMap<Long, Player> players = new ConcurrentHashMap<>();
    private Long masterId = 0L;
    private String answer;
    private Chat chat;

    public Room(Long id, String name){
        this.id = id;
        this.name = name;
        this.chat = new Chat();
    }

    public Long start(){
        // TODO: 24.10.2022 Generate words
        answer = "Ответ";
        Random random = new Random();
        List<Long> keys = new ArrayList<>(players.keySet());
        masterId = keys.get(random.nextInt(keys.size()));
        return masterId;
    }

    public boolean checkAnswer(String answer) {
        return this.answer.equals(answer);
    }

    public void addPoint(Long id){
        players.get(id).getScore().addAndGet(1);
    }

    public Player getMaster(){
        return players.get(masterId);
    }

    public Player addPlayer(Long id, String name, Principal principal) throws PlayerAlreadyInTheRoomException {
        Player player = new Player(id, name, principal);
        if(players.size() == 0) // TODO: 28.10.2022 Удалить после написания нормального старта игры
            masterId = id;
        if(players.putIfAbsent(id, player) != null) {
            throw new PlayerAlreadyInTheRoomException();
        }
        else {
            return player;
        }
    }

    public void removePlayer(Long id){
        players.remove(id);
    }

}
