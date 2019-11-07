package edu.isa681.game.items;

import edu.isa681.game.types.CardType;
import edu.isa681.game.types.GameSymbols;

public class Card {
    CardType cardType;
    GameSymbols gameSymbols;

    Card(CardType cardType, GameSymbols gameSymbols) {
        this.cardType = cardType;
        this.gameSymbols = gameSymbols;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public boolean equals(Object obj){
        if (obj instanceof Card){
            return ((Card) obj).cardType == this.cardType && ((Card) obj).gameSymbols == this.gameSymbols;
        }
        return false;
    }
}
