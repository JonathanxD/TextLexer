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
package com.github.jonathanxd.textlexer.ext.constructor.structure;

import com.github.jonathanxd.iutils.map.Map;
import com.github.jonathanxd.textlexer.ext.common.TokenElementType;
import com.github.jonathanxd.textlexer.lexer.token.IToken;

import java.util.List;

/**
 * Created by jonathan on 21/02/16.
 */
public interface PositionFactory {

    Map<IToken<?>, Position> getPositionOfToken(List<IToken<?>> tokenList, TokenElementType type);

    final PositionFactory DEFAULT = new Default();
    final PositionFactory DIVISOR = new Divisor();

    class Default implements PositionFactory {

        @Override
        public Map<IToken<?>, Position> getPositionOfToken(List<IToken<?>> tokenList, TokenElementType type) {
            Map<IToken<?>, Position> map = new Map<>();

            for (int x = 0; x < tokenList.size(); ++x) {
                IToken<?> token = tokenList.get(x);
                if (x == 0) {
                    map.put(token, Position.START);
                } else {
                    map.put(token, Position.END);
                }
            }

            return map;
        }
    }

    class Divisor implements PositionFactory {

        @Override
        public Map<IToken<?>, Position> getPositionOfToken(List<IToken<?>> tokenList, TokenElementType type) {

            Map<IToken<?>, Position> map = new Map<>();
            int middle = tokenList.size() / 2;

            for (int x = 0; x < tokenList.size(); ++x) {
                IToken<?> token = tokenList.get(x);

                if (x < middle) {
                    map.put(token, Position.START);
                } else {
                    map.put(token, Position.END);
                }
            }

            return map;

        }
    }
}
