package ru.nsu.fit.crocodile.service;

import ru.nsu.fit.crocodile.model.Image;

@FunctionalInterface
public interface ImageSender {
    public void send(Image image);
}
