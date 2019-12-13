package edu.isa681.DOA.entity;

import javax.persistence.*;
import java.util.List;


// Player is fkey Player has one to many rel with history
@Entity
@Table
public class PlayerGameHistory {
    Integer ID;
    Player player;
    List<String> moves;
    Boolean Won;

    public PlayerGameHistory() {
    }

    @Id
    @GeneratedValue
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    @ManyToOne
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @ElementCollection
    public List<String> getMoves() {
        return moves;
    }

    public void setMoves(List<String> moves) {
        this.moves = moves;
    }

    public Boolean getWon() {
        return Won;
    }

    public void setWon(Boolean won) {
        Won = won;
    }
}
