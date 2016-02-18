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

import com.github.jonathanxd.textlexer.ext.parser.structure.options.ElementOption;
import com.github.jonathanxd.textlexer.ext.parser.structure.options.ExitOption;
import com.github.jonathanxd.textlexer.ext.parser.structure.options.HardHeadOption;
import com.github.jonathanxd.textlexer.ext.parser.structure.options.HeadOption;
import com.github.jonathanxd.textlexer.ext.parser.structure.options.IgnoreOption;
import com.github.jonathanxd.textlexer.ext.parser.structure.options.InnerOption;
import com.github.jonathanxd.textlexer.ext.parser.structure.options.StackOption;

/**
 * Created by jonathan on 17/02/16.
 */
public class Options {
    public static final Option<?> HARD_HEAD = new HardHeadOption();
    public static final Option<?> HEAD = new HeadOption();
    public static final Option<?> ELEMENT = new ElementOption();
    public static final Option<?> IGNORE = new IgnoreOption();
    public static final Option<?> EXIT = new ExitOption();

    public static final Option<?> STACK = new StackOption();
    // INNER
    public static final Option<?> INNER = new InnerOption();

    public boolean is(Option<?> staticOption, Option<?> option) {

        return staticOption.getClass().isAssignableFrom(option.getClass());

    }
}
