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
package github.therealbuggy.textparser.lexer.token.history.analise;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import github.therealbuggy.textparser.lexer.token.IToken;
import github.therealbuggy.textparser.lexer.token.history.ITokenList;
import github.therealbuggy.textparser.lexer.token.history.analise.exception.AnaliseException;
import github.therealbuggy.textparser.lexer.token.structure.analise.StructureRule;

/**
 * Created by jonathan on 07/02/16.
 */
public class AnaliseTokenList {


    public static Optional<IToken<?>> oneSideFind(ITokenList tokenList, Class<? extends IToken> tokenClass) {

        return oneSideFind(tokenList, 0, tokenClass);
    }

    public static Optional<IToken<?>> oneSideFind(ITokenList tokenList, int pos, Class<? extends IToken> tokenClass) {

        for (int x = pos; x < tokenList.size(); ++x) {

            IToken<?> token = tokenList.fetch(x);

            StructureRule rule = token.getStructureRule();
            if (rule != null && rule.after() != null) {
                Class<? extends IToken> afterTokenRule = rule.after();

                if (afterTokenRule.isAssignableFrom(tokenClass)) {
                    return Optional.empty();
                }
            }

            if (tokenClass.isAssignableFrom(token.getClass())) {
                return Optional.of(token);
            }
        }

        return Optional.empty();
    }


    public static IToken<?> doubleSideFind(ITokenList tokenList) {

        int openTokens = 0;
        int closedTokens = 0;

        Set<IToken<?>> openTokensList = new HashSet<>();
        Set<IToken<?>> closeTokensList = new HashSet<>();

        int lastFound = 0;
        // ANALISANDO DE TRAS PARA FRENTE
        for (int x = 0; x < tokenList.size(); ++x) {


            IToken<?> token = tokenList.fetch(x);
            if (token.getStructureRule() != null) {

                StructureRule structureRule = token.getStructureRule();
                Class<?> tokenStart = structureRule.after();
                Class<?> tokenEnd = structureRule.before();

                if (tokenStart == null && tokenEnd == null) {
                    continue;
                }

                if (tokenStart == null) {
                    ++openTokens;
                    openTokensList.add(token);
                }

                if (tokenEnd == null) {
                    ++closedTokens;
                    closeTokensList.add(token);
                }

                for (int i = 0; i < tokenList.size(); ++i) {
                    IToken<?> checkToken = tokenList.fetch(i);

                    if (i == x) continue;


                    if (tokenEnd != null && tokenEnd.isAssignableFrom(checkToken.getClass())) {
                        closeTokensList.add(checkToken);
                        ++closedTokens;
                    } else if (tokenStart != null && tokenStart.isAssignableFrom(checkToken.getClass())) {
                        openTokensList.add(checkToken);
                        ++openTokens;
                    }

                }
            }
        }

        if (openTokens != closedTokens) {
            throw new RuntimeException("Invalid format! openTokens[" + openTokensList.size() + "] != closedTokens[" + closeTokensList.size() + "]. Multi Check: open[" + openTokens + "],close[" + closedTokens + "]", new AnaliseException("Structure problem", openTokensList, closeTokensList));
        }
        /*if (openTokens != 0
                || closedTokens != 0){
        }*/
        return null;
    }
}
