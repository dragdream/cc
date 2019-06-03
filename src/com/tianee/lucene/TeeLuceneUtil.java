package com.tianee.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.tianee.webframe.util.global.TeeSysProps;

public class TeeLuceneUtil {
	private static IndexWriter indexWriter = null;

	// 私有化构造
	private TeeLuceneUtil() {
	}

	// 创建IndexWriter对象
	public static IndexWriter getInstance(String tableName) {
		try {
			if (indexWriter == null) {
				/* 放索引文件的位置 */
				Directory indexDir = FSDirectory.open(new File(TeeSysProps.getString("LUCENE_SPACE")+"/"+tableName));
				Analyzer analyzer = new IKAnalyzer();// 创建一个Analyzer的一个接口的实例StandardAnalyzer（分词器）
				IndexWriterConfig iwc = new IndexWriterConfig(
						Version.LUCENE_48, new IKAnalyzer());
				indexWriter = new IndexWriter(indexDir, iwc);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      return indexWriter;
	}
	
	
	//关闭IndexWriter对象
	public static void closeIndexWriter(){
		try {
			if(indexWriter!=null){
				indexWriter.close();	
				indexWriter=null;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
