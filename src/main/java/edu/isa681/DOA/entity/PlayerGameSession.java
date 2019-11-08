package edu.isa681.DOA.entity;

import edu.isa681.game.Game;
import edu.isa681.game.items.Card;

import java.util.ArrayList;
import java.util.List;

public class PlayerGameSession {
    List<Card> cardsFacingDownList;
    List<Card> cardsFacingUpList;
    Player player;
    Game game;

    PlayerGameSession(Player player) {
        this.player = player;
        this.cardsFacingDownList = new ArrayList<Card>();
    }

    public List<Card> getCardsFacingDownList() {
        return cardsFacingDownList;
    }

    public void setCardsFacingDownList(List<Card> cardsFacingDownList) {
        this.cardsFacingDownList = cardsFacingDownList;
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

    public List<Card> getCardsFacingUpList() {
        return cardsFacingUpList;
    }

    public void setCardsFacingUpList(List<Card> cardsFacingUpList) {
        this.cardsFacingUpList = cardsFacingUpList;
    }

    public void addCard(Card card){
        this.cardsFacingDownList.add(card);
    }

}
