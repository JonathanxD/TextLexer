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

/**
 * Created by jonathan on 20/02/16.
 */
public class Requirements {

    /**
     * Requirement list
     */
    private List<Requirement> requirements = new ArrayList<>();

    /**
     * Create new instance of Requirements
     *
     * @return New instance of Requirements
     */
    public static Requirements newInstance() {
        return new Requirements();
    }

    /**
     * Require a class
     *
     * @param tokenClass Class to require
     * @return This
     */
    public Requirements require(Class<? extends IToken> tokenClass) {
        return require(tokenClass, Operator.FIRST);
    }

    /**
     * Require a State
     *
     * @param state State
     * @return This
     */
    public Requirements require(RequireState state) {
        return require(state, Operator.FIRST);
    }

    /**
     * Require a class and define operator
     *
     * @param tokenClass Class
     * @param operator   Operator
     * @return This
     */
    public Requirements require(Class<? extends IToken> tokenClass, Operator operator) {
        requirements.add(new Requirement(tokenClass, operator));
        return this;
    }

    /**
     * Define requirement and operator
     *
     * @param requirement Requirement
     * @param operator    Operator
     * @return This
     */
    public Requirements require(Requirement requirement, Operator operator) {
        requirement.getOps().add(operator);
        requirements.add(requirement);
        return this;
    }

    /**
     * Create a State requirement and define operator
     *
     * @param state    State
     * @param operator Operator
     * @return This
     */
    public Requirements require(RequireState state, Operator operator) {
        requirements.add(new Requirement(state, operator));
        return this;
    }

    /**
     * Require other Class/State instead
     *
     * @param requirement Requirement
     * @return This
     */
    public Requirements or(Requirement requirement) {
        return require(requirement, Operator.OR);
    }

    /**
     * Require other Class instead
     *
     * @param tokenClass Required class
     * @return This
     */
    public Requirements or(Class<? extends IToken> tokenClass) {
        return require(tokenClass, Operator.OR);
    }

    /**
     * Require other State instead
     *
     * @param state Required state
     * @return This
     */

    public Requirements or(RequireState state) {
        return require(state, Operator.OR);
    }

    /**
     * @see Operator#AND
     */
    @Deprecated
    public Requirements and(/*Requirement requirement*/) {
        throw new UnsupportedOperationException("Read the documentation");
    }

    /**
     * Get all requirements
     *
     * @return Requirements
     */
    public List<Requirement> getRequirements() {
        return requirements;
    }

    @Override
    public String toString() {

        if (requirements.isEmpty())
            return "?";

        StringBuilder sb = new StringBuilder();

        sb.append("Require ").append(requirements.get(0));

        for (int x = 1; x < requirements.size(); ++x) {
            sb.append(" ").append(requirements.get(x));
        }

        return sb.toString();
    }

    /**
     * Process requirements
     *
     * @param token   Token
     * @param pointer Location of the Token in List
     * @return Position of token in list
     */
    public boolean apply(Class<? extends IToken> token, int pointer) {

        boolean last = true;
        for (Requirement requirement : requirements) {

            if (pointer <= 0 && (requirement.getState() != null && requirement.getState() == RequireState.EMPTY))
                return true;

            boolean check = check(token, requirement);

            if (requirement.getOps().contains(Operator.FIRST)) {
                last = check;
            } else {
                if (requirement.getOps().contains(Operator.AND)) {
                    last = last && check;
                } else if (requirement.getOps().contains(Operator.OR)) {
                    last = last || check;
                }
            }
        }
        return last;
    }

    /**
     * Check a requirement
     *
     * @param token       Token
     * @param requirement Requirement
     * @return True if requirement is ok
     */
    private boolean check(Class<? extends IToken> token, Requirement requirement) {
        if (token == null && (requirement.getTokenClass() == null && requirement.getState() == RequireState.EMPTY)) {
            return true;
        }

        return requirement.getTokenClass() == token;

    }

    /**
     * State
     */
    public enum RequireState {
        EMPTY
    }
}
