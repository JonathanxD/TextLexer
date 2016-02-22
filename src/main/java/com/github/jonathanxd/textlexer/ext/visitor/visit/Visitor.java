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
package com.github.jonathanxd.textlexer.ext.visitor.visit;

import com.github.jonathanxd.textlexer.ext.parser.holder.TokenHolder;
import com.github.jonathanxd.textlexer.lexer.token.IToken;

/**
 * Created by jonathan on 20/02/16.
 */

/**
 * Visitor call all Listeners via Reflection.
 */
public interface Visitor<T> {

    /**
     * Visit a Token.
     * @param token Token to visit
     */
    default void visit(IToken<?> token) {
        visit(TokenHolder.of(token.getSimpleName(), null, token));
    }

    /**
     * Exit from a Token.
     *
     * @param token Token to Exit
     */
    default void exit(IToken<?> token) {
        exit(TokenHolder.of(token.getSimpleName(), null, token));
    }

    /**
     * Call TokenHolder Visit (normally will call methods of listener via Reflection)
     * @param tokenHolder TokenHolder to visit
     */
    void visit(TokenHolder tokenHolder);

    /**
     * Call TokenHolder Exit (normally will call methods of listener via Reflection)
     * @param tokenHolder TokenHolder to exit
     */
    void exit(TokenHolder tokenHolder);

    /**
     * Add a listener to handle visits
     *
     * @see com.github.jonathanxd.textlexer.ext.visitor.listener.ListenerFor
     * @param listener Listener Object
     */
    void addListener(Object listener);

    /**
     * Remove a Listener
     *
     * @param listener Listener
     */
    void removeListener(Object listener);

    /**
     * Start visiting all elements
     *
     * @param visit Element(s) to visit
     */
    default void visit(T visit) {
        visit();
    }

    /**
     * Visit default elements.
     */
    void visit();
}
