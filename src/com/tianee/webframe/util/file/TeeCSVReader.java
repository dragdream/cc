package com.tianee.webframe.util.file;
/**
 Copyright 2005 Bytecode Pty Ltd.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * A very simple CSV reader released under a commercial-friendly license.
 * 
 * @author syl
 * 
 */
public class TeeCSVReader implements Closeable {

    private BufferedReader br;

    private boolean hasNext = true;

    private final char separator;

    private final char quotechar;
    
    private final char escape;
    
    private int skipLines;

    private boolean linesSkiped;

    /** 使用默认的分隔符，如果没有向构造函数提供。 */
    public static final char DEFAULT_SEPARATOR = ',';

    public static final int INITIAL_READ_SIZE = 64;

    /**
     *默认引号字符使用，如果没有提供给
     *构造函数。
     */
    public static final char DEFAULT_QUOTE_CHARACTER = '"';
    

    /**
     *如果没有提供给使用缺省的转义字符
     *构造函数
     */
    public static final char DEFAULT_ESCAPE_CHARACTER = '\\';
    
    /**
     * 默认的行开始阅读.
     */
    public static final int DEFAULT_SKIP_LINES = 0;

    /**
     * 构造TeeCSVReader使用逗号为分隔符
     * 
     * @param reader  读底层的CSV源
     *         
     */
    public TeeCSVReader(Reader reader) {
        this(reader, DEFAULT_SEPARATOR);
    }

    /**
     * 构造CSVReader提供的分隔符。
     * 
     * @param reader
     *            读底层的CSV源
     * @param separator
     *            分隔符，用于分离项目。
     */
    public TeeCSVReader(Reader reader, char separator) {
        this(reader, separator, DEFAULT_QUOTE_CHARACTER, DEFAULT_ESCAPE_CHARACTER);
    }
    
    

    /**
     * 构造CSVReader提供的分隔符和引号字符。
     * 
     * @param reader
     *            读底层的CSV源
     * @param separator
     *             分隔符，用于分离项目。
     * @param quotechar
     *           要使用的字符报价元素
     */
    public TeeCSVReader(Reader reader, char separator, char quotechar) {
        this(reader, separator, quotechar, DEFAULT_ESCAPE_CHARACTER, DEFAULT_SKIP_LINES);
    }

    public TeeCSVReader(Reader reader, char separator,
      char quotechar, char escape) {
        this(reader, separator, quotechar, escape, DEFAULT_SKIP_LINES);
  }
    
    /**
     * 构造CSVReader提供的分隔符和引号字符。
     * 
     * @param reader
     *            the reader to an underlying CSV source.
     * @param separator
     *            the delimiter to use for separating entries
     * @param quotechar
     *            the character to use for quoted elements
     * @param line
     *            the line number to skip for start reading 
     */
    public TeeCSVReader(Reader reader, char separator, char quotechar, int line) {
        this(reader, separator, quotechar, DEFAULT_ESCAPE_CHARACTER, line);
    }
    
    /**
     * 构造CSVReader提供的分隔符和引号字符。
     * 
     * @param reader
     *            the reader to an underlying CSV source.
     * @param separator
     *            the delimiter to use for separating entries
     * @param quotechar
     *            the character to use for quoted elements
     * @param escape
     *            the character to use for escaping a separator or quote
     * @param line
     *            the line number to skip for start reading 
     */
    public TeeCSVReader(Reader reader, char separator, char quotechar, char escape, int line) {
        this.br = new BufferedReader(reader);
        this.separator = separator;
        this.quotechar = quotechar;
        this.escape = escape;
        this.skipLines = line;
    }


  /**
     * 读取整个文件到一个列表，其中每个元素的String []数组记号。
     * 
     * @return a List of String[], with each String[] representing a line of the
     *         file.
     * 
     * @throws IOException
     *             if bad things happen during the read
     */
    public List<String[]> readAll() throws IOException {

        List<String[]> allElements = new ArrayList<String[]>();
        while (hasNext) {
            String[] nextLineAsTokens = readNext();
            if (nextLineAsTokens != null)
                allElements.add(nextLineAsTokens);
        }
        return allElements;

    }

