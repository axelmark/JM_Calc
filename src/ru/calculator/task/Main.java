package ru.calculator.task;

import ru.calculator.task.operators.*;
import ru.calculator.task.typeNumber.ArabicNumber;
import ru.calculator.task.typeNumber.HexNumber;
import ru.calculator.task.typeNumber.RomeNumber;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 1. Описать новый класс
// 2. Зарегистрировать его в TokenManager с соответствующим Regex

// xxx 2. Изменить входное регулярное выражение (не забыть xxx (!), что в нем ДВА таких места)
// xxx 3. Изменить метод NumericToken.parse(...)
// xxx 3.1. Не забыть (!!!) в третий раз в NumericToken.parse(...) указать ТАКУЮ же регулярку как на входе !
// xxx 4. Изменить функцию вывода результата на выходе добавив новый тип в условия!!!

public class Main {
    public static void main(String[] args) throws Exception {

        // добавляем нужные нам для работы операторы и типы аргументов
        var tokenManager = TokenManager.getSingleton();
        tokenManager.register(Plus.class, "[\\+]");
        tokenManager.register(Minus.class, "[\\-]");
        tokenManager.register(Multiply.class, "[\\*]");
        tokenManager.register(Divide.class, "[\\/]");

        tokenManager.register(ArabicNumber.class, "[\\d]+");
        tokenManager.register(RomeNumber.class, "[MLCDXVIN]+");
        tokenManager.register(HexNumber.class, "0x[0-9A-Fa-f]+");
        tokenManager.register(DivideMod.class, "[\\%]");

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

        // Создаем динамически регулярку, исходя из списка поддерживаемых операторов и типов аргументов
        var argRegexList = tokenManager.getAllRegexList(NumericToken.class);
        var opRegexList = tokenManager.getAllRegexList(OperatorToken.class);
        var argRegex = String.join("|", argRegexList);
        var opRegex = String.join("|", opRegexList);
        //String regex = "^\\s*(?<arg1>\\d+|[MLCDXVIN]+|0x[0-9A-Fa-f]+)\\s*(?<op>[\\-+*/])\\s*(?<arg2>(?:\\d+)|(?:[MLCDXVIN]+)|(?:0x[0-9A-Fa-f]+))\\s*$";
        String regex = String.format("^\\s*(?<arg1>%s)\\s*(?<op>%s)\\s*(?<arg2>%s)\\s*$", argRegex, opRegex, argRegex);

        // Проверка входной строки на соответствие (все что поддерживается, будет допущено)
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            // создаём враперы по входящей строке
            NumericToken arg1 = NumericToken.parse(matcher.group("arg1"));
            NumericToken arg2 = NumericToken.parse(matcher.group("arg2"));
            OperatorToken op = OperatorToken.parse(matcher.group("op"));

            if (strictRulesMode) {
                // Проверки по "строгому" соответсвию ТЗ (лишние, по факту)
                if (arg1.getClass() != arg2.getClass()) {
                    throw new Exception("Калькулятор умеет работать одновременно только с цифрами одного типа!");
                }
                if (arg1.getValue() > 10 || arg2.getValue() > 10) {
                    throw new Exception("Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более.");
                }
            }

            // Вывод результата с типом первого аргумента.
            float result = op.execute(arg1.getValue(), arg2.getValue());
            NumericToken resultToken = (NumericToken) arg1.getClass().getConstructor(float.class).newInstance(result);

            System.out.printf("%s %s %s = %s", arg1, op, arg2, resultToken);
        } else {
            throw new Exception("При вводе пользователем неподходящих чисел приложение выбрасывает исключение и завершает свою работу!");
        }
    }
}
