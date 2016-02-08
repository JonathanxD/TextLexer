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
package io.github.jonathanxd.iutils.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import io.github.jonathanxd.iutils.iterator.Navigator;
import io.github.jonathanxd.iutils.iterator.SafeBackableIterator;

/**
 * Created by jonathan on 08/02/16.
 */
public class ListSafeBackableIterator<E> implements SafeBackableIterator<E> {

    private final List<E> list;
    private int index = -1;

    public ListSafeBackableIterator(List<E> list) {
        this.list = list;
    }

    @Override
    public boolean hasBack() {
        return index - 1 >= 0;
    }

    @Override
    public E back() {
        return list.get(--index);
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public Navigator<E> safeNavigate() {
        return new ListSafeNavigator<>(this.list);
    }

    @Override
    public boolean hasNext() {
        return index + 1 < list.size();
    }

    @Override
    public E next() {
        return list.get(++index);
    }

    private static class ListSafeNavigator<E> implements Navigator<E> {

        private final List<E> list;
        private int currentIndex;
        private E currentValue;

        private ListSafeNavigator(List<E> list) {
            this.list = list;
        }

        @Override
        public boolean has(int index) {
            return index >= 0 && index < list.size();
        }

        @Override
        public E navigateTo(int index) {
            if (index < 0) {
                currentIndex = 0;
            } else {
                currentIndex = index;
            }
            return currentValue = list.get(currentIndex);
        }

        @Override
        public E currentValue() {
            return currentValue;
        }

        @Override
        public void goNextWhen(Predicate<E> predicate) {
            do {
                ++currentIndex;
            }
            while (has(currentIndex) && predicate.test(navigateTo(currentIndex)));
        }

        @Override
        public int currentIndex() {
            return currentIndex;
        }

        @Override
        public List<E> collect(int to) {
            List<E> list = new ArrayList<>();
            to = to + currentIndex;
            do {
                list.add(navigateTo(currentIndex));
                ++currentIndex;
            }
            while (has(currentIndex) && currentIndex < to);

            return list;
        }
    }

}
