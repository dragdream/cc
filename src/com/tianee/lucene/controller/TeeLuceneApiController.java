package com.tianee.lucene.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.lucene.TeeLuceneSoapService;
import com.tianee.lucene.entity.DocumentRecords;
import com.tianee.lucene.entity.SearchModel;
import com.tianee.webframe.util.global.TeeSysProps;


/**
 * Http开放性全文检索接口
 * @author kakalion
 *
 */

@Controller
@RequestMapping("/luceneApi")
public class TeeLuceneApiController {
	
	/**
	 * 检索数据
	 * @param key 数据交换密钥
	 * @param searchModel 查询模型
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryParserSearch")
	public DocumentRecords queryParserSearch(String key,@RequestBody SearchModel searchModel){
		if(!TeeSysProps.getString("LUCENE_KEY").equals(key)){
			return null;
		}
		TeeLuceneSoapService luceneSoapService = new TeeLuceneSoapService();
		DocumentRecords result = luceneSoapService.queryParserSearch(searchModel);
		return result;
	}
	
	
	/**
	 * 添加一条检索记录
	 * @param key 密钥
	 * @param tableName 表名称
	 * @param record 数据实体
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addRecord")
	public boolean addRecord(String key,String tableName,@RequestBody Map record){
		TeeLuceneSoapService luceneSoapService = new TeeLuceneSoapService();
		try{
			luceneSoapService.addRecord(tableName, record);
		}catch(Exception ex){
			return false;
		}
		return true;
	}
	
	/**
	 * 添加多条检索记录
	 * @param key 密钥
	 * @param tableName 表名称
	 * @param record 数据实体
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addRecords")
	public boolean addRecords(String key,String tableName,@RequestBody List<Map> record){
		if(!TeeSysProps.getString("LUCENE_KEY").equals(key)){
			return false;
		}
		TeeLuceneSoapService luceneSoapService = new TeeLuceneSoapService();
		try{
		luceneSoapService.addRecords(tableName, record);
		}catch(Exception ex){
			return false;
		}
		return true;
	}
	
	
	/**
	 * 删除文档
	 * @param key  密钥
	 * @param space 路径
	 * @param field 字段
	 * @param term 词条
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteDocuments")
	public boolean deleteDocuments(String key,String space,String field,String term){
		if(!TeeSysProps.getString("LUCENE_KEY").equals(key)){
			return false;
		}
		TeeLuceneSoapService luceneSoapService = new TeeLuceneSoapService();
		try{
		luceneSoapService.deleteDocuments(space, field, term);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	
	/**
	 * 删除文档
	 * @param key  密钥
	 * @param space 路径
	 * @param field 字段
	 * @param term 词条数组
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteDocumentsBatch")
	public boolean deleteDocumentsBatch(String key,String space,String field,@RequestBody List<String> term){
		if(!TeeSysProps.getString("LUCENE_KEY").equals(key)){
			return false;
		}
		TeeLuceneSoapService luceneSoapService = new TeeLuceneSoapService();
		try{
			if(term.size()>0){
				String t[]=new String[term.size()];
				for(int i=0,j=term.size();i<j;i++){
				    t[i]=term.get(i);
				}
				luceneSoapService.deleteDocuments(space, field,t);	
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	/**
	 * 删除文档
	 * @param key  密钥
	 * @param space 路径
	 * @param field 字段
	 * @param term 词条
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/clear")
	public boolean clear(String key,String space){
		if(!TeeSysProps.getString("LUCENE_KEY").equals(key)){
			return false;
		}
		TeeLuceneSoapService luceneSoapService = new TeeLuceneSoapService();
		try{
			luceneSoapService.clear(space);
		}catch(Exception e){
			return false;
		}
		return true;
	}
}
