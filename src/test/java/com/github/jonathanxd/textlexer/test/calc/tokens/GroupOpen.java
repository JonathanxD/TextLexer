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
package com.github.jonathanxd.textlexer.test.calc.tokens;

import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.UnifiedTokenFactory;
import com.github.jonathanxd.textlexer.lexer.token.structure.analise.StructureRule;

/**
 * Created by jonathan on 06/02/16.
 */
public class GroupOpen extends UnifiedTokenFactory<String> {
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
