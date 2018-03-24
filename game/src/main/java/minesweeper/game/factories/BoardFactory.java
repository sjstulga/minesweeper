/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.game.factories;

import java.util.Random;
import minesweeper.core.model.Coordinate;
import minesweeper.game.Board;

/**
 *
 * @author sjstulga
 */
public class BoardFactory {

    private int columns;
    private int rows;
    private int mines;

    public BoardFactory(int columns, int rows, int mines) {
        this.columns = columns;
        this.rows = rows;
        this.mines = mines;
    }

    public Board createBoard(Random random) {
        return createBoard(
                new Coordinate(
                        random.nextInt(columns),
                        random.nextInt(rows)),
                random);
    }

    public Board createBoard(Coordinate freeCell, Random random) {
        assert freeCell.getColumn() >= 0 && freeCell.getColumn() < columns;
        assert freeCell.getRow() >= 0 && freeCell.getRow() < rows;

        return new Board(columns, rows, mines);
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        assert columns > 0;
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        assert rows > 0;
        this.rows = rows;
    }

    public int getMines() {
        return mines;
    }

    public void setMines(int mines) {
        this.mines = mines;
    }

}
