package edu.isa681.game;

import edu.isa681.DOA.entity.Player;
import edu.isa681.DOA.entity.PlayerGameSession;
import edu.isa681.DOA.entity.type.PlayerSate;
import edu.isa681.game.items.Board;
import edu.isa681.game.items.Card;
import edu.isa681.game.items.Deck;
import edu.isa681.game.types.Chips;
import edu.isa681.game.types.GameSymbols;
import org.apache.log4j.Logger;


import java.awt.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class Game {
    final static Logger log = Logger.getLogger(Game.class);
    private Deck deck;
    Board board;
    ArrayList<PlayerGameSession> playersGameSessions;
    Integer turnIndex;
    GameState gameState;

    public Game(List<Player> players) {
        log.info("New Game being created.");
        if (players.size() != 3) {
            throw new IllegalStateException("Invalid Number of players. Exactly 3 players allowed to play the game.");
        }
        playersGameSessions = new ArrayList<>();
        initPlayerSession(players);
        Chips[] chips = Chips.values();
        for (int i = 0; i < playersGameSessions.size(); i++) {
            PlayerGameSession player = playersGameSessions.get(i);
            player.setGame(this);
            player.setChip(chips[i]);

        }
        turnIndex = 0;
        this.board = new Board();
        this.deck = new Deck();
        this.distributeCards();
        this.gameState = new GameState(this);
        log.info("Game creation complete.");
    }

    private void initPlayerSession(List<Player> players) {
        log.info("New Player session initiation started");
        for (Player player : players) {
            if (player.getPlayerSate() == PlayerSate.Invited) {
                this.playersGameSessions.add(player.getNewPlayerSession());
                log.info("Session for Player " + player.getName() + "Initiated");
            } else {
                IllegalStateException exception = new IllegalStateException("Player " + player.getName() + " is not available to play a game right now");
                log.info("Game initiation failed ", exception);
                throw exception;
            }
        }
        log.info("All players initiated");
    }


    public void distributeCards() {
        log.info("Distribute 6 cards to each player");
        for (int __i = 0; __i < 6; __i++) {
            for (PlayerGameSession playerGameSession : playersGameSessions) {
                playerGameSession.addCard(this.deck.dealCard());
            }
        }
    }

    private void nextTurn() {
        if (turnIndex == this.playersGameSessions.size() - 1) {
            turnIndex = 0;
        } else {
            turnIndex++;
        }
        gameState.setCurrentPlayer(this);
        log.info("Next turn index is " + turnIndex);
    }

    public void placeChip(PlayerGameSession playerGameSession, Point point, Integer cardIndex) {
        log.info("Player " + playerGameSession.getPlayer().getName() + " has initiated next move");
        if (playersGameSessions.indexOf(playerGameSession) == turnIndex) {
            try {
                Card card = playerGameSession.getCardsList().get(cardIndex);
                if (card.equals(this.board.getCellCardType(point)) || card.getCardValue() == GameSymbols.JackTwoEye) {
                    this.board.putChip(point, playerGameSession.getChip());
                } else if (card.getCardValue() == GameSymbols.JackOneEye) {
                    this.board.removeChip(point);
                } else {
                    IllegalStateException ex = new IllegalStateException("The position does not match the Card selected");
                    log.info("Selected Card -- " + card.getCardValue() + "-" + card.getCardType() + " _ Card at position " + point.toString()
                            + " is " + this.board.getCellCardType(point).getCardValue() + "-" + this.board.getCellCardType(point).getCardType(), ex);
                    throw ex;
                }
                Card cardToPick = deck.dealCard();
                playerGameSession.pickNextCard(card, cardToPick);
                playerGameSession.setNumberChipsAvailable(playerGameSession.getNumberChipsAvailable() - 1);
            } catch (ArrayIndexOutOfBoundsException ex) {
                log.info("Card position given is out of bound... No card of such an index exist");
                log.info("Tried to get card -- " + cardIndex + " out of a total " + playerGameSession.getCardsList().size(), ex);
                throw new ArrayIndexOutOfBoundsException("Card position given is out of bound... No card of such an index exist");
            } catch (IllegalStateException ex) {
                log.info("Exception occurred ", ex);
                throw ex;
            }
        } else {
            IllegalArgumentException ex = new IllegalArgumentException("The present player doesnt have turn right now.");
            log.info("Wrong Player initiated the Move ", ex);
            throw ex;
        }
        gameState.incrementBoardState(this);
    }

    public Boolean checkSequenceAndNextTurn(PlayerGameSession playerGameSession) {
        log.info("Check for Sequence started");
        AbstractMap.SimpleEntry<Boolean, Chips> isSequnce = board.isSequence();
        if (isSequnce.getKey()) {
            gameState.setSequenceDone(true);
            if (isSequnce.getValue() == playerGameSession.getChip()) {
                gameState.setPlayerWon(playerGameSession.getPlayer());
                log.info("Valid Player won.");
            } else {
                log.info("Someone else won. Checking who won");
                for (PlayerGameSession playerSession : this.playersGameSessions) {
                    if (playerGameSession.getChip() == isSequnce.getValue()) {
                        gameState.setPlayerWon(playerSession.getPlayer());
                        log.info("Player " + playerGameSession.getPlayer().getName() + " won.");
                        break;
                    }
                }
            }
            return true;
        }
        nextTurn();
        return false;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public ArrayList<PlayerGameSession> getPlayersGameSessions() {
        return playersGameSessions;
    }

    public void setPlayersGameSessions(ArrayList<PlayerGameSession> playersGameSessions) {
        this.playersGameSessions = playersGameSessions;
    }

    public Integer getTurnIndex() {
        return turnIndex;
    }

    public void setTurnIndex(Integer turnIndex) {
        this.turnIndex = turnIndex;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
