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

import github.therealbuggy.textlexer.lexer.token.type.ITokenType;

/**
 * Created by jonathan on 06/02/16.
 */
public class BuilderProcessData {
    private final TokenBuilder builder;
    private final ITokenType<?> type;

    public BuilderProcessData(TokenBuilder builder, ITokenType<?> type) {
        this.builder = builder;
        this.type = type;
    }

    public TokenBuilder getBuilder() {
        return builder;
    }

    public ITokenType<?> getType() {
        return type;
    }

    @Override
    public String toString() {
        return "BPD[Type: " + getType() + ", Builder: " + builder + "]";
    }
}
