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
package github.therealbuggy.ext.textlexer.reconstructor;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import github.therealbuggy.ext.textlexer.reconstructor.data.IDataReconstructor;
import github.therealbuggy.textlexer.lexer.token.IToken;
import github.therealbuggy.textlexer.lexer.token.history.ITokenList;

/**
 * Created by jonathan on 08/02/16.
 */
public class Reconstructor {

    private final List<IToken<?>> tokenList;
    private final Set<IDataReconstructor> dataReconstructors = new TreeSet<>();

    public Reconstructor(ITokenList tokenList) {
        this.tokenList = tokenList.allToList();
    }

    public void addReconstructor(IDataReconstructor dataReconstructor) {
        dataReconstructors.add(dataReconstructor);
    }

    public char[] toCharArray() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int x = 0; x < tokenList.size(); ++x) {
            IToken<?> token = tokenList.get(x);
            String data = "";

            for (IDataReconstructor dataReconstructor : dataReconstructors) {
                if (dataReconstructor.canTranslate(token)) {

                    data = dataReconstructor.getTokenData(token, data, tokenList, x);

                    if (data == null)
                        break;
                }
            }
            if (data != null)
                stringBuilder.append(data);
        }

        return stringBuilder.toString().toCharArray();

    }

}
