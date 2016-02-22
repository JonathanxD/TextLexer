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
package com.github.jonathanxd.textlexer.test.calc.ext.parser;

import com.github.jonathanxd.textlexer.ext.parser.holder.TokenHolder;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.DefaultOptions;
import com.github.jonathanxd.textlexer.ext.parser.structure.ParseSection;
import com.github.jonathanxd.textlexer.ext.parser.structure.StructuredTokens;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.options.StructureOptions;
import com.github.jonathanxd.textlexer.ext.parser.processor.standard.inverse.InverseProcessor;
import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.history.TokenListUtil;
import com.github.jonathanxd.textlexer.test.calc.tokens.Garbage;
import com.github.jonathanxd.textlexer.test.calc.tokens.GroupClose;
import com.github.jonathanxd.textlexer.test.calc.tokens.GroupOpen;
import com.github.jonathanxd.textlexer.test.calc.tokens.operators.Operator;

import java.util.function.Predicate;

/**
 * Created by jonathan on 17/02/16.
 */
public class CalcProcessor extends InverseProcessor {

    @Override
    public StructureOptions optionsOf(IToken<?> token, ParseSection section) {
        if (token instanceof Operator) {
            return new StructureOptions().set(DefaultOptions.Common.HOST, true);/*.and(DefaultOptions.InverseProc.INNER, true);*/
        } else if (token instanceof GroupOpen || token instanceof GroupClose) {

            return new StructureOptions().set(DefaultOptions.Common.STACK, true)
                    .and(DefaultOptions.Common.EXIT, true);
        } else if (token instanceof Garbage) {
            return new StructureOptions().set(DefaultOptions.Common.IGNORE, true);
        } else {
            return new StructureOptions().set(DefaultOptions.InverseProc.ELEMENT, true)
                    .and(DefaultOptions.Common.EXIT, true);
        }
    }

    @Override
    public void processFinish(StructuredTokens structure) {

        for (TokenHolder holder : structure.getTokenHolders()) {
            TokenHolder.recursiveLoop(holder, structure, (tokenHolder, iTokenList, aStructure, type) -> {

                if(TokenListUtil.findTokenInList(GroupOpen.class, iTokenList)) {
                    structure.createModifier().unify("Group", tokenHolder, aStructure,
                            new GroupPredicate());
                }
            });
        }
    }


    private static final class GroupPredicate implements Predicate<TokenHolder> {

        @Override
        public boolean test(TokenHolder tokenHolder) {
            return TokenListUtil.findTokenInList(GroupOpen.class, tokenHolder.getTokens()) || TokenListUtil.findTokenInList(GroupClose.class, tokenHolder.getTokens());
        }
    }
}
