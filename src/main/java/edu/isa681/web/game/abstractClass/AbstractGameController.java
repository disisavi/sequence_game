package edu.isa681.web.game.abstractClass;

import edu.isa681.DOA.entity.Player;
import edu.isa681.DOA.entity.type.PlayerSate;
import edu.isa681.game.Game;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AbstractGameController {
    private ConcurrentHashMap<String, Player> players;
    private Set<Game> games;

    public AbstractGameController() {
        players = new ConcurrentHashMap<>();
        games = new HashSet<>();
    }

    protected ConcurrentHashMap<String, Player> getPlayers() {
        return players;
    }

    public Player getPlayerWithSub(String playerSub) {
        return getPlayers().get(playerSub);
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

    public List<Player> getPlayersOnline() {
        List<Player> players = new ArrayList<>();

        for (Map.Entry<String, Player> entry : getPlayers().entrySet()) {
            Player v = entry.getValue();
            if ((v.getPlayerSate().equals(PlayerSate.Online))) {
                players.add(v);
            }
        }
        return players;
    }
}
