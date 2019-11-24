package edu.isa681.web.game;

import edu.isa681.DOA.entity.Player;
import edu.isa681.game.Game;

import java.util.HashSet;
import java.util.Set;

public class GameController {
    private static GameController gameController;
    private Set<Player> players;
    private Set<Game> games;

    private GameController() {
        players = new HashSet<>();
        games = new HashSet<>();

    }

    /**
     * @return a singleton GameController
     */
    static GameController getGameController() {
        if (gameController == null) {
            synchronized (GameController.class) {
                if (gameController == null) {
                    gameController = new GameController();
                }
            }
        }
        return gameController;
    }


    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public void setPlayer(Player player) {
        this.players.add(player);
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public void setGame(Game game) {
        this.games.add(game);
    }
}
