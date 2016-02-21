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
package com.github.jonathanxd.textlexer.ext.visitor.visit;

import com.github.jonathanxd.iutils.data.DataProvider;
import com.github.jonathanxd.textlexer.ext.parser.holder.TokenHolder;
import com.github.jonathanxd.textlexer.ext.parser.structure.StructuredTokens;

import java.util.List;

/**
 * Created by jonathan on 20/02/16.
 */

/**
 * Visits The StructuredTokens
 */
@DataProvider({StructuredTokens.class})
public class StructureVisitor extends CommonVisitor<StructuredTokens> {

    private final StructuredTokens structure;

    public StructureVisitor(StructuredTokens structure) {
        super();
        this.structure = structure;
        getDefaultData().registerData(this.structure);
    }

    @Override
    public void visit() {
        recursive(structure.getTokenHolders());
    }

    /**
     * Recursive token visit, visit token immediately and exit only has no direct-related and
     * indirect-related child TokenHolders
     *
     * @param tokenHolderList Tokens
     */
    private void recursive(List<TokenHolder> tokenHolderList) {
        for (TokenHolder tokenHolder : tokenHolderList) {
            visit(tokenHolder);
            recursive(tokenHolder.getChildTokens());
            exit(tokenHolder);
        }
    }
}
