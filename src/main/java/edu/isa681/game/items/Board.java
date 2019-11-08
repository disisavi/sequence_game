package edu.isa681.game.items;


import edu.isa681.game.types.CardType;
import edu.isa681.game.types.Chips;
import edu.isa681.game.types.GameSymbols;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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

    private List<Point> get4Edges() {
        return new ArrayList<Point>(Arrays.asList((new Point(0, 0)), (new Point(0, Max_Cols - 1))
                , (new Point(Max_Rows - 1, 0)), (new Point(Max_Rows - 1, Max_Cols - 1))));
    }

    public void putChip(Point point, Chips chip) {
        Boolean onOrOverEdge = false;
        for (Point edge : get4Edges()) {
            if ((edge.x <= point.x) || (edge.y <= point.y)) {
                onOrOverEdge = true;
                break;
            }
        }
        if (onOrOverEdge) {
            throw new IllegalStateException("Point Chosen is over the edge");
        }

        if (cells[point.x][point.y].chip == null) {
            cells[point.x][point.y].chip = chip;
        } else {
            throw new IllegalStateException("Some Chip is already been put at the point");
        }
    }

    public Card getCellCardType(Point point){
        return this.cells[point.x][point.y].cellCardType;
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
