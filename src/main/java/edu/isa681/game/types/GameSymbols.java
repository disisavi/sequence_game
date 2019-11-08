package edu.isa681.game.types;


import java.util.EnumSet;
import java.util.Set;

public enum GameSymbols {
    Ace,
    Two,
    Three,
    Four,
    Five,
    Six,
    Seven,
    Eight,
    Nine,
    Ten,
    JackOneEye,
    JackTwoEye,
    Queen,
    King,
    CornerPocket;

    public static Set<GameSymbols> allowedInDeck() {
        return EnumSet.complementOf(EnumSet.of(GameSymbols.JackOneEye, GameSymbols.JackTwoEye, GameSymbols.CornerPocket));
    }
    public Set<GameSymbols> allowedInBoard() {
        return EnumSet.complementOf(EnumSet.of(GameSymbols.JackOneEye, GameSymbols.JackTwoEye));
    }
}
