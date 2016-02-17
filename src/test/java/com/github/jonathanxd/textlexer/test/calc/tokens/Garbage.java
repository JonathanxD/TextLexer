package com.github.jonathanxd.textlexer.test.calc.tokens;

import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.UnifiedTokenType;

/**
 * Created by jonathan on 07/02/16.
 */
public class Garbage extends UnifiedTokenType<String> {


    @Override
    public String dataToValue() {
        return getData();
    }

    @Override
    public boolean matches(char c) {
        return c == ' ';
    }

    @Override
    public int order() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean hide() {
        return true;
    }
}
