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
package com.github.jonathanxd.textlexer.ext.visitor.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

public class Reflection {

    /**
     * Get all Methods annotated with specified annotation
     * @param object Object to find methods
     * @param annotation Annotation
     * @return Collection of annotated methods
     */
    public static Collection<Method> annotatedMethods(Object object, Class<? extends Annotation> annotation) {
        Collection<Method> methods = new ArrayList<>();

        for (Method m : object.getClass().getDeclaredMethods()) {
            if (m.getDeclaredAnnotation(annotation) != null) {
                methods.add(m);
            }
        }

        return methods;
    }

}
