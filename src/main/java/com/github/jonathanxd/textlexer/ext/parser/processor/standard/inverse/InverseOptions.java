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
package com.github.jonathanxd.textlexer.ext.parser.processor.standard.inverse;

import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.inverse.ElementOption;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.inverse.HardHeadOption;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.inverse.HeadOption;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.inverse.InnerOption;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.inverse.StackOption;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.common.ExitOption;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.common.IgnoreOption;
import com.github.jonathanxd.textlexer.ext.parser.structure.Option;

/**
 * Created by jonathan on 17/02/16.
 */
public class InverseOptions {
    public static final Option<?> HARD_HEAD = new HardHeadOption();
    public static final Option<?> HEAD = new HeadOption();
    public static final Option<?> ELEMENT = new ElementOption();

    /**
     * @see com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.common.CommonOptions#IGNORE
     */
    @Deprecated
    public static final Option<?> IGNORE = new IgnoreOption();

    /**
     * @see com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.common.CommonOptions#EXIT
     */
    @Deprecated
    public static final Option<?> EXIT = new ExitOption();

    public static final Option<?> STACK = new StackOption();
    // INNER
    public static final Option<?> INNER = new InnerOption();

    public boolean is(Option<?> staticOption, Option<?> option) {

        return staticOption.getClass().isAssignableFrom(option.getClass());

    }
}
