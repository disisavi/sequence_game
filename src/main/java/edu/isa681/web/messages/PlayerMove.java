package edu.isa681.web.messages;

public class PlayerMove {
    String playerSub;
    Integer x;
    Integer y;
    Integer cardIndex;

    public String getPlayerSub() {
        return playerSub;
    }

    public void setPlayerSub(String playerSub) {
        this.playerSub = playerSub;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getCardIndex() {
        return cardIndex;
    }

    public void setCardIndex(Integer cardIndex) {
        this.cardIndex = cardIndex;
    }
}
