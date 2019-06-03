package com.tianee.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.tianee.lucene.entity.DocumentRecords;
import com.tianee.lucene.entity.SearchModel;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 全文检索Soap服务
 * 
 * @author kakalion
 * 
 */
public class TeeLuceneSoapService extends SpringBeanAutowiringSupport {
	public static final int LARGER_NUM = 10000;

	/**
	 * 外部调用 创建单个索引
	 * 
	 * @param tableName
	 * @param record
	 */
	public static void addRecord(String tableName, Map record) {
		// 创建indexWriter对象
		IndexWriter indexWriter = TeeLuceneUtil.getInstance(tableName);
		addRecord(tableName, record, indexWriter);

		// 关闭indexWriter对象
		TeeLuceneUtil.closeIndexWriter();
	}

	/**
	 * 私有 创建单个索引纪录
	 * 
	 * @param tableName
	 * @param record
	 * @return
	 */
	private static void addRecord(String tableName, Map record, IndexWriter indexWriter) {

		Document document = new Document();
		Set<String> keys = record.keySet();
		Object value = null;
		try {
			Field field = null;
			String temp = null;
			for (String key : keys) {
				value = record.get(key);// 获取到value

				if (value == null) {
					field = new TextField(key, "", Field.Store.YES);
					document.add(field);
				} else {
					// 如果不是文件类型
					if (value instanceof Integer) {
						field = new IntField(key,
								((Integer) value).intValue(), Field.Store.YES);
						document.add(field);

					} else if (value instanceof Long) {
						field = new LongField(key, (Long) value,
								Field.Store.YES);
						document.add(field);

					} else if (value instanceof Double) {
						field = new DoubleField(key, (Double) value,
								Field.Store.YES);
						document.add(field);

					} else if (value instanceof Float) {
						field = new FloatField(key, (Float) value,
								Field.Store.YES);
						document.add(field);

					} else if (value instanceof String) {
						field = new TextField(key, value.toString(),
								Field.Store.YES);
						document.add(field);

					} else {
						File file = (File) value;

						/** txt方法* */
						if (file.getName().endsWith(".txt")
								|| file.getName().endsWith(".xml")) {
							temp = TeeFileExtractUtil.FileReaderAll(
									file.getCanonicalPath(), "utf-8");
						} else
						/** word方法doc* */
						if (file.getName().endsWith(".doc")) {
							temp = TeeFileExtractUtil.WordFileReader(file
									.getCanonicalPath());
						} else
						/** word方法docx* */
						if (file.getName().endsWith(".docx")) {
							temp = TeeFileExtractUtil.WordDocxFileReader(file
									.getCanonicalPath());
						} else
						/** Excel方法xls* */
						if (file.getName().endsWith(".xls")) {
							temp = TeeFileExtractUtil.ExcelFileReader(file
									.getCanonicalPath());
						} else
						/** Excel方法xlsx* */
						if (file.getName().endsWith(".xlsx")) {
							temp = TeeFileExtractUtil.xlsxFileReader(file
									.getCanonicalPath());
						} else
						/** pdf文档* */
						if (file.getName().endsWith(".pdf")) {
							temp = TeeFileExtractUtil.PdfboxFileReader(file
									.getCanonicalPath());
						} else
						/** htm文档* */
						if (file.getName().endsWith(".htm")
								|| file.getName().endsWith(".html")) {
							temp = TeeFileExtractUtil.htmlFileReader(file
									.getCanonicalPath());
						} else
						/** ppt文件* */
						if (file.getName().endsWith(".ppt")) {
							temp = TeeFileExtractUtil.PptFileReader(file
									.getCanonicalPath());
						} else
							/** pptx文件* */
							if (file.getName().endsWith(".pptx")) {
								temp = TeeFileExtractUtil.PptxFileReader(file
										.getCanonicalPath());
							}
//                        System.out.println(temp);
					 field = new TextField(key, TeeStringUtil.getString(temp), Field.Store.YES);
						document.add(field);
					}
				}
			}
			indexWriter.addDocument(document);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 创建多个索引纪录
	 * 
	 * @param tableName
	 * @param records
	 */
	public static  void addRecords(String tableName, List<Map> records) {

		// 创建indexWriter对象
		IndexWriter indexWriter = TeeLuceneUtil.getInstance(tableName);
		for (Map map : records) {
			addRecord(tableName, map, indexWriter);
		}
		// 关闭indexWriter对象
		TeeLuceneUtil.closeIndexWriter();
	}

	
	
	/**
	 * 分页查询 单条件 多域查询
	 * 
	 * @param indexFilePath
	 * @param field
	 * @param term
	 * @return
	 * @throws ParseException
	 */
	public static DocumentRecords queryParserSearch(SearchModel model) {
		DocumentRecords records = new DocumentRecords();
		List<Map> list = new ArrayList<Map>();
		try {
			// 1、打开索引库
			Directory indexDir = FSDirectory.open(new File(TeeSysProps.getString("LUCENE_SPACE")+"/"+model.getSpace()));
			IndexReader ir = DirectoryReader.open(indexDir);
			IndexSearcher searcher = new IndexSearcher(ir);

			if (model.getDefaultSearchField().length > 0
					&& model.getDefaultSearchField() != null) {
				QueryParser parser = new MyQueryParser(
						Version.LUCENE_48, model.getDefaultSearchField(),
						new IKAnalyzer());
				parser.setDefaultOperator(QueryParser.AND_OPERATOR);
				Query query = parser.parse(model.getTerm());

				TopDocs docs = searcher.search(query, LARGER_NUM);
				// 3、遍历结果并处理
				ScoreDoc[] hits = docs.scoreDocs;
				// 查询的起始位置
				int begin = model.getPageSize() * (model.getCurPage() - 1);
				// 查询终止的位置
				int end = Math.min(begin + model.getPageSize(), hits.length);
				// 构建Scorer,用于选取最佳切片
				QueryScorer fragmentScore = new QueryScorer(query);
				// 自定义标注高亮文本标签
				SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter(
						"<font color='red'>", "</font>");
				// 实例化highlight组件
				Highlighter highlighter = new Highlighter(htmlFormatter,
						new QueryScorer(query));

				List<String> lightFieldList = new ArrayList<String>();
				// 将需要被高亮的数组中的数据存放到集合中
				for (String lf : model.getLightedField()) {
					lightFieldList.add(lf);
				}

				for (int i = begin; i < end; i++) {
					Map map = new HashMap();
					int id = hits[i].doc;
					map.put("LUCENE_DOC_ID", id);
					Document doc = searcher.doc(id);
					if (model.getReturnField() == null
							|| model.getReturnField().length <= 0) {
						List<IndexableField> fields = doc.getFields();
						for (IndexableField indexableField : fields) {
							String name = indexableField.name();
							String text = indexableField.stringValue();
							
							//返回的索引
							int indexed = fields.indexOf(indexableField);
							int wordCount = (indexed<model.getReturnFieldWordCount().length)?(model.getReturnFieldWordCount()[indexed]):(Integer.MAX_VALUE);
							
							if (lightFieldList.contains(name)) {
								// 分词流 语汇单元化
								TokenStream tokenStream = TokenSources
										.getAnyTokenStream(
												searcher.getIndexReader(), id,
												indexableField.name(),
												new IKAnalyzer());
								// 构建Fragmenter对象,用于文档切片
								Fragmenter fragmenter = new SimpleFragmenter(
										wordCount);// 默认字符为100
								highlighter.setTextFragmenter(fragmenter);
								String s = highlighter.getBestFragment(
										tokenStream, text);// 第二个参数为原始文档信息

								if (s == "" || s == null) {
									map.put(name, text.substring(0, wordCount<=text.length()?(wordCount):(text.length())));
								} else {
									map.put(name, s);
								}
							} else {
								map.put(name, text.substring(0, wordCount<=text.length()?(wordCount):(text.length())));
							}
						}
					} else {
						int index=0;
						for (String f : model.getReturnField()) {
							String text = null;
							if(doc.getField(f)==null){
								text = "";
							}else{
								text = doc.getField(f).stringValue();
							}
							
							//返回的索引
							int wordCount = (index<model.getReturnFieldWordCount().length)?(model.getReturnFieldWordCount()[index]):(Integer.MAX_VALUE);
							
							if (lightFieldList.contains(f)) {
								// 分词流 语汇单元化
								TokenStream tokenStream = TokenSources
										.getAnyTokenStream(
												searcher.getIndexReader(), id,
												f, new IKAnalyzer());
								// 构建Fragmenter对象,用于文档切片
								Fragmenter fragmenter = new SimpleFragmenter(
										wordCount);// 默认字符为100
								highlighter.setTextFragmenter(fragmenter);
								String s = highlighter.getBestFragment(
										tokenStream, text);// 第二个参数为原始文档信息

								if (s == "" || s == null) {
									map.put(f, text.substring(0, wordCount<=text.length()?(wordCount):(text.length())));
								} else {
									map.put(f, s);
								}
							} else {
								map.put(f, text.substring(0, wordCount<=text.length()?(wordCount):(text.length())));
							}
							index++;
						}
					}
					list.add(map);
				}
				records.setRecordList(list);
				records.setTotalHits(hits.length);
				ir.close();
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return records;
	}

	
	
	/**
	 * 利用indexWriter删除文档
	 * @param space
	 * @param term
	 */
	public static void deleteDocuments(String space,String field,String term){

		try {
			// 创建indexWriter对象
			IndexWriter indexWriter = TeeLuceneUtil.getInstance(space);
		    Term t=new Term(field,term);
			indexWriter.deleteDocuments(t);
		} catch (IOException e) {
			
			e.printStackTrace();
		}finally{
			TeeLuceneUtil.closeIndexWriter();
		}
	}

	public static void deleteDocuments(SearchModel model){
		try {
			IndexWriter indexWriter = TeeLuceneUtil.getInstance(model.getSpace());
			QueryParser parser = new MyQueryParser(
					Version.LUCENE_48, model.getDefaultSearchField(),
					new IKAnalyzer());
			parser.setDefaultOperator(QueryParser.AND_OPERATOR);
			Query query = parser.parse(model.getTerm());
			indexWriter.deleteDocuments(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			TeeLuceneUtil.closeIndexWriter();
		}
	}
	
	/**
	 * 重载方法
	 * @param space
	 * @param field
	 * @param term
	 */
	public static void deleteDocuments(String space,String field,String[] term){

		try {
			// 创建indexWriter对象
			IndexWriter indexWriter = TeeLuceneUtil.getInstance(space);
			for (int i = 0; i < term.length; i++) {
				 Term t=new Term(field,term[i]);
			     indexWriter.deleteDocuments(t);
			}
		   
		} catch (IOException e) {
			
			e.printStackTrace();
		}finally{
			TeeLuceneUtil.closeIndexWriter();
		}
	}
	
	
	
	
	/**
	 * 清空路径下的所有索引
	 * @param space
	 * @param field
	 * @param term
	 */
	public static void clear(String space){
		try {
			// 创建indexWriter对象
			IndexWriter indexWriter = TeeLuceneUtil.getInstance(space);
			//清空
			indexWriter.deleteAll();
		   
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			TeeLuceneUtil.closeIndexWriter();
		}
	}
	
	
	
	// /**
	// * 搜索
	// *
	// * @param indexFilePath
	// * @param term
	// * @return
	// */
	// public List<Document> search(String indexFilePath, String field, String
	// term) {
	// List<Document> list = new ArrayList<Document>();
	// try {
	// // 1、打开索引库
	// Directory indexDir = FSDirectory.open(new File(indexFilePath));
	// IndexReader ir = DirectoryReader.open(indexDir);
	// IndexSearcher searcher = new IndexSearcher(ir);
	// // 2、根据关键词进行搜索
	// TopDocs docs = searcher.search(
	// new TermQuery(new Term(field, term)),LARGER_NUM);
	// // 3、遍历结果并处理
	// ScoreDoc[] hits = docs.scoreDocs;
	// for (ScoreDoc hit : hits) {
	// Document doc = searcher.doc(hit.doc);
	// list.add(doc);
	// }
	//
	// ir.close();
	// } catch (IOException e) {
	//
	// e.printStackTrace();
	// }
	// return list;
	// }
	//
	// /**
	// * 分页查询 单条件 单域查询
	// *
	// * @param indexFilePath
	// * @param field
	// * @param term
	// * @return
	// */
	// public DocumentRecords search(String indexFilePath, String field,
	// String term, int curPage, int pageSize) {
	// DocumentRecords records = new DocumentRecords();
	// List<Map> list = new ArrayList<Map>();
	// try {
	// // 1、打开索引库
	// Directory indexDir = FSDirectory.open(new File(indexFilePath));
	// IndexReader ir = DirectoryReader.open(indexDir);
	// IndexSearcher searcher = new IndexSearcher(ir);
	// // 2、根据关键词进行搜索
	// TopDocs docs = searcher.search(
	// new TermQuery(new Term(field, term)), LARGER_NUM);
	//
	// // 3、遍历结果并处理
	// ScoreDoc[] hits = docs.scoreDocs;
	//
	// // 查询的起始位置
	// int begin = pageSize * (curPage - 1);
	// // 查询终止的位置
	// int end = Math.min(begin + pageSize, hits.length);
	//
	// for (int i = begin; i < end; i++) {
	// Document doc = searcher.doc(hits[i].doc);
	// List<IndexableField> fields = doc.getFields();
	// Map map = new HashMap();
	// for (IndexableField indexableField : fields) {
	// map.put(indexableField.name(), indexableField.stringValue());
	// //
	// System.out.println("fieldName:"+indexableField.name()+"   value:"+indexableField.stringValue());
	// }
	// list.add(map);
	// }
	//
	// records.setRecordList(list);
	// records.setTotalHits(hits.length);
	// ir.close();
	// } catch (IOException e) {
	//
	// e.printStackTrace();
	// }
	// return records;
	// }

	//
	//
	// /**
	// * 分页查询 多条件 多域查询
	// *
	// * @param indexFilePath
	// * @param field
	// * @param term
	// * @return
	// * @throws ParseException
	// */
	// public DocumentRecords search2(String indexFilePath,String field[],String
	// term[],int curPage,int pageSize) throws ParseException{
	// DocumentRecords records=new DocumentRecords();
	// List<Map> list=new ArrayList<Map>();
	// try {
	// // 1、打开索引库
	// Directory indexDir = FSDirectory.open(new File(indexFilePath));
	// IndexReader ir = DirectoryReader.open(indexDir);
	// IndexSearcher searcher = new IndexSearcher(ir);
	//
	//
	// //声明BooleanClause.Occur[]数组,它表示多个条件之间的关系
	// Occur[] flags=new Occur[]{Occur.MUST,Occur.MUST};
	// //创建分词器
	// IKAnalyzer analyzer = new IKAnalyzer();
	// //用MultiFieldQueryParser得到query对象
	// Query query = MultiFieldQueryParser.parse(Version.LUCENE_48, term, field,
	// flags, analyzer);
	//
	// TopDocs docs = searcher.search(
	// query, LARGER_NUM);
	//
	//
	// // 3、遍历结果并处理
	// ScoreDoc[] hits = docs.scoreDocs;
	//
	// //查询的起始位置
	// int begin=pageSize*(curPage-1);
	// //查询终止的位置
	// int end=Math.min(begin + pageSize, hits.length);
	//
	// for(int i=begin;i<end;i++){
	// Document doc = searcher.doc(hits[i].doc);
	// List<IndexableField> fields = doc.getFields();
	// Map map=new HashMap();
	// for (IndexableField indexableField : fields) {
	// map.put(indexableField.name(), indexableField.stringValue());
	// //System.out.println("fieldName:"+indexableField.name()+"   value:"+indexableField.stringValue());
	// }
	// list.add(map);
	// }
	//
	// records.setRecordList(list);
	// records.setTotalHits(hits.length);
	// ir.close();
	// } catch (IOException e) {
	//
	// e.printStackTrace();
	// }
	// return records;
	// }
	//
	//
	//
	// /**
	// *
	// * Wildcard Searches
	// * @param indexFilePath
	// * @param field
	// * @param term
	// * @return
	// * @throws ParseException
	// */
	// public DocumentRecords wildCardSearch(String indexFilePath,String
	// field,String term,int curPage,int pageSize) throws ParseException{
	// DocumentRecords records = new DocumentRecords();
	// List<Map> list = new ArrayList<Map>();
	// try {
	// // 1、打开索引库
	// Directory indexDir = FSDirectory.open(new File(indexFilePath));
	// IndexReader ir = DirectoryReader.open(indexDir);
	// IndexSearcher searcher = new IndexSearcher(ir);
	//
	// //创建wildCardQuery对象
	// WildcardQuery query = new WildcardQuery(new Term(field, term));
	// // 2、根据关键词进行搜索
	// TopDocs docs = searcher.search(query, LARGER_NUM);
	//
	// // 3、遍历结果并处理
	// ScoreDoc[] hits = docs.scoreDocs;
	//
	// // 查询的起始位置
	// int begin = pageSize * (curPage - 1);
	// // 查询终止的位置
	// int end = Math.min(begin + pageSize, hits.length);
	//
	// for (int i = begin; i < end; i++) {
	// Document doc = searcher.doc(hits[i].doc);
	// List<IndexableField> fields = doc.getFields();
	// Map map = new HashMap();
	// for (IndexableField indexableField : fields) {
	// map.put(indexableField.name(), indexableField.stringValue());
	// //
	// System.out.println("fieldName:"+indexableField.name()+"   value:"+indexableField.stringValue());
	// }
	// list.add(map);
	// }
	//
	// records.setRecordList(list);
	// records.setTotalHits(hits.length);
	// ir.close();
	// } catch (IOException e) {
	//
	// e.printStackTrace();
	// }
	// return records;
	// }
	//
	//
	//
	// /**
	// *
	// * Fuzzy Searches
	// * @param indexFilePath
	// * @param field
	// * @param term
	// * @return
	// * @throws ParseException
	// */
	// public DocumentRecords fuzzySearch(String indexFilePath,String
	// field,String term,int curPage,int pageSize) throws ParseException{
	// DocumentRecords records = new DocumentRecords();
	// List<Map> list = new ArrayList<Map>();
	// try {
	// // 1、打开索引库
	// Directory indexDir = FSDirectory.open(new File(indexFilePath));
	// IndexReader ir = DirectoryReader.open(indexDir);
	// IndexSearcher searcher = new IndexSearcher(ir);
	//
	// //创建FuzzyQuery对象
	// FuzzyQuery query = new FuzzyQuery(new Term(field, term),2);
	// // 2、根据关键词进行搜索
	// TopDocs docs = searcher.search(query, LARGER_NUM);
	//
	// // 3、遍历结果并处理
	// ScoreDoc[] hits = docs.scoreDocs;
	//
	// // 查询的起始位置
	// int begin = pageSize * (curPage - 1);
	// // 查询终止的位置
	// int end = Math.min(begin + pageSize, hits.length);
	//
	// for (int i = begin; i < end; i++) {
	// Document doc = searcher.doc(hits[i].doc);
	// List<IndexableField> fields = doc.getFields();
	// Map map = new HashMap();
	// for (IndexableField indexableField : fields) {
	// map.put(indexableField.name(), indexableField.stringValue());
	// //
	// System.out.println("fieldName:"+indexableField.name()+"   value:"+indexableField.stringValue());
	// }
	// list.add(map);
	// }
	//
	// records.setRecordList(list);
	// records.setTotalHits(hits.length);
	// ir.close();
	// } catch (IOException e) {
	//
	// e.printStackTrace();
	// }
	// return records;
	// }
	//
	//
	// /**
	// *
	// * prefix Searches
	// * @param indexFilePath
	// * @param field
	// * @param term
	// * @return
	// * @throws ParseException
	// */
	// public DocumentRecords prefixSearch(String indexFilePath,String
	// field,String term,int curPage,int pageSize) throws ParseException{
	// DocumentRecords records = new DocumentRecords();
	// List<Map> list = new ArrayList<Map>();
	// try {
	// // 1、打开索引库
	// Directory indexDir = FSDirectory.open(new File(indexFilePath));
	// IndexReader ir = DirectoryReader.open(indexDir);
	// IndexSearcher searcher = new IndexSearcher(ir);
	//
	// //创建PrefixQuery对象
	// PrefixQuery query = new PrefixQuery(new Term(field, term));
	// // 2、根据关键词进行搜索
	// TopDocs docs = searcher.search(query, LARGER_NUM);
	//
	// // 3、遍历结果并处理
	// ScoreDoc[] hits = docs.scoreDocs;
	//
	// // 查询的起始位置
	// int begin = pageSize * (curPage - 1);
	// // 查询终止的位置
	// int end = Math.min(begin + pageSize, hits.length);
	//
	// for (int i = begin; i < end; i++) {
	// Document doc = searcher.doc(hits[i].doc);
	// List<IndexableField> fields = doc.getFields();
	// Map map = new HashMap();
	// for (IndexableField indexableField : fields) {
	// map.put(indexableField.name(), indexableField.stringValue());
	// //
	// System.out.println("fieldName:"+indexableField.name()+"   value:"+indexableField.stringValue());
	// }
	// list.add(map);
	// }
	//
	// records.setRecordList(list);
	// records.setTotalHits(hits.length);
	// ir.close();
	// } catch (IOException e) {
	//
	// e.printStackTrace();
	// }
	// return records;
	// }
	//
	//
	//
	//
	// /**
	// *
	// * range Searches
	// * @param indexFilePath
	// * @param field
	// * @param term
	// * @return
	// * @throws ParseException
	// */
	// public DocumentRecords rangeSearch(String indexFilePath,String
	// field,String term,int curPage,int pageSize) throws ParseException{
	// DocumentRecords records = new DocumentRecords();
	// List<Map> list = new ArrayList<Map>();
	// try {
	// // 1、打开索引库
	// Directory indexDir = FSDirectory.open(new File(indexFilePath));
	// IndexReader ir = DirectoryReader.open(indexDir);
	// IndexSearcher searcher = new IndexSearcher(ir);
	//
	// //创建RangeQuery对象
	// NumericRangeQuery query=NumericRangeQuery.newIntRange(field,1, 100, true,
	// true);
	// // 2、根据关键词进行搜索
	// TopDocs docs = searcher.search(query, LARGER_NUM);
	//
	// // 3、遍历结果并处理
	// ScoreDoc[] hits = docs.scoreDocs;
	//
	// // 查询的起始位置
	// int begin = pageSize * (curPage - 1);
	// // 查询终止的位置
	// int end = Math.min(begin + pageSize, hits.length);
	//
	// for (int i = begin; i < end; i++) {
	// Document doc = searcher.doc(hits[i].doc);
	// List<IndexableField> fields = doc.getFields();
	// Map map = new HashMap();
	// for (IndexableField indexableField : fields) {
	// map.put(indexableField.name(), indexableField.stringValue());
	// //
	// System.out.println("fieldName:"+indexableField.name()+"   value:"+indexableField.stringValue());
	// }
	// list.add(map);
	// }
	//
	// records.setRecordList(list);
	// records.setTotalHits(hits.length);
	// ir.close();
	// } catch (IOException e) {
	//
	// e.printStackTrace();
	// }
	// return records;
	// }
}
