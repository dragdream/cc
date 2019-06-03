package com.tianee.oa.core.lucene;

//1、打开索引库IndexSearcher
//2、根据关键词进行搜索
//3、遍历结果并处理
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class TeeLuceneSearcher {
	/**
	 * 搜索实现
	 * @param args
	 * @throws IOException
	 */
	public String search(String indexPath,String searchContent,int pageSize ,int curPage) throws IOException {
		// 1、打开索引库
		StringBuffer sb = new StringBuffer();  
		StringBuffer sbs = new StringBuffer("{"); 
		Directory indexDir = FSDirectory.open(new File(indexPath));
		IndexReader ir = DirectoryReader.open(indexDir);
		IndexSearcher searcher = new IndexSearcher(ir);
		QueryParser qp = new QueryParser(Version.LUCENE_48, "contents", getAnalyzer());
		qp.setDefaultOperator(QueryParser.AND_OPERATOR);
		Query query;
		try {
			// 2、根据关键词进行搜索
			query = qp.parse(searchContent);
			TopDocs docs = searcher.search(query, Integer.MAX_VALUE);
			// 3、遍历结果并处理
			ScoreDoc[] hits = docs.scoreDocs;
			
			SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color='red'>", "</font></b>");
			Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
			highlighter.setTextFragmenter(new SimpleFragmenter(Integer.MAX_VALUE));
			 //查询起始记录位置
	        int begin = pageSize * (curPage - 1);
	        //查询终止记录位置
	        int end = Math.min(begin + pageSize, hits.length);
	        
			//for (ScoreDoc hit : hits) {
	        for(int i=begin;i<end;i++) {
				 Document hittedDocument = searcher.doc(hits[i].doc);
				 String abstractsField = hittedDocument.get("abstracts");
				 String path =hittedDocument.get("path");
				 String title=hittedDocument.get("title");
				 String time = hittedDocument.get("time");
				 if (path != null) { 
						try {
							TokenStream tokenStream = getAnalyzer().tokenStream("abstracts", new StringReader(abstractsField));      
							String str = highlighter.getBestFragment(tokenStream, abstractsField);
							if(null==str){
								str="";
							}
							TokenStream tokenStream2 = getAnalyzer().tokenStream("title", new StringReader(title));      
							String str2 = highlighter.getBestFragment(tokenStream2, title);
							if(null==str2){
								str2=title;
							}
							String url = "<div style='width:80%;margin:0 auto;'><span style='width:70%;float:left;'><a target='_blank' href='"+path+"'>"+str2+"</a></span><span style='width:30%;float:right;text-align:right;'>"+time+"</span></div>";
							url+="<div style='width:80%;margin:0 auto;text-align:left;'>摘要："+str+"</div>";
							sb.append("<li><li>").append(url);      
						} catch (InvalidTokenOffsetsException e) {
							e.printStackTrace();
						}      
	                } 
			}
	        int pageNums =  hits.length%pageSize==0?hits.length/pageSize:hits.length/pageSize+1;
	        sbs.append("\"pageNums\":"+pageNums+",\"docList\":\""+sb.toString()+"\"}");
		} catch (ParseException e) {
			e.printStackTrace();
		}finally{
			ir.close();
		}
		return sbs.toString();
	}
	
    public synchronized Analyzer getAnalyzer() {      
        return new IKAnalyzer(true);// 此处使用"庖丁解牛"分词法，另外一种是中科院分词法      
    } 

}
