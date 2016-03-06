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
package com.github.jonathanxd.textlexer.scanner;

import java.util.Arrays;

/**
 * Created by jonathan on 30/01/16.
 */
public class CharScanner implements IScanner {

    private final char[] chars;
    private int currentIndex = -1;

    public CharScanner(char[] chars) {
        this.chars = chars;
    }

    @Override
    public char nextChar() {
        if (!hasNextChar()) {
            throw new ArrayIndexOutOfBoundsException(String.format("Size: %d. Index: %d", currentIndex, chars.length));
        }
        return chars[++currentIndex];
    }

    @Override
    public char[] getChars() {
        return Arrays.copyOf(chars, chars.length);
    }

    @Override
    public boolean hasNextChar() {
        return currentIndex + 1 < chars.length;
    }

    @Override
    public int getCurrentIndex() {
        return currentIndex;
    }

    @Override
    public CharScanner clone() {
        CharScanner cs = new CharScanner(this.chars.clone());
        cs.currentIndex = this.currentIndex;

        return cs;
    }
}
