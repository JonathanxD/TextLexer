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
package github.therealbuggy.textlexer.lexer.token.builder;

import github.therealbuggy.textlexer.lexer.token.IToken;
import github.therealbuggy.textlexer.lexer.token.processor.ProcessorData;
import github.therealbuggy.textlexer.lexer.token.type.ITokenType;

/**
 * Created by jonathan on 30/01/16.
 */
public class TokenBuilder {

    private final ITokenType<?> tokenType;

    private StringBuilder dataBuilder = new StringBuilder();
    private boolean build = false;

    public TokenBuilder(ITokenType<?> tokenType) {
        this.tokenType = tokenType;
    }

    public TokenBuilder addToData(char character) {
        buildCheck();
        dataBuilder.append(character);
        return this;
    }

    private void buildCheck() {
        if (build) {
            throw new RuntimeException("Error!");
        }
    }

    public IToken<?> build(ProcessorData data) {
        build = true;
        return tokenType.createToken(ProcessorData.builder()
                .from(data)
                .setData(dataBuilder.toString())
                .build());
    }

    public ITokenType<?> getTokenType() {
        return tokenType;
    }

    public int length() {
        return dataBuilder.length();
    }

    public String getData() {
        return dataBuilder.toString();
    }
}
