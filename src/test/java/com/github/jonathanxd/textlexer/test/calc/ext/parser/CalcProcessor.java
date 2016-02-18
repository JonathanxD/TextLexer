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

import com.github.jonathanxd.iutils.annotations.Immutable;
import com.github.jonathanxd.textlexer.ext.parser.Processor;
import com.github.jonathanxd.textlexer.ext.parser.holder.TokenHolder;
import com.github.jonathanxd.textlexer.ext.parser.processor.ParserProcessor;
import com.github.jonathanxd.textlexer.ext.parser.structure.Options;
import com.github.jonathanxd.textlexer.ext.parser.structure.ParseStructure;
import com.github.jonathanxd.textlexer.ext.parser.structure.StructureOptions;
import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.history.TokenListUtil;
import com.github.jonathanxd.textlexer.test.calc.tokens.Garbage;
import com.github.jonathanxd.textlexer.test.calc.tokens.GroupClose;
import com.github.jonathanxd.textlexer.test.calc.tokens.GroupOpen;
import com.github.jonathanxd.textlexer.test.calc.tokens.operators.Operator;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by jonathan on 17/02/16.
 */
public class CalcProcessor implements ParserProcessor {

    @Override
    public boolean isProcessor() {
        return false;
    }

    @Override
    public StructureOptions tokenOptions(IToken<?> token) {
        if (token instanceof Operator) {
            return new StructureOptions().set(Options.HEAD, true).and(Options.INNER, true);
        } else if (token instanceof GroupOpen || token instanceof GroupClose) {

            return new StructureOptions().set(Options.STACK, true)
                    .and(Options.EXIT, true);
        } else if (token instanceof Garbage) {
            return new StructureOptions().set(Options.IGNORE, true);
        } else {
            return new StructureOptions().set(Options.ELEMENT, true)
                    .and(Options.EXIT, true);
        }
    }

    @Override
    public void processFinish(ParseStructure structure) {
        for (TokenHolder holder : structure.getTokenHolders()) {
            TokenHolder.recursiveLoop(holder, (tokenHolder, iTokenList) -> {
                if(TokenListUtil.findTokenInList(GroupOpen.class, iTokenList)) {
                    structure.createModifier().unify("Group", tokenHolder,
                            new GroupPredicate(),
                            new GroupPredicate().negate());
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
