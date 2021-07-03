package com.leosbelpoll.mancala.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ConfigurationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Environment env;

    @Test
    void whenGetConfig_thenSuccess() throws Exception {

        mockMvc.perform(get("/v1/configs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maxPitsNumber",
                        is(Integer.parseInt(env.getProperty("app.configs.maxPitsNumber")))))
                .andExpect(jsonPath("$.pitsNumber",
                        is(Integer.parseInt(env.getProperty("app.configs.pitsNumber")))))
                .andExpect(jsonPath("$.ballsNumber",
                        is(Integer.parseInt(env.getProperty("app.configs.ballsNumber")))))
                .andExpect(jsonPath("$.maxBallsNumber",
                        is(Integer.parseInt(env.getProperty("app.configs.maxBallsNumber")))));
    }

}