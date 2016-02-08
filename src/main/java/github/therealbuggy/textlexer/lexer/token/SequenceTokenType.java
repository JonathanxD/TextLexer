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
package github.therealbuggy.textlexer.lexer.token;

import github.therealbuggy.textlexer.lexer.token.builder.TokenBuilder;
import github.therealbuggy.textlexer.lexer.token.processor.ProcessorData;

/**
 * Created by jonathan on 08/02/16.
 */
public class SequenceTokenType<T> extends UnifiedTokenType<T> {

    @Override
    public T dataToValue() {
        return null;
    }

    @Override
    public boolean matches(char character) {
        return false;
    }

    @Override
    public boolean matches(ProcessorData processorData) {

        TokenBuilder tokenBuilder = processorData.getBuilderList().current();
        return false;
    }
}
