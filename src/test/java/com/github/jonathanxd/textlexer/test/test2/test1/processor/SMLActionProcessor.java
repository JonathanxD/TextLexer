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

import com.github.jonathanxd.iutils.collection.ListUtils;
import com.github.jonathanxd.iutils.object.ObjectUtils;
import com.github.jonathanxd.textlexer.ext.parser.exceptions.ActionDeterminateException;
import com.github.jonathanxd.textlexer.ext.parser.processor.action.Action;
import com.github.jonathanxd.textlexer.ext.parser.processor.action.ProcessingState;
import com.github.jonathanxd.textlexer.ext.parser.structure.ParseSection;
import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.history.TokenListUtil;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.Comma;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.Garbage;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.KeyToken;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.KeyValueDivider;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.ValueToken;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.host.MapClose;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.host.MapOpen;

import java.util.List;

/**
 * Created by jonathan on 20/02/16.
 */
public class SMLActionProcessor implements Action {
    @Override
    public ProcessingState stateAction(IToken<?> token, ParseSection section, List<IToken<?>> tokenList, int index) {

        if (token instanceof Garbage)
            return ProcessingState.CONTINUE;


        if (ObjectUtils.isInstanceOfAny(token, KeyToken.class, MapOpen.class, ValueToken.class)) {
            // AUTO_ASSIGN
            section.createOrLink(token);
            return ProcessingState.DEFAULT;
        }

        if (ObjectUtils.isInstanceOfAny(token, MapClose.class)) {
            //STACK
            section.stack(token);
            return ProcessingState.DEFAULT;
        }

        if (ObjectUtils.isInstanceOfAny(token, Comma.class)) {

            section.exit();
            section.stack(token);
            //section.exit();

            System.out.println("Current: "+section.getCurrent());


            if(!isMap(section) && index + 1 < tokenList.size()) {
                IToken<?> next = TokenListUtil.nextVisibleToken(index+1, tokenList);
                if(next != null && next instanceof KeyToken || next instanceof MapOpen || next instanceof MapClose) {
                    section.exit();
                }
            }
            return ProcessingState.DEFAULT;
        }

        if(ObjectUtils.isInstanceOfAny(token, KeyValueDivider.class)) {
            //STACK
            section.stack(token);
            return ProcessingState.DEFAULT;
        }
        throw new ActionDeterminateException("Cannot deteminer action of token: '"+token+"'.", ListUtils.markListPosition(tokenList, index, java.util.Objects::toString));
    }

    private boolean isMap(ParseSection section) {
        for(IToken<?> token : section.getCurrent().getTokens()) {
            if(token instanceof MapOpen) {
                return true;
            }
        }
        return false;
    }
}
