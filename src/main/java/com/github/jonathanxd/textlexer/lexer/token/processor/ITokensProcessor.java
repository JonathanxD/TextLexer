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
package com.github.jonathanxd.textlexer.lexer.token.processor;

import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.history.ITokenList;
import com.github.jonathanxd.textlexer.lexer.token.processor.future.CurrentTokenData;
import com.github.jonathanxd.textlexer.lexer.token.type.ITokenType;
import com.github.jonathanxd.textlexer.scanner.IScanner;
import com.github.jonathanxd.textlexer.util.StackArrayList;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by jonathan on 30/01/16.
 */
public interface ITokensProcessor extends Cloneable {

    /**
     * Add a Token Factory (TokenType)
     *
     * @param token TokenFactory
     */
    void addTokenType(ITokenType<?> token);

    /**
     * Add a Token and token matcher (Is Recommended to use {@link ITokenType} instead)
     *
     * @param token   Token to add
     * @param matcher Character Matcher
     * @param <T>     Type of {@code token} return
     */
    <T> void addToken(Class<IToken<T>> token, Predicate<Character> matcher);

    /**
     * Process a character
     *
     * @param input    Current Character
     * @param allChars All Characters
     * @param index    Index of 'pointer' to a character in {@code allChars} List.
     * @param scanner  The Scanner
     */
    void process(char input, List<Character> allChars, int index, IScanner scanner);

    /**
     * Returns A List of Tokens
     *
     * @return A List of Tokens
     */
    ITokenList getTokenList();

    /**
     * Close current open builder
     *
     * @return True if has current builder.
     */
    boolean closeOpenBuilders();

    /**
     * Get Future Token emulating tokens.
     *
     * @param from           Position to start in scanner (Current + from)
     * @param index          Position of token to get
     * @param emulatedTokens Emulated/fake tokens, the size of the emulatedTokens List need to be
     *                       the same of {@code index} parameter or empty list
     * @param currentType    CurrentTokenData to be set as current token building.
     * @param scanner        The Scanner
     * @param ignoreHide     Ignore the hidden tokens
     * @return Token in Offset
     */
    StackArrayList<IToken<?>> future(int from, int index, List<IToken<?>> emulatedTokens, CurrentTokenData currentType, IScanner scanner, boolean ignoreHide);

    ITokensProcessor clone();
}
