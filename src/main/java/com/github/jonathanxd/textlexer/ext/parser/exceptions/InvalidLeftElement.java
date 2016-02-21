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
 * Occurs when the Left-Side Element of List doesn't match the expected Element.
 */
public class InvalidLeftElement extends RuntimeException {
    public InvalidLeftElement() {
        super();
    }

    public InvalidLeftElement(String message) {
        super(message);
    }

    public InvalidLeftElement(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLeftElement(Throwable cause) {
        super(cause);
    }

    protected InvalidLeftElement(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
