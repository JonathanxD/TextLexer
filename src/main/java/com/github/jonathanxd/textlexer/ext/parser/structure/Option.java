package com.github.jonathanxd.textlexer.ext.parser.structure;

/**
 * Created by jonathan on 17/02/16.
 */
public class Option {

    private final String name;

    Option(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Options[" + name + "]";
    }
}