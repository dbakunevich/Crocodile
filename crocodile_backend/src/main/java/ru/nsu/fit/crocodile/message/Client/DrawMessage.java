package ru.nsu.fit.crocodile.message.Client;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
public class DrawMessage {
    @NotNull
    private String changes;
}
