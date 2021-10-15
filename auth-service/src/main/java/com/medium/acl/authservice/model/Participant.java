package com.medium.acl.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "participant")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "username", unique = true)
    private String username;
    private String password;
    private String authorities;

    public Participant(String username, String password, String authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
}
