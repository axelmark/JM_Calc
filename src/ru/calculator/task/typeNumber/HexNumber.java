package ru.calculator.task.typeNumber;

import ru.calculator.task.NumericToken;

import java.util.Locale;

public class HexNumber extends NumericToken {
    public HexNumber(String str) {
        setValue(Integer.parseInt(str.startsWith("0x") ? str.substring(2) : str, 16));
    }

    public HexNumber(float value) {
        setValue(value);
    }

    @Override
    public String toString() {
        return "0x" + Integer.toString((int) getValue(), 16).toUpperCase(Locale.ROOT);
    }
}
