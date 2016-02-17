package com.github.jonathanxd.textlexer.test.calc.tokens;

import com.github.jonathanxd.textlexer.lexer.token.UnifiedTokenType;

/**
 * Created by jonathan on 06/02/16.
 */
public class Number extends UnifiedTokenType<Integer> {

    @Override
    public boolean matches(char character) {
        return Character.isDigit(character);
    }

    @Override
    public Integer dataToValue() {
        return Integer.parseInt(getData());
    }
}
