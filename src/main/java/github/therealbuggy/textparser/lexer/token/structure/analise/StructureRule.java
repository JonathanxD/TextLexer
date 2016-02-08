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
package github.therealbuggy.textparser.lexer.token.structure.analise;

import github.therealbuggy.textparser.lexer.token.IToken;

/**
 * Created by jonathan on 07/02/16.
 */
public interface StructureRule {

    default Class<? extends IToken> after() {
        return null;
    }

    default Class<? extends IToken> before() {
        return null;
    }

    default int dataLength() {
        return -1;
    }

    default boolean valueRule() {
        return getToken().dataToValue() != null;
    }

    IToken<?> getToken();
}
