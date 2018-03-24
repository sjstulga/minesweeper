/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import minesweeper.game.factories.BoardFactory;

/**
 *
 * @author sjstulga
 */
public class MenuView {

    private final TextField columns;
    private final TextField rows;
    private final TextField mines;

    private final ToggleGroup difficulty;
    private final RadioButton beginner;
    private final RadioButton intermediate;
    private final RadioButton expert;
    private final RadioButton custom;

    private final Button start;

    private final Label columnsLabel;
    private final Label rowsLabel;
    private final Label minesLabel;

    private final GridPane root;

    private final Scene scene;

    private BoardFactory boardFactory;

    public MenuView() {
        columns = new TextField();
        rows = new TextField();
        mines = new TextField();

        difficulty = new ToggleGroup();

        beginner = new RadioButton("Beginner");
        intermediate = new RadioButton("Intermediate");
        expert = new RadioButton("Expert");
        custom = new RadioButton("Custom");

        start = new Button("Start");

        columnsLabel = new Label("Columns:");
        rowsLabel = new Label("Rows:");
        minesLabel = new Label("Mines:");

        root = new GridPane();

        scene = new Scene(root);

        boardFactory = null;
    }

    public TextField getColumns() {
        return columns;
    }

    public TextField getRows() {
        return rows;
    }

    public TextField getMines() {
        return mines;
    }

    public ToggleGroup getDifficulty() {
        return difficulty;
    }

    public RadioButton getBeginner() {
        return beginner;
    }

    public RadioButton getIntermediate() {
        return intermediate;
    }

    public RadioButton getExpert() {
        return expert;
    }

    public RadioButton getCustom() {
        return custom;
    }

    public Button getStart() {
        return start;
    }

    public Label getColumnsLabel() {
        return columnsLabel;
    }

    public Label getRowsLabel() {
        return rowsLabel;
    }

    public Label getMinesLabel() {
        return minesLabel;
    }

    public GridPane getRoot() {
        return root;
    }

    public Scene getScene() {
        return scene;
    }

    public BoardFactory getBoardFactory() {
        return boardFactory;
    }

    public void setBoardFactory(BoardFactory boardFactory) {
        this.boardFactory = boardFactory;
    }
}
