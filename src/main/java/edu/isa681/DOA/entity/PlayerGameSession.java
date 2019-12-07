package edu.isa681.DOA.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.isa681.game.Game;
import edu.isa681.game.items.Card;
import edu.isa681.game.types.Chips;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerGameSession {
    List<Card> cardsList;
    Chips chip;
    Integer numberChipsAvailable;
    @JsonIgnore
    public Player player;
    @JsonIgnore
    Game game;

    void placeChip(Point point, Integer cardIndex) {
        if (cardsList.size() > cardIndex && cardIndex > -1) {
            this.game.placeChip(this, point, cardIndex);
        } else {
            System.out.println("Card position given is out of bound... No card of such an index exist");
            System.out.println("Tried to get card -- " + cardIndex + " out of a total " + this.cardsList.size());
            throw new ArrayIndexOutOfBoundsException("Card position given is out of bound... No card of such an index exist");
        }
    }

    PlayerGameSession(Player player) {
        this.player = player;
        this.cardsList = new ArrayList<>();
    }

    public void pickNextCard(Card cardToThrow, Card cardToPick) {
        cardsList.remove(cardToThrow);
        cardsList.add(cardToPick);
    }

    public List<Card> getCardsList() {
        return cardsList;
    }

    public void setCardsList(List<Card> cardsList) {
        this.cardsList = cardsList;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void addCard(Card card) {
        this.cardsList.add(card);
    }

    public Chips getChip() {
        return chip;
    }

    public void setChip(Chips chip) {
        this.chip = chip;
    }

    public Integer getNumberChipsAvailable() {
        return numberChipsAvailable;
    }

    public void setNumberChipsAvailable(Integer numberChipsAvailable) {
        this.numberChipsAvailable = numberChipsAvailable;
    }
}
