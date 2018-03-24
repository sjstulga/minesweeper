/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.view.factories;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import minesweeper.game.factories.BoardFactory;

/**
 *
 * @author sjstulga
 */
public class MenuSceneFactory {

    private static final BoardFactory BEGINNER = new BoardFactory(9, 9, 10);
    private static final BoardFactory INTERMEDIATE = new BoardFactory(16, 16, 40);
    private static final BoardFactory EXPERT = new BoardFactory(30, 16, 99);

    private final Stage stage;
    private GameSceneFactory gameSceneFactory;

    public MenuSceneFactory(Stage stage) {
        this.stage = stage;
    }

    public void setGameSceneFactory(GameSceneFactory gameSceneFactory) {
        this.gameSceneFactory = gameSceneFactory;
    }

    public Scene create() {
        AtomicReference<BoardFactory> boardFactory = new AtomicReference<>(BEGINNER);

        TextField columns = new TextField();
        columns.setDisable(true);

        TextField rows = new TextField();
        rows.setDisable(true);

        TextField mines = new TextField();
        mines.setDisable(true);

        ToggleGroup selector = new ToggleGroup();

        RadioButton beginner = new RadioButton("Beginner");
        beginner.setToggleGroup(selector);
        beginner.setOnAction((event) -> {
            boardFactory.set(BEGINNER);
            columns.setDisable(true);
            rows.setDisable(true);
            mines.setDisable(true);
        });

        RadioButton intermediate = new RadioButton("Intermediate");
        intermediate.setToggleGroup(selector);
        intermediate.setOnAction((event) -> {
            boardFactory.set(INTERMEDIATE);
            columns.setDisable(true);
            rows.setDisable(true);
            mines.setDisable(true);
        });

        RadioButton expert = new RadioButton("Expert");
        expert.setToggleGroup(selector);
        expert.setOnAction((event) -> {
            boardFactory.set(EXPERT);
            columns.setDisable(true);
            rows.setDisable(true);
            mines.setDisable(true);
        });

        RadioButton custom = new RadioButton("Custom");
        custom.setToggleGroup(selector);
        selector.selectToggle(beginner);
        custom.setOnAction((event) -> {
            boardFactory.set(null);
            columns.setDisable(false);
            rows.setDisable(false);
            mines.setDisable(false);
        });

        Button startButton = new Button();
        startButton.setText("Start");
        startButton.setOnAction((ActionEvent event) -> {
            Platform.runLater(() -> {
                Optional<BoardFactory> selectedBoardFactory = Optional.ofNullable(boardFactory.get());
                Scene gameScene = gameSceneFactory.create(selectedBoardFactory
                        .orElseGet(() -> new BoardFactory(
                        Integer.parseInt(columns.getText()),
                        Integer.parseInt(rows.getText()),
                        Integer.parseInt(mines.getText()))));
                stage.setScene(gameScene);
                stage.sizeToScene();
            });
        });

        GridPane root = new GridPane();
        root.setPadding(new Insets(15));
        root.setHgap(5);
        root.setVgap(5);
        root.setAlignment(Pos.CENTER);

        root.add(beginner, 0, 0);
        root.add(intermediate, 0, 1);
        root.add(expert, 0, 2);
        root.add(custom, 0, 3);

        root.add(new Label("Columns:"), 1, 3);
        root.add(columns, 2, 3);

        root.add(new Label("Rows:"), 1, 4);
        root.add(rows, 2, 4);

        root.add(new Label("Mines:"), 1, 5);
        root.add(mines, 2, 5);

        root.add(startButton, 0, 6);

        Scene scene = new Scene(root);

        return scene;
    }

}
