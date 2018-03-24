/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.game;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongConsumer;
import minesweeper.game.factories.BoardFactory;
import minesweeper.game.model.Cell;
import minesweeper.core.model.Coordinate;

/**
 *
 * @author sjstulga
 */
public class Game {

    private final BoardFactory factory;
    private final Random random;

    private Board board;
    private Status status;

    private Timer clock;
    private AtomicLong time;
    private LongConsumer secondHandler;

    public Game(BoardFactory factory) {
        this(factory, new Random());
    }

    public Game(BoardFactory factory,
            Random random) {
        this.factory = factory;
        this.random = random;

        this.status = Status.NOT_STARTED;
        this.clock = new Timer();
    }

    public void setOnSecondHandler(LongConsumer secondHandler) {
        this.secondHandler = secondHandler;
    }

    public void start(Coordinate freeCell) {
        board = factory.createBoard(freeCell, random);
        board.setup(freeCell, random);

        status = Status.IN_PLAY;

        time = new AtomicLong(0);
        stopClock();
        clock.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                secondHandler.accept(time.getAndIncrement());
            }
        }, 0, 1000);
    }

    public void toggleFlag(Coordinate coordinate) {
        Cell cell = board.getCell(coordinate);

        switch (cell.getStatus()) {
            case HIDDEN:
                cell.setStatus(Cell.Status.FLAGGED);
                break;
            case FLAGGED:
                cell.setStatus(Cell.Status.HIDDEN);
            case REVEALED:
                break;
            default:
                throw new IllegalStateException("cell in unknown state " + cell.getStatus());
        }
    }

    public void reveal(Coordinate coordinate) {
        Cell cell = board.getCell(coordinate);
        switch (cell.getStatus()) {
            case HIDDEN:
                cell.setStatus(Cell.Status.REVEALED);
                switch (cell.getType()) {
                    case MINE:
                        lose();
                        break;
                    case CLEAR:
                        if (cell.getNeighborMines() == 0) {
                            board.getNeighbors(cell)
                                    .forEach((neighbor) -> {
                                        reveal(neighbor.getCoordinate());
                                    });
                        }
                        checkClearedWin();
                        break;
                    default:
                        throw new IllegalStateException("cell in unknown state " + cell.getStatus());
                }
            case REVEALED:
                int flaggedNeighbors = (int) board.getNeighbors(cell).stream().filter((neighbor) -> Cell.Status.FLAGGED.equals(neighbor.getStatus())).count();
                if (flaggedNeighbors == cell.getNeighborMines()) {
                    board.getNeighbors(cell)
                            .stream().filter((neighbor) -> Cell.Status.HIDDEN.equals(neighbor.getStatus()))
                            .forEach((neighbor) -> {
                                reveal(neighbor.getCoordinate());
                            });
                }
                break;
            case FLAGGED:
            default:
            // do nothing
        }
    }

    public Board getBoard() {
        return board;
    }

    public Status getStatus() {
        return status;
    }

    public static enum Status {
        NOT_STARTED,
        IN_PLAY,
        WIN,
        LOSE
    }

    private void checkClearedWin() {
        boolean win = board.map((cell, coordinate) -> {
            return Cell.Type.MINE.equals(cell.getType()) && !Cell.Status.REVEALED.equals(cell.getStatus())
                    || Cell.Type.CLEAR.equals(cell.getType()) && Cell.Status.REVEALED.equals(cell.getStatus());
        }).reduce(Boolean.TRUE, (result, element) -> {
            return result && element;
        });
        if (win) {
            win();
        }
    }

    private void win() {
        status = Status.WIN;
        board.forEach((cell, coordinate) -> {
            switch (cell.getType()) {
                case CLEAR:
                    cell.setStatus(Cell.Status.REVEALED);
                    break;
                case MINE:
                    cell.setStatus(Cell.Status.FLAGGED);
                    break;
            }
        });
        stopClock();
    }

    private void lose() {
        status = Status.LOSE;
        board.forEach((c, coord) -> {
            c.setStatus(Cell.Status.REVEALED);
        });
        stopClock();
    }

    private void stopClock() {
        clock.cancel();
        clock.purge();
        clock = new Timer();
    }
}
