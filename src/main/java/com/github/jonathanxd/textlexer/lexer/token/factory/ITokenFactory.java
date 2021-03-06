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
package com.github.jonathanxd.textlexer.lexer.token.factory;

import com.github.jonathanxd.iutils.annotations.ProcessedBy;
import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.builder.TokenBuilder;
import com.github.jonathanxd.textlexer.lexer.token.processor.OrderComparator;
import com.github.jonathanxd.textlexer.lexer.token.processor.ProcessorData;
import com.github.jonathanxd.textlexer.lexer.token.structure.analise.StructureRule;

import java.util.Collection;

/**
 * Created by jonathan on 30/01/16.
 */
public interface ITokenFactory<T> {

    TokenBuilder process(ProcessorData processorData);

    boolean matches(char character);

    default boolean matches(ProcessorData processorData) {
        return matches(processorData.getCharacter());
    }

    IToken<T> createToken(String data);

    default IToken<?> createToken(ProcessorData processorData) {
        return createToken(processorData.getData());
    }

    @ProcessedBy({OrderComparator.class})
    default int order() {
        Collection<? extends ITokenFactory> tokenTypes;
        if ((tokenTypes = orderAfter()) == null || tokenTypes.isEmpty())
            return 5;

        int priority = 0;

        for (ITokenFactory<?> tokenType : tokenTypes) {
            if (tokenType.order() > priority) {
                priority = tokenType.order() + 1;
            }
        }

        return priority;
    }

    @ProcessedBy({ITokenFactory.class})
    default Collection<? extends ITokenFactory> orderAfter() {
        return null;
    }

    @ProcessedBy({OrderComparator.class})
    default Collection<Class<? extends ITokenFactory>> orderAfterClasses() {
        return null;
    }

    default int maxSize() {
        return -1;
    }

    default int minSize() {
        return 0;
    }

    /**
     * Similar to {@link StructureRule#after()}. But, check the structure in build-runtime
     *
     * @return Need to return a class of token need to be present after this token
     * @deprecated Structure Analise update #1 may have broken build-runtime structure analyse
     */
    @Deprecated
    default Class<? extends IToken> after() { // DEPOIS
        return null;
    }

}
