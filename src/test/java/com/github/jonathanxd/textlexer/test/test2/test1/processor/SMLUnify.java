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
package com.github.jonathanxd.textlexer.test.test2.test1.processor;

import com.github.jonathanxd.textlexer.ext.parser.holder.TokenHolder;
import com.github.jonathanxd.textlexer.ext.parser.structure.StructuredTokens;
import com.github.jonathanxd.textlexer.lexer.token.history.TokenListUtil;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.host.MapClose;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.host.MapOpen;

import java.util.function.Predicate;

/**
 * Created by jonathan on 20/02/16.
 */
public class SMLUnify {
    public static void unify(StructuredTokens structure) {
        for (TokenHolder holder : structure.getTokenHolders()) {
            TokenHolder.recursiveLoop(holder, structure, (tokenHolder, iTokenList, aStructure, type) -> {
                if (TokenListUtil.findTokenInList(MapOpen.class, iTokenList) )
                {
                    structure.createModifier().unifyHead("Map", tokenHolder, aStructure,
                            new ArrayPredicate(),
                            new ArrayPredicate().negate());
                }
            });

        }
    }

    private static final class ArrayPredicate implements Predicate<TokenHolder> {

        @Override
        public boolean test(TokenHolder tokenHolder) {
            /** Probably ArrayOpen is not needed here because is a HeadUnify */
            return /*TokenListUtil.findTokenInList(ArrayOpen.class, tokenHolder.getTokens()) || */TokenListUtil.findTokenInList(MapClose.class, tokenHolder.getTokens());
        }
    }


}
