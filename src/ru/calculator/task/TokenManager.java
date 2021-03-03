package ru.calculator.task;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class TokenManager {
    private static final TokenManager singleton = new TokenManager();

    public static TokenManager getSingleton() {
        return singleton;
    }

//    public void registerFromPackage() {
//        registerFromPackage(null /* TODO: get current package */);
//    }
//
//    public void registerFromPackage(Package pack) {
//        // TODO: iterate all classes in the package
//        // and call register(Class instClass) for the classes,
//        // which has your custom Annotation!
//    }
//
//    public void register(Class instClass) {
//        String regexPattern = null;
//        //TODO: get pattern value from your custom Annotation!
//        register(instClass, regexPattern);
//    }

    public void register(Class instClass, String regexPattern) {
        if (instClass == null || regexPattern == null) {
            throw new InvalidParameterException("All parameters must be not null");
        }

        tokenMap.add(new TokenInfo() {{
            baseTokenClass = instClass.getSuperclass();
            tokenClass = instClass;
            regex = regexPattern;
        }});
    }

    public Object createTokenByRegex(Class baseClass, String testValue) throws Exception {
        var info = findByRegex(baseClass, testValue);
        if (info != null) {

            // ссылка на конструктор
            Constructor ctor = null;

            try {
                // попытаться получить конструктор, который принимает один аргумент типа String
                ctor = info.tokenClass.getConstructor(String.class);

                // конструкторов найден, создаем экземпляр класса
                return ctor.newInstance(testValue);
            } catch (NoSuchMethodException e1) {

                // если ошибка, то значит не удалось получить такой конструктор
                try {
                    // значит пробуем поискать конструктор без параметров
                    ctor = info.tokenClass.getConstructor();

                    // конструкторов найден, создаем экземпляр класса
                    return ctor.newInstance();
                } catch (NoSuchMethodException e2) {

                    // если ошибка, то не смогли найти даже конструктор без аргументов!
                    throw new Exception("Класс токена '" + info.tokenClass.getName() + "' должен иметь один из конструкторов .ctor() или .ctor(String)");
                }
            }

            //try {
            //    // the bellow code is analog of 'new tokenInfo(testValue)'
            //    var ctor = info.tokenClass.getConstructor(String.class);
            //    return ctor.newInstance(testValue);
            //} catch (Exception e) {
            //    e.printStackTrace();
            //    //TODO: throw another Exception that reports about Token Constructor problems.
            //}
        }
        return null;
    }

    public String[] getAllRegexList(Class baseClass) {
        ArrayList<String> result = new ArrayList<>();
        for (var item : tokenMap) {
            if (item.baseTokenClass == baseClass)
                result.add(item.regex);
        }
        return result.toArray(new String[0]);
    }

    private ArrayList<TokenInfo> tokenMap = new ArrayList<>();

    private TokenInfo findByRegex(Class baseClass, String testValue) {
        for (var item : tokenMap) {
            if (item.baseTokenClass == baseClass && Pattern.matches(item.regex, testValue)) {
                return item;
            }
        }
        return null;
    }

    private class TokenInfo {
        public Class baseTokenClass;
        public Class tokenClass;
        public String regex;
    }
}
