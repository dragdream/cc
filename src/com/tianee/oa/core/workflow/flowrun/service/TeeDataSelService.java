package com.tianee.oa.core.workflow.flowrun.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.subsys.bisengin.bean.BisDataSource;
import com.tianee.oa.subsys.bisengin.bean.BisTable;
import com.tianee.oa.util.workflow.TeeWorkflowHelperInterface;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDataSource;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.expReplace.TeeHTMLInputTag;
import com.tianee.webframe.util.str.expReplace.TeeHTMLTag;

@Service
public class TeeDataSelService extends TeeBaseService implements TeeDataSelServiceInterface{
	
	@Autowired
	private TeeWorkflowHelperInterface helper;
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeDataSelServiceInterface#getDataList(int, int, java.util.Map)
	 */
	@Override
	public TeeEasyuiDataGridJson getDataList(int itemSid,int runId,Map<String,String> formDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeeFlowRun flowRun = (TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class, runId);
		List<TeeFormItem> items = flowRun.getForm().getFormItems();
		TeeFormItem target = null;
		//获取控件ID
		for(TeeFormItem item:items){
			if(item.getSid()==itemSid){
				target = item;
				break;
			}
		}
		
		if(target==null){
			return null;
		}
		
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(target.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		String sql = attrs.get("sql");//sql
		String mapping = attrs.get("mapping");//mapping
		int bistableId = TeeStringUtil.getInteger(attrs.get("bistableid"), 0);
		
		//获取业务表
		BisTable bisTable = (BisTable) simpleDaoSupport.get(BisTable.class, bistableId);
		Connection conn = null;
		BisDataSource bisDataSource = bisTable.getBisDataSource();
		if(bisDataSource.getDataSource()==1){//内部数据源
			conn = TeeDbUtility.getConnection(null);
		}else{
			TeeDataSource dataSource = new TeeDataSource();
			dataSource.setDriverClass(bisDataSource.getDriverClass());
			dataSource.setPassWord(bisDataSource.getDriverPwd());
			dataSource.setUrl(bisDataSource.getDriverUrl());
			dataSource.setUserName(bisDataSource.getDriverUsername());
			dataSource.setConfigModel(bisDataSource.getConfigModel());
			conn = TeeDbUtility.getConnection(dataSource);
		}
		
		if(!"".equals(bisTable.getEntityClass()) && bisTable.getEntityClass()!=null){
			sql = sql.replace("bis_", "");
		}
		
		DbUtils dbUtils = new DbUtils(conn);
		Statement stmt = null;
		ResultSet rs = null;
		try {
			List<Map> list = dbUtils.queryToMapList(sql, null);
			int index = sql.indexOf("from");
			sql = "select count(1) "+sql.substring(index);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			int total = 0;
			if(rs.next()){
				total = rs.getInt(1);
			}
			
			dataGridJson.setRows(list);
			dataGridJson.setTotal(Long.parseLong(total+""));
//			System.out.println(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			TeeDbUtility.close(stmt, rs);
			TeeDbUtility.closeConn(conn);
		}
		
		return dataGridJson;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeDataSelServiceInterface#getMetaData(int, int, java.util.Map)
	 */
	@Override
	public Map<String,String> getMetaData(int itemSid,int runId,Map<String,String> formDatas){
		Map<String,String> metaData = new HashMap();
		
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeeFlowRun flowRun = (TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class, runId);
		List<TeeFormItem> items = flowRun.getForm().getFormItems();
		TeeFormItem target = null;
		//获取控件ID
		for(TeeFormItem item:items){
			if(item.getSid()==itemSid){
				target = item;
				break;
			}
		}
		
		if(target==null){
			return null;
		}
		
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(target.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		String sql = attrs.get("sql");//sql
		String mapping = attrs.get("mapping");//mapping
		int bistableId = TeeStringUtil.getInteger(attrs.get("bistableid"), 0);
		
		//获取业务表
		BisTable bisTable = (BisTable) simpleDaoSupport.get(BisTable.class, bistableId);
		Connection conn = null;
		BisDataSource bisDataSource = bisTable.getBisDataSource();
		if(bisDataSource.getDataSource()==1){//内部数据源
			conn = TeeDbUtility.getConnection(null);
		}else{
			TeeDataSource dataSource = new TeeDataSource();
			dataSource.setDriverClass(bisDataSource.getDriverClass());
			dataSource.setPassWord(bisDataSource.getDriverPwd());
			dataSource.setUrl(bisDataSource.getDriverUrl());
			dataSource.setUserName(bisDataSource.getDriverUsername());
			dataSource.setConfigModel(bisDataSource.getConfigModel());
			conn = TeeDbUtility.getConnection(dataSource);
		}
		
		if(!"".equals(bisTable.getEntityClass()) && bisTable.getEntityClass()!=null){
			sql = sql.replace("bis_", "");
		}
		
		DbUtils dbUtils = new DbUtils(conn);
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
	        int cols = rsmd.getColumnCount();

	        for (int i = 1; i <= cols; i++) {
	        	metaData.put(rsmd.getColumnLabel(i),"");
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			TeeDbUtility.close(stmt, rs);
			TeeDbUtility.closeConn(conn);
		}
		
		return metaData;
	}
}
