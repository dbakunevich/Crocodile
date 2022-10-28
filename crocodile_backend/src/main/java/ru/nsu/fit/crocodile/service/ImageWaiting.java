package ru.nsu.fit.crocodile.service;

import org.springframework.stereotype.Component;
import ru.nsu.fit.crocodile.model.Image;
import ru.nsu.fit.crocodile.model.Player;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component("waiting")
public class ImageWaiting {
    private Map<String, ArrayList<ImageSender>> waitingUsers = new ConcurrentHashMap<>();

    public void addWaiter(Player master, ImageSender callback){
        waitingUsers.putIfAbsent(master.getPrincipal().getName(), new ArrayList<>());
        waitingUsers.get(master.getPrincipal().getName()).add(callback);
    }

    public void forwardImage(Principal master, Image image){
        ArrayList<ImageSender> waiters = waitingUsers.get(master.getName());
        waiters.forEach(imageSender -> imageSender.send(image));
        waiters.clear();
    }
}