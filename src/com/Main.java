package com;

import com.controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Controller controller = new Controller();

        stage.setTitle("TicTacToe");
        stage.setResizable(false);
        stage.setScene(controller.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}