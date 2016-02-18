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
