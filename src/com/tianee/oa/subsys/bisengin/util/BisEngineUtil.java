package com.tianee.oa.subsys.bisengin.util;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.dbutils.DbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.bisengin.bean.BisDataSource;
import com.tianee.oa.subsys.bisengin.bean.BisRunRelation;
import com.tianee.oa.subsys.bisengin.bean.BisTable;
import com.tianee.oa.subsys.bisengin.bean.BisTableField;
import com.tianee.oa.subsys.bisengin.service.BisTableService;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDataSource;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.dynamic.TeeClassRunner;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 业务引擎工具类
 * @author kakalion
 *
 */
@Repository
@Qualifier(value="bisEngineUtil")
public class BisEngineUtil{
	@Autowired
	private TeeSimpleDaoSupport simpleDaoSupport;
	
	@Autowired
	private BisTableService tableService;
	
	/**
	 * 获取业务表的连接对象
	 * @param bisTable
	 * @return
	 */
	public Connection getConnection(BisTable bisTable){
		Connection conn = null;
		BisDataSource dataSource_ = bisTable.getBisDataSource();
		if(dataSource_.getDataSource()==1){//内部数据源
			conn = TeeDbUtility.getConnection(null);
		}else{
			TeeDataSource dataSource = new TeeDataSource();
			dataSource.setUrl(dataSource_.getDriverUrl());
			dataSource.setUserName(dataSource_.getDriverUsername());
			dataSource.setPassWord(dataSource_.getDriverPwd());
			dataSource.setDriverClass(dataSource_.getDriverClass());
			dataSource.setConfigModel(dataSource_.getConfigModel());
			conn = TeeDbUtility.getConnection(dataSource);
		}
		return conn;
	}
	
	/**
	 * 根据业务表名称获取数据库连接
	 * @param bisTableName
	 * @return
	 */
	public Connection getConnection(String bisTableName){
		String hql = "from BisTable bt where bt.tableName=?";
		BisTable bisTable = (BisTable) simpleDaoSupport.unique(hql, new Object[]{bisTableName});
		if(bisTable==null){
			return null;
		}
		Connection conn = getConnection(bisTable);
		return conn;
	}
	
	/**
	 * 通过runId获取业务-内部流程关系图
	 * @return
	 */
	public List<BisRunRelation> getBisRunRelations(int runId){
		String hql = "from BisRunRelation brr where brr.runId=?";
		return simpleDaoSupport.find(hql, new Object[]{runId});
	}
	
