package ru.nsu.fit.crocodile.controller;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.nsu.fit.crocodile.message.Client.*;
import ru.nsu.fit.crocodile.message.Client.DrawMessage;
import ru.nsu.fit.crocodile.model.Image;
import ru.nsu.fit.crocodile.service.ImageWaiting;
import ru.nsu.fit.crocodile.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class WebSocketController {
    @Autowired
    private RoomService roomService;

    @Getter
    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    ImageWaiting waiting;

    static final Logger log = LoggerFactory.getLogger(WebSocketController.class);


    public WebSocketController(){

    }

    @MessageMapping("/join/{roomId}")
    public void joinRoom(JoinMessage message, @DestinationVariable Long roomId, SimpMessageHeaderAccessor headerAccessor){
        roomService.joinGame(message, roomId, headerAccessor);
    }

    @MessageMapping("/session/master/image")
    public void forwardImage(Image image, SimpMessageHeaderAccessor headerAccessor){
        waiting.forwardImage(headerAccessor.getUser(), image);
    }

    @MessageMapping("/session/draw")
    public void drawEvent(DrawMessage message, SimpMessageHeaderAccessor headerAccessor){
        roomService.draw(message, headerAccessor);
    }

    @MessageMapping("/session/chat")
    public void chat(ClientChatMessage message, SimpMessageHeaderAccessor headerAccessor){
        roomService.sendChatMessage(message, headerAccessor);
    }

    @MessageMapping("/session/react")
    public void react(ClientReactionMessage message, SimpMessageHeaderAccessor headerAccessor){
        roomService.react(message, headerAccessor);
    }

    @MessageMapping("/session/choose")
    public void choose(MasterChoiceMessage message, SimpMessageHeaderAccessor headerAccessor){
        roomService.choose(message, headerAccessor);
    }


}
