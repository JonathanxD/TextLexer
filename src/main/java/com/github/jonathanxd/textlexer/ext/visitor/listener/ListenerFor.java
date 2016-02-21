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
package com.github.jonathanxd.textlexer.ext.visitor.listener;

/**
 * Created by jonathan on 20/02/16.
 */

import com.github.jonathanxd.textlexer.ext.parser.structure.StructuredTokens;
import com.github.jonathanxd.textlexer.ext.visitor.visit.Visitor;
import com.github.jonathanxd.textlexer.lexer.token.IToken;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Methods annotated with this annotation needs to have:
 *
 * 1 parameter. Types: {@link com.github.jonathanxd.textlexer.ext.parser.holder.TokenHolder}
 *
 * or
 *
 * 2 parameters:
 *
 * Type: {@link com.github.jonathanxd.textlexer.ext.parser.holder.TokenHolder}
 *
 * Type: {@link com.github.jonathanxd.textlexer.ext.visitor.visit.Visitor}
 *
 * Or other parameters like {@link com.github.jonathanxd.textlexer.lexer.token.history.ITokenList}
 * and {@link StructuredTokens} if the visitor is {@link com.github.jonathanxd.textlexer.ext.visitor.visit.TokenListVisitor}
 * or {@link com.github.jonathanxd.textlexer.ext.visitor.visit.StructureVisitor}.
 *
 * Parameters is annotated in head of {@link com.github.jonathanxd.textlexer.ext.parser.Parser}
 * classes with Annotation {@link com.github.jonathanxd.iutils.data.DataProvider}
 *
 * @see com.github.jonathanxd.textlexer.ext.visitor.visit.Visitor
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListenerFor {


    /**
     * Supported tokens
     *
     * @return Supported tokens
     */
    Class<? extends IToken<?>>[] value();

    /**
     * Visit phase
     *
     * @return Visit phase
     */
    VisitPhase phase() default VisitPhase.VISIT;

    /**
     * Required visitor to call this method
     *
     * @return Required visitor to call this method
     */
    Class<? extends Visitor> required() default Visitor.class;

}
