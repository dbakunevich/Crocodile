package ru.nsu.fit.crocodile.message.Server;

/*import lombok.Getter;
import ru.nsu.fit.crocodile.message.Client.DrawMessage;

@Getter
public class ImageUpdateMessage extends ServerMessage{
    private String changes;
    private long timestamp;

    public ImageUpdateMessage(DrawMessage message){
        this();
        changes = message.getChanges();
        timestamp = System.nanoTime();
    }

    public ImageUpdateMessage(){
        super(ServerMessageType.IMAGE_UPDATE);
    }
}
*/

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.nsu.fit.crocodile.controller.WebSocketController;
import ru.nsu.fit.crocodile.message.Client.DrawMessage;
import ru.nsu.fit.crocodile.model.Point;

@Getter
public class ImageUpdateMessage extends ServerMessage {
    static final Logger log = LoggerFactory.getLogger(WebSocketController.class);
    Point point;
    Point prevPoint;
    String color;
    Integer width;
    private long timestamp;

    public ImageUpdateMessage(DrawMessage message) {
        this();
        log.warn("gg" + message);
        point = message.getPoint();
        prevPoint = message.getPrevPoint();
        color = message.getColor();
        width = message.getWidth();
        timestamp = System.nanoTime();
    }

    public ImageUpdateMessage() {
        super(ServerMessageType.IMAGE_UPDATE);
    }
}