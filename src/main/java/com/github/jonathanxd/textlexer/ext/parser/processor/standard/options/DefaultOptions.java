package com.github.jonathanxd.textlexer.ext.parser.processor.standard.options;

import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.common.ExitOption;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.common.HostOption;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.common.IgnoreOption;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.common.StackOption;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.inverse.ElementOption;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.inverse.HardHeadOption;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.inverse.HeadOption;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.inverse.InnerOption;
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
