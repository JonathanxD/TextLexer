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

import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.UnifiedTokenFactory;

import java.util.List;

/**
 * Created by jonathan on 20/02/16.
 */
public class Garbage extends UnifiedTokenFactory<String> {

    public static void garbage(IToken<?> token, List<IToken<?>> tokenList, int index) {
        String startSpaces = startsSpaces(token.getData());
        String endSpaces = endSpaces(token.getData());

        if(token.getData().trim().isEmpty()) {
            setGarbage(token, tokenList, index, 0, token.getData(), true, true);
        }

        if(startSpaces != null) {
            setGarbage(token, tokenList, index, -1, startSpaces, false, false);
            token.setData(token.getData().substring(startSpaces.length()));
        }

        if(endSpaces != null) {
            setGarbage(token, tokenList, index, 1, endSpaces, false, false);
            int pos = token.getData().length() - endSpaces.length();

            token.setData(token.getData().substring(0, pos));
        }
    }

    public static void setGarbage(IToken<?> current, List<IToken<?>> tokenList, int index, int offset, String data, boolean remove, boolean set) {
        if(index - 1 > -1) {
            IToken<?> token = tokenList.get(index-1);
            if(token instanceof Garbage) {
                garbageAppend(tokenList, token, index, data, remove);
                return;
            }
        }

        int checkedIndex = (index + offset > -1 ? index + offset : 0);

        {
            IToken<?> token = tokenList.get(checkedIndex);

            if(token == current && checkedIndex - 1 > -1) {
                token = tokenList.get(checkedIndex -1);
            }

            if(token instanceof Garbage) {
                garbageAppend(tokenList, token, checkedIndex, data, remove);
                return;
            }
        }

        if(index + 1 < tokenList.size()) {
            IToken<?> token = tokenList.get(index+1);
            if(token instanceof Garbage) {
                garbageAppend(tokenList, token, index, data, remove);
                return;
            }
        }
        if(set) {
            tokenList.set(index + offset, new Garbage().createToken(data));
        }else{

            tokenList.add(checkedIndex, new Garbage().createToken(data));
        }

    }

    private static void garbageAppend(List<IToken<?>> tokenList, IToken<?> appendTo, int index, String data, boolean remove) {
        if(appendTo instanceof Garbage) {
            appendTo.setData(appendTo.getData()+data);
            if(remove)
                tokenList.remove(index);
        }
    }

    public static String startsSpaces(String data) {

        if (data.startsWith(" ")) {
            int x = 0;
            do {
                if (x < data.length() && data.charAt(x) == ' ')
                    ++x;
                else
                    break;
            } while (true);

            return data.substring(0, x);
        }

        return null;
    }

    public static String endSpaces(String data) {

        if (data.endsWith(" ")) {
            int x = data.length()-1;
            do {
                if (x > -1 && data.charAt(x) == ' ')
                    --x;
                else {
                    ++x;
                    break;
                }
            } while (true);

            return data.substring(x, data.length());
        }

        return null;
    }

    @Override
    public String dataToValue() {
        return getData();
    }

    @Override
    public boolean matches(char character) {
        return character == ' ';
    }

    @Override
    public int order() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean hide() {
        return true;
    }
}
