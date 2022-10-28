package ru.nsu.fit.crocodile.controller;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.nsu.fit.crocodile.message.Client.JoinMessage;
import ru.nsu.fit.crocodile.message.Server.GreetingMessage;
import ru.nsu.fit.crocodile.model.Image;
import ru.nsu.fit.crocodile.model.test.TestAnswer;
import ru.nsu.fit.crocodile.model.test.TestInput;
import ru.nsu.fit.crocodile.service.ImageWaiting;
import ru.nsu.fit.crocodile.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class WebSocketController {
    private RoomService roomService;

    @Getter
    @Autowired
    SimpMessagingTemplate template;

    @Getter
    @Autowired
    ImageWaiting waiting;

    static final Logger log = LoggerFactory.getLogger(WebSocketController.class);


    public WebSocketController(){
        roomService = new RoomService(this);
    }

    @MessageMapping("/test")
    //@SendTo("/topic/answer")
    public void greeting(TestInput message, SimpMessageHeaderAccessor accessor) {
        String s = (String) accessor.getSessionAttributes().get("prev");
        log.info(accessor.getUser().getName());
        if(s == null){
            accessor.getSessionAttributes().put("prev", message.getContent());
        }
        else {
            accessor.getSessionAttributes().put("prev", s.concat(message.getContent()));
        }
        template.convertAndSendToUser(accessor.getUser().getName(), "/queue/session", new TestAnswer("Input was: " + accessor.getSessionAttributes().get("prev") + accessor.getSessionAttributes().get("roomId")));
        //return new TestAnswer("Input was: " + accessor.getSessionAttributes().get("prev") + accessor.getSessionAttributes().get("roomId"));

    }

    @MessageMapping("/join/{roomId}")
    public void joinRoom(JoinMessage message, @DestinationVariable Long roomId, SimpMessageHeaderAccessor headerAccessor){
        roomService.joinGame(message, roomId, headerAccessor);
    }

    @MessageMapping("/session/master/image")
    public void forwardImage(Image image, SimpMessageHeaderAccessor headerAccessor){
        waiting.forwardImage(headerAccessor.getUser(), image);
    }

    public void testMessage (String message){
        template.convertAndSend("/topic/answer", "message");
    }


}
