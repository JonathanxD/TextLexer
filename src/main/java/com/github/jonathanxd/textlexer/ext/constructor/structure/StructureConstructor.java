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
package com.github.jonathanxd.textlexer.ext.constructor.structure;

import com.github.jonathanxd.iutils.map.JwMap;
import com.github.jonathanxd.textlexer.ext.common.TokenElementType;
import com.github.jonathanxd.textlexer.ext.parser.holder.TokenHolder;
import com.github.jonathanxd.textlexer.ext.parser.structure.StructuredTokens;
import com.github.jonathanxd.textlexer.lexer.token.IToken;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 21/02/16.
 */
public class StructureConstructor {

    final PositionFactory positionFactory;
    private final StructuredTokens structuredTokens;

    public StructureConstructor(StructuredTokens structuredTokens, PositionFactory positionFactory) {
        this.structuredTokens = structuredTokens;
        this.positionFactory = positionFactory;
    }

    public byte[] construct() {

        StringBuilder sb = new StringBuilder();
        recursive(structuredTokens.getTokenHolders(), sb);

        try {
            return sb.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return sb.toString().getBytes();
        }
    }

    private void recursive(List<TokenHolder> tokenHolderList, StringBuilder builder) {

        for (TokenHolder tokenHolder : tokenHolderList) {

            List<Runnable> runnable = new ArrayList<>();


            List<IToken<?>> tokens = tokenHolder.getTokens();

            JwMap<IToken<?>, Position> positions = positionFactory.getPositionOfToken(tokens, TokenElementType.HEAD);

            positions.forEach((token, position) -> {
                if (position == Position.START) {
                    builder.append(token.mutableData().get());
                } else {
                    runnable.add(() -> builder.append(token.mutableData().get()));
                }
            });


            recursive(tokenHolder.getChildTokens(), builder);

            runnable.forEach(Runnable::run);
        }
    }


}
