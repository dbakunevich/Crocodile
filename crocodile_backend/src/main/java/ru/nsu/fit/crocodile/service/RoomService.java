package ru.nsu.fit.crocodile.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.nsu.fit.crocodile.controller.WebSocketController;
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
import java.util.concurrent.ConcurrentHashMap;

@Service
@Component
public class RoomService {

    private Logger log = LoggerFactory.getLogger(RoomService.class);

    private ConcurrentHashMap<Long, Room> rooms = new ConcurrentHashMap<>();

    private WebSocketController controller;

    @Autowired
    private ImageWaiting waiting;

    public RoomService(WebSocketController controller) {
        this();
        this.controller = controller;
    }

    @Autowired
    public RoomService() { // TODO: 28.10.2022 Создание комнат
        rooms.put(1L, new Room(1L, "Test"));
    }

    public void joinGame(JoinMessage message, Long roomId, SimpMessageHeaderAccessor headerAccessor) {
        Room room = rooms.get(roomId);
        GreetingMessage greetingMessage;
        Player player = null;
        if (room == null) {
            greetingMessage = new GreetingMessage(-1, "Комната с таким ID не найдена", null, null, 0);
        } else {
            try {
                player = room.addPlayer(message.getId(), message.getUsername(), headerAccessor.getUser());
                headerAccessor.getSessionAttributes().put("roomId", roomId);
                headerAccessor.getSessionAttributes().put("userId", message.getId());
                greetingMessage = new GreetingMessage(0, "", new ArrayList<>(room.getPlayers().values()), room.getChat(), room.getMasterId());
            } catch (PlayerAlreadyInTheRoomException e) {
                greetingMessage = new GreetingMessage(-1, "Игрок с таким именем уже находится в этой комнате", null, null, 0);
            }
        }
        controller.getTemplate().convertAndSendToUser(headerAccessor.getUser().getName(), "/queue/session", greetingMessage);
        if (greetingMessage.getCode() == 0) {
            controller.getTemplate().convertAndSend("/topic/session/" + roomId, new NewPlayerMessage(message.getId()));
            Player master = room.getPlayers().get(room.getMasterId());
            if (master != null) {
                askForImage(player, room.getPlayers().get(room.getMasterId()));
            }
        }
    }

    private void askForImage(Player newPlayer, Player master) {
        controller.getTemplate().convertAndSendToUser(master.getPrincipal().getName(), "/queue/session", new PlayerImageRequest());
        controller.getWaiting().addWaiter(master, (image -> sendImage(newPlayer, image)));
    }

    private void sendImage(Player newPlayer, Image image) {
        controller.getTemplate().convertAndSendToUser(newPlayer.getPrincipal().getName(), "/queue/session", image);
    }

    public void draw(DrawMessage message, SimpMessageHeaderAccessor headerAccessor) {
        Principal user = headerAccessor.getUser();
        Long roomId = (Long) headerAccessor.getSessionAttributes().get("roomId");
        if (roomId == null) {
            log.warn("roomId is null");
            return; // TODO: 28.10.2022
        }
        Room room = rooms.get(roomId);
        if (room == null) {
            log.warn("roomId doesn't exist: " + roomId);
            return; // TODO: 28.10.2022
        }
        if (!user.getName().equals(room.getMaster().getPrincipal().getName())) {
            log.warn("Not master trying to draw " + user.getName() + " " + room.getMaster().getUsername());
            return; // TODO: 28.10.2022
        }
        log.info(new ImageUpdateMessage(message).toString());
        controller.getTemplate().convertAndSend("/topic/session/" + roomId, new ImageUpdateMessage(message));
    }

    public void sendChatMessage(ClientChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
        Principal user = headerAccessor.getUser();
        Long userId = (Long) headerAccessor.getSessionAttributes().get("userId");
        Long roomId = (Long) headerAccessor.getSessionAttributes().get("roomId");
        if (roomId == null || userId == null) {
            return; // TODO: 28.10.2022
        }
        Room room = rooms.get(roomId);
        if (room == null) {
            return; // TODO: 28.10.2022
        }
        if (!user.getName().equals(room.getPlayers().get(userId).getPrincipal().getName())) {
            return; // TODO: 28.10.2022
        }
        ChatMessage chatMessage = room.getChat().addMessage(message.getMessage(), userId);
        if (room.checkAnswer(message.getMessage()) && !room.isPaused()) {
            room.pause();
            chatMessage.setReaction(ChatMessage.Reaction.RIGHT);
            room.addPoint(userId);
            controller.getTemplate().convertAndSend("/topic/session/" + roomId, chatMessage);
            startRound(room);
            return;
        }
        controller.getTemplate().convertAndSend("/topic/session/" + roomId, chatMessage);
    }

    public void startRound(Room room) {
        Long masterId = room.generateMaster();
        controller.getTemplate().convertAndSend("topic/session/" + room.getId(), new NewMasterMessage(masterId));
        Set<String> answerSet = room.generateAnswers();
        controller.getTemplate().convertAndSendToUser(room.getMaster().getPrincipal().getName(), "/queue/session", new AnswersChoice(answerSet));
    }

    public void react(ReactionMessage message, SimpMessageHeaderAccessor headerAccessor) {
        Principal user = headerAccessor.getUser();
        Long roomId = (Long) headerAccessor.getSessionAttributes().get("roomId");
        if (roomId == null) {
            return; // TODO: 28.10.2022
        }
        Room room = rooms.get(roomId);
        if (room == null) {
            return; // TODO: 28.10.2022
        }
        if (!user.getName().equals(room.getMaster().getPrincipal().getName())) {
            return; // TODO: 28.10.2022
        }
        room.getChat().react(message.getReaction(), message.getMessageId());
        controller.getTemplate().convertAndSend("/topic/session/" + roomId, message);
    }

    public void choose(MasterChoiceMessage message, SimpMessageHeaderAccessor headerAccessor) {
        Principal user = headerAccessor.getUser();
        Long roomId = (Long) headerAccessor.getSessionAttributes().get("roomId");
        if (roomId == null) {
            return; // TODO: 28.10.2022
        }
        Room room = rooms.get(roomId);
        if (room == null) {
            return; // TODO: 28.10.2022
        }
        if (!user.getName().equals(room.getMaster().getPrincipal().getName())) {
            return; // TODO: 28.10.2022
        }
        if(room.getAnswerSet().contains(message.getChoice())){
            room.start(message.getChoice());
            controller.getTemplate().convertAndSend("/topic/session/" + roomId, new RoundStartMessage());
        }
    }


}
