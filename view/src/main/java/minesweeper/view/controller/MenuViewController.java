/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.view.controller;

import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import minesweeper.game.factories.BoardFactory;
import minesweeper.view.GameView;
import minesweeper.view.MenuView;

/**
 *
 * @author sjstulga
 */
public class MenuViewController {

    private static final BoardFactory BEGINNER = new BoardFactory(9, 9, 10);
    private static final BoardFactory INTERMEDIATE = new BoardFactory(16, 16, 40);
    private static final BoardFactory EXPERT = new BoardFactory(30, 16, 99);

    private final Stage stage;
    private final MenuView view;

    public MenuViewController(Stage stage, MenuView view) {
        this.view = view;
        this.stage = stage;
    }

    public void init() {
        view.getBeginner().setToggleGroup(view.getDifficulty());
        view.getIntermediate().setToggleGroup(view.getDifficulty());
        view.getExpert().setToggleGroup(view.getDifficulty());
        view.getCustom().setToggleGroup(view.getDifficulty());

        view.getBeginner().setOnAction(this::doBeginnerAction);
        view.getIntermediate().setOnAction(this::doIntermediateAction);
        view.getExpert().setOnAction(this::doExpertAction);
        view.getCustom().setOnAction(this::doCustomAction);

        view.getStart().setOnAction(this::doStartAction);

        view.getRoot().add(view.getBeginner(), 0, 0);
        view.getRoot().add(view.getIntermediate(), 0, 1);
        view.getRoot().add(view.getExpert(), 0, 2);
        view.getRoot().add(view.getCustom(), 0, 3);

        view.getRoot().add(view.getColumnsLabel(), 1, 3);
        view.getRoot().add(view.getColumns(), 2, 3);

        view.getRoot().add(view.getRowsLabel(), 1, 4);
        view.getRoot().add(view.getRows(), 2, 4);

        view.getRoot().add(view.getMinesLabel(), 1, 5);
        view.getRoot().add(view.getMines(), 2, 5);

        view.getRoot().add(view.getStart(), 0, 6);

        view.getRoot().setPadding(new Insets(15));
        view.getRoot().setHgap(5);
        view.getRoot().setVgap(5);

        view.getBeginner().fire();
    }

    public void display() {
        stage.setScene(view.getScene());
        stage.sizeToScene();
    }

    public MenuView getView() {
        return view;
    }

    public void doBeginnerAction(ActionEvent event) {
        view.setBoardFactory(BEGINNER);
        setCustomBoardFieldsDisable(true);
    }

    public void doIntermediateAction(ActionEvent event) {
        view.setBoardFactory(INTERMEDIATE);
        setCustomBoardFieldsDisable(true);
    }

    public void doExpertAction(ActionEvent event) {
        view.setBoardFactory(EXPERT);
        setCustomBoardFieldsDisable(true);
    }

    public void doCustomAction(ActionEvent event) {
        view.setBoardFactory(null);
        setCustomBoardFieldsDisable(false);
    }

    public void doStartAction(ActionEvent event) {
        Platform.runLater(() -> {
            BoardFactory boardFactory = Optional.ofNullable(view.getBoardFactory())
                    .orElseGet(this::createCustomBoardFactory);
            GameView gameViewModel = new GameView(boardFactory);
            GameViewController gameViewController = new GameViewController(stage, gameViewModel);

            gameViewController.init();

            gameViewController.display();
        });
    }

    public void setCustomBoardFieldsDisable(boolean disable) {
        view.getColumns().setDisable(disable);
        view.getRows().setDisable(disable);
        view.getMines().setDisable(disable);
    }

    public BoardFactory createCustomBoardFactory() {
        return new BoardFactory(
                Integer.parseInt(view.getColumns().getText()),
                Integer.parseInt(view.getRows().getText()),
                Integer.parseInt(view.getMines().getText()));
    }
}
