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
                }
            }
        }
    }

    public List<Cell> generateBoardSnapshot() {
        List<Cell> returnList = new ArrayList<Cell>();
        for (Cell[] cells : this.cells) {
            Collections.addAll(returnList, cells);
        }
        return returnList;
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

    private Boolean onOrOverEdge(Point point) {
        Boolean onOrOverEdge = false;
        if (isInBoard(point)) {
            for (Point edge : get4Edges()) {
                if (edge.equals(point)) {
                    onOrOverEdge = true;
                    break;
                }
            }
        } else {
            onOrOverEdge = true;
        }
        return onOrOverEdge;
    }

    public void putChip(Point point, Chips chip) {
        if (onOrOverEdge(point)) {
            throw new IllegalStateException("Point Chosen is not a valid point to put chip on the board");
        }

        if (getCellFromPoint(point).chip == null) {
            getCellFromPoint(point).chip = chip;
        } else {
            throw new IllegalStateException("Chip already exist at the point");
        }

    }

    public void removeChip(Point point) {
        if (onOrOverEdge(point)) {
            throw new IllegalStateException("Point Chosen is not a valid point to remove chip from the board");
        }

        getCellFromPoint(point).chip = null;
    }

    public Card getCellCardType(Point point) {
        return this.cells[point.x][point.y].cellType;
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
        if (cell.cellType.cardValue == GameSymbols.CornerPocket) {
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

    public class Cell {
        public Chips chip;
        final Card cellType;

        Cell(Card cellType) {
            this.cellType = cellType;
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
}
