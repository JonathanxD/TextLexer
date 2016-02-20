package com.github.jonathanxd.textlexer.ext.parser.processor.action;

import com.github.jonathanxd.textlexer.ext.parser.structure.ParseStructure;
import com.github.jonathanxd.textlexer.lexer.token.IToken;

/**
 * Created by jonathan on 19/02/16.
 */
@FunctionalInterface
public interface Action {
    void doAction(IToken<?> token, ParseStructure.ParseSection section);
}
