package ru.nsu.fit.crocodile.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_data")
public class UserData {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private String email;

    @Enumerated(value = EnumType.ORDINAL)
    private Status status;

}
