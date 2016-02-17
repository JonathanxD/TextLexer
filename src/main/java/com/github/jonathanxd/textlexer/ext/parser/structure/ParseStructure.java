package com.github.jonathanxd.textlexer.ext.parser.structure;

import com.github.jonathanxd.textlexer.ext.parser.holder.TokenHolder;
import com.github.jonathanxd.textlexer.ext.parser.structure.exceptions.EmptyCheckException;
import com.github.jonathanxd.textlexer.lexer.token.IToken;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by jonathan on 17/02/16.
 */
public class ParseStructure {

    List<TokenHolder> tokenHolders = new ArrayList<>();


    public TokenHolder addToken(IToken<?> token) {
        TokenHolder holder = TokenHolder.of(token);
        tokenHolders.add(holder);
        return holder;
    }

    public void link(TokenHolder tokenHolder, IToken<?> token) {
        tokenHolders.stream().filter(holder -> holder == tokenHolder).forEach(holder -> holder.link(token));
    }

    public ParseSection createSection() {
        return new ParseSection();
    }

    public class ParseSection {
        Deque<TokenHolder> current = new LinkedList<>();

        public TokenHolder link(IToken<?> token) {
            currentHolderCheck("No current TokenHolder to link, you should use link(TokenHolder, IToken) instead!");
            TokenHolder newHolder = current.getLast().link(token);
            keepEnter(newHolder);
            return newHolder;
        }

        private void currentHolderCheck(String message) {
            if(current.isEmpty()) {
                throw new EmptyCheckException(message, new NullPointerException());
            }
        }

        /**
         * Cleanup current process and enter in a TokenHolder
         * @param tokenHolder
         */
        public void enter(TokenHolder tokenHolder) {
            enter(tokenHolder, true);
        }

        public void softEnter(TokenHolder tokenHolder) {
            keepEnter(tokenHolder);
        }

        private void keepEnter(TokenHolder tokenHolder) {
            enter(tokenHolder, false);
        }

        private void enter(TokenHolder tokenHolder, boolean cleanup) {
            if(cleanup) current.clear();

            current.addLast(tokenHolder);
        }


        public boolean hasCurrent() {
            return !current.isEmpty();
        }

        public boolean canExit() {
            return hasCurrent();
        }

        /**
         * Exit current TokenHolder and return that.
         * @return TokenHolder removed
         */
        public TokenHolder exit() {
            currentHolderCheck("Cannot exit, no more TokenHolders!");
            return current.pollLast();
        }

    }

    @Override
    public String toString() {

        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");

        for(TokenHolder holder : tokenHolders) {
            stringJoiner.add(holder.toString());
        }

        return this.getClass().getSimpleName()+":( "+stringJoiner.toString()+" )";
    }
}
