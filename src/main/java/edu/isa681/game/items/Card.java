package edu.isa681.game.items;

import edu.isa681.game.types.CardType;
import edu.isa681.game.types.GameSymbols;


public class Card {
    CardType cardType;
    GameSymbols cardValue;

    Card(CardType cardType, GameSymbols cardValue) {
        this.cardType = cardType;
        this.cardValue = cardValue;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Card) {
            if (this.cardValue == GameSymbols.JackOneEye || this.cardValue == GameSymbols.JackTwoEye) {
                return ((Card) obj).cardValue == this.cardValue;
            } else {
                return ((Card) obj).cardType == this.cardType && ((Card) obj).cardValue == this.cardValue;
            }
        }
        return false;
    }
}