	/**
	 * 保存业务表
	 * @param dbConn 数据库连接
	 * @param insertItem 插入项
	 * @param bisTableName 业务表标识
	 */
	public Object executeSave(Connection dbConn,Map<String,Object> insertItem,String bisTableName){
		
		BisTable bisTable = tableService.getBisTableByName(bisTableName);
//		Connection conn = null;
//		
		String tableName = bisTable.getTableName();
//		if(bisTable.getDataSource()==1){//内部数据源
//			conn = TeeDbUtility.getConnection();
//		}else{//外部数据源
//			TeeDataSource dataSource = new TeeDataSource();
//			dataSource.setDriverClass(bisTable.getDriverClass());
//			dataSource.setPassWord(bisTable.getDriverPwd());
//			dataSource.setUrl(bisTable.getDriverUrl());
//			dataSource.setUserName(bisTable.getDriverUsername());
//			conn = TeeDbUtility.getConnection(dataSource);
//		}
		DbUtils dbUtils = new DbUtils(dbConn);
		
		//获取列表
		List<BisTableField> fields = simpleDaoSupport.find("from BisTableField btf where btf.bisTable.sid="+bisTable.getSid(), null);
		//获取主键
		BisTableField primaryKey = null;
		for(BisTableField field:fields){
			if(field.getPrimaryKeyFlag()==1){
				primaryKey = field;
				break;
			}
		}
		
		List params = new ArrayList();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		StringBuffer sql = new StringBuffer("insert into "+tableName+"(");
		int sequence = 0;//序列值
		Object defineSeq = null;
		
		//GUID递增策略
		if(primaryKey!=null && primaryKey.getGeneratedType()==2){
			sql.append(primaryKey.getFieldName()+",");
		}else if(primaryKey!=null && primaryKey.getGeneratedType()==3){//自定义主键生成策略
			sql.append(primaryKey.getFieldName()+",");
		}else if(primaryKey!=null && primaryKey.getGeneratedType()==4){//序列生成策略
			sql.append(primaryKey.getFieldName()+",");
		}
		
		Set<String> keys = insertItem.keySet();
		
		for(String key:keys){
			sql.append(key+",");
		}
		if(sql.charAt(sql.length()-1)==','){
			sql.deleteCharAt(sql.length()-1);
		}
		sql.append(") values(");
		
		//GUID
		if(primaryKey!=null && primaryKey.getGeneratedType()==2){
			sql.append("?,");
			params.add(uuid);
		}else if(primaryKey!=null && primaryKey.getGeneratedType()==3){//自定义主键生成策略
			sql.append("?,");
			try {
				defineSeq = TeeClassRunner.exec(primaryKey.getGeneratePlugin(), "generate", null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			params.add(defineSeq);
		}else if(primaryKey!=null && primaryKey.getGeneratedType()==4){//序列生成策略
			sql.append("?,");
			Map seqMap = null;
			try {
				seqMap = dbUtils.queryToMap("select "+primaryKey.getGeneratePlugin()+".nextval as NEXTVAL from dual");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(seqMap!=null){
				sequence = TeeStringUtil.getInteger(seqMap.get("NEXTVAL"), 0);
			}
			params.add(sequence);
		}
		
		keys = insertItem.keySet();
		for(String key:keys){
			sql.append("?,");
			params.add(insertItem.get(key));
		}
		if(sql.charAt(sql.length()-1)==','){
			sql.deleteCharAt(sql.length()-1);
		}
		sql.append(")");
//		System.out.println(sql.toString());
		
		Map data = null;
		Object primaryKeyVal = null;
		
		BisQuery bisQuery = new BisQuery(dbConn);
		primaryKeyVal = bisQuery.executeInsert(sql.toString(),bisTable.getBisDataSource().getDbType(), params.toArray());
//		dbConn.commit();
		
		//获取主键
		if(primaryKey!=null && primaryKey.getGeneratedType()==2){//UUID策略
			primaryKeyVal = uuid;
		}else if(primaryKey!=null && primaryKey.getGeneratedType()==3){//自定义主键生成策略
			primaryKeyVal = defineSeq;
		}else if(primaryKey!=null && primaryKey.getGeneratedType()==4){//序列自增策略
			primaryKeyVal = sequence;
		}
		
		return primaryKeyVal;
		
//		StringBuffer sql = new StringBuffer();
//		String uuid = UUID.randomUUID().toString();
//		sql.append("insert into "+bisTableName+"(BIS_ID");
//		List params = new ArrayList();
//		params.add(uuid);
//		Set<String> keys = insertItem.keySet();
//		for(String key:keys){
//			sql.append(","+key);
//			params.add(insertItem.get(key));
//		}
//		sql.append(") values(?");
//		for(String key:keys){
//			sql.append(",?");
//		}
//		sql.append(")");
//		
//		BisQuery bisQuery = new BisQuery(dbConn);
//		bisQuery.executeUpdate(sql.toString(), params.toArray());
	}
	
	/**
	 * 执行插入方式，返回主键
	 * @param dbConn
	 * @param insertItem
	 * @param bisTable
	 * @return
	 */
	public Object doInsert(Connection dbConn,Map<String,Object> insertItem,BisTable bisTable){
		//获取列表
		List<BisTableField> fields = simpleDaoSupport.find("from BisTableField btf where btf.bisTable.sid="+bisTable.getSid(), null);
		//获取主键
		BisTableField primaryKey = null;
		for(BisTableField field:fields){
			if(field.getPrimaryKeyFlag()==1){
				primaryKey = field;
				break;
			}
		}
		
		DbUtils dbUtils = new DbUtils(dbConn);
		Object primaryKeyVal = null;
		
		List params = new ArrayList();
		StringBuffer sql = new StringBuffer("insert into "+bisTable.getTableName()+"(");
		
		//非本地递增策略
		if(primaryKey.getGeneratedType()!=1){
			sql.append(primaryKey.getFieldName()+",");
		}
		
		//根据数据结构过滤
		filterVarsType(insertItem,fields);
		Set<String> keys = insertItem.keySet();
		
		for(String key:keys){
			sql.append(key+",");
		}
		if(sql.charAt(sql.length()-1)==','){
			sql.deleteCharAt(sql.length()-1);
		}
		sql.append(") values(");
		
		
		if(primaryKey.getGeneratedType()==2){//GUID
			String uuid = UUID.randomUUID().toString().replace("-", "");
			primaryKeyVal = uuid;
			sql.append("?,");
			params.add(uuid);
		}else if(primaryKey.getGeneratedType()==3){//自定义主键生成策略
			sql.append("?,");
		}else if(primaryKey.getGeneratedType()==4){//oracle序列
			Map data = new HashMap();
			try {
				data = dbUtils.queryToMap("select "+primaryKey.getGeneratePlugin()+".nextval as KEY from dual");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			primaryKeyVal = data.get("KEY");
			params.add(TeeStringUtil.getLong(data.get("KEY"), 0));
		}
		
		keys = insertItem.keySet();
		for(String key:keys){
			sql.append("?,");
			params.add(insertItem.get(key));
		}
		if(sql.charAt(sql.length()-1)==','){
			sql.deleteCharAt(sql.length()-1);
		}
		sql.append(")");
		
		
		try {
			Object obj = dbUtils.executeInsert(sql.toString(), bisTable.getBisDataSource().getDbType(), params.toArray());
			if(primaryKeyVal==null){
				primaryKeyVal = obj;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return primaryKeyVal;
	}
	
	/**
	 * 执行更新状态
	 * @param dbConn
	 * @param updateItem
	 * @param bisTable
	 */
	public void doUpdate(Connection dbConn,Map<String,Object> updateItem,BisTable bisTable){
		//获取列表
		List<BisTableField> fields = simpleDaoSupport.find("from BisTableField btf where btf.bisTable.sid="+bisTable.getSid(), null);
		//获取主键
		BisTableField primaryKey = null;
		for(BisTableField field:fields){
			if(field.getPrimaryKeyFlag()==1){
				primaryKey = field;
				break;
			}
		}
		
		DbUtils dbUtils = new DbUtils(dbConn);
		Object primaryKeyVal = updateItem.get("bisKey");
		updateItem.remove("bisKey");
		
		List params = new ArrayList();
		StringBuffer sql = new StringBuffer("update "+bisTable.getTableName()+" set ");
		
		//根据数据结构过滤
		filterVarsType(updateItem,fields);
		Set<String> keys = updateItem.keySet();
		
		for(String key:keys){
			sql.append(key+" = ? ,");
			params.add(updateItem.get(key));
		}
		if(sql.charAt(sql.length()-1)==','){
			sql.deleteCharAt(sql.length()-1);
		}
		
		sql.append(" where "+primaryKey.getFieldName()+" ");
		if(primaryKey.getFieldType().equals("NUMBER")){//数字
			sql.append(" = "+primaryKeyVal);
		}else{//字符串
			sql.append(" = '"+primaryKeyVal+"'");
		}
		
		
		try {
			dbUtils.executeUpdate(sql.toString(), params.toArray());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 过滤变量类型
	 */
	public void filterVarsType(Map itemData,List<BisTableField> fields){
		Object data = null;
		for(BisTableField bisTableField:fields){
			data = itemData.get(bisTableField.getFieldName());
			if(data==null){
				continue;
			}
			if("NUMBER".equals(bisTableField.getFieldType())){//数字
				data = TeeStringUtil.getDouble(data, 0);
			}else if("DATE".equals(bisTableField.getFieldType())){//日期时间
				data = TeeDateUtil.parseDateByPattern(data.toString());
			}else{//其余都是字符串
				data = TeeStringUtil.getString(data);
			}
			itemData.put(bisTableField.getFieldName(), data);
		}
	}
	
	
	public void executeUpdate(Connection dbConn,String sql,Object params[]){
		BisQuery bisQuery = new BisQuery(dbConn);
		bisQuery.executeUpdate(sql.toString(), params);
	}
	
	/**
	 * 创建业务-内部流程关系
	 * @param runId
	 * @param bisTableName
	 * @param bis
	 */
	public void createBisRunRelation(int runId,String bisTableName,String bisId){
		BisRunRelation bisRunRelation = new BisRunRelation();
		bisRunRelation.setBisId(bisId);
		bisRunRelation.setBisTable(bisTableName);
		bisRunRelation.setRunId(runId);
		simpleDaoSupport.save(bisRunRelation);
	}
	
	public Map<String,Object> getDataByBisId(Connection dbConn,String bisId,String bisTableName){
		BisQuery bisQuery = new BisQuery(dbConn);
		return bisQuery.queryToMap("select * from "+bisTableName+" where BIS_ID=?", new Object[]{bisId});
	}
}
