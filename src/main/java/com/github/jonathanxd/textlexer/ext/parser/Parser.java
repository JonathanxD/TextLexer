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

import com.github.jonathanxd.textlexer.ext.parser.holder.TokenHolder;
import com.github.jonathanxd.textlexer.ext.parser.processor.ParserProcessor;
import com.github.jonathanxd.textlexer.ext.parser.structure.ParseStructure;
import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.history.ITokenList;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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

    public ParseStructure process() {

        ParseStructure structure = new ParseStructure();
        ParseStructure.ParseSection section = structure.createSection();

        List<IToken<?>> tokenList = getTokenList().allToList();

        processors.forEach(processor -> processor.process(tokenList, structure, section));

        processingEnd(structure);

        return structure;

    }

    private void processingEnd(ParseStructure structure) {
        processors.forEach(processor -> processor.processFinish(structure));
    }


    public ParseStructure parse() {
        return process();
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
