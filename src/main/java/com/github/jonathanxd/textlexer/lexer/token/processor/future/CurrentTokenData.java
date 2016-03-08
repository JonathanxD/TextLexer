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
package com.github.jonathanxd.textlexer.lexer.token.processor.future;

import com.github.jonathanxd.textlexer.lexer.token.builder.BuilderList;

import java.util.function.Consumer;

import javax.annotation.Nonnull;

/**
 * Created by jonathan on 3/7/16.
 */
public class CurrentTokenData {
    private final Consumer<BuilderList> builderListConsumer;

    /**
     * Create a CurrentTokenData to Future Emulation
     *
     * @param builderListConsumer Consume the BuilderList to you modify the list if necessary
     */
    public CurrentTokenData(@Nonnull Consumer<BuilderList> builderListConsumer) {
        this.builderListConsumer = builderListConsumer;
    }

    public void apply(BuilderList builderList) {
        if (this.builderListConsumer != null) {
            this.builderListConsumer.accept(builderList);
        }
    }
}
