package ru.calculator.task;

import ru.calculator.task.typeNumber.RomeNumber;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws Exception {
        // Ввод выражения от пользователя
        System.out.println("введите выражения ...: a + b, a - b, a * b, a / b.");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();

        // Вычисляем аргументы
        String regex2 = "^\\s*(?<arg1>(?<arab1>\\d+)|(?<rome1>[MLCDXVI]+))\\s*(?<op>[\\-\\+\\*\\/])\\s*(?<arg2>(?<arab2>\\d+)|(?<rome2>[MLCDXVI]+))\\s*$";
        Pattern pattern = Pattern.compile(regex2);
        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            if (matcher.group("arab1") != null && matcher.group("rome2") != null || matcher.group("rome1") != null && matcher.group("arab2") != null) {
                throw new Exception("Калькулятор умеет работать только с арабскими или римскими цифрами одновременно!");
            }
            NumericToken arg1 = NumericToken.parse(matcher.group("arg1"));
            NumericToken arg2 = NumericToken.parse(matcher.group("arg2"));

            if (arg1.getValue() > 10 || arg2.getValue() > 10) {
                throw new Exception("Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более.");
            }
            String op = matcher.group("op");
            OperatorToken operatorToken = OperatorToken.parse(op);
            float result = operatorToken.execute(arg1.getValue(), arg2.getValue());
            if (arg1 instanceof RomeNumber && arg2 instanceof RomeNumber) {
                String res = RomeNumber.parseArab(String.valueOf(Math.round(result)));
                System.out.printf("%s-(%S)%n", res, result);
            } else {
                System.out.println(Math.round(result));
            }

        } else {
            throw new Exception("При вводе пользователем неподходящих чисел приложение выбрасывает исключение и завершает свою работу.!");
        }

    }

}
