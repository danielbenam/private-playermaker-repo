package com.danielbenami.playermaker.controller;


import com.danielbenami.playermaker.dto.PlayersRequestDto;
import com.danielbenami.playermaker.dto.PlayersResponseDto;
import com.danielbenami.playermaker.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/topPlayers")
    public ResponseEntity<PlayersResponseDto> getTopPlayers(@Valid @RequestBody PlayersRequestDto playersRequestDto){
        final List<String> topPlayers = playerService.getTopPlayers(playersRequestDto);
        PlayersResponseDto playersResponseDto = new PlayersResponseDto(topPlayers);
        return new ResponseEntity<>(playersResponseDto, HttpStatus.OK);
    }
}

