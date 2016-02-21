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
package com.github.jonathanxd.textlexer.ext.constructor.data;

import com.github.jonathanxd.textlexer.lexer.token.IToken;

import java.util.List;

/**
 * Created by jonathan on 08/02/16.
 */
@FunctionalInterface
public interface IDataConstructor extends Comparable<IDataConstructor> {

    /**
     * Get data of Specific Token
     *
     * @param token Token
     * @return Data
     * @see IToken#getData()
     */
    String getTokenData(IToken<?> token);

    /**
     * Get token data based on specific states
     *
     * @param token       Token
     * @param currentData Current Data
     * @param tokenList   TokenList
     * @param index       Index of token in TokenList
     * @return Data
     */
    default String getTokenData(IToken<?> token, String currentData, List<IToken<?>> tokenList, int index) {
        return getTokenData(token);
    }

    /**
     * Determine if this DataConstructor can extract data of Token
     *
     * @param token Token
     * @return True if it do
     */
    default boolean canTranslate(IToken<?> token) {
        return true;
    }

    /**
     * Priority of this DataConstructor
     *
     * @return Priority
     */
    default int priority() {
        return 5;
    }

    /**
     * Compare priorities
     *
     * @param o Another DataConstructor to compare priority
     * @return Comparison of priorities
     */
    @Override
    default int compareTo(IDataConstructor o) {
        int compare;
        if ((compare = Integer.compare(this.priority(), o.priority())) == 0) {
            return 1;
        }
        return compare;
    }
}
