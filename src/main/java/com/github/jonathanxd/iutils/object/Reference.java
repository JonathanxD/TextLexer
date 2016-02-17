/*
 * 	JwIUtils - Utility Library for Java
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

import com.github.jonathanxd.iutils.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Created by jonathan on 13/02/16.
 */
public class Reference<T> implements Comparable<Reference> {

    private final Class<? extends T> aClass;
    private final Reference[] related;
    private final Object hold;

    Reference(Class<? extends T> aClass, Reference[] related, Object hold) {
        this.hold = hold;
        this.aClass = Objects.requireNonNull(aClass);
        this.related = related != null ? related : new Reference[0];
    }

    @NotNull
    public static String toString(Reference reference) {

        StringBuilder sb = new StringBuilder();
        String shortName = reference.getAClass().getSimpleName();
        sb.append(shortName);

        if (reference.getRelated().length != 0) {
            sb.append("<");
            StringJoiner sj = new StringJoiner(", ");

            for (Reference loopRef : reference.getRelated()) {
                sj.add(toString(loopRef));
            }

            String processResult = sj.toString();
            sb.append(processResult);
            sb.append(">");
        }

        return sb.toString();
    }

    public static <T> ReferenceBuilder<T> to() {
        return referenceTo();
    }

    public static <T> ReferenceBuilder<T> referenceTo() {
        return new ReferenceBuilder<>();
    }

    public static <T> ReferenceBuilder<T> a(Class<T> aClass) {
        return Reference.<T>referenceTo().a(aClass);
    }

    public static <T> Reference<T> aEnd(Class<T> aClass) {
        return Reference.<T>referenceTo().a(aClass).build();
    }

    @SuppressWarnings("unchecked")
    public static <T> ReferenceBuilder<T> but(Reference reference) {
        return Reference.<T>referenceTo().a(reference.getAClass()).of(reference.getRelated());
    }

    public ReferenceBuilder<? extends T> but() {
        return Reference.but(this);
    }

    public Class<? extends T> getAClass() {
        return aClass;
    }

    public Object get() {
        return hold;
    }

    public Reference[] getRelated() {
        return related;
    }

    @Override
    public String toString() {
        return toString(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aClass, Arrays.deepHashCode(related));
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Reference))
            return false;

        Reference other = (Reference) obj;

        return compareTo(other) == 0;
    }

    @Override
    public int compareTo(@NotNull Reference compareTo) {

        if (getAClass() == compareTo.getAClass()) {

            if (Arrays.deepEquals(getRelated(), compareTo.getRelated())) {
                return 0;
            }

            return 1;
        }

        return -1;
    }

}
