package com.leosbelpoll.mancala.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leosbelpoll.mancala.dto.StartGameRequestDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

}