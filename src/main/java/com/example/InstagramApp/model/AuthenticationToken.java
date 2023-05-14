package com.example.InstagramApp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor

public class AuthenticationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long tokenId;
    private String token;
    private LocalDate tokenCreation;
    @OneToOne()
    @JoinColumn(nullable = false,name =  "fk_patient_ID")
    private User user;

    public AuthenticationToken(User user) {
        this.user = user;
        this.tokenCreation = LocalDate.now();
        this.token = UUID.randomUUID().toString();
;    }
}
