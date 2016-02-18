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
public abstract class Option<T> {

    private final String name;
    private final T value;

    public Option(String name) {
        this(name, null);
    }

    public Option(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Option[" + name + " = " + value + "]";
    }

    public static class Type {
        private final String name;
        private final Class<?> type;

        public Type(String name, Class<?> type) {
            this.name = name;
            this.type = type;
        }

        public Class<?> getType() {
            return type;
        }

        @Override
        public String toString() {
            return "Option[name=" + name + ", type=" + type + "]";
        }
    }
}