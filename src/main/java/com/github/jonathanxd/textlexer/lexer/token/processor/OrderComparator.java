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
package com.github.jonathanxd.textlexer.lexer.token.processor;

import com.github.jonathanxd.textlexer.lexer.token.factory.ITokenFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;

/**
 * Created by jonathan on 08/02/16.
 */
public class OrderComparator implements Comparator<ITokenFactory<?>>, Serializable {
    @Override
    public int compare(ITokenFactory<?> o1, ITokenFactory<?> o2) {
        Collection<Class<? extends ITokenFactory>> tokenClasses = null;
        Class<? extends ITokenFactory> otherCheck = null;
        int aReturn = 0;

        if (o1.orderAfterClasses() != null) {
            tokenClasses = o1.orderAfterClasses();
            otherCheck = o2.getClass();
            aReturn = 1;
        } else if (o2.orderAfterClasses() != null) {
            tokenClasses = o2.orderAfterClasses();
            otherCheck = o1.getClass();
            aReturn = -1;
        }

        if (tokenClasses != null
                && otherCheck != null
                && tokenClasses.contains(otherCheck)) {
            return aReturn;
        }

        int i = Integer.compare(o1.order(), o2.order());
        if (i == 0) {
            return 1;
        }
        return i;
    }
}
