package com.tianee.test;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class LuceneTest {

	public static void main(String[] args) throws Exception {
		//数字 日期 
		for(int i=0;i<100;i++){
			new Test().start();
		}
		 
	}
	

}

class Test extends Thread{
	public void run(){
		 Document document=new Document();
		 document.add(new Field("name","wocao",Field.Store.YES, Field.Index.ANALYZED));
		 IndexWriter writer=null;
		 Directory directory;
		try {
			directory = FSDirectory.open(new File("d:\\lucene"));
			IndexWriterConfig config =  new IndexWriterConfig(Version.LUCENE_42,  
	                new IKAnalyzer());
			 config.setOpenMode(OpenMode.CREATE_OR_APPEND);
			 config.setWriteLockTimeout(100000);
			 writer=new IndexWriter(directory, config);
			 writer.addDocument(document);
			 writer.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
