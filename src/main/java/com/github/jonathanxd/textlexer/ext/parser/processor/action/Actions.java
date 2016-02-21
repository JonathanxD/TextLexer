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

import com.github.jonathanxd.iutils.annotations.Immutable;
import com.github.jonathanxd.textlexer.ext.parser.exceptions.NullActionInList;
import com.github.jonathanxd.textlexer.ext.parser.processor.OptionProcessor;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.DefaultOptions;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.StructureOptions;
import com.github.jonathanxd.textlexer.ext.parser.structure.ParseSection;
import com.github.jonathanxd.textlexer.lexer.token.IToken;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by jonathan on 19/02/16.
 */
public class Actions {
    /**
     * Actions Deque
     */
    private final Queue<Action> actions = new ArrayDeque<>();

    /**
     * Create new instance of actions
     *
     * @return New instance of actions
     */
    public static Actions newInstance() {
        return new Actions();
    }

    /**
     * Return Action from OptionProcessor
     *
     * @param processor OptionProcessor
     * @return Action
     * @see Action
     * @see OptionProcessor
     */
    public static Action fromOptionProcessor(OptionProcessor processor) {
        return new OptionAction(processor);
    }

    /**
     * Offer a action to list
     *
     * @param action Action
     * @return This
     */
    public Actions offer(Action action) {
        actions.offer(action);
        return this;
    }

    /**
     * Offer a action to list
     *
     * @param action Action
     * @return This
     */
    public Actions offer(Supplier<Action> action) {
        offer(action.get());
        return this;
    }

    /**
     * Offer a action to list, the function will receive a Immutable list of current actions
     *
     * @param action Action
     * @return This
     */
    public Actions offer(@Immutable Function<Collection<Action>, Action> action) {
        offer(action.apply(actions));
        return this;
    }

    /**
     * Offer a action to list, but, test the current actions
     *
     * @param action  Action
     * @param offerIf Test Predicate
     * @return This
     */
    public Actions offer(Action action, @Immutable Predicate<Collection<Action>> offerIf) {
        if (offerIf.test(Collections.unmodifiableCollection(actions))) {
            offer(action);
        }
        return this;
    }

    /**
     * Do all actions
     *
     * @param token     Current token
     * @param section   Section
     * @param tokenList Token list
     * @param index     Index of current Token
     * @return The most important state (higher ordinal)
     */
    public ProcessingState doAll(IToken<?> token, ParseSection section, List<IToken<?>> tokenList, int index) {
        AtomicReference<ProcessingState> state = new AtomicReference<>();

        actions.forEach(action -> {
            if (action == null)
                throw new NullActionInList("List: " + tokenList.toString());
            ProcessingState otherState = action.stateAction(token, section, tokenList, index);
            if (state.get() == null) {
                state.set(otherState);
            } else {
                if (state.get().compareTo(otherState) >= -1) {
                    state.set(otherState);
                }
            }
        });

        return state.get();
    }

    /**
     * Get action from class reference
     *
     * @param typeClass Class Reference
     * @return Action or Null
     */
    @SuppressWarnings("unchecked")
    public Action getAction(Class<?> typeClass) {

        for (Action action : actions) {

            if (typeClass.isAssignableFrom(action.getClass())) {
                return action;
            }
        }

        return null;
    }

    /**
     * Return true if the action list contains specific action
     *
     * @param action Action
     * @return Return true if the action list contains specific action
     */
    public boolean is(Action action) {
        return getAction(action.getClass()) != null;
    }

    /**
     * Clone Actions filtering.
     *
     * @param filter Filter
     * @return Clone of filtered Actions
     */
    public Actions clone(Predicate<? super Action> filter) {
        Actions actions = new Actions();
        actions.actions.addAll(this.actions.stream().filter(filter).collect(Collectors.toList()));

        return actions;
    }

    /**
     * Get all Actions from other Action list
     *
     * @param other Other Action list
     * @return This
     */
    public Actions allFrom(Actions other) {

        this.actions.addAll(other.actions);

        return this;
    }

    /**
     * Wrap Option system to new Action system
     */
    private static final class OptionAction implements Action {

        private final OptionProcessor processor;

        private OptionAction(OptionProcessor processor) {
            this.processor = processor;
        }

        @Override
        public ProcessingState stateAction(IToken<?> token, ParseSection section, List<IToken<?>> tokenList, int index) {
            StructureOptions options = processor.optionsOf(token, section);

            Objects.requireNonNull(options, "Null options for token: '" + token + "'. Processor: '" + processor + "'");

            if (options.is(DefaultOptions.Common.IGNORE))
                return ProcessingState.CONTINUE;

            if (options.is(DefaultOptions.Common.STACK)) {
                if (!section.hasCurrent())
                    section.enter(token);
                else
                    section.link(token);
                section.exit();
            }

            if (options.is(DefaultOptions.Standard.AUTO_ASSIGN)) {

                if (!section.hasCurrent())
                    section.enter(token);
                else
                    section.link(token);
            }

            if (options.is(DefaultOptions.Common.HOST)) {
                section.enter(token);
            }


            if (options.is(DefaultOptions.Common.EXIT/* OR CommonOptions.SEPARATOR*/))
                section.exit();
            return null;
        }
    }
}
