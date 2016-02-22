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
package com.github.jonathanxd.textlexer.ext.parser.structure.modifier;

import com.github.jonathanxd.textlexer.ext.parser.holder.TokenHolder;
import com.github.jonathanxd.textlexer.ext.parser.structure.StructuredTokens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by jonathan on 18/02/16.
 */

/**
 * Structure modification utilities
 */
public class StructureModifier {

    /**
     * Structure
     */
    private final StructuredTokens structure;
    /**
     * Token list
     */
    private final List<TokenHolder> tokenHolderList;

    /**
     * Create new Structure Modifier
     *
     * @param structure Structure
     */
    public StructureModifier(StructuredTokens structure) {
        this.structure = structure;
        this.tokenHolderList = this.structure.getTokenHolders();
    }

    /**
     * Unify TokenHolders replacing old section with new TokenHolder holding unification and child
     * elements
     *
     * @param id             Id of new TokenHolder
     * @param section        Section to unify
     * @param structure      Structure
     * @param unifyPredicate Predicate that determine if this token will be unified
     */
    public void unify(String id, TokenHolder section, StructuredTokens structure, Predicate<TokenHolder> unifyPredicate) {


        List<TokenHolder> unify = new ArrayList<>();


        List<Runnable> removal = new ArrayList<>();

        for (TokenHolder tokenHolder : section.getChildTokens()) {
            TokenHolder.recursive(tokenHolder, section.getChildTokens(), (list, token) -> {
                if (unifyPredicate.test(token)) {
                    unify.add(token);
                    removal.add(() -> list.remove(token));
                }
            });
        }

        removal.forEach(Runnable::run);


        TokenHolder holder = TokenHolder.ofHolders(id, unify, Collections.singletonList(section));

        // UPDATE
        structure.replace(section, holder);

    }

    /**
     * Unify to head, this unification replaces the head. In this case the head is an related token
     * but not the child token.
     *
     * @param id             Id of new replacement
     * @param head           Head
     * @param structure      Structure
     * @param unifyPredicate Predicate that determine if this token will be unified
     * @param childUnify     Predicate that determine if this token is a child
     */
    public void unifyHead(String id, TokenHolder head, StructuredTokens structure, Predicate<TokenHolder> unifyPredicate, Predicate<TokenHolder> childUnify) {


        List<TokenHolder> unify = new ArrayList<>(Collections.singleton(head));

        List<TokenHolder> childs = new ArrayList<>();

        List<Runnable> removal = new ArrayList<>();

        for (TokenHolder tokenHolder : head.getChildTokens()) {
            TokenHolder.recursive(tokenHolder, head.getChildTokens(), (list, token) -> {
                if (unifyPredicate.test(token)) {
                    unify.add(token);

                    childs.addAll(token.getChildTokens());
                    token.getChildTokens().clear();

                    removal.add(() -> list.remove(token));
                } else if (childUnify.test(token)) {
                    if(!TokenHolder.recursiveChildCheck(childs, token))
                        childs.add(token);
                }
            });
        }


        removal.forEach(Runnable::run);
        removal.clear();

        unify.forEach(u -> u.getChildTokens().forEach(c -> {
            for(TokenHolder h : childs) {
                if(h.creationHashCode() == c.creationHashCode()) {
                    removal.add(() -> childs.remove(c));
                }
            }
        }));



        removal.forEach(Runnable::run);
        removal.clear();

        TokenHolder holder = TokenHolder.ofHolders(id, unify, childs);

        // UPDATE
        structure.replace(head, holder);

    }

    /**
     * Return StructuredTokens
     *
     * @return StructuredTokens
     */
    public StructuredTokens getStructure() {
        return structure;
    }
}
