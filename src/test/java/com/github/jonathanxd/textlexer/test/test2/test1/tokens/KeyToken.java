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

import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.UnifiedTokenFactory;
import com.github.jonathanxd.textlexer.lexer.token.builder.BuilderList;
import com.github.jonathanxd.textlexer.lexer.token.builder.TokenBuilder;
import com.github.jonathanxd.textlexer.lexer.token.history.ITokenList;
import com.github.jonathanxd.textlexer.lexer.token.history.LoopDirection;
import com.github.jonathanxd.textlexer.lexer.token.history.analise.ElementSpecification;
import com.github.jonathanxd.textlexer.lexer.token.processor.ProcessorData;
import com.github.jonathanxd.textlexer.lexer.token.processor.future.FutureSpec;
import com.github.jonathanxd.textlexer.lexer.token.structure.analise.StructureRule;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.host.MapClose;
import com.github.jonathanxd.textlexer.test.test2.test1.tokens.host.MapOpen;
import com.github.jonathanxd.textlexer.util.StackArrayList;

import java.util.Collections;

/**
 * Created by jonathan on 20/02/16.
 */
public class KeyToken extends UnifiedTokenFactory<String> {

    private final StructureRule structureRule = new StructureRule() {
        @Override
        public IToken<?> getToken() {
            return KeyToken.this;
        }

        @Override
        public Class<? extends IToken> before() {
            return KeyValueDivider.class;
        }
    };

    @Override
    public String dataToValue() {
        return getData();
    }

    @Override
    public boolean matches(char character) {
        return false;
    }

    @Override
    public StructureRule getStructureRule() {
        return structureRule;
    }

    @Override
    public boolean matches(ProcessorData processorData) {

        if(!processorData.isFutureAnalysis()) {
            try{

                StackArrayList<IToken<?>> next = processorData.getTokensProcessor().futureToken(
                        new FutureSpec(1, 2, ((character, tokenList) ->character != 'v'), null, null),
                        Collections.emptyList(),
                        null,
                        processorData.getScanner(),
                        false);


                next.foreachAddOrder(System.out::println);

                System.out.println("Next: "+next);
            }catch (Exception e){}
        }

        char character = processorData.getCharacter();
        BuilderList builderList = processorData.getBuilderList();
        ITokenList tokenList = processorData.getTokenList();

        if(!builderList.hasCurrent()) {
            return true;
        }

        TokenBuilder tokenBuilder = builderList.current();

        String data = tokenBuilder.getData();

        if((data.trim().isEmpty() && character == ' '))
            return false;

        if(tokenBuilder.getTokenFactory() instanceof Comma && character != ' ') {
            return true;
        }

        if(tokenList.size() == 0) {
            return true;
        }

        IToken<?> iToken = tokenList.find(ElementSpecification.tokenPredicateSpec((token) -> {
            if(token instanceof MapOpen || token instanceof MapClose || token instanceof Comma) {
                return true;
            }
            return false;
        }, (stopAt) -> {
            if(stopAt instanceof KeyValueDivider) {
                return true;
            }
            return false;
        }, LoopDirection.LAST_TO_FIRST));



        if(iToken != null) {
            boolean isObject = SMLUtil.isObject(processorData);
            return isObject;
        }


        return false;
    }

    @Override
    public int order() {
        return 7;
    }
}
