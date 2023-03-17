package com.danielbenami.playermaker.service;

import com.danielbenami.playermaker.dto.PlayersRequestDto;
import com.danielbenami.playermaker.handler.InvalidPlayerNameException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

@Service
public class PlayerService {


    public List<String> getTopPlayers(PlayersRequestDto input) {
        checkIfPlayersNamesAreValidNames(input);
        final var gamesWithoutDuplicatesPlayers = removeDuplicatesFromAllTheGames(input);
        final List<String> players = gamesWithoutDuplicatesPlayers.stream().flatMap(Collection::stream).collect(toList());
        return getTopPlayers(players, input.getRequiredTopPlayers());
    }



    private List<String> getTopPlayers(List<String> players, int requiredTopPlayers) {

       return players.stream()
                .collect(groupingBy(Function.identity(), counting()))
                .entrySet().stream()
                .sorted(comparing(Map.Entry<String, Long>::getValue).reversed())
                .limit(requiredTopPlayers)
                .map(Map.Entry::getKey)
                .collect(toList());
    }

    private List<List<String>> removeDuplicatesFromAllTheGames(PlayersRequestDto input) {

        final var participatedPlayers = input.getParticipatedPlayers();
        return participatedPlayers.stream().
                map(this::removeDuplicatesFromGame).collect(toList());
    }

    private List<String> removeDuplicatesFromGame(List<String> list) {
        return list.stream().distinct().collect(toList());
    }

    private void checkIfPlayersNamesAreValidNames(PlayersRequestDto input) {
        for (List<String> game : input.getParticipatedPlayers()) {
            for (String name : game) {
                if (!name.matches("[a-zA-Z]+")){
                    throw new InvalidPlayerNameException("player name invalid :  " + name);
                }
            }
        }
    }



}
