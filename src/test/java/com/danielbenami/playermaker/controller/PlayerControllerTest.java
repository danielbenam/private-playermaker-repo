package com.danielbenami.playermaker.controller;

import com.danielbenami.playermaker.dto.PlayersRequestDto;
import com.danielbenami.playermaker.dto.PlayersResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldGetTopPlayersWhenRequiredTopPlayersIsEqualTo1() throws Exception {

        final MvcResult result = mockMvc.perform(post("/players/topPlayers")
                .content(asJsonString(1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        PlayersResponseDto actual = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() { });
        assertEquals(List.of("Ronaldo"), actual.getMostParticipatedPlayers());
    }

    @Test
    void shouldGetTopPlayersWhenRequiredTopPlayersIsEqualTo2() throws Exception {

        final MvcResult result = mockMvc.perform(post("/players/topPlayers")
                .content(asJsonString(2))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        PlayersResponseDto actual = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() { });
        assertEquals(List.of("Ronaldo", "Shalom"), actual.getMostParticipatedPlayers());
    }

    @Test
    void shouldReturnAllThePlayersWhenRequiredTopPlayersIsEqualToTheNumberOfAllPlayersInTheGames() throws Exception {

        final MvcResult result = mockMvc.perform(post("/players/topPlayers")
                .content(asJsonString(8))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        PlayersResponseDto actual = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() { });
        assertEquals(List.of("Ronaldo", "Shalom", "Yechiel","Sharon","Myke","Messi","Sivan","Assaf"), actual.getMostParticipatedPlayers());
    }

    @Test
    void shouldReturnNoPlayersWhenRequiredTopPlayersIsEqualTo0() throws Exception {

        final MvcResult result = mockMvc.perform(post("/players/topPlayers")
                .content(asJsonString(0))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        PlayersResponseDto actual = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() { });
        assertEquals(Collections.emptyList(), actual.getMostParticipatedPlayers());

    }

    @Test
    void shouldFailWhenRequiredTopPlayersIsEqualToNegativeNumber() throws Exception {

        mockMvc.perform(post("/players/topPlayers")
                .content(asJsonString(-1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldFailWhenThereIsPlayerWithInvalidName() throws Exception {

        mockMvc.perform(post("/players/topPlayers")
                .content(asJsonStringWithInvalidName(1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldFailWhenRequiredTopPlayersIsMissing() throws Exception {

        mockMvc.perform(post("/players/topPlayers")
                .content(asJsonStringWithNoRequiredTopPlayers())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    private String asJsonString(int requiredTopPlayers) throws JsonProcessingException {
        PlayersRequestDto playersRequestDto = new PlayersRequestDto();
        playersRequestDto.setRequiredTopPlayers(requiredTopPlayers);
        List<List<String>> participatedPlayers = new ArrayList<>();
        participatedPlayers.add(List.of("Sharon", "Shalom", "Sharon", "Ronaldo"));
        participatedPlayers.add(List.of("Sharon", "Shalom", "Myke", "Ronaldo"));
        participatedPlayers.add(List.of("Yechiel", "Sivan", "Messi", "Ronaldo"));
        participatedPlayers.add(List.of("Yechiel", "Assaf", "Shalom", "Ronaldo"));
        playersRequestDto.setParticipatedPlayers(participatedPlayers);
        return mapper.writeValueAsString(playersRequestDto);
    }


    private String asJsonStringWithInvalidName(int requiredTopPlayers) throws JsonProcessingException {
        PlayersRequestDto playersRequestDto = new PlayersRequestDto();
        playersRequestDto.setRequiredTopPlayers(requiredTopPlayers);
        List<List<String>> participatedPlayers = new ArrayList<>();
        participatedPlayers.add(List.of("Sharon", "Shalom", "Sharon", "Rona9ldo"));
        playersRequestDto.setParticipatedPlayers(participatedPlayers);
        return mapper.writeValueAsString(playersRequestDto);

    }

    private String asJsonStringWithNoRequiredTopPlayers() throws JsonProcessingException {
        PlayersRequestDto playersRequestDto = new PlayersRequestDto();
        List<List<String>> participatedPlayers = new ArrayList<>();
        participatedPlayers.add(List.of("Sharon", "Shalom", "Sharon", "Rona9ldo"));
        playersRequestDto.setParticipatedPlayers(participatedPlayers);
        return mapper.writeValueAsString(playersRequestDto);
    }
}