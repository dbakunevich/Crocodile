package ru.nsu.fit.crocodile.message.Server;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ErrorMessage extends ServerMessage {
    private String error;

    public ErrorMessage(String error){
        this();
        this.error = error;
    }

    public ErrorMessage(){
        super(ServerMessageType.ERROR);
    }
}
