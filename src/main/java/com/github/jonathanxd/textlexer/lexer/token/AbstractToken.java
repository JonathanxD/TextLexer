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
package com.github.jonathanxd.textlexer.lexer.token;

import com.github.jonathanxd.iutils.extra.IMutableContainer;
import com.github.jonathanxd.iutils.extra.ImmutableContainer;
import com.github.jonathanxd.iutils.extra.MutableContainer;

/**
 * Created by jonathan on 06/02/16.
 */
public abstract class AbstractToken<T> implements IToken<T> {

    private final IMutableContainer<String> data = new MutableContainer<>();

    public AbstractToken(String data) {
        this.data.set(data);
    }

    @Override
    public IMutableContainer<String> mutableData() {
        return data;
    }

    @Override
    public ImmutableContainer<String> immutableData() {
        return new ImmutableContainer<>(data.getValue());
    }

    @Override
    public String getData() {
        return immutableData().get();
    }

    @Override
    public void setData(String data) {
        this.data.set(data);
    }

    @Override
    public String toString() {
        return getSimpleName() + "[" + getData() + "]";
    }
}
