package pl.kisielw.calculatorapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class CalculatorApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        CalculatorView calculatorView = CalculatorView.getView();
        stage.setScene(calculatorView.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}