package ru.nsu.fit.crocodile;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.nsu.fit.crocodile.testMessages.TestAnswer;
import ru.nsu.fit.crocodile.testMessages.TestInput;

@Controller
public class WebSocketController {


    @MessageMapping("/test")
    @SendTo("/out/answer")
    public TestAnswer greeting(TestInput message) throws Exception {
        return new TestAnswer("Input was: " + message.getContent());
    }

}