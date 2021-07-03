package com.leosbelpoll.mancala.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

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
}
