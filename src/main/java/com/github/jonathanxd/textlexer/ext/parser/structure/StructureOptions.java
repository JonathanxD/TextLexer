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
