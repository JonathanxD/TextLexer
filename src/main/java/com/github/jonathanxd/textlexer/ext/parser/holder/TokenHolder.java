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

import com.github.jonathanxd.textlexer.ext.common.TokenElementType;
import com.github.jonathanxd.textlexer.ext.parser.structure.StructuredTokens;
import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.history.list.CommonTokenList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.BiConsumer;

/**
 * Created by jonathan on 17/02/16.
 */

/**
 * This class have a List with Tokens and Child Tokens
 *
 * TokenHolder have a multiple "head" tokens because usually the Tokens like '{' and '}' belong the
 * same element.
 */
public class TokenHolder {

    /**
     * Name of this TokenHolder, commonly is the first Token name or a Token determined by Parser
     */
    final String name;

    /**
     * Parent Nullable Token
     */
    final TokenHolder parent;

    /**
     * "Head"/"Host" Tokens
     */
    final CommonTokenList tokens = CommonTokenList.mutable();

    /**
     * Child Tokens
     */
    final List<TokenHolder> childTokens = new ArrayList<>();

    /**
     * Create TokenHolder from a Name and Token with null parent and empty ChildTokens
     *
     * @param name  Name
     * @param token Token
     */
    TokenHolder(String name, IToken<?> token) {
        this(name, Collections.singletonList(token), Collections.emptyList());
    }

    /**
     * Create a TokenHolder with determined name, parent, tokens and childTokens
     *
     * @param name        Name
     * @param parent      Parent TokenHolder
     * @param tokens      "Head"/"Host" tokens
     * @param childTokens Child tokens
     */
    TokenHolder(String name, TokenHolder parent, List<IToken<?>> tokens, List<TokenHolder> childTokens) {
        this.tokens.addAll(tokens);
        this.name = name;
        this.parent = parent;
        if (!childTokens.isEmpty())
            this.childTokens.addAll(childTokens);
    }

    /**
     * Create a TokenHolder with determined name, tokens and childTokens, with null parent
     *
     * @param name        Name
     * @param tokens      "Head"/"Host" tokens
     * @param childTokens Child Tokens
     */
    TokenHolder(String name, List<IToken<?>> tokens, List<TokenHolder> childTokens) {
        this(name, null, tokens, childTokens);
    }

