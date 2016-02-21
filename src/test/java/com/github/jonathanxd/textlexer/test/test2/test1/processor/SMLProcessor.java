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
package com.github.jonathanxd.textlexer.test.test2.test1.processor;

import com.github.jonathanxd.textlexer.ext.parser.processor.action.Action;
import com.github.jonathanxd.textlexer.ext.parser.processor.action.Actions;
import com.github.jonathanxd.textlexer.ext.parser.processor.rule.RuleProcessor;
import com.github.jonathanxd.textlexer.ext.parser.processor.rule.TokenRules;
import com.github.jonathanxd.textlexer.ext.parser.processor.rule.requeriments.Requirements;
import com.github.jonathanxd.textlexer.ext.parser.structure.StructuredTokens;
import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.Comma;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.KeyToken;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.KeyValueDivider;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.ValueToken;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.host.MapClose;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.host.MapOpen;

/**
 * Created by jonathan on 20/02/16.
 */
public class SMLProcessor extends RuleProcessor {

    private static final Action DEFAULT = Actions.fromOptionProcessor(new SMLOptionsProcessor());
    private static final Action ACTION = new SMLActionProcessor();

    @Override
    public void processFinish(StructuredTokens structure) {
        /** UNIFY **/
        SMLUnify.unify(structure);
    }

    @Override
    public void parseVisit(IToken<?> token, TokenRules rules) {
        /**
         * DEFAULT
         */
        rules.set(Actions.newInstance().offer(ACTION));

        /**
         *

         * MAPOPEN *

         * MAPCLOSE *

         * COMMA *

         * KEYTOKEN *

         * VALUETOKEN

         * KEYVALUEDIVIDER

         *

         *
         */

        if(token instanceof KeyToken) {


            rules.left(Requirements.newInstance().require(MapOpen.class).or(Comma.class).or(Requirements.RequireState.EMPTY));
            rules.right(Requirements.newInstance().require(KeyValueDivider.class).or(MapOpen.class).or(Comma.class).or(MapClose.class));

        } else if (token instanceof Comma) { //COMMA

            rules.left(Requirements.newInstance().require(KeyToken.class).or(ValueToken.class));
            rules.right(Requirements.newInstance().require(KeyToken.class));

        } else if (token instanceof KeyValueDivider) {

            rules.left(Requirements.newInstance().require(KeyToken.class));
            rules.right(Requirements.newInstance().require(ValueToken.class).or(KeyToken.class).or(MapOpen.class));

        } else if (token instanceof MapOpen) {

            rules.left(Requirements.newInstance().require(KeyToken.class).or(KeyValueDivider.class));
            rules.right(Requirements.newInstance().require(MapOpen.class).or(KeyToken.class));

        } else if (token instanceof MapClose) {

            rules.left(Requirements.newInstance().require(MapClose.class).or(KeyToken.class));
            rules.right(Requirements.newInstance().require(Comma.class).or(KeyToken.class));

        } else if (token instanceof ValueToken) {

            rules.left(Requirements.newInstance().require(KeyToken.class).or(KeyValueDivider.class));
            rules.right(Requirements.newInstance().require(Comma.class).or(KeyToken.class));

        }
    }


}
