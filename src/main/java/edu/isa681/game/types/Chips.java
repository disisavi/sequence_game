package edu.isa681.game.types;

import java.util.EnumSet;
import java.util.Set;

public enum Chips {
    Red(50), Blue(50), Green(35);

    Chips(int i) {
    }

    public static Set<Chips> availableChips() {
        return EnumSet.allOf(Chips.class);
    }
}
