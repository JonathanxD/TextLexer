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
package com.github.jonathanxd.textlexer.ext.parser.structure;

import com.github.jonathanxd.textlexer.ext.parser.holder.TokenHolder;
import com.github.jonathanxd.textlexer.ext.parser.structure.exceptions.EmptyCheckException;
import com.github.jonathanxd.textlexer.ext.parser.structure.modifier.StructureModifier;
import com.github.jonathanxd.textlexer.lexer.token.IToken;

import java.util.ArrayList;
import java.util.Collections;
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
        TokenHolder holder = TokenHolder.of(null, token);
        tokenHolders.add(holder);
        return holder;
    }

    public void link(TokenHolder tokenHolder, IToken<?> token) {
        tokenHolders.stream().filter(holder -> holder == tokenHolder).forEach(holder -> holder.link(token));
    }

    public StructureModifier createModifier() {
        return new StructureModifier(this);
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

        public TokenHolder getCurrent() {
            return !hasCurrent() ? null : current.getLast();
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

    public void remove(TokenHolder tokenHolder) {

        List<Runnable> removeSched = new ArrayList<>();
        for(TokenHolder headHolder : this.getTokenHolders()) {
            TokenHolder.recursive(headHolder, this.getTokenHolders(), (tokenHolders1, tokenHolder1) -> {
                if(tokenHolder1 == tokenHolder) {
                    removeSched.add(() -> tokenHolders1.remove(tokenHolder1));
                }
            });
        }

        removeSched.forEach(Runnable::run);
    }

    public void replace(TokenHolder tokenHolder, TokenHolder newTokenHolder) {

        List<Runnable> replaceSched = new ArrayList<>();
        for(TokenHolder headHolder : this.getTokenHolders()) {
            TokenHolder.recursive(headHolder, this.getTokenHolders(), (tokenHolders1, tokenHolder1) -> {
                if(tokenHolder1 == tokenHolder) {
                    replaceSched.add(() -> Collections.replaceAll(tokenHolders1, tokenHolder, newTokenHolder));
                }
            });
        }

        replaceSched.forEach(Runnable::run);
    }

    public List<TokenHolder> getTokenHolders() {
        return tokenHolders;
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
