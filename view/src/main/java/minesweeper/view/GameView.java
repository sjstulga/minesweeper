/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import minesweeper.game.Game;
import minesweeper.game.factories.BoardFactory;

/**
 *
 * @author sjstulga
 */
public class GameView {

    private final BoardFactory boardFactory;
    private final Game game;

    private final VBox root;
    private final HBox topBar;

    private final Label flagsLabel;
    private final Label secondsLabel;

    private final Label flags;
    private final Label seconds;

    private final Button menu;

    private final GridPane boardGrid;

    private final GameBoardButton[][] buttons;

    private final Scene scene;

    public GameView(BoardFactory boardFactory) {
        this.boardFactory = boardFactory;

        game = new Game(boardFactory);

        root = new VBox();
        topBar = new HBox();

        flagsLabel = new Label("Flags:");
        secondsLabel = new Label("Seconds:");

        flags = new Label(String.valueOf(boardFactory.getMines()));
        seconds = new Label("0");

        menu = new Button("Menu");

        boardGrid = new GridPane();

        buttons = new GameBoardButton[boardFactory.getColumns()][boardFactory.getRows()];

        scene = new Scene(root);
    }

    public BoardFactory getBoardFactory() {
        return boardFactory;
    }

    public Game getGame() {
        return game;
    }

    public VBox getRoot() {
        return root;
    }

    public HBox getTopBar() {
        return topBar;
    }

    public Label getFlagsLabel() {
        return flagsLabel;
    }

    public Label getSecondsLabel() {
        return secondsLabel;
    }

    public Label getFlags() {
        return flags;
    }

    public Label getSeconds() {
        return seconds;
    }

    public Button getMenu() {
        return menu;
    }

    public GridPane getBoardGrid() {
        return boardGrid;
    }

    public GameBoardButton[][] getButtons() {
        return buttons;
    }

    public Scene getScene() {
        return scene;
    }
}
