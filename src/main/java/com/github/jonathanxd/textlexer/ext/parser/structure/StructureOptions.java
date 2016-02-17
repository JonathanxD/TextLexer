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
package com.github.jonathanxd.textlexer.ext.parser.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by jonathan on 17/02/16.
 */
public class StructureOptions {

    private final List<Option> optionses = new ArrayList<>();

    public StructureOptions set(Option option, boolean value) {
        if (!value)
            optionses.remove(option);
        else
            optionses.add(option);
        return this;
    }

    public StructureOptions and(Option option, boolean value) {
        return set(option, value);
    }

    public boolean is(Option option) {
        return optionses.contains(option);
    }

    public StructureOptions clone(Predicate<? super Option> filter) {
        StructureOptions structureOptions = new StructureOptions();
        structureOptions.optionses.addAll(this.optionses.stream().filter(filter).collect(Collectors.toList()));

        return structureOptions;
    }

    public StructureOptions allFrom(StructureOptions other) {

        this.optionses.addAll(other.optionses);

        return this;
    }

}
