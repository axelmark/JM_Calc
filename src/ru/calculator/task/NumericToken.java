package ru.calculator.task;

import ru.calculator.task.typeNumber.ArabicNumber;
import ru.calculator.task.typeNumber.HexNumber;
import ru.calculator.task.typeNumber.RomeNumber;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

public abstract class NumericToken {
    public float getValue() {
        return Value;
    }

    protected void setValue(float value) {
        Value = value;
    }

//    public NumericToken apply(OperatorToken op, NumericToken arg2) {
//        var result = op.execute(getValue(), arg2.getValue());
//        try {
//            return (NumericToken) this.getClass().getConstructor(Float.class).newInstance(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    private float Value;

    public static NumericToken parse(String str) throws Exception {
        var tokenManager = TokenManager.getSingleton();
        var tokenInstance = (NumericToken) tokenManager.createTokenByRegex(NumericToken.class, str);
        if (tokenInstance == null) {
            throw new Exception("Значение аргумента '" + str + "' не распознано!");
        }
        return tokenInstance;

        //        String romeNum = "[MDCLXVIN]+";
//        String arabNum = "\\d+";
//        String hexNum = "0x[0-9A-Fa-f]+";
//
//        if (Pattern.matches(romeNum, str)) {
//            return new RomeNumber(str);
//        } else if (Pattern.matches(arabNum, str)) {
//            return new ArabicNumber(str);
//        } else if (Pattern.matches(hexNum, str)) {
//            return new HexNumber(str);
//        } else {
//            throw new Exception("Не верный символ");
//        }
    }
}