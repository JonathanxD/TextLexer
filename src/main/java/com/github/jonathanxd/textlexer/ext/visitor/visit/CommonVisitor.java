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

import com.github.jonathanxd.iutils.data.DataProvider;
import com.github.jonathanxd.iutils.data.ExtraData;
import com.github.jonathanxd.textlexer.ext.parser.holder.TokenHolder;
import com.github.jonathanxd.textlexer.ext.visitor.VisitException;
import com.github.jonathanxd.textlexer.ext.visitor.listener.ListenerFor;
import com.github.jonathanxd.textlexer.ext.visitor.listener.VisitPhase;
import com.github.jonathanxd.textlexer.ext.visitor.util.ArrayUtils;
import com.github.jonathanxd.textlexer.ext.visitor.util.Reflection;
import com.github.jonathanxd.textlexer.lexer.token.IToken;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * Created by jonathan on 20/02/16.
 */
@DataProvider({Visitor.class, TokenHolder.class})
public abstract class CommonVisitor<T> implements Visitor<T> {

    /**
     * Data to send to methods
     */
    protected final ExtraData defaults = new ExtraData();

    /**
     * Listeners and Annotated methods
     */
    private final Map<Object, Collection<Method>> listeners = new HashMap<>();

    /**
     * Create a CommonVisitor with default registers
     */
    public CommonVisitor() {
        defaults.registerData(this);
    }

    /**
     * Get Listener fro Method
     *
     * @param method Method
     * @return Listener
     */
    private static ListenerFor get(Method method) {
        return method.getDeclaredAnnotation(ListenerFor.class);
    }

    /**
     * Get default ExtraData
     *
     * @return Default Extra data
     */
    protected ExtraData getDefaultData() {
        return defaults;
    }

    @Override
    public void addListener(Object listener) {
        register(listener);
    }

    /**
     * Register a Listener
     *
     * @param listener Listener to register
     */
    public void register(Object listener) {
        Collection<Method> annotatedMethods = Reflection.annotatedMethods(listener, ListenerFor.class);
        check(listener, annotatedMethods);
        listeners.put(listener, annotatedMethods);
    }

    /**
     * Unregister a listener
     *
     * @param listener Listener
     */
    public void unregister(Object listener) {
        listeners.remove(listener);
    }

    @Override
    public void removeListener(Object listener) {
        unregister(listener);
    }

    @Override
    public void visit(TokenHolder tokenHolder) {
        processVisit(tokenHolder, VisitPhase.VISIT);
    }

    @Override
    public void exit(TokenHolder tokenHolder) {
        processVisit(tokenHolder, VisitPhase.EXIT);
    }

    @Override
    public void endVisit(List<TokenHolder> tokenHolders) {
        processVisit(tokenHolders, VisitPhase.VISIT_END);
    }

    /**
     * Check methods
     *
     * @param methods Methods
     * @return true if method Collection is not empty
     */
    private boolean checkObject(Collection<Method> methods) {
        return methods.size() > 0;
    }

    /**
     * Check object Listener and detect if is a valid listener
     *
     * @param listener Listener
     * @param methods  Methods
     * @see ListenerFor
     */
    private void check(Object listener, Collection<Method> methods) {
        if (!checkObject(methods))
            throw new IllegalStateException("Invalid listener! Object: " + listener);
    }

    /**
     * Clone Default Extra data to a new ExtraData
     *
     * @param tokenHolder TokenHolder
     * @return new ExtraData
     */
    @Deprecated
    private ExtraData clone(TokenHolder tokenHolder) {
        ExtraData data;
        try {
            data = defaults.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cannot clone data", e);
        }

        Objects.requireNonNull(data, "Null data!");
        if (tokenHolder != null)
            data.registerData(tokenHolder);

        return data;
    }

    /**
     * Clone Default Extra data to a new ExtraData
     *
     * @param include New data to Include
     * @return new ExtraData
     */
    private ExtraData cloneWith(Object include) {
        ExtraData data;
        try {
            data = defaults.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cannot clone data", e);
        }

        Objects.requireNonNull(data, "Null data!");
        if (include != null)
            data.registerData(include);

        return data;
    }

    /**
     * Process the visit of token
     *
     * @param with  Data to add to ExtraData to be passed to Method
     * @param phase VisitPhase
     */
    private void processVisit(Object with, VisitPhase phase) {
        final ExtraData finalData = cloneWith(with);

        listeners.entrySet().forEach(entry -> {
            try {
                Object listener = entry.getKey();

                ExtraData.match(finalData,
                        entry.getKey().getClass(),
                        entry.getKey().getClass()::getDeclaredMethods,
                        new MethodInvoke(listener),
                        new MethodPredicate(phase, entry.getValue(), with));
                //for(Collection<entry.getValue()
            } catch (Throwable t) {
                throw new VisitException("Object cause: " + entry.getKey(), t);
            }

        });
    }

    /**
     * Invoke method
     */
    private static final class MethodInvoke implements BiFunction<Method, Object[], Object> {

        /**
         * Listener with the method
         */
        private final Object listener;

        private MethodInvoke(Object listener) {
            this.listener = listener;
        }

        @Override
        public Object apply(Method method, Object[] args) {
            ListenerFor listenerFor = get(method);

            try {
                method.setAccessible(true);

                method.invoke(listener, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Determine the Methods for specification
     */
    private final class MethodPredicate implements Predicate<Method> {

        /**
         * VisitPhase
         */
        private final VisitPhase phase;
        /**
         * Methods
         */
        private final Collection<Method> methodCollection;
        /**
         * TokenHolder
         */
        private final Optional<TokenHolder> tokenHolder;

        /*
         * Object to send to method
         */
        private final Object handle;

        private MethodPredicate(VisitPhase phase, Collection<Method> methodCollection, Object handle) {
            this.phase = phase;
            this.methodCollection = methodCollection;
            this.handle = handle;
            if (handle instanceof TokenHolder) {
                this.tokenHolder = Optional.of((TokenHolder) handle);
            } else {
                this.tokenHolder = Optional.empty();
            }
        }

        @Override
        public boolean test(Method method) {

            if (methodCollection.contains(method)) {
                ListenerFor listenerFor = get(method);

                if (listenerFor.phase() != phase)
                    return false;


                if (listenerFor.required() == null || listenerFor.required().isAssignableFrom(CommonVisitor.this.getClass())) {
                    if (listenerFor.value().length == 0)
                        return true;

                    boolean allPassed = true;

                    if (ArrayUtils.contains(listenerFor.value(), ListenerFor.ALL.class)) {
                        allPassed = true;
                    } else {
                        if (tokenHolder.isPresent()) {
                            for (IToken<?> token : tokenHolder.get().getTokens()) {

                                for (Class<? extends IToken<?>> tokenClass : listenerFor.value()) {
                                    if (!tokenClass.isAssignableFrom(token.getClass())) {
                                        allPassed = false;
                                    }
                                }
                            }
                        }

                    }
                    if (allPassed)
                        return true;

                }
            }
            return false;
        }
    }
}
