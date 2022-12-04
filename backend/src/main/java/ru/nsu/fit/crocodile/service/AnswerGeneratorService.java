package ru.nsu.fit.crocodile.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AnswerGeneratorService {
    public Set<String> generate(){
        Set<String> answers = new HashSet<>();
        answers.add("Крокодил");
        answers.add("Змея");
        answers.add("Мышь");
        return answers;
    }
}
