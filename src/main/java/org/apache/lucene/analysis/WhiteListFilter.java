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
package org.apache.lucene.analysis;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/** Keeps white words from a token stream. */
public class WhiteListFilter extends FilteringTokenFilter {

  private final CharArraySet whiteWords;
  private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

  /**
   * Constructs a filter which keeps words from the input TokenStream that are named in the Set.
   *
   * @param in Input stream
   * @param whiteWords A {@link CharArraySet} representing the white words.
   * @see #makeStopSet(java.lang.String...)
   */
  public WhiteListFilter(TokenStream in, CharArraySet whiteWords) {
    super(in);
    this.whiteWords = Objects.requireNonNull(whiteWords, "whiteWords");
  }

  /**
   * Builds a Set from an array of white words, appropriate for passing into the WhiteListFilter
   * constructor. This permits this white Words construction to be cached once when an Analyzer is
   * constructed.
   *
   * @param whiteWords An array of white words
   * @see #makeStopSet(java.lang.String[], boolean) passing false to ignoreCase
   */
  public static CharArraySet makeStopSet(String... whiteWords) {
    return makeStopSet(whiteWords, false);
  }

  /**
   * Builds a Set from an array of white words, appropriate for passing into the WhiteListFilter
   * constructor. This permits this white Words construction to be cached once when an Analyzer is
   * constructed.
   *
   * @param whiteWords A List of Strings or char[] or any other toString()-able list representing the
   *     white words
   * @return A Set ({@link CharArraySet}) containing the words
   * @see #makeStopSet(java.lang.String[], boolean) passing false to ignoreCase
   */
  public static CharArraySet makeStopSet(List<?> whiteWords) {
    return makeStopSet(whiteWords, false);
  }

  /**
   * Creates a whiteWords set from the given whiteWords array.
   *
   * @param whiteWords An array of whiteWords
   * @param ignoreCase If true, all words are lower cased first.
   * @return a Set containing the words
   */
  public static CharArraySet makeStopSet(String[] whiteWords, boolean ignoreCase) {
    return makeStopSet(Arrays.asList(Objects.requireNonNull(whiteWords, "stopWords")), ignoreCase);
  }

  /**
   * Creates a whiteword set from the given white word list.
   *
   * @param whiteWords A List of Strings or char[] or any other toString()-able list representing the
   *     whiteWords
   * @param ignoreCase if true, all words are lower cased first
   * @return A Set ({@link CharArraySet}) containing the words
   */
  public static CharArraySet makeStopSet(List<?> whiteWords, boolean ignoreCase) {
    Objects.requireNonNull(whiteWords, "stopWords");
    CharArraySet whiteSet = new CharArraySet(whiteWords.size(), ignoreCase);
    whiteSet.addAll(whiteWords);
    return whiteSet;
  }

  /** Returns the next input Token whose term() is a white word. */
  @Override
  protected boolean accept() {
    return whiteWords.contains(termAtt.buffer(), 0, termAtt.length());
  }
}