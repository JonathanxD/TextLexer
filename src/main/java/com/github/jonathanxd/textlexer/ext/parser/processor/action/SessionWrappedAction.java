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
package com.github.jonathanxd.textlexer.ext.parser.processor.action;

import com.github.jonathanxd.textlexer.ext.parser.structure.ParseSection;
import com.github.jonathanxd.textlexer.lexer.token.IToken;

import java.util.List;

/**
 * Created by jonathan on 20/02/16.
 */

/**
 * SessionWrappedAction is a class that holds an {@link ParseSection}
 * and use a new method to handle actions
 */
public abstract class SessionWrappedAction implements Action {

    /**
     * Parse Section
     */
    private final ParseSection section;

    /**
     * Create a SessionWrappedAction
     *
     * @param section Session to hold
     */
    public SessionWrappedAction(ParseSection section) {
        this.section = section;
    }

    @Override
    public ProcessingState stateAction(IToken<?> token, ParseSection section, List<IToken<?>> tokenList, int index) {
        return stateAction(token);
    }

    /**
     * Handle token action
     *
     * @param token Token
     * @return ProcessingState
     * @see ProcessingState
     */
    public abstract ProcessingState stateAction(IToken<?> token);

    /**
     * Get Section
     *
     * @return Section
     */
    public ParseSection getSection() {
        return section;
    }

    /**
     * Wrapper
     */

    /**
     * @param token Token
     * @return This
     * @see ParseSection#link(IToken)
     */
    public SessionWrappedAction link(IToken<?> token) {
        section.link(token);
        return this;
    }

    /**
     * @param token Token
     * @return This
     * @see ParseSection#enter(IToken)
     */
    public SessionWrappedAction enter(IToken<?> token) {
        section.enter(token);
        return this;
    }

    /**
     * @param token Token
     * @return This
     * @see ParseSection#softEnter(IToken)
     */
    public SessionWrappedAction softEnter(IToken<?> token) {
        section.softEnter(token);
        return this;
    }

    /**
     * @param token Token
     * @return This
     * @see ParseSection#createOrLink(IToken)
     */
    public SessionWrappedAction createOrLink(IToken<?> token) {
        section.createOrLink(token);
        return this;
    }

    /**
     * @return This
     * @see ParseSection#exit()
     */
    public SessionWrappedAction exit() {
        section.exit();
        return this;
    }

}
