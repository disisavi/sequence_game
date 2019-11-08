package edu.isa681.game.items;


import edu.isa681.game.types.CardType;
import edu.isa681.game.types.Chips;
import edu.isa681.game.types.GameSymbols;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    public final static Integer Max_Rows = 10;
    public final static Integer Max_Cols = 10;
    public Cell[][] cells;

    public Board() {
        cells = new Cell[Max_Rows][Max_Cols];
        generateGrid();
    }

    private void generateGrid() {
        List<Card> boardSequence = getBoardSequence();
        cells[0][0] = new Cell(new Card(CardType.Generic, GameSymbols.CornerPocket));
        cells[0][Max_Cols - 1] = new Cell(new Card(CardType.Generic, GameSymbols.CornerPocket));
        cells[Max_Rows - 1][Max_Cols - 1] = new Cell(new Card(CardType.Generic, GameSymbols.CornerPocket));
        cells[Max_Rows - 1][0] = new Cell(new Card(CardType.Generic, GameSymbols.CornerPocket));
        for (int i = 0; i < cells.length; i++) {

            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j] == null && boardSequence.size() > 0) {
                    cells[i][j] = new Cell(boardSequence.remove(0));
                } else {
                    continue;
                }
            }
        }
    }

    private List<Card> getBoardSequence() {
        List<Card> boardSequence = new ArrayList<>();
        for (int __i = 0; __i < 2; __i++) {
            for (CardType type : CardType.allowedInDeck()) {
                GameSymbols.allowedInDeck().forEach(e -> boardSequence.add(new Card(type, e)));
            }
        }
        Collections.shuffle(boardSequence);
        return boardSequence;
    }

    private class Cell {
        Card card;
        Chips chip;
        Card cellCardType;

        Cell(Card cellCardType) {
            this.cellCardType = cellCardType;
        }
    }
}
