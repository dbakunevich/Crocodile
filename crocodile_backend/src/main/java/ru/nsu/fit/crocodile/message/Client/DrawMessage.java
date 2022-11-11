package ru.nsu.fit.crocodile.message.Client;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class DrawMessage {
    private final String changes;
}
