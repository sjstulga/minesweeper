/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.view.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import minesweeper.core.TwoDimensionalArray;
import minesweeper.core.model.Coordinate;
import static minesweeper.game.Game.Status.IN_PLAY;
import static minesweeper.game.Game.Status.LOSE;
import static minesweeper.game.Game.Status.NOT_STARTED;
import static minesweeper.game.Game.Status.WIN;
import minesweeper.game.model.Cell;
import minesweeper.view.GameBoardButton;
import minesweeper.view.GameView;
import minesweeper.view.MenuView;

/**
 *
 * @author sjstulga
 */
public class GameViewController {

    private static final Font MONOSPACED = Font.font("Monospaced");

    private final Stage stage;
    private final GameView view;

    public GameViewController(Stage stage, GameView view) {
        this.stage = stage;
        this.view = view;
    }

    public void init() {
        view.getRoot().setAlignment(Pos.TOP_CENTER);
        view.getTopBar().setSpacing(10);
        view.getTopBar().setAlignment(Pos.CENTER);

        view.getMenu().setFont(MONOSPACED);
        view.getMenu().setOnAction(this::doMenuAction);

        view.getGame().setOnSecondHandler(this::doClockTick);

        view.getBoardGrid().setHgap(2);
        view.getBoardGrid().setVgap(2);
        view.getBoardGrid().setAlignment(Pos.CENTER);

        TwoDimensionalArray.setEach(view.getButtons(), (button, coordinate) -> {
            button = new GameBoardButton(view.getGame(), coordinate, (event) -> {
                refresh();
            });
            button.init();
            view.getBoardGrid().add(button, coordinate.getColumn(), coordinate.getRow());
            return button;
        });

        view.getTopBar().getChildren().add(view.getFlagsLabel());
        view.getTopBar().getChildren().add(view.getFlags());
        view.getTopBar().getChildren().add(view.getMenu());
        view.getTopBar().getChildren().add(view.getSecondsLabel());
        view.getTopBar().getChildren().add(view.getSeconds());

        view.getRoot().getChildren().add(view.getTopBar());
        view.getRoot().getChildren().add(view.getBoardGrid());
    }

    public void display() {
        stage.setScene(view.getScene());
        stage.sizeToScene();
    }

    public GameView getView() {
        return view;
    }

    public void doMenuAction(ActionEvent event) {
        Platform.runLater(() -> {
            MenuView menuViewModel = new MenuView();
            MenuViewController menuViewController = new MenuViewController(stage, menuViewModel);

            menuViewController.init();

            menuViewController.display();
        });
    }

    public void doClockTick(long time) {
        Platform.runLater(() -> {
            view.getSeconds().setText(String.valueOf(time));
        });
    }

    public void refresh() {
        Platform.runLater(() -> {
            refreshFlags();
            refreshMenu();
            TwoDimensionalArray.forEach(view.getButtons(),
                    this::refreshButton);
        });
    }

    public void refreshFlags() {
        int remainingFlags = view.getGame().getBoard().getRemainingFlags();
        view.getFlags().setText(String.valueOf(remainingFlags));
    }

    public void refreshMenu() {
        switch (view.getGame().getStatus()) {
            case NOT_STARTED:
            case IN_PLAY:
                view.getMenu().setText("Quit");
                break;
            case WIN:
                view.getMenu().setText(" :) ");
                break;
            case LOSE:
                view.getMenu().setText(" :( ");
                break;
        }
    }

    public void refreshButton(Button button, Coordinate coordinate) {
        Cell cell = view.getGame().getBoard().getCell(coordinate);
        switch (cell.getStatus()) {
            case HIDDEN:
                button.setText(" ");
                break;
            case FLAGGED:
                button.setText("\u2691");
                break;
            case REVEALED:
                switch (cell.getType()) {
                    case MINE:
                        button.setText("\u2738");
                        break;
                    case CLEAR:
                        if (cell.getNeighborMines() == 0) {
                            button.setText(" ");
                            button.setDisable(true);
                        } else {
                            button.setText(String.valueOf(cell.getNeighborMines()));
                        }
                        break;
                }
                break;
            default:
                throw new IllegalStateException("cell in unknown state " + cell.getStatus());
        }
    }

}
