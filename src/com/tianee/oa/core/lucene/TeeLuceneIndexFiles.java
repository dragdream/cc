package com.tianee.oa.core.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

// 1、创建索引库IndexWriter
// 2、根据文件创建文档Document
// 3、向索引库中写入文档内容

public class TeeLuceneIndexFiles {
	public static void main(String[] args){
		TeeLuceneIndexFiles indexFiles = new TeeLuceneIndexFiles();
		try {
			indexFiles.createIndex("D:\\lucene\\index", "D:\\test");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void createIndex(String indexPath,String docsPath) throws IOException {
		
		final File docDir = new File(docsPath);
		if (!docDir.exists() || !docDir.canRead()) {
			System.out.println("Document directory '"+ docDir.getAbsolutePath()+ "' does not exist or is not readable, please check the path");
		}
		IndexWriter writer = null;
		try {
			// 1、创建索引库IndexWriter
			writer = getIndexWriter(indexPath);
			index(writer, docDir);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(null!=writer){
				writer.close();
			}
		}
	}

	private static IndexWriter getIndexWriter(String indexPath)throws IOException {
		Directory indexDir = FSDirectory.open(new File(indexPath));
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_48,new StandardAnalyzer(Version.LUCENE_48));
		iwc.setOpenMode( OpenMode.CREATE_OR_APPEND );
		IndexWriter writer = new IndexWriter(indexDir, iwc);
		writer.deleteAll();
		return writer;
	}

	private static void index(IndexWriter writer, File file) throws IOException {
		if (file.isDirectory()) {
			String[] files = file.list();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					index(writer, new File(file, files[i]));
				}
			}
		} else {
			// 2、根据文件创建文档Document
			if(file.getName().endsWith(".html")){
				Document doc = new Document();
				Field pathField = new StringField("path", file.getPath(),Field.Store.YES);
				Field titleField = new StringField("title", file.getName(), Field.Store.YES); 
				
				String temp = FileReaderAll(file.getCanonicalPath(), "UTF-8");
				
				doc.add(pathField);
				doc.add(titleField);
				doc.add(new LongField("modified", file.lastModified(),Field.Store.NO));
				doc.add(new TextField("contents", temp, Field.Store.YES));
				System.out.println("Indexing " + file.getName());
				// 3、向索引库中写入文档内容
				Analyzer analyzer = new IKAnalyzer();
				writer.addDocument(doc,analyzer);
				writer.forceMerge(1);
			}
		}

	}
	
	public static String FileReaderAll(String FileName, String charset)throws IOException {      
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(FileName), charset));      
        String line = new String();      
        String temp = new String();      
        while ((line = reader.readLine()) != null) {   
            temp += line;      
        }      
        reader.close();      
        return temp;      
    } 

}
