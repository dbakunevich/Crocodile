package ru.nsu.fit.crocodile.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeNameRequest {
    private String email;
    private String newName;
}
