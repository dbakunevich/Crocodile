package ru.nsu.fit.crocodile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.crocodile.exception.PlayerAlreadyInTheRoomException;
import ru.nsu.fit.crocodile.message.Client.*;
import ru.nsu.fit.crocodile.message.Server.*;
import ru.nsu.fit.crocodile.model.ChatMessage;
import ru.nsu.fit.crocodile.model.Image;
import ru.nsu.fit.crocodile.model.Player;
import ru.nsu.fit.crocodile.model.Room;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoomService {

    private ConcurrentHashMap<Long, Room> rooms = new ConcurrentHashMap<>();
    
    @Autowired
    private ImageWaiting waiting;

    @Autowired
    private MessageSender sender;

    public RoomService() { // TODO: 28.10.2022 Создание комнат
        rooms.put(1L, new Room(1L, "Test"));
    }

    public void joinGame(JoinMessage message, Long roomId, SimpMessageHeaderAccessor headerAccessor) {
        Room room = rooms.get(roomId);
        GreetingMessage greetingMessage;
        Player player = null;
        if (room == null) {
            greetingMessage = new GreetingMessage(-1, "Комната с таким ID не найдена", null, null, 0, 0, 0);
        } else {
            try {
                player = room.addPlayer(message.getId(), message.getUsername(), headerAccessor.getUser());
                headerAccessor.getSessionAttributes().put("roomId", roomId);
                headerAccessor.getSessionAttributes().put("userId", message.getId());
                long roundLength = room.getRoundLength();
                long timeLeft = roundLength - (System.currentTimeMillis() - room.getStartTime());
                greetingMessage = new GreetingMessage(0, "", new ArrayList<>(room.getPlayers().values()), room.getChat(), room.getMasterId(), roundLength, timeLeft);
            } catch (PlayerAlreadyInTheRoomException e) {
                greetingMessage = new GreetingMessage(-1, "Игрок с таким именем уже находится в этой комнате", null, null, 0, 0, 0);
            }
        }
        sender.sendToUser(greetingMessage, headerAccessor.getUser().getName());
        if (greetingMessage.getCode() == 0) {
            sender.sendToRoom(new NewPlayerMessage(message.getId()), roomId);
            Player master = room.getPlayers().get(room.getMasterId());
            if (master != null) {
                askForImage(player, room.getPlayers().get(room.getMasterId()));
            }
        }
    }

    private void askForImage(Player newPlayer, Player master) {
        sender.sendToUser(new PlayerImageRequest(), master.getPrincipal().getName());
        waiting.addWaiter(master, (image -> sendImage(newPlayer, image)));
    }

    private void sendImage(Player newPlayer, Image image) {
        sender.sendToUser(image, newPlayer.getPrincipal().getName());
    }

    public void draw(DrawMessage message, SimpMessageHeaderAccessor headerAccessor) {
        Principal user = headerAccessor.getUser();
        Long roomId = (Long) headerAccessor.getSessionAttributes().get("roomId");
        if (roomId == null) {
            sender.sendToUser(new ErrorMessage("Игрок не находится в комнате"), user.getName());
            return;
        }
        Room room = rooms.get(roomId);
        if (room == null) {
            sender.sendToUser(new ErrorMessage("Игрок находится в несуществующей комнате"), user.getName());
            return;
        }
        if (!user.getName().equals(room.getMaster().getPrincipal().getName())) {
            sender.sendToUser(new ErrorMessage("Рисовать может только ведущий"), user.getName());
            return;
        }
        sender.sendToRoom(new ImageUpdateMessage(message), roomId);
    }

    public void sendChatMessage(ClientChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
        Principal user = headerAccessor.getUser();
        Long userId = (Long) headerAccessor.getSessionAttributes().get("userId");
        Long roomId = (Long) headerAccessor.getSessionAttributes().get("roomId");
        if (roomId == null || userId == null) {
            sender.sendToUser(new ErrorMessage("Игрок не находится в комнате"), user.getName());
            return;
        }
        Room room = rooms.get(roomId);
        if (room == null) {
            sender.sendToUser(new ErrorMessage("Игрок находится в несуществующей комнате"), user.getName());
            return;
        }
        if (!user.getName().equals(room.getPlayers().get(userId).getPrincipal().getName())) {
            sender.sendToUser(new ErrorMessage("Попытка отправки сообщения от лица другого игрока"), user.getName());
            return;
        }
        ChatMessage chatMessage = room.getChat().addMessage(message.getMessage(), userId);
        if (room.checkAnswer(message.getMessage()) && !room.isPaused()) {
            room.pause();
            chatMessage.setReaction(ChatMessage.Reaction.RIGHT);
            room.addPoint(userId);
            sender.sendToRoom(chatMessage, roomId);
            startRound(room);
            return;
        }
        sender.sendToRoom(chatMessage, roomId);
    }

    public void startRound(Room room) {
        Long masterId = room.generateMaster();
        sender.sendToRoom(new NewMasterMessage(masterId), room.getId());
        Set<String> answerSet = room.generateAnswers();
        sender.sendToUser(new AnswersChoice(answerSet), room.getMaster().getPrincipal().getName());
    }

    public void react(ReactionMessage message, SimpMessageHeaderAccessor headerAccessor) {
        Principal user = headerAccessor.getUser();
        Long roomId = (Long) headerAccessor.getSessionAttributes().get("roomId");
        if (roomId == null) {
            sender.sendToUser(new ErrorMessage("Игрок не находится в комнате"), user.getName());
            return;
        }
        Room room = rooms.get(roomId);
        if (room == null) {
            sender.sendToUser(new ErrorMessage("Игрок находится в несуществующей комнате"), user.getName());
            return;
        }
        if (!user.getName().equals(room.getMaster().getPrincipal().getName())) {
            sender.sendToUser(new ErrorMessage("Попытка отправки реакции от лица другого игрока"), user.getName());
            return;
        }
        room.getChat().react(message.getReaction(), message.getMessageId());
        sender.sendToRoom(message, roomId);
    }

    public void choose(MasterChoiceMessage message, SimpMessageHeaderAccessor headerAccessor) {
        Principal user = headerAccessor.getUser();
        Long roomId = (Long) headerAccessor.getSessionAttributes().get("roomId");
        if (roomId == null) {
            sender.sendToUser(new ErrorMessage("Игрок не находится в комнате"), user.getName());
            return;
        }
        Room room = rooms.get(roomId);
        if (room == null) {
            sender.sendToUser(new ErrorMessage("Игрок находится в несуществующей комнате"), user.getName());
            return;
        }
        if (!user.getName().equals(room.getMaster().getPrincipal().getName())) {
            sender.sendToUser(new ErrorMessage("Попытка выбора слова от лица ведущего"), user.getName());
            return;
        }
        if(room.getAnswerSet().contains(message.getChoice())){
            room.start(message.getChoice());
            room.getTimer().schedule(new TimerTask() {
                @Override
                public void run() {
                    room.pause();
                    startRound(room);
                }
            }, room.getRoundLength());
            sender.sendToRoom(new RoundStartMessage(), roomId);
        }
    }


}
