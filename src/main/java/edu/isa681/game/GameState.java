package edu.isa681.game;

import edu.isa681.DOA.entity.Player;
import edu.isa681.game.items.Board;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class GameState {
    private final static Integer Max_Moves = 104;
    Integer boardVersionNumber;
    Boolean isSequenceDone;
    List<Board.Cell> boardStateSnapShot;

    Player playerWon;


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

    public Integer boardVersionNumber() {
        return boardVersionNumber;
    }

    public void setBoardVersionNumber(Integer boardVersionNumber) {
        this.boardVersionNumber = boardVersionNumber;
    }

    public Boolean isSequenceDone() {
        return isSequenceDone;
    }

    public void setSequenceDone(Boolean sequenceDone) {
        isSequenceDone = sequenceDone;
    }

    public List<Board.Cell> boardStateSnapShot() {
        return boardStateSnapShot;
    }

    public void setBoardStateSnapShot(List<Board.Cell> boardStateSnapShot) {
        this.boardStateSnapShot = boardStateSnapShot;
    }

    public Player playerWon() {
        return playerWon;
    }

    public void setPlayerWon(Player playerWon) {
        if (isSequenceDone) {
            this.playerWon = playerWon;
        }
    }
}
