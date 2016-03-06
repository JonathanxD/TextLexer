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

import com.github.jonathanxd.textlexer.lexer.token.type.ITokenType;

import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Created by jonathan on 06/02/16.
 */
public class BuilderList {
    private final Deque<BuilderProcessData> tokenBuilders = new LinkedList<>();

    public BuilderList add(TokenBuilder builder, ITokenType<?> tokenType) {
        tokenBuilders.addLast(new BuilderProcessData(builder, tokenType));
        return this;
    }

    public TokenBuilder current() {
        return currentData().getBuilder();
    }

    private BuilderProcessData currentData() {
        return tokenBuilders.getLast();
    }

    public TokenBuilder endCurrent() {
        BuilderProcessData data = tokenBuilders.pollLast();
        return data.getBuilder();
    }

    public boolean hasCurrent() {
        return tokenBuilders.size() > 0;
    }

    public boolean isCurrent(ITokenType<?> type) {
        return type != null && currentData().getType().equals(type);
    }

    public boolean isCurrent(Class<? extends ITokenType<?>> typeClass) {
        return typeClass != null && currentData().getType().getClass().equals(typeClass);
    }

    public void endAll() {
        while (hasCurrent())
            endCurrent();
    }

    public Deque<BuilderProcessData> getTokenBuilders() {
        return new LinkedList<>(tokenBuilders);
    }

    @Override
    public String toString() {
        return tokenBuilders.toString();
    }

    public void applyTo(BuilderList builderList) {
        builderList.tokenBuilders.clear();
        builderList.tokenBuilders.addAll(this.tokenBuilders.stream().map(BuilderProcessData::clone).collect(Collectors.toList()));
    }
}
