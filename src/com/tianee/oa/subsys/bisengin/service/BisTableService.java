package com.tianee.oa.subsys.bisengin.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.subsys.bisengin.bean.BisCategory;
import com.tianee.oa.subsys.bisengin.bean.BisDataSource;
import com.tianee.oa.subsys.bisengin.bean.BisTable;
import com.tianee.oa.subsys.bisengin.bean.BisTableEngine;
import com.tianee.oa.subsys.bisengin.bean.BisTableField;
import com.tianee.oa.subsys.bisengin.bean.BisView;
import com.tianee.oa.subsys.bisengin.bean.BisViewListItem;
import com.tianee.oa.subsys.bisengin.dao.BisCatDao;
import com.tianee.oa.subsys.bisengin.dao.BisTableDao;
import com.tianee.oa.subsys.bisengin.dao.BisTableFieldDao;
import com.tianee.oa.subsys.bisengin.model.BisTableModel;
import com.tianee.oa.subsys.bisengin.util.BisQuery;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDataSource;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class BisTableService extends TeeBaseService{
	@Autowired
	private BisTableDao bisTableDao;
	
	@Autowired
	private BisTableFieldDao bisTableFieldDao;
	
	@Autowired
	private BisCatDao bisCatDao;
	
	public void modelToEntity(BisTableModel bisTableModel,BisTable bisTable){
		BeanUtils.copyProperties(bisTableModel, bisTable);
		BisCategory bisCat = bisCatDao.get(bisTableModel.getBisCatId());
		BisDataSource bisDataSource = new BisDataSource();
		bisDataSource.setSid(bisTableModel.getDataSource());
		bisTable.setBisCat(bisCat);
		bisTable.setBisDataSource(bisDataSource);
	}
	
	public void entityToModel(BisTable bisTable,BisTableModel bisTableModel){
		BeanUtils.copyProperties(bisTable, bisTableModel);
		if(bisTable.getBisCat()!=null){
			bisTableModel.setBisCatId(bisTable.getBisCat().getSid());
		}
		//获取对应的流程名称
		Map<String,String> data = simpleDaoSupport.getMap("select ft.flowName as FLOWNAME from TeeFlowType ft where ft.sid="+bisTableModel.getFlowId(), null);
		if(data!=null){
			bisTableModel.setFlowName(data.get("FLOWNAME"));
		}
		BisDataSource bisDataSource = bisTable.getBisDataSource();
		if(bisDataSource!=null){
			bisTableModel.setDataSource(bisDataSource.getSid());
		}
	}
	
	public void addBisTable(BisTableModel bisTableModel){
		BisTable bisTable = new BisTable();
		modelToEntity(bisTableModel,bisTable);
		bisTableDao.save(bisTable);
	}
	
	public void updateBisTable(BisTableModel bisTableModel){
		BisTable bisTable = new BisTable();
		modelToEntity(bisTableModel,bisTable);
		bisTableDao.update(bisTable);
	}
	
	public void updateBisTableModel(BisTableModel bisTableModel){
		BisTable bisTable = (BisTable) simpleDaoSupport.get(BisTable.class,bisTableModel.getSid());
		bisTable.setBisModel(bisTableModel.getBisModel());
		bisTable.setFlowId(bisTableModel.getFlowId());
	}
	
	public BisTableModel getModelById(int sid){
		BisTableModel bisTableModel = new BisTableModel();
		BisTable bisTable = bisTableDao.get(sid);
		entityToModel(bisTable,bisTableModel);
		if(bisTable.getBisDataSource()!=null){
			bisTableModel.setDbType(bisTable.getBisDataSource().getDbType());
		}
		return bisTableModel;
	}
	
	public BisTable deleteBisTable(int sid){
		BisTable bisTable = bisTableDao.get(sid);
		bisTableDao.deleteByObj(bisTable);
		//删除字段
		bisTableDao.executeUpdate("delete BisTableField where bisTable.sid=?", new Object[]{sid});
		return bisTable;
	}
	
	public TeeEasyuiDataGridJson datagrid(BisTableModel bisTableModel,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = bisTableDao.datagrid(bisTableModel, dm);
		List<BisTable> list = dataGridJson.getRows();
		List<BisTableModel> mlist = new ArrayList<BisTableModel>();
		for(BisTable bt:list){
			BisTableModel m = new BisTableModel();
			entityToModel(bt, m);
			mlist.add(m);
		}
		dataGridJson.setRows(mlist);
		return dataGridJson;
	}
	
	/**
	 * 根据数据源获取表格
	 * @param dataSource
	 * @return
	 */
	public TeeEasyuiDataGridJson findTableByDatasource(int dataSource){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		List<BisTable> list = simpleDaoSupport.find("from BisTable where bisDataSource.sid=? order by sid asc", new Object[]{dataSource});
		List<BisTableModel> mlist = new ArrayList<BisTableModel>();
		for(BisTable bt:list){
			BisTableModel m = new BisTableModel();
			entityToModel(bt, m);
			mlist.add(m);
		}
		dataGridJson.setRows(mlist);
		dataGridJson.setTotal(Long.parseLong(mlist.size()+""));
		return dataGridJson;
	}
	
	/**
	 * 生成bisTable业务实体表
	 * @param bisTable
	 */
	public void generateBisTable(int bisTableId){
		BisTable bisTable = bisTableDao.get(bisTableId);
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
			sql.append("create table "+bisTable.getTableName()+"(");
			String ext = null;
			
			//遍历所有字段对象
			List<BisTableField> fields = bisTableFieldDao.find("from BisTableField btf where btf.bisTable.sid="+bisTable.getSid(), null);
			for(int i=0;i<fields.size();i++){
				BisTableField field = fields.get(i);
				if(field.getPrimaryKeyFlag()==1){
					ext = BisTableField.getBasicFieldExt(bisTable.getBisDataSource().getDbType(), field.getFieldType());
					sql.append(field.getFieldName()+" "+ext+" primary key ");
					if(field.getGeneratedType()==1){//本地递增策略
						if("MYSQL".equals(bisTable.getBisDataSource().getDbType())){//MYSQL
							sql.append(" auto_increment");//
						}else if("SQLSERVER".equals(bisTable.getBisDataSource().getDbType())){//SQLSERVER
							sql.append(" identity(1,1)");
						}
					}
					break;
				}
			}
			for(int i=0;i<fields.size();i++){
				BisTableField field = fields.get(i);
				if(field.getPrimaryKeyFlag()==1){
					continue;
				}
				ext = BisTableField.getBasicFieldExt(bisTable.getBisDataSource().getDbType(), field.getFieldType());
				sql.append(","+field.getFieldName()+" "+ext);
			}
			sql.append(")");
			
			bisQuery.executeUpdate(sql.toString(), null);
			bisTable.setGen(1);
			
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			throw new TeeOperationException(e);
		}finally{
			TeeDbUtility.closeConn(conn);
		}
		
	}
	
	/**
	 * 删除bisTable业务实体表
	 * @param bisTable
	 */
	public void dropBisTable(int bisTableId){
		BisTable bisTable = bisTableDao.get(bisTableId);
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
			BisQuery bisQuery = new BisQuery(conn);
			bisQuery.executeUpdate("drop table "+bisTable.getTableName(), null);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			TeeDbUtility.closeConn(conn);
		}
	}
	
	public BisTable getBisTableByName(String bisTableName){
		return (BisTable) simpleDaoSupport.unique("from BisTable bt where bt.tableName=?", new Object[]{bisTableName});
	}
	
	
	public String exportXml(int sid){
		BisTable bisTable = (BisTable) simpleDaoSupport.get(BisTable.class,sid);
		Element root;   
		root=new Element("BisTable");
		
		root.addContent(new Element("tableName").setText(TeeStringUtil.getString(bisTable.getTableName())));
		root.addContent(new Element("tableDesc").setText(TeeStringUtil.getString(bisTable.getTableDesc())));
		root.addContent(new Element("alias").setText(TeeStringUtil.getString(bisTable.getAlias())));
		
		//存放表单字段
		Element items = new Element("Fields");
		List<BisTableField> fields = simpleDaoSupport.find("from BisTableField where bisTable.sid="+sid, null);
		for(BisTableField field:fields){
			Element item = new Element("Field");
			item.addContent(new Element("fieldName").setText(TeeStringUtil.getString(field.getFieldName())));
			item.addContent(new Element("fieldDesc").setText(TeeStringUtil.getString(field.getFieldDesc())));
			item.addContent(new Element("alias").setText(TeeStringUtil.getString(field.getAlias())));
			item.addContent(new Element("fieldType").setText(TeeStringUtil.getString(field.getFieldType())));
			item.addContent(new Element("fieldTypeExt").setText(TeeStringUtil.getString(field.getFieldTypeExt())));
			item.addContent(new Element("primaryKeyFlag").setText(TeeStringUtil.getString(field.getPrimaryKeyFlag())));
			item.addContent(new Element("generatedType").setText(TeeStringUtil.getString(field.getGeneratedType())));
			item.addContent(new Element("generatePlugin").setText(TeeStringUtil.getString(field.getGeneratePlugin())));
			item.addContent(new Element("defaultVal").setText(TeeStringUtil.getString(field.getDefaultVal())));
			
			//字段显示类型
			item.addContent(new Element("fieldDisplayType").setText(TeeStringUtil.getString(field.getFieldDisplayType())));
			//字段控制模型
			item.addContent(new Element("fieldControlModel").setText(TeeStringUtil.getString(field.getFieldControlModel())));
			
			items.addContent(item);
		}
		root.addContent(items);
		
		//存放业务引擎
		Element items1 = new Element("Engines");
		List<BisTableEngine> engines = simpleDaoSupport.find("from BisTableEngine where bisTable.sid="+sid, null);;
		for(BisTableEngine engine:engines){
			Element item = new Element("Engine");
			item.addContent(new Element("flowId").setText(TeeStringUtil.getString(engine.getFlowId())));
			item.addContent(new Element("type").setText(TeeStringUtil.getString(engine.getType())));
			item.addContent(new Element("listTitle").setText(TeeStringUtil.getString(engine.getListTitle())));
			item.addContent(new Element("bisModel").setText(TeeStringUtil.getString(engine.getBisModel())));
			item.addContent(new Element("remark").setText(TeeStringUtil.getString(engine.getRemark())));
			item.addContent(new Element("status").setText(TeeStringUtil.getString(engine.getStatus())));
			items1.addContent(item);
		}
		root.addContent(items1);
		
		Document doc = new Document(root);   
        XMLOutputter out = new XMLOutputter();   
        
        String str = out.outputString(doc);
        
		return str;
	}
	
	
	
	public void importXml(InputStream in,int catId) throws JDOMException{
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try {
			doc = builder.build(in);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Element root = doc.getRootElement();
		
		String tableName = root.getChildText("tableName");
		String tableDesc = root.getChildText("tableDesc");
		String alias = root.getChildText("alias");
		
		//保存BisTable
		BisTable bisTable = new BisTable();
		bisTable.setAlias(alias);
		bisTable.setTableDesc(tableDesc);
		bisTable.setTableName(tableName);
		if(catId!=0){
			BisCategory bisCategory = new BisCategory();
			bisCategory.setSid(catId);
			bisTable.setBisCat(bisCategory);
		}
		
		BisDataSource bisDataSource = new BisDataSource();
		bisTable.setBisDataSource(bisDataSource);
		bisDataSource.setSid(1);
		
		simpleDaoSupport.save(bisTable);
		
		
		//保存BisTableField
		Element ItemsElement = root.getChild("Fields");
		List<Element> items = ItemsElement.getChildren();
		for(Element item:items){
			BisTableField field = new  BisTableField();
			field.setFieldName(item.getChild("fieldName").getText());
			field.setFieldDesc(item.getChild("fieldDesc").getText());
			field.setAlias(item.getChild("alias").getText());
			field.setFieldType(item.getChild("fieldType").getText());
			field.setFieldTypeExt(item.getChild("fieldTypeExt").getText());
			field.setPrimaryKeyFlag(Integer.parseInt(item.getChild("primaryKeyFlag").getText()));
			field.setGeneratePlugin(item.getChild("generatePlugin").getText());
			field.setGeneratedType(Integer.parseInt(item.getChild("generatedType").getText()));
			field.setDefaultVal(item.getChild("defaultVal").getText());
			
			//字段显示类型  
			field.setFieldDisplayType(item.getChild("fieldDisplayType").getText());
			//字段控制模型
			field.setFieldControlModel(item.getChild("fieldControlModel").getText());
			
			field.setBisTable(bisTable);
			
			simpleDaoSupport.save(field);
		}
		
		//保存业务引擎
		ItemsElement = root.getChild("Engines");
		items = ItemsElement.getChildren();
		for(Element item:items){
			BisTableEngine bisTableEngine = new BisTableEngine();
			bisTableEngine.setFlowId(Integer.parseInt(item.getChild("flowId").getText()));
			bisTableEngine.setType(Integer.parseInt(item.getChild("type").getText()));
			bisTableEngine.setListTitle(item.getChild("listTitle").getText());
			bisTableEngine.setBisModel(item.getChild("bisModel").getText());
			bisTableEngine.setRemark(item.getChild("remark").getText());
			bisTableEngine.setStatus(Integer.parseInt(item.getChild("status").getText()));
			bisTableEngine.setBisTable(bisTable);
			
			simpleDaoSupport.save(bisTableEngine);
		}
		
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
