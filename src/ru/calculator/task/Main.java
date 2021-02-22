package ru.calculator.task;

import ru.calculator.task.typeNumber.RomeNumber;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws Exception {
        boolean strictRulesMode;
        Scanner scanner = new Scanner(System.in);

        // Запрос режимов работы калькулятора
        System.out.println("Включить режим строгой совместимости с ТЗ? (Введите: 0 - Нет, 1 - Да)");
        strictRulesMode = scanner.nextLine().equals("1");
        System.out.println(strictRulesMode);
        if (strictRulesMode) {
            System.out.println("\033[0;106m" + "Режим строгой совместимости с ТЗ включен!" + "\u001B[0m");
        } else {
            System.out.println("\033[0;103m" + "Режим строгой совместимости с ТЗ отключен!" + "\u001B[0m");
        }

        // Ввод выражения от пользователя
        System.out.println("Введите выражение: a + b, a - b, a * b, a / b.");
        String input = scanner.nextLine();

        // Вычисляем аргументы
        String regex = "^\\s*(?<arg1>(?:\\d+)|(?:[MLCDXVIN]+))\\s*(?<op>[\\-+*/])\\s*(?<arg2>(?:\\d+)|(?:[MLCDXVIN]+))\\s*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            NumericToken arg1 = NumericToken.parse(matcher.group("arg1"));
            NumericToken arg2 = NumericToken.parse(matcher.group("arg2"));

            if (strictRulesMode) {
                if (arg1.getClass() != arg2.getClass()) {
                    throw new Exception("Калькулятор умеет работать одновременно только с цифрами одного типа!");
                }
                if (arg1.getValue() > 10 || arg2.getValue() > 10) {
                    throw new Exception("Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более.");
                }
            }

            String op = matcher.group("op");
            OperatorToken operatorToken = OperatorToken.parse(op);
            float result = operatorToken.execute(arg1.getValue(), arg2.getValue());

            //Проверка типа и вывод результа
            String resultStr;
            if (arg1 instanceof RomeNumber) {
                resultStr = RomeNumber.toRome(((int) result));
            } else {
                resultStr = String.valueOf((int) result);
            }
            System.out.printf("%s %s %s = %s", arg1, op, arg2, resultStr);
        } else {
            throw new Exception("При вводе пользователем неподходящих чисел приложение выбрасывает исключение и завершает свою работу!");
        }
    }
}
