package edu.isa681.game.items;

import edu.isa681.game.types.CardType;
import edu.isa681.game.types.GameSymbols;
import org.apache.log4j.Logger;

import java.util.*;

public class Deck {
    private ArrayList<Card> cards;
    private Set<Card> cardsDealt;
    static Logger log = Logger.getLogger(Deck.class);


    public Deck() {
        cards = new ArrayList<>();
        generateDeck();
        shuffleDeck();
        cardsDealt = new HashSet<>();
    }

    /**
     * Generates the deck which will be used to play the game. The deck for this game is twice the size of the deck used for
     * traditional card games
     */
    private void generateDeck() {

        for (CardType cardType : CardType.allowedInDeck()) {
            for (int i = 0; i < 2; i++) {
                for (GameSymbols gameSymbols : GameSymbols.allowedInDeck()) {
                    Card card = new Card(cardType, gameSymbols);
                    cards.add(card);
                }

            }
            cards.add(new Card(cardType, GameSymbols.JackOneEye));
            cards.add(new Card(cardType, GameSymbols.JackTwoEye));
        }

        if (this.cards.size() != 104) {
            throw new IllegalStateException("Cards not initiated properly." + this.cards.size());
        }
    }


    public Deck shuffleDeck() {
        Collections.shuffle(cards);
        return this;
    }

    public Card dealCard() {
        Card dealtCard = cards.remove(0);
        cardsDealt.add(dealtCard);
        return dealtCard;
    }

    public List<Card> getCards() {
        return cards;
    }

}
