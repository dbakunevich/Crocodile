package ru.nsu.fit.crocodile.message.Server;

import lombok.Data;
import ru.nsu.fit.crocodile.message.Client.DrawMessage;

@Data
public class ImageUpdateMessage {
    private String changes;
    private long timestamp;

    public ImageUpdateMessage(DrawMessage message){
        changes = message.getChanges();
        timestamp = System.nanoTime();
    }
}
