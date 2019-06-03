package com.tianee.oa.core.base.pm.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.dbutils.DbUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.tianee.oa.core.base.pm.bean.TeePmCustomerField;
import com.tianee.oa.core.base.pm.model.TeePmCustomerFieldModel;
import com.tianee.oa.util.workflow.TeeColumnType;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDbUtility;

@Service
public class TeePmCustomerFieldService extends TeeBaseService {

	/**
	 * 添加自定义字段
	 * @param model
	 */
	public void addOrUpdate(TeePmCustomerFieldModel model) {
		TeePmCustomerField exeField = new TeePmCustomerField();
		BeanUtils.copyProperties(model, exeField);
		if(!"下拉列表".equals(model.getFiledType())){
			exeField.setCodeType(null);
			exeField.setSysCode(null);
			exeField.setOptionName(null);
			exeField.setOptionValue(null);
		}
		if("自定义选项".equals(model.getCodeType())){
			exeField.setSysCode(null);
		}
		if("HR系统编码".equals(model.getCodeType())){
			exeField.setOptionName(null);
			exeField.setOptionValue(null);
		}
	    simpleDaoSupport.save(exeField);
	    updateColumns("EXTRA_"+exeField.getSid(),"PERSONNEL_MANAGEMENT");
	 
	}
	
	/**
	 * 变更人事档案表--添加自定义字段
	 * @param columnName
	 * @param tableName
	 */
	private void updateColumns(String columnName, String tableName) {
		Connection conn = null;
		try{
			conn = TeeDbUtility.getConnection(null);
			conn.setAutoCommit(true);
			DbUtils dbUtils = new DbUtils(conn);
				try{
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

	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm, Object object) {
		 TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		 String hql = "from TeePmCustomerField";
			List<TeePmCustomerFieldModel> modelList=new ArrayList<TeePmCustomerFieldModel>();
			List<TeePmCustomerField> list = simpleDaoSupport.pageFind(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
			long total = simpleDaoSupport.count("select count(*) "+hql, null);
			for (int i = 0; i < list.size(); i++) {
				TeePmCustomerFieldModel model=new TeePmCustomerFieldModel();
				TeePmCustomerField field=list.get(i);
				BeanUtils.copyProperties(field, model);
				modelList.add(model);
			}
			
			dataGridJson.setRows(modelList);
			dataGridJson.setTotal(total);
			return dataGridJson;
	}
	
	

	public void deleteFieldById(int sid) {
		simpleDaoSupport.delete(TeePmCustomerField.class, sid);
		}

	public TeePmCustomerFieldModel getInfoBySid(int sid) {
		TeePmCustomerField field = (TeePmCustomerField) simpleDaoSupport.get(TeePmCustomerField.class, sid);
		TeePmCustomerFieldModel model=new TeePmCustomerFieldModel();
		BeanUtils.copyProperties(field, model);
		return model;
	}

	public void updateField(TeePmCustomerFieldModel model, int sid) {
		TeePmCustomerField field = (TeePmCustomerField) simpleDaoSupport.get(TeePmCustomerField.class,sid);
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
		if("HR系统编码".equals(model.getCodeType())){
			field.setOptionName(null);
			field.setOptionValue(null);
		}
		simpleDaoSupport.update(field);
		
	}
	
	/**
	 * 获取自定义字段列表
	 * @param request
	 * @return
	 */
	 public List<TeePmCustomerField> getListFieldByHuman(HttpServletRequest request){
		   List<TeePmCustomerField> list = new ArrayList<TeePmCustomerField>();
		   String hql = "from TeePmCustomerField where 1=1";
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
			List<TeePmCustomerField> list=simpleDaoSupport.executeQuery(" from TeePmCustomerField where  1=1 and isQuery=1 ", null);
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
			List<TeePmCustomerField> list=simpleDaoSupport.executeQuery(" from TeePmCustomerField where 1=1 and isShow=1 ", null);
			json.setRtState(true);
			json.setRtData(list);
			return json;
		}
		
		
		public TeeEasyuiDataGridJson datagrid2() {
			 TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
			 String hql = "from TeePmCustomerField";
				List<TeePmCustomerFieldModel> modelList=new ArrayList<TeePmCustomerFieldModel>();
				List<TeePmCustomerField> list = simpleDaoSupport.find(hql, null);
				for (int i = 0; i < list.size(); i++) {
					TeePmCustomerFieldModel model=new TeePmCustomerFieldModel();
					TeePmCustomerField field=list.get(i);
					BeanUtils.copyProperties(field, model);
					modelList.add(model);
				}
				
				dataGridJson.setRows(modelList);
				return dataGridJson;
		}

}
