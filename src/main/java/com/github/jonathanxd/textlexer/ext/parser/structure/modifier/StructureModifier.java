package com.github.jonathanxd.textlexer.ext.parser.structure.modifier;

import com.github.jonathanxd.textlexer.ext.parser.holder.TokenHolder;
import com.github.jonathanxd.textlexer.ext.parser.structure.ParseStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by jonathan on 18/02/16.
 */
public class StructureModifier {

    private final ParseStructure structure;
    private final List<TokenHolder> tokenHolderList;

    public StructureModifier(ParseStructure structure) {
        this.structure = structure;
        this.tokenHolderList = this.structure.getTokenHolders();
    }

    /*public TokenNavigate navigate() {

    }*/


    public void unify(String id, TokenHolder section, Predicate<TokenHolder> unifyPredicate, Predicate<TokenHolder> childUnify) {


        List<TokenHolder> unify = new ArrayList<>();
        List<TokenHolder> childs = new ArrayList<>();

        for (TokenHolder tokenHolder : section.getChildTokens()) {
            if (unifyPredicate.test(tokenHolder)) {
                unify.add(tokenHolder);
            } else if (childUnify.test(tokenHolder)) {
                childs.add(tokenHolder);
            }
        }

        section.getChildTokens().removeAll(unify);
        section.getChildTokens().removeAll(childs);


        TokenHolder holder = TokenHolder.ofHolders(id, unify, childs);
        section.getChildTokens().add(holder);
    }

    public ParseStructure getStructure() {
        return structure;
    }
}
