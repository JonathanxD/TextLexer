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
package com.github.jonathanxd.textlexer.lexer.token.processor;

import com.github.jonathanxd.iutils.iterator.SafeBackableIterator;
import com.github.jonathanxd.textlexer.lexer.AnnonData;
import com.github.jonathanxd.textlexer.lexer.token.builder.BuilderList;
import com.github.jonathanxd.textlexer.lexer.token.builder.TokenBuilder;
import com.github.jonathanxd.textlexer.lexer.token.factory.ITokenFactory;
import com.github.jonathanxd.textlexer.lexer.token.history.ITokenList;
import com.github.jonathanxd.textlexer.lexer.token.history.LoopDirection;
import com.github.jonathanxd.textlexer.scanner.IScanner;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by jonathan on 07/02/16.
 */
public class ProcessorData {
    private final ITokenList tokenList;
    private final BuilderList builderList;
    private final Character character;
    private final String data;
    private final int index;
    private final SafeBackableIterator<Character> characterIterator;
    private final IScanner scanner;
    private final ITokensProcessor tokensProcessor;
    private final boolean isFutureAnalysis;

    public ProcessorData(ITokenList tokenList, BuilderList builderList, Character character, String data, int index, SafeBackableIterator<Character> characterIterator, IScanner scanner, ITokensProcessor tokensProcessor, boolean isFutureAnalysis) {
        this.tokenList = tokenList;
        this.builderList = builderList;
        this.character = character;
        this.data = data;
        this.index = index;
        this.characterIterator = characterIterator;
        this.scanner = scanner;
        this.tokensProcessor = tokensProcessor;
        this.isFutureAnalysis = isFutureAnalysis;
    }

    public ProcessorData(ITokenList tokenList, BuilderList builderList, char character, SafeBackableIterator<Character> characterIterator, int index, IScanner scanner, ITokensProcessor tokensProcessor, boolean isFutureAnalysis) {
        this(tokenList, builderList, character, null, index, characterIterator, scanner, tokensProcessor, isFutureAnalysis);
    }

    public ProcessorData(ITokenList tokenList, BuilderList builderList, String data, SafeBackableIterator<Character> characterIterator, int index, IScanner scanner, ITokensProcessor tokensProcessor, boolean isFutureAnalysis) {
        this(tokenList, builderList, null, data, index, characterIterator, scanner, tokensProcessor, isFutureAnalysis);
    }

    public static ProcessorDataBuilder builder() {
        return new ProcessorDataBuilder();
    }

    public ITokenList getTokenList() {
        return tokenList;
    }

    public BuilderList getBuilderList() {
        return builderList;
    }

    public Character getCharacter() {
        return character;
    }

    public String getData() {
        return data;
    }

    public SafeBackableIterator<Character> getCharacterIterator() {
        return characterIterator;
    }

    public int getIndex() {
        return index;
    }

    public IScanner getScanner() {
        return scanner;
    }

    public ITokensProcessor getTokensProcessor() {
        return tokensProcessor;
    }


    public AnnonData getLastToken() {
        return getLastToken(true, (data) -> true).orElse(null);
    }

    /**
     * Get last token
     *
     * @param includeFactory True to Include ITokenFactory in {@link #builderList}
     * @param dataPredicate  Predicate for AnnonData (will try all tokens from last to first)
     * @return {@link Optional} with AnnonData or {@link Optional#empty()}
     */
    public Optional<AnnonData> getLastToken(boolean includeFactory, Predicate<AnnonData> dataPredicate) {
        if (includeFactory && builderList.hasCurrent()) {
            TokenBuilder tokenBuilder = builderList.current();
            ITokenFactory<?> source = tokenBuilder.getTokenFactory();
            AnnonData annonData = new AnnonData(source, tokenBuilder.getData());

            if (dataPredicate.test(annonData)) {
                return annonData.isValid() ? Optional.of(annonData) : Optional.empty();
            }
        }

        final AnnonData annonData = AnnonData.invalid();

        tokenList.forEach(source -> {

            // "Stop loop"
            if(annonData.isValid()) return;

            // Performance improved
            annonData.redefine(source, source.mutableData().get());

            if (!dataPredicate.test(annonData)) {
                annonData.invalidate();
            }
        }, LoopDirection.LAST_TO_FIRST);


        return annonData.isValid() ? Optional.of(annonData) : Optional.empty();

    }

    public boolean hasLastToken() {
        return builderList.hasCurrent() || tokenList.size() > 0;
    }

    public boolean isFutureAnalysis() {
        return isFutureAnalysis;
    }

    public boolean charPresent() {
        return character != null;
    }

    public boolean dataPresent() {
        return data != null;
    }

    public boolean builderPresent() {
        return builderList != null;
    }

    public boolean tokenListPresent() {
        return builderList != null;
    }

    public boolean characterIteratorPresent() {
        return characterIterator != null;
    }

    public boolean scannerPresent() {
        return scanner != null;
    }

    public boolean tokensProcessorPresent() {
        return tokensProcessor != null;
    }

    public static class ProcessorDataBuilder {
        private String data;
        private ITokenList tokenList;
        private BuilderList builderList;
        private Character character;
        private int index;
        private SafeBackableIterator<Character> characterIterator;
        private IScanner iScanner;
        private ITokensProcessor tokensProcessor;
        private boolean isFutureAnalysis = false;

        private ProcessorDataBuilder() {
        }

        public ProcessorDataBuilder setData(String data) {
            this.data = data;
            return this;
        }

        public ProcessorDataBuilder setTokenList(ITokenList tokenList) {
            this.tokenList = tokenList;
            return this;
        }

        public ProcessorDataBuilder setBuilderList(BuilderList builderList) {
            this.builderList = builderList;
            return this;
        }

        public ProcessorDataBuilder setCharacter(Character character) {
            this.character = character;
            return this;
        }

        public ProcessorDataBuilder setCharacterIterator(SafeBackableIterator<Character> characterIterator) {
            this.characterIterator = characterIterator;
            return this;
        }

        public ProcessorDataBuilder setIndex(int index) {
            this.index = index;
            return this;
        }

        public ProcessorDataBuilder setiScanner(IScanner iScanner) {
            this.iScanner = iScanner;
            return this;
        }

        public ProcessorDataBuilder setTokensProcessor(ITokensProcessor tokensProcessor) {
            this.tokensProcessor = tokensProcessor;
            return this;
        }

        public ProcessorDataBuilder setFutureAnalysis(boolean futureAnalysis) {
            isFutureAnalysis = futureAnalysis;
            return this;
        }

        public ProcessorData build() {
            ProcessorData processorData = new ProcessorData(tokenList, builderList, character, data, index, characterIterator, iScanner, tokensProcessor, isFutureAnalysis);
            return processorData;
        }

        public ProcessorDataBuilder from(ProcessorData data) {
            return new ProcessorDataBuilder()
                    .setCharacter(data.getCharacter())
                    .setBuilderList(data.getBuilderList())
                    .setData(data.getData())
                    .setTokenList(data.getTokenList())
                    .setCharacterIterator(data.getCharacterIterator())
                    .setIndex(data.getIndex())
                    .setiScanner(data.getScanner())
                    .setTokensProcessor(data.getTokensProcessor())
                    .setFutureAnalysis(data.isFutureAnalysis());
        }
    }
}
