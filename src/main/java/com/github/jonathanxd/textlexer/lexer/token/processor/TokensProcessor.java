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

import com.github.jonathanxd.iutils.collection.ListUtils;
import com.github.jonathanxd.iutils.iterator.SafeBackableIterator;
import com.github.jonathanxd.textlexer.Debug;
import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.builder.BuilderList;
import com.github.jonathanxd.textlexer.lexer.token.builder.TokenBuilder;
import com.github.jonathanxd.textlexer.lexer.token.factory.FixedTokenFactory;
import com.github.jonathanxd.textlexer.lexer.token.factory.ITokenFactory;
import com.github.jonathanxd.textlexer.lexer.token.history.ITokenList;
import com.github.jonathanxd.textlexer.lexer.token.history.LoopDirection;
import com.github.jonathanxd.textlexer.lexer.token.history.TokenListImpl;
import com.github.jonathanxd.textlexer.lexer.token.history.analise.AnaliseTokenList;
import com.github.jonathanxd.textlexer.lexer.token.processor.future.CurrentTokenData;
import com.github.jonathanxd.textlexer.lexer.token.processor.future.FutureSpec;
import com.github.jonathanxd.textlexer.scanner.IScanner;
import com.github.jonathanxd.textlexer.util.StackArrayList;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

import javax.annotation.Nonnull;

/**
 * Created by jonathan on 30/01/16.
 */
public class TokensProcessor implements ITokensProcessor {


    final Set<ITokenFactory<?>> tokenTypes = new TreeSet<>(new OrderComparator());
    final BuilderList builderList = new BuilderList();
    final ITokenList tokenList = new TokenListImpl();
    ITokenFactory<?> lastTokenType = null;
    private boolean isFuture = false;

    @Override
    public void addTokenType(ITokenFactory<?> token) {
        if (contains(token))
            return;
        tokenTypes.add(token);
    }

    private <T> boolean containsToken(Class<IToken<T>> tokenClass) {

        for (ITokenFactory<?> tokenType : tokenTypes) {

            if (tokenType instanceof FixedTokenFactory) {
                FixedTokenFactory fixedTokenType = (FixedTokenFactory) tokenType;
                if (fixedTokenType.getTokenClass() == tokenClass) {
                    return true;
                }
            }
        }
        return false;

    }

