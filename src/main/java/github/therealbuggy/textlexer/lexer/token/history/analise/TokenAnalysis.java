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
package github.therealbuggy.textlexer.lexer.token.history.analise;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import github.therealbuggy.textlexer.lexer.token.IToken;

/**
 * Created by jonathan on 08/02/16.
 */
public class TokenAnalysis {

    public static IToken<?> find(List<IToken<?>> tokenList, int startAt, SearchDirection searchDirection, Class<? extends IToken> tokenToFind, Class<? extends IToken> endAt) {
        Objects.requireNonNull(tokenToFind);

        Function<IToken<?>, State> tokenConsumer = (token) -> {
            if (tokenToFind.isAssignableFrom(token.getClass())) {
                return State.OK;
            }

            if (endAt != null && endAt.isAssignableFrom(token.getClass())) {
                return State.BREAK;
            }
            return State.CONTINUE;
        };

        if (searchDirection == SearchDirection.LEFT) {
            for (int x = startAt; x > -1; --x) {
                IToken<?> token = tokenList.get(x);

                State state = tokenConsumer.apply(token);

                if (state == State.OK) {
                    return token;
                }

                if (state == State.BREAK) {
                    break;
                }
            }
        } else if (searchDirection == SearchDirection.RIGHT) {
            for (int x = startAt; x < tokenList.size(); ++x) {
                IToken<?> token = tokenList.get(x);

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

    private enum State {
        BREAK,
        OK,
        CONTINUE
    }
}
