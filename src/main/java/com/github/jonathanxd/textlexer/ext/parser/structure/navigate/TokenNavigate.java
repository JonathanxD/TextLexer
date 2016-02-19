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
package com.github.jonathanxd.textlexer.ext.parser.structure.navigate;

import com.github.jonathanxd.textlexer.ext.parser.holder.TokenHolder;

import java.util.Iterator;
import java.util.List;

/**
 * Created by jonathan on 18/02/16.
 */
public class TokenNavigate {

    private final List<TokenHolder> list;
    private TokenHolder current;
    private int index = -1;

    public TokenNavigate(List<TokenHolder> list) {
        this.list = list;
    }

    //public List<>

    public TokenHolder enter() {
        current = list.get(++index);
        return current;
    }

    public static class TokenNavigating {

        private final TokenNavigating last;
        private final Iterator<TokenHolder> tokenIterator;
        private final TokenHolder holder;
        private final TokenNavigate navigate;

        TokenNavigating(TokenNavigating last, Iterator<TokenHolder> tokenIterator, TokenHolder holder, TokenNavigate navigate) {
            this.last = last;
            this.tokenIterator = tokenIterator;
            this.holder = holder;
            this.navigate = navigate;
        }

        public TokenHolder getMain() {
            return holder;
        }

        public TokenNavigating enter() {

            /*TokenHolder holder

            return new TokenNavigating(, )*/
            return null;
        }

        public void remove() {
            //tokenHolders.remove(holder);
        }

        public TokenNavigate exit() {
            return navigate;
        }
    }

}
