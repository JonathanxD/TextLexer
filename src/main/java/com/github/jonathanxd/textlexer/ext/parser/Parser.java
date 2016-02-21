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

import com.github.jonathanxd.textlexer.ext.parser.processor.StructureProcessor;
import com.github.jonathanxd.textlexer.ext.parser.structure.ParseSection;
import com.github.jonathanxd.textlexer.ext.parser.structure.StructuredTokens;
import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.history.ITokenList;

import java.util.ArrayList;
import java.util.List;

/**
 * Parsing Processor
 */
public class Parser {

    /**
     * TokenList
     */
    private final ITokenList tokenList;

    /**
     * Processor list
     */
    private final List<StructureProcessor> processors = new ArrayList<>();

    /**
     * Create new parser
     *
     * @param tokenList TokenList
     */
    public Parser(ITokenList tokenList) {
        this.tokenList = tokenList;
    }


    /**
     * Return TokenList
     *
     * @return TokenList
     */
    public ITokenList getTokenList() {
        return tokenList;
    }

    /**
     * Add a Processor to Processor List
     *
     * @param processor Processor
     */
    public void addProcessor(StructureProcessor processor) {
        getProcessors().add(processor);
    }

    /**
     * Return processors
     *
     * @return Processors
     */
    protected List<StructureProcessor> getProcessors() {
        return processors;
    }

    /**
     * Process Structure calling processor and Return StructuredTokens
     *
     * @return Processed StructuredTokens
     */
    public StructuredTokens process() {

        StructuredTokens structure = new StructuredTokens();
        ParseSection section = structure.createSection();

        List<IToken<?>> tokenList = getTokenList().allToList();

        processors.forEach(processor -> processor.process(tokenList, structure, section));

        processingEnd(structure);

        return structure;

    }

    /**
     * End parsing process
     *
     * @param structure StructuredTokens
     */
    private void processingEnd(StructuredTokens structure) {
        processors.forEach(processor -> processor.processFinish(structure));
    }

    /**
     * @see #process()
     * @return StructuredTokens
     */
    public StructuredTokens parse() {
        return process();
    }

}
