package com.tianee.webframe.util.lucene;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 全文检索类
 * @author kakalion
 *
 */
public class TeeLuceneHelper {
	
	/**
	 * 添加文档记录
	 * @param store
	 * @param field
	 */
	public static void addDocument(String store,Map<String,String> field){
		Document document = new Document();
		Set<String> ketSet = field.keySet();
		for(String key : ketSet){
			document.add(new Field(key.toUpperCase(),field.get(key),Field.Store.YES, Field.Index.ANALYZED));
		}
		IndexWriter writer=null;
		try {
			Directory directory= FSDirectory.open(new File(getContextPath()+store));
			writer=new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_CURRENT,new IKAnalyzer()));
			writer.addDocument(document);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除文档记录
	 * @param store
	 * @param id
	 */
	public static void deleteDocument(String store,String idKey,String idValue){
		Document document = new Document();
		IndexWriter writer=null;
		try {
			Directory directory= FSDirectory.open(new File(getContextPath()+store));
			writer=new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_CURRENT,new IKAnalyzer()));
			Term term = new Term(idKey, idValue);
			writer.deleteDocuments(term);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取lucene上下文路径
	 * @return
	 */
	public static String getContextPath(){
		return "d:\\lucene\\";
	}
}
