package com.tianee.oa.util.workflow;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.dbutils.DbUtils;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Encoder;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowTypeDao;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunCtrlFeedback;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunVars;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunDao;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunPrcsDao;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowSeqServiceInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeWfPluginFactory;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.core.workflow.formmanage.dao.TeeFlowFormDao;
import com.tianee.oa.core.workflow.plugins.TeeWfPlugin;
import com.tianee.oa.core.workflow.proxy.TeeJsonProxy;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.util.workflow.ctrl.TeeCtrl;
import com.tianee.oa.util.workflow.ctrl.TeeMacroCtrl;
import com.tianee.oa.util.workflow.ctrl.TeeXFetchCtrl;
import com.tianee.oa.util.workflow.ctrl.TeeXPositionCtrl;
import com.tianee.oa.util.workflow.ctrl.TeeXSealCtrl;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.servlet.HttpClientUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.str.expReplace.TeeExpFetcher;
import com.tianee.webframe.util.str.expReplace.TeeRegexpAnalyser;
import com.tianee.webframe.util.thread.TeeRequestInfo;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Service
public class TeeWorkflowHelper extends TeeBaseService implements TeeWorkflowHelperInterface{
	@Autowired
	private TeeAttachmentService attachmentService;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private TeeFlowFormDao formDao;
	
	@Autowired
	private TeeFlowRunDao flowRunDao;
	
	@Autowired
	private TeeFlowTypeDao flowTypeDao;
	
	@Autowired
	private TeeFlowRunPrcsDao flowRunPrcsDao;
	
	@Autowired
	private TeeSimpleDaoSupport simpleDaoSupport;
	
