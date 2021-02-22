package ru.calculator.task.typeNumber;

import ru.calculator.task.NumericToken;

public class ArabicNumber extends NumericToken {
    public ArabicNumber(String str) {
        setValue(Float.parseFloat(str));
    }

    @Override
    public String toString() {
        return Integer.toString((int)getValue());
    }
}
