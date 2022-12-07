package ru.nsu.fit.crocodile.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.nsu.fit.crocodile.controller.WebSocketController;
import ru.nsu.fit.crocodile.message.Server.ServerMessage;

@Service
public class MessageSender {
    static final Logger log = LoggerFactory.getLogger(WebSocketController.class);

    @Autowired
    private SimpMessagingTemplate template;

    public void sendToRoom(ServerMessage message, Long roomId){
        template.convertAndSend("/topic/session/" + roomId, message);
        log.warn("sendtoroom" + roomId);
    }

    public void sendToUser(ServerMessage message, String name){
        template.convertAndSendToUser(name, "/queue/session", message);
        log.warn("sendtouser" + name);
    }

}
