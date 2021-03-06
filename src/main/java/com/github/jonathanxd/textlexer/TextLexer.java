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
package com.github.jonathanxd.textlexer;

import com.github.jonathanxd.textlexer.lexer.ILexer;
import com.github.jonathanxd.textlexer.lexer.LexerImpl;
import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.history.ITokenList;
import com.github.jonathanxd.textlexer.lexer.token.processor.TokensProcessor;
import com.github.jonathanxd.textlexer.lexer.token.structure.analise.StructureAnalyzer;
import com.github.jonathanxd.textlexer.lexer.token.factory.ITokenFactory;
import com.github.jonathanxd.textlexer.scanner.CharScanner;
import com.github.jonathanxd.textlexer.scanner.IScanner;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Created by jonathan on 06/02/16.
 */
public final class TextLexer {

    private final TokensProcessor tokensProcessor = new TokensProcessor();
    private final List<StructureAnalyzer> analyzers = new ArrayList<>();

    public TextLexer addTokenType(ITokenFactory<?> tokenType) {
        tokensProcessor.addTokenType(tokenType);
        return this;
    }

    @SafeVarargs
    public final TextLexer addTokenTypes(ITokenFactory<?>... tokenTypes) {
        for (ITokenFactory<?> aTokenType : tokenTypes)
            addTokenType(aTokenType);
        return this;
    }

    @SafeVarargs
    public final TextLexer addTokenTypes(Class<? extends ITokenFactory>... tokenTypesClass) throws InstantiationException, IllegalAccessException {
        for (Class<? extends ITokenFactory> aTokenTypeClass : tokenTypesClass) {
            addTokenType(aTokenTypeClass.newInstance());
        }
        return this;
    }

    public <T> TextLexer addToken(Class<IToken<T>> tokenClass, Predicate<Character> predicate) {
        tokensProcessor.addToken(tokenClass, predicate);
        return this;
    }

    public TextLexer processFile(File file) {
        ILexer lexer = new LexerImpl(analyzers);
        lexer.process(new FileScanner(file), tokensProcessor);

        return this;
    }

    public TextLexer processString(String string) {
        ILexer lexer = new LexerImpl(analyzers);
        lexer.process(new CharScanner(string.toCharArray()), tokensProcessor);
        return this;
    }

    public ITokenList getTokens() {
        return tokensProcessor.getTokenList();
    }

    public Util getReflectionUtil() {
        return new Util(this);
    }

    public void addStructureAnalyzer(StructureAnalyzer analyzer) {
        analyzers.add(analyzer);
    }

    public static class Util {
        private final TextLexer lexer;

        public Util(TextLexer lexer) {
            this.lexer = lexer;
        }

        @SuppressWarnings("unchecked")
        public Set<ITokenFactory<?>> getOrderedTokenSet() throws Exception {
            Field field = TokensProcessor.class.getDeclaredField("tokenTypes");
            field.setAccessible(true);
            Set<ITokenFactory<?>> list = (Set<ITokenFactory<?>>) field.get(lexer.tokensProcessor);
            return Collections.unmodifiableSet(list);
        }
    }

    private static class FileScanner implements IScanner {

        private final File file;
        private final char[] chars;
        private int currentIndex = -1;

        private FileScanner(File file) {
            this.file = file;
            try {
                byte[] bytes = Files.readAllBytes(file.toPath());
                chars = new char[bytes.length];

                for (int x = 0; x < bytes.length; ++x) {
                    chars[x] = (char) bytes[x];
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public char nextChar() {
            return chars[++currentIndex];
        }

        @Override
        public char previousChar() {
            if (!hasNextChar()) {
                throw new ArrayIndexOutOfBoundsException(String.format("Index: %d. Size: %d. Next: %d", currentIndex, chars.length, currentIndex + 1));
            }

            return chars[--currentIndex];
        }

        @Override
        public char[] getChars() {
            return chars;
        }

        @Override
        public boolean hasNextChar() {
            return currentIndex + 1 < chars.length;
        }

        @Override
        public boolean hasPreviousChar() {
            return currentIndex - 1 > -1;
        }

        @Override
        public int getCurrentIndex() {
            return currentIndex;
        }

        @Override
        public Object getSource() {
            return file;
        }

        @Override
        public void walkTo(int i) {
            currentIndex = i;
        }

        @Override
        public FileScanner clone() {
            FileScanner fileScanner = new FileScanner(this.file);
            fileScanner.currentIndex = this.currentIndex;
            return fileScanner;
        }
    }
}
