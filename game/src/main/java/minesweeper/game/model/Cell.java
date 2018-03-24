package minesweeper.game.model;

import minesweeper.core.model.Coordinate;

/**
 *
 * @author sjstulga
 */
public class Cell {

    private final Coordinate coordinate;
    private final Type type;

    private int neighborMines;
    private Status status;

    public Cell(Coordinate coordinate, Type contentType) {
        this.coordinate = coordinate;
        this.type = contentType;

        this.status = Status.HIDDEN;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Type getType() {
        return type;
    }

    public int getNeighborMines() {
        return neighborMines;
    }

    public void setNeighborMines(int neighborMines) {
        this.neighborMines = neighborMines;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public static enum Status {
        HIDDEN,
        REVEALED,
        FLAGGED
    }

    public static enum Type {
        MINE,
        CLEAR
    }
}
