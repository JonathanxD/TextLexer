package com.github.jonathanxd.textlexer.ext.parser.tokenlist;

import com.github.jonathanxd.textlexer.ext.parser.Parser;
import com.github.jonathanxd.textlexer.ext.parser.structure.Options;
import com.github.jonathanxd.textlexer.lexer.token.IToken;

import java.util.List;

/**
 * Created by jonathan on 17/02/16.
 */
public class TokenListParserUtil {

    public static IToken<?> next(List<IToken<?>> list, int index, Parser parser) {
        for (int x = index; x < list.size(); ++x) {
            IToken<?> token = list.get(x);
            if (parser.optionsOf(token).is(Options.IGNORE)) {
                continue;
            }
            return token;
        }
        return null;
    }

}
