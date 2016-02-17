package com.github.jonathanxd.textlexer.test.calc.tokens.operators;

import com.github.jonathanxd.textlexer.lexer.token.UnifiedTokenType;

/**
 * Created by jonathan on 06/02/16.
 */
public class Minus extends UnifiedTokenType<String> implements Operator {

    @Override
    public boolean matches(char character) {
        return character == '-';
    }

    @Override
    public String dataToValue() {
        return getData();
    }
}
