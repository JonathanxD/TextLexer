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
package com.github.jonathanxd.textlexer.test.test2.test1.visit.listener;

import com.github.jonathanxd.textlexer.ext.parser.holder.TokenHolder;
import com.github.jonathanxd.textlexer.ext.visitor.listener.ListenerFor;
import com.github.jonathanxd.textlexer.ext.visitor.listener.VisitPhase;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.KeyToken;

import java.util.List;

/**
 * Created by jonathan on 20/02/16.
 */
public class TokenVisitor {

    @ListenerFor({KeyToken.class})
    public void visit(TokenHolder holder) {
        System.out.println("Visit: "+holder);
    }

    @ListenerFor(value = {KeyToken.class}, phase = VisitPhase.EXIT)
    public void exit(TokenHolder holder) {
        System.out.println("Exit: "+holder);
    }

    @ListenerFor(phase = VisitPhase.VISIT_END)
    public void end(List<TokenHolder> end) {
        System.out.println("Visit END ]-> "+end);
    }

}
