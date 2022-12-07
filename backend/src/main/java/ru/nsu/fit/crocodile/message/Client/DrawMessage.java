package ru.nsu.fit.crocodile.message.Client;

import lombok.Data;
import ru.nsu.fit.crocodile.model.Point;


@Data
public class DrawMessage {
    final Point point;
    final Point prevPoint;
    final String color;
    final Integer width;
}