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
package com.github.jonathanxd.textlexer.test.calc;

import com.github.jonathanxd.textlexer.TextLexer;
import com.github.jonathanxd.textlexer.ext.parser.Parser;
import com.github.jonathanxd.textlexer.ext.parser.holder.TokenHolder;
import com.github.jonathanxd.textlexer.ext.parser.structure.ParseStructure;
import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.history.ITokenList;
import com.github.jonathanxd.textlexer.test.calc.ext.parser.CalcProcessor;
import com.github.jonathanxd.textlexer.test.calc.tokens.Garbage;
import com.github.jonathanxd.textlexer.test.calc.tokens.GroupClose;
import com.github.jonathanxd.textlexer.test.calc.tokens.GroupOpen;
import com.github.jonathanxd.textlexer.test.calc.tokens.Number;
import com.github.jonathanxd.textlexer.test.calc.tokens.Round;
import com.github.jonathanxd.textlexer.test.calc.tokens.operators.Divide;
import com.github.jonathanxd.textlexer.test.calc.tokens.operators.Minus;
import com.github.jonathanxd.textlexer.test.calc.tokens.operators.Multiply;
import com.github.jonathanxd.textlexer.test.calc.tokens.operators.Plus;

import org.junit.Test;

import java.util.List;

/**
 * Created by jonathan on 17/02/16.
 */
public class CalcTest {

    @Test
    public void LexerAndParserTest() {
        TextLexer lexer = new TextLexer();

        try {
            lexer.addTokenTypes(Plus.class, Minus.class, Divide.class, Multiply.class,
                    /* | */ Round.class,
                    /* | */ Number.class,
                    /* | */ GroupOpen.class, GroupClose.class,
                    /* | */ Garbage.class);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        lexer.processString("5 + 7 * 4 + (5 + 7)");

        ITokenList list = lexer.getTokens();

        Parser parser = new Parser(list);
        parser.addProcessor(new CalcProcessor());

        ParseStructure structure = parser.parse();

        System.out.println("Structure: " + structure);



/*
    Number OPERATOR Number Operator Number
    | Number Operator (Number Operator Number)|
    process(Processor processor) {
        if(token is Operator) {
            processor.create();
        } else if (token is Number) {
            if(processor.safeNext is Operator)
            processor.link();
        }
    }
    5 + 7 * 4 + (5 + 7)
    Number OPERATOR Number OPERATOR Number OPERATOR SYMBOL Number Operator Number SYMBOL
    |Number OPERATOR| |Number OPERATOR| |Number OPERATOR| (SYMBOL) |Number Operator| + Number (SYMBOL)

     */

    }


}
