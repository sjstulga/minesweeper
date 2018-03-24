/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.view;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import minesweeper.core.model.Coordinate;
import minesweeper.game.Game;
import static minesweeper.game.Game.Status.IN_PLAY;
import static minesweeper.game.Game.Status.LOSE;
import static minesweeper.game.Game.Status.NOT_STARTED;
import static minesweeper.game.Game.Status.WIN;

/**
 *
 * @author sjstulga
 */
public class GameBoardButton extends Button {

    private static final Font MONOSPACED = Font.font("Monospaced");

    private final Game game;
    private final Coordinate coordinate;
    private final EventHandler<? super MouseEvent> clickHandler;

    public GameBoardButton(Game game, Coordinate coordinate, EventHandler<? super MouseEvent> clickHandler) {
        super(" ");

        this.game = game;
        this.coordinate = coordinate;
        this.clickHandler = clickHandler;
    }

    public void init() {
        setFocusTraversable(false);
        setFont(MONOSPACED);
        setOnMouseClicked(this::doMouseClick);
    }

    public Game getGame() {
        return game;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void doMouseClick(MouseEvent event) {
        switch (game.getStatus()) {
            case NOT_STARTED:
            case IN_PLAY:
                switch (event.getButton()) {
                    case PRIMARY:
                        switch (game.getStatus()) {
                            case NOT_STARTED:
                                game.start(coordinate);
                            case IN_PLAY:
                                game.reveal(coordinate);
                                break;
                            default:
                            // do nothing
                            }
                        break;
                    case SECONDARY:
                        if (Game.Status.IN_PLAY.equals(game.getStatus())) {
                            game.toggleFlag(coordinate);
                        }
                        break;
                }
                break;
            case WIN:
            case LOSE:
                // do nothing
                break;
            default:
                throw new IllegalStateException("game is unknown state " + game.getStatus());
        }

        clickHandler.handle(event);
        event.consume();
    }
}
