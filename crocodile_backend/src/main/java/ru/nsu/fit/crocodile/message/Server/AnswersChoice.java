package ru.nsu.fit.crocodile.message.Server;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
public class AnswersChoice {
    private final Set<String> answers;
}
