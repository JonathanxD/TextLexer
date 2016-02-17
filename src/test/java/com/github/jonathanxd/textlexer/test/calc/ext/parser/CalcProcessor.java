package com.github.jonathanxd.textlexer.test.calc.ext.parser;

import com.github.jonathanxd.iutils.annotations.Immutable;
import com.github.jonathanxd.textlexer.ext.parser.Processor;
import com.github.jonathanxd.textlexer.ext.parser.processor.ParserProcessor;
import com.github.jonathanxd.textlexer.ext.parser.structure.Options;
import com.github.jonathanxd.textlexer.ext.parser.structure.StructureOptions;
import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.test.calc.tokens.Garbage;
import com.github.jonathanxd.textlexer.test.calc.tokens.GroupClose;
import com.github.jonathanxd.textlexer.test.calc.tokens.GroupOpen;
import com.github.jonathanxd.textlexer.test.calc.tokens.operators.Operator;

import java.util.List;

/**
 * Created by jonathan on 17/02/16.
 */
public class CalcProcessor implements ParserProcessor {

    @Override
    public void process(Processor processor) {

    }

    @Override
    public StructureOptions tokenOptions(IToken<?> token) {
        if (token instanceof Operator) {
            return new StructureOptions().set(Options.HEAD, true).and(Options.INNER, true);
        } else if (token instanceof GroupOpen || token instanceof GroupClose) {

            return new StructureOptions().set(Options.STACK, true)
                    .and(Options.EXIT, true);
        } else if (token instanceof Garbage) {
            return new StructureOptions().set(Options.IGNORE, true);
        } else {
            return new StructureOptions().set(Options.ELEMENT, true)
                    .and(Options.EXIT, true);
        }
    }
}
