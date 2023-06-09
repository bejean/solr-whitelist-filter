/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.lucene.analysis.core;

import java.util.Map;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WordlistLoader;
import org.apache.lucene.analysis.en.AbstractWordsFileFilterFactory;
import org.apache.lucene.analysis.en.EnglishAnalyzer;

/**
 * Factory for {@link WhiteListFilter}.
 *
 * <pre class="prettyprint">
 * &lt;fieldType name="text_white" class="solr.TextField" positionIncrementGap="100" autoGeneratePhraseQueries="true"&gt;
 *   &lt;analyzer&gt;
 *     &lt;tokenizer class="solr.WhitespaceTokenizerFactory"/&gt;
 *     &lt;filter class="org.apache.lucene.analysis.core.WhiteListFilterFactory" ignoreCase="true"
 *             words="whitewords.txt" format="wordset"
 *   &lt;/analyzer&gt;
 * &lt;/fieldType&gt;</pre>
 *
 * <p>All attributes are optional:
 *
 * <ul>
 *   <li><code>ignoreCase</code> defaults to <code>false</code>
 *   <li><code>words</code> should be the name of a whitewords file to parse, if not specified the
 *       factory will use {@link EnglishAnalyzer#ENGLISH_STOP_WORDS_SET}
 *   <li><code>format</code> defines how the <code>words</code> file will be parsed, and defaults to
 *       <code>wordset</code>. If <code>words</code> is not specified, then <code>format</code> must
 *       not be specified.
 * </ul>
 *
 * <p>The valid values for the <code>format</code> option are:
 *
 * <ul>
 *   <li><code>wordset</code> - This is the default format, which supports one word per line
 *       (including any intra-word whitespace) and allows whole line comments beginning with the "#"
 *       character. Blank lines are ignored. See {@link WordlistLoader#getLines
 *       WordlistLoader.getLines} for details.
 *   <li><code>snowball</code> - This format allows for multiple words specified on each line, and
 *       trailing comments may be specified using the vertical line ("&#124;"). Blank lines are
 *       ignored. See {@link WordlistLoader#getSnowballWordSet WordlistLoader.getSnowballWordSet}
 *       for details.
 * </ul>
 *
 * @since 3.1
 * @lucene.spi {@value #NAME}
 */
public class WhiteListFilterFactory extends AbstractWordsFileFilterFactory {

    /** SPI name */
    public static final String NAME = "white";

    /** Creates a new WhiteListFilterFactory */
    public WhiteListFilterFactory(Map<String, String> args) {
        super(args);
    }

    /** Default ctor for compatibility with SPI */
 /*   public WhiteListFilterFactory() {
        throw defaultCtorException();
    }
*/
    public CharArraySet getStopWords() {
        return getWords();
    }

    @Override
    protected CharArraySet createDefaultWords() {
        return null;
        //return new CharArraySet(EnglishAnalyzer.ENGLISH_STOP_WORDS_SET, isIgnoreCase());
    }

    @Override
    public TokenStream create(TokenStream input) {
        WhiteListFilter whiteListFilter = new WhiteListFilter(input, getWords());
        return whiteListFilter;
    }
}