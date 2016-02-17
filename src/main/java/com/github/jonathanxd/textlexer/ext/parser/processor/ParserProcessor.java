package com.github.jonathanxd.textlexer.ext.parser.processor;

import com.github.jonathanxd.textlexer.ext.parser.Processor;
import com.github.jonathanxd.textlexer.ext.parser.structure.StructureOptions;
import com.github.jonathanxd.textlexer.lexer.token.IToken;

/**
 * Created by jonathan on 17/02/16.
 */
public interface ParserProcessor {
    void process(Processor processor);

    StructureOptions tokenOptions(IToken<?> token);
}
