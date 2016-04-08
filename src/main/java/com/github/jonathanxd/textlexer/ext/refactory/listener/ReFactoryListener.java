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
package com.github.jonathanxd.textlexer.ext.refactory.listener;

import com.github.jonathanxd.iutils.annotations.Immutable;
import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.history.ITokenList;
import com.github.jonathanxd.textlexer.lexer.token.history.list.CommonTokenList;

/**
 * Created by jonathan on 09/03/16.
 */
public interface ReFactoryListener {

    /**
     * Factorize a Token.
     *
     * @param token         Token to factorize
     * @param index         Index of token in {@code fromTokenList}
     * @param fromTokenList Source list
     * @param toRefactored  New Token List to be returned
     * @return Return same token to add or null if you will add it manually (adding to {@code
     * toRefactored} list) or delete it.
     */
    IToken<?> factory(IToken<?> token, int index, @Immutable CommonTokenList fromTokenList, ITokenList toRefactored);

    Class<? extends IToken<?>> target();

}
