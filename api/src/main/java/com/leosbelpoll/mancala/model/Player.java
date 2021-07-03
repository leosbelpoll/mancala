package com.leosbelpoll.mancala.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "players")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    private int[] pits;

    private int hut;

    public void addBall(int position) {
        this.getPits()[position] +=1;
    }

    public void subtractBalls(int pitPosition) {
        this.getPits()[pitPosition] = 0;
    }

    public void goHut() {
        hut +=1;
    }

    public void addHut(int amount) {
        hut = hut+amount;
    }

    public void endGame() {
        for (int i = 0; i < pits.length; i++) {
            addHut(pits[i]);
            pits[i] = 0;
        }

    }
}
