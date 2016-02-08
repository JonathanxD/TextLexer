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
package github.therealbuggy.textlexer.lexer;

import github.therealbuggy.textlexer.lexer.token.IToken;
import github.therealbuggy.textlexer.lexer.token.history.ITokenList;
import github.therealbuggy.textlexer.lexer.token.history.analise.AnaliseTokenList;
import github.therealbuggy.textlexer.lexer.token.processor.ITokensProcessor;
import github.therealbuggy.textlexer.scanner.IScanner;

/**
 * Created by jonathan on 30/01/16.
 */
public class LexerImpl implements ILexer {

    @Override
    public ITokenList process(IScanner scanner, ITokensProcessor tokenTypeList) {
        while (scanner.hasNextChar()) {
            char current = scanner.nextChar();
            tokenTypeList.process(current);
        }
        tokenTypeList.closeOpenBuilders();

        ITokenList tokenList = tokenTypeList.getTokenList();
        analise(tokenList);


        return tokenList;
    }

    public void analise(ITokenList tokenList) {

        IToken<?> token = AnaliseTokenList.doubleSideFind(tokenList);
        if (token != null) {
            throw new RuntimeException("Error during token structure analise phase!",
                    new RuntimeException("Token '" + token + "' must be between '" + token.getStructureRule().after() + "' and '" + token.getStructureRule().before() + "'"));
        }

    }
}
