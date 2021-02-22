package ru.calculator.task.typeNumber;

import ru.calculator.task.NumericToken;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class RomeNumber extends NumericToken {
    public RomeNumber(String str) {
        setValue(toFloat(str));
    }

    @Override
    public String toString() {
        return toRome(getValue());
    }

    public static String correct(String rome) {
        return toRome(toFloat(rome));
    }

    public static float toFloat(String rome) {
        if (rome == null || rome.isBlank() || rome.equals("N")) {
            return 0;
        }

        // Examples:
        // val:  10, rome: X
        // val: 957, rome: CMLVII
        // val: 444, rome: CDXLIV
        // val: 333, rome: CCCXXXIII

        initRomeTable();
        int ind = 0;
        int result = 0;
        while (ind < rome.length()) {
            var row =
                    ind + 2 > rome.length() ? null :
                            Find(rome.substring(ind, ind + 2));

            if (row != null) {
                result += row.num;
                ind += 2;
            } else {
                row = Find(rome.substring(ind, ind + 1));
                if (row != null) {
                    result += row.num;
                    ind++;
                } else {
                    throw new InvalidParameterException(
                            "Синтаксическая ошибка: входная строка содержит не римский символ '" + rome.charAt(ind) + "' в позиции " + ind);
                }
            }
        }
        return result;
    }

    public static String toRome(float value) {
        if (value == 0) {
            return "N";
        }

        // Examples:
        // val: 957, rome: CMLVII
        // val: 444, rome: CDXLIV
        // val: 333, rome: CCCXXXIII

        initRomeTable();
        StringBuilder result = new StringBuilder();
        int val = (int) value;
        while (val > 0) {
            // ищем строку с ближайшим меньшим числом, чем val
            for (var item : romeTable) {
                if (item.num <= val) {
                    result.append(item.rome);
                    val -= item.num;
                    break;
                }
            }
        }
        return result.toString();
    }


    private static ArrayList<RomeRow> romeTable = null;

    private static RomeRow Find(String romePart) {
        // ищем строку по части римского набора
        for (var item : romeTable) {
            if (item.rome.equalsIgnoreCase(romePart)) {
                return item;
            }
        }
        return null;
    }

    private static void initRomeTable() {
        if (romeTable == null) {
            romeTable = new ArrayList<>();
            romeTable.add(new RomeRow() {{
                num = 1000;
                rome = "M";
            }});
            romeTable.add(new RomeRow() {{
                num = 900;
                rome = "CM";
            }});
            romeTable.add(new RomeRow() {{
                num = 500;
                rome = "D";
            }});
            romeTable.add(new RomeRow() {{
                num = 400;
                rome = "CD";
            }});
            romeTable.add(new RomeRow() {{
                num = 100;
                rome = "C";
            }});
            romeTable.add(new RomeRow() {{
                num = 90;
                rome = "XC";
            }});
            romeTable.add(new RomeRow() {{
                num = 50;
                rome = "L";
            }});
            romeTable.add(new RomeRow() {{
                num = 40;
                rome = "XL";
            }});
            romeTable.add(new RomeRow() {{
                num = 10;
                rome = "X";
            }});
            romeTable.add(new RomeRow() {{
                num = 9;
                rome = "IX";
            }});
            romeTable.add(new RomeRow() {{
                num = 5;
                rome = "V";
            }});
            romeTable.add(new RomeRow() {{
                num = 4;
                rome = "IV";
            }});
            romeTable.add(new RomeRow() {{
                num = 1;
                rome = "I";
            }});
            romeTable.add(new RomeRow() {{
                num = 0;
                rome = "N";
            }});
        }
    }

    private static class RomeRow {
        public int num;
        public String rome;
    }
}
