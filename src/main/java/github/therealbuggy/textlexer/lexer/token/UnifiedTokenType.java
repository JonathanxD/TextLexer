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
package github.therealbuggy.textlexer.lexer.token;

import github.therealbuggy.textlexer.lexer.token.builder.TokenBuilder;
import github.therealbuggy.textlexer.lexer.token.history.ITokenList;
import github.therealbuggy.textlexer.lexer.token.type.ITokenType;

/**
 * A Unification of TokenType and IToken.
 *
 * The class that implements it needs to have a empty constructor, or override the {@link
 * #createToken(String)} method.
 */
public abstract class UnifiedTokenType<T> extends AbstractToken<T> implements ITokenType<T> {

    public UnifiedTokenType() {
        super("");
    }

    @Override
    public String getSimpleName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public TokenBuilder process(ITokenList ITokenList, TokenBuilder current, char character) {
        current.addToData(character);
        return current;
    }

    @SuppressWarnings("unchecked")
    @Override
    public IToken<T> createToken(String data) {
        IToken<T> token;
        try {
            token = this.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Error, read the " + UnifiedTokenType.class.getName() + " documentation!", e);
        }
        token.setData(data);
        return token;
    }
}
