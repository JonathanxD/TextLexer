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
package com.github.jonathanxd.textlexer.ext.parser.processor.rule.requeriments;

import com.github.jonathanxd.textlexer.lexer.token.IToken;

import java.util.ArrayList;
import java.util.List;

import static com.github.jonathanxd.textlexer.ext.parser.processor.rule.requeriments.Requirements.RequireState;

/**
 * Created by jonathan on 20/02/16.
 */
public class Requirement {

    /**
     * Required token factory
     */
    private final Class<? extends IToken> tokenClass;
    /**
     * Required state
     */
    private final RequireState state;
    /**
     * Operators
     */
    private final List<Operator> ops = new ArrayList<>();

    /**
     * Create a new requirement
     * @param tokenClass Token Class
     * @param op Operator
     */
    public Requirement(Class<? extends IToken> tokenClass, Operator op) {
        this.tokenClass = tokenClass;
        this.state = null;
        this.ops.add(op);
    }

    /**
     * Create a new state requirement
     * @param state State
     * @param op Operator
     */
    public Requirement(RequireState state, Operator op) {
        this.tokenClass = null;
        this.state = state;
        this.ops.add(op);
    }

    /**
     * Get the State
     * @return State
     */
    public RequireState getState() {
        return state;
    }

    /**
     * Get operators
     * @return Operators
     */
    public List<Operator> getOps() {
        return ops;
    }

    /**
     * Get required token class
     * @return Required Token class
     */
    public Class<? extends IToken> getTokenClass() {
        return tokenClass;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        String list = ops.toString();

        sb.append(list.substring(1, list.length() - 1)).append(" ");

        if (tokenClass != null) {
            sb.append("Class ").append(tokenClass.getCanonicalName());
        }

        if (state != null) {
            sb.append("State ").append(state.toString());
        }


        return sb.toString();
    }
}
