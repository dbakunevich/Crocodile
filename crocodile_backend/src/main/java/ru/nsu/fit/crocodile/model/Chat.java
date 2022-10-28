package ru.nsu.fit.crocodile.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Chat {
    private List<ChatMessage> messages = new ArrayList<>();

    synchronized public ChatMessage addMessage(String message, Long userId) {
        ChatMessage newMessage = new ChatMessage(message, userId, messages.size(), ChatMessage.Reaction.NO_REACTION);
        messages.add(newMessage);
        return newMessage;
    }

    synchronized public void react(ChatMessage.Reaction reaction, Integer messageId) {
        ChatMessage message = messages.get(messageId);
        if (message == null) {
            return; // TODO: 28.10.2022
        }
        message.setReaction(reaction);
    }

    synchronized public void clear(){
        messages.clear();
    }
}
