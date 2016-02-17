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
package com.github.jonathanxd.iutils.object;

import com.github.jonathanxd.iutils.optional.Optional;
import com.github.jonathanxd.iutils.optional.Required;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jonathan on 13/02/16.
 */
public final class ReferenceBuilder<T> {
    private Class<? extends T> aClass;
    private List<Reference> related = new ArrayList<>();
    private Object hold = null;

    ReferenceBuilder() {
    }

    @Required
    public ReferenceBuilder<T> a(Class<? extends T> aClass) {
        this.aClass = aClass;
        return this;
    }

    @Optional
    public ReferenceBuilder<T> hold(Object object) {
        this.hold = object;
        return this;
    }

    // Of
    @Optional
    public <E> ReferenceBuilder<T> of(List<Reference<E>> related) {
        this.related.addAll(related);
        return this;
    }

    @SafeVarargs
    @Optional
    public final <E> ReferenceBuilder<T> of(Reference<E>... related) {
        of(Arrays.asList(related));
        return this;
    }

    @Optional
    public ReferenceBuilder<T> of(ReferenceBuilder... builders) {

        for (ReferenceBuilder builder : builders) {
            of(builder.build());
        }
        return this;
    }

    @SafeVarargs
    @Optional
    public final <E> ReferenceBuilder<T> of(Class<? extends E>... classes) {

        List<Reference<E>> references = new ArrayList<>();

        for (Class<? extends E> classz : classes) {
            references.add(new ReferenceBuilder<E>().a(classz).build());
        }

        of(references);

        return this;
    }

    // AND OF
    @Optional
    public <E> ReferenceBuilder<T> and(List<Reference<E>> related) {
        andCheck();
        of(related);
        return this;
    }

    @SafeVarargs
    @Optional
    public final <E> ReferenceBuilder<T> and(Reference<E>... related) {
        andCheck();
        of(related);
        return this;
    }

    @Optional
    public ReferenceBuilder<T> and(ReferenceBuilder... builders) {
        andCheck();
        of(builders);
        return this;
    }

    @Optional
    public ReferenceBuilder<T> and(Class<?>... classes) {
        andCheck();
        of(classes);
        return this;
    }

    private void andCheck() {
        if (related.size() == 0)
            throw new IllegalStateException("'and' cannot be used here! Usage ex: referenceTo().a(Object.class).of(String.class).and(Class.class)");
    }

    public Reference<T> build() {
        return new Reference<>(aClass, related.toArray(new Reference[related.size()]), hold);
    }
}
