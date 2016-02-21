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

import com.github.jonathanxd.iutils.object.ObjectUtils;

/**
 * Created by jonathan on 20/02/16.
 */

/**
 * Occurs when the Parser cannot determine a Action of a TokenHolder
 */
public class ActionDeterminateException extends RuntimeException {

    public ActionDeterminateException() {
        super();
    }

    public ActionDeterminateException(String message, String[] mark) {
        super(message.concat(ObjectUtils.strip(mark, java.util.Objects::toString)));
    }

    public ActionDeterminateException(String message, String[] mark, Throwable cause) {
        super(message.concat(ObjectUtils.strip(mark, java.util.Objects::toString)), cause);
    }

    public ActionDeterminateException(String[] mark, Throwable cause) {
        super(ObjectUtils.strip(mark, java.util.Objects::toString), cause);
    }

    protected ActionDeterminateException(String message, String[] mark, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message.concat(ObjectUtils.strip(mark, java.util.Objects::toString)), cause, enableSuppression, writableStackTrace);
    }
}
