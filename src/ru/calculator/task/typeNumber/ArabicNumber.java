package ru.calculator.task.typeNumber;

import ru.calculator.task.NumericToken;

public class ArabicNumber extends NumericToken {
    public ArabicNumber(String str) {
        setValue(Float.parseFloat(str));
    }

    public ArabicNumber(float value) {
        setValue(value);
    }

    @Override
    public String toString() {
        return Integer.toString((int) getValue());
    }
}
