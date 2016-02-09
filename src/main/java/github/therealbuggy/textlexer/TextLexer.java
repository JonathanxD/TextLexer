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
package github.therealbuggy.textlexer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import github.therealbuggy.textlexer.lexer.LexerImpl;
import github.therealbuggy.textlexer.lexer.token.IToken;
import github.therealbuggy.textlexer.lexer.token.history.ITokenList;
import github.therealbuggy.textlexer.lexer.token.processor.TokensProcessor;
import github.therealbuggy.textlexer.lexer.token.structure.analise.StructureAnalyzer;
import github.therealbuggy.textlexer.lexer.token.type.ITokenType;
import github.therealbuggy.textlexer.scanner.CharScanner;
import github.therealbuggy.textlexer.scanner.IScanner;

/**
 * Created by jonathan on 06/02/16.
 */
public final class TextLexer {

    private final TokensProcessor tokensProcessor = new TokensProcessor();
    private final List<StructureAnalyzer> analyzers = new ArrayList<>();

    public TextLexer addTokenType(ITokenType<?> tokenType) {
        tokensProcessor.addTokenType(tokenType);
        return this;
    }

    @SafeVarargs
    public final TextLexer addTokenTypes(ITokenType<?>... tokenTypes) {
        for (ITokenType<?> aTokenType : tokenTypes)
            addTokenType(aTokenType);
        return this;
    }

    @SafeVarargs
    public final TextLexer addTokenTypes(Class<? extends ITokenType>... tokenTypesClass) throws InstantiationException, IllegalAccessException {
        for (Class<? extends ITokenType> aTokenTypeClass : tokenTypesClass) {
            addTokenType(aTokenTypeClass.newInstance());
        }
        return this;
    }

    public <T> TextLexer addToken(Class<IToken<T>> tokenClass, Predicate<Character> predicate) {
        tokensProcessor.addToken(tokenClass, predicate);
        return this;
    }

    public TextLexer processFile(File file) {
        LexerImpl lexer = new LexerImpl(analyzers);
        lexer.process(new FileScanner(file), tokensProcessor);

        return this;
    }

    public TextLexer processString(String string) {
        LexerImpl lexer = new LexerImpl(analyzers);
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
        public Set<ITokenType<?>> getOrderedTokenSet() throws Exception {
            Field field = TokensProcessor.class.getDeclaredField("tokenTypes");
            field.setAccessible(true);
            Set<ITokenType<?>> list = (Set<ITokenType<?>>) field.get(lexer.tokensProcessor);
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
        public char[] getChars() {
            return chars;
        }

        @Override
        public boolean hasNextChar() {
            return currentIndex + 1 < chars.length;
        }

        @Override
        public int getCurrentIndex() {
            return currentIndex;
        }

        @Override
        public Object getSource() {
            return file;
        }
    }
}
