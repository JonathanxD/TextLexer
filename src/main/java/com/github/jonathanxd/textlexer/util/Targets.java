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
package com.github.jonathanxd.textlexer.util;

import com.github.jonathanxd.iutils.object.Node;

/**
 * Created by jonathan on 09/03/16.
 */
public class Targets<T> {
    private final Node<T, Type> element1;
    private final Node<T, Type> element2;

    public Targets(Node<T, Type> element1, Node<T, Type> element2) {
        this.element1 = element1;
        this.element2 = element2;
    }

    public static <T> Targets<T> fromA(T obj) {
        return new Targets<>(new Node<>(obj, Type.FROM), null);
    }

    public static <T> Targets<T> toA(T obj) {
        return new Targets<>(new Node<>(obj, Type.TO), null);
    }

    private static Type inverse(Node<Object, Type> node) {
        if (node.getValue() == Type.FROM)
            return Type.TO;
        else
            return Type.FROM;
    }

    public Targets<T> to(T obj) {
        return up(obj, Type.TO);
    }

    public Targets<T> from(T obj) {
        return up(obj, Type.FROM);
    }

    private Targets<T> up(T obj, Type type) {
        Node<T, Type> element1 = this.element1;
        Node<T, Type> element2 = this.element2;

        if (element1 == null)
            element1 = new Node<>(obj, type);
        else
            element2 = new Node<>(obj, type);

        return new Targets<>(element1, element2);
    }

    public T getFrom() {
        return get(Type.FROM);
    }

    public T getTo() {
        return get(Type.TO);
    }

    public T get(Type type) {
        if(this.element1 != null && this.element1.getValue() == type) {
            return this.element1.getKey();
        }
        if(this.element2 != null && this.element2.getValue() == type) {
            return this.element2.getKey();
        }
        throw new IllegalStateException("Cannot find '"+type+"'!");
    }

    enum Type {
        FROM, TO
    }
}
