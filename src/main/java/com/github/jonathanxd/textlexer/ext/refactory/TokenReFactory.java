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
package com.github.jonathanxd.textlexer.ext.refactory;

import com.github.jonathanxd.textlexer.ext.refactory.listener.ReFactoryListener;
import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.history.ITokenList;
import com.github.jonathanxd.textlexer.lexer.token.history.TokenListImpl;
import com.github.jonathanxd.textlexer.lexer.token.history.list.CommonTokenList;
import com.github.jonathanxd.textlexer.util.Targets;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by jonathan on 09/03/16.
 */

/**
 * Token ReFactory organizes and parse tokens again to fix common problems during Lex.
 *
 * If you found a bug in the Lex organization system, or limitations, this class is considered a
 * **temporary** alternative. Please create a issue to me fix and improve the Lex system (It is the
 * central purpose of the TextLexer)
 */
public class TokenReFactory {

    private final ITokenList tokenList;
    private final Set<ReFactoryListener> listeners = new HashSet<>();

    public TokenReFactory(ITokenList tokenList) {
        this.tokenList = tokenList;
    }

    public static ITokenList assembleWhenPossible(ITokenList tokenList, Function<IToken<?>, Integer> maxSizeFunction, Predicate<IToken<?>> trashPredicate) {

        CommonTokenList commonTokenList = tokenList.allToList();
        ITokenList newTokenList = new TokenListImpl();

        IToken<?> last = null;
        IToken<?> next = null;

        for (int x = 0; x < commonTokenList.size(); ++x) {
            if (x - 1 > -1) {
                last = commonTokenList.get(x - 1);
            } else {
                last = null;
            }

            IToken<?> current = commonTokenList.get(x);
            final int maxSize = maxSizeFunction.apply(current);

            if (x + 1 < commonTokenList.size()) {
                next = commonTokenList.get(x + 1);
            } else {
                next = null;
            }


            if (next != null) {
                if (current.getClass() == next.getClass()) {
                    // len = 4
                    // maxsize = 5
                    // 1 OK!
                    if (current.mutableData().get().length() < maxSize) {
                        int transferAmount = current.mutableData().get().length() - maxSize;
                        transfer(Targets.<IToken<?>>fromA(next).to(current), transferAmount);
                    }
                }
            }

            if (current.mutableData().get().isEmpty()) {
                if (trashPredicate.test(current)) {
                    continue;
                }
            }

            newTokenList.add(current);

        }


        return newTokenList;
    }

    private static void transfer(Targets<IToken<?>> targets, int size) {

        IToken<?> source = targets.getFrom();
        IToken<?> target = targets.getTo();

        target.mutableData().set((original) -> original + source.mutableData().get().substring(0, size));
        source.mutableData().set((original) -> original.substring(size, original.length()));
    }

    public void register(ReFactoryListener listener) {
        listeners.add(listener);
    }

    public void unregister(ReFactoryListener listener) {
        listeners.remove(listener);
    }

    public ITokenList reFactory(ITokenList iTokenList) {
        CommonTokenList tokens = CommonTokenList.immutable(iTokenList.allToList());

        ITokenList targetList = new TokenListImpl();

        for (int x = 0; x < tokens.size(); ++x) {
            IToken<?> token = tokens.get(x);

            for (ReFactoryListener reFactoryListener : listeners) {

                if (reFactoryListener.target().isAssignableFrom(token.getClass())) {
                    IToken<?> returned = reFactoryListener.factory(token, x, tokens, iTokenList);

                    if (returned != null) {
                        if (!returned.equals(token)) {
                            targetList.add(returned);
                        } else {
                            targetList.add(token);
                        }
                    }
                    break;
                }
            }
        }
        return targetList;

    }


}
