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
package com.github.jonathanxd.textlexer.ext.parser.processor.action;

import com.github.jonathanxd.textlexer.ext.parser.structure.ParseSection;
import com.github.jonathanxd.textlexer.lexer.token.IToken;

import java.util.List;

/**
 * Created by jonathan on 19/02/16.
 */
@FunctionalInterface
public interface Action {

    /**
     * Do an action with this token
     *
     * @param token     Token
     * @param section   Section of token
     * @param tokenList List of tokens
     * @param index     Index of this token in list
     * @return a State
     * @see Actions#doAll(IToken, ParseSection, List, int)
     * @see com.github.jonathanxd.textlexer.ext.parser.processor.ActionProcessor
     */
    ProcessingState stateAction(IToken<?> token, ParseSection section, List<IToken<?>> tokenList, int index);

}
