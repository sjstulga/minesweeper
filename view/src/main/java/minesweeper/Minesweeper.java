/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import javafx.application.Application;
import javafx.stage.Stage;
import minesweeper.view.factories.GameSceneFactory;
import minesweeper.view.factories.MenuSceneFactory;

/**
 *
 * @author sjstulga
 */
public class Minesweeper extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        MenuSceneFactory menuSceneFactory = new MenuSceneFactory(primaryStage);
        GameSceneFactory gameSceneFactory = new GameSceneFactory(primaryStage);

        menuSceneFactory.setGameSceneFactory(gameSceneFactory);
        gameSceneFactory.setMenuSceneFactory(menuSceneFactory);

        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(menuSceneFactory.create());
        primaryStage.sizeToScene();
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
