package com.tianee.oa.subsys.doc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.doc.bean.TeeDocumentRecMapping;
import com.tianee.oa.subsys.doc.model.TeeDocumentRecMappingModel;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeDocumentRecMappingService extends TeeBaseService{
	
	public void addOrUpdate(Map requestData){
		String uuid = TeeStringUtil.getString(requestData.get("uuid"));
		int deptId = TeeStringUtil.getInteger(requestData.get("deptId"), 0);
		String privUserIds = TeeStringUtil.getString(requestData.get("privUserIds"));
		
		TeeDocumentRecMapping documentRecMapping = null;
		
		/*long deptCount = simpleDaoSupport.count("select count(*) from TeeDocumentRecMapping where dept.uuid=?", new Object[]{deptId});
		if(deptCount!=0){
			throw new TeeOperationException("已存在该部门的收文员规则项，请勿重复添加");
		}*/
		
		if("".equals(uuid)){//添加
			documentRecMapping = new TeeDocumentRecMapping();
			TeeDepartment dept = new TeeDepartment();
			dept.setUuid(deptId);
			documentRecMapping.setDept(dept);
			
			String sp[] = privUserIds.split(",");
			for(String userId:sp){
				TeePerson p = new TeePerson();
				if(!"".equals(userId)){
					p.setUuid(Integer.parseInt(userId));
					documentRecMapping.getPrivUsers().add(p);
				}
			}
			simpleDaoSupport.save(documentRecMapping);
		}else{//更新
			
			documentRecMapping=(TeeDocumentRecMapping) simpleDaoSupport.get(TeeDocumentRecMapping.class,uuid);
			//清空人员
			documentRecMapping.getPrivUsers().clear();
			String sp[] = privUserIds.split(",");
			for(String userId:sp){
				TeePerson p = new TeePerson();
				if(!"".equals(userId)){
					p.setUuid(Integer.parseInt(userId));
					documentRecMapping.getPrivUsers().add(p);
				}
			}
			simpleDaoSupport.update(documentRecMapping);
		}
	}
	
	public void delete(String uuid){
		simpleDaoSupport.delete(TeeDocumentRecMapping.class, uuid);
	}
	
	public TeeDocumentRecMappingModel get(String uuid){
		TeeDocumentRecMapping entity = (TeeDocumentRecMapping) simpleDaoSupport.get(TeeDocumentRecMapping.class, uuid);
		return parseToModel(entity);
	}
	
	public List<TeeDocumentRecMappingModel> list(){
		List<TeeDocumentRecMappingModel> list = new ArrayList(0);
		List<TeeDocumentRecMapping> mappings = simpleDaoSupport.find("from TeeDocumentRecMapping order by dept.deptNo asc", null);
		for(TeeDocumentRecMapping documentRecMapping : mappings){
			list.add(parseToModel(documentRecMapping));
		}
		return list;
	}
	
	public static TeeDocumentRecMappingModel parseToModel(TeeDocumentRecMapping documentRecMapping){
		TeeDocumentRecMappingModel documentRecMappingModel = new TeeDocumentRecMappingModel();
		TeeDepartment dept = documentRecMapping.getDept();
		documentRecMappingModel.setUuid(documentRecMapping.getUuid());
		documentRecMappingModel.setDeptId(dept.getUuid());
		documentRecMappingModel.setDeptName(dept.getDeptName());
		
		Set<TeePerson> persons = documentRecMapping.getPrivUsers();
		StringBuffer ids = new StringBuffer();
		StringBuffer names = new StringBuffer();
		
		for(TeePerson p:persons){
			ids.append(p.getUuid()+",");
			names.append(p.getUserName()+",");
		}
		
		if(ids.length()!=0){
			ids.deleteCharAt(ids.length()-1);
			names.deleteCharAt(names.length()-1);
		}
		
		documentRecMappingModel.setPrivUserIds(ids.toString());
		documentRecMappingModel.setPrivUserNames(names.toString());
		
		return documentRecMappingModel;
	}

	/**
	 * 根据部门id获取收文员设置信息
	 * @param depId
	 * @return
	 */
	public TeeDocumentRecMappingModel getDocRecMappingByDeptId(int deptId) {
		String hql="from TeeDocumentRecMapping t where t.dept.uuid="+deptId;
		TeeDocumentRecMapping m=(TeeDocumentRecMapping) simpleDaoSupport.unique(hql, null);
		TeeDocumentRecMappingModel model=null;
		if(m!=null){
			model=parseToModel(m);
		}
		return model;
		
	}
	
}
