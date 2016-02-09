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
package com.github.jonathanxd.textlexer.lexer.token.type;

import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.builder.TokenBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Predicate;

import com.github.jonathanxd.textlexer.lexer.token.history.ITokenList;

/**
 * Created by jonathan on 06/02/16.
 */
public class FixedTokenType<T> implements ITokenType<T> {

    private final Class<IToken<T>> tokenClass;
    private final Predicate<Character> matcher;

    public FixedTokenType(Class<IToken<T>> tokenClass, Predicate<Character> matcher) {
        this.tokenClass = tokenClass;
        this.matcher = matcher;
    }

    @Override
    public TokenBuilder process(ITokenList ITokenList, TokenBuilder current, char character) {
        current.addToData(character);
        return current;
    }

    @Override
    public boolean matches(char character) {
        return matcher.test(character);
    }

    @Override
    public IToken<T> createToken(String data) {
        Constructor<IToken<T>> constructor;
        try {
            constructor = tokenClass.getConstructor(String.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Fixed Token needs a constructor with only one parameter of String.", e);
        }
        try {
            return constructor.newInstance(data);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Cannot create a new instance of Token! Possible problems: Private Constructor, Incompatible Types.", e);
        }
    }

    public Class<IToken<T>> getTokenClass() {
        return tokenClass;
    }
}
