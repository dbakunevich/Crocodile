package ru.nsu.fit.crocodile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.fit.crocodile.message.Server.ServerMessage;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image extends ServerMessage {
    String imageData;
}
