/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.view;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import static javafx.scene.input.MouseButton.PRIMARY;
import static javafx.scene.input.MouseButton.SECONDARY;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import minesweeper.core.model.Coordinate;
import minesweeper.game.Game;

/**
 *
 * @author sjstulga
 */
public class CellButton extends Button {

    private final Game game;
    private final Coordinate coordinate;

    public CellButton(Game game, Coordinate coordinate, EventHandler<? super MouseEvent> handler) {
        this.game = game;
        this.coordinate = coordinate;

        setFocusTraversable(false);
        setFont(Font.font("Monospaced"));
        setText(" ");
        setOnMouseClicked((event) -> {
            click(event);
            handler.handle(event);
        });
    }

    private void click(MouseEvent event) {
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
        event.consume();
    }
}
