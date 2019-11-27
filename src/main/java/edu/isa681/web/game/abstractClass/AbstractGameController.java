package edu.isa681.web.game.abstractClass;

import edu.isa681.DOA.entity.Player;
import edu.isa681.game.Game;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AbstractGameController {
    private ConcurrentHashMap<String, Player> players;
    private Set<Game> games;

    public AbstractGameController() {
        players = new ConcurrentHashMap<>();
        games = new HashSet<>();
    }


    public ConcurrentHashMap<String, Player> getPlayers() {
        return players;
    }

    public void setPlayers(ConcurrentHashMap<String, Player> players) {
        this.players = players;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
}
