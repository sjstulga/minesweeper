/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import javafx.application.Application;
import javafx.stage.Stage;
import minesweeper.view.controller.MenuViewController;
import minesweeper.view.MenuView;

/**
 *
 * @author sjstulga
 */
public class Minesweeper extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Minesweeper");

        MenuView menuViewModel = new MenuView();
        MenuViewController menuViewController = new MenuViewController(primaryStage, menuViewModel);

        menuViewController.init();
        menuViewController.display();

        primaryStage.show();
        primaryStage.setOnCloseRequest((event) -> {
            System.exit(0);
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
