package ru.nsu.fit.crocodile.message.Server;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
public class AnswersChoice extends ServerMessage {
    private Set<String> answers;

    public AnswersChoice(Set<String> answers) {
        this();
        this.answers = answers;
    }

    public AnswersChoice(){
        super(ServerMessageType.ANSWERS_CHOICE);
    }
}
