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
package com.github.jonathanxd.textlexer.ext.reconstructor.data;

import com.github.jonathanxd.textlexer.lexer.token.IToken;

import java.util.List;

/**
 * Created by jonathan on 08/02/16.
 */
@FunctionalInterface
public interface IDataConstructor extends Comparable<IDataConstructor> {

    String getTokenData(IToken<?> token);

    default String getTokenData(IToken<?> token, String currentData, List<IToken<?>> tokenList, int index) {
        return getTokenData(token);
    }

    default boolean canTranslate(IToken<?> token) {
        return true;
    }


    default int priority() {
        return 5;
    }

    @Override
    default int compareTo(IDataConstructor o) {
        int compare;
        if ((compare = Integer.compare(this.priority(), o.priority())) == 0) {
            return 1;
        }
        return compare;
    }
}
