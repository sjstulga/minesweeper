/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.core;

import minesweeper.core.model.Coordinate;

/**
 *
 * @author sjstulga
 */
@FunctionalInterface
public interface TwoDimensionalArrayFunction<E, R> { 
    
    R call(E element, Coordinate coordinate);
}
