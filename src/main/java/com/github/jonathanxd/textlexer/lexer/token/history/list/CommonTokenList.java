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
package com.github.jonathanxd.textlexer.lexer.token.history.list;

import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.history.ITokenList;
import com.github.jonathanxd.textlexer.lexer.token.history.TokenListImpl;
import com.github.jonathanxd.textlexer.lexer.token.history.TokenListUtil;

import java.util.Collection;
import java.util.List;

/**
 * Created by jonathan on 20/02/16.
 */
public interface CommonTokenList extends List<IToken<?>> {

    static CommonTokenList immutable(Collection<? extends IToken<?>> c) {
        return new ImmutableCommonTokenList(c);
    }

    static CommonTokenList mutable() {
        return new MutableCommonTokenList();
    }

    static CommonTokenList mutable(Collection<? extends IToken<?>> c) {
        return new MutableCommonTokenList(c);
    }

    default int lastVisibleIndex(int index) {

        return TokenListUtil.lastVisibleTokenIndex(index, this);
    }

    default IToken<?> lastVisibleToken(int index) {

        return TokenListUtil.lastVisibleToken(index, this);
    }

    default int nextVisibleIndex(int index) {

        return TokenListUtil.nextVisibleTokenIndex(index, this);
    }

    default IToken<?> nextVisibleToken(int index) {

        return TokenListUtil.nextVisibleToken(index, this);
    }

    default ITokenList toTokenList() {
        ITokenList tokenList = new TokenListImpl();
        this.forEach(tokenList::add);
        return tokenList;
    }
}
