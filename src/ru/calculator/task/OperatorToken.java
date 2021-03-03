package ru.calculator.task;

import ru.calculator.task.operators.Divide;
import ru.calculator.task.operators.Minus;
import ru.calculator.task.operators.Multiply;
import ru.calculator.task.operators.Plus;

import java.util.ArrayList;
import java.util.regex.Pattern;

public abstract class OperatorToken {
    static OperatorToken parse(String str) throws Exception {
        var tokenManager = TokenManager.getSingleton();
        var tokenInstance = (OperatorToken) tokenManager.createTokenByRegex(OperatorToken.class, str);
        if (tokenInstance == null) {
            throw new Exception("Оператор '" + str + "' не поддерживается!");
        }
        return tokenInstance;

//        if (Pattern.matches("\\+", str)) {
//            return new Plus();
//        }
//        if (Pattern.matches("-", str)) {
//            return new Minus();
//        }
//        if (Pattern.matches("\\*", str)) {
//            return new Multiply();
//        }
//        if (Pattern.matches("/", str)) {
//            return new Divide();
//        } else {
//            throw new Exception("Не подходящий оператор"); //A вдруг!)
//        }
    }

    protected abstract float execute(float arg1, float arg2);
}
