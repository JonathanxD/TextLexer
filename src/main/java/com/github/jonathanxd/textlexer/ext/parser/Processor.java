package com.github.jonathanxd.textlexer.ext.parser;

import com.github.jonathanxd.textlexer.ext.parser.structure.ParseStructure;
import com.github.jonathanxd.textlexer.lexer.token.IToken;

/**
 * Created by jonathan on 17/02/16.
 */
public interface Processor {
    IToken<?> currentToken();
    IToken<?> safeNext();
    ParseStructure structure();
    ParseStructure.ParseSection section();
}