	@Autowired
	private TeeFlowSeqServiceInterface flowSeqService;
	


	
	/* (non-Javadoc)
	 * @see com.tianee.oa.util.workflow.TeeWorkflowHelperInterface#updateColumns(java.util.List, java.lang.String)
	 */
	public void updateColumns(List<TeeFormItem> items,String tableName){
		Connection conn = null;
//		Statement stmt = null;
		try{
			conn = TeeDbUtility.getConnection(null);
			conn.setAutoCommit(true);
			DbUtils dbUtils = new DbUtils(conn);
//			stmt = conn.createStatement();
			
			//获取表单元数据
//			Map<String,Integer> map = this.getTableMetaData(tableName);
			
			//分析出新加的字段
			List<String> extra = new ArrayList<String>();
			for(TeeFormItem item:items){
				try{
//					simpleDaoSupport.executeNativeUpdate("alter table "+tableName+" add "+item.getName()+" "+TeeColumnType.getColumnType(TeeColumnType.TEXT),null);
					dbUtils.executeUpdate("alter table "+tableName+" add "+item.getName()+" "+TeeColumnType.getColumnType(TeeColumnType.TEXT));
				}catch(Exception e){}
//				if(!map.containsKey(item.getName())){
//					extra.add(item.getName());
//				}
				
				//分析EXTRA扩展字段类型
				ctrlFieldExtraInfoAdder(item,extra);
			}
			
			//加入EXTRA扩展字段类型
			for(String name:extra){
				try{
//					simpleDaoSupport.executeNativeUpdate("alter table "+tableName+" add "+name+" "+TeeColumnType.getColumnType(TeeColumnType.TEXT)+"",null);
					dbUtils.executeUpdate("alter table "+tableName+" add "+name+" "+TeeColumnType.getColumnType(TeeColumnType.TEXT)+"");
				}catch(Exception e){}
			}
			
		}catch(Exception e){
			throw new RuntimeException("更新数据库元信息出错",e);
		}finally{
			if(conn!=null){
				try {
					conn.setAutoCommit(false);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			TeeDbUtility.closeConn(conn);
//			TeeDbUtility.close(stmt,null);
		}
	}
	
	/**
	 * 分析扩展字段额外字段，例如DATA_5需要依赖EXTRA_5数据
	 * @param item
	 * @param extra
	 */
	private void ctrlFieldExtraInfoAdder(TeeFormItem item,List<String> extra){
		TeeCtrl ctrl = TeeCtrl.getInstanceOf(item.getXtype());
		TeeJsonUtil json = new TeeJsonUtil();
		String model = null;
		if(ctrl instanceof TeeXFetchCtrl){//获取器
			model = item.getModel();
			Map<String,String> params = json.JsonStr2Map(model);
			String type = params.get("type");
			
			//部门 人员 角色选择控件
			if("3".equals(type) || "4".equals(type)
					|| "5".equals(type) || "6".equals(type)
					|| "7".equals(type) || "8".equals(type)){
				
				extra.add(getFormDataFieldExtraPrefix()+item.getItemId());
				
			}
		}else if(ctrl instanceof TeeMacroCtrl){//宏控件
			extra.add(getFormDataFieldExtraPrefix()+item.getItemId());
		}else if(ctrl instanceof TeeXSealCtrl){//签章组件
			extra.add(getSealPrefix()+item.getItemId());
			extra.add(getHwPrefix()+item.getItemId());
		}else if(ctrl instanceof TeeXPositionCtrl){//定位控件
			extra.add(getFormDataFieldExtraPrefix()+item.getItemId());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.util.workflow.TeeWorkflowHelperInterface#hasExistFormDataTable(java.lang.String)
	 */
	public boolean hasExistFormDataTable(String tableName){
		Session session = null;
		try{
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery("select 1 from "+tableName+" where 1!=1");
			query.uniqueResult();
			return true;
		}catch(HibernateException e){
			return false;
		}finally{
			if(session!=null){
				session.close();
			}
//			TeeDbUtility.closeConn(conn);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.util.workflow.TeeWorkflowHelperInterface#getTableMetaData(java.lang.String)
	 */
	public Map<String,Integer> getTableMetaData(String tableName){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		Map<String,Integer> metaData = new HashMap<String,Integer>();
		try{
			conn = SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from "+tableName+" where 1!=1");
			rsmd = rs.getMetaData();
			int c = rsmd.getColumnCount();
			String colName;
			for(int i=1;i<=c;i++){
				colName = rsmd.getColumnLabel(i);
				//列名称前缀拥有默认表单数据列前缀，方可加入元数据中
				if(colName.toUpperCase().indexOf(getFormDataFieldPrifix().toUpperCase())!=-1
						|| colName.toUpperCase().indexOf(getFormDataFieldExtraPrefix().toUpperCase())!=-1){
					metaData.put(colName.toUpperCase(), rsmd.getColumnType(i));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			TeeDbUtility.closeConn(conn);
			TeeDbUtility.close(stmt, rs);
		}
		return metaData;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.util.workflow.TeeWorkflowHelperInterface#getFlowRunData(int, int)
	 */
	public Map getFlowRunData(int runId,int flowId){
		String tableName = getFormDataTableName(flowId);
		TeeRequestInfo requestInfo = TeeRequestInfoContext.getRequestInfo();
		String thread_local_archives = TeeStringUtil.getString(requestInfo.getRequest().get("thread_local_archives"));
		thread_local_archives = thread_local_archives.replace("-", "");
		if(!"".equals(thread_local_archives)){
			tableName = "oaop_archives."+tableName+"_"+thread_local_archives;
		}
//		Connection conn = null;
//		Statement stmt = null;
//		ResultSet rs = null;
		Map result = null;
		try{
//			conn = SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
//			stmt = conn.createStatement();
//			rs = stmt.executeQuery("select * from "+tableName+" where RUN_ID="+runId);
			Map metaDatas = simpleDaoSupport.executeNativeUnique("select * from "+tableName+" where RUN_ID="+runId, null);
			if(metaDatas!=null){
				Set<String> keys = metaDatas.keySet();
				for(String key:keys){
					if(metaDatas.get(key) instanceof Clob){
						metaDatas.put(key, TeeStringUtil.clob2String(metaDatas.get(key)));
					}
				}
			}
			
		
//			Map<String,Integer> metaDatas = getTableMetaData(tableName);
//			if(rs.next()){
//				result = new HashMap();
//				for(String key:metaDatas.keySet()){
//					result.put(key, rs.getString(key)==null?"":rs.getString(key));
//				}
//			}
			result = metaDatas;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
//			TeeDbUtility.close(stmt, rs);
//			TeeDbUtility.closeConn(conn);
		}
		return result==null?new HashMap():result;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.util.workflow.TeeWorkflowHelperInterface#getFlowRunData(int)
	 */
	public Map getFlowRunData(int runId){
		Map result = null;
		try{
			result = simpleDaoSupport.executeNativeUnique("select flow_id as FLOWID from FLOW_RUN where RUN_ID="+runId, null);
			result = getFlowRunData(runId,TeeStringUtil.getInteger(result.get("FLOWID"), 0));
		}catch(Exception e){
			e.printStackTrace();
			throw new TeeOperationException("获取表单数据失败");
		}finally{
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.util.workflow.TeeWorkflowHelperInterface#deleteFlowRunDataTable(int)
	 */
	public void deleteFlowRunDataTable(int flowId){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String tableName = getFormDataTableName(flowId);
		try{
			conn = SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate("drop table "+tableName+"");
		}catch(Exception e){
			e.printStackTrace();
			throw new TeeOperationException("不存在名为["+tableName+"]的表");
		}finally{
			TeeDbUtility.close(stmt, rs);
			TeeDbUtility.closeConn(conn);
		}
	}
	
//	public Map getFlowRunData(int runId,int flowId){
//		String tableName = getFormDataTableName(flowId);
//		Connection conn = null;
//		Statement stmt = null;
//		ResultSet rs = null;
//		Map result = null;
//		try{
//			conn = SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
//			stmt = conn.createStatement();
//			
//			//执行表单
//			rs = stmt.executeQuery("select * from "+tableName+" where RUN_ID="+runId);
//			
//			//获取表单元数据
//			Map<String,Integer> metaDatas = getTableMetaData(tableName);
//			if(rs.next()){
//				result = new HashMap();
//				for(String key:metaDatas.keySet()){
//					result.put(key, rs.getString(key));
//				}
//			}
//		}catch(Exception e){
//			
//		}finally{
//			TeeDbUtility.close(stmt, rs);
//			TeeDbUtility.closeConn(conn);
//		}
//		return result;
//	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.util.workflow.TeeWorkflowHelperInterface#createFlowRunDataTable(com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType)
	 */
	public void createFlowRunDataTable(TeeFlowType ft){
		TeeForm form = ft.getForm();
		form = formDao.getLatestVersion(form);
		String tableName = getFormDataTableName(ft.getSid());
		List<TeeFormItem> items = form.getFormItems();
		Connection conn = null;
		DbUtils dbUtils = null;
		try{
			conn = TeeDbUtility.getConnection(null);
			conn.setAutoCommit(true);
			dbUtils = new DbUtils(conn);
			//判断表是否存在
//			simpleDaoSupport.executeNativeUnique(, null);
			dbUtils.queryToMap("select count(1) from "+tableName);
			//更新列
			updateColumns(items, tableName);
		}catch(Exception e){
			//e.printStackTrace();
			//如果表不存在，则创建表结构
//			StringBuffer createSql = new StringBuffer("create table "+tableName+" (RUN_ID "+TeeColumnType.getColumnType(TeeColumnType.INTEGER)+" primary key, FOREIGN KEY (RUN_ID) REFERENCES FLOW_RUN(RUN_ID) on delete cascade");
			StringBuffer createSql = new StringBuffer("create table "+tableName+" (RUN_ID "+TeeColumnType.getColumnType(TeeColumnType.INTEGER)+" primary key ");
			List<String> extra = new ArrayList();
			for(TeeFormItem item:items){
				createSql.append(","+item.getName()+" "+TeeColumnType.getColumnType(TeeColumnType.TEXT));
				//分析EXTRA扩展字段类型
				ctrlFieldExtraInfoAdder(item,extra);
			}
			
			for(String ext:extra){
				createSql.append(","+ext+" "+TeeColumnType.getColumnType(TeeColumnType.TEXT));
			}
			createSql.append(")");
			try {
				dbUtils.executeUpdate(createSql.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				conn.setAutoCommit(false);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			simpleDaoSupport.executeNativeUpdate(createSql.toString(), null);
		}finally{
			TeeDbUtility.closeConn(conn);
		}
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.util.workflow.TeeWorkflowHelperInterface#initFlowRunDataFormTable(com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType)
	 */
	public boolean  initFlowRunDataFormTable(TeeFlowType ft){
		try {
			String tableName = getFormDataTableName(ft.getSid());
			if(tableName == null || "".equals(tableName)){
				return false;
			}
			if(hasExistFormDataTable(tableName)){
				return false;
			}else{
				createFlowRunDataTable(ft);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.util.workflow.TeeWorkflowHelperInterface#initFlowRunData(int, int)
	 */
	public void initFlowRunData(int runId,int flowId){
		String tableName = getFormDataTableName(flowId);
//		Connection conn = null;
//		Statement stmt = null;
		try{
//			conn = SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
//			stmt = conn.createStatement();
			StringBuffer sql = new StringBuffer();
			//插入初始表结构
			TeeFlowRun fr = flowRunDao.get(runId);
			TeeForm form = fr.getFlowType().getForm();
			List<TeeFormItem> items = formDao.getLatestVersion(form).getFormItems();
			
			//插入预设值
			sql.append("insert into "+tableName+" (RUN_ID");
			List<String> extra = new ArrayList<String>();
			for(TeeFormItem item:items){
				sql.append(","+item.getName());
				ctrlFieldExtraInfoAdder(item,extra);
			}
			for(String tmp:extra){
				sql.append(","+tmp);
			}
			sql.append(") values ("+runId);
			for(TeeFormItem item:items){
				if("xinput".equalsIgnoreCase(item.getXtype()) 
						|| "xtextarea".equalsIgnoreCase(item.getXtype())){
					sql.append(",'"+(item.getDefaultValue()==null?"":item.getDefaultValue())+"'");
				}else{
					sql.append(",''");
				}
			}
			for(String tmp:extra){
				sql.append(",''");
			}
			sql.append(")");
			//System.out.println(sql.toString());
			simpleDaoSupport.executeNativeUpdate(sql.toString(), null);
//			stmt.executeUpdate(sql.toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
//			TeeDbUtility.close(stmt, null);
//			TeeDbUtility.closeConn(conn);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.util.workflow.TeeWorkflowHelperInterface#getSessionFactory()
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.util.workflow.TeeWorkflowHelperInterface#setSessionFactory(org.hibernate.SessionFactory)
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 根据flowId获取表单数据表名
	 * @param flowId
	 * @param formId
	 * @return
	 */
	public static String getFormDataTableName(int flowId){
		return TeeConst.FORM_DATA_TABLE_PREFIX+flowId;
	}
	
	/**
	 * 获取表单数据字段前缀名称
	 * @return
	 */
	public static String getFormDataFieldPrifix(){
		return TeeConst.FORM_DATA_FIELD_PREFIX;
	}
	
	public static String getFormDataFieldExtraPrefix(){
		return TeeConst.FORM_DATA_FIELD_EXTRA_PREFIX;
	}
	
	public static String getSealPrefix(){
		return TeeConst.SEAL_PREFIX;
	}
	
	public static String getHwPrefix(){
		return TeeConst.HW_PREFIX;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.util.workflow.TeeWorkflowHelperInterface#saveOrUpdateFlowRunData(int, int, int, java.util.Map, java.util.Map)
	 */
	public void saveOrUpdateFlowRunData(final int runId,int flowId,int frpSid,Map<String,String> datas,Map<String,String> requestParam){
//		Connection conn = null;
//		PreparedStatement pstmt = null;
		final String tableName = getFormDataTableName(flowId);
		StringBuffer sql = new StringBuffer();
		TeeFlowRunPrcs frp = null;
		TeeWfPluginFactory pluginFactory = null;
		TeeWfPlugin wfPlugin = null;
		frp = flowRunPrcsDao.get(frpSid);
		try{
			//创建插件工厂
			pluginFactory = new TeeWfPluginFactory(frp, datas,requestParam);
			wfPlugin = pluginFactory.newInstance();
			
			//获取处理前的流程变量hashcode
			int hashcode = wfPlugin.getFlowRunVars()==null?0:wfPlugin.getFlowRunVars().hashCode();
			
			//执行保存前操作
			TeeJsonProxy jsonProxy = wfPlugin.beforeSave();
			if(jsonProxy!=null){
				if(!jsonProxy.isRtState()){
					throw new TeeOperationException(jsonProxy.getRtMsg());
				}
			}
			
//			conn = SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
			Set<String> set = datas.keySet();
			//判断是否存在该数据
			Map tmpData = getFlowRunData(runId,flowId);
			if(tmpData==null || tmpData.size()==0){//不存在流程数据，则分析表单控件，并初始化数据
				//插入实际值
				sql.append("insert into "+tableName+" (RUN_ID");
				for(String key:set){
					sql.append(","+key);
				}
				sql.append(") values (?");
				for(String key:set){
					sql.append(",?");
				}
				sql.append(")");
			}else{
				sql.append("update "+tableName+" set RUN_ID=?");
				for(String key:set){
					sql.append(","+key+"=?");
				}
				sql.append(" where RUN_ID="+runId);
			}
			
//			pstmt = conn.prepareStatement(sql.toString());
			List params = new ArrayList();
			params.add(runId);
			
			for(String key:set){
				params.add(datas.get(key));
			}
			
			simpleDaoSupport.executeNativeUpdate(sql.toString(), params.toArray());
			
			//动态设置流程标题
			   TeeFlowType ft=(TeeFlowType) simpleDaoSupport.get(TeeFlowType.class,flowId);
			   TeeFlowRun fr=(TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class,runId);
			   final TeeForm form=ft.getForm();//获取流程绑定的表单
			   if(fr!=null){
				   //获取流程的工作名称设置表达式
				   String oRunName=fr.getoRunName();
				   TeeRegexpAnalyser analyser = new TeeRegexpAnalyser(); 
				   analyser.setText(oRunName);
				   String newRunName = analyser.replace(new String[] { "\\{DATA_[a-zA-Z0-9\\W]+\\}" },
							new TeeExpFetcher() {
									@Override
									public String parse(String pattern) {
										//pattern是满足正则的字符串截取内容
										//在这里去掉{DATA_ 和 }
										String ctrlTitle = pattern.replace("{DATA_", "").replace("}", "");
//										//查询控件
										List<TeeFormItem> ctrlList=simpleDaoSupport.executeQuery(" from TeeFormItem where form=? and title=? ", new Object[]{form,ctrlTitle});
										String value="";
										if(ctrlList!=null&&ctrlList.size()>0){//控件名称存在
											   TeeFormItem ctrl=ctrlList.get(0);
											   //获取控件的name  例如：DATA_1
											   String ctrlName=ctrl.getName();
											   //查询值
											   List result=simpleDaoSupport.getBySql("select to_char("+ctrlName+") from "+tableName+" where RUN_ID=? ", new Object[]{runId});
											   value=TeeStringUtil.getString(result.get(0));
											  
										  }else{
											  value="";
										  }
										
										
										return value;//此处是返回最新的字符串片段  也就是将原先匹配的{DATA_xxxx}替换成指定的字符串
									}

								});
				   
				 //修改名称
				 fr.setRunName(newRunName);
			   }
			
			   
			   
			   
			//执行保存后操作
			wfPlugin.afterSave();
			int hashcode2 = wfPlugin.getFlowRunVars()==null?0:wfPlugin.getFlowRunVars().hashCode();
			
			//做存储
			if(hashcode!=0 && hashcode2!=0 && hashcode!=hashcode2){
				List<TeeFlowRunVars> list = frp.getFlowRun().getFlowRunVars();
				String key = null;
				String value = null;
				Map flowRunVarsMap = wfPlugin.getFlowRunVars();
				if(flowRunVarsMap!=null){
					for(TeeFlowRunVars flowRunVars:list){
						key = flowRunVars.getVarKey();
						flowRunVars.setVarValue((String)flowRunVarsMap.get(key));
						simpleDaoSupport.update(flowRunVars);
					}
				}
				
			}
			
			
			//执行触发器
			if(frp.getFlowPrcs()!=null && !TeeUtility.isNullorEmpty(frp.getFlowPrcs().getTriggerUrl())){
				Map<String,String> postRequest = new HashMap();
				postRequest.put("runId", frp.getFlowRun().getRunId()+"");
				postRequest.put("runName", frp.getFlowRun().getRunName());
				postRequest.put("oper", "SAVE");
				postRequest.put("datas", new BASE64Encoder().encode(TeeJsonUtil.mapToJson(tmpData).getBytes()).replace("\r\n", ""));
				postRequest.put("curNode", frp.getFlowPrcs().getPrcsName());
				String result = HttpClientUtil.requestPost(postRequest, frp.getFlowPrcs().getTriggerUrl());
				JSONObject jsonObject = null;
				try{
					jsonObject = JSONObject.fromObject(result);
					if(!jsonObject.containsKey("status")){
						throw new TeeOperationException("插件所返回的返回值格式不正确，必须包含status");
					}
					if(jsonObject.getInt("status")!=0){
						throw new TeeOperationException(jsonObject.getString("msg"));
					}
				}catch(Exception ex){
					throw new TeeOperationException(ex);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new TeeOperationException(e.getMessage());
		}finally{
//			TeeDbUtility.close(pstmt, null);
//			TeeDbUtility.closeConn(conn);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.util.workflow.TeeWorkflowHelperInterface#getHttpRequestFlowRunDataMaps(javax.servlet.http.HttpServletRequest)
	 */
	public Map getHttpRequestFlowRunDataMaps(HttpServletRequest request){
		Map params = new HashMap();
		Map parameterMap = request.getParameterMap();
		Set<String> set = parameterMap.keySet();
		for(String key:set){
			if(key.indexOf("DATA_")!=-1 || key.indexOf("EXTRA_")!=-1 || key.indexOf("SEAL_")!=-1 || key.indexOf("HW_")!=-1){
				params.put(key, ((String[])parameterMap.get(key))[0]);
			}
		}
		return params;
	}
	
	
	
	/**
	 * 检查字符串是否正确
	 * @param oper 操作
	 * @param value 值
	 * @param realValue  真实值
	 * @return
	 */
	public static boolean checkStrTrueOrFalse(String oper ,String value , String realValue,String vtype){
		boolean isTrue = true;//默认为false
		String operStr = "";
		if(oper.equals("等于")){//等于
			
			if("1".equals(vtype)){//字符串
				if(value.equals(realValue)){
					return true;
				}
			}else if("2".equals(vtype)){//数字
				if(TeeStringUtil.getDouble(realValue, 0.00)==TeeStringUtil.getDouble(value, 0.00)){
					return true;
				}
			}else{//日期
				if(TeeDateUtil.parseCalendar(realValue).compareTo(TeeDateUtil.parseCalendar(value))==0){
					return true;
				}
			}
			
			return false;
		}else if(oper.equals("不等于")){//不等于
			if("1".equals(vtype)){//字符串
				if(!value.equals(realValue)){
					return true;
				}
			}else if("2".equals(vtype)){//数字
				if(TeeStringUtil.getDouble(realValue, 0.00)!=TeeStringUtil.getDouble(value, 0.00)){
					return true;
				}
			}else{//日期
				if(TeeDateUtil.parseCalendar(realValue).compareTo(TeeDateUtil.parseCalendar(value))!=0){
					return true;
				}
			}
			return false;
		}else if(oper.equals("大于")){//大于
			if("1".equals(vtype)){//字符串
				if(realValue.hashCode()>value.hashCode()){
					return true;
				}
			}else if("2".equals(vtype)){//数字
				if(TeeStringUtil.getDouble(realValue, 0.00)>TeeStringUtil.getDouble(value, 0.00)){
					return true;
				}
			}else{//日期
				if(TeeDateUtil.parseCalendar(realValue).compareTo(TeeDateUtil.parseCalendar(value))>1){
					return true;
				}
			}
			return false;
		}else if(oper.equals("大于等于")){//大于等于
			if("1".equals(vtype)){//字符串
				if(realValue.hashCode()>=value.hashCode()){
					return true;
				}
			}else if("2".equals(vtype)){//数字
				if(TeeStringUtil.getDouble(realValue, 0.00)>=TeeStringUtil.getDouble(value, 0.00)){
					return true;
				}
			}else{//日期
				if(TeeDateUtil.parseCalendar(realValue).compareTo(TeeDateUtil.parseCalendar(value))>-1){
					return true;
				}
			}
			return false;
		}else if(oper.equals("小于")){//小于
			if("1".equals(vtype)){//字符串
				if(realValue.hashCode()<value.hashCode()){
					return true;
				}
			}else if("2".equals(vtype)){//数字
				if(TeeStringUtil.getDouble(realValue, 0.00)<TeeStringUtil.getDouble(value, 0.00)){
					return true;
				}
			}else{//日期
				if(TeeDateUtil.parseCalendar(realValue).compareTo(TeeDateUtil.parseCalendar(value))==-1){
					return true;
				}
			}
			return false;
		}else if(oper.equals("小于等于")){//小于等于
			if("1".equals(vtype)){//字符串
				if(realValue.hashCode()<=value.hashCode()){
					return true;
				}
			}else if("2".equals(vtype)){//数字
				if(TeeStringUtil.getDouble(realValue, 0.00)<=TeeStringUtil.getDouble(value, 0.00)){
					return true;
				}
			}else{//日期
				if(TeeDateUtil.parseCalendar(realValue).compareTo(TeeDateUtil.parseCalendar(value))==-1){
					return true;
				}
			}
			return false;
		}else if(oper.equals("以字符开头")){//以字符开头
			return realValue.startsWith(value);
		}else if(oper.equals("以字符结尾")){//以字符结尾
			return realValue.endsWith(value);
		}else if(oper.equals("包含")){//包含
			return realValue.contains(value);
		}else if(oper.equals("不包含")){//不包含
			return !realValue.contains(value);
		}else{
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.util.workflow.TeeWorkflowHelperInterface#basicMacroFiltering(java.lang.String)
	 */
	public String basicMacroFiltering(String pattern){
		TeeRegexpAnalyser analyser = new TeeRegexpAnalyser();
		analyser.setText(pattern);
		final Calendar c = Calendar.getInstance();
		String text = analyser.replace(new String[]{"\\{[\\u4e00-\\u9fa5a-zA-Z]+\\}"}, new TeeExpFetcher(){
			@Override
			public String parse(String pattern) {
				// TODO Auto-generated method stub
				String token = pattern.substring(1,pattern.length()-1);
				if("年".equals(token)){
					return String.valueOf(c.get(Calendar.YEAR));
				}else if("月".equals(token)){
					return String.valueOf(c.get(Calendar.MONTH)+1);
				}else if("日".equals(token)){
					return String.valueOf(c.get(Calendar.DATE));
				}else if("时".equals(token)){
					return String.valueOf(c.get(Calendar.HOUR_OF_DAY));
				}else if("分".equals(token)){
					return String.valueOf(c.get(Calendar.MINUTE)<10?"0"+c.get(Calendar.MINUTE):c.get(Calendar.MINUTE));
				}else if("秒".equals(token)){
					return String.valueOf(c.get(Calendar.SECOND));
				}
				return pattern;
			}
		});
		return text;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.util.workflow.TeeWorkflowHelperInterface#flowMacroFiltering(java.lang.String, com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun, com.tianee.oa.core.org.bean.TeePerson)
	 */
	public String flowMacroFiltering(String pattern,final TeeFlowRun flowRun,final TeePerson loginUser){
		TeeRegexpAnalyser analyser = new TeeRegexpAnalyser();
		analyser.setText(pattern);
		String text = analyser.replace(new String[]{"\\{[\\u4e00-\\u9fa5a-zA-Z]+\\}"}, new TeeExpFetcher(){
			@Override
			public String parse(String pattern) {
				// TODO Auto-generated method stub
				String token = pattern.substring(1,pattern.length()-1);
				if("流程名称".equals(token)){
					return flowRun.getFlowType().getFlowName();
				}else if("流程分类名称".equals(token)){
					return flowRun.getFlowType().getFlowSort().getSortName();
				}else if("用户姓名".equals(token)){
					return loginUser.getUserName();
				}else if("部门".equals(token)){
					return loginUser.getDept().getDeptName();
				}else if("部门全称".equals(token)){
					String deptFullName=loginUser.getDept().getDeptFullName();
					if(deptFullName.startsWith("/")){
						deptFullName=deptFullName.substring(1,deptFullName.length());
					}
					deptFullName=deptFullName.replace("/", "_");
					return deptFullName;
				}else if("角色".equals(token)){
					return loginUser.getUserRole().getRoleName();
				}else if("流水号".equals(token)){
					return String.valueOf(flowRun.getRunId());
				}else if("编号".equals(token)){
					TeeFlowType ft = flowRun.getFlowType();
					int length = ft.getNumberingLength();
					int numbering = flowSeqService.generateFlowTypeNumbering(ft.getSid());
					String numStr = String.valueOf(numbering);
					int strLength = numStr.length();
					if(numStr.length()<length){
						for(int i=0;i<(length-strLength);i++){
							numStr = "0"+numStr;
						}
					}
					return numStr;
				}
				return pattern;
			}
		});
		return text;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.util.workflow.TeeWorkflowHelperInterface#getFieldMappingTranslate(java.util.Map, java.util.Map, java.util.List, java.util.List)
	 */
	public Map getFieldMappingTranslate(Map mappingMap,Map<String,String> dataMap,List<TeeFormItem> keyItems,List<TeeFormItem> valueItems){
		Map params = new HashMap();
		Set<String> keys = mappingMap.keySet();
		boolean hasKey = false;
		boolean hasValue = false;
		String data = "";
		String keyIdentity = "";
		for(String key:keys){
			//从左侧查找数据
			for(TeeFormItem item:keyItems){
				if(item.getTitle().equals(key)){
					hasKey = true;
					data = dataMap.get(item.getName());
					break;
				}
			}
			if(!hasKey){
				continue;
			}
			//从右侧查找数据
			for(TeeFormItem item:valueItems){
				if(item.getTitle().equals(mappingMap.get(key))){
					hasValue = true;
					keyIdentity = item.getName();
					break;
				}
			}
			if(!hasValue){
				continue;
			}
			//将翻译后的插入到params中
			params.put(keyIdentity, data);
		}
		return params;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.util.workflow.TeeWorkflowHelperInterface#getHistoryFlowRunDatas(int, int, com.tianee.oa.core.org.bean.TeePerson, com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	public TeeEasyuiDataGridJson getHistoryFlowRunDatas(int itemId,int frpSid,TeePerson loginPerson,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		try{
			//获取当前流程步骤
			TeeFlowRunPrcs frp = (TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class, frpSid);
			TeeFlowRun fr = frp.getFlowRun();
			TeeFlowType ft = fr.getFlowType();
			
			//查询出当前item数据不为空的历史数据信息
			
			String sql = " from "+TeeWorkflowHelper.getFormDataTableName(ft.getSid())+" d,flow_run fr "
					+ "where d.run_id=fr.run_id and "+TeeWorkflowHelper.getFormDataFieldPrifix()+itemId+" is not null and "
							+ TeeWorkflowHelper.getFormDataFieldPrifix()+itemId+" not like '' ";
			
			List<Map> datas = simpleDaoSupport.executeNativeQuery("select d.*,fr.run_name as RUN_NAME "+sql+" order by run_id desc", null, (dm.getPage() - 1) * dm.getRows(), dm.getRows());
			Map totalMap = simpleDaoSupport.executeNativeUnique("select count(d.run_id) as C  "+sql, null);
			dataGridJson.setRows(datas);
			dataGridJson.setTotal(TeeStringUtil.getLong(totalMap.get("C"), 0));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return dataGridJson;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.util.workflow.TeeWorkflowHelperInterface#conditionResult(java.util.Map, java.lang.String)
	 */
	public boolean conditionResult(Map<String,String> datas,String model){
		
		return true;
	}


	
	/* (non-Javadoc)
	 * @see com.tianee.oa.util.workflow.TeeWorkflowHelperInterface#getFeedBackFieldMappingTranslate(java.util.Map, int, java.util.List, java.util.List, com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun, com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs)
	 */
	public void getFeedBackFieldMappingTranslate(Map<String, String> fieldMapping, int runId,List<TeeFormItem> keyItems,List<TeeFormItem> valueItems,TeeFlowRun childFlowRun,TeeFlowRunPrcs childFlowRunPrcs) {
		List<TeeFlowRunCtrlFeedback> fbList=null;
		TeeFlowRunCtrlFeedback  flowRunCtrlFeedback=null;
		Set<String> keys = fieldMapping.keySet();
		boolean hasKey = false;
		boolean hasValue = false;
		String data = "";
		String keyIdentity = "";
		for(String key:keys){
			//从左侧查找数据
			for(TeeFormItem item:keyItems){
				if(item.getTitle().equals(key)&&("xfeedback").equals(item.getXtype())){
					hasKey = true;
					fbList=simpleDaoSupport.executeQuery(" from TeeFlowRunCtrlFeedback where itemId=? and flowRun.runId=? ", new Object[]{item.getItemId(),runId});
					break;
				}
			}
			if(!hasKey){
				continue;
			}
			//从右侧查找数据
			for(TeeFormItem item:valueItems){
				if(item.getTitle().equals(fieldMapping.get(key))&&("xfeedback").equals(item.getXtype())){
					hasValue = true;
					if(fbList!=null&&fbList.size()>0){
						for (TeeFlowRunCtrlFeedback fb : fbList) {
							flowRunCtrlFeedback=new TeeFlowRunCtrlFeedback();
							flowRunCtrlFeedback.setContent(fb.getContent());
							flowRunCtrlFeedback.setCreateTime(fb.getCreateTime());
							flowRunCtrlFeedback.setCreateUser(fb.getCreateUser());
							flowRunCtrlFeedback.setDeptName(fb.getDeptName());
							flowRunCtrlFeedback.setDeptNamePath(fb.getDeptNamePath());
							flowRunCtrlFeedback.setFlowPrcs(childFlowRunPrcs.getFlowPrcs());
							flowRunCtrlFeedback.setFlowRun(childFlowRun);
							flowRunCtrlFeedback.setHwData(fb.getHwData());
							flowRunCtrlFeedback.setItemId(item.getItemId());
							flowRunCtrlFeedback.setPicData(fb.getPicData());
							flowRunCtrlFeedback.setRand(fb.getRand());
							flowRunCtrlFeedback.setRoleName(fb.getRoleName());
							flowRunCtrlFeedback.setSealData(fb.getSealData());
							flowRunCtrlFeedback.setSignData(fb.getSignData());
							flowRunCtrlFeedback.setUserName(fb.getUserName());
							simpleDaoSupport.save(flowRunCtrlFeedback);
						}
					}
					break;
				}
			}
		}
	}

	
	/* (non-Javadoc)
	 * @see com.tianee.oa.util.workflow.TeeWorkflowHelperInterface#getFeedBackFieldReverseMappingTranslate(java.util.Map, java.util.List, java.util.List, int, int, int)
	 */
	public void getFeedBackFieldReverseMappingTranslate(
			Map<String, String> fieldReverseMapping,
			List<TeeFormItem> valueItems, List<TeeFormItem> keyItems,int childRunId,int pRunId,int pFlowPrcsId) {
		List<TeeFlowRunCtrlFeedback> fbList=null;
		List<TeeFlowRunCtrlFeedback> oldList=null;//原父流程的会签数据集合
		List<String> randomList=null;
		TeeFlowRunCtrlFeedback  flowRunCtrlFeedback=null;
		Set<String> keys = fieldReverseMapping.keySet();
		TeeFlowRun pFlowRun=(TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class,pRunId);
		TeeFlowProcess pFlowProcess=(TeeFlowProcess) simpleDaoSupport.get(TeeFlowProcess.class,pFlowPrcsId);
		boolean hasKey = false;
		boolean hasValue = false;
		String data = "";
		String keyIdentity = "";
		for(String key:keys){
			//从左侧查找数据
			for(TeeFormItem item:keyItems){
				if(item.getTitle().equals(key)&&("xfeedback").equals(item.getXtype())){
					hasKey = true;
					fbList=simpleDaoSupport.executeQuery(" from TeeFlowRunCtrlFeedback where itemId=? and flowRun.runId=? ", new Object[]{item.getItemId(),childRunId});
					break;
				}
			}
			if(!hasKey){
				continue;
			}
			//从右侧查找数据
			for(TeeFormItem item:valueItems){
				if(item.getTitle().equals(fieldReverseMapping.get(key))&&("xfeedback").equals(item.getXtype())){
					hasValue = true;
					oldList=simpleDaoSupport.executeQuery(" from TeeFlowRunCtrlFeedback where itemId=? and flowRun.runId=? ", new Object[]{item.getItemId(),pRunId});
					randomList=new ArrayList<String>();
					for (TeeFlowRunCtrlFeedback old : oldList) {
						randomList.add(old.getRand());
					}
					
					if(fbList!=null&&fbList.size()>0){
						for (TeeFlowRunCtrlFeedback fb : fbList) {
							if(!randomList.contains(fb.getRand())){
								flowRunCtrlFeedback=new TeeFlowRunCtrlFeedback();
								flowRunCtrlFeedback.setContent(fb.getContent());
								flowRunCtrlFeedback.setCreateTime(fb.getCreateTime());
								flowRunCtrlFeedback.setCreateUser(fb.getCreateUser());
								flowRunCtrlFeedback.setDeptName(fb.getDeptName());
								flowRunCtrlFeedback.setDeptNamePath(fb.getDeptNamePath());
								flowRunCtrlFeedback.setFlowPrcs(pFlowProcess);
								flowRunCtrlFeedback.setFlowRun(pFlowRun);
								flowRunCtrlFeedback.setHwData(fb.getHwData());
								flowRunCtrlFeedback.setItemId(item.getItemId());
								flowRunCtrlFeedback.setPicData(fb.getPicData());
								flowRunCtrlFeedback.setRand(fb.getRand());
								flowRunCtrlFeedback.setRoleName(fb.getRoleName());
								flowRunCtrlFeedback.setSealData(fb.getSealData());
								flowRunCtrlFeedback.setSignData(fb.getSignData());
								flowRunCtrlFeedback.setUserName(fb.getUserName());
								simpleDaoSupport.save(flowRunCtrlFeedback);
							}
						}
					}
					break;
				}
			}
		}
	}

	
	/* (non-Javadoc)
	 * @see com.tianee.oa.util.workflow.TeeWorkflowHelperInterface#getFlowRunDatasOnTitle(int)
	 */
	public Object getFlowRunDatasOnTitle(int runId) {
		Map result = null;
		try{
			result = simpleDaoSupport.executeNativeUnique("select flow_id as FLOWID from FLOW_RUN where RUN_ID="+runId, null);
			result = getFlowRunDataOnTitle(runId,TeeStringUtil.getInteger(result.get("FLOWID"), 0));
		}catch(Exception e){
			e.printStackTrace();
			throw new TeeOperationException("获取表单数据失败");
		}finally{
		}
		return result;
	}

	private Map getFlowRunDataOnTitle(int runId, int flowId) {
		//根据FLOWID获取关联的form
		TeeFlowType flowType=(TeeFlowType) simpleDaoSupport.get(TeeFlowType.class,flowId);
		TeeForm form=null;
		List<TeeFormItem> itemList=null;
		if(flowType!=null){
			form=flowType.getForm();
			if(form!=null){
				itemList=form.getFormItems();
			}
		}
		
		
		String tableName = getFormDataTableName(flowId);
		TeeRequestInfo requestInfo = TeeRequestInfoContext.getRequestInfo();
		String thread_local_archives = TeeStringUtil.getString(requestInfo.getRequest().get("thread_local_archives"));
		thread_local_archives = thread_local_archives.replace("-", "");
		if(!"".equals(thread_local_archives)){
			tableName = "oaop_archives."+tableName+"_"+thread_local_archives;
		}

		Map result = new HashMap();
		try{
			Map metaDatas = simpleDaoSupport.executeNativeUnique("select * from "+tableName+" where RUN_ID="+runId, null);
			if(metaDatas!=null){
				Set<String> keys = metaDatas.keySet();
				for(String key:keys){
					if(metaDatas.get(key) instanceof Clob){
						metaDatas.put(key, TeeStringUtil.clob2String(metaDatas.get(key)));
					}
				}
				
				if(itemList!=null&&itemList.size()>0){
					for(String key:keys){
						for (TeeFormItem item : itemList) {
							if(key.equals(item.getName())){
								result.put(item.getTitle(),metaDatas.get(key) )	;
								break;
							}
						}
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		}
		return result;
	}

	
	/**
	 * 父流程的附件上传控件映射到子流程
	 */
	@Override
	public void getAttachUploadCtrlFieldMappingTranslate(
			Map<String, String> fieldMapping, int runId,
			List<TeeFormItem> keyItems, List<TeeFormItem> valueItems,
			TeeFlowRun childFlowRun, TeeFlowRunPrcs childFlowRunPrcs) {
		List<TeeAttachment> attachList=null;
		TeeAttachment  att=null;
		Set<String> keys = fieldMapping.keySet();
		boolean hasKey = false;
		boolean hasValue = false;
		String data = "";
		String keyIdentity = "";
		for(String key:keys){
			
			//从左侧查找数据
			for(TeeFormItem item:keyItems){
				if(item.getTitle().equals(key)&&("xupload").equals(item.getXtype())){
					hasKey = true;
					attachList=attachmentService.getAttaches(TeeAttachmentModelKeys.workFlowUploadCtrl, item.getItemId()+"_"+runId);
					break;
				}
			}
			if(!hasKey){
				continue;
			}
			//从右侧查找数据
			for(TeeFormItem item:valueItems){
				if(item.getTitle().equals(fieldMapping.get(key))&&("xupload").equals(item.getXtype())){
					hasValue = true;
					if(attachList!=null&&attachList.size()>0){
						for (TeeAttachment attachment : attachList) {
							att=attachmentService.clone(attachment, TeeAttachmentModelKeys.workFlowUploadCtrl, attachment.getUser());
							att.setModelId(item.getItemId()+"_"+childFlowRun.getRunId());
							simpleDaoSupport.save(att);
						}
					}
					break;
				}
			}
		}
	}

	
	
	/**
	 * 子流程的附件上传控件映射到父流程
	 */
	@Override
	public void getAttachUploadCtrlFieldReverseMappingTranslate(
			Map<String, String> fieldReverseMapping,
			List<TeeFormItem> valueItems, List<TeeFormItem> keyItems,
			int runId, int pRunId, int pFlowPrcsId) {
		List<TeeAttachment> attachList=null;
		List<TeeAttachment> oldList=null;//原父流程的附件集合
		List<String> randomList=null;
		TeeAttachment  attach=null;
		Set<String> keys = fieldReverseMapping.keySet();
		TeeFlowRun pFlowRun=(TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class,pRunId);
		TeeFlowProcess pFlowProcess=(TeeFlowProcess) simpleDaoSupport.get(TeeFlowProcess.class,pFlowPrcsId);
		boolean hasKey = false;
		boolean hasValue = false;
		String data = "";
		String keyIdentity = "";
		for(String key:keys){
			//从左侧查找数据
			for(TeeFormItem item:keyItems){
				if(item.getTitle().equals(key)&&("xupload").equals(item.getXtype())){
					hasKey = true;
					attachList=attachmentService.getAttaches(TeeAttachmentModelKeys.workFlowUploadCtrl, item.getItemId()+"_"+runId);
					break;
				}
			}
			if(!hasKey){
				continue;
			}
			//从右侧查找数据
			for(TeeFormItem item:valueItems){
				if(item.getTitle().equals(fieldReverseMapping.get(key))&&("xupload").equals(item.getXtype())){
					hasValue = true;
					oldList=attachmentService.getAttaches(TeeAttachmentModelKeys.workFlowUploadCtrl,item.getItemId()+"_"+pRunId);
					randomList=new ArrayList<String>();
					for (TeeAttachment att : oldList) {
						randomList.add(att.getMd5());
					}
					
					if(attachList!=null&&attachList.size()>0){
						for (TeeAttachment  attachment : attachList) {
							if(!randomList.contains(attachment.getMd5())){
								attach=attachmentService.clone(attachment, TeeAttachmentModelKeys.workFlowUploadCtrl, attachment.getUser());
							    attach.setModelId(item.getItemId()+"_"+pRunId);
								simpleDaoSupport.save(attach);
							}
						}
					}
					break;
				}
			}
		}
		
	}

	
	/**
	 * 父流程到子流程  图片上传控件的映射
	 */
	@Override
	public void getImgCtrlFieldMappingTranslate(
			Map<String, String> fieldMapping, int runId,
			List<TeeFormItem> keyItems, List<TeeFormItem> valueItems,
			TeeFlowRun childFlowRun, TeeFlowRunPrcs childFlowRunPrcs,Map<String,String> parentData) {
		
		TeeAttachment  att=null;
		TeeAttachment  oAtt=null;
		Set<String> keys = fieldMapping.keySet();
		boolean hasKey = false;
		boolean hasValue = false;
		String data = "";
		String keyIdentity = "";
		for(String key:keys){
			
			//从左侧查找数据
			for(TeeFormItem item:keyItems){
				if(item.getTitle().equals(key)&&("ximg").equals(item.getXtype())){
					hasKey = true;
					if(TeeStringUtil.getInteger(parentData.get("DATA_"+item.getItemId()),0)>0){
						oAtt=(TeeAttachment) simpleDaoSupport.get(TeeAttachment.class,TeeStringUtil.getInteger(parentData.get("DATA_"+item.getItemId()),0));
					}
					break;
				}
			}
			if(!hasKey){
				continue;
			}
			//从右侧查找数据
			for(TeeFormItem item:valueItems){
				if(item.getTitle().equals(fieldMapping.get(key))&&("ximg").equals(item.getXtype())){
					hasValue = true;
					if(oAtt!=null){
						att=attachmentService.clone(oAtt, TeeAttachmentModelKeys.imgupload, oAtt.getUser());
						att.setModelId(childFlowRun.getRunId()+"");
						simpleDaoSupport.save(att);
						
						//修改中间表数据tee_f_r_d_xxx
						simpleDaoSupport.executeNativeUpdate(" update tee_f_r_d_"+childFlowRun.getFlowType().getSid()+" set DATA_"+item.getItemId()+"=? where RUN_ID=? ", new Object[]{att.getSid(),childFlowRun.getRunId()});
						
					}
					break;
				}
			}
		}
		
	}

	
	/**
	 * 子流程的图片上传控件映射到父流程
	 */
	@Override
	public void getImgUploadCtrlFieldReverseMappingTranslate(
			Map<String, String> fieldReverseMapping,
			List<TeeFormItem> valueItems, List<TeeFormItem> keyItems,
			int runId, int pRunId, int pFlowPrcsId, Map datas) {
	
		TeeAttachment  attach=null;
		TeeAttachment  OAttach=null;
		Set<String> keys = fieldReverseMapping.keySet();
		TeeFlowRun pFlowRun=(TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class,pRunId);
		TeeFlowProcess pFlowProcess=(TeeFlowProcess) simpleDaoSupport.get(TeeFlowProcess.class,pFlowPrcsId);
		boolean hasKey = false;
		boolean hasValue = false;
		String data = "";
		String keyIdentity = "";
		for(String key:keys){
			//从左侧查找数据
			for(TeeFormItem item:keyItems){
				if(item.getTitle().equals(key)&&("ximg").equals(item.getXtype())){
					hasKey = true;
					if(TeeStringUtil.getInteger(datas.get("DATA_"+item.getItemId()),0)>0){
						OAttach=(TeeAttachment) simpleDaoSupport.get(TeeAttachment.class,TeeStringUtil.getInteger(datas.get("DATA_"+item.getItemId()),0));
					}
					break;
				}
			}
			if(!hasKey){
				continue;
			}
			//从右侧查找数据
			for(TeeFormItem item:valueItems){
				if(item.getTitle().equals(fieldReverseMapping.get(key))&&("ximg").equals(item.getXtype())){
					hasValue = true;
					if(OAttach!=null){
						attach=attachmentService.clone(OAttach, TeeAttachmentModelKeys.imgupload, OAttach.getUser());
						attach.setModelId(pRunId+"");
						simpleDaoSupport.save(attach);
						
						//修改中间表数据tee_f_r_d_xxx
						simpleDaoSupport.executeNativeUpdate(" update tee_f_r_d_"+pFlowRun.getFlowType().getSid()+" set DATA_"+item.getItemId()+"=? where RUN_ID=? ", new Object[]{attach.getSid(),pRunId});
						
					}
					break;
				}
			}
		}
		
		
	}

}
