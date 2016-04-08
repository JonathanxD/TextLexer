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

import com.github.jonathanxd.iutils.annotations.ProcessedBy;
import com.github.jonathanxd.iutils.extra.IMutableContainer;
import com.github.jonathanxd.iutils.extra.ImmutableContainer;
import com.github.jonathanxd.textlexer.lexer.LexerImpl;
import com.github.jonathanxd.textlexer.lexer.token.history.ITokenList;
import com.github.jonathanxd.textlexer.lexer.token.history.TokenListImpl;
import com.github.jonathanxd.textlexer.lexer.token.history.analise.AnaliseTokenList;
import com.github.jonathanxd.textlexer.lexer.token.structure.analise.StructureRule;

/**
 * Created by jonathan on 30/01/16.
 */
public interface IToken<T> {

    /**
     * Converts from Object representation to Text data
     * @param value Object Representation
     * @return True if success, false otherwise
     */
    boolean valueToData(T value);

    /**
     * Converts from Text data to Object representation
     *
     * @return Object representation
     */
    T dataToValue();

    /**
     * Get data of Token
     *
     * @return Data of token
     */
    @Deprecated
    String getData();

    /**
     * Set the data
     *
     * @param data Set data
     */
    void setData(String data);

    /**
     * Get Data as MutableContainer
     *
     * @return Data
     */
    IMutableContainer<String> mutableData();

    /**
     * Get Data as ImmutableContainer
     *
     * @return Data
     */
    ImmutableContainer<String> immutableData();

    /**
     * Get Token simple name
     *
     * @return Token simple name
     */
    String getSimpleName();

    /**
     * Hides the token from {@link ITokenList} list
     *
     * @return True to hide or false otherwise
     */
    @ProcessedBy({ITokenList.class, TokenListImpl.class})
    default boolean hide() {
        return false;
    }

    /**
     * Structure Rule for Token
     *
     * @return StructureRule
     * @deprecated See {@link com.github.jonathanxd.textlexer.ext.parser.Parser}, and {@link
     * com.github.jonathanxd.textlexer.ext.parser.processor.rule.RuleProcessor}
     */
    @Deprecated
    @ProcessedBy({AnaliseTokenList.class, LexerImpl.class})
    default StructureRule getStructureRule() {
        return null;
    }

}
