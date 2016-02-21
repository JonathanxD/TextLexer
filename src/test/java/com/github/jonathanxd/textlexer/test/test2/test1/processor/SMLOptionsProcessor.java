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
package com.github.jonathanxd.textlexer.test.test2.test1.processor;

import com.github.jonathanxd.textlexer.ext.parser.processor.OptionProcessor;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.DefaultOptions;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.StructureOptions;
import com.github.jonathanxd.textlexer.ext.parser.structure.ParseSection;
import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.Comma;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.Garbage;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.KeyToken;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.KeyValueDivider;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.ValueToken;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.host.MapClose;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.host.MapOpen;

/**
 * Created by jonathan on 20/02/16.
 */
public class SMLOptionsProcessor implements OptionProcessor {

    @Override
    public StructureOptions optionsOf(IToken<?> token, ParseSection section) {
        if(token instanceof KeyToken || token instanceof MapOpen) {
            return new StructureOptions().set(DefaultOptions.Standard.AUTO_ASSIGN, true);
        }

        if(token instanceof MapClose) {
            return new StructureOptions().set(DefaultOptions.Common.STACK, true).and(DefaultOptions.Common.EXIT, true);
        }

        if(token instanceof ValueToken) {
            return new StructureOptions().set(DefaultOptions.Standard.AUTO_ASSIGN, true);
        }

        if(token instanceof Comma) {
            return new StructureOptions().set(DefaultOptions.Common.SEPARATOR, true);
        }

        if(token instanceof KeyValueDivider) {
            return new StructureOptions().set(DefaultOptions.Common.STACK, true)/*.and(DefaultOptions.Common.EXIT, true)*/;
        }

        if(token instanceof Garbage) {
            return new StructureOptions().set(DefaultOptions.Common.IGNORE, true);
        }

        return null;
    }
}
