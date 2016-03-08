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
        this.chars = chars.clone();
    }

    @Override
    public char nextChar() {
        if (!hasNextChar()) {
            throw new ArrayIndexOutOfBoundsException(String.format("Index: %d. Size: %d. Next: %d", currentIndex, chars.length, currentIndex + 1));
        }
        return chars[++currentIndex];
    }

    @Override
    public char previousChar() {
        if (!hasNextChar()) {
            throw new ArrayIndexOutOfBoundsException(String.format("Index: %d. Size: %d. Next: %d", currentIndex, chars.length, currentIndex + 1));
        }

        return chars[--currentIndex];
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
    public boolean hasPreviousChar() {
        return currentIndex - 1 > -1;
    }

    @Override
    public int getCurrentIndex() {
        return currentIndex;
    }

    @Override
    public void walkTo(int i) {
        currentIndex = i;
    }

    @Override
    public CharScanner clone() {

        CharScanner cs = new CharScanner(this.chars.clone());
        cs.currentIndex = this.currentIndex;

        return cs;
    }
}
