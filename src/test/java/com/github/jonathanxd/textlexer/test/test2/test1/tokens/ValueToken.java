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
package com.github.jonathanxd.textlexer.test.test2.test1.tokens;

import com.github.jonathanxd.iutils.iterator.IteratorUtil;
import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.UnifiedTokenType;
import com.github.jonathanxd.textlexer.lexer.token.builder.BuilderList;
import com.github.jonathanxd.textlexer.lexer.token.builder.TokenBuilder;
import com.github.jonathanxd.textlexer.lexer.token.history.ITokenList;
import com.github.jonathanxd.textlexer.lexer.token.history.LoopDirection;
import com.github.jonathanxd.textlexer.lexer.token.history.analise.ElementSpecification;
import com.github.jonathanxd.textlexer.lexer.token.processor.ProcessorData;
import com.github.jonathanxd.textlexer.test.test2.test1.QuoteUtil;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.host.MapClose;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.host.MapOpen;

import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * Created by jonathan on 20/02/16.
 */
public class ValueToken extends UnifiedTokenType<String> {

    @Override
    public String dataToValue() {
        return null;
    }

    @Override
    public boolean matches(char character) {
        return false;
    }

    @Override
    public boolean matches(ProcessorData processorData) {


        char character = processorData.getCharacter();
        BuilderList builderList = processorData.getBuilderList();
        ITokenList tokenList = processorData.getTokenList();

        if(!builderList.hasCurrent())
            return false;

        TokenBuilder tokenBuilder = builderList.current();


        String data = tokenBuilder.getData();

        if(data.trim().isEmpty() && character == ' ')
            return false;

        if(tokenBuilder.getTokenType() instanceof KeyValueDivider && character != ' ') {
            return true;
        }

        IToken<?> iToken = tokenList.find(ElementSpecification.tokenPredicateSpec((token) -> {
            if(token instanceof KeyValueDivider) {
                return true;
            }
            return false;
        }, (stopAt) -> {
            if(stopAt instanceof ValueToken || stopAt instanceof MapOpen || stopAt instanceof MapClose) {
                return true;
            }
            return false;
        }, LoopDirection.LAST_TO_FIRST));


        if(iToken != null) {
            boolean isObj = SMLUtil.isObject(processorData);
            return isObj;
        }


        return false;
    }

}
