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

import com.github.jonathanxd.textlexer.annotation.AnnotationUtil;
import com.github.jonathanxd.textlexer.annotation.Hide;
import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.history.analise.ElementSpecification;
import com.github.jonathanxd.textlexer.lexer.token.history.list.CommonTokenList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by jonathan on 30/01/16.
 */
public class TokenListImpl implements ITokenList {

    final List<IToken<?>> tokenList = new ArrayList<>();
    final List<IToken> visibleTokens = new ArrayList<>();

    @Override
    public void add(IToken token) {
        tokenList.add(token);
        if (!AnnotationUtil.isPresent(token.getClass(), Hide.class)) {
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
    public void updateVisible() {
        visibleTokens.clear();
        tokenList.forEach(token -> {
            if (!AnnotationUtil.isPresent(token.getClass(), Hide.class))
                visibleTokens.add(token);
        });
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
    public IToken fetchLast(boolean includeHidden) {
        if (!includeHidden)
            return fetchLast();

        return tokenList.get(tokenList.size() - 1);
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

        final int start = (elementSpecification.getStartIndex() > -1 && elementSpecification.getStartIndex() < size()
                ? elementSpecification.getStartIndex()
                : (loopDirection == LoopDirection.FIRST_TO_LAST ? size() - 1 : 0));

        if (loopDirection == LoopDirection.FIRST_TO_LAST) {
            for (int x = start; x > -1; --x) {
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
            for (int x = start; x < size(); ++x) {
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
    public void filter(Predicate<? super IToken<?>> tokenFilter) {
        List<IToken<?>> iTokenList = tokenList.stream().filter(tokenFilter).collect(Collectors.toList());
        tokenList.clear();
        tokenList.addAll(iTokenList);
        updateVisible();
    }

    @Override
    public void modify(ModifyFunction modifyFunction) {
        List<IToken<?>> localTokenList = new ArrayList<>(tokenList);
        for (int x = 0; x < tokenList.size(); ++x) {

            IToken<?> token = tokenList.get(x);
            modifyFunction.fix(token, localTokenList, x);
        }

        tokenList.clear();
        tokenList.addAll(localTokenList);

        updateVisible();
    }

    @Override
    public CommonTokenList allToList() {
        return CommonTokenList.immutable(tokenList);
    }

    @Override
    public ITokenList clone() {
        TokenListImpl tokenList = new TokenListImpl();
        tokenList.tokenList.addAll(this.tokenList.stream().collect(Collectors.toList()));

        tokenList.visibleTokens.clear();

        tokenList.visibleTokens.addAll(this.visibleTokens.stream().collect(Collectors.toList()));
        return tokenList;
    }

    private enum State {
        BREAK,
        OK,
        CONTINUE
    }
}
