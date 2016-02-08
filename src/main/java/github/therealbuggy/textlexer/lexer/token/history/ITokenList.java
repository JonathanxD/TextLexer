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

import java.util.function.Consumer;

import github.therealbuggy.textlexer.lexer.token.IToken;

/**
 * Created by jonathan on 30/01/16.
 */
public interface ITokenList {

    void add(IToken token);

    IToken fetch(int inverseIndex);

    IToken fetchLast();

    int size();

    IToken find(Class<? extends IToken> tokenClass, Class<? extends IToken> stopAt, LoopDirection loopDirection);

    default void forEach(Consumer<IToken<?>> tokenConsumer) {
        for(int x = size()-1; x > -1; --x) {
            tokenConsumer.accept(fetch(x));
        }
    }
}
