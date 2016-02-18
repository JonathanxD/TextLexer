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
package com.github.jonathanxd.textlexer.ext.parser.holder;

import com.github.jonathanxd.textlexer.lexer.token.IToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.BiConsumer;

/**
 * Created by jonathan on 17/02/16.
 */
public class TokenHolder {

    final String name;
    final List<IToken<?>> tokens = new ArrayList<>();
    final List<TokenHolder> childTokens = new ArrayList<>();

    TokenHolder(String name, IToken<?> token) {
        this(name, Collections.singletonList(token), Collections.emptyList());
    }

    TokenHolder(String name, List<IToken<?>> tokens, List<TokenHolder> childTokens) {
        this.tokens.addAll(tokens);
        this.name = name;
        if (!childTokens.isEmpty())
            this.childTokens.addAll(childTokens);
    }

    public static String toString(TokenHolder holder) {

        StringBuilder sb = new StringBuilder();

        String shortName = "TokenHolder[name=" + holder.name + ",tokens=(" + holder.tokens + ")]";

        sb.append(shortName);

        if (holder.getChildTokens().size() != 0) {

            sb.append("{\n");
            StringJoiner sj = new StringJoiner(", \n");

            for (TokenHolder loopRef : holder.getChildTokens()) {
                sj.add(toString(loopRef));
            }

            String processResult = sj.toString();
            sb.append(processResult);
            sb.append("}\n");
        }

        return sb.toString();
    }

    public static TokenHolder of(String name, IToken<?> token) {
        return new TokenHolder(name(name, token), token);
    }

    private static String name(String name, IToken<?> token) {
        return name != null ? name : token.getSimpleName();
    }

    public static TokenHolder ofHolders(String name, List<TokenHolder> tokenHolder, List<TokenHolder> childTokens) {

        List<IToken<?>> tokens = new ArrayList<>();
        List<TokenHolder> childHolders = new ArrayList<>(childTokens);

        tokenHolder.forEach(token -> {
            tokens.addAll(token.getTokens());
            childHolders.addAll(token.getChildTokens());
        });

        return new TokenHolder(name, tokens, childHolders);
    }

    public static TokenHolder of(String name, List<IToken<?>> token, List<TokenHolder> childTokens) {
        return new TokenHolder(name, token, childTokens);
    }

    public static TokenHolder from(String name, TokenHolder tokenHolder) {
        return new TokenHolder(name, tokenHolder.tokens, tokenHolder.childTokens);
    }

    public static void recursiveLoop(TokenHolder tokenHolder, BiConsumer<TokenHolder, List<IToken<?>>> tokensConsumer) {
        tokensConsumer.accept(tokenHolder, tokenHolder.getTokens());

        List<TokenHolder> child = new ArrayList<>(tokenHolder.getChildTokens());

        for (TokenHolder holder : child) {
            if (holder.hasChildTokens()) {
                recursiveLoop(holder, tokensConsumer);
            } else {
                tokensConsumer.accept(tokenHolder, holder.getTokens());
            }
        }

    }

    public void link(TokenHolder tokenHolder) {
        childTokens.add(tokenHolder);
    }

    public TokenHolder link(IToken<?> token) {
        TokenHolder holder = of(token.getSimpleName(), token);
        childTokens.add(holder);
        return holder;
    }

    public boolean hasChildTokens() {
        return !childTokens.isEmpty();
    }

    public List<IToken<?>> getTokens() {
        return tokens;
    }

    public List<TokenHolder> getChildTokens() {
        return childTokens;
    }

    @Override
    public String toString() {

        return TokenHolder.toString(this);
    }
}
