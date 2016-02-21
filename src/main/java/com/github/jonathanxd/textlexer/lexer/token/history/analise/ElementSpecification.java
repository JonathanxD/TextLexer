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
package com.github.jonathanxd.textlexer.lexer.token.history.analise;

import com.github.jonathanxd.textlexer.lexer.token.IToken;
import com.github.jonathanxd.textlexer.lexer.token.history.LoopDirection;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * Created by jonathan on 19/02/16.
 */
public class ElementSpecification {

    private final Predicate<IToken> tokenFindPredicate; // OR
    private final Predicate<IToken> stopAtPredicate; // OR
    private final LoopDirection direction;
    private final int startIndex;

    ElementSpecification(Predicate<IToken> tokenFindPredicate, Predicate<IToken> stopAtPredicate, LoopDirection direction, int startIndex) {
        this.tokenFindPredicate = tokenFindPredicate;
        this.stopAtPredicate = stopAtPredicate;
        this.direction = direction;
        this.startIndex = startIndex;
    }

    public static ElementSpecification tokenPredicateSpec(Predicate<IToken> tokenFindPredicate,
                                                          Predicate<IToken> stopAtPredicate,
                                                          LoopDirection direction) {

        return new ElementSpecification(tokenFindPredicate, stopAtPredicate, direction, -1);
    }

    public static ElementSpecification tokenPredicateSpec(Predicate<IToken> tokenFindPredicate,
                                                          Predicate<IToken> stopAtPredicate,
                                                          LoopDirection direction,
                                                          int startIndex) {

        return new ElementSpecification(tokenFindPredicate, stopAtPredicate, direction, startIndex);
    }

    public static ElementSpecification classCollectionSpec(Collection<Class<? extends IToken>> tokenClasses,
                                                           Collection<Class<? extends IToken>> stopAtClasses,
                                                           LoopDirection direction) {

        return new ClassCollectionSpec(tokenClasses, stopAtClasses, direction, -1);
    }

    public static ElementSpecification classCollectionSpec(Collection<Class<? extends IToken>> tokenClasses,
                                                           Collection<Class<? extends IToken>> stopAtClasses,
                                                           LoopDirection direction,
                                                           int startIndex) {

        return new ClassCollectionSpec(tokenClasses, stopAtClasses, direction, startIndex);
    }

    public LoopDirection getDirection() {
        return direction;
    }

    public boolean testStopToken(IToken<?> token) {
        return stopAtPredicate.test(token);
    }

    public boolean testFindToken(IToken<?> token) {
        return tokenFindPredicate.test(token);
    }

    public int getStartIndex() {
        return startIndex;
    }

    private static final class ClassCollectionSpec extends ElementSpecification {
        ClassCollectionSpec(Collection<Class<? extends IToken>> tokenClasses,
                            Collection<Class<? extends IToken>> stopAtClasses,
                            LoopDirection direction,
                            int startIndex) {

            /* IF(ANY TOKEN InstanceOf ANY findTokenClass) : FIND */
            super(token -> tokenClasses.stream().filter(findTokenClass -> findTokenClass.isAssignableFrom(token.getClass())).findAny().isPresent(),
                    /* IF(ANY TOKEN InstanceOf ANY stopClass) : STOP */
                    token -> stopAtClasses.stream().filter(stopAtClass -> stopAtClass.isAssignableFrom(token.getClass())).findAny().isPresent(),
                    direction, startIndex);
        }
    }

}
