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
package github.therealbuggy.textparser.lexer.token.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import github.therealbuggy.textparser.Debug;
import github.therealbuggy.textparser.lexer.token.IToken;
import github.therealbuggy.textparser.lexer.token.builder.BuilderList;
import github.therealbuggy.textparser.lexer.token.builder.TokenBuilder;
import github.therealbuggy.textparser.lexer.token.history.ITokenList;
import github.therealbuggy.textparser.lexer.token.history.TokenListImpl;
import github.therealbuggy.textparser.lexer.token.history.analise.AnaliseTokenList;
import github.therealbuggy.textparser.lexer.token.processor.ProcessorData.ProcessorDataBuilder;
import github.therealbuggy.textparser.lexer.token.type.FixedTokenType;
import github.therealbuggy.textparser.lexer.token.type.ITokenType;

/**
 * Created by jonathan on 30/01/16.
 */
public class TokensProcessor implements ITokensProcessor {


    List<ITokenType<?>> tokenTypes = new ArrayList<>();
    BuilderList builderList = new BuilderList();
    ITokenList tokenList = new TokenListImpl();
    ITokenType<?> lastTokenType = null;

    @Override
    public void addTokenType(ITokenType<?> token) {
        if (contains(token))
            return;

        tokenTypes.add(token);

        updateList();
    }

    private void updateList() {
        Collections.sort(tokenTypes, Comparator.comparing(ITokenType::order));
        Collections.sort(tokenTypes, (o1, o2) -> Integer.compare(o1.order(), o2.order()));
    }

    private <T> boolean containsToken(Class<IToken<T>> tokenClass) {
        for (ITokenType<?> tokenType : tokenTypes) {

            if (tokenType instanceof FixedTokenType) {
                FixedTokenType fixedTokenType = (FixedTokenType) tokenType;
                if (fixedTokenType.getTokenClass() == tokenClass) {
                    return true;
                }
            }
        }
        return false;

    }

    private boolean contains(ITokenType<?> token) {
        for (ITokenType<?> tokenType : tokenTypes) {

            if (tokenType instanceof FixedTokenType) {
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

        ITokenType<T> fixedTokenType = new FixedTokenType<>(token, matcher);
        addTokenType(fixedTokenType);

        updateList();
    }

    @Override
    public void process(char input) {
        boolean anyMatch = false;
        Iterator<ITokenType<?>> tokenTypeIterator = tokenTypes.iterator();
        while (tokenTypeIterator.hasNext()) {
            ITokenType<?> type = tokenTypeIterator.next();
            lastTokenType = type;

            ProcessorDataBuilder processorDataBuilder = createProcessorBuilder()
                    .setCharacter(input);

            ProcessorData processorData = processorDataBuilder.build();

            if (type.matches(processorData)) {//tokenList, input

                // END IF NECESSARY, SEE METHOD JAVADOC
                endCurrent(type);
                // END IF NECESSARY, SEE METHOD JAVADOC

                /* RULE CHECK */
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

                TokenBuilder anotherBuilder = type.process(tokenList, builder, input);
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
            throw new RuntimeException("Cannot determine token of character '" + input + "'");
        }
    }

    @Override
    public ITokenList getTokenList() {
        return tokenList;
    }

    private void endCurrent() {
        endCurrent(lastTokenType, true);
    }

    private ProcessorDataBuilder createProcessorBuilder() {
        ProcessorDataBuilder builder = ProcessorData.builder()
                .setTokenList(tokenList)
                .setBuilderList(builderList);

        return builder;
    }

    private void endCurrent(ITokenType<?> type) {
        endCurrent(type, false);
    }

    private void endCurrent(ITokenType<?> type, boolean force) {
        if (builderList.hasCurrent()) {
            if (!builderList.isCurrent(type) || force) {
                ProcessorData processorData = createProcessorBuilder().build();

                IToken<?> token = builderList.endCurrent().build(processorData);
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
}
