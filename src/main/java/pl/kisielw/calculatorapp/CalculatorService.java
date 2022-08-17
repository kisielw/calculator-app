package pl.kisielw.calculatorapp;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorService {

    private Float number1 = null;
    private Float number2 = null;
    private Float result = null;
    private ArithmeticOperations operation = null;

    public Float getNumber1() {
        return number1;
    }

    public void setNumber2(Float number2) {
        this.number2 = number2;
    }

    public Float getResult() {
        return result;
    }

    public void setOperation(ArithmeticOperations operation) {
        this.operation = operation;
    }

    public ArithmeticOperations getOperation() {
        return operation;
    }

    public String calculate(String componentToOperation, Integer accuracyNumber) {
        String resultToDisplayOn;
        if (result == null) {
            number1 = Float.valueOf(componentToOperation);
            result = number1;
            resultToDisplayOn = result.toString();
        } else {
            number1 = result;
            number2 = Float.valueOf(componentToOperation);
            result = executeOperation(number1, number2, accuracyNumber);
            if (result == null) {
                resultToDisplayOn = "Nie dziel przez 0!";
            } else {
                resultToDisplayOn = result.toString();
            }
        }
        return resultToDisplayOn;
    }

    private Float executeOperation(Float number1, Float number2, Integer accuracyNumber) {
        BigDecimal helpNumber;
        switch (operation) {
            case ADD:
                result = number1 + number2;
                helpNumber = BigDecimal.valueOf(result);
                helpNumber = helpNumber.setScale(accuracyNumber, RoundingMode.HALF_UP);
                result = helpNumber.floatValue();
                return result;

            case SUBTRACT:
                result = number1 - number2;
                helpNumber = BigDecimal.valueOf(result);
                helpNumber = helpNumber.setScale(accuracyNumber, RoundingMode.HALF_UP);
                result = helpNumber.floatValue();
                return result;

            case MULTIPLE:
                result = number1 * number2;
                helpNumber = BigDecimal.valueOf(result);
                helpNumber = helpNumber.setScale(accuracyNumber, RoundingMode.HALF_UP);
                result = helpNumber.floatValue();
                return result;

            case DIVIDE:
                if (number2.equals((float)0)) {
                    return result = null;
                }
                result = number1 / (float)number2;
                helpNumber = BigDecimal.valueOf(result);
                helpNumber = helpNumber.setScale(accuracyNumber, RoundingMode.HALF_UP);
                result = helpNumber.floatValue();
                return result;

            case POWER:
                double power = Math.pow(number1.doubleValue(), number2.doubleValue());
                helpNumber = BigDecimal.valueOf(power);
                helpNumber = helpNumber.setScale(accuracyNumber, RoundingMode.HALF_UP);
                result = helpNumber.floatValue();
                return result;

            default:
                return result = null;
        }
    }

    public void clean() {
        number1 = null;
        number2 = null;
        operation = null;
        result = null;
    }
}
