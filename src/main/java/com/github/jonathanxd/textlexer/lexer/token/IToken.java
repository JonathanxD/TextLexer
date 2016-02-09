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
package com.github.jonathanxd.textlexer.lexer.token;

import com.github.jonathanxd.iutils.annotation.ProcessedBy;
import com.github.jonathanxd.textlexer.lexer.LexerImpl;
import com.github.jonathanxd.textlexer.lexer.token.history.ITokenList;
import com.github.jonathanxd.textlexer.lexer.token.history.TokenListImpl;
import com.github.jonathanxd.textlexer.lexer.token.history.analise.AnaliseTokenList;
import com.github.jonathanxd.textlexer.lexer.token.structure.analise.StructureRule;

/**
 * Created by jonathan on 30/01/16.
 */
public interface IToken<T> {

    T dataToValue();

    String getData();

    void setData(String data);

    String getSimpleName();

    @ProcessedBy({ITokenList.class, TokenListImpl.class})
    default boolean hide() {
        return false;
    }

    @ProcessedBy({AnaliseTokenList.class, LexerImpl.class})
    default StructureRule getStructureRule() {
        return null;
    }

}
