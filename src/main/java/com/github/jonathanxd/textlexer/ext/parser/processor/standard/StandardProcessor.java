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
package com.github.jonathanxd.textlexer.ext.parser.processor.standard;

import com.github.jonathanxd.textlexer.ext.parser.holder.TokenHolder;
import com.github.jonathanxd.textlexer.ext.parser.processor.OptionProcessor;
import com.github.jonathanxd.textlexer.ext.parser.processor.ParserProcessor;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.common.CommonOptions;
import com.github.jonathanxd.textlexer.ext.parser.structure.ParseStructure;
import com.github.jonathanxd.textlexer.ext.parser.structure.StructureOptions;
import com.github.jonathanxd.textlexer.lexer.token.IToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by jonathan on 19/02/16.
 */
public abstract class StandardProcessor implements ParserProcessor, OptionProcessor {

    @Override
    public void process(List<IToken<?>> tokenList, ParseStructure structure, ParseStructure.ParseSection section) {
        ListIterator<IToken<?>> tokenIterator = tokenList.listIterator();


        while (tokenIterator.hasNext()) {

            IToken<?> token = tokenIterator.next();
            StructureOptions options = optionsOf(token, structure, section);
            try{
                if (options.is(CommonOptions.IGNORE))
                    continue;

                if (options.is(CommonOptions.STACK)) {
                    if (!section.hasCurrent())
                        section.enter(structure.addToken(token));
                    else
                        section.link(token);
                    section.exit();
                }

                if (options.is(StandardOptions.AUTO_ASSIGN)) {

                    if (!section.hasCurrent())
                        section.enter(structure.addToken(token));
                    else
                        section.link(token);
                }

                if (options.is(StandardOptions.HOST)) {
                    section.enter(structure.addToken(token));
                }


                if (options.is(CommonOptions.EXIT/* OR CommonOptions.SEPARATOR*/))
                    section.exit();
            }
            catch (Exception e) {
                List<StackTraceElement> elements = new ArrayList<>(Arrays.asList(e.getStackTrace()));
                elements.add(0, new StackTraceElement(this.getClass().getCanonicalName(), "optionsOf", this.getClass().getSimpleName()+".java", 0));
                e.setStackTrace(elements.toArray(new StackTraceElement[elements.size()]));
                throw new RuntimeException("Invalid Option. IToken: "+token+". Structure: "+structure, e);
            }

        }
    }

}
