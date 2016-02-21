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
package com.github.jonathanxd.textlexer.ext.parser.exceptions;

/**
 * Created by jonathan on 20/02/16.
 */

/**
 * This Exception have a very similar structure to ActionDeterminateException, will occurs if the
 * Parser cannot Parse the Token.
 */
public class TokenParseException extends ActionDeterminateException {
    public TokenParseException() {
        super();
    }

    public TokenParseException(String message, String[] mark) {
        super(message, mark);
    }

    public TokenParseException(String message, String[] mark, Throwable cause) {
        super(message, mark, cause);
    }

    public TokenParseException(String[] mark, Throwable cause) {
        super(mark, cause);
    }

    protected TokenParseException(String message, String[] mark, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, mark, cause, enableSuppression, writableStackTrace);
    }
}
