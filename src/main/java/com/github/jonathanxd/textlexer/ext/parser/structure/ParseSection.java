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
package com.github.jonathanxd.textlexer.ext.parser.structure;

import com.github.jonathanxd.textlexer.ext.parser.holder.TokenHolder;
import com.github.jonathanxd.textlexer.ext.parser.structure.exceptions.EmptyCheckException;
import com.github.jonathanxd.textlexer.lexer.token.IToken;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by jonathan on 21/02/16.
 */

/**
 * Parsing Section, organize and create a Structure of Tokens
 */
public class ParseSection {
    /**
     * Current TokenHolders
     */
    Deque<TokenHolder> current = new LinkedList<>();

    /**
     * StructuredTokens instance
     */
    private StructuredTokens structure;

    /**
     * Create new ParseSection
     * @param structure ParseSection
     */
    protected ParseSection(StructuredTokens structure) {
        this.structure = structure;
    }

    /**
     * Link Token to current
     * @param token Token
     * @return new TokenHolder created for Token
     */
    public TokenHolder link(IToken<?> token) {
        currentHolderCheck("No current TokenHolder to link, you should use link(TokenHolder, IToken) instead!");
        TokenHolder parent = current.getLast();
        TokenHolder newHolder = parent.link(token, parent);
        keepEnter(newHolder);
        return newHolder;
    }

    /**
     * Check current TokenHolder
     * @param message Exception message
     */
    private void currentHolderCheck(String message) {
        if (current.isEmpty()) {
            throw new EmptyCheckException(message, new NullPointerException());
        }
    }

    /**
     * Stack a Token to Current. Adding to List and Exit from the TokenHolder.
     * @param token Token
     */
    public void stack(IToken<?> token) {
        createOrLink(token);
        exit();
    }

    /**
     * Create if don't have current Holder, or link to current otherwise.
     * @param token Token
     */
    public void createOrLink(IToken<?> token) {
        if (!hasCurrent())
            enter(token);
        else
            link(token);
    }

    /**
     * Cleanup current process and enter in a TokenHolder
     * @param tokenHolder TokenHodler to enter
     */
    public void enter(TokenHolder tokenHolder) {
        enter(tokenHolder, true);
    }

    /**
     * Cleanup current process, create TokenHolder from Token and enter the created TokenHolder
     * @param token Token
     */
    public void enter(IToken<?> token) {
        TokenHolder parent = hasCurrent() ? getCurrent() : null;
        enter(structure.addToken(token, parent), true);
    }

    /**
     * Soft enter does the same of the {@link #enter(TokenHolder)} but doesn't cleanup current tokens.
     * and this makes the {@link #exit()} can do more exits, but, the code can access already processed TokenHolders and modify them, it is not good.
     *
     * @param tokenHolder TokenHolder
     */
    public void softEnter(TokenHolder tokenHolder) {
        keepEnter(tokenHolder);
    }

    /**
     * Soft enter does the same of the {@link #enter(IToken)} but doesn't cleanup current tokens.
     * and this makes the {@link #exit()} can do more exits, but, the code can access already processed TokenHolders and modify them, it is not good.
     *
     * @param token Token
     */
    public void softEnter(IToken<?> token) {
        TokenHolder parent = hasCurrent() ? getCurrent() : null;
        keepEnter(structure.addToken(token, parent));
    }

    /**
     * @see #softEnter(TokenHolder)
     */
    private void keepEnter(TokenHolder tokenHolder) {
        enter(tokenHolder, false);
    }

    /**
     * Return Current TokenHolder
     * @return Current TokenHolder
     */
    public TokenHolder getCurrent() {
        return !hasCurrent() ? null : current.getLast();
    }

    /**
     * Enter in a TokenHolder
     * @param tokenHolder TokenHolder
     * @param cleanup Cleanup current TokenHolder list
     */
    private void enter(TokenHolder tokenHolder, boolean cleanup) {
        if (cleanup) current.clear();

        current.addLast(tokenHolder);
    }

    /**
     * Return true if have current TokenHolders
     * @return true if have current TokenHolders
     */
    public boolean hasCurrent() {
        return !current.isEmpty();
    }

    /**
     * Return true if can exit current element.
     * @see #hasCurrent()
     * @return {@link #hasCurrent()}
     */
    public boolean canExit() {
        return hasCurrent();
    }

    /**
     * Exit current TokenHolder and return that.
     *
     * @return TokenHolder removed
     */
    public TokenHolder exit() {
        currentHolderCheck("Cannot exit, no more TokenHolders!");
        return current.pollLast();
    }

}
