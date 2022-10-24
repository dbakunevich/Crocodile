package ru.nsu.fit.crocodile.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

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

    @ManyToMany
    private List<UserData> friends = new LinkedList<>();

    @ManyToMany(mappedBy = "outcomingFriendRequests")
    private List<UserData> incomingFriendRequests = new LinkedList<>();

    @ManyToMany
    private List<UserData> outcomingFriendRequests = new LinkedList<>();

}
