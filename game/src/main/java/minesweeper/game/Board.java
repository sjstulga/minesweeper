package minesweeper.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;
import minesweeper.core.TwoDimensionalArray;
import minesweeper.core.TwoDimensionalArrayConsumer;
import minesweeper.core.TwoDimensionalArrayFunction;
import minesweeper.game.factories.RandomCoordinateFactory;
import minesweeper.game.model.Cell;
import minesweeper.core.model.Coordinate;

/**
 *
 * @author sjstulga
 */
public class Board {

    private final int columns;
    private final int rows;
    private final int mines;
    private final Cell[][] board;

    public Board(int columns, int rows, int mines) {
        assert columns > 0 && rows > 0;
        assert mines > 0 && mines < columns * rows;

        this.columns = columns;
        this.rows = rows;
        this.mines = mines;
        this.board = new Cell[columns][rows];
    }

    public void setup(Coordinate freeCell) {
        setup(freeCell, new Random());
    }

    public void setup(Coordinate freeCell, Random random) {
        assert freeCell.getColumn() >= 0 && freeCell.getColumn() < columns;
        assert freeCell.getRow() >= 0 && freeCell.getRow() < rows;

        clear();
        placeMines(freeCell, random);
        countMines();
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public void setCell(Cell cell) {
        Coordinate coordinate = cell.getCoordinate();
        board[coordinate.getColumn()][coordinate.getRow()] = cell;
    }

    public Cell getCell(Coordinate coordinate) {
        return board[coordinate.getColumn()][coordinate.getRow()];
    }

    public Collection<Cell> getNeighbors(Cell cell) {
        Collection<Cell> neighbors = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            getNeighbor(cell, direction)
                    .ifPresent(neighbors::add);
        }
        return neighbors;
    }

    public <R> Stream<R> map(TwoDimensionalArrayFunction<Cell, R> function) {
        return TwoDimensionalArray.map(board, function);
    }

    public void forEach(TwoDimensionalArrayConsumer<Cell> consumer) {
        TwoDimensionalArray.forEach(board, consumer);
    }

    public int getRemainingFlags() {
        return mines - (int) TwoDimensionalArray.filter(board, (cell, coordinate) -> {
            return Cell.Status.FLAGGED.equals(cell.getStatus());
        }).count();
    }

    private void placeMines(Coordinate freeCell, Random random) {
        RandomCoordinateFactory coordinateFactory = new RandomCoordinateFactory(random);

        for (int i = 0; i < mines; i++) {
            Coordinate coordinate = coordinateFactory
                    .createCoordinate(columns, rows);

            if (coordinate.equals(freeCell)) {
                i--;
                continue;
            }

            if (getCell(coordinate) != null) {
                i--;
                continue;
            }

            setCell(new Cell(coordinate, Cell.Type.MINE));
        }
    }

    private void countMines() {
        TwoDimensionalArray.setEach(board, (cell, coordinate) -> {
            if (cell == null) {
                cell = new Cell(coordinate, Cell.Type.CLEAR);
            }

            for (Cell neighbor : getNeighbors(cell)) {
                if (Cell.Type.MINE.equals(neighbor.getType())) {
                    cell.setNeighborMines(
                            cell.getNeighborMines() + 1);
                }
            }

            return cell;
        });
    }

    private Optional<Cell> getNeighbor(Cell cell, Direction direction) {
        Coordinate coordinate = cell.getCoordinate();

        assert coordinate.getColumn() >= 0 && coordinate.getColumn() < columns;
        assert coordinate.getRow() >= 0 && coordinate.getRow() < rows;

        Coordinate neighborCoordinate = new Coordinate();
        switch (direction) {
            case SOUTH_WEST:
            case WEST:
            case NORTH_WEST:
                neighborCoordinate.setColumn(coordinate.getColumn() - 1);
                break;
            case NORTH:
            case SOUTH:
                neighborCoordinate.setColumn(coordinate.getColumn());
                break;
            case NORTH_EAST:
            case EAST:
            case SOUTH_EAST:
                neighborCoordinate.setColumn(coordinate.getColumn() + 1);
                break;
            default:
                throw new IllegalArgumentException("unknown direction " + direction);
        }

        switch (direction) {
            case NORTH:
            case NORTH_EAST:
            case NORTH_WEST:
                neighborCoordinate.setRow(coordinate.getRow() - 1);
                break;
            case EAST:
            case WEST:
                neighborCoordinate.setRow(coordinate.getRow());
                break;
            case SOUTH_EAST:
            case SOUTH:
            case SOUTH_WEST:
                neighborCoordinate.setRow(coordinate.getRow() + 1);
                break;
            default:
                throw new IllegalArgumentException("unknown direction " + direction);
        }

        if (neighborCoordinate.getColumn() < 0 || neighborCoordinate.getColumn() >= columns
                || neighborCoordinate.getRow() < 0 || neighborCoordinate.getRow() >= rows) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(getCell(neighborCoordinate));
        }
    }

    private void clear() {
        TwoDimensionalArray.setEach(board, (cell, coordinate) -> null);
    }

    private enum Direction {
        NORTH,
        NORTH_EAST,
        EAST,
        SOUTH_EAST,
        SOUTH,
        SOUTH_WEST,
        WEST,
        NORTH_WEST
    }
}
