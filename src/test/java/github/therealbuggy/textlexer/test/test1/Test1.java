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
package github.therealbuggy.textlexer.test.test1;

import java.util.Optional;

import github.therealbuggy.ext.textlexer.reconstructor.Constructor;
import github.therealbuggy.textlexer.TextLexer;
import github.therealbuggy.textlexer.lexer.token.IToken;
import github.therealbuggy.textlexer.lexer.token.history.ITokenList;
import github.therealbuggy.textlexer.lexer.token.structure.analise.SimpleAnalyzer;
import github.therealbuggy.textlexer.test.test1.tokens.ExpressionClose;
import github.therealbuggy.textlexer.test.test1.tokens.ExpressionOpen;
import github.therealbuggy.textlexer.test.test1.tokens.IfToken;
import github.therealbuggy.textlexer.test.test1.tokens.Space;
import github.therealbuggy.textlexer.test.test1.tokens.Variable;

/**
 * Created by jonathan on 08/02/16.
 */
public class Test1 {
    public static void main(String[] args) throws Exception {
        TextLexer textLexer = new TextLexer();
        textLexer.addTokenTypes(ExpressionOpen.class, ExpressionClose.class, IfToken.class, Space.class, Variable.class);
        textLexer.addStructureAnalyzer(new SimpleAnalyzer());

        textLexer.processString("if (x)");

        System.out.println(textLexer.getReflectionUtil().getOrderedTokenSet());

        ITokenList tokenList = textLexer.getTokens();
        System.out.println(tokenList);

        // Change variable to y
        Optional<Variable> variableOpt = tokenList.first(Variable.class);

        if(variableOpt.isPresent()) {
            Variable variable = variableOpt.get();
            variable.setData("y");
        }

        // Construct data again

        Constructor constructor = new Constructor(textLexer.getTokens());
        constructor.addConstructor(IToken::getData);
        // Or constructor.addConstructor(new BaseConstructor());
        char[] chars = constructor.toCharArray();
        String str = new String(chars);

        System.out.println("Data: "+str);

    }

}
