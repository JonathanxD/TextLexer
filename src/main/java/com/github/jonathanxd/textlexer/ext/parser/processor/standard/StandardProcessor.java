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

import com.github.jonathanxd.textlexer.ext.parser.processor.ActionProcessor;
import com.github.jonathanxd.textlexer.ext.parser.processor.OptionProcessor;
import com.github.jonathanxd.textlexer.ext.parser.processor.StructureProcessor;
import com.github.jonathanxd.textlexer.ext.parser.processor.action.Actions;
import com.github.jonathanxd.textlexer.ext.parser.processor.action.ProcessingState;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.DefaultOptions;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.OptionSupport;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.StructureOptions;
import com.github.jonathanxd.textlexer.ext.parser.structure.ParseSection;
import com.github.jonathanxd.textlexer.ext.parser.structure.StructuredTokens;
import com.github.jonathanxd.textlexer.lexer.token.IToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by jonathan on 19/02/16.
 */

/**
 * Common way to parse tokens
 */
@OptionSupport(value = {DefaultOptions.Common.class, DefaultOptions.Standard.class}, description = "Uses all Options of DefaultOptions.Common and DefaultOptions.Standard")
public abstract class StandardProcessor implements StructureProcessor, OptionProcessor, ActionProcessor {

    @Override
    public void process(List<IToken<?>> tokenList, StructuredTokens structure, ParseSection section) {
        ListIterator<IToken<?>> tokenIterator = tokenList.listIterator();


        while (tokenIterator.hasNext()) {
            int index = tokenIterator.nextIndex();
            IToken<?> token = tokenIterator.next();
            StructureOptions options = optionsOf(token, structure, section);
            Actions actions = actionsOf(token, section);
            try {
                if (options == null) {
                    ProcessingState state = actions.doAll(token, section, tokenList, index);

                    if (state == null || state == ProcessingState.DEFAULT) {
                        // DO NOTHING
                    } else if (state == ProcessingState.BREAK) {
                        break;
                    } else if (state == ProcessingState.CONTINUE) {
                        /**
                         * DEFAULT & null do same thing in this situation
                         */
                        continue;
                    }
                } else {
                    if (options.is(DefaultOptions.Common.IGNORE))
                        continue;

                    if (options.is(DefaultOptions.Common.STACK)) {
                        if (!section.hasCurrent())
                            section.enter(structure.addToken(token, null));
                        else
                            section.link(token);
                        section.exit();
                    }

                    if (options.is(DefaultOptions.Standard.AUTO_ASSIGN)) {

                        if (!section.hasCurrent())
                            section.enter(structure.addToken(token, null));
                        else
                            section.link(token);
                    }

                    if (options.is(DefaultOptions.Common.HOST)) {
                        section.enter(structure.addToken(token, null));
                    }


                    if (options.is(DefaultOptions.Common.EXIT/* OR CommonOptions.SEPARATOR*/))
                        section.exit();
                }
            } catch (Exception e) {
                List<StackTraceElement> elements = new ArrayList<>(Arrays.asList(e.getStackTrace()));
                elements.add(0, new StackTraceElement(this.getClass().getCanonicalName(), "optionsOf", this.getClass().getSimpleName() + ".java", 0));
                e.setStackTrace(elements.toArray(new StackTraceElement[elements.size()]));
                throw new RuntimeException("Invalid Option. IToken: " + token + ". Structure: " + structure, e);
            }

        }
    }

}
