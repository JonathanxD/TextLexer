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
package com.github.jonathanxd.textlexer.lexer.token.builder;

import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.processor.ProcessorData;
import com.github.jonathanxd.textlexer.lexer.token.factory.ITokenFactory;

/**
 * Created by jonathan on 30/01/16.
 */
public class TokenBuilder implements Cloneable {

    private final ITokenFactory<?> tokenType;

    private final StringBuilder dataBuilder = new StringBuilder();
    private boolean build = false;

    public TokenBuilder(ITokenFactory<?> tokenType) {
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

    public ITokenFactory<?> getTokenFactory() {
        return tokenType;
    }

    public int length() {
        return dataBuilder.length();
    }

    public String getData() {
        return dataBuilder.toString();
    }

    /**
     * Is not recommended to use this method because it force to create a new String from StringBuilder.
     * @return String representation
     */
    @Override
    public String toString() {
        return "TokenBuilder[TokenType["+tokenType.getClass().getSimpleName()+"], Data["+dataBuilder.toString()+"]]";
    }

    @Override
    protected TokenBuilder clone() {
        TokenBuilder tokenBuilder = new TokenBuilder(this.tokenType);
        tokenBuilder.dataBuilder.append(this.dataBuilder.toString());
        tokenBuilder.build = this.build;
        return tokenBuilder;
    }
}
