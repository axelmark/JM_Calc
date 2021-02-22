package ru.calculator.task;

import ru.calculator.task.typeNumber.ArabicNumber;
import ru.calculator.task.typeNumber.RomeNumber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public abstract class NumericToken {
    public float getValue() {
        return Value;
    }
    protected void setValue(float value) {
        Value = value;
    }
    private float Value;

    public static NumericToken parse(String str) throws Exception {

        String romeNum = "^[MDCLXVIN]*$";
        String arabNum = "^\\d+$";

        if (Pattern.matches(romeNum, str)) {
            return new RomeNumber(str);
        }
        if (Pattern.matches(arabNum, str)) {
            return new ArabicNumber(str);
        } else {
            throw new Exception("Не верный символ");
        }
    }
}