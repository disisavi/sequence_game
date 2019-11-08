package edu.isa681.game.types;

import java.util.EnumSet;
import java.util.Set;

public enum CardType {
    Hearts, Diamonds, Clubs, Spades,Generic;

    public static Set<CardType> allowedInDeck() {
        return EnumSet.complementOf(EnumSet.of(CardType.Generic));
    }
}
