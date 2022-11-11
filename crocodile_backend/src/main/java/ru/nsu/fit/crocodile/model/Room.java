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
    private String answer = "default";
    private Chat chat;
    private boolean paused = false;
    private Set<String> answerSet;
    private final long roundLength = 180000; //Этому тут не место, но пока пусть побудет тут.
    private final Timer timer = new Timer();
    private long startTime;

    public Room(Long id, String name) {
        this.id = id;
        this.name = name;
        this.chat = new Chat();
    }

    public Long generateMaster() {
        Random random = new Random();
        List<Long> keys = new ArrayList<>(players.keySet());
        masterId = keys.get(random.nextInt(keys.size()));
        return masterId;
    }

    public Set<String> generateAnswers(){
        // TODO: 28.10.2022 Рандомная генерация слов
        answerSet = new HashSet<>();
        answerSet.add("Крокодил");
        answerSet.add("Змея");
        answerSet.add("Мышь");
        return answerSet;
    }

    public void start(String answer){
        this.answer = answer;
        paused = false;
        startTime = System.currentTimeMillis();
    }

    public boolean checkAnswer(String answer) {
        return this.answer.equals(answer);
    }

    synchronized public void pause() {
        paused = true;
    }

    public void addPoint(Long id) {
        players.get(id).getScore().addAndGet(1);
    }

    public Player getMaster() {
        return players.get(masterId);
    }

    public Player addPlayer(Long id, String name, Principal principal) throws PlayerAlreadyInTheRoomException {
        Player player = new Player(id, name, principal);
        if (players.size() == 0) // TODO: 28.10.2022 Удалить после написания нормального старта игры
            masterId = id;
        if (players.putIfAbsent(id, player) != null) {
            throw new PlayerAlreadyInTheRoomException();
        } else {
            return player;
        }
    }

    public void removePlayer(Long id) {
        players.remove(id);
    }

}
