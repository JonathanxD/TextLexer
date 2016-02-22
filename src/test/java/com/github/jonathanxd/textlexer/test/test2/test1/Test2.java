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
package com.github.jonathanxd.textlexer.test.test2.test1;

import com.github.jonathanxd.textlexer.TextLexer;
import com.github.jonathanxd.textlexer.ext.constructor.structure.PositionFactory;
import com.github.jonathanxd.textlexer.ext.constructor.structure.StructureConstructor;
import com.github.jonathanxd.textlexer.ext.parser.Parser;
import com.github.jonathanxd.textlexer.ext.parser.structure.StructuredTokens;
import com.github.jonathanxd.textlexer.ext.visitor.visit.StructureVisitor;
import com.github.jonathanxd.textlexer.ext.visitor.visit.Visitor;
import com.github.jonathanxd.textlexer.lexer.token.history.ITokenList;
import com.github.jonathanxd.textlexer.test.test2.test1.processor.SMLProcessor;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.Comma;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.Garbage;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.KeyToken;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.KeyValueDivider;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.ValueToken;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.host.MapClose;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.host.MapOpen;
import com.github.jonathanxd.textlexer.test.test2.test1.visit.listener.TokenVisitor;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * Created by jonathan on 08/02/16.
 */
public class Test2 {

    @Test
    public void LexerTest() throws Exception {
        Test();
    }

    public StructuredTokens Test() throws IllegalAccessException, InstantiationException, UnsupportedEncodingException {
        TextLexer textLexer = new TextLexer();

        textLexer.addTokenTypes(MapOpen.class, Garbage.class, MapClose.class, Comma.class, KeyToken.class, KeyValueDivider.class, ValueToken.class);

        textLexer.processString("keyA = \"value\", keyB = { value1 = 7, value2 }");


        ITokenList tokenList = textLexer.getTokens();

        System.out.println("TokenList [] : "+tokenList);

        tokenList.modify((token, list, index) -> {
            if(!(token instanceof Garbage)) {
                Garbage.garbage(token, list, index);
            }
        });

        System.out.println("TokenList: "+tokenList);

        Parser parser = new Parser(tokenList);

        parser.addProcessor(new SMLProcessor());

        StructuredTokens structure = parser.parse();

        System.out.println("Structure: "+structure);

        Visitor<StructuredTokens> visitor = new StructureVisitor(structure);

        visitor.addListener(new TokenVisitor());

        visitor.visit();

        StructureConstructor constructor = new StructureConstructor(structure, PositionFactory.DEFAULT);

        String str = new String(constructor.construct(), "UTF-8");

        System.out.println("CONSTRUCT: "+str);

        return structure;
    }

}
