package edu.isa681.game;

import edu.isa681.DOA.entity.Player;
import edu.isa681.game.items.Board;

import java.util.List;

public class GameState {
    private final static Integer Max_Moves = 104;
    Integer boardVersionNumber;
    Boolean isSequenceDone;
    List<Board.Cell> boardStateSnapShot;
    Player playerWon;
    byte[] currentPlayer;
    String currentPlayerSub;


    public GameState(Game game) {
        boardVersionNumber = 0;
        isSequenceDone = false;
        boardStateSnapShot = game.board.generateBoardSnapshot();
        setCurrentPlayer(game);
    }


    void incrementBoardState(Game game) {
        if (boardVersionNumber == Max_Moves) {
            throw new IllegalStateException("All the moves has been made. Game is over");
        }
        boardVersionNumber++;
        boardStateSnapShot = game.board.generateBoardSnapshot();
    }

    public void setPlayerWon(Player playerWon) {
        if (isSequenceDone) {
            this.playerWon = playerWon;
        }
    }

    public Integer getBoardVersionNumber() {
        return boardVersionNumber;
    }

    public Boolean getSequenceDone() {
        return isSequenceDone;
    }

    public List<Board.Cell> getBoardStateSnapShot() {
        return boardStateSnapShot;
    }

    public Player getPlayerWon() {
        return playerWon;
    }

    public void setBoardVersionNumber(Integer boardVersionNumber) {
        this.boardVersionNumber = boardVersionNumber;
    }

    public void setSequenceDone(Boolean sequenceDone) {
        isSequenceDone = sequenceDone;
    }

    public void setBoardStateSnapShot(List<Board.Cell> boardStateSnapShot) {
        this.boardStateSnapShot = boardStateSnapShot;
    }

    public byte[] getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(byte[] currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setCurrentPlayer(Game game) {
        currentPlayer = game.playersGameSessions.get(game.turnIndex).getPlayer().getName();
        setCurrentPlayerSub(game);
    }

    public String getCurrentPlayerSub() {
        return currentPlayerSub;
    }

    public void setCurrentPlayerSub(String currentPlayerSub) {
        this.currentPlayerSub = currentPlayerSub;
    }

    private void setCurrentPlayerSub(Game game) {
        currentPlayerSub = game.playersGameSessions.get(game.turnIndex).getPlayer().getPlayerSub();
    }
}
