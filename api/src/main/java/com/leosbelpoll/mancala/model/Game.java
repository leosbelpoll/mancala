package com.leosbelpoll.mancala.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

@Entity
@Table(name = "games")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    private int pitsNumber;

    private int ballsNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player1_id")
    private Player player1;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player2_id")
    private Player player2;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User turn;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User winner;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    public Game(Integer pitsNumber, Integer ballsNumber, User user1, User user2) {
        this.pitsNumber = pitsNumber != null ? pitsNumber : 6;
        this.ballsNumber = ballsNumber != null ? ballsNumber : 4;
        startGame(user1, user2);
    }

    public Game(User user1, User user2) {
        this.pitsNumber = 6;
        this.ballsNumber = 4;
        startGame(user1, user2);
    }

    public void startGame(User user1, User user2) {
        this.createAt = new Date();
        int [] pits = new int[this.pitsNumber];
        Arrays.fill(pits, this.ballsNumber);
        this.player1 = Player.builder().pits(pits).user(user1).build();
        this.player2 = Player.builder().pits(Arrays.copyOf(pits, pits.length)).user(user2).build();

        if(new Random().nextInt(2) == 0)
            this.turn = user1;
        else
            this.turn = user2;
        this.status = Status.IN_PROGRESS;
    }
}
