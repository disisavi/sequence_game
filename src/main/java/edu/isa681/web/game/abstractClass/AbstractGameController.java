package edu.isa681.web.game.abstractClass;

import edu.isa681.DOA.entity.Player;
import edu.isa681.DOA.entity.type.PlayerSate;
import edu.isa681.game.Game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public List<Player> getPlayersNotOffline() {
        List<Player> players = new ArrayList<>();

        getPlayers().forEach((k, v) -> {
            if (!(v.getPlayerSate().equals(PlayerSate.Offline))) {
                players.add(v);
            }
        });
        return players;
    }
}
