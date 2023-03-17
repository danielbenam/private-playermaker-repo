package com.danielbenami.playermaker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlayersResponseDto {


    private List<String> mostParticipatedPlayers;



}
