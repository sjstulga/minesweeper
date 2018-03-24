/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.view.factories;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import minesweeper.core.TwoDimensionalArray;
import minesweeper.game.Game;
import minesweeper.game.factories.BoardFactory;
import minesweeper.game.model.Cell;
import minesweeper.view.CellButton;

/**
 *
 * @author sjstulga
 */
public class GameSceneFactory {

    private final Stage stage;
    private MenuSceneFactory menuSceneFactory;

    public GameSceneFactory(Stage stage) {
        this.stage = stage;
    }

    public void setMenuSceneFactory(MenuSceneFactory menuSceneFactory) {
        this.menuSceneFactory = menuSceneFactory;
    }

    public Scene create(BoardFactory boardFactory) {
        Game game = new Game(boardFactory);

        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);

        HBox topBar = new HBox();
        topBar.setSpacing(10);
        topBar.setAlignment(Pos.CENTER);

        topBar.getChildren().add(new Label("Flags:"));
        Label flags = new Label(String.valueOf(boardFactory.getMines()));
        topBar.getChildren().add(flags);

        Button menu = new Button();
        menu.setFont(Font.font("Monospaced"));
        menu.setText("Menu");
        menu.setOnAction((event) -> {
            Platform.runLater(() -> {
                Scene menuScene = menuSceneFactory.create();
                stage.setScene(menuScene);
                stage.sizeToScene();
            });
        });
        topBar.getChildren().add(menu);

        topBar.getChildren().add(new Label("Seconds:"));
        Label timer = new Label("0");
        topBar.getChildren().add(timer);

        game.setOnSecondHandler((time) -> {
            Platform.runLater(() -> {
                timer.setText(String.valueOf(time));
            });
        });

        GridPane grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(2);
        grid.setAlignment(Pos.CENTER);

        Button[][] buttons = new Button[boardFactory.getColumns()][boardFactory.getRows()];
        TwoDimensionalArray.setEach(buttons, (button, coordinate) -> {
            button = new CellButton(game, coordinate, (event) -> {
                if (!Game.Status.NOT_STARTED.equals(game.getStatus())) {
                    refresh(game, menu, flags, buttons);
                }
            });

            grid.add(button, coordinate.getColumn(), coordinate.getRow() + 1);
            return button;
        }
        );

        root.getChildren()
                .add(topBar);
        root.getChildren()
                .add(grid);

        Scene scene = new Scene(root);
        return scene;
    }

    public void refresh(Game game, Button menu, Label flags, Button[][] cells) {
        Platform.runLater(() -> {
            flags.setText(String.valueOf(game.getBoard().getRemainingFlags()));

            switch (game.getStatus()) {
                case NOT_STARTED:
                case IN_PLAY:
                    menu.setText("Quit");
                    break;
                case WIN:
                    menu.setText(" :) ");
                    break;
                case LOSE:
                    menu.setText(" :( ");
                    break;
            }

            TwoDimensionalArray.forEach(cells, (button, coordinate) -> {
                Cell cell = game.getBoard().getCell(coordinate);
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
            });
        });
    }
}
