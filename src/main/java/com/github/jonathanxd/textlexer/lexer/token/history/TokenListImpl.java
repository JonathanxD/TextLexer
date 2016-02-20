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
import com.github.jonathanxd.textlexer.lexer.token.history.analise.ElementSpecification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Created by jonathan on 30/01/16.
 */
public class TokenListImpl implements ITokenList {

    final List<IToken<?>> tokenList = new ArrayList<>();
    final List<IToken> visibleTokens = new ArrayList<>();

    @Override
    public void add(IToken token) {
        tokenList.add(token);
        if (!token.hide()) {
            visibleTokens.add(token);
        }
    }

    @Override
    public IToken fetch(int inverseIndex) {
        int index = (visibleTokens.size() - 1) - inverseIndex;
        if (index < 0) {
            throw new IndexOutOfBoundsException(String.format("Size: %d. Inverse Index: (-)%d. Index: %d", tokenList.size(), inverseIndex, index));
        }

        return visibleTokens.get(index);
    }

    @Override
    public int size() {
        return visibleTokens.size();
    }

    @Override
    public String toString() {
        return visibleTokens.toString();
    }

    @Override
    public IToken fetchLast() {
        return visibleTokens.get(visibleTokens.size() - 1);
    }

    @Override
    public IToken find(Class<? extends IToken> tokenClass, Class<? extends IToken> stopAt, LoopDirection loopDirection) {
        return find(ElementSpecification.classCollectionSpec(Collections.singleton(tokenClass), Collections.singleton(stopAt), loopDirection));
    }

    @Override
    public IToken find(ElementSpecification elementSpecification) {

        LoopDirection loopDirection = elementSpecification.getDirection();

        Function<IToken<?>, State> tokenConsumer = (token) -> {

            if (elementSpecification.testFindToken(token)) {
                return State.OK;
            }

            if (elementSpecification.testStopToken(token)) {
                return State.BREAK;
            }
            return State.CONTINUE;
        };

        if (loopDirection == LoopDirection.FIRST_TO_LAST) {
            for (int x = size() - 1; x > -1; --x) {
                IToken<?> token = fetch(x);

                State state = tokenConsumer.apply(token);

                if (state == State.OK) {
                    return token;
                }

                if (state == State.BREAK) {
                    break;
                }
            }
        } else if (loopDirection == LoopDirection.LAST_TO_FIRST) {
            for (int x = 0; x < size(); ++x) {
                IToken<?> token = fetch(x);

                State state = tokenConsumer.apply(token);

                if (state == State.OK) {
                    return token;
                }

                if (state == State.BREAK) {
                    break;
                }
            }
        }

        return null;
    }

    @Override
    public List<IToken<?>> allToList() {
        return Collections.unmodifiableList(tokenList);
    }

    private enum State {
        BREAK,
        OK,
        CONTINUE
    }
}
