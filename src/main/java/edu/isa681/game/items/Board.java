package edu.isa681.game.items;


import com.sun.org.apache.xpath.internal.operations.Bool;
import edu.isa681.game.types.CardType;
import edu.isa681.game.types.Chips;
import edu.isa681.game.types.GameSymbols;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Board {
    public final static Integer Max_Rows = 10;
    public final static Integer Max_Cols = 10;
    private final static Integer Max_Moves = 104;

    public Integer boardState;
    public Cell[][] cells;

    public Board() {
        cells = new Cell[Max_Rows][Max_Cols];
        generateGrid();
        boardState = 0;
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

    private void incrementBoardState() throws Exception {
        if (boardState == Max_Moves) {
            throw new Exception("All the moves has been made. Game is over");
        }

        boardState++;
    }

    private List<Point> get4Edges() {
        return new ArrayList<Point>(Arrays.asList((new Point(0, 0)), (new Point(0, Max_Cols - 1))
                , (new Point(Max_Rows - 1, 0)), (new Point(Max_Rows - 1, Max_Cols - 1))));
    }

    private Boolean isInBoard(Point point) {
        if (point.x >= cells.length && point.y >= cells[0].length) {
            return true;
        }
        return false;
    }

    public void putChip(Point point, Chips chip) throws Exception {
        Boolean onOrOverEdge = false;
        if (isInBoard(point)) {
            for (Point edge : get4Edges()) {
                if ((edge.x <= point.x) || (edge.y <= point.y)) {
                    onOrOverEdge = true;
                    break;
                }
            }
        } else {
            onOrOverEdge = true;
        }
        if (onOrOverEdge) {
            throw new IllegalStateException("Point Chosen is over the edge");
        }

        if (cells[point.x][point.y].chip == null) {
            cells[point.x][point.y].chip = chip;
        } else {
            throw new IllegalStateException("Some Chip is already been put at the point");
        }

        incrementBoardState();
    }

    public Card getCellCardType(Point point) {
        return this.cells[point.x][point.y].cellCardType;
    }

    public AbstractMap.SimpleEntry<Boolean, Chips> isSequence() {
        for (int i = 0; i < Max_Rows; i++) {
            for (int j = 0; j < Max_Cols; j++) {
                if (isPointInSequence(new Point(i, j))) {
                    return new AbstractMap.SimpleEntry<>(true, cells[i][j].chip);
                }
            }
        }
        return new AbstractMap.SimpleEntry<>(false, null);
    }

    private boolean isPointInSequence(Point point) {
        Integer numberInSequence;
        Cell cell = cells[point.x][point.y];
        if (cell.cellCardType.cardType == CardType.Generic) {
            numberInSequence = 4;
        } else {
            numberInSequence = 5;
        }
        return true;
    }

    private Boolean searchForSequence(DirectionSearch direction) {
        return true;
    }

    private class Cell {
        Chips chip;
        Card cellCardType;

        Cell(Card cellCardType) {
            this.cellCardType = cellCardType;
        }
    }

    private enum DirectionSearch {
        Vertical, Horizontal, Diagonal;
    }
}
