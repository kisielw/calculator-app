package pl.kisielw.calculatorapp;

import javafx.application.Application;
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