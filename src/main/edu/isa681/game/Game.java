package edu.isa681.game;

import edu.isa681.DOA.entity.Player;
import edu.isa681.DOA.entity.PlayerGameSession;
import edu.isa681.DOA.entity.type.PlayerSate;
import edu.isa681.game.items.Deck;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class Game {
    final static Logger log = Logger.getLogger(Game.class);
    private Deck deck;
    ArrayList<PlayerGameSession> playersGameSessions;

    public Game(Player[] players) {
        log.info("New game initiation started");
        for (Player player : players) {
            if (player.getPlayerSate() == PlayerSate.Online) {
                this.playersGameSessions.add(player.getNewPlayerSession());
                this.deck = new Deck();
            } else {
                IllegalStateException exception = new IllegalStateException("Player " + player.getName() + " is not available to play a game right now");
                log.info("Game initiation failed ", exception);
                throw exception;
            }
        }
    }

    public void distributeCards() {
        for (int __i = 0; __i < 7; __i++) {
            for (PlayerGameSession playerGameSession : playersGameSessions) {
                playerGameSession.addCard(this.deck.dealCard());
            }
        }
    }

}
