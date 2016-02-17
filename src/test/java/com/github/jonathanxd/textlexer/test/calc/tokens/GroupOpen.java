package com.github.jonathanxd.textlexer.test.calc.tokens;

import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.UnifiedTokenType;
import com.github.jonathanxd.textlexer.lexer.token.structure.analise.StructureRule;

/**
 * Created by jonathan on 06/02/16.
 */
public class GroupOpen extends UnifiedTokenType<String> {
    private final StructureRule structureRule = new StructureRule() {
        @Override
        public IToken<?> getToken() {
            return GroupOpen.this;
        }

        @Override
        public Class<? extends IToken> before() {
            return GroupClose.class;
        }
    };

    @Override
    public boolean matches(char character) {
        return character == '(';
    }

    @Override
    public String dataToValue() {
        return getData();
    }

    @Override
    public int maxSize() {
        return 1;
    }

    @Override
    public StructureRule getStructureRule() {
        return structureRule;
    }

    @Override
    public int order() {
        return 1;
    }
}
