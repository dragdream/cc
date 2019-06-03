package com.tianee.oa.subsys.crm.core.base.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.subsys.crm.core.base.bean.TeeCrmSalesGroup;
import com.tianee.oa.subsys.crm.core.base.dao.TeeCrmSalesGroupDao;
import com.tianee.oa.subsys.crm.core.base.model.TeeCrmSalesGroupModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeCrmSalesGroupService extends TeeBaseService {
	@Autowired
    TeeCrmSalesGroupDao crmProductsTypeDao;
	
	@Autowired
	TeePersonDao personDao;
	
	/**
	 * 新建或者更新
	 * @param request 
	 * @param object 模型
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeCrmSalesGroupModel object){
		TeeJson json = new TeeJson();
		TeeCrmSalesGroup type = new TeeCrmSalesGroup();
		BeanUtils.copyProperties(object, type);
		TeeCrmSalesGroup parentType = null;//上级产品类型
		String parentPath = "";//上级产品类型路径
		if(object.getParentId() > 0){
			parentType = crmProductsTypeDao.get(object.getParentId());
		}
		if(parentType != null){
			parentPath = parentType.getParentPath() + parentType.getSid() + "/";
		}
		type.setParentPath(parentPath);
		type.setParentGroup(parentType);
		//负责人
		if(object.getManagerUserId() > 0){
			TeePerson p = personDao.selectPersonById(object.getManagerUserId());
			type.setManagerUser(p);
		}
		//成员
		
		if(!TeeUtility.isNullorEmpty(object.getManagerMemberIds())){
			List<TeePerson> pl = personDao.getPersonByUuids(object.getManagerMemberIds());
			type.setManagerMember(pl);
		}
		if(object.getSid() > 0){
			TeeCrmSalesGroup typeOld = crmProductsTypeDao.get(object.getSid());
			
			//更新下级上级
			String oldMenuId = TeeStringUtil.getString(typeOld.getParentPath());
			crmProductsTypeDao.updateProductsTypeParentPath(oldMenuId, parentPath, typeOld.getSid());
			int sid = typeOld.getSid();
			BeanUtils.copyProperties(object, typeOld);
			typeOld.setSid(sid);
			typeOld.setManagerMember(type.getManagerMember());
			typeOld.setManagerUser(type.getManagerUser());
			typeOld.setParentPath(type.getParentPath());
			typeOld.setParentGroup(type.getParentGroup());
			crmProductsTypeDao.update(typeOld);
			
			
		}else{
	
			crmProductsTypeDao.save(type);
		}
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取所有的销售组
	 * @param request
	 * @return
	 */
	public TeeJson getAllGroup(HttpServletRequest request){
		TeeJson json = new TeeJson();
		List<TeeCrmSalesGroup> list = crmProductsTypeDao.getCrmAllGroup();
		List<TeeCrmSalesGroupModel> listModel = new ArrayList<TeeCrmSalesGroupModel>();
		for (int i = 0; i < list.size(); i++) {
			TeeCrmSalesGroup group = list.get(i);
			TeeCrmSalesGroupModel model = parseModel(group);
			listModel.add(model);
		}
		json.setRtState(true);
		json.setRtData(listModel);
		return json;
	}
	
	/**
	 * 获取产品类型数
	 * @param request
	 * @return
	 */
	public TeeJson getGroupTree(HttpServletRequest request){
		TeeJson json = new TeeJson();
		List<TeeCrmSalesGroup> list = crmProductsTypeDao.getCrmAllGroup();
		List<TeeZTreeModel> ztreeList = new ArrayList<TeeZTreeModel>();
		for (int i = 0; i < list.size(); i++) {
			TeeCrmSalesGroup type = list.get(i);
			String pId = "0";
			TeeCrmSalesGroup parentType = type.getParentGroup();
			if(parentType != null){
				pId = parentType.getSid() + "";
			}
			TeeZTreeModel ztreeModel = new TeeZTreeModel(type.getSid() + "" , type.getGroupName() , true , pId  , true , "" , "" , true);
			ztreeModel.setParent(false);
			ztreeList.add(ztreeModel);
		}
		json.setRtState(true);
		json.setRtData(ztreeList);
		return json;
	}
	
	/**
	 * 获取上级产品类型树， 
	 * @param request
	 * @return
	 */
	public TeeJson getParentGroupTree(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		String typeStr = TeeStringUtil.getString(request.getParameter("type") , "0");//0-新建  1- 编辑
		TeeCrmSalesGroup productType = null;
		if(sid > 0){
			productType = crmProductsTypeDao.get(sid);
		}
		List<TeeCrmSalesGroup> list = new ArrayList<TeeCrmSalesGroup>();
		
		if(productType == null || typeStr.equals("0")){//新建
			list = crmProductsTypeDao.getCrmAllGroup();
		}else{
			String parentPath = productType.getParentPath() + productType.getSid() + "/" ;
			list = crmProductsTypeDao.getCrmProductsTypeNoChildAndSelf(sid, parentPath);
		}
		
		List<TeeZTreeModel> ztreeList = new ArrayList<TeeZTreeModel>();
		for (int i = 0; i < list.size(); i++) {
			TeeCrmSalesGroup type = list.get(i);
			String pId = "0";
			TeeCrmSalesGroup parentType = type.getParentGroup();
			if(parentType != null){
				pId = parentType.getSid() + "";
			}
			TeeZTreeModel ztreeModel = new TeeZTreeModel(type.getSid() + "" , type.getGroupName() , true , pId  , true , "" , "" , true);
			ztreeList.add(ztreeModel);
		}
		json.setRtState(true);
		json.setRtData(ztreeList);
		return json;
	}
	
	
	/**
	 * 根据Id 删除 --- 级联删除下级产品类型
	 * @param request
	 * @return
	 */
	public TeeJson deleteById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0 );
		TeeCrmSalesGroup parentType = null;
		if(sid > 0){
			 parentType = crmProductsTypeDao.get(sid);
		}
		String parentPath = "";
		if(parentType != null){
			parentPath = parentType.getParentPath() + parentType.getSid() + "/" ;
			int count = crmProductsTypeDao.deleteType(sid, parentPath);
		}
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取 BY Id 
	 * @param request
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0 );
		TeeCrmSalesGroup Type = null;
		TeeCrmSalesGroupModel model = null;
		if(sid > 0){
			Type = crmProductsTypeDao.get(sid);
		}
		if(Type == null){
			json.setRtState(false);
			json.setRtMsg("数据未找到，可能已被删除");
			return json;
		}
		model = parseModel(Type);
		json.setRtState(true);
		json.setRtData(model);
		return json;
	}
	
	/**
	 * 转换产品类型 模型
	 * @param type
	 * @return
	 */
	public  TeeCrmSalesGroupModel parseModel(TeeCrmSalesGroup  type){
		TeeCrmSalesGroupModel model = new TeeCrmSalesGroupModel();
		if(type != null){
			TeeCrmSalesGroup parentType = type.getParentGroup();
			int parentTypeId = 0;
			String parentTypeName = "";
			if(parentType != null){
				parentTypeId= parentType.getSid();
				parentTypeName = parentType.getGroupName();
			}
			BeanUtils.copyProperties(type, model);
			
			TeePerson p = type.getManagerUser();
			int managerUserId = 0;
			String managerUserName = "";
 			//负责人
			if(p != null){
				managerUserId = p.getUuid() ;
				managerUserName = p.getUserName();
			}
			model.setManagerUserId(managerUserId);
			model.setManagerUserName(managerUserName);
			
			//成员
			StringBuffer managerMemberIds = new StringBuffer();
			StringBuffer managerMemberNames = new StringBuffer();
			List<TeePerson> pl = type.getManagerMember();
			if(pl != null){
				for (int i = 0; i < pl.size(); i++) {
					TeePerson pTemp = pl.get(i);
					managerMemberIds.append(pTemp.getUuid() + ","); ;
					managerMemberNames.append(pTemp.getUserName() + ",");
				}
			}
			
			model.setParentId(parentTypeId);
			model.setParentName(parentTypeName);
			model.setManagerMemberIds(managerMemberIds.toString());
			model.setManagerMemberNames(managerMemberNames.toString());
		}
		return model;
	}
}

    