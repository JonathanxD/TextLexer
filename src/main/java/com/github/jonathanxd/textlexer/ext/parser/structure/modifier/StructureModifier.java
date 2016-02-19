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
import com.github.jonathanxd.textlexer.ext.parser.structure.ParseStructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by jonathan on 18/02/16.
 */
public class StructureModifier {

    private final ParseStructure structure;
    private final List<TokenHolder> tokenHolderList;

    public StructureModifier(ParseStructure structure) {
        this.structure = structure;
        this.tokenHolderList = this.structure.getTokenHolders();
    }

    /*public TokenNavigate navigate() {

    }*/


    public void unify(String id, TokenHolder section, ParseStructure structure, Predicate<TokenHolder> unifyPredicate, Predicate<TokenHolder> childUnify) {


        List<TokenHolder> unify = new ArrayList<>();

        // TODO REMOVE CHILD DETECTION! Update 19/02/2016 00:20 (GMT-2) fixed it
        List<TokenHolder> childs = new ArrayList<>();

        List<Runnable> removal = new ArrayList<>();

        for (TokenHolder tokenHolder : section.getChildTokens()) {
            TokenHolder.recursive(tokenHolder, section.getChildTokens(), (list, token) -> {
                if (unifyPredicate.test(token)) {
                    unify.add(token);
                    removal.add(() -> list.remove(token));
                } else if (childUnify.test(token)) {
                    childs.add(token);
                }
            });
        }

        removal.forEach(Runnable::run);


        TokenHolder holder = TokenHolder.ofHolders(id, unify, Collections.singletonList(section));

        // UPDATE
        structure.replace(section, holder);

    }

    public ParseStructure getStructure() {
        return structure;
    }
}
