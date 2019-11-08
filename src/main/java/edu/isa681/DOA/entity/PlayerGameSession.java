package edu.isa681.DOA.entity;

import edu.isa681.game.Game;
import edu.isa681.game.items.Card;
import edu.isa681.game.types.Chips;

import java.util.ArrayList;
import java.util.List;

public class PlayerGameSession {
    List<Card> cardsList;
    Chips chip;

    Integer numberChipsAvailable;
    Player player;
    Game game;

    PlayerGameSession(Player player) {
        this.player = player;
        this.cardsList = new ArrayList<Card>();
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
