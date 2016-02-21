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
package com.github.jonathanxd.textlexer.ext.parser.structure;

import com.github.jonathanxd.textlexer.ext.parser.holder.TokenHolder;
import com.github.jonathanxd.textlexer.ext.parser.structure.modifier.StructureModifier;
import com.github.jonathanxd.textlexer.lexer.token.IToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by jonathan on 17/02/16.
 */

/**
 * StructuredTokens
 */
public class StructuredTokens {
    /**
     * TokenHolders
     */
    List<TokenHolder> tokenHolders = new ArrayList<>();

    /**
     * Add a token
     *
     * @param token Token
     * @return new TokenHolder
     */
    public TokenHolder addToken(IToken<?> token) {
        TokenHolder holder = TokenHolder.of(null, token);
        tokenHolders.add(holder);
        return holder;
    }

    /**
     * Link token to tokenHulder
     *
     * @param tokenHolder TokenHolder
     * @param token       Token to link to TokenHolder
     */
    public void link(TokenHolder tokenHolder, IToken<?> token) {
        tokenHolders.stream().filter(holder -> holder == tokenHolder).forEach(holder -> holder.link(token));
    }

    /**
     * Create modifier
     *
     * @return StructureModifier
     */
    public StructureModifier createModifier() {
        return new StructureModifier(this);
    }

    /**
     * Create a ParseSection
     *
     * @return ParseSection
     */
    public ParseSection createSection() {
        return new ParseSection(this);
    }

    /**
     * Remove a TokenHolder
     *
     * @param tokenHolder TokenHolder
     */
    public void remove(TokenHolder tokenHolder) {

        List<Runnable> removeSched = new ArrayList<>();
        for (TokenHolder headHolder : this.getTokenHolders()) {
            TokenHolder.recursive(headHolder, this.getTokenHolders(), (tokenHolders1, tokenHolder1) -> {
                if (tokenHolder1 == tokenHolder) {
                    removeSched.add(() -> tokenHolders1.remove(tokenHolder1));
                }
            });
        }

        removeSched.forEach(Runnable::run);
    }

    /**
     * Replace e TokenHolder
     *
     * @param tokenHolder    TokenHolder to Replace
     * @param newTokenHolder New TokenHolder
     */
    public void replace(TokenHolder tokenHolder, TokenHolder newTokenHolder) {

        List<Runnable> replaceSched = new ArrayList<>();
        for (TokenHolder headHolder : this.getTokenHolders()) {
            TokenHolder.recursive(headHolder, this.getTokenHolders(), (tokenHolders1, tokenHolder1) -> {
                if (tokenHolder1 == tokenHolder) {
                    replaceSched.add(() -> Collections.replaceAll(tokenHolders1, tokenHolder, newTokenHolder));
                }
            });
        }

        replaceSched.forEach(Runnable::run);
    }

    /**
     * Get all TokenHolders
     *
     * @return All TokenHolders
     */
    public List<TokenHolder> getTokenHolders() {
        return tokenHolders;
    }

    @Override
    public String toString() {

        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");

        for (TokenHolder holder : tokenHolders) {
            stringJoiner.add(holder.toString());
        }

        return this.getClass().getSimpleName() + ":( " + stringJoiner.toString() + " )";
    }
}
