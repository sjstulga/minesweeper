/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;
import minesweeper.core.model.Coordinate;

/**
 *
 * @author sjstulga
 */
public class TwoDimensionalArray {

    private TwoDimensionalArray() {
        // do nothing
    }

    public static <E> void forEach(E[][] array, TwoDimensionalArrayConsumer<E> function) {
        toStream(array).forEach((pair) -> {
            function.accept(pair.getElement(), pair.getCoordinate());
        });
    }

    public static <E, R> Stream<R> map(E[][] array, TwoDimensionalArrayFunction<E, R> function) {
        return toStream(array).map((pair) -> {
            return function.call(pair.getElement(), pair.getCoordinate());
        });
    }

    public static <E> void setEach(E[][] array, TwoDimensionalArrayFunction<E, E> function) {
        toStream(array).forEach((pair) -> {
            Coordinate coordinate = pair.getCoordinate();
            array[coordinate.getColumn()][coordinate.getRow()]
                    = function.call(pair.getElement(), coordinate);
        });
    }

    public static <E> Stream<E> filter(E[][] array, TwoDimensionalArrayPredicate<E> predicate) {
        return toStream(array).filter((pair) -> {
            return predicate.test(pair.getElement(), pair.getCoordinate());
        }).map((pair) -> {
            return pair.getElement();
        });
    }

    private static <E> Stream<ElementAndCoordinate<E>> toStream(E[][] array) {
        assert array != null && array[0] != null;
        Collection<ElementAndCoordinate<E>> collection = new ArrayList<>(array.length * array[0].length);
        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array[0].length; y++) {
                collection.add(new ElementAndCoordinate(array[x][y], new Coordinate(x, y)));
            }
        }
        return collection.stream();
    }

    private static class ElementAndCoordinate<E> {

        private final E element;
        private final Coordinate coordinate;

        public ElementAndCoordinate(E element, Coordinate coordinate) {
            this.element = element;
            this.coordinate = coordinate;
        }

        public E getElement() {
            return element;
        }

        public Coordinate getCoordinate() {
            return coordinate;
        }
    }
}
