package com.tianee.lucene;


import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.tianee.lucene.entity.DocumentRecords;
import com.tianee.lucene.entity.SearchModel;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeKeyWordUtil;

public class TeeLuceneApiExecutor {
	private String url = null;
	private String key = null;
	
	public TeeLuceneApiExecutor(String url,String key){
		this.url = url;
		this.key = key;
	}
	
	/**
	 * 查找
	 * @param url
	 * @param model
	 * @return
	 */
	public DocumentRecords queryParserSearch(SearchModel model){
		
		DocumentRecords record=new DocumentRecords();
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url+"/luceneApi/queryParserSearch.action?key="+key);
		
		post.addHeader("Content-type", "application/json; charset=utf-8");
		post.setHeader("Accept", "application/json");
		
		String encodeStr = new String(JSONObject.fromObject(model).toString());
		StringEntity s = new StringEntity(encodeStr,Charset.forName("UTF-8"));
		s.setContentEncoding("UTF-8");
		s.setContentType("application/json;charset=utf-8");// 发送json数据需要设置contentType
		post.setEntity(s);
		try {
			HttpResponse res = client.execute(post);
			
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				String result = EntityUtils.toString(entity);// 返回json格式：
				
				//将json字符串里面的数据转成对象DocumentRecord对象
				JSONObject object=JSONObject.fromObject(result);
				record=(DocumentRecords) JSONObject.toBean(object, DocumentRecords.class);
			    
				List<Map> list=record.getRecordList();
				//重新封装Map
				List<Map>newlist=new ArrayList<Map>();
				for(int i=0;i<list.size();i++){
					Map<String,String> map=new HashMap<String,String>();
					JSONObject o=JSONObject.fromObject(list.get(i));
					map=(Map<String, String>) JSONObject.toBean(o, java.util.Map.class);
					newlist.add(map);
				 }
		        record.setRecordList(newlist);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
			
		return record;
	}
	
	
	/**
	 * 添加索引
	 * @param url http://127.0.0.1
	 * @param tableName
	 * @param record
	 */
	public boolean addRecord(String tableName,Map record){
		String result="";	
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url+"/luceneApi/addRecord.action?key="+key+"&tableName="+tableName);
		
		post.addHeader("Content-type", "application/json; charset=utf-8");
		post.setHeader("Accept", "application/json");
		
		String encodeStr = new String(JSONObject.fromObject(record).toString());
		StringEntity s = new StringEntity(encodeStr,Charset.forName("UTF-8"));
		s.setContentEncoding("UTF-8");
		s.setContentType("application/json;charset=utf-8");// 发送json数据需要设置contentType
		post.setEntity(s);
		
		try {
			HttpResponse res = client.execute(post);
			
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				result = EntityUtils.toString(entity);// 返回json格式：
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			return false;
		} 
		
		if("false".equals(result)){
			return false;
		}else{
			return true;
		}
		
	}
	
	
	
	/**
	 * 添加索引
	 * @param url
	 * @param tableName
	 * @param record
	 */
	public boolean addRecords(String tableName,List<Map> record){
		String result="";	
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url+"/luceneApi/addRecords.action?key="+key+"&tableName="+tableName);
		
		post.addHeader("Content-type", "application/json; charset=utf-8");
		post.setHeader("Accept", "application/json");
		
		String encodeStr = new String(JSONArray.fromObject((List<Map>) record).toString());
		StringEntity s = new StringEntity(encodeStr,Charset.forName("UTF-8"));
		s.setContentEncoding("UTF-8");
		s.setContentType("application/json;charset=utf-8");// 发送json数据需要设置contentType
		post.setEntity(s);
		
		try {
			HttpResponse res = client.execute(post);
			
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				result = EntityUtils.toString(entity);// 返回json格式：
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			return false;
		} 
		
		if("false".equals(result)){
			return false;
		}else{
			return true;
		}	
	}
	
	
	
	/**
	 * 删除文档
	 * @param url
	 * @return
	 */
	public boolean deleteDocuments(String space,String field,String term){
		String result="";	
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url+"/luceneApi/deleteDocuments.action?key="+key+"&space="+space+"&field="+field+"&term="+term);
		
		post.addHeader("Content-type", "text/html; charset=utf-8");
		post.setHeader("Accept", "text/html");
		
		
		try {
			HttpResponse res = client.execute(post);
			
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				result = EntityUtils.toString(entity);// 返回json格式：
				
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			return false;
		} 
		
		if("false".equals(result)){
			return false;
		}else{
			return true;
		}	
	}
	
	
	
	/**
	 * 删除文档
	 * @param url
	 * @return
	 */
	public boolean deleteDocumentsBatch(String space,String field,String term[]){
		String result="";	
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url+"/luceneApi/deleteDocumentsBatch.action?key="+key+"&space="+space+"&field="+field);
		
		post.addHeader("Content-type", "application/json; charset=utf-8");
		post.setHeader("Accept", "application/json");
		
		String encodeStr = new String(JSONArray.fromObject(term).toString());
		StringEntity s = new StringEntity(encodeStr,Charset.forName("UTF-8"));
		s.setContentEncoding("UTF-8");
		s.setContentType("application/json;charset=utf-8");// 发送json数据需要设置contentType
		post.setEntity(s);
	
		try {
			HttpResponse res = client.execute(post);
			
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				result = EntityUtils.toString(entity);// 返回json格式：	
				//System.out.println(result);
			}
		} catch (Exception e) {			
			e.printStackTrace();
			return false;
		} 
		
		if("false".equals(result)){
			return false;
		}else{
			return true;
		}
	}
	
	
	
	/**
	 * 清空索引
	 * @param url
	 * @return
	 */
	public boolean clear(String space){
		String result="";	
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url+"/luceneApi/clear.action?key="+key+"&space="+space);
		
		post.addHeader("Content-type", "text/html; charset=utf-8");
		post.setHeader("Accept", "text/html");
		
		
		try {
			HttpResponse res = client.execute(post);
			
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				result = EntityUtils.toString(entity);// 返回json格式：
				
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			return false;
		} 
		
		if("false".equals(result)){
			return false;
		}else{
			return true;
		}	
	}
	
	
	public static void main(String[] args) {
		TeeSysProps.setProps(new Properties());
		TeeSysProps.getProps().setProperty("LUCENE_SPACE", "d:\\lucene");
		TeeLuceneSoapService luceneSoapService = new TeeLuceneSoapService();
		
//		Map record = new HashMap();
//		record.put("title", "中华人民共和国国歌8");
//		record.put("content", TeeKeyWordUtil.keyWord);
//		record.put("count", 14);
//		luceneSoapService.addRecord("test", record);
		
		SearchModel model = new SearchModel();
		model.setSpace("test");
		model.setCurPage(1);
		model.setPageSize(10);
		model.setTerm("count|int:[13 TO 10000]");
		DocumentRecords records = luceneSoapService.queryParserSearch(model);
		System.out.println(records.getRecordList());
	}
	
}
