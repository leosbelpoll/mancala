package com.leosbelpoll.mancala.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leosbelpoll.mancala.dto.PlayRequestDto;
import com.leosbelpoll.mancala.dto.StartGameRequestDto;
import com.leosbelpoll.mancala.model.Game;
import com.leosbelpoll.mancala.model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @ParameterizedTest
    @CsvSource({
            "user1@gmail.com, user2@gmail.com",
            "mock11@gmail.com, mock22@gmail.com",
            "user2@gmail.com, user1@gmail.com",
    })
    void whenCreateGame_thenResponseOk(String email1, String email2) throws Exception {
        StartGameRequestDto request = StartGameRequestDto.builder().user1(email1).user2(email2).build();
        mockMvc.perform(post("/v1/game")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is("IN_PROGRESS")))
                .andExpect(jsonPath("$.pitsNumber", is(6)))
                .andExpect(jsonPath("$.ballsNumber", is(4)))
                .andExpect(jsonPath("$.turn", notNullValue()))

                .andExpect(jsonPath("$", hasKey("player1")))
                .andExpect(jsonPath("$.player1", notNullValue()))
                .andExpect(jsonPath("$.player1.user.email", is(email1)))
                .andExpect(jsonPath("$.player1.pits", hasSize(6)))
                .andExpect(jsonPath("$.player1.pits", everyItem(is(4))))

                .andExpect(jsonPath("$", hasKey("player2")))
                .andExpect(jsonPath("$.player2", notNullValue()))
                .andExpect(jsonPath("$.player2.user.email", is(email2)))
                .andExpect(jsonPath("$.player2.pits", hasSize(6)))
                .andExpect(jsonPath("$.player1.pits", everyItem(is(4))));
    }

    @ParameterizedTest
    @CsvSource({
            "user1@gmail.com, user2@gmail.com, 4, 2",
            "mock11@gmail.com, mock22@gmail.com, 5, 6",
            "user2@gmail.com, user1@gmail.com, 10, 5",
    })
    void whenCreateGameWithConfig_thenResponseOk(String email1, String email2, Integer pits, Integer balls) throws Exception {
        StartGameRequestDto request = StartGameRequestDto.builder()
                .user1(email1).user2(email2).ballsNumber(balls).pitsNumber(pits).build();
        mockMvc.perform(post("/v1/game")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is("IN_PROGRESS")))
                .andExpect(jsonPath("$.pitsNumber", is(pits)))
                .andExpect(jsonPath("$.ballsNumber", is(balls)))
                .andExpect(jsonPath("$.turn", notNullValue()))

                .andExpect(jsonPath("$", hasKey("player1")))
                .andExpect(jsonPath("$.player1", notNullValue()))
                .andExpect(jsonPath("$.player1.user.email", is(email1)))
                .andExpect(jsonPath("$.player1.pits", hasSize(pits)))
                .andExpect(jsonPath("$.player1.pits", everyItem(is(balls))))

                .andExpect(jsonPath("$", hasKey("player2")))
                .andExpect(jsonPath("$.player2", notNullValue()))
                .andExpect(jsonPath("$.player2.user.email", is(email2)))
                .andExpect(jsonPath("$.player2.pits", hasSize(pits)))
                .andExpect(jsonPath("$.player1.pits", everyItem(is(balls))));
    }

    @ParameterizedTest
    @CsvSource({
            ", user2@gmail.com, 2, 2",
            "mock11@gmail.com, , 0, 2",
            "user2, user1@, 10, 5",
            "user2, user1, 10, 5",
            "user1@gmail.com, user2@gmail.com, 0, -1",
            "user1@gmail.com, user2@gmail.com, 21, 0",
    })
    void whenCreateGameWithConfig_thenBadRequest(String email1, String email2, Integer pits, Integer balls) throws Exception {
        StartGameRequestDto request = StartGameRequestDto.builder()
                .user1(email1).user2(email2).ballsNumber(balls).pitsNumber(pits).build();
        mockMvc.perform(post("/v1/game")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPlayGame_thenOk()  throws Exception{
        StartGameRequestDto request = StartGameRequestDto.builder().user1("user1@gmail.com").user2("user2@gmail.com")
                .ballsNumber(4).pitsNumber(6).build();
        String gameResponse = mockMvc.perform(post("/v1/game")
                .content(mapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        Game game = mapper.readValue(gameResponse, Game.class);

        int position = game.getPitsNumber()-3;
        Long idUser = game.getTurn().getId();

        game = this.play(game.getId(), position, idUser);

        Player currentPlayer = game.getPlayer2();
        Player otherPlayer = game.getPlayer1();

        if(idUser == game.getPlayer1().getUser().getId()) {
            currentPlayer = game.getPlayer1();
            otherPlayer = game.getPlayer2();
        }

        int[] move1_expected = {4,4,4,0,5,5};
        Assertions.assertArrayEquals(move1_expected, currentPlayer.getPits());
        Assertions.assertEquals(1, currentPlayer.getHut());

        int[] move1_expected2 = {5,4,4,4,4,4};
        Assertions.assertArrayEquals(move1_expected2, otherPlayer.getPits());

        Assertions.assertNotEquals(idUser, game.getTurn().getId());

        position = 2;
        idUser = game.getTurn().getId();

        game = this.play(game.getId(), position, idUser);
        currentPlayer = game.getPlayer2();
        otherPlayer = game.getPlayer1();

        if(idUser == game.getPlayer1().getUser().getId()) {
            currentPlayer = game.getPlayer1();
            otherPlayer = game.getPlayer2();
        }

        int[] move2_expected = {5,4,0,5,5,5};
        Assertions.assertArrayEquals(move2_expected, currentPlayer.getPits());
        Assertions.assertEquals(1, currentPlayer.getHut());

        int[] move2_expected2 = {4,4,4,0,5,5};
        Assertions.assertArrayEquals(move2_expected2, otherPlayer.getPits());

        Assertions.assertEquals(idUser, game.getTurn().getId());
    }

    private Game play(Long idGame, int position, Long idUser) throws Exception {
        PlayRequestDto requestDto = PlayRequestDto.builder().pitPosition(position).userId(idUser).build();
        String gameResponse = mockMvc.perform(put("/v1/game/"+idGame)
                .content(mapper.writeValueAsString(requestDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        Game game = mapper.readValue(gameResponse, Game.class);
        return game;
    }
}