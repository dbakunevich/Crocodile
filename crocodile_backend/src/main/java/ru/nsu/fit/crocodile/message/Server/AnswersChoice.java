package ru.nsu.fit.crocodile.message.Server;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
public class AnswersChoice extends ServerMessage {
    private final Set<String> answers;
}