    private boolean contains(ITokenFactory<?> token) {
        for (ITokenFactory<?> tokenType : tokenTypes) {

            if (tokenType instanceof FixedTokenFactory) {
                continue;
            }

            if (tokenType.getClass() == token.getClass()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public <T> void addToken(Class<IToken<T>> token, Predicate<Character> matcher) {
        if (containsToken(token))
            return;

        ITokenFactory<T> fixedTokenType = new FixedTokenFactory<>(token, matcher);
        addTokenType(fixedTokenType);

    }

    public void process(char input, List<Character> allChars, int index, IScanner scanner) {
        boolean anyMatch = false;
        Iterator<ITokenFactory<?>> tokenTypeIterator = tokenTypes.iterator();

        while (tokenTypeIterator.hasNext()) {
            ITokenFactory<?> type = tokenTypeIterator.next();
            lastTokenType = type;

            SafeBackableIterator<Character> backableIterator = ListUtils.toSafeBackableIterator(allChars);

            ProcessorData.ProcessorDataBuilder processorDataBuilder = createProcessorBuilder()
                    .setCharacter(input)
                    .setCharacterIterator(backableIterator)
                    .setIndex(index)
                    .setiScanner(scanner.clone());

            ProcessorData processorData = processorDataBuilder.build();

            if (type.matches(processorData)) {//tokenList, input

                // END IF NECESSARY, SEE METHOD JAVADOC
                endCurrent(type);
                // END IF NECESSARY, SEE METHOD JAVADOC

                /* RULE CHECK DEPRECATED */
                Class<? extends IToken> startToken = type.after();

                if (startToken != null) {
                    Optional<IToken<?>> tokenOptional = AnaliseTokenList.oneSideFind(tokenList, startToken);
                    String errMessage = "Cannot parse '" + input + "', last TokenProcessor '" + type + "' need to be in front of token " + Debug.getClassString(startToken) + ", but, the token cannot be found!";
                    if (!tokenOptional.isPresent())
                        if (tokenTypeIterator.hasNext()) {
                            Debug.printErr(errMessage);
                            Debug.printErr("Table: " + tokenList);
                            continue;
                        } else {
                            throw new RuntimeException(errMessage);
                        }
                }
                /* RULE CHECK */


                TokenBuilder builder = null;
                if (builderList.hasCurrent()
                        && builderList.isCurrent(type)) {
                    builder = builderList.current();

                    // Se o tamanho maximo não for negativo continua
                    // Se não for verifica o tamanho do builder, se ja tiver alcançado o limite irá terminar o build e começar um novo
                    if (type.maxSize() > 0 && builderList.current().length() >= type.maxSize()) {
                        endCurrent(type, /* FORCE = */ true);
                        builder = null;
                    }
                }

                if (builder == null) {
                    builder = new TokenBuilder(type);
                    builderList.add(builder, type);
                }

                TokenBuilder anotherBuilder = type.process(processorData);
                if (anotherBuilder != builder) {
                    // END WITHOUT TYPE CHECKING
                    endCurrent();

                    builderList.add(builder, type);
                }
                anyMatch = true;
                //TODO PROCESS
                break;
            } else {
                //TODO Exception
            }
        }

        if (!anyMatch) {

            throw new RuntimeException("Cannot determine token of character '"
                    + input + "' at index '" + index + "'."
                    + " Current builder data: " + (builderList.hasCurrent() ? "'" + builderList.current().getData() + "'." : "NO CURRENT BUILDER.")
                    + " Future analysis: '" + isFuture + "'."
                    + " TokenList: '" + tokenList + "'."
                    + " Token Types: '" + tokenTypes + "'."
                    + (builderList.hasCurrent() ? " Current Builder: '" + builderList.current() + "'" : ""));
        }
    }

    @Override
    public ITokenList getTokenList() {
        return tokenList;
    }

    public ITokenList clonedTokenList() {
        return tokenList.clone();
    }

    private void endCurrent() {
        endCurrent(lastTokenType, true);
    }

    private ProcessorData.ProcessorDataBuilder createProcessorBuilder() {
        ProcessorData.ProcessorDataBuilder builder = ProcessorData.builder()
                .setTokensProcessor(this)
                .setTokenList(tokenList)
                .setBuilderList(builderList)
                .setFutureAnalysis(isFuture);

        return builder;
    }

    private void endCurrent(ITokenFactory<?> type) {
        endCurrent(type, false);
    }

    private void endCurrent(ITokenFactory<?> type, boolean force) {
        if (builderList.hasCurrent()) {
            if (!builderList.isCurrent(type) || force) {
                ProcessorData processorData = createProcessorBuilder().build();

                TokenBuilder currentBuilder = builderList.endCurrent();

                IToken<?> token = Objects.requireNonNull(
                        currentBuilder.build(processorData),
                        "IToken<?> createToken(ProcessorData) cannot be null! Use hide() method to hide the Token! ITokenFactory: " + currentBuilder.getTokenFactory()
                                + " at "
                                + Debug.getClassString(currentBuilder.getTokenFactory().getClass(), getTokenTypeCreateMethod()));


                if (token != null) {

                    if (type != null && token.getData().length() < type.minSize()) {
                        throw new IllegalStateException("Token data doesn't match ITypeToken minimum size. Data size: "
                                + token.getData().length()
                                + ". Minimum Size: " + type.minSize());
                    }

                    tokenList.add(token);
                }
            }
        }
    }

    private Method getTokenTypeCreateMethod() {
        return Debug.getMethod(ITokenFactory.class, "createToken", new Class<?>[]{ProcessorData.class});
    }

    @Override
    public boolean closeOpenBuilders() {
        if (builderList.hasCurrent()) {
            while (builderList.hasCurrent())
                endCurrent();
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void futureToken(FutureSpec futureSpec, List<IToken<?>> emulatedTokens, CurrentTokenData data, IScanner scanner, boolean ignoreHidden, BiConsumer<StackArrayList<IToken<?>>, ITokensProcessor> futureTokenConsumer) {
        Objects.requireNonNull(scanner);

        StackArrayList<IToken<?>> tokens = new StackArrayList<>(futureSpec.getAmount(), IToken.class);

        TokensProcessor tokensProcessor = clone();

        tokensProcessor.isFuture = true;

        if (scanner.getCurrentIndex() > -1)
            scanner.walkTo(scanner.getCurrentIndex() - 1);

        if (data != null)
            data.apply(tokensProcessor.builderList);

        if (!emulatedTokens.isEmpty())
            for (IToken<?> emulatedToken : emulatedTokens) {
                tokensProcessor.tokenList.add(emulatedToken);
            }

        int keepUnchanged = this.tokenList.size();

        int start = tokensProcessor.tokenList.size();
        int remaining = futureSpec.getAmount();
        int remainingFrom = futureSpec.getStartIn();

        while (tokensProcessor.tokenList.size() == start && scanner.hasNextChar()) {
            char next = scanner.nextChar();

            if (!futureSpec.accept(next, tokensProcessor.clonedTokenList())) {
                start = tokensProcessor.tokenList.size();
                continue;
            }

            int scanIndex = scanner.getCurrentIndex();

            if (remaining == 0) {
                break;
            }

            List<Character> chars = ListUtils.from(scanner.getChars());
            try {
                try{
                    tokensProcessor.process(next, chars, scanIndex, scanner);
                }catch (Throwable t) {

                    Optional<TokenBuilder> tokenBuilderOpt = Optional.empty();

                    if(builderList.hasCurrent())
                        tokenBuilderOpt = Optional.of(builderList.current());

                    if(!futureSpec.acceptError(tokenBuilderOpt, t)) {
                        throw new FutureParseException(t);
                    }
                }


                if (tokensProcessor.tokenList.size() != start) {
                    if (keepUnchanged != this.tokenList.size())
                        throw new RuntimeException("Future system is broken!");

                    IToken<?> last;

                    if ((last = tokensProcessor.tokenList.fetchLast()).hide() && ignoreHidden) {
                        start = tokensProcessor.tokenList.size();
                    }

                    if (!futureSpec.accept(last, tokensProcessor.clonedTokenList())) {
                        start = tokensProcessor.tokenList.size();
                        continue;
                    }

                    if (remaining > 0 && remainingFrom == 0) {
                        --remaining;
                        start = tokensProcessor.tokenList.size();

                        if (!ignoreHidden || !last.hide()) {
                            boolean add = tokens.add(last);
                            if (!add) {
                                throw new RuntimeException("Cannot add new element ('" + last + "') to list. List max size: " + tokens.size() + ". Current size: " + tokens.sizeWithoutNull() + ". List elements: " + tokens);
                            }
                        }
                    }

                    if (remainingFrom > 0) {
                        --remainingFrom;
                        start = tokensProcessor.tokenList.size();
                    }

                }

            } catch (Exception e) {
                throw new RuntimeException("Cannot get future token. [TokenList: " + tokensProcessor.getTokenList() + "]", e);
            }
        }


        futureTokenConsumer.accept(tokens, tokensProcessor);
    }

    @Nonnull
    @Override
    public TokensProcessor clone() {
        TokensProcessor tokensProcessor = new TokensProcessor();

        // Appliers
        this.tokenTypes.forEach(tokensProcessor::addTokenType);
        this.builderList.applyTo(tokensProcessor.builderList);
        this.tokenList.forEach(tokensProcessor.tokenList::add, LoopDirection.FIRST_TO_LAST);

        // Set
        tokensProcessor.lastTokenType = this.lastTokenType;

        return tokensProcessor;
    }

    private class FutureParseException extends RuntimeException {
        public FutureParseException() {
            super();
        }

        public FutureParseException(String message) {
            super(message);
        }

        public FutureParseException(String message, Throwable cause) {
            super(message, cause);
        }

        public FutureParseException(Throwable cause) {
            super(cause);
        }

        protected FutureParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
