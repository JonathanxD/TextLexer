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
package github.therealbuggy.textlexer;

import java.lang.reflect.Method;

import github.therealbuggy.textlexer.lexer.token.type.ITokenType;

/**
 * Created by jonathan on 07/02/16.
 */
public class Debug {

    private static final boolean DEBUG;

    static {
        if (System.getProperty("DEBUG") != null && System.getProperty("DEBUG").equalsIgnoreCase("true")) {
            DEBUG = true;
        } else {
            DEBUG = false;
        }
    }


    public static void printErr(String err) {
        if(DEBUG)
            System.err.println(err);
    }

    public static String getClassString(Class<?> clazz) {
        return clazz.getCanonicalName()
                +"<>"
                +"("
                +clazz.getSimpleName()
                +".java"
                +":0"
                +")";
    }

    public static String getClassString(Class<?> clazz, Method m) {
        if(m == null)
            return getClassString(clazz);
        return clazz.getCanonicalName()
                + "."
                + m.getName()
                +"("
                +clazz.getSimpleName()
                +".java"
                +":0"
                +")";
    }

    public static Method getMethod(Class<?> clazz, String method, Class<?>[] parameters) {
        try {
            return clazz.getDeclaredMethod(method, parameters);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}
