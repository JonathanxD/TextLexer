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
public class Options {
    public static final Option HARD_HEAD = new Option("HARD_HEAD");
    public static final Option HEAD = new Option("HEAD");
    public static final Option STACK = new Option("STACK");
    public static final Option ELEMENT = new Option("ELEMENT");
    public static final Option IGNORE = new Option("IGNORE");
    public static final Option EXIT = new Option("EXIT NODE");
    // INNER
    public static final Option INNER = new Option("INNER");
}
