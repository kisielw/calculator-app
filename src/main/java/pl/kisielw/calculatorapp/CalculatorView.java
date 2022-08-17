package pl.kisielw.calculatorapp;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CalculatorView {

    private static CalculatorView view = null;
    private final CalculatorService calculatorService;
    private static int SCENE_WIDTH = 200;
    private static int SCENE_HEIGHT = 400;
    private static int BUTTONS_MIN_WIDTH = 50;
    private static int BUTTONS_MIN_HEIGHT = 60;
    private static int RESULT_FIELD_MIN_WIDTH = 200;
    private static int RESULT_FIELD_MIN_HEIGHT = 50;

    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    private Button b6;
    private Button b7;
    private Button b8;
    private Button b9;
    private Button b0;
    private Button comma;
    private Button add;
    private Button subtract;
    private Button multiply;
    private Button divide;
    private Button power;
    private Button result;
    private Button changeSign;
    private Button clean;
    private TextField resultField;
    private Spinner<Integer> accuracy;
    private VBox vBox;
    private Scene scene;

    public CalculatorView() {
        if (view != null) {
            throw new RuntimeException("Not allowed. Please use getInstance() method");
        }
        calculatorService = new CalculatorService();
        setScene();
    }

    private void setButtons() {
        b1 = new Button("1");
        b2 = new Button("2");
        b3 = new Button("3");
        b4 = new Button("4");
        b5 = new Button("5");
        b6 = new Button("6");
        b7 = new Button("7");
        b8 = new Button("8");
        b9 = new Button("9");
        b0 = new Button("0");
        comma = new Button(".");
        add = new Button("+");
        subtract = new Button("-");
        multiply = new Button("*");
        divide = new Button("/");
        power = new Button("^");
        result = new Button("=");
        changeSign = new Button("+/-");
        clean = new Button("C");
        comma.setDisable(true);
        setSizeForButtons(b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, comma, add, subtract, multiply, divide, power,
                result, changeSign, clean);
        setOnActionForNumberButtons(b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, comma);
        setOnActionsForOperationButtons(add, subtract, multiply, divide, power);
        setOnActionsForOtherButtons();
    }

    private void setSizeForButtons(Button... buttons) {
        for (Button button : buttons) {
            button.setMinSize(BUTTONS_MIN_WIDTH, BUTTONS_MIN_HEIGHT);
        }
    }

    private void setOnActionForNumberButtons(Button... buttons) {
        for (Button button : buttons) {
            button.setOnAction(actionEvent -> {
                if (isOperationNotNull() && ifResultFieldEqualsNumber1OrResult()) {
                    resultField.setText("");
                }
                changeSign.setDisable(false);
                String resultFieldText = resultField.getText();
                if (!resultFieldText.contains(".")) {
                    comma.setDisable(false);
                }
                String buttonText = button.getText();

                switch (buttonText) {
                    case "0":
                        if (resultFieldText.equals("0")) {
                            resultField.setText("0");
                        } else {
                            resultField.setText(String.format("%s%s", resultFieldText, buttonText));
                        }
                        break;
                    case ".":
                        comma.setDisable(true);
                        resultField.setText(String.format("%s%s", resultFieldText, buttonText));
                        break;
                    default:
                        if (resultFieldText.equals("0")) {
                            resultField.setText(buttonText);
                        } else {
                            resultField.setText(String.format("%s%s", resultFieldText, buttonText));
                        }
                }
            });
        }
    }

    private boolean isOperationNotNull() {
        return calculatorService.getOperation() != null;
    }

    private boolean ifResultFieldEqualsNumber1OrResult() {
        String textFromResultField = resultField.getText();
        String number1 = calculatorService.getNumber1().toString();
        boolean isResultFieldEqualsNumber1 = textFromResultField.equals(number1);
        boolean isResultFieldEqualsResult = resultField.getText().equals(calculatorService.getResult().toString());
        return (isResultFieldEqualsNumber1 || isResultFieldEqualsResult);
    }

    private void setOnActionsForOperationButtons(Button... buttons) {
        for (Button button : buttons) {
            button.setOnAction(actionEvent -> {
                switch (button.getText()) {
                    case "+":
                        setOnActionByOperation(ArithmeticOperations.ADD);
                        break;
                    case "-":
                        setOnActionByOperation(ArithmeticOperations.SUBTRACT);
                        break;
                    case "*":
                        setOnActionByOperation(ArithmeticOperations.MULTIPLE);
                        break;
                    case "/":
                        setOnActionByOperation(ArithmeticOperations.DIVIDE);
                        break;
                    case "^":
                        setOnActionByOperation(ArithmeticOperations.POWER);
                        break;
                }
            });
        }
    }

    private void setOnActionsForOtherButtons() {
        result.setOnAction(actionEvent -> {
            if (calculatorService.getResult() != null) {
                setTextToResultFieldWithCalculatedResult();
                disableButtons();
            }
        });

        changeSign.setOnAction(actionEvent -> {
            String textInField = resultField.getText();
            if (textInField.equals("")) {
                resultField.setText("-");
            }
            if (textInField.startsWith("-")) {
                resultField.setText(textInField.substring(1));
            } else {
                resultField.setText("-" + textInField);
            }
        });

        clean.setOnAction(actionEvent -> {
            calculatorService.clean();
            enableButtons();
            resultField.setText("");
        });
    }

    private void disableButtons() {
        setDisableButtons(true, b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, comma, add,
                subtract, multiply, divide, power, result, changeSign);
    }

    private void enableButtons() {
        setDisableButtons(false, b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, add,
                subtract, multiply, divide, power, result, changeSign);
    }

    private void setOnActionByOperation(ArithmeticOperations operation) {
        if (resultField.getText().length() != 0) {
            if (isOperationNotNull()) {
                setTextToResultFieldWithCalculatedResult();
                calculatorService.setNumber2(null);
                calculatorService.setOperation(operation);
            } else {
                calculatorService.setOperation(operation);
                setTextToResultFieldWithCalculatedResult();
            }
        }
    }

    private void setTextToResultFieldWithCalculatedResult() {
        changeSign.setDisable(true);
        String componentToOperation = resultField.getText();
        Integer accuracyNumber = accuracy.getValue();
        resultField.setText(calculatorService.calculate(componentToOperation, accuracyNumber));
        if (resultField.getText().equals("Nie dziel przez 0!")) {
            disableButtons();
        }
    }

    private void setDisableButtons(boolean disable, Button... buttons) {
        for (Button button : buttons) {
            button.setDisable(disable);
        }
    }

    private void setVBox() {
        setResultField();
        setButtons();
        HBox hBox1 = new HBox(clean, power, divide);
        HBox hBox2 = new HBox(b7, b8, b9, multiply);
        HBox hBox3 = new HBox(b4, b5, b6, subtract);
        HBox hBox4 = new HBox(b1, b2, b3, add);
        HBox hBox5 = new HBox(changeSign, b0, comma, result);
        accuracy = new Spinner<>(0, 20, 2);
        Label accuracyLabel = new Label("Liczba miejsc po przecinku:");
        vBox = new VBox(resultField, hBox1, hBox2, hBox3, hBox4, hBox5, accuracyLabel, accuracy);
    }

    private void setResultField() {
        resultField = new TextField();
        resultField.setDisable(true);
        resultField.setMinSize(RESULT_FIELD_MIN_WIDTH, RESULT_FIELD_MIN_HEIGHT);
    }

    private void setScene() {
        setVBox();
        scene = new Scene(vBox, SCENE_WIDTH, SCENE_HEIGHT);
    }

    public Scene getScene() {
        return scene;
    }

    public static CalculatorView getView() {

        if (view == null) {
            view = new CalculatorView();
        }
        return view;
    }
}
