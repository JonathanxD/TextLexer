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
package github.therealbuggy.textlexer.test.test1.tokens;

import java.util.regex.Pattern;

import github.therealbuggy.textlexer.lexer.token.IToken;
import github.therealbuggy.textlexer.lexer.token.UnifiedTokenType;
import github.therealbuggy.textlexer.lexer.token.structure.analise.StructureRule;
import github.therealbuggy.textlexer.test.test1.basetokens.DataContainerClose;
import github.therealbuggy.textlexer.test.test1.basetokens.DataContainerOpen;

/**
 * Created by jonathan on 08/02/16.
 */
public class Variable extends UnifiedTokenType<String> {

    private static final Pattern pattern = Pattern.compile("[A-Za-z0-9_]");

    private final StructureRule structureRule = new StructureRule() {
        @Override
        public IToken<?> getToken() {
            return Variable.this;
        }

        @Override
        public Class<? extends IToken> after() {
            return DataContainerOpen.class;
        }

        @Override
        public Class<? extends IToken> before() {
            return DataContainerClose.class;
        }
    };

    @Override
    public String dataToValue() {
        return getData();
    }

    @Override
    public boolean matches(char character) {
        return pattern.matcher(String.valueOf(character)).matches();
    }

    @Override
    public StructureRule getStructureRule() {
        return structureRule;
    }

    @Override
    public int order() {
        return 2;
    }
}
