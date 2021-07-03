package com.leosbelpoll.mancala.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.leosbelpoll.mancala.exception.PlayException;
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

    public void play(Integer position) {
        Player currentPlayer;
        Player player;

        if(checkCurrentPlayer1()) {
            currentPlayer = player1;
            player = player2;
        } else {
            currentPlayer = player2;
            player = player1;
        }
        validPosition(currentPlayer, position);

        int balls = currentPlayer.getPits()[position];
        currentPlayer.getPits()[position] = 0;

        int tempPosition = position + 1;
        Player tempPLayer = currentPlayer;
        boolean againPlay = false;

        for (int i = balls; i > 0; i--) {
            if(tempPLayer.getId().equals(currentPlayer.getId())) {
                if(tempPosition < currentPlayer.getPits().length) {
                    currentPlayer.addBall(tempPosition);
                    int positionPitPlayer = pitsNumber - 1 - tempPosition;
                    if(currentPlayer.getPits()[tempPosition] == 1 && i == 1 && player.getPits()[positionPitPlayer] > 0) {
                        currentPlayer.addHut(1 + player.getPits()[positionPitPlayer]);
                        currentPlayer.subtractBalls(tempPosition);
                        player.subtractBalls(positionPitPlayer);
                    }
                } else {
                    currentPlayer.goHut();
                    tempPosition = -1;
                    tempPLayer = player;
                    if(i == 1)
                        againPlay = true;
                }
            } else {
                if(tempPosition < currentPlayer.getPits().length) {
                    player.addBall(tempPosition);
                } else {
                    tempPLayer = currentPlayer;
                    tempPosition = -1;
                    i++;
                }
            }
            tempPosition +=1;
        }

        endGame();

        if(!againPlay) {
            switchPlayer();
        }
    }

    public void validPosition(Player player, Integer position) {
        if (position > pitsNumber || position < 0)
            throw new PlayException("Invalid position");
        if(player.getPits()[position] == 0) {
            throw new PlayException("Empty pit");
        }
    }

    private void switchPlayer() {
        if (checkCurrentPlayer1()) {
            turn = player2.getUser();
        } else {
            turn = player1.getUser();
        }
    }

    public boolean checkCurrentPlayer1() {
        return turn.getId().equals(player1.getUser().getId());
    }

    public void endGame() {
        if(checkEndGame()) {
            status = Status.FINISHED;
            player1.endGame();
            player2.endGame();
            if(player1.getHut() > player2.getHut()){
                winner = player1.getUser();
            } else if(player1.getHut() < player2.getHut()){
                winner = player2.getUser();
            }
        }
    }
    public boolean checkEndGame() {
        return Arrays.stream(player1.getPits()).allMatch(e -> e == 0) || Arrays.stream(player2.getPits()).allMatch(e -> e == 0);
    }
}
