package com.tianee.oa.subsys.bisengin.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import seamoonotp.tea;

import com.tianee.oa.subsys.bisengin.bean.BisDataSource;
import com.tianee.oa.subsys.bisengin.bean.BisTable;
import com.tianee.oa.subsys.bisengin.bean.BisTableField;
import com.tianee.oa.subsys.bisengin.dao.BisTableDao;
import com.tianee.oa.subsys.bisengin.dao.BisTableFieldDao;
import com.tianee.oa.subsys.bisengin.model.BisTableFieldModel;
import com.tianee.oa.subsys.bisengin.util.BisQuery;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDataSource;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class BisTableFieldService extends TeeBaseService{
	@Autowired
	private BisTableFieldDao bisTableFieldDao;
	
	@Autowired
	private BisTableDao bisTableDao;
	
	public void entityToModel(BisTableField bisTableField,BisTableFieldModel bisTableFieldModel){
		BeanUtils.copyProperties(bisTableField, bisTableFieldModel);
		bisTableFieldModel.setBisTableId(bisTableField.getBisTable().getSid());
	}
	
	public void modelToEntity(BisTableFieldModel bisTableFieldModel,BisTableField bisTableField){
		BeanUtils.copyProperties(bisTableFieldModel,bisTableField);
		BisTable bisTable = bisTableDao.get(bisTableFieldModel.getBisTableId());
		bisTableField.setBisTable(bisTable);
	}
	
	public void addBisTableField(BisTableFieldModel bisTableFieldModel){
		BisTableField bisTableField = new BisTableField();
		boolean exists = checkFieldIsExist(bisTableFieldModel.getFieldName(), bisTableFieldModel.getBisTableId());
		if(exists){
			throw new TeeOperationException("该字段标识已存在，请勿输入相同的字段唯一标识。");
		}
		modelToEntity(bisTableFieldModel, bisTableField);
		bisTableFieldDao.save(bisTableField);
	}
	
	public void updateBisTableField(BisTableFieldModel bisTableFieldModel){
		BisTableField bisTableField = new BisTableField();
		modelToEntity(bisTableFieldModel, bisTableField);
		bisTableFieldDao.update(bisTableField);
	}
	
	public BisTableFieldModel getModelById(int sid){
		BisTableFieldModel bisTableFieldModel = new BisTableFieldModel();
		BisTableField bisTableField = bisTableFieldDao.get(sid);
		if(bisTableField!=null){
			entityToModel(bisTableField, bisTableFieldModel);
			bisTableFieldModel.setFieldName(bisTableFieldModel.getFieldName().toUpperCase());
		}
		return bisTableFieldModel;
	}
	
	public BisTableField deleteBisTableField(int sid){
		BisTableField bisTableField = bisTableFieldDao.get(sid);
		bisTableFieldDao.deleteByObj(bisTableField);
		return bisTableField;
	}
	
	public TeeEasyuiDataGridJson datagrid(BisTableFieldModel bisTableFieldModel,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = bisTableFieldDao.datagrid(bisTableFieldModel, dm);
		List<BisTableField> list = dataGridJson.getRows();
		List modelList = new ArrayList();
		for(BisTableField btf:list){
			BisTableFieldModel m = new BisTableFieldModel();
			entityToModel(btf, m);
			modelList.add(m);
		}
		dataGridJson.setRows(modelList);
		return dataGridJson;
	}
	
	/**
	 * 检查字段是否存在
	 * @param fieldName
	 * @param tableId
	 * @return
	 */
	public boolean checkFieldIsExist(String fieldName,int tableId){
		return bisTableFieldDao.checkFieldIsExist(fieldName, tableId);
	}
	
	@Transactional(readOnly=true)
	public void createIndex(int sid){
		BisTableField bisTableField = bisTableFieldDao.get(sid);
		BisTable bisTable = bisTableField.getBisTable();
		if(bisTable==null){
			throw new TeeOperationException("业务表实体不存在");
		}
		
		Connection conn = null;
		BisDataSource bisDataSource = bisTable.getBisDataSource();
		try{
			if(bisDataSource.getDataSource()==1){//内部数据源
				conn = TeeDbUtility.getConnection(null);
			}else{//外部数据源
				TeeDataSource dataSource = new TeeDataSource();
				dataSource.setDriverClass(bisDataSource.getDriverClass());
				dataSource.setPassWord(bisDataSource.getDriverPwd());
				dataSource.setUrl(bisDataSource.getDriverUrl());
				dataSource.setUserName(bisDataSource.getDriverUsername());
				dataSource.setConfigModel(bisDataSource.getConfigModel());
				conn = TeeDbUtility.getConnection(dataSource);
			}
			
			if(conn==null){
				throw new TeeOperationException("数据库连接错误，请查看对应的数据源配置。");
			}
			
			BisQuery bisQuery = new BisQuery(conn);
			
			StringBuffer sql = new StringBuffer();
			sql.append("create index "+bisTable.getTableName()+"_"+bisTableField.getFieldName()+" on "+bisTable.getTableName()+"("+bisTableField.getFieldName()+")");
			
			bisQuery.executeUpdate(sql.toString(), null);
		}catch(Exception e){
			//e.printStackTrace();
			throw new TeeOperationException(e);
		}finally{
			TeeDbUtility.closeConn(conn);
		}
		
	}
	
	@Transactional(readOnly=true)
	public void cancelIndex(int sid){
		BisTableField bisTableField = bisTableFieldDao.get(sid);
		BisTable bisTable = bisTableField.getBisTable();
		if(bisTable==null){
			throw new TeeOperationException("业务表实体不存在");
		}
		
		Connection conn = null;
		BisDataSource bisDataSource = bisTable.getBisDataSource();
		try{
			if(bisDataSource.getDataSource()==1){//内部数据源
				conn = TeeDbUtility.getConnection(null);
			}else{//外部数据源
				TeeDataSource dataSource = new TeeDataSource();
				dataSource.setDriverClass(bisDataSource.getDriverClass());
				dataSource.setPassWord(bisDataSource.getDriverPwd());
				dataSource.setUrl(bisDataSource.getDriverUrl());
				dataSource.setUserName(bisDataSource.getDriverUsername());
				dataSource.setConfigModel(bisDataSource.getConfigModel());
				conn = TeeDbUtility.getConnection(dataSource);
			}
			
			if(conn==null){
				throw new TeeOperationException("数据库连接错误，请查看对应的数据源配置。");
			}
			
			BisQuery bisQuery = new BisQuery(conn);
			
			StringBuffer sql = new StringBuffer();
			sql.append("drop index "+bisTable.getTableName()+"_"+bisTableField.getFieldName()+" on "+bisTable.getTableName());
			
			bisQuery.executeUpdate(sql.toString(), null);
		}catch(Exception e){
			//e.printStackTrace();
			throw new TeeOperationException(e);
		}finally{
			TeeDbUtility.closeConn(conn);
		}
	}
	
	@Transactional(readOnly=true)
	public void changeField(int sid){
		BisTableField bisTableField = bisTableFieldDao.get(sid);
		BisTable bisTable = bisTableField.getBisTable();
		if(bisTable==null){
			throw new TeeOperationException("业务表实体不存在");
		}
		
		Connection conn = null;
		BisDataSource bisDataSource = bisTable.getBisDataSource();
		try{
			if(bisDataSource.getDataSource()==1){//内部数据源
				conn = TeeDbUtility.getConnection(null);
			}else{//外部数据源
				TeeDataSource dataSource = new TeeDataSource();
				dataSource.setDriverClass(bisDataSource.getDriverClass());
				dataSource.setPassWord(bisDataSource.getDriverPwd());
				dataSource.setUrl(bisDataSource.getDriverUrl());
				dataSource.setUserName(bisDataSource.getDriverUsername());
				dataSource.setConfigModel(bisDataSource.getConfigModel());
				conn = TeeDbUtility.getConnection(dataSource);
			}
			
			if(conn==null){
				throw new TeeOperationException("数据库连接错误，请查看对应的数据源配置。");
			}
			
			BisQuery bisQuery = new BisQuery(conn);
			
			//先增加列
			try{
				bisQuery.executeUpdate("alter table "+bisTable.getTableName()+" add column "+bisTableField.getFieldName()+" "+bisTableField.getFieldTypeExt(), null);
				conn.commit();
			}catch(Exception e){
				//再修改列
				bisQuery.executeUpdate("alter table "+bisTable.getTableName()+" modify column "+bisTableField.getFieldName()+" "+bisTableField.getFieldTypeExt(), null);
				conn.commit();
			}
			
		}catch(Exception e){
			//e.printStackTrace();
			throw new TeeOperationException(e);
		}finally{
			TeeDbUtility.closeConn(conn);
		}
	}
	
	/**
	 * 设置主键位
	 * @param sid
	 */
	public void setPrimaryKey(int sid){
		BisTableField bisTableField = bisTableFieldDao.get(sid);
		BisTable bisTable = bisTableField.getBisTable();
		if(bisTable==null){
			throw new TeeOperationException("业务表实体不存在");
		}
		
		Connection conn = null;
		BisDataSource bisDataSource = bisTable.getBisDataSource();
		try{
			if(bisDataSource.getDataSource()==1){//内部数据源
				conn = TeeDbUtility.getConnection(null);
			}else{//外部数据源
				TeeDataSource dataSource = new TeeDataSource();
				dataSource.setDriverClass(bisDataSource.getDriverClass());
				dataSource.setPassWord(bisDataSource.getDriverPwd());
				dataSource.setUrl(bisDataSource.getDriverUrl());
				dataSource.setUserName(bisDataSource.getDriverUsername());
				dataSource.setConfigModel(bisDataSource.getConfigModel());
				conn = TeeDbUtility.getConnection(dataSource);
			}
			
			if(conn==null){
				throw new TeeOperationException("数据库连接错误，请查看对应的数据源配置。");
			}
			
			BisQuery bisQuery = new BisQuery(conn);
			
			//获取所有字段列
			List<BisTableField> fields = simpleDaoSupport.find("from BisTableField where bisTable.sid="+bisTable.getSid(), null);
			//清除之前存在主键的约束列
			for(BisTableField field:fields){
				if(field.getPrimaryKeyFlag()==1){
					try{
						bisQuery.executeUpdate("alter table "+bisTable.getTableName()+" drop constraint "+field.getFieldName(), null);
						conn.commit();
					}catch(Exception e){e.printStackTrace();}
					field.setPrimaryKeyFlag(0);
				}
			}
			
			//设置当前列为主键
			try{
				bisQuery.executeUpdate("alter table "+bisTable.getTableName()+" add constraint "+bisTableField.getFieldName()+" primary key", null);
				conn.commit();
				bisTableField.setPrimaryKeyFlag(1);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}catch(Exception e){
			//e.printStackTrace();
			throw new TeeOperationException(e);
		}finally{
			TeeDbUtility.closeConn(conn);
		}
	}

	/**
	 * 根据表格的id获取字段的列表
	 * @param tableId
	 * @return
	 */
	public List<BisTableField> getFieldsByTableId(int tableId) {
		List<BisTableField> list=new ArrayList<BisTableField>();
		String hql="from BisTableField btf where btf.bisTable.sid="+tableId;
		list=bisTableFieldDao.executeQuery(hql, null);
		return list;
	}
}
