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
package com.github.jonathanxd.textlexer.ext.parser.processor.standard.options;

import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.common.ExitOption;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.common.HostOption;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.common.IgnoreOption;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.common.StackOption;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.inverse.ElementOption;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.inverse.HardHeadOption;
import com.github.jonathanxd.textlexer.ext.parser.structure.Option;

/**
 * Created by jonathan on 19/02/16.
 */
public final class DefaultOptions {

    public static final class Standard {
        public static final Option<?> AUTO_ASSIGN = new AutoAssignOption();
    }

    public static final class InverseProc {
        public static final Option<?> HARD_HEAD = new HardHeadOption();
        public static final Option<?> ELEMENT = new ElementOption();
    }

    public static final class Common {
        public static final Option<?> IGNORE = new IgnoreOption();
        public static final Option<?> EXIT = new ExitOption();
        public static final Option<?> HOST = new HostOption();
        public static final Option<?> STACK = new StackOption();

        /**
         * @see #EXIT
         */
        public static final Option<?> SEPARATOR = EXIT;
    }

}
