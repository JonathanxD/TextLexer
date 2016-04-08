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
package com.github.jonathanxd.textlexer.lexer.token;

import com.github.jonathanxd.textlexer.lexer.token.builder.TokenBuilder;
import com.github.jonathanxd.textlexer.lexer.token.processor.ProcessorData;
import com.github.jonathanxd.textlexer.lexer.token.factory.ITokenFactory;

/**
 * A Unification of TokenType and IToken.
 *
 * The class that implements it needs to have a empty constructor, or override the {@link
 * #createToken(String)} method.
 */
public abstract class UnifiedTokenFactory<T> extends AbstractToken<T> implements ITokenFactory<T> {

    public UnifiedTokenFactory() {
        super("");
    }

    @Override
    public String getSimpleName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public TokenBuilder process(ProcessorData data) {

        TokenBuilder current = data.getBuilderList().hasCurrent() ? data.getBuilderList().current() : null;

        if (current != null) {
            current.addToData(data.getCharacter());
        }

        return current;
    }

    @SuppressWarnings("unchecked")
    @Override
    public IToken<T> createToken(String data) {
        IToken<T> token;
        try {
            token = this.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Error, read the " + UnifiedTokenFactory.class.getName() + " documentation!", e);
        }
        token.setData(data);
        return token;
    }

}
