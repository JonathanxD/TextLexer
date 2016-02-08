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
package github.therealbuggy.textparser.lexer.token.processor;

import github.therealbuggy.textparser.lexer.token.builder.BuilderList;
import github.therealbuggy.textparser.lexer.token.history.ITokenList;

/**
 * Created by jonathan on 07/02/16.
 */
public class ProcessorData {
    private final ITokenList tokenList;
    private final BuilderList builderList;
    private final Character character;
    private final String data;

    public ProcessorData(ITokenList tokenList, BuilderList builderList, Character character, String data) {
        this.tokenList = tokenList;
        this.builderList = builderList;
        this.character = character;
        this.data = data;
    }

    public ProcessorData(ITokenList tokenList, BuilderList builderList, char character) {
        this(tokenList, builderList, character, null);
    }

    public ProcessorData(ITokenList tokenList, BuilderList builderList, String data) {
        this(tokenList, builderList, null, data);
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

    public static ProcessorDataBuilder builder() {
        return new ProcessorDataBuilder();
    }


    public static class ProcessorDataBuilder {
        private String data;
        private ITokenList tokenList;
        private BuilderList builderList;
        private Character character;

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

        public ProcessorData build() {
            ProcessorData processorData = new ProcessorData(tokenList, builderList, character, data);
            return processorData;
        }

        public ProcessorDataBuilder from(ProcessorData data) {
            return new ProcessorDataBuilder()
                    .setCharacter(data.getCharacter())
                    .setBuilderList(data.getBuilderList())
                    .setData(data.getData())
                    .setTokenList(data.getTokenList());
        }
    }
}
