package ru.nsu.fit.crocodile.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.nsu.fit.crocodile.controller.WebSocketController;
import ru.nsu.fit.crocodile.exception.PlayerAlreadyInTheRoomException;
import ru.nsu.fit.crocodile.message.Client.JoinMessage;
import ru.nsu.fit.crocodile.message.Server.GreetingMessage;
import ru.nsu.fit.crocodile.message.Server.NewPlayerMessage;
import ru.nsu.fit.crocodile.message.Server.PlayerImageRequest;
import ru.nsu.fit.crocodile.model.Image;
import ru.nsu.fit.crocodile.model.Player;
import ru.nsu.fit.crocodile.model.Room;

import java.security.Principal;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Component
public class RoomService {

    private ConcurrentHashMap<Long, Room> rooms = new ConcurrentHashMap<>();

    private WebSocketController controller;

    @Autowired
    private ImageWaiting waiting;

    public RoomService(WebSocketController controller){
        this();
        this.controller = controller;
    }

    @Autowired
    public RoomService(){ // TODO: 28.10.2022 Создание комнат
        rooms.put(1L, new Room(1L, "Test"));
    }

    public void joinGame(JoinMessage message, Long roomId, SimpMessageHeaderAccessor headerAccessor){
        Room room = rooms.get(roomId);
        GreetingMessage greetingMessage;
        Player player = null;
        if(room == null) {
            greetingMessage = new GreetingMessage(-1, "Комната с таким ID не найдена", null, 0);
        }
        else {
            try {
                player = room.addPlayer(message.getId(), message.getUsername(), headerAccessor.getUser());
                headerAccessor.getSessionAttributes().put("roomId", roomId);
                greetingMessage = new GreetingMessage(0, "", new ArrayList<>(room.getPlayers().values()), room.getMaster());
            } catch (PlayerAlreadyInTheRoomException e) {
                greetingMessage = new GreetingMessage(-1, "Игрок с таким именем уже находится в этой комнате", null, 0);
            }
        }
        controller.getTemplate().convertAndSendToUser(headerAccessor.getUser().getName(), "/queue/session", greetingMessage);
        if(greetingMessage.getCode() == 0) {
            controller.getTemplate().convertAndSend("/topic/session", new NewPlayerMessage(message.getId()));
            Player master = room.getPlayers().get(room.getMaster());
            if(master != null) {
                askForImage(player, room.getPlayers().get(room.getMaster()));
            }
        }
    }

    public void askForImage(Player newPlayer, Player master){
        controller.getTemplate().convertAndSendToUser(master.getPrincipal().getName(), "/queue/session", new PlayerImageRequest());
        controller.getWaiting().addWaiter(master, (image -> sendImage(newPlayer, image)));
    }

    public void sendImage(Player newPlayer, Image image){
        controller.getTemplate().convertAndSendToUser(newPlayer.getPrincipal().getName(), "/queue/session", image);
    }

}
