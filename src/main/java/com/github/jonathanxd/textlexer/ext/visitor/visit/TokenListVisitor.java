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
package com.github.jonathanxd.textlexer.ext.visitor.visit;

import com.github.jonathanxd.iutils.data.DataProvider;
import com.github.jonathanxd.textlexer.lexer.token.history.ITokenList;
import com.github.jonathanxd.textlexer.lexer.token.history.LoopDirection;

/**
 * Created by jonathan on 20/02/16.
 */

/**
 * Simple Visitor
 *
 * Visit Token List
 */
@DataProvider({ITokenList.class})
public class TokenListVisitor extends CommonVisitor<ITokenList> {

    private final ITokenList tokenList;

    public TokenListVisitor(ITokenList tokenList) {
        super();
        this.tokenList = tokenList;
        getDefaultData().registerData(this.tokenList);
    }

    @Override
    public void visit() {
        tokenList.forEach(token -> {
            visit(token);
            exit(token);
        }, LoopDirection.FIRST_TO_LAST);
    }
}
