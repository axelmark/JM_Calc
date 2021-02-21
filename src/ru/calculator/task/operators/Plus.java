package ru.calculator.task.operators;

import ru.calculator.task.OperatorToken;

public class Plus extends OperatorToken {

    @Override
    protected float execute(float arg1, float arg2) {
        return arg1 + arg2;
    }
}
