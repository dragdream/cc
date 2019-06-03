package com.tianee.oa.core.tpl.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.tpl.bean.TeeWordModel;
import com.tianee.oa.core.tpl.model.TeeWordModelModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeWordModelService extends TeeBaseService{
	
	public void saveWordModel(TeeWordModelModel wordModelModel){
		TeeWordModel model = new TeeWordModel();
		modelToEntity(wordModelModel,model);
		TeeAttachment attach = (TeeAttachment) simpleDaoSupport.get(TeeAttachment.class, wordModelModel.getAttachId());
		model.setAttach(attach);
		simpleDaoSupport.save(model);
		if(attach!=null){
			attach.setModelId(String.valueOf(model.getSid()));
			simpleDaoSupport.update(attach);
		}
	}
	
	public void update(TeeWordModelModel wordModelModel){
		TeeWordModel wordModel = new TeeWordModel();
		modelToEntity(wordModelModel,wordModel);
		TeeAttachment attach = (TeeAttachment) simpleDaoSupport.get(TeeAttachment.class, wordModelModel.getAttachId());
		wordModel.setAttach(attach);
		simpleDaoSupport.update(wordModel);
		if(attach!=null){
			attach.setModelId(String.valueOf(wordModel.getSid()));
			simpleDaoSupport.update(attach);
		}
	}
	
	public TeeWordModel getWordModel(int sid){
		return (TeeWordModel) simpleDaoSupport.get(TeeWordModel.class, sid);
	}
	
	public TeeWordModelModel getWordModelModel(int sid){
		TeeWordModelModel model = new TeeWordModelModel();
		TeeWordModel wm = getWordModel(sid);
		entityToModel(wm, model, true);
		return model;
	}
	
	public TeeWordModel deleteWordModel(int sid){
		TeeWordModel model = getWordModel(sid);
		simpleDaoSupport.deleteByObj(model);
		return model;
	}
	
	/**
	 * 根据权限取出套红模板
	 * @param userUuid
	 * @return
	 */
	public List<TeeWordModelModel> getWordModelListByPriv(int userUuid,String wordModelType){
		TeePerson person = (TeePerson) simpleDaoSupport.get(TeePerson.class, userUuid);
		String hql="";
		if(wordModelType!=null && !("").equals(wordModelType)){
		  hql = "from TeeWordModel wm where  ((exists (select 1 from wm.userPriv userPriv where userPriv.uuid="+person.getUuid()+") " +
					" or exists (select 1 from wm.deptPriv deptPriv where deptPriv.uuid="+person.getDept().getUuid()+") " +
							" or exists (select 1 from wm.rolePriv rolePriv where rolePriv.uuid="+person.getUserRole().getUuid()+") " +
									" and wm.privRanges=2) or wm.privRanges=1)  and wm.wordModelType='"+wordModelType+"' order by wm.sortNo asc";
			
		}else{
			hql = "from TeeWordModel wm where (exists (select 1 from wm.userPriv userPriv where userPriv.uuid="+person.getUuid()+") " +
					" or exists (select 1 from wm.deptPriv deptPriv where deptPriv.uuid="+person.getDept().getUuid()+") " +
							" or exists (select 1 from wm.rolePriv rolePriv where rolePriv.uuid="+person.getUserRole().getUuid()+") " +
									" and wm.privRanges=2) or wm.privRanges=1  order by wm.sortNo asc";
			
		}
		
		
		List<TeeWordModelModel> list = new ArrayList<TeeWordModelModel>();
		List<TeeWordModel> models = simpleDaoSupport.pageFind(hql, 0, Integer.MAX_VALUE, null);
		for(TeeWordModel wordModel:models){
			TeeWordModelModel wordModelModel = new TeeWordModelModel();
			entityToModel(wordModel, wordModelModel, true);
			list.add(wordModelModel);
		}
		
		return list;
	}
	
	/**
	 * 查询管理
	 * @param dm
	 * @param requestData
	 * @return
	 */
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,Map requestData){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String hql = "from TeeWordModel wm ";
		List<TeeWordModel> wordModelList = simpleDaoSupport.pageFind(hql+"order by wm.sortNo asc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		long total = simpleDaoSupport.count("select count(wm.sid) "+hql, null);
		List<TeeWordModelModel> modelList = new ArrayList<TeeWordModelModel>();
		for(TeeWordModel wm:wordModelList){
			TeeWordModelModel model = new TeeWordModelModel();
			entityToModel(wm, model, true);
			model.setWordModelType(TeeSysCodeManager.getChildSysCodeNameCodeNo("THLX",wm.getWordModelType()));
			modelList.add(model);
		}
		
		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	/**
	 * 模型转实体
	 * @param wordModelModel
	 * @param wordModel
	 */
	public void modelToEntity(TeeWordModelModel wordModelModel,TeeWordModel wordModel){
		BeanUtils.copyProperties(wordModelModel, wordModel);
		
		wordModel.getUserPriv().clear();
		int userIds [] = TeeStringUtil.parseIntegerArray(wordModelModel.getUserPrivIds());
		for(int uuid:userIds){
			if(uuid==0){
				continue;
			}
			wordModel.getUserPriv().add((TeePerson)simpleDaoSupport.get(TeePerson.class, uuid));
		}
		
		wordModel.getDeptPriv().clear();
		int deptIds [] = TeeStringUtil.parseIntegerArray(wordModelModel.getDeptPrivIds());
		for(int uuid:deptIds){
			if(uuid==0){
				continue;
			}
			wordModel.getDeptPriv().add((TeeDepartment)simpleDaoSupport.get(TeeDepartment.class, uuid));
		}
		
		wordModel.getRolePriv().clear();
		int roleIds [] = TeeStringUtil.parseIntegerArray(wordModelModel.getRolePrivIds());
		for(int uuid:roleIds){
			if(uuid==0){
				continue;
			}
			wordModel.getRolePriv().add((TeeUserRole)simpleDaoSupport.get(TeeUserRole.class, uuid));
		}
		
	}
	
	/**
	 * 实体转换成模型
	 * @param wordModel
	 * @param wordModelModel
	 * @param extra
	 */
	public void entityToModel(TeeWordModel wordModel,TeeWordModelModel wordModelModel,boolean extra){
		BeanUtils.copyProperties(wordModel, wordModelModel);
		if(wordModel.getAttach()!=null){
			wordModelModel.setAttachId(wordModel.getAttach().getSid());
			wordModelModel.setFileName(wordModel.getAttach().getFileName());
		}else{
			return;
		}
		
		if(!extra){
			return;
		}
		
		StringBuffer ids = new StringBuffer();
		StringBuffer names = new StringBuffer();
		
		Set<TeePerson> persons = wordModel.getUserPriv();
		for(TeePerson person:persons){
			ids.append(person.getUuid()+",");
			names.append(person.getUserName()+",");
		}
		wordModelModel.setUserPrivIds(ids.toString());
		wordModelModel.setUserPrivNames(names.toString());
		ids.delete(0, ids.length());
		names.delete(0, names.length());
		
		Set<TeeDepartment> deptPriv = wordModel.getDeptPriv();
		for(TeeDepartment dept:deptPriv){
			ids.append(dept.getUuid()+",");
			names.append(dept.getDeptName()+",");
		}
		wordModelModel.setDeptPrivIds(ids.toString());
		wordModelModel.setDeptPrivNames(names.toString());
		ids.delete(0, ids.length());
		names.delete(0, names.length());
		
		Set<TeeUserRole> rolePriv = wordModel.getRolePriv();
		for(TeeUserRole role:rolePriv){
			ids.append(role.getUuid()+",");
			names.append(role.getRoleName()+",");
		}
		wordModelModel.setRolePrivIds(ids.toString());
		wordModelModel.setRolePrivNames(names.toString());
		
	}
	
	
}
