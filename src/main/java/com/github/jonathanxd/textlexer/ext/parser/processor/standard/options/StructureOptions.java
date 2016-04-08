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
package com.github.jonathanxd.textlexer.ext.parser.processor.standard.options;

import com.github.jonathanxd.textlexer.ext.parser.structure.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by jonathan on 17/02/16.
 */

/**
 * Highly recommended to use {@link com.github.jonathanxd.textlexer.ext.parser.processor.action.Actions}
 * System.
 *
 * Actions System give you more ways to create Structure.
 *
 * Options System has predefined Structure determination.
 *
 * Option Parse system
 */
public class StructureOptions {
    /**
     * List of options
     */
    private final List<Option> optionses = new ArrayList<>();

    /**
     * Create/Delete option
     *
     * @param option Option
     * @param value  If true, create option, if false, delete
     * @return This
     */
    public StructureOptions set(Option option, boolean value) {
        if (!value)
            optionses.remove(option);
        else
            optionses.add(option);
        return this;
    }

    /**
     * @param option Option
     * @param value  Value
     * @return StructureOptions
     * @see #set(Option, boolean)
     */
    public StructureOptions and(Option option, boolean value) {
        return set(option, value);
    }

    /**
     * Get Option or Null
     *
     * @param option Option
     * @param <T>    Type of option
     * @return Option or null
     */
    @SuppressWarnings("unchecked")
    public <T> Option<T> getOption(Option<T> option) {

        return getOption(option.getClass());
    }

    /**
     * Get option based related class
     *
     * @param typeClass Class
     * @param <T>       Type of option
     * @return Option or null
     */
    @SuppressWarnings("unchecked")
    public <T> Option<T> getOption(Class<?> typeClass) {

        for (Option<?> optionX : optionses) {

            if (typeClass.isAssignableFrom(optionX.getClass())) {
                return (Option<T>) optionX;
            }
        }

        return null;
    }

    /**
     * Return true if option factory is present
     *
     * @param type Option Type
     * @return true if option factory is present
     */
    public boolean is(Option.Type type) {
        return getOption(type.getType()) != null;
    }

    /**
     * Return true if option is present
     *
     * @param option Option
     * @return true if option is present
     */
    public boolean is(Option option) {

        return getOption(option.getClass()) != null;
    }

    /**
     * Clone the StructureOptions
     *
     * @param filter Filter
     * @return Filtered StructureOptions
     */
    public StructureOptions clone(Predicate<? super Option> filter) {
        StructureOptions structureOptions = new StructureOptions();
        structureOptions.optionses.addAll(this.optionses.stream().filter(filter).collect(Collectors.toList()));

        return structureOptions;
    }

    /**
     * Add all options from another StructureOptions
     *
     * @param other Other StructureOptions
     * @return This
     */
    public StructureOptions allFrom(StructureOptions other) {

        this.optionses.addAll(other.optionses);

        return this;
    }

}
