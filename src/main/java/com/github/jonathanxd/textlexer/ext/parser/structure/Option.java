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
package com.github.jonathanxd.textlexer.ext.parser.structure;

/**
 * Created by jonathan on 17/02/16.
 */

/**
 * Option.
 *
 * Highly recommended usage of Actions System
 *
 * @param <T> Type of value
 * @see com.github.jonathanxd.textlexer.ext.parser.processor.action.Actions
 */
public abstract class Option<T> {

    /**
     * Name of Option
     */
    private final String name;

    /**
     * Value of Option
     */
    private final T value;

    /**
     * Create a named option
     *
     * @param name Name
     */
    public Option(String name) {
        this(name, null);
    }

    /**
     * Create a named and valued option
     *
     * @param name  Name
     * @param value Value
     */
    public Option(String name, T value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Get the value
     *
     * @return the value
     */
    public T getValue() {
        return value;
    }

    /**
     * Get the name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Option[" + name + " = " + value + "]";
    }

    /**
     * Option Type
     */
    public static class Type {
        /**
         * Name
         */
        private final String name;
        /**
         * Option class
         */
        private final Class<?> type;

        /**
         * Create option type
         *
         * @param name name
         * @param type Option Class
         */
        public Type(String name, Class<?> type) {
            this.name = name;
            this.type = type;
        }

        /**
         * Get type
         *
         * @return Type
         */
        public Class<?> getType() {
            return type;
        }

        @Override
        public String toString() {
            return "Option[name=" + name + ", type=" + type + "]";
        }
    }
}