package com.danielbenami.playermaker.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlayersRequestDto {

    @NotNull(message = "requiredTopPlayers must not be null")
    @Min(value=0, message="requiredTopPlayers must be equal or greater than 0")
    private Integer requiredTopPlayers;

    @NotNull(message = "participatedPlayers must not be null")
    private List<List<@NotBlank String>> participatedPlayers;


}
