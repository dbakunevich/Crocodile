package ru.nsu.fit.crocodile.message.Server;

import lombok.Data;
import ru.nsu.fit.crocodile.message.Client.DrawMessage;

@Data
public class ImageUpdateMessage extends ServerMessage{
    private final String changes;
    private final long timestamp;

    public ImageUpdateMessage(DrawMessage message){
        changes = message.getChanges();
        timestamp = System.nanoTime();
    }
}
