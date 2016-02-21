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
package com.github.jonathanxd.textlexer.lexer.token.history.list;

import com.github.jonathanxd.textlexer.lexer.token.IToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Created by jonathan on 20/02/16.
 */
final class ImmutableCommonTokenList implements CommonTokenList {

    final List<? extends IToken<?>> c;

    ImmutableCommonTokenList(List<? extends IToken<?>> c) {
        this.c = c;
    }

    ImmutableCommonTokenList(Collection<? extends IToken<?>> c) {
        this(new ArrayList<>(c));
    }

    @Override
    public boolean equals(Object o) {
        return o == this || c.equals(o);
    }

    @Override
    public int hashCode() {
        return c.hashCode();
    }

    @Override
    public IToken<?> get(int index) {
        return c.get(index);
    }

    @Override
    public IToken<?> set(int index, IToken<?> element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, IToken<?> element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IToken<?> remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        return c.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return c.lastIndexOf(o);
    }

    @Override
    public boolean addAll(int index, Collection<? extends IToken<?>> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void replaceAll(UnaryOperator<IToken<?>> operator) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sort(Comparator<? super IToken<?>> c) {
        throw new UnsupportedOperationException();
    }

    public ListIterator<IToken<?>> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<IToken<?>> listIterator(final int index) {
        return new ListIterator<IToken<?>>() {
            private final ListIterator<? extends IToken<?>> i
                    = c.listIterator(index);

            @Override
            public boolean hasNext() {
                return i.hasNext();
            }

            @Override
            public IToken<?> next() {
                return i.next();
            }

            @Override
            public boolean hasPrevious() {
                return i.hasPrevious();
            }

            @Override
            public IToken<?> previous() {
                return i.previous();
            }

            @Override
            public int nextIndex() {
                return i.nextIndex();
            }

            @Override
            public int previousIndex() {
                return i.previousIndex();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void set(IToken<?> e) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void add(IToken<?> e) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void forEachRemaining(Consumer<? super IToken<?>> action) {
                i.forEachRemaining(action);
            }
        };
    }

    @Override
    public int size() {
        return c.size();
    }

    @Override
    public boolean isEmpty() {
        return c.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return c.contains(o);
    }

    @Override
    public Object[] toArray() {
        return c.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return c.toArray(a);
    }

    @Override
    public String toString() {
        return c.toString();
    }

    @Override
    public Iterator<IToken<?>> iterator() {
        return new Iterator<IToken<?>>() {
            private final Iterator<? extends IToken<?>> i = c.iterator();

            @Override
            public boolean hasNext() {
                return i.hasNext();
            }

            @Override
            public IToken<?> next() {
                return i.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void forEachRemaining(Consumer<? super IToken<?>> action) {
                // Use backing collection version
                i.forEachRemaining(action);
            }
        };
    }

    @Override
    public boolean add(IToken<?> e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> coll) {
        return c.containsAll(coll);
    }

    @Override
    public boolean addAll(Collection<? extends IToken<?>> coll) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> coll) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> coll) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    // Override default methods in Collection
    @Override
    public void forEach(Consumer<? super IToken<?>> action) {
        c.forEach(action);
    }

    @Override
    public boolean removeIf(Predicate<? super IToken<?>> filter) {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Spliterator<IToken<?>> spliterator() {
        return (Spliterator<IToken<?>>) c.spliterator();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Stream<IToken<?>> stream() {
        return (Stream<IToken<?>>) c.stream();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Stream<IToken<?>> parallelStream() {
        return (Stream<IToken<?>>) c.parallelStream();
    }

    @Override
    public List<IToken<?>> subList(int fromIndex, int toIndex) {
        return new ImmutableCommonTokenList(c.subList(fromIndex, toIndex));
    }

}
