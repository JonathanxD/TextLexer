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
package com.github.jonathanxd.textlexer.lexer;

import com.github.jonathanxd.iutils.collection.ListUtils;
import com.github.jonathanxd.textlexer.lexer.token.history.ITokenList;
import com.github.jonathanxd.textlexer.lexer.token.processor.ITokensProcessor;
import com.github.jonathanxd.textlexer.lexer.token.structure.analise.StructureAnalyzer;
import com.github.jonathanxd.textlexer.scanner.IScanner;

import java.util.Collections;
import java.util.List;

/**
 * Created by jonathan on 30/01/16.
 */
public class LexerImpl implements ILexer {

    private final List<StructureAnalyzer> structureAnalyzers;
    private IScanner currentScanner = null;

    public LexerImpl(List<StructureAnalyzer> structureAnalyzers) {
        this.structureAnalyzers = structureAnalyzers;
    }


    @Override
    public ITokenList process(IScanner scanner, ITokensProcessor tokenTypeList) {

        if(currentScanner != null && currentScanner != scanner)
            throw new RuntimeException("Doesn't supports 2 process method call with different scanners!");

        this.currentScanner = scanner;

        List<Character> characters = Collections.unmodifiableList(ListUtils.from(scanner.getChars()));

        while (scanner.hasNextChar()) {
            char current = scanner.nextChar();
            tokenTypeList.process(current, characters, scanner.getCurrentIndex(), scanner);
        }
        tokenTypeList.closeOpenBuilders();

        ITokenList tokenList = tokenTypeList.getTokenList();
        analyse(tokenList);

        this.currentScanner = null;

        return tokenList;
    }

    @Override
    public void analyse(ITokenList tokenList) {
        if (structureAnalyzers != null && !structureAnalyzers.isEmpty())
            for (StructureAnalyzer analyzer : structureAnalyzers)
                analyzer.analyse(tokenList);
    }

    @Override
    public ILexer clone() {

        Object cloneLexer = null;

        try{
            cloneLexer = super.clone();
        } catch (CloneNotSupportedException ignored) {
        }

        if(cloneLexer == null || !(cloneLexer instanceof LexerImpl))
            cloneLexer = new LexerImpl(this.structureAnalyzers);

        LexerImpl lexer = (LexerImpl) cloneLexer;
        lexer.currentScanner = this.currentScanner;
        return lexer;
    }
}
