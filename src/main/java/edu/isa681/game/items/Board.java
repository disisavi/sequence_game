package edu.isa681.game.items;


import edu.isa681.game.types.CardType;
import edu.isa681.game.types.Chips;
import edu.isa681.game.types.GameSymbols;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Board {
    static final Logger log = Logger.getLogger(Board.class);
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

    /***
     *
     * @return List of edges starting for the bottom left corner in a clock wise direction
     */
    private List<Point> get4Edges() {
        return new ArrayList<Point>(Arrays.asList((new Point(0, 0)), (new Point(0, Max_Cols - 1))
                , (new Point(Max_Rows - 1, 0)), (new Point(Max_Rows - 1, Max_Cols - 1))));
    }

    private Boolean isInBoard(Point point) {
        return point.x >= 0 && point.x < Max_Cols && point.y >= 0 && point.y < Max_Rows;
    }

    public void putChip(Point point, Chips chip) throws Exception {
        Boolean onOrOverEdge = false;
        if (isInBoard(point)) {
            for (Point edge : get4Edges()) {
                if (edge.equals(point)) {
                    onOrOverEdge = true;
                }
            }
        } else {
            onOrOverEdge = true;
        }
        if (onOrOverEdge) {
            throw new IllegalStateException("Point Chosen is over the edge");
        }

        if (

                getCellFromPoint(point).chip == null) {
            getCellFromPoint(point).chip = chip;
        } else {
            throw new IllegalStateException("Some Chip is already been put at the point");
        }

        incrementBoardState();
    }

    public Card getCellCardType(Point point) {
        return this.cells[point.x][point.y].cellCardType;
    }

    Cell getCellFromPoint(Point point) {
        return cells[point.x][point.y];
    }

    public AbstractMap.SimpleEntry<Boolean, Chips> isSequence() {
        log.info("Is sequence started");
        for (int i = 0; i < Max_Rows; i++) {
            for (int j = 0; j < Max_Cols; j++) {
                log.info("checking for point " + new Point(i, j));
                if (isPointInSequence(new Point(i, j))) {
                    return new AbstractMap.SimpleEntry<>(true, getCellFromPoint(new Point(i, j)).chip);
                }
            }
        }
        return new AbstractMap.SimpleEntry<>(false, null);
    }

    private boolean isPointInSequence(Point point) {
        Integer numberInSequence;
        Cell cell = getCellFromPoint(point);
        if (cell.cellCardType.cardValue == GameSymbols.CornerPocket) {
            numberInSequence = 4;
        } else {
            numberInSequence = 5;
        }

        return searchForSequence(point, DirectionSearch.Vertical, numberInSequence) || searchForSequence(point, DirectionSearch.Horizontal, numberInSequence) || searchForSequence(point, DirectionSearch.Diagonal, numberInSequence);
    }

    private Boolean searchForSequence(Point point, DirectionSearch direction, Integer numberInSequence) {
        Point nextPoint = new Point(point.x + direction.point().x, point.y + direction.point().y);
        if (isInBoard(nextPoint)) {
            if (null != getCellFromPoint(point).chip
                    && (getCellFromPoint(point).chip == getCellFromPoint(nextPoint).chip || getCellCardType(nextPoint).cardValue == GameSymbols.CornerPocket)) {
                numberInSequence--;
                if (numberInSequence == 1) {
                    return true;
                } else return searchForSequence(nextPoint, direction, numberInSequence);
            }
        }
        return false;
    }

    class Cell {
        Chips chip;
        final Card cellCardType;

        Cell(Card cellCardType) {
            this.cellCardType = cellCardType;
        }
    }

    private enum DirectionSearch {
        Vertical(new Point(0, 1)), Horizontal(new Point(1, 0)), Diagonal(new Point(1, 1));
        private final Point point;

        DirectionSearch(Point point) {
            this.point = point;
        }

        public Point point() {
            return point;
        }
    }

    /*
     * TODO
     * 1. Please finish the search algorithm of the board
     * 1. Write tests
     */
}
