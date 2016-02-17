package com.github.jonathanxd.textlexer.ext.parser.holder;

import com.github.jonathanxd.textlexer.lexer.token.IToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by jonathan on 17/02/16.
 */
public class TokenHolder {

    final IToken<?> token;
    final List<TokenHolder> childTokens = new ArrayList<>();

    TokenHolder(IToken<?> token) {
        this(token, Collections.emptyList());
    }

    TokenHolder(IToken<?> token, List<TokenHolder> childTokens) {
        this.token = token;

        if (!childTokens.isEmpty())
            this.childTokens.addAll(childTokens);
    }

    public static String toString(TokenHolder holder) {

        StringBuilder sb = new StringBuilder();

        String shortName = "TokenHolder[name="+holder.token.getSimpleName()+", toString="+holder.token.toString()+"]";

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

    public static TokenHolder of(IToken<?> token) {
        return new TokenHolder(token);
    }

    public static TokenHolder of(IToken<?> token, List<TokenHolder> childTokens) {
        return new TokenHolder(token, childTokens);
    }

    public static TokenHolder from(TokenHolder tokenHolder) {
        return new TokenHolder(tokenHolder.token, tokenHolder.childTokens);
    }

    public void link(TokenHolder tokenHolder) {
        childTokens.add(tokenHolder);
    }

    public TokenHolder link(IToken<?> token) {
        TokenHolder holder = of(token);
        childTokens.add(holder);
        return holder;
    }

    public boolean hasChildTokens() {
        return !childTokens.isEmpty();
    }

    public IToken<?> getToken() {
        return token;
    }

    public List<TokenHolder> getChildTokens() {
        return childTokens;
    }

    @Override
    public String toString() {

        return TokenHolder.toString(this);
    }
}
