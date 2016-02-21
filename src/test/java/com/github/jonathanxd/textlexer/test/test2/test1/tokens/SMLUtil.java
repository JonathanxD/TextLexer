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
package com.github.jonathanxd.textlexer.test.test2.test1.tokens;

import com.github.jonathanxd.iutils.iterator.IteratorUtil;
import com.github.jonathanxd.textlexer.lexer.token.builder.BuilderList;
import com.github.jonathanxd.textlexer.lexer.token.builder.TokenBuilder;
import com.github.jonathanxd.textlexer.lexer.token.history.ITokenList;
import com.github.jonathanxd.textlexer.lexer.token.processor.ProcessorData;
import com.github.jonathanxd.textlexer.test.test2.test1.QuoteUtil;

import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * Created by jonathan on 20/02/16.
 */
public class SMLUtil {

    private static final long INT_MAX_SIZE = String.valueOf(Integer.MAX_VALUE).length();
    private static final Pattern NUMBER_REGEX = Pattern.compile("-?(?:0|[1-9]\\d*)(?:\\.\\d+)?(?:[eE][+-]?\\d+)?");

    private static final String[] OUT_QUOTE = {
            "true",
            "false",
            "null"
    };

    public static boolean isObject(ProcessorData processorData) {
        char character = processorData.getCharacter();
        BuilderList builderList = processorData.getBuilderList();
        ITokenList tokenList = processorData.getTokenList();
        TokenBuilder tokenBuilder = builderList.current();


        Iterator<Character> characterIterator = processorData.getCharacterIterator();
        int index = processorData.getIndex();

        IteratorUtil.goTo(characterIterator, index);

        String data = tokenBuilder.getData();

        if(!data.trim().isEmpty()) {
            for (String str : OUT_QUOTE) {
                if (data.startsWith(str)) {
                    return true;
                }
            }
        }

        if ((!data.startsWith("\"") && String.valueOf(character).matches("[A-Za-z0-9.]+")) || data.matches("[A-Za-z0-9.]+")) {
            return true;
        }


        if (data.startsWith("\"")) {
            if (QuoteUtil.allOpenAllClose(data)) {
                return false;
            } else {
                return true;
            }
        } else {
            if(character == '"')
                return true;

            if (isInt(characterIterator)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isInt(Iterator<Character> iterator) {
        StringBuilder aData = new StringBuilder();

        while (iterator.hasNext()) {

            Character next = iterator.next();
            aData.append(next);

            if (aData.length() >= INT_MAX_SIZE) {
                break;
            }
        }


        String str = aData.toString();
        if (NUMBER_REGEX.matcher(str).matches()) {
            return true;
        } else {
            for (int x = str.length(); x > -1; --x) {
                if (NUMBER_REGEX.matcher(str).matches()) {
                    return true;
                } else {
                    str = str.substring(0, x);
                }
            }
        }

        return false;
    }
}
