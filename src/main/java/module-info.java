module pl.kisielw.calculatorapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens pl.kisielw.calculatorapp to javafx.fxml;
    exports pl.kisielw.calculatorapp;
}