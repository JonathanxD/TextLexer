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
package com.github.jonathanxd.textlexer.ext.parser.processor.rule;

import com.github.jonathanxd.iutils.collection.ListUtils;
import com.github.jonathanxd.textlexer.ext.parser.exceptions.TokenParseException;
import com.github.jonathanxd.textlexer.ext.parser.processor.StructureProcessor;
import com.github.jonathanxd.textlexer.ext.parser.processor.action.Actions;
import com.github.jonathanxd.textlexer.ext.parser.processor.action.ProcessingState;
import com.github.jonathanxd.textlexer.ext.parser.structure.ParseSection;
import com.github.jonathanxd.textlexer.ext.parser.structure.StructuredTokens;
import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.history.TokenListUtil;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;

/**
 * Created by jonathan on 20/02/16.
 */

/**
 * RuleProcessor, this processor is used to process Elements using Rules/Requirements
 */
public abstract class RuleProcessor implements StructureProcessor {

    @Override
    public void process(List<IToken<?>> tokenList, StructuredTokens structure, ParseSection section) {
        ListIterator<IToken<?>> tokenIterator = tokenList.listIterator();

        Map<IToken<?>, TokenRules> allRules = new HashMap<>();

        while (tokenIterator.hasNext()) {
            int index = tokenIterator.nextIndex();
            IToken<?> token = tokenIterator.next();

            if (!token.hide()) {
                int vIndex = TokenListUtil.lastVisibleTokenIndex(index - 1, tokenList);
                IToken<?> lastToken = TokenListUtil.lastVisibleToken(index - 1, tokenList);
                allRules.entrySet().forEach(rule -> {
                    try {
                        rule.getValue().applyRight(token.getClass(), vIndex);
                    } catch (Exception e) {
                        String[] results = ListUtils.markListPosition(tokenList, index, Objects::toString);
                        throw new TokenParseException("Exception during parsing Token: '" + token + "'. Last Token: " + tokenList.get(index - 1)+". With Rules of Token: '"+rule.getKey()+"'", results, e);
                    }

                });
                allRules.clear();

                TokenRules rules = new TokenRules();
                parseVisit(token, rules);

                allRules.put(token, rules);

                try {
                    //int vIndex = TokenListUtil.lastVisibleTokenIndex(index - 1, tokenList);
                    rules.applyLeftToList(vIndex, tokenList);
                } catch (Exception e) {
                    throw new RuntimeException("Exception during parse of Token: '" + token + "'. Last Token: " + tokenList.get(index - 1), e);
                }


                Actions actions = rules.getActions();

                ProcessingState state = actions.doAll(token, section, tokenList, index);

                if (state == null || state == ProcessingState.DEFAULT) {
                } else if (state == ProcessingState.BREAK) {
                    break;
                } else if (state == ProcessingState.CONTINUE) {
                    /**
                     *
                     */
                    continue;
                }
            }
        }
    }

    public abstract void parseVisit(IToken<?> token, TokenRules rules);
}
