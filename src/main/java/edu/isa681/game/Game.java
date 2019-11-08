package edu.isa681.game;

import edu.isa681.DOA.entity.Player;
import edu.isa681.DOA.entity.PlayerGameSession;
import edu.isa681.DOA.entity.type.PlayerSate;
import edu.isa681.game.items.Board;
import edu.isa681.game.items.Card;
import edu.isa681.game.items.Deck;
import edu.isa681.game.types.CardType;
import edu.isa681.game.types.Chips;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Game {
    final static Logger log = Logger.getLogger(Game.class);
    private Deck deck;
    private Board board;
    ArrayList<PlayerGameSession> playersGameSessions;
    Integer turnIndex;

    public Game(Player[] players) {

        initPlayerSession(players);
        Set<Chips> chips = Chips.availableChips();
        playersGameSessions.forEach(player -> {
            player.setGame(this);
            player.setChip(chips.iterator().next());
        });
        turnIndex = 0;

        board = new Board();
    }

    private void initPlayerSession(Player[] players) {
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
        for (int __i = 0; __i < 6; __i++) {
            for (PlayerGameSession playerGameSession : playersGameSessions) {
                playerGameSession.addCard(this.deck.dealCard());
            }
        }
    }

    public PlayerGameSession nextTurn() {
        if (turnIndex == this.playersGameSessions.size() - 1) {
            turnIndex = 0;
        } else {
            turnIndex++;
        }

        return this.playersGameSessions.get(turnIndex);
    }

    public void placeChip(PlayerGameSession playerGameSession, Point point, Integer cardIndex) throws Exception {

        //Still have to incorporate joker rules
        if (playersGameSessions.indexOf(playerGameSession) == turnIndex) {
            try {
                Card card = playerGameSession.getCardsList().get(cardIndex);
                if (card.equals(this.board.getCellCardType(point))) {
                    this.board.putChip(point, playerGameSession.getChip());
                } else throw new IllegalStateException("Chip Type Mismatch");
            } catch (ArrayIndexOutOfBoundsException ex) {
                throw new ArrayIndexOutOfBoundsException("Card position given is out of bound... No card of such an index exist");
            } catch (Exception ex) {
                log.info("Exception occured ", ex);
                throw ex;
            }
        } else throw new IllegalArgumentException("The present player doesnt have turn right now.");
    }
}
