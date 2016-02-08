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
package github.therealbuggy.textlexer.lexer.token.processor;

import github.therealbuggy.textlexer.lexer.token.builder.BuilderList;
import github.therealbuggy.textlexer.lexer.token.history.ITokenList;
import io.github.jonathanxd.iutils.iterator.SafeBackableIterator;

/**
 * Created by jonathan on 07/02/16.
 */
public class ProcessorData {
    private final ITokenList tokenList;
    private final BuilderList builderList;
    private final Character character;
    private final String data;
    private final int index;
    private final SafeBackableIterator<Character> characterIterator;

    public ProcessorData(ITokenList tokenList, BuilderList builderList, Character character, String data, int index, SafeBackableIterator<Character> characterIterator) {
        this.tokenList = tokenList;
        this.builderList = builderList;
        this.character = character;
        this.data = data;
        this.index = index;
        this.characterIterator = characterIterator;
    }

    public ProcessorData(ITokenList tokenList, BuilderList builderList, char character, SafeBackableIterator<Character> characterIterator, int index) {
        this(tokenList, builderList, character, null, index, characterIterator);
    }

    public ProcessorData(ITokenList tokenList, BuilderList builderList, String data, SafeBackableIterator<Character> characterIterator, int index) {
        this(tokenList, builderList, null, data, index, characterIterator);
    }

    public ITokenList getTokenList() {
        return tokenList;
    }

    public BuilderList getBuilderList() {
        return builderList;
    }

    public Character getCharacter() {
        return character;
    }

    public String getData() {
        return data;
    }

    public SafeBackableIterator<Character> getCharacterIterator() {
        return characterIterator;
    }

    public int getIndex() {
        return index;
    }

    public boolean charPresent() {
        return character != null;
    }

    public boolean dataPresent() {
        return data != null;
    }

    public boolean builderPresent() {
        return builderList != null;
    }

    public boolean tokenListPresent() {
        return builderList != null;
    }

    public boolean characterIteratorPresent() {
        return characterIterator != null;
    }

    public static ProcessorDataBuilder builder() {
        return new ProcessorDataBuilder();
    }


    public static class ProcessorDataBuilder {
        private String data;
        private ITokenList tokenList;
        private BuilderList builderList;
        private Character character;
        private int index;
        private SafeBackableIterator<Character> characterIterator;

        private ProcessorDataBuilder() {
        }

        public ProcessorDataBuilder setData(String data) {
            this.data = data;
            return this;
        }

        public ProcessorDataBuilder setTokenList(ITokenList tokenList) {
            this.tokenList = tokenList;
            return this;
        }

        public ProcessorDataBuilder setBuilderList(BuilderList builderList) {
            this.builderList = builderList;
            return this;
        }

        public ProcessorDataBuilder setCharacter(Character character) {
            this.character = character;
            return this;
        }

        public ProcessorDataBuilder setCharacterIterator(SafeBackableIterator<Character> characterIterator) {
            this.characterIterator = characterIterator;
            return this;
        }

        public ProcessorDataBuilder setIndex(int index) {
            this.index = index;
            return this;
        }

        public ProcessorData build() {
            ProcessorData processorData = new ProcessorData(tokenList, builderList, character, data, index, characterIterator);
            return processorData;
        }

        public ProcessorDataBuilder from(ProcessorData data) {
            return new ProcessorDataBuilder()
                    .setCharacter(data.getCharacter())
                    .setBuilderList(data.getBuilderList())
                    .setData(data.getData())
                    .setTokenList(data.getTokenList())
                    .setCharacterIterator(data.getCharacterIterator())
                    .setIndex(data.getIndex());
        }
    }
}
