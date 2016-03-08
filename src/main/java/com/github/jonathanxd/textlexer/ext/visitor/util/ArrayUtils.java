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
package com.github.jonathanxd.textlexer.ext.visitor.util;

/**
 * Created by jonathan on 04/03/16.
 */
public class ArrayUtils {

    public static <D>boolean contains(D[] array, D value) {
        return Any.ARRAY(array).is(value);
    }

    public static abstract class Any<T extends Any, D> {

        private final T any;

        @SuppressWarnings("unchecked")
        protected Any() {
            this.any = (T) this;
        }

        public static <D> Any<AnyArray, D> ARRAY(D[] array) {
            return new AnyArray<>(array);
        }

        public T get() {
            return any;
        }

        public abstract boolean is(D value);

        public static class AnyArray<D> extends Any<AnyArray, D> {
            private final D[] array;

            public AnyArray(D[] array) {
                this.array = array.clone();
            }

            public D[] getArray() {
                return array.clone();
            }

            @Override
            public boolean is(D value) {
                for(D valueInArray : array) {
                    if(valueInArray.equals(value))
                        return true;
                }

                return false;
            }
        }
    }

}
