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
package github.therealbuggy.textlexer.lexer.token.history;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import github.therealbuggy.textlexer.lexer.token.IToken;

/**
 * Created by jonathan on 07/02/16.
 */
public class TokenListUtil {


    public static Iterator<IToken<?>> toIterator(ITokenList tokenList) {
        List<IToken<?>> iTokenList = new ArrayList<>();
        tokenList.forEach(iTokenList::add);
        return iTokenList.iterator();
    }


    @SafeVarargs
    public static ITokenList ignore(ITokenList tokenList, Class<? extends IToken<?>>... tokensClasses) {
        List<Class<?>> tokenClassList = Arrays.asList(tokensClasses);
        return retain(tokenList, token -> !tokenClassList.contains(token.getClass()));

    }

    public static ITokenList ignore(ITokenList tokenList, Predicate<IToken<?>> tokenIgnorePredicate) {
        return retain(tokenList, token -> !tokenIgnorePredicate.test(token));

    }

    @SafeVarargs
    public static ITokenList retain(ITokenList tokenList, Class<? extends IToken<?>>... tokensClasses) {
        List<Class<?>> tokenClassList = Arrays.asList(tokensClasses);
        return retain(tokenList, token -> tokenClassList.contains(token.getClass()));

    }

    public static ITokenList retain(ITokenList tokenList, Predicate<IToken<?>> retainPredicate) {
        ITokenList filteredTokenList = new TokenListImpl();

        filteredTokenList.forEach(itoken -> {
            if (!retainPredicate.test(itoken)) {
                filteredTokenList.add(itoken);
            }
        });
        return filteredTokenList;
    }
}
