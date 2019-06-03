package com.tianee.oa.subsys.crm.core.customer.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.dbutils.DbUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.subsys.crm.core.customer.bean.TeeExtendFiled;
import com.tianee.oa.subsys.crm.core.customer.model.TeeExtendFiledModel;
import com.tianee.oa.util.workflow.TeeColumnType;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDbUtility;

@Service
public class TeeCrmCustomerExeFiledService extends TeeBaseService {
/*	@Autowired
	//private TeeCrmCustomerExeFiledDao customerExeFiledDao;
*/
	/**
	 * 添加或更新自定义字段
	 * @param request
	 * @return
	 */
	public void addOrUpdate(TeeExtendFiledModel model) {
		TeeExtendFiled exeFiled = new TeeExtendFiled();
		BeanUtils.copyProperties(model, exeFiled);
		if(!"下拉列表".equals(model.getFiledType())){
			exeFiled.setCodeType(null);
			exeFiled.setSysCode(null);
			exeFiled.setOptionName(null);
			exeFiled.setOptionValue(null);
		}
		if("自定义选项".equals(model.getCodeType())){
			exeFiled.setSysCode(null);
		}
		if("CRM系统编码".equals(model.getCodeType())){
			exeFiled.setOptionName(null);
			exeFiled.setOptionValue(null);
		}
	    simpleDaoSupport.save(exeFiled);
	    updateColumns("EXTRA_"+exeFiled.getSid(),"CRM_CUSTOMER");
	 
	}
	
	/**
	 * 变更客户表-添加或修改客户表字段
	 * @param exeFiled
	 * @param tableName
	 */
	private void updateColumns(String columnName, String tableName) {
		Connection conn = null;
		try{
			conn = TeeDbUtility.getConnection(null);
			conn.setAutoCommit(true);
			DbUtils dbUtils = new DbUtils(conn);
				try{
//					simpleDaoSupport.executeNativeUpdate("alter table "+tableName+" add "+item.getName()+" "+TeeColumnType.getColumnType(TeeColumnType.TEXT),null);
					dbUtils.executeUpdate("alter table "+tableName+" add "+columnName+" "+TeeColumnType.getColumnType(TeeColumnType.TEXT));
				}catch(Exception e){}
				
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
		}
	
	}
	/**
	 * 自定义字段列表
	 * @param dm
	 * @param object
	 * @return
	 */
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm, Object object) {
		 TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		 String hql = "from TeeExtendFiled";
			List<TeeExtendFiledModel> modelList=new ArrayList<TeeExtendFiledModel>();
			List<TeeExtendFiled> list = simpleDaoSupport.pageFind(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
			long total = simpleDaoSupport.count("select count(*) "+hql, null);
			for (int i = 0; i < list.size(); i++) {
				TeeExtendFiledModel model=new TeeExtendFiledModel();
				TeeExtendFiled field=list.get(i);
				BeanUtils.copyProperties(field, model);
				modelList.add(model);
			}
			
			dataGridJson.setRows(modelList);
			dataGridJson.setTotal(total);
			return dataGridJson;
	}
	
	/**
	 * 删除选中字段
	 * @param request
	 * @param sids
	 * @return
	 */
	public void deleteFieldById(int sid) {
		simpleDaoSupport.delete(TeeExtendFiled.class, sid);
	}
	
	/**
	 * 获取自定义字段
	 * @param sid
	 * @return
	 */
	public TeeExtendFiledModel getInfoBySid(int sid) {
		TeeExtendFiled field = (TeeExtendFiled) simpleDaoSupport.get(TeeExtendFiled.class, sid);
		TeeExtendFiledModel model=new TeeExtendFiledModel();
		BeanUtils.copyProperties(field, model);
		return model;
	}
	
	/**
	 * 更新自定义字段
	 * @param model
	 * @param sid
	 */
	public void updateField(TeeExtendFiledModel model, int sid) {
		TeeExtendFiled field = (TeeExtendFiled) simpleDaoSupport.get(TeeExtendFiled.class,sid);
		BeanUtils.copyProperties(model, field);
		if(!"下拉列表".equals(model.getFiledType())){
			field.setCodeType(null);
			field.setSysCode(null);
			field.setOptionName(null);
			field.setOptionValue(null);
		}
		if("自定义选项".equals(model.getCodeType())){
			field.setSysCode(null);
		}
		if("CRM系统编码".equals(model.getCodeType())){
			field.setOptionName(null);
			field.setOptionValue(null);
		}
		simpleDaoSupport.update(field);
		
	}
	
   public List<TeeExtendFiled> getListFieldByCustomer(HttpServletRequest request){
	   List<TeeExtendFiled> list = new ArrayList<TeeExtendFiled>();
	   String hql = "from TeeExtendFiled where 1=1";
	   list = simpleDaoSupport.executeQuery(hql, null);
	   return list;
   }

   /**
    *  获取自定义字段中可以查询的字段集合
    * @param request
    * @return
    */
	public TeeJson getQueryFieldListById(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		List<TeeExtendFiled> list=simpleDaoSupport.executeQuery(" from TeeExtendFiled where  1=1 and isQuery=1 ", null);
		json.setRtState(true);
		json.setRtData(list);
		return json;
	}

	/**
	 * 获取自定义字段中显示的字段集合
	 * @param request
	 * @return
	 */
	public TeeJson getShowFieldListById(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		List<TeeExtendFiled> list=simpleDaoSupport.executeQuery(" from TeeExtendFiled where 1=1 and isShow=1 ", null);
		json.setRtState(true);
		json.setRtData(list);
		return json;
	}

}
