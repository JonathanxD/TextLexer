package com.github.jonathanxd.textlexer.test.calc.tokens;

import com.github.jonathanxd.textlexer.lexer.token.SequenceTokenType;

/**
 * Created by jonathan on 08/02/16.
 */
public class Round extends SequenceTokenType<String> {

    @Override
    public boolean matches(String s) {
        return s.equalsIgnoreCase("round");
    }

    @Override
    public int[] matchSizes() {
        return new int[]{"round".length()};
    }

    @Override
    public String dataToValue() {
        return getData();
    }


}
