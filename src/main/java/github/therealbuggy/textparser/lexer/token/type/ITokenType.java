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
package github.therealbuggy.textparser.lexer.token.type;

import github.therealbuggy.textparser.lexer.token.IToken;
import github.therealbuggy.textparser.lexer.token.builder.TokenBuilder;
import github.therealbuggy.textparser.lexer.token.history.ITokenList;
import github.therealbuggy.textparser.lexer.token.processor.ProcessorData;
import github.therealbuggy.textparser.lexer.token.structure.analise.StructureRule;

/**
 * Created by jonathan on 30/01/16.
 */
public interface ITokenType<T> {

    TokenBuilder process(ITokenList tokenList, TokenBuilder current, char character);

    boolean matches(char character);

    default boolean matches(ProcessorData processorData) {
        return matches(processorData.getCharacter());
    }

    IToken<T> createToken(String data);

    default IToken<T> createToken(ProcessorData processorData) {
        return createToken(processorData.getData());
    }

    default int order() {
        return 5;
    }

    default int maxSize() { return -1; }
    default int minSize() { return 0; }

    /**
     * Similar to {@link StructureRule#after()}. But, check the structure in build-runtime
     * @return Need to return a class of token need to be present after this token
     * @deprecated Structure Analise update #1 may have broken build-runtime structure analise
     */
    @Deprecated
    default Class<? extends IToken> after() { // DEPOIS
        return null;
    }

}
