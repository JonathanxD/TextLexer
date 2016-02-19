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

import com.github.jonathanxd.textlexer.ext.parser.structure.ParseStructure;
import com.github.jonathanxd.textlexer.ext.show.StructureGUI;
import com.github.jonathanxd.textlexer.test.calc.CalcTest;

/**
 * Created by jonathan on 18/02/16.
 */
public class Test {

    @org.junit.Test
    public void viewTest() {
        CalcTest calcTest = new CalcTest();

        ParseStructure structure = calcTest.LexerAndParserTest();

        StructureGUI gui = StructureGUI.make(structure);

        gui.show();
    }

    public static void main(String[] args) {
        new Test().viewTest();
    }
}
