/**
 * 	${name} - ${description} <${url}>
 *     Copyright (C) ${year} ${organization} <${email}>
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
package com.github.jonathanxd.textlexer.test.calc.view;

import com.github.jonathanxd.textlexer.ext.parser.structure.StructuredTokens;
import com.github.jonathanxd.textlexer.ext.show.StructureGUI;
import com.github.jonathanxd.textlexer.test.calc.CalcTest;
import com.github.jonathanxd.textlexer.test.test2.test1.Test2;

/**
 * Created by jonathan on 18/02/16.
 */
public class Test {

    //@org.junit.Test
    public void viewTest() {
        CalcTest calcTest = new CalcTest();

        StructuredTokens structure = calcTest.LexerAndParserTest();

        StructureGUI gui = StructureGUI.make(structure);

        gui.show();
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        //new Test().viewTest();

        Test2 test2 = new Test2();

        StructuredTokens structuredTokens = test2.Test();

        StructureGUI gui = StructureGUI.make(structuredTokens);

        gui.show();


    }
}
