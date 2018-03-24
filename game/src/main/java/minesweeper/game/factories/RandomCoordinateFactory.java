/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.game.factories;

import java.util.Random;
import minesweeper.core.model.Coordinate;

/**
 *
 * @author sjstulga
 */
public class RandomCoordinateFactory {

    private final Random random;

    public RandomCoordinateFactory() {
        this(new Random());
    }

    public RandomCoordinateFactory(Random random) {
        this.random = random;
    }

    public Coordinate createCoordinate(int columnBound, int rowBound) {
        return new Coordinate(
            random.nextInt(columnBound),
            random.nextInt(rowBound));
    }
    
}
