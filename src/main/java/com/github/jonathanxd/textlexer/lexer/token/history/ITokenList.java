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
package com.github.jonathanxd.textlexer.lexer.token.history;

import com.github.jonathanxd.textlexer.lexer.token.IToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by jonathan on 30/01/16.
 */
public interface ITokenList {

    void add(IToken token);

    IToken fetch(int inverseIndex);

    IToken fetchLast();

    int size();

    IToken find(Class<? extends IToken> tokenClass, Class<? extends IToken> stopAt, LoopDirection loopDirection);

    @SuppressWarnings("unchecked")
    default <T, R extends IToken<T>> Optional<R> first(Class<? extends IToken<T>> tokenClass) {
        return this.<T, R>all(tokenClass).stream().findFirst();
    }

    @SuppressWarnings("unchecked")
    default <T, R extends IToken<T>> Optional<R> last(Class<? extends IToken<T>> tokenClass) {
        AtomicReference<R> findToken = new AtomicReference<>();

        forEach(token -> {
            if (token.getClass() == tokenClass) {
                findToken.set((R) token);
            }
        }, LoopDirection.LAST_TO_FIRST, token -> findToken.get() == null);

        return Optional.ofNullable(findToken.get());
    }

    @SuppressWarnings("unchecked")
    default <T, R extends IToken<T>> Collection<R> all(Class<? extends IToken<T>> tokenClass) {
        Collection<R> tokens = new ArrayList<>();

        forEach(token -> {
            if (token.getClass() == tokenClass) {
                tokens.add((R) token);
            }
        }, LoopDirection.LAST_TO_FIRST);

        return tokens;
    }

    @SuppressWarnings("unchecked")
    default <T, R extends IToken<T>> Collection<R> allAssignable(Class<? extends IToken<T>> tokenClass) {
        Collection<R> tokens = new ArrayList<>();

        forEach(token -> {
            if (tokenClass.isAssignableFrom(token.getClass())) {
                tokens.add((R) token);
            }
        }, LoopDirection.LAST_TO_FIRST);

        return tokens;
    }

    default void forEach(Consumer<IToken<?>> tokenConsumer) {
        forEach(tokenConsumer, LoopDirection.FIRST_TO_LAST, token -> true);
    }

    default void forEach(Consumer<IToken<?>> tokenConsumer, LoopDirection loopDirection) {
        forEach(tokenConsumer, loopDirection, token -> true);
    }

    default void forEach(Consumer<IToken<?>> tokenConsumer, Predicate<IToken<?>> while_) {
        forEach(tokenConsumer, LoopDirection.FIRST_TO_LAST, while_);
    }

    default void forEach(Consumer<IToken<?>> tokenConsumer, LoopDirection loopDirection, Predicate<IToken<?>> while_) {

        if (loopDirection == LoopDirection.FIRST_TO_LAST) {
            for (int x = size() - 1; x > -1; --x) {
                IToken<?> token = fetch(x);

                if (while_.test(token)) {
                    tokenConsumer.accept(token);
                }
            }
        } else if (loopDirection == LoopDirection.LAST_TO_FIRST) {
            for (int x = 0; x < size(); ++x) {
                IToken<?> token = fetch(x);

                if (while_.test(token)) {
                    tokenConsumer.accept(token);
                }
            }
        }
    }

    default List<IToken<?>> toList() {
        List<IToken<?>> list = new ArrayList<>();
        forEach(list::add);
        return list;
    }

    List<IToken<?>> allToList();
}
