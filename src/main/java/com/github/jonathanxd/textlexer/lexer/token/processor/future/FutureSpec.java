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
package com.github.jonathanxd.textlexer.lexer.token.processor.future;

import com.github.jonathanxd.iutils.annotations.Named;
import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.builder.TokenBuilder;
import com.github.jonathanxd.textlexer.lexer.token.history.ITokenList;

import java.util.Optional;
import java.util.function.BiPredicate;

import javax.annotation.Nullable;

/**
 * Created by jonathan on 09/03/16.
 */
public class FutureSpec {
    private final int startIn;
    private final int amount;
    private final BiPredicate<Character, ITokenList> charAcceptor;
    private final BiPredicate<IToken<?>, ITokenList> tokenAcceptor;
    private final BiPredicate<Optional<TokenBuilder>, Throwable> ignoreError;

    /**
     * Specification to Collect future tokens
     *
     * @param startIn       Position to start token collect (Current + from)
     * @param amount        Amount of tokens you want to collect.
     * @param charAcceptor  Char Acceptor, receives a char and TokenList
     * @param tokenAcceptor Token Acceptor, receives a Token and TokenList
     * @param ignoreError   Predicate to ignore exceptions
     */
    public FutureSpec(int startIn, int amount, @Nullable BiPredicate<Character, @Named("Immutable token list") ITokenList> charAcceptor, @Nullable BiPredicate<IToken<?>, @Named("Immutable token list") ITokenList> tokenAcceptor, @Nullable BiPredicate<Optional<TokenBuilder>, @Named("Immutable token list") Throwable> ignoreError) {
        this.startIn = startIn;
        this.amount = amount;
        this.charAcceptor = charAcceptor;
        this.tokenAcceptor = tokenAcceptor;
        this.ignoreError = ignoreError;
    }

    public int getStartIn() {
        return startIn;
    }

    public int getAmount() {
        return amount;
    }

    public boolean accept(Character character, ITokenList tokenList) {
        return charAcceptor == null || charAcceptor.test(character, tokenList);
    }

    public boolean accept(IToken<?> token, ITokenList tokenList) {
        return tokenAcceptor == null || tokenAcceptor.test(token, tokenList);
    }

    public boolean acceptError(Optional<TokenBuilder> data, Throwable ex) {
        return ignoreError == null || ignoreError.test(data, ex);
    }
}