    /**
     * 从缓冲区读取下一行，并转换成一个字符串数组。
     * 
     * @return a string array with each comma-separated element as a separate
     *         entry.
     * 
     * @throws IOException
     *             if bad things happen during the read
     */
    public String[] readNext() throws IOException {

        String nextLine = getNextLine();
        return hasNext ? parseLine(nextLine) : null;
    }
    /**
     * 从文件中读取下一行
     * 
     * @return the next line from the file without trailing newline
     * @throws IOException
     *             if bad things happen during the read
     */
    private String getNextLine() throws IOException {
      if (!this.linesSkiped) {
            for (int i = 0; i < skipLines; i++) {
                br.readLine();
            }
            this.linesSkiped = true;
        }
        String nextLine = br.readLine();
        if (nextLine == null) {
            hasNext = false;
        }
        return hasNext ? nextLine : null;
    }

    /**
     * 解析传入的字符串，并返回一个数组中的元素.
     * 
     * @param nextLine
     *            the string to parse
     * @return the comma-tokenized list of elements, or null if nextLine is null
     * @throws IOException if bad things happen during the read
     */
    private String[] parseLine(String nextLine) throws IOException {

        if (nextLine == null) {
            return null;
        }

        List<String>tokensOnThisLine = new ArrayList<String>();
        StringBuilder sb = new StringBuilder(INITIAL_READ_SIZE);
        boolean inQuotes = false;
        do {
          if (inQuotes) {
                // 持续的引用部分 ，换行符

                sb.append("\n");
                nextLine = getNextLine();
                if (nextLine == null)
                    break;
            }
            for (int i = 0; i < nextLine.length(); i++) {

                char c = nextLine.charAt(i);
                if (c == this.escape) {
                  if( isEscapable(nextLine, inQuotes, i) ){ 
                    sb.append(nextLine.charAt(i+1));
                    i++;
                  } 
                } else if (c == quotechar) {
                  if( isEscapedQuote(nextLine, inQuotes, i) ){ 
                    sb.append(nextLine.charAt(i+1));
                    i++;
                  }else{
                    inQuotes = !inQuotes;
                    // the tricky case of an embedded quote in the middle: a,bc"d"ef,g
                    if(i>2 //不上开头的行
                        && nextLine.charAt(i-1) != this.separator //不是一个转义序列的开头
                        && nextLine.length()>(i+1) &&
                        nextLine.charAt(i+1) != this.separator //不是结束一个转义序列
                    ){
                      sb.append(c);
                    }
                  }
                } else if (c == separator && !inQuotes) {
                    tokensOnThisLine.add(sb.toString());
                    sb = new StringBuilder(INITIAL_READ_SIZE); //启动下一个标记工作
                } else {
                    sb.append(c);
                }
            }
        } while (inQuotes);
        tokensOnThisLine.add(sb.toString());
        return tokensOnThisLine.toArray(new String[0]);

    }

  /**  
   * 前提条件：当前字符的引用
   * @param nextLine the current line
   * @param inQuotes true if the current context is quoted
   * @param i current index in line
   * @return true if the following character is a quote
   */
  private boolean isEscapedQuote(String nextLine, boolean inQuotes, int i) {
    return inQuotes  // we are in quotes, therefore there can be escaped quotes in here.
        && nextLine.length() > (i+1)  // there is indeed another character to check.
        && nextLine.charAt(i+1) == quotechar;
  }

  /**  
   * 前提：当前字符转
   * @param nextLine the current line
   * @param inQuotes true if the current context is quoted
   * @param i current index in line
   * @return true if the following character is a quote
   */
  private boolean isEscapable(String nextLine, boolean inQuotes, int i) {
    return inQuotes  // we are in quotes, therefore there can be escaped quotes in here.
        && nextLine.length() > (i+1)  // there is indeed another character to check.
        && ( nextLine.charAt(i+1) == quotechar || nextLine.charAt(i+1) == this.escape);
  }

    /**
     * 关闭
     * 
     * @throws IOException if the close fails
     */
    public void close() throws IOException{
      br.close();
    }
    
}
