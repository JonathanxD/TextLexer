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
package github.therealbuggy.textlexer.lexer.token.history;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import github.therealbuggy.textlexer.lexer.token.IToken;

/**
 * Created by jonathan on 30/01/16.
 */
public class TokenListImpl implements ITokenList {

    List<IToken> tokenList = new ArrayList<>();

    @Override
    public void add(IToken token) {
        tokenList.add(token);
    }

    @Override
    public IToken fetch(int inverseIndex) {
        int index = (tokenList.size() - 1) - inverseIndex;
        if (index < 0) {
            throw new IndexOutOfBoundsException(String.format("Size: %d. Inverse Index: (-)%d. Index: %d", tokenList.size(), inverseIndex, index));
        }

        return tokenList.get(index);
    }

    @Override
    public int size() {
        return tokenList.size();
    }

    @Override
    public String toString() {
        return tokenList.toString();
    }

    @Override
    public IToken fetchLast() {
        return tokenList.get(tokenList.size() - 1);
    }

    @Override
    public IToken find(Class<? extends IToken> tokenClass, Class<? extends IToken> stopAt, LoopDirection loopDirection) {
        Objects.requireNonNull(tokenClass);

        Function<IToken<?>, State> tokenConsumer = (token) -> {

            if(tokenClass.isAssignableFrom(token.getClass())) {
                return State.OK;
            }

            if(stopAt.isAssignableFrom(token.getClass())) {
                return State.BREAK;
            }
            return State.CONTINUE;
        };

        if(loopDirection == LoopDirection.FIRST_TO_LAST) {
            for (int x = tokenList.size() - 1; x > -1; --x) {
                IToken<?> token = fetch(x);

                State state = tokenConsumer.apply(token);

                if(state == State.OK) {
                    return token;
                }

                if(state == State.BREAK){
                    break;
                }
            }
        }else if(loopDirection == LoopDirection.LAST_TO_FIRST) {
            for (int x = 0; x < tokenList.size(); ++x) {
                IToken<?> token = fetch(x);

                State state = tokenConsumer.apply(token);

                if(state == State.OK) {
                    return token;
                }

                if(state == State.BREAK){
                    break;
                }
            }
        }

        return null;
    }

    private enum State {
        BREAK,
        OK,
        CONTINUE
    }
}
