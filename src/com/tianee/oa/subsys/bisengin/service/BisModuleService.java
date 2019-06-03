package com.tianee.oa.subsys.bisengin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.subsys.bisengin.bean.BisModule;
import com.tianee.oa.subsys.bisengin.bean.BisTable;
import com.tianee.oa.subsys.bisengin.bean.BisView;
import com.tianee.oa.subsys.bisengin.model.BisModuleModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDbUtility;

@Service
public class BisModuleService extends TeeBaseService{
	
	public void addBisModule(BisModuleModel bisModuleModel){
		BisModule bisModule = new BisModule();
		BeanUtils.copyProperties(bisModuleModel, bisModule);
		
		BisTable bisTable = new BisTable();
		bisTable.setSid(bisModuleModel.getBisTableId());
		
		BisView bisView = new BisView();
		bisView.setIdentity(bisModuleModel.getBisViewId());
		
		TeeForm form = new TeeForm();
		form.setSid(bisModuleModel.getFormId());
		bisModule.setForm(form);
		bisModule.setBisTable(bisTable);
		bisModule.setBisView(bisView);
		
		bisModule.setCreatePriv(bisModuleModel.getCreatePrivIds());
		bisModule.setDelPriv(bisModuleModel.getDelPrivIds());
		bisModule.setEditPriv(bisModuleModel.getEditPrivIds());
		
		simpleDaoSupport.save(bisModule);
	}
	
	public void updateBisModule(BisModuleModel bisModuleModel){
		BisModule bisModule = (BisModule) simpleDaoSupport.get(BisModule.class, bisModuleModel.getUuid());
		BeanUtils.copyProperties(bisModuleModel, bisModule);
		
		BisTable bisTable = new BisTable();
		bisTable.setSid(bisModuleModel.getBisTableId());
		
		BisView bisView = new BisView();
		bisView.setIdentity(bisModuleModel.getBisViewId());
		
		TeeForm form = new TeeForm();
		form.setSid(bisModuleModel.getFormId());
		bisModule.setForm(form);
		bisModule.setBisTable(bisTable);
		bisModule.setBisView(bisView);
		
		
		bisModule.setCreatePriv(bisModuleModel.getCreatePrivIds());
		bisModule.setDelPriv(bisModuleModel.getDelPrivIds());
		bisModule.setEditPriv(bisModuleModel.getEditPrivIds());
		
		simpleDaoSupport.update(bisModule);
	}
	
	public void deleteBisModule(String uuid){
		simpleDaoSupport.delete(BisModule.class, uuid);
	}
	
	public BisModuleModel getBisModule(String bisModuleId){
		BisModule bisModule = (BisModule) simpleDaoSupport.get(BisModule.class, bisModuleId);
		return Entity2Model(bisModule);
	}
	
	public TeeEasyuiDataGridJson dataGridJson(){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		StringBuffer hql = new StringBuffer("from BisModule");
		List<BisModule> list = simpleDaoSupport.find(hql.toString()+" order by crTime asc", null);
		List modelList = new ArrayList();
		for(BisModule bisModule:list){
			modelList.add(Entity2Model(bisModule));
		}
		
		long total = simpleDaoSupport.count("select count(*) "+hql.toString(), null);
		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);
		return dataGridJson;
	}
	
	public BisModuleModel Entity2Model(BisModule bisModule){
		BisModuleModel bisModuleModel = new BisModuleModel();
		BeanUtils.copyProperties(bisModule, bisModuleModel);
		
		BisTable bisTable = bisModule.getBisTable();
		BisView bisView = bisModule.getBisView();
		TeeForm form = bisModule.getForm();
		
		if(bisTable!=null){
			bisModuleModel.setBisTableId(bisTable.getSid());
			bisModuleModel.setBisTableName(bisTable.getTableName()+"-"+bisTable.getTableDesc());
		}
		if(bisView!=null){
			bisModuleModel.setBisViewId(bisView.getIdentity());
			bisModuleModel.setBisViewName(bisView.getName());
		}
		if(form!=null){
			bisModuleModel.setFormId(form.getSid());
			bisModuleModel.setFormName(form.getFormName());
		}
		
		StringBuffer ids = new StringBuffer();
		StringBuffer names = new StringBuffer();
		
		List<Map> persons = simpleDaoSupport.getMaps("select uuid as UUID,userName as USERNAME from TeePerson where "+TeeDbUtility.IN("uuid", bisModule.getCreatePriv()), null);
		
		for(Map data:persons){
			ids.append(data.get("UUID")+",");
			names.append(data.get("USERNAME")+",");
		}
		if(ids.length()!=0){
			ids.deleteCharAt(ids.length()-1);
			names.deleteCharAt(names.length()-1);
		}
		bisModuleModel.setCreatePrivIds(ids.toString());
		bisModuleModel.setCreatePrivNames(names.toString());
		
		ids.delete(0, ids.length());
		names.delete(0, names.length());
		
		
		persons = simpleDaoSupport.getMaps("select uuid as UUID,userName as USERNAME from TeePerson where "+TeeDbUtility.IN("uuid", bisModule.getDelPriv()), null);
		
		for(Map data:persons){
			ids.append(data.get("UUID")+",");
			names.append(data.get("USERNAME")+",");
		}
		if(ids.length()!=0){
			ids.deleteCharAt(ids.length()-1);
			names.deleteCharAt(names.length()-1);
		}
		bisModuleModel.setDelPrivIds(ids.toString());
		bisModuleModel.setDelPrivNames(names.toString());
		
		ids.delete(0, ids.length());
		names.delete(0, names.length());
		
		
		persons = simpleDaoSupport.getMaps("select uuid as UUID,userName as USERNAME from TeePerson where "+TeeDbUtility.IN("uuid", bisModule.getEditPriv()), null);
		
		for(Map data:persons){
			ids.append(data.get("UUID")+",");
			names.append(data.get("USERNAME")+",");
		}
		if(ids.length()!=0){
			ids.deleteCharAt(ids.length()-1);
			names.deleteCharAt(names.length()-1);
		}
		bisModuleModel.setEditPrivIds(ids.toString());
		bisModuleModel.setEditPrivNames(names.toString());
		
		ids.delete(0, ids.length());
		names.delete(0, names.length());
		
		
		return bisModuleModel;
	}
}
