package ru.calculator.task;

import ru.calculator.task.typeNumber.ArabicNumber;
import ru.calculator.task.typeNumber.RomeNumber;
import java.util.HashMap;
import java.util.regex.Pattern;

public abstract class NumericToken {

    public NumericToken(String str) {
        this.setValue(Float.parseFloat(str));
    }

    public NumericToken(String str, String dd) {
        this.setValue(Float.parseFloat(str));
    }

    public float getValue() {
        return Value;
    }

    protected void setValue(float value) {
        Value = value;
    }

    private float Value;

    public static NumericToken parse(String str) throws Exception {

        String romeNum = "^[MDCLXVI]*$";
        String arabNum = "^\\d+$";

        if (Pattern.matches(romeNum, str)) {
            String arabN = parseRome(str);
            return new RomeNumber(arabN);
        }
        if (Pattern.matches(arabNum, str)) {
            return new ArabicNumber(str);
        } else {
            throw new Exception("Не верный символ");
        }
    }

    public static String parseRome(String str) {

        HashMap<String, String> rome = new HashMap<String, String>();
        rome.put("I", "1");
        rome.put("II", "2");
        rome.put("III", "3");
        rome.put("IV", "4");
        rome.put("V", "5");
        rome.put("VI", "6");
        rome.put("VII", "7");
        rome.put("VIII", "8");
        rome.put("IX", "9");

        rome.put("X", "10");
        rome.put("XI", "11");
        rome.put("XII", "12");
        rome.put("XIII", "13");
        rome.put("XIV", "14");
        rome.put("XV", "15`");
        rome.put("XVI", "16");
        rome.put("XVII", "17");
        rome.put("XVIII", "18");
        rome.put("XIX", "19");
        rome.put("XX", "20");

        rome.put("L", "50");
        rome.put("C", "100");
        rome.put("D", "500");
        rome.put("M", "1000");

        return rome.getOrDefault(str, "None");
    }

    public static String parseArab(String str) {

        HashMap<String, String> arab = new HashMap<String, String>();
        arab.put("1", "I");
        arab.put("2", "II");
        arab.put("3", "III");
        arab.put("4", "IV");
        arab.put("5", "V");
        arab.put("6", "VI");
        arab.put("7", "VII");
        arab.put("8", "VIII");
        arab.put("9", "IX");

        arab.put("10", "X");
        arab.put("11", "XI");
        arab.put("12", "XII");
        arab.put("13", "XIII");
        arab.put("14", "XIV");
        arab.put("15", "XV");
        arab.put("16", "XVI");
        arab.put("17", "XVII");
        arab.put("18", "XVIII");
        arab.put("19", "XIX");
        arab.put("20", "XX");

        arab.put("50", "L");
        arab.put("100", "C");
        arab.put("500", "D");
        arab.put("1000", "M");

        return arab.getOrDefault(str, "None");
    }

}