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
package com.github.jonathanxd.textlexer.test.test2.test1;

/**
 * Created by jonathan on 20/02/16.
 */
public class QuoteUtil {

    public static boolean allOpenAllClose(String text) {
        return countQuote(text);
    }

    public static boolean countQuote(String text) {

        int singleQuotes = 0;
        int doubleQuotes = 0;

        boolean lastCharDefined = false;
        char lastChar = ' ';

        for(int x = 0; x < text.length(); ++x) {
            char currentChar = text.charAt(x);

            if(!lastCharDefined) {
                lastChar = currentChar;
                lastCharDefined = true;
            }

            if(lastChar != '\\') {
                if(currentChar == '\'') {
                    ++singleQuotes;
                } else if(currentChar == '"') {
                    ++doubleQuotes;
                }
            }
        }

        double finalSingle = singleQuotes / 2;
        double finalDouble = doubleQuotes / 2;

        if(finalSingle != 0) {
            return finalSingle == Math.floor(finalSingle);
        }

        if(finalDouble != 0) {
            return finalDouble == Math.floor(finalDouble);
        }

        return false;
    }

}
