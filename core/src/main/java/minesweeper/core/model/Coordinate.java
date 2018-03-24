/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.core.model;

/**
 *
 * @author sjstulga
 */
public class Coordinate {

    private int column;
    private int row;

    public Coordinate() {
        this(0, 0);
    }

    public Coordinate(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public void setColumn(int c) {
        this.column = c;
    }

    public int getColumn() {
        return column;
    }

    public void setRow(int r) {
        this.row = r;
    }

    public int getRow() {
        return row;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.column;
        hash = 97 * hash + this.row;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coordinate other = (Coordinate) obj;
        if (this.column != other.column) {
            return false;
        }
        if (this.row != other.row) {
            return false;
        }
        return true;
    }
}
