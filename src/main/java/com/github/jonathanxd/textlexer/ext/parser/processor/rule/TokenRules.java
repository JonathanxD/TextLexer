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
package com.github.jonathanxd.textlexer.ext.parser.processor.rule;

import com.github.jonathanxd.textlexer.ext.parser.exceptions.InvalidLeftElement;
import com.github.jonathanxd.textlexer.ext.parser.exceptions.InvalidRightElement;
import com.github.jonathanxd.textlexer.ext.parser.processor.action.Actions;
import com.github.jonathanxd.textlexer.ext.parser.processor.rule.requeriments.Operator;
import com.github.jonathanxd.textlexer.ext.parser.processor.rule.requeriments.Requirement;
import com.github.jonathanxd.textlexer.ext.parser.processor.rule.requeriments.Requirements;
import com.github.jonathanxd.textlexer.lexer.token.IToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * TokenRules.
 *
 * Requirements can have only one {@link Operator#FIRST} element.
 */
public class TokenRules {
    /**
     * List of Left-Side-of-List requirements
     */
    private final List<Requirements> leftRequirements = new ArrayList<>();
    /**
     * List of Right-Side-of-List requirements
     */
    private final List<Requirements> rightRequirements = new ArrayList<>();
    /**
     * Left index of requirements
     */
    private int leftIndex = -1;
    /**
     * Right index of requirements
     */
    private int rightIndex = -1;
    /**
     * Actions
     *
     * @see Actions
     */
    private Actions actions = null;

    /**
     * Define right requirements
     *
     * @param requirements Requirements
     * @return This
     */
    public TokenRules right(Requirements requirements) {
        if (requirements != null) {
            rightRequirements.add(requirements);
            check(rightRequirements);
        }
        return this;
    }

    /**
     * Validate the Requirement list
     *
     * @param requirementsList List
     * @return This
     */
    private TokenRules check(List<Requirements> requirementsList) {
        if (requirementsList.stream().filter(requirements -> requirements.getRequirements().stream().filter(requirement -> requirement.getOps().contains(Operator.FIRST)).findAny().isPresent()
        ).count() > 1) {
            throw new UnsupportedOperationException("Multiple FIRST Requirement is not supported.");
        }
        return this;
    }

    /**
     * Define requirements of Right-Side-of-List
     *
     * @param requirement Requirement
     * @param addRightIf  Add only if predicate accepts the current requirements
     * @return This
     */
    public TokenRules right(Requirements requirement, Predicate<Collection<Requirements>> addRightIf) {
        if (addRightIf.test(Collections.unmodifiableList(rightRequirements))) {
            right(requirement);
        }
        return this;
    }

    /**
     * Define requirements of Right-Side-of-List
     *
     * @param requirements Requirements Collection
     * @param addRightIf   Add only if predicate accepts the current requirements
     * @return This
     */
    public TokenRules right(Collection<Requirements> requirements, Predicate<Collection<Requirements>> addRightIf) {
        if (addRightIf.test(Collections.unmodifiableList(rightRequirements))) {
            right(requirements);
        }
        return this;
    }

    /**
     * Define requirements of Right-Side-of-List
     *
     * @param requirements Requirements Collection
     * @return This
     */
    public TokenRules right(Collection<Requirements> requirements) {
        requirements.forEach(this::right);
        return this;
    }

    /**
     * Define requirements of Right-Side-of-List
     *
     * @param requirements Requirements Supplier
     * @return This
     */
    public TokenRules right(Supplier<Requirements> requirements) {
        right(requirements.get());
        return this;
    }

    /**
     * Define requirements of Right-Side-of-List
     *
     * @param requirements Apply current Requirements Collection and get result Requirements
     * @return This
     */
    public TokenRules right(Function<Collection<Requirements>, Requirements> requirements) {
        right(requirements.apply(Collections.unmodifiableCollection(rightRequirements)));
        return this;
    }

    /**********************************************************************************************/
    /**
     * Define requirements of Left-Side-of-List
     *
     * @param requirements Requirements
     * @return This
     */
    public TokenRules left(Requirements requirements) {
        if (requirements != null) {
            leftRequirements.add(requirements);
            check(leftRequirements);
        }
        return this;
    }

    /**
     * Define requirements of Left-Side-of-List
     *
     * @param requirement Requirement
     * @param addLeftIf   Add only if predicate accepts the current requirements
     * @return This
     */
    public TokenRules left(Requirements requirement, Predicate<Collection<Requirements>> addLeftIf) {
        if (addLeftIf.test(Collections.unmodifiableList(leftRequirements))) {
            left(requirement);
        }
        return this;
    }

    /**
     * Define requirements of Left-Side-of-List
     *
     * @param requirements Requirements Collection
     * @param addLeftIf    Add only if predicate accepts the current requirements
     * @return This
     */
    public TokenRules left(Collection<Requirements> requirements, Predicate<Collection<Requirements>> addLeftIf) {
        if (addLeftIf.test(Collections.unmodifiableList(leftRequirements))) {
            left(requirements);
        }
        return this;
    }

    /**
     * Define requirements of Left-Side-of-List
     *
     * @param requirements Requirements
     * @return This
     */
    public TokenRules left(Collection<Requirements> requirements) {
        requirements.forEach(this::left);
        return this;
    }

    /**
     * Define requirements of Left-Side-of-List
     *
     * @param requirements Requirements
     * @return This
     */
    public TokenRules left(Supplier<Requirements> requirements) {
        left(requirements.get());
        return this;
    }

    /**
     * Define requirements of Left-Side-of-List
     *
     * @param requirements Apply current Requirements Collection and get result Requirements
     * @return This
     */

    public TokenRules left(Function<Collection<Requirements>, Requirements> requirements) {
        left(requirements.apply(Collections.unmodifiableCollection(leftRequirements)));
        return this;
    }

    /**********************************************************************************************/
    /**
     * Change elements from Right List to Left List and Left List to Right
     *
     * @return This
     */
    public TokenRules swap() {
        List<Requirements> right = new ArrayList<>(rightRequirements);
        List<Requirements> left = new ArrayList<>(leftRequirements);

        // Left to Right
        rightRequirements.clear();
        rightRequirements.addAll(left);

        // Right to Left
        leftRequirements.clear();
        leftRequirements.addAll(right);

        return this;
    }

    /**
     * Set Actions
     *
     * @param actions Actions
     * @return Old Actions
     */
    public Optional<Actions> set(Actions actions) {
        Actions old = this.actions;

        this.actions = Objects.requireNonNull(actions);

        return Optional.ofNullable(old);
    }

    /**
     * Check if have more Left-Side-of-List requirements
     *
     * @return True if have more Left-Side-of-List requirements
     */
    public boolean moreLeftCheck() {
        return leftIndex + 1 < leftRequirements.size();
    }

    /**
     * Check if have more Right-Side-of-List requirements
     *
     * @return True if have more Right-Side-of-List requirements
     */
    public boolean moreRightCheck() {
        return leftIndex + 1 < leftRequirements.size();
    }

    /**
     * Apply elements to Left-Side-of-List
     *
     * @param token   Token
     * @param pointer Position of Token in list
     * @return True if apply
     */
    public boolean applyLeft(Class<? extends IToken> token, int pointer) {

        if (leftIndex + 1 >= leftRequirements.size() || leftRequirements.isEmpty()) {
            return true;
        }

        Requirements requirements = leftRequirements.get(++leftIndex);
        checkApply(leftIndex, requirements);

        if (requirements.apply(token, pointer)) {
            return true;
        } else {
            String required = requirements.toString();
            throw new InvalidLeftElement("Invalid LEFT, Found: '" + token + "'. Required: " + required);
            // throw exception ?
            //return false;
        }
    }

    /**
     * Apply elements to Right-Side-of-List
     *
     * @param token   Token
     * @param pointer Position of Token in list
     * @return True if apply
     */

    public boolean applyRight(Class<? extends IToken> token, int pointer) {

        if (rightIndex + 1 >= rightRequirements.size() || rightRequirements.isEmpty()) {
            return true;
        }

        Requirements requirements = rightRequirements.get(++rightIndex);

        checkApply(rightIndex, requirements);

        if (requirements.apply(token, pointer)) {
            return true;
        } else {
            String required = requirements.toString();
            throw new InvalidRightElement("Invalid RIGHT, Found: '" + token + "'. Required: " + required);
            // throw exception ?
            //return false;
        }
    }

    /**
     * Check {@link Operator#FIRST}
     *
     * @param index        Index
     * @param requirements Requirements
     */
    private void checkApply(int index, Requirements requirements) {
        List<Operator> op = new ArrayList<>();

        for (Requirement requirement : requirements.getRequirements()) {
            op.addAll(requirement.getOps());
        }

        if (index == 0 && !op.contains(Operator.FIRST)) {
            throw new IllegalStateException("The first element need to have FIRST Operator");
        } else if (index != 0 && op.contains(Operator.FIRST)) {
            throw new IllegalStateException("Only first element can have FIRST Operator");
        }
    }

    /**
     * Apply Left-Side-of-List from a list of tokens
     *
     * @param tokens Token List
     * @return true if success
     */
    public boolean applyLeftToList(List<IToken<?>> tokens) {
        return applyLeftToList(tokens.size() - 1, tokens);
    }

    /**
     * Apply Left-Side-of-List from a list of tokens
     *
     * @param tokens  Token List
     * @param pointer Index of Token
     * @return true if success
     */
    public boolean applyLeftToList(int pointer, List<IToken<?>> tokens) {
        for (int x = pointer; x > -1; --x) {
            if (!applyLeft(tokens.get(x).getClass(), pointer)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Apply Right-Side-of-List from a list of tokens
     *
     * @param tokens  Token List
     * @param pointer Index of Token
     * @return true if success
     */
    public boolean applyRightToList(int pointer, List<IToken<?>> tokens) {
        for (int x = pointer; x < tokens.size(); ++x) {
            if (!applyRight(tokens.get(pointer).getClass(), pointer)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Get Actions
     *
     * @return Actions
     * @see Actions
     */
    public Actions getActions() {
        return actions;
    }
}
