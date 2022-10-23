package ru.nsu.fit.crocodile.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.nsu.fit.crocodile.model.test.TestAnswer;
import ru.nsu.fit.crocodile.model.test.TestInput;

@Controller
public class WebSocketController {

    @MessageMapping("/test")
    @SendTo("/topic/answer")
    public TestAnswer greeting(TestInput message) {
        return new TestAnswer("Input was: " + message.getContent());
    }

}
