package com.github.jonathanxd.textlexer.ext.parser.processor.action;

import com.github.jonathanxd.textlexer.ext.parser.structure.Option;
import com.github.jonathanxd.textlexer.ext.parser.structure.ParseStructure;
import com.github.jonathanxd.textlexer.lexer.token.IToken;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Queue;
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

    public void doAll(IToken<?> token, ParseStructure.ParseSection section) {
        actions.forEach(action -> action.doAction(token, section));
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
