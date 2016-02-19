/**
 * 	${name} - ${description} <${url}>
 *     Copyright (C) ${year} ${organization} <${email}>
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
package com.github.jonathanxd.textlexer.ext.show;

import com.github.jonathanxd.textlexer.ext.parser.holder.TokenHolder;
import com.github.jonathanxd.textlexer.ext.parser.structure.ParseStructure;
import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import javax.swing.*;

/**
 * Created by jonathan on 18/02/16.
 */
public class StructureGUI {

    private final JFrame jf;
    private final mxGraph graph;
    private final Object parent;
    private final ParseStructure structure;
    private static final int[] mainSizes = {200, 150, 80, 30};

    StructureGUI(ParseStructure structure) {
        this.structure = structure;

        jf = new JFrame("View");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setSize(500, 500);
        jf.setLayout(new GridLayout());

        graph = new mxGraph();
        mxGraphComponent component = new mxGraphComponent(graph);
        component.setSize(500, 500);
        component.setEnabled(false);

        parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();

        try{

            create();
        }finally {
            graph.getModel().endUpdate();
        }

        mxIGraphLayout layout = new mxHierarchicalLayout(graph);

        graph.getModel().beginUpdate();
        try {
            layout.execute(graph.getDefaultParent());
        } finally {
            graph.getModel().endUpdate();
        }

        jf.add(component);
        //jf.pack();
        jf.setVisible(true);
    }


    public void show() {
        jf.setVisible(true);
    }

    public static void main(String[] args) {
        new StructureGUI(null).show();

    }

    public static StructureGUI make(ParseStructure structure) {
        return new StructureGUI(structure);
    }

    private void create() {
        proc(null, structure.getTokenHolders());
    }


    private void proc(Collection<Object> mains, Collection<TokenHolder> holders) {
        for(TokenHolder tokenHolder : holders) {
            Collection<Object> links = vertexLinking(tokenHolder.getName(), tokenHolder.getTokens());

            LinkedList<Object> linkedList = new LinkedList<>(links);


            Object root = null;

            if(mains != null) {
                for(Object main : mains) {

                    if(linkedList.size() > 2) {
                        Object last = linkedList.getLast();
                        if(last != null) {
                            addEdge(parent, null, "", main, last, true);
                            root = last;
                            break;
                        }
                    }


                    for(Object link : linkedList) {
                        addEdge(parent, null, "", main, link, true);
                    }
                }
            }
            if(root != null) {
                linkedList.remove(root);
                links = linkedList;
            }

            proc(links, tokenHolder.getChildTokens());
        }
    }

    private Collection<Object> vertexLinking(String rootNodeName, Collection<IToken<?>> tokens) {
        Object root = null;
        if(tokens.size() > 1) {
            root = addVertex(parent, null, rootNodeName);
        }



        if(tokens.isEmpty())
            return Collections.singleton(root);

        Collection<Object> objects = new ArrayList<>();

        for(IToken<?> token : tokens) {

            Object vertex = addVertex(parent, null, token.toString());
            if(root != null)
                addEdge(parent, null, "", root, vertex, false);
            objects.add(vertex);
        }

        objects.add(root);

        return objects;
    }

    private Object addEdge(Object parent, String id, String name, Object first, Object second, boolean arrow) {
        return graph.insertEdge(parent, id, name, first, second, (!arrow ?  "endArrow=none;" : ""));
    }

    private Object addVertex(Object parent, String id, String name) {
        return graph.insertVertex(parent, id, name, mainSizes[0], mainSizes[1], mainSizes[2], mainSizes[3]);
    }
}
