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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by jonathan on 06/03/16.
 *
 * // Array[5]    {?, ?, ?, ?, ?} // // add("A") // // Array[5]    {"A", ?, ?, ?, ?} // // add("B")
 * // // Array[5]    {"B", "A", ?, ?, ?} // // remove("A") // // Array[5]    {"B", ?, ?, ?, ?} // //
 * or // // Array[5]    {"B", "A", ?, ?, ?} // // remove("B") // // Array[5]    {"A", ?, ?, ?, ?}
 *
 * // Array[5]    {?, ?, ?, ?, ?} // // add("A") // // Array[5]    {"A", ?, ?, ?, ?} // // add("B")
 * // // Array[5]    {"B", "A", ?, ?, ?} // // remove("A") // // Array[5]    {"B", ?, ?, ?, ?} // //
 * or // // Array[5]    {"B", "A", ?, ?, ?} // // remove("B") // // Array[5]    {"A", ?, ?, ?, ?}
 *
 * // Array[5]    {?, ?, ?, ?, ?} // // add("A") // // Array[5]    {"A", ?, ?, ?, ?} // // add("B")
 * // // Array[5]    {"B", "A", ?, ?, ?} // // remove("A") // // Array[5]    {"B", ?, ?, ?, ?} // //
 * or // // Array[5]    {"B", "A", ?, ?, ?} // // remove("B") // // Array[5]    {"A", ?, ?, ?, ?}
 *
 * // Array[5]    {?, ?, ?, ?, ?} // // add("A") // // Array[5]    {"A", ?, ?, ?, ?} // // add("B")
 * // // Array[5]    {"B", "A", ?, ?, ?} // // remove("A") // // Array[5]    {"B", ?, ?, ?, ?} // //
 * or // // Array[5]    {"B", "A", ?, ?, ?} // // remove("B") // // Array[5]    {"A", ?, ?, ?, ?}
 *
 * // Array[5]    {?, ?, ?, ?, ?} // // add("A") // // Array[5]    {"A", ?, ?, ?, ?} // // add("B")
 * // // Array[5]    {"B", "A", ?, ?, ?} // // remove("A") // // Array[5]    {"B", ?, ?, ?, ?} // //
 * or // // Array[5]    {"B", "A", ?, ?, ?} // // remove("B") // // Array[5]    {"A", ?, ?, ?, ?}
 *
 * // Array[5]    {?, ?, ?, ?, ?} // // add("A") // // Array[5]    {"A", ?, ?, ?, ?} // // add("B")
 * // // Array[5]    {"B", "A", ?, ?, ?} // // remove("A") // // Array[5]    {"B", ?, ?, ?, ?} // //
 * or // // Array[5]    {"B", "A", ?, ?, ?} // // remove("B") // // Array[5]    {"A", ?, ?, ?, ?}
 *
 * // Array[5]    {?, ?, ?, ?, ?} // // add("A") // // Array[5]    {"A", ?, ?, ?, ?} // // add("B")
 * // // Array[5]    {"B", "A", ?, ?, ?} // // remove("A") // // Array[5]    {"B", ?, ?, ?, ?} // //
 * or // // Array[5]    {"B", "A", ?, ?, ?} // // remove("B") // // Array[5]    {"A", ?, ?, ?, ?}
 *
 * // Array[5]    {?, ?, ?, ?, ?} // // add("A") // // Array[5]    {"A", ?, ?, ?, ?} // // add("B")
 * // // Array[5]    {"B", "A", ?, ?, ?} // // remove("A") // // Array[5]    {"B", ?, ?, ?, ?} // //
 * or // // Array[5]    {"B", "A", ?, ?, ?} // // remove("B") // // Array[5]    {"A", ?, ?, ?, ?}
 */
/**
 // Array[5]    {?, ?, ?, ?, ?}
 //
 // add("A")
 //
 // Array[5]    {"A", ?, ?, ?, ?}
 //
 // add("B")
 //
 // Array[5]    {"B", "A", ?, ?, ?}
 //
 // remove("A")
 //
 // Array[5]    {"B", ?, ?, ?, ?}
 //
 // or
 //
 // Array[5]    {"B", "A", ?, ?, ?}
 //
 // remove("B")
 //
 // Array[5]    {"A", ?, ?, ?, ?}
 */

/**
 This list works like Stack, when you add a element, it will be added in the left-side of list
 If you remove a element, all elements will be moved to left-side
 **/
public class StackArrayList<T> implements List<T> {

    private final int max;
    private final T[] array;
    private final Class<?> component;

