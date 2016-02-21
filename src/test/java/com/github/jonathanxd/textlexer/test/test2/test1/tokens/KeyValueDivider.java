/*
 * 	TextLexer - Lexical Analyzer API for Java! <https://github.com/JonathanxD/TextLexer>
 *     Copyright (C) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
 *
 * 	GNU GPLv3
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.jonathanxd.textlexer.test.test2.test1.tokens;

import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.UnifiedTokenType;
import com.github.jonathanxd.textlexer.lexer.token.structure.analise.StructureRule;

/**
 * Created by jonathan on 20/02/16.
 */
public class KeyValueDivider extends UnifiedTokenType<String> {

    private final StructureRule rule = new StructureRule() {
        @Override
        public IToken<?> getToken() {
            return KeyValueDivider.this;
        }

        @Override
        public Class<? extends IToken> after() {
            return KeyToken.class;
        }

        @Override
        public Class<? extends IToken> before() {
            return ValueToken.class;
        }
    };

    @Override
    public String dataToValue() {
        return getData();
    }

    @Override
    public boolean matches(char character) {
        return character == '=';
    }

    @Override
    public StructureRule getStructureRule() {
        return rule;
    }
}
