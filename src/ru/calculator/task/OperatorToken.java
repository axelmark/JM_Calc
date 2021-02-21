package ru.calculator.task;

import ru.calculator.task.operators.Divide;
import ru.calculator.task.operators.Minus;
import ru.calculator.task.operators.Multiply;
import ru.calculator.task.operators.Plus;

import java.util.regex.Pattern;

public abstract class OperatorToken {

    static OperatorToken parse(String str) throws Exception {

        if (Pattern.matches("\\+", str)) {
            return new Plus();
        }
        if (Pattern.matches("\\-", str)) {
            return new Minus();
        }
        if (Pattern.matches("\\*", str)) {
            return new Multiply();
        }
        if (Pattern.matches("\\/", str)) {
            return new Divide();
        } else {
            throw new Exception("Не подходящий оператор"); //Не должно сработать, а вдруг)
        }
    }

    public NumericToken execute(NumericToken arg1, NumericToken arg2) {
        return null;
    }

    protected abstract float execute(float arg1, float arg2);
}
