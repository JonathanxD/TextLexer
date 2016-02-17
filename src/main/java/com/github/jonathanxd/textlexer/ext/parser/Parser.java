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
package com.github.jonathanxd.textlexer.ext.parser;

import com.github.jonathanxd.iutils.collection.ListSafeBackableIterator;
import com.github.jonathanxd.iutils.iterator.IteratorUtil;
import com.github.jonathanxd.iutils.iterator.SafeBackableIterator;
import com.github.jonathanxd.iutils.optional.Require;
import com.github.jonathanxd.textlexer.ext.parser.holder.TokenHolder;
import com.github.jonathanxd.textlexer.ext.parser.processor.ParserProcessor;
import com.github.jonathanxd.textlexer.ext.parser.structure.Options;
import com.github.jonathanxd.textlexer.ext.parser.structure.ParseStructure;
import com.github.jonathanxd.textlexer.ext.parser.structure.StructureOptions;
import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.history.ITokenList;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by jonathan on 17/02/16.
 */
public class Parser {

    private final ITokenList tokenList;
    private final List<ParserProcessor> processors = new ArrayList<>();
    private final List<TokenHolder> tokenHolders = new ArrayList<>();

    public Parser(ITokenList tokenList) {
        this.tokenList = tokenList;
    }

    public ITokenList getTokenList() {
        return tokenList;
    }

    public void addProcessor(ParserProcessor processor) {
        getProcessors().add(processor);
    }

    protected List<ParserProcessor> getProcessors() {
        return processors;
    }

    public ParseStructure process(boolean _testWay) {

        ParseStructure structure = new ParseStructure();
        ParseStructure.ParseSection section = structure.createSection();

        Deque<IToken<?>> tokenDeque = new LinkedList<>();
        List<IToken<?>> tokenList = getTokenList().allToList();


        ListIterator<IToken<?>> tokenIterator = tokenList.listIterator();

        TokenHolder previousHeadHolder = null;

        while(tokenIterator.hasNext()) {
            int index = tokenIterator.nextIndex();
            IToken<?> token = tokenIterator.next();
            IToken<?> next = (index + 1 < tokenList.size() ? tokenList.get(tokenIterator.nextIndex()) : null);
            IToken<?> previous = (index -1 > -1 ? tokenList.get(tokenIterator.previousIndex()) : null);


            if(_testWay) {
                call(token, index, tokenList, structure, section);
            }else{
                StructureOptions options = optionsOf(token);

                if(options.is(Options.IGNORE))
                    continue;

                if(!options.is(Options.HARD_HEAD) && !options.is(Options.HEAD)
                        && (options.is(Options.STACK) || options.is(Options.ELEMENT))) {
                    if(next == null || optionsOf(next).is(Options.STACK)) {
                        if(previousHeadHolder != null) {
                            previousHeadHolder.link(token);
                        }else{
                            section.enter(structure.addToken(token));
                        }
                    }else{
                        tokenDeque.addLast(token);
                    }

                } else {

                    if(options.is(Options.HEAD)) {
                        if(!section.canExit()) {
                            previousHeadHolder = structure.addToken(token);
                            section.enter(previousHeadHolder);
                        }else{
                            previousHeadHolder = section.link(token);
                        }
                    }else{
                        previousHeadHolder = structure.addToken(token);
                        section.enter(previousHeadHolder);
                    }

                    for(IToken<?> iToken : tokenDeque) {
                        section.link(iToken);

                        if(optionsOf(iToken).is(Options.EXIT)) {
                            section.exit();
                        }
                    }
                    tokenDeque.clear();

                    if(optionsOf(token).is(Options.EXIT)) {
                        section.exit();
                    }
                }
            }
        }

        if(!tokenDeque.isEmpty()) {
            for(IToken<?> token : tokenDeque) {
                if(previousHeadHolder != null) {
                    previousHeadHolder.link(token);
                }else{
                    section.enter(structure.addToken(token));
                }
            }
        }

        return structure;

    }

    private void call(IToken<?> token, int index, List<IToken<?>> tokenList,  ParseStructure structure, ParseStructure.ParseSection section) {
        for(ParserProcessor parserProcessor : processors) {
            ListIterator<IToken<?>> iterator = tokenList.listIterator(index);
            Processor processor = new AProcessor(token, iterator, structure, section);
            parserProcessor.process(processor);
        }
    }

    public StructureOptions optionsOf(IToken<?> token) {
        Optional<ParserProcessor> processorOptional = processors.stream().findFirst();
        return Objects.requireNonNull(Require.require(processorOptional, "Cannot find Options for Tokens: "+token).tokenOptions(token), "Null options!");
    }



    public ParseStructure parse() {
        return process(false);
    }

    private static final class AProcessor implements Processor {

        private final IToken<?> token;
        private final ListIterator<IToken<?>> iterator;
        private final ParseStructure structure;
        private final ParseStructure.ParseSection section;

        private AProcessor(IToken<?> token, ListIterator<IToken<?>> iterator, ParseStructure structure, ParseStructure.ParseSection section) {
            this.token = token;
            this.iterator = iterator;
            this.structure = structure;
            this.section = section;
        }


        @Override
        public IToken<?> currentToken() {
            return token;
        }

        @Override
        public IToken<?> safeNext() {
            return iterator.next();
        }

        @Override
        public ParseStructure structure() {
            return structure;
        }

        @Override
        public ParseStructure.ParseSection section() {
            return section;
        }
    }
}
