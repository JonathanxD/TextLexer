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

import java.util.List;

import github.therealbuggy.textlexer.lexer.token.builder.TokenBuilder;
import github.therealbuggy.textlexer.lexer.token.processor.ProcessorData;
import io.github.jonathanxd.iutils.iterator.Navigator;
import io.github.jonathanxd.iutils.iterator.SafeBackableIterator;

/**
 * Created by jonathan on 08/02/16.
 */
public abstract class SequenceTokenType<T> extends UnifiedTokenType<T> {

    private int processNext = -1;

    @Override
    public boolean matches(char character) {
        throw new UnsupportedOperationException();
    }

    public abstract boolean matches(String sequence);

    public abstract int[] matchSizes();

    @Override
    public boolean matches(ProcessorData processorData) {
        SafeBackableIterator<Character> iterator = processorData.getCharacterIterator();

        Navigator<Character> navigator = iterator.safeNavigate();


        if (processorData.getBuilderList().hasCurrent()) {

            TokenBuilder tokenBuilder = processorData.getBuilderList().current();

            if (processNext > -1
                    && tokenBuilder.getTokenType() == this) {
                --processNext;
                if (processNext == 0) {
                    processNext = -1;
                }
                return true;
            }
        }

        for (int x = 0; x < matchSizes().length; ++x) {

            navigator.navigateTo(processorData.getIndex());

            int matchSize = matchSizes()[x];

            List<Character> characters = navigator.collect(matchSize);
            StringBuilder builder = new StringBuilder();
            characters.forEach(builder::append);

            if (matches(builder.toString())) {
                processNext = builder.length() - 1;
                return true;
            }
        }
        return false;
    }
}
