package edu.isa681.card;

import java.util.ArrayList;

public class Deck {
    ArrayList<Card> cards;

    public Deck() {
        generateDeck();
    }

    private void generateDeck() {
        for (CardType cardType : CardType.values()) {
            for (CardValue cardValue : CardValue.values()) {
                Card card = new Card(cardType, cardValue);
                cards.add(card);
            }
        }
    }
}
