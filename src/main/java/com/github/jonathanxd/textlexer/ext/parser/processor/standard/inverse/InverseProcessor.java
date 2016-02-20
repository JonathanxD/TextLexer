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

import com.github.jonathanxd.textlexer.ext.parser.holder.TokenHolder;
import com.github.jonathanxd.textlexer.ext.parser.processor.OptionProcessor;
import com.github.jonathanxd.textlexer.ext.parser.processor.ParserProcessor;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.DefaultOptions;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.OptionSupport;
import com.github.jonathanxd.textlexer.ext.parser.structure.ParseStructure;
import com.github.jonathanxd.textlexer.ext.parser.structure.StructureOptions;
import com.github.jonathanxd.textlexer.lexer.token.IToken;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by jonathan on 19/02/16.
 */
@OptionSupport(value = {DefaultOptions.Common.class, DefaultOptions.InverseProc.class}, description = "Uses all options of DefaultOptions.Common and DefaultOptions.InverseProc")
public abstract class InverseProcessor implements OptionProcessor, ParserProcessor {

    @Override
    public void process(List<IToken<?>> tokenList, ParseStructure structure, ParseStructure.ParseSection section) {
        Deque<IToken<?>> tokenDeque = new LinkedList<>();
        ListIterator<IToken<?>> tokenIterator = tokenList.listIterator();

        TokenHolder previousHeadHolder = null;

        while (tokenIterator.hasNext()) {
            int index = tokenIterator.nextIndex();
            IToken<?> token = tokenIterator.next();
            IToken<?> next = next(index, tokenList);
            IToken<?> previous = (index - 1 > -1 ? tokenList.get(tokenIterator.previousIndex()) : null);

            StructureOptions options = optionsOf(token);

            if (options.is(DefaultOptions.Common.IGNORE))
                continue;

            if (!options.is(DefaultOptions.InverseProc.HARD_HEAD) && !options.is(DefaultOptions.Common.HOST)
                    && (options.is(DefaultOptions.Common.STACK) || options.is(DefaultOptions.InverseProc.ELEMENT))) {
                if (next == null || optionsOf(next).is(DefaultOptions.Common.STACK)) {
                    if (previousHeadHolder != null) {
                        previousHeadHolder.link(token);
                    } else {
                        section.enter(structure.addToken(token));
                    }
                } else {
                    tokenDeque.addLast(token);
                }

            } else {

                if (options.is(DefaultOptions.Common.HOST)) {
                    if (!section.canExit()) {
                        previousHeadHolder = structure.addToken(token);
                        section.enter(previousHeadHolder);
                    } else {
                        previousHeadHolder = section.link(token);
                    }
                } else {
                    previousHeadHolder = structure.addToken(token);
                    section.enter(previousHeadHolder);
                }

                for (IToken<?> iToken : tokenDeque) {
                    section.link(iToken);

                    if (optionsOf(iToken).is(DefaultOptions.Common.EXIT)) {
                        section.exit();
                    }
                }
                tokenDeque.clear();

                if (optionsOf(token).is(DefaultOptions.Common.EXIT)) {
                    section.exit();
                }
            }
        }

        if (!tokenDeque.isEmpty()) {
            for (IToken<?> token : tokenDeque) {
                if (previousHeadHolder != null) {
                    previousHeadHolder.link(token);
                } else {
                    section.enter(structure.addToken(token));
                }
            }
        }

    }

    private IToken<?> next(int index, List<IToken<?>> tokenList) {
        for (int x = index + 1; x < tokenList.size(); ++x) {
            IToken<?> token = tokenList.get(x);
            if (!optionsOf(token).is(DefaultOptions.Common.IGNORE)) {
                return token;
            }
        }
        return null;
    }

}