    /**
     * Construct a new StackArrayList
     * @param max Max elements
     * @param component Component Type (List type, required to create a "Type-Safe" array)
     */
    @SuppressWarnings("unchecked")
    public StackArrayList(int max, @Nonnull Class<?> component) {
        this.max = max;
        this.component = component;
        array = (T[]) Array.newInstance(component, this.max);
        for (int x = 0; x < max; ++x) {
            array[x] = null;
        }
    }

    @Override
    public int size() {
        return max;
    }

    @Override
    public boolean isEmpty() {

        for (T e : array) {
            if (e != null)
                return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean contains(@Nonnull Object o) {
        return containsSafe((T) o);
    }

    public boolean containsSafe(@Nonnull T o) {
        for (T e : array)
            if (e.equals(o))
                return true;

        return false;
    }

    @Nonnull
    @Override
    public Iterator<T> iterator() {
        return new Iter();
    }

    @Nonnull
    @Override
    public Object[] toArray() {
        return array.clone();
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    @Override
    public <T1> T1[] toArray(@Nonnull T1[] a) {
        for (int x = 0; x < a.length; ++x) {
            a[x] = (T1) array[x];
        }
        return a;
    }

    @Override
    public boolean add(@Nonnull T element) {

        if (array[max - 1] != null) {
            return false;
        } else {
            moveRight();
            array[0] = element;
            return true;
        }
    }

    /**
     * Push element to Stack
     * @param element Element
     * @return Element in stack or {@link Optional#empty()} if list is full
     */
    @Nonnull
    public Optional<T> push(@Nonnull T element) {
        if(add(element)) {
            return Optional.of(element);
        }else{
            return Optional.empty();
        }
    }

    /**
     * Pop element in stack
     * @return Element in stack or {@link Optional#empty()} if list is empty
     */
    @Nonnull
    public Optional<T> pop() {
        if(isEmpty())
            return Optional.empty();

        T valueInArray = array[0];

        return Optional.ofNullable(valueInArray);
    }

    /**
     * Move the list to right-side and return lost element
     * @return Last element (lost element)
     */
    @Nonnull
    private T moveRight() {
        T last = array[max - 1];
        System.arraycopy(array, 0, array, 1, max - 1);
        return last;
    }

    /**
     * Move the list to right-side from a index and return lost element
     * @param from Index to move to right.
     * @return Last element (lost element)
     */
    @Nonnull
    private T moveRight(int from) {
        T last = array[max - 1];
        System.arraycopy(array, from, array, 1, max - 1);
        return last;
    }

    /**
     * Fix the Array elements
     * Example, if the array is:
     * {"A", "B", null, "C", "D", "E"}
     * all elements after 'null' will be shifted to left side
     */
    private void fix() {

        if (isEmpty())
            return;

        boolean move = false;
        for (int x = 0; x < max; ++x) {
            if (array[x] == null) {
                move = true;
            }

            if (move) {
                if (x + 1 < max) {
                    array[x] = array[x + 1];
                } else {
                    array[x] = null;
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(@Nonnull Object o) {
        return removeSafe((T) o);
    }

    public boolean removeSafe(@Nonnull T o) {
        if (!containsSafe(o))
            return false;

        boolean found = false;

        for (int x = 0; x < max; ++x) {
            T elem = array[x];
            if (elem.equals(o)) {
                array[x] = null;
                found = true;
            }
        }

        fix();

        return found;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean containsAll(@Nonnull Collection<?> c) {

        return containsAllSafe(c.stream().map(o -> (T) o).collect(Collectors.toList()));
    }

    public boolean containsAllSafe(@Nonnull Collection<? extends T> c) {

        boolean contains = true;

        for (int x = 0; x < max; ++x) {
            T val = array[x];

            if (!c.contains(val)) {
                contains = false;
            }
        }

        if (c.isEmpty() && this.isEmpty())
            return true;

        if (!c.isEmpty() && this.isEmpty())
            return false;

        return contains;
    }

    /**
     * Foreach Stack elements in Add Order (last-to-first-loop).
     * @param consumer Element Consumer
     */
    public void foreachAddOrder(@Nonnull Consumer<T> consumer) {
        foreachToReturn((t) -> {
            consumer.accept(t);
            return null;
        });
    }

    /**
     * Foreach Stack elements in Add Order (last-to-first-loop) and return a value.
     * @param function Function to Apply, if Function returns null loop will continue, otherwise will return value.
     * @param <R> Type of return value.
     * @return Value or Null.
     */
    @Nullable
    public <R> R foreachToReturn(@Nonnull Function<T, R> function) {
        for (int x = max - 1; x > -1/*x != 0*/; --x) {

            if (array[x] == null)
                continue;

            R apply = function.apply(array[x]);
            if (apply != null)
                return apply;
        }
        return null;
    }

    @Override
    public boolean addAll(@Nonnull Collection<? extends T> c) {
        boolean result = false;

        for (T elem : c) {
            if (this.add(elem)) {
                result = true;
            }
        }

        return result;
    }

    @Override
    public boolean addAll(int index, @Nonnull Collection<? extends T> c) {

        if (index >= max)
            throw new IndexOutOfBoundsException("List Max size: " + max + ". Provided index: " + index);

        for (T elem : c) {
            this.add(index, elem);
            ++index;
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean removeAll(@Nonnull Collection<?> c) {
        return removeAllSafe(c.stream().map(o -> (T) o).collect(Collectors.toList()));
    }

    public boolean removeAllSafe(@Nonnull Collection<? extends T> c) {
        boolean success = false;

        for (Object elem : c) {
            if (remove(elem)) {
                success = true;
            }
        }

        return success;
    }

    /**
     * Get size of the size without null values.
     * @return Size without null values.
     */
    public int sizeWithoutNull() {
        int size = 0;
        for (int x = 0; x < max; ++x) {
            if (array[x] != null)
                ++size;
        }

        return size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean retainAll(@Nonnull Collection<?> c) {
        return retainAllSafe(c.stream().map(o -> (T) o).collect(Collectors.toList()));
    }

    public boolean retainAllSafe(@Nonnull Collection<? extends T> c) {

        boolean success = false;

        for (int x = 0; x < max; ++x) {
            T val = array[x];
            if (!c.contains(val)) {
                success = true;
                removeSafe(val);
            }
        }

        return success;
    }

    @Override
    public void clear() {
        for (int x = 0; x < max; ++x) {
            array[x] = null;
        }
    }

    @Override
    public T get(int index) {
        return array[index];
    }

    @Override
    public T set(int index, T element) {
        return array[index] = Objects.requireNonNull(element);
    }

    /**
     * Check if last element is null.
     * @return True if last element is null.
     */
    private boolean checkLast() {
        return array[max - 1] == null;
    }

    @Override
    public void add(int index, T element) {

        if (checkLast()) {
            moveRight(index);
        } else {
            throw new StackListFull(this.getClass().getSimpleName() + " is full! Elements: " + this.toString());
        }

    }

    @Override
    public T remove(int index) {
        T val = array[index];

        array[index] = null;

        fix();

        return val;
    }

    @SuppressWarnings("unchecked")
    @Override
    public int indexOf(Object o) {

        return indexOfSafe((T) o);
    }

    public int indexOfSafe(T o) {
        for (int x = 0; x < max; ++x) {
            T val = array[x];
            if (val.equals(o))
                return x;
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    @Override
    public int lastIndexOf(Object o) {
       return lastIndexOfSafe((T) o);
    }

    public int lastIndexOfSafe(T o) {
        for (int x = max - 1; x > -1; --x) {
            T val = array[x];
            if (val.equals(o))
                return x;
        }
        return -1;
    }

    @Nonnull
    @Override
    public ListIterator<T> listIterator() {
        return new Iter();
    }

    @Nonnull
    @Override
    public ListIterator<T> listIterator(int index) {
        Iter iter = new Iter();
        iter.index = index;
        return iter;
    }

    @Nonnull
    @Override
    public List<T> subList(int fromIndex, int toIndex) {

        int size = toIndex - fromIndex;

        List<T> ranged = new StackArrayList<>(size, this.component);

        for (int x = fromIndex; x < toIndex; ++x) {
            ranged.add(array[x]);
        }

        return ranged;
    }

    @Nonnull
    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for (T e : array)
            sj.add(String.valueOf(e));
        return sj.toString();
    }

    private static class StackListFull extends RuntimeException {
        public StackListFull() {
            super();
        }

        public StackListFull(String message) {
            super(message);
        }

        public StackListFull(String message, Throwable cause) {
            super(message, cause);
        }

        public StackListFull(Throwable cause) {
            super(cause);
        }

        protected StackListFull(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }

    private class Iter implements ListIterator<T> {

        int index = -1;

        @Override
        public boolean hasNext() {
            return index + 1 < array.length;
        }

        @Override
        public T next() {
            if(!hasNext())
                throw new NoSuchElementException("No more elements!");

            return array[++index];
        }

        @Override
        public boolean hasPrevious() {
            return index - 1 > -1;
        }

        @Override
        public T previous() {
            return array[--index];
        }

        @Override
        public int nextIndex() {
            return index + 1;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            array[index] = null;
        }

        @Override
        public void set(T t) {
            array[index] = t;
        }

        @Override
        public void add(T t) {
            StackArrayList.this.add(t);
        }
    }
}
