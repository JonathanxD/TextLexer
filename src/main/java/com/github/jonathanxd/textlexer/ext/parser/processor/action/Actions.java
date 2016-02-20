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

import com.github.jonathanxd.textlexer.ext.parser.structure.ParseStructure;
import com.github.jonathanxd.textlexer.lexer.token.IToken;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
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

    private final Queue<Action> actions = new ArrayDeque<>();

    public Actions offer(Action action) {
        actions.offer(action);
        return this;
    }

    public Actions offer(Supplier<Action> action) {
        offer(action.get());
        return this;
    }

    public Actions offer(Function<Collection<Action>, Action> action) {
        offer(action.apply(actions));
        return this;
    }

    public Actions offer(Action action, Predicate<Collection<Action>> offerIf) {
        if (offerIf.test(Collections.unmodifiableCollection(actions))) {
            offer(action);
        }
        return this;
    }

    public ProcessingState doAll(IToken<?> token, ParseStructure.ParseSection section) {
        AtomicReference<ProcessingState> state = new AtomicReference<>();

        actions.forEach(action -> {
            ProcessingState otherState = action.stateAction(token, section);
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

    @SuppressWarnings("unchecked")
    public <T> Action getAction(Class<?> typeClass) {

        for (Action action : actions) {

            if (typeClass.isAssignableFrom(action.getClass())) {
                return action;
            }
        }

        return null;
    }

    public boolean is(Action action) {

        return getAction(action.getClass()) != null;
    }

    public Actions clone(Predicate<? super Action> filter) {
        Actions actions = new Actions();
        actions.actions.addAll(this.actions.stream().filter(filter).collect(Collectors.toList()));

        return actions;
    }

    public Actions allFrom(Actions other) {

        this.actions.addAll(other.actions);

        return this;
    }


}
