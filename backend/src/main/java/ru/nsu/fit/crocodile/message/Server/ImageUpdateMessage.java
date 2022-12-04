package ru.nsu.fit.crocodile.message.Server;

import lombok.Getter;
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