    /**
     * Recursive ToString
     *
     * @param holder TokenHolder
     * @return String representation
     */
    public static String toString(TokenHolder holder) {

        StringBuilder sb = new StringBuilder();

        String shortName = "TokenHolder[name=" + holder.name + ", parent=" + (holder.parent != null ? holder.parent.getName() : "<main>") + ", tokens=(" + holder.tokens + ")]";

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

    /**
     * Crete TokenHolder
     *
     * @param name  Nullable Name
     * @param token "Head"/"Host" token
     * @return TokenHolder
     */
    public static TokenHolder of(String name, TokenHolder parent, IToken<?> token) {
        return new TokenHolder(name(name, token), parent, Collections.singletonList(token), Collections.emptyList());
    }

    /**
     * Get name if not null, or Token simple name
     *
     * @param name  Nullable Name
     * @param token Token
     * @return Name
     * @see IToken#getSimpleName()
     */
    private static String name(String name, IToken<?> token) {
        return name != null ? name : token.getSimpleName();
    }

    /**
     * Create a TokenHolder
     *
     * @param name        NotNull Name
     * @param tokenHolder "Head"/"Host" TokenHolder List
     * @param childTokens Child Tokens
     * @return TokenHolder
     */
    public static TokenHolder ofHolders(String name, List<TokenHolder> tokenHolder, List<TokenHolder> childTokens) {

        List<IToken<?>> tokens = new ArrayList<>();
        List<TokenHolder> childHolders = new ArrayList<>(childTokens);

        tokenHolder.forEach(token -> {
            tokens.addAll(token.getTokens());
            childHolders.addAll(token.getChildTokens());
        });

        return new TokenHolder(name, tokens, childHolders);
    }

    /**
     * Create a token holder
     *
     * @param name        Name of TokenHolder
     * @param token       "Head"/"Host" tokens
     * @param childTokens Child Tokens
     * @return TokenHolder
     */
    public static TokenHolder of(String name, TokenHolder parent, CommonTokenList token, List<TokenHolder> childTokens) {
        return new TokenHolder(name, parent, token, childTokens);
    }

    /**
     * Create a new TokenHolder based in another
     *
     * @param name        Name
     * @param tokenHolder Other TokenHolder
     * @return TokenHolder
     */
    public static TokenHolder from(String name, TokenHolder parent, TokenHolder tokenHolder) {
        return new TokenHolder(name, parent, tokenHolder.tokens, tokenHolder.childTokens);
    }

    /**
     * Do a recursive loop in TokenHolders
     *
     * @param tokenHolder    TokenHolder TokenHolder to do a recursive loop
     * @param structure      Structure passed to TokenLoopCallback
     * @param tokensConsumer Consumer TokenLoopCallback to receive tokens, the first received tokens
     *                       are HeadTokens
     */
    public static void recursiveLoop(TokenHolder tokenHolder, StructuredTokens structure, TokenLoopCallback tokensConsumer) {
        tokensConsumer.accept(tokenHolder, tokenHolder.getTokens(), structure, TokenElementType.HEAD);

        List<TokenHolder> child = new ArrayList<>(tokenHolder.getChildTokens());

        for (TokenHolder holder : child) {
            if (holder.hasChildTokens()) {
                recursiveLoop(holder, structure, tokensConsumer);
            } else {
                tokensConsumer.accept(tokenHolder, holder.getTokens(), structure, TokenElementType.CHILD_PART);
            }
        }

    }

    public static boolean recursiveChildCheck(List<TokenHolder> tokenHolder, TokenHolder child) {
        for (TokenHolder holder : tokenHolder) {
            if (recursiveChildCheck(holder, child)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Do a recursive loop in TokenHolders and check child token exists
     *
     * @param tokenHolder TokenHolder TokenHolder to do a recursive loop
     * @param child       Child holder to check
     */
    public static boolean recursiveChildCheck(TokenHolder tokenHolder, TokenHolder child) {

        if (tokenHolder.getChildTokens().contains(child)) {
            return true;
        }


        for (TokenHolder holder : tokenHolder.getChildTokens()) {
            if (recursiveChildCheck(holder, child)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Do a recursive loop in TokenHolders
     *
     * @param holder              TokenHolder
     * @param tokenHolderList     TokenHolderList
     * @param tokenHolderConsumer Consumer
     */
    public static void recursive(TokenHolder holder, List<TokenHolder> tokenHolderList, BiConsumer<List<TokenHolder>, TokenHolder> tokenHolderConsumer) {
        tokenHolderConsumer.accept(tokenHolderList, holder);
        for (TokenHolder tokenHolder : holder.getChildTokens()) {
            recursive(tokenHolder, holder.getChildTokens(), tokenHolderConsumer);
        }
    }

    /**
     * Get TokenHolder name
     *
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * Link this token to other
     *
     * @param tokenHolder Other TokenHolder
     */
    public void link(TokenHolder tokenHolder) {
        childTokens.add(tokenHolder);
    }

    /**
     * Link this token to other
     *
     * @param token Token
     * @return New TokenHolder created for Token
     */
    public TokenHolder link(IToken<?> token, TokenHolder parent) {
        TokenHolder holder = of(token.getSimpleName(), parent, token);
        childTokens.add(holder);
        return holder;
    }

    /**
     * Return true if this TokenHolder has child elements
     *
     * @return Return true if this TokenHolder has child elements
     */
    public boolean hasChildTokens() {
        return !childTokens.isEmpty();
    }

    /**
     * Get "Head"/"Host" Tokens
     *
     * @return "Head"/"Host" Tokens
     */
    public List<IToken<?>> getTokens() {
        return tokens;
    }

    /**
     * Get Child TokenHolders
     *
     * @return Child TokenHolders
     */
    public List<TokenHolder> getChildTokens() {
        return childTokens;
    }

    /**
     * Get parent token
     *
     * @return Parent Token
     */
    public Optional<TokenHolder> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public String toString() {
        return TokenHolder.toString(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tokens, childTokens);
    }

    public int creationHashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.hashCode() == obj.hashCode();
    }
}
