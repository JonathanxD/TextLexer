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

import github.therealbuggy.textlexer.lexer.token.IToken;
import github.therealbuggy.textlexer.lexer.token.SequenceTokenType;
import github.therealbuggy.textlexer.lexer.token.structure.analise.StructureRule;
import github.therealbuggy.textlexer.test.test1.basetokens.type.Expression;

/**
 * Created by jonathan on 08/02/16.
 */
public class IfToken extends SequenceTokenType<String> implements Expression<String> {

    private final String token = "if";
    private final StructureRule structureRule = new StructureRule() {
        @Override
        public IToken<?> getToken() {
            return IfToken.this;
        }

        @Override
        public Class<? extends IToken> after() {
            return ExpressionOpen.class;
        }
    };

    @Override
    public boolean matches(String sequence) {
        return sequence.equals(token);
    }

    @Override
    public int[] matchSizes() {
        return new int[] {
                token.length()
        };
    }

    @Override
    public String dataToValue() {
        return getData();
    }


}
