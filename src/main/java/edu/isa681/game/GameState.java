package edu.isa681.game;

import edu.isa681.DOA.entity.Player;
import edu.isa681.game.items.Board;
import edu.isa681.web.game.EncryptionRoutine;
import edu.isa681.web.game.GameController;

import java.util.List;

public class GameState {
    private final static Integer Max_Moves = 104;
    Integer boardVersionNumber;
    Boolean isSequenceDone;
    List<Board.Cell> boardStateSnapShot;
    Player playerWon;
    String currentPlayer;
    String currentPlayerSub;


    public GameState(Game game) {
        boardVersionNumber = 0;
        isSequenceDone = false;
        boardStateSnapShot = game.board.generateBoardSnapshot();
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

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public String getCurrentPlayerSub() {
        return currentPlayerSub;
    }

    public void setCurrentPlayerSub(String currentPlayerSub) {
        this.currentPlayerSub = currentPlayerSub;
    }

}
