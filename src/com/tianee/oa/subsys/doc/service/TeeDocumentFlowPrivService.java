package com.tianee.oa.subsys.doc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.subsys.doc.bean.TeeDocumentFlowPriv;
import com.tianee.oa.subsys.doc.model.TeeDocumentFlowPrivModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFlowSortModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFlowTypeModel;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

import org.springframework.stereotype.Service;

@Service
public class TeeDocumentFlowPrivService extends TeeBaseService {

	public void addOrUpdate(Map requestData) {
		int flowId = TeeStringUtil.getInteger(requestData.get("flowId"), 0);

		String privUuid=TeeStringUtil.getString(requestData
				.get("privUuid"));;
		
		String privDeptIds = TeeStringUtil.getString(requestData
				.get("privDeptIds"));
		String privRoleIds = TeeStringUtil.getString(requestData
				.get("privRoleIds"));
		String privUserIds = TeeStringUtil.getString(requestData
				.get("privUserIds"));

		long flowCount = simpleDaoSupport
				.count("select count(*) from TeeDocumentFlowPriv where flowType.sid=?",
						new Object[] { flowId });
	 
		TeeDocumentFlowPriv documentFlowPriv = null;
		if(!TeeUtility.isNullorEmpty(privUuid)){
			documentFlowPriv=(TeeDocumentFlowPriv) simpleDaoSupport.get(TeeDocumentFlowPriv.class,privUuid);
		}else{
			documentFlowPriv=new TeeDocumentFlowPriv();
		}
		
		if(!TeeUtility.isNullorEmpty(privUuid)&&flowId!=documentFlowPriv.getFlowType().getSid()){
			throw new TeeOperationException("编辑时不允许修改流程类型");
		}
		
		TeeFlowType flowType = new TeeFlowType();
		flowType.setSid(flowId);

		// documentFlowPriv = new TeeDocumentFlowPriv();
		documentFlowPriv.setFlowType(flowType);
       
		// 部门
		if (privDeptIds != null && !("").equals(privDeptIds)) {
			int deptIds[] = TeeStringUtil.parseIntegerArray(privDeptIds);
			documentFlowPriv.getPrivDepts().clear();
			for (int deptId : deptIds) {
				TeeDepartment department = new TeeDepartment();
				department.setUuid(deptId);
				documentFlowPriv.getPrivDepts().add(department);
			}
		}else{
			documentFlowPriv.getPrivDepts().clear();
		}

		// 角色
		if (privRoleIds != null && !("").equals(privRoleIds)) {
			int roleIds[] = TeeStringUtil.parseIntegerArray(privRoleIds);
			documentFlowPriv.getPrivRoles().clear();
			for (int roleId : roleIds) {
				TeeUserRole role = new TeeUserRole();
				role.setUuid(roleId);
				documentFlowPriv.getPrivRoles().add(role);
			}
		}else{
			documentFlowPriv.getPrivRoles().clear();
		}

		// 人员
		if (privUserIds != null && !("").equals(privUserIds)) {
			int userIds[] = TeeStringUtil.parseIntegerArray(privUserIds);
			documentFlowPriv.getPrivUsers().clear();
			for (int userId : userIds) {
				TeePerson person = new TeePerson();
				person.setUuid(userId);
				documentFlowPriv.getPrivUsers().add(person);
			}
		}else{
			documentFlowPriv.getPrivUsers().clear();
		}
		
		
		if(flowCount>0 && TeeUtility.isNullorEmpty(privUuid)){
			throw new TeeOperationException("已存在该流程的权限规则，请勿重复设置");
			
		}
		
		if (!TeeUtility.isNullorEmpty(documentFlowPriv.getUuid())) {//修改
			simpleDaoSupport.update(documentFlowPriv);
			//throw new TeeOperationException("已存在该流程的权限规则，请勿重复设置");
		}else{//添加
			simpleDaoSupport.save(documentFlowPriv);	
		}
	}

	public void delete(String uuid) {
		simpleDaoSupport.delete(TeeDocumentFlowPriv.class, uuid);
	}

	public void updateMapping(String uuid, String jsonMapping) {
		TeeDocumentFlowPriv flowPriv = (TeeDocumentFlowPriv) simpleDaoSupport
				.get(TeeDocumentFlowPriv.class, uuid);
		flowPriv.setFieldMapping(jsonMapping);
	}

	public List<TeeDocumentFlowPrivModel> list() {
		List<TeeDocumentFlowPrivModel> list = new ArrayList(0);
		List<TeeDocumentFlowPriv> privList = simpleDaoSupport.find(
				"from TeeDocumentFlowPriv order by flowType.orderNo asc", null);
		for (TeeDocumentFlowPriv documentFlowPriv : privList) {
			list.add(parseToModel(documentFlowPriv));
		}
		return list;
	}

	public TeeDocumentFlowPrivModel get(String uuid) {
		TeeDocumentFlowPriv priv=(TeeDocumentFlowPriv) simpleDaoSupport.get(TeeDocumentFlowPriv.class,uuid);
		TeeDocumentFlowPrivModel model=parseToModel(priv);
		return model;
	}

	/**
	 * 获取当前可发起的流程
	 * @param person
	 * @return
	 */
	public List<TeeFlowTypeModel> listCreatableFlow(TeePerson person) {
		List<TeeFlowType> flowTypes = simpleDaoSupport
				.find("select priv.flowType from TeeDocumentFlowPriv priv where "
						+ "exists (select 1 from priv.privDepts dept where dept.uuid=?) "
						+ "  or exists (select 1 from priv.privRoles role where role.uuid=?) "
						+ "  or exists (select 1 from priv.privUsers user where user.uuid=?) ",
						new Object[] { person.getDept().getUuid(),person.getUserRole().getUuid(),person.getUuid() });

		List<TeeFlowTypeModel> list = new ArrayList(0);
		for (TeeFlowType ft : flowTypes) {
			TeeFlowTypeModel flowTypeModel = new TeeFlowTypeModel();
			flowTypeModel.setSid(ft.getSid());
			flowTypeModel.setFlowName(ft.getFlowName());
			flowTypeModel.setComment(ft.getComment());
			flowTypeModel.setFormId(ft.getForm().getSid());
			list.add(flowTypeModel);
		}
		// System.out.println(list);
		return list;
	}

	public List<TeeFlowSortModel> listCreatableFlowSort(TeePerson person) {

		return null;
	}

	public List<TeeFlowTypeModel> listCreatableFlowBySort(int sortId,
			TeePerson person) {

		return null;
	}

	public static TeeDocumentFlowPrivModel parseToModel(
			TeeDocumentFlowPriv documentFlowPriv) {
		TeeDocumentFlowPrivModel documentFlowPrivModel = new TeeDocumentFlowPrivModel();
		documentFlowPrivModel.setUuid(documentFlowPriv.getUuid());
		documentFlowPrivModel
				.setFlowId(documentFlowPriv.getFlowType().getSid());
		documentFlowPrivModel.setFlowName(documentFlowPriv.getFlowType()
				.getFlowName());

		StringBuffer deptIds = new StringBuffer();
		StringBuffer deptNames = new StringBuffer();

		Set<TeeDepartment> depts = documentFlowPriv.getPrivDepts();
		for (TeeDepartment dept : depts) {
			deptIds.append(dept.getUuid() + ",");
			deptNames.append(dept.getDeptName() + ",");
		}

		if (deptIds.length() != 0) {
			deptIds.deleteCharAt(deptIds.length() - 1);
			deptNames.deleteCharAt(deptNames.length() - 1);
		}

		documentFlowPrivModel.setPrivDeptIds(deptIds.toString());
		documentFlowPrivModel.setPrivDeptNames(deptNames.toString());
		
		
		StringBuffer roleIds = new StringBuffer();
		StringBuffer roleNames = new StringBuffer();

		Set<TeeUserRole> roles = documentFlowPriv.getPrivRoles();
		for (TeeUserRole role : roles) {
			roleIds.append(role.getUuid() + ",");
			roleNames.append(role.getRoleName() + ",");
		}

		if (roleIds.length() != 0) {
			roleIds.deleteCharAt(roleIds.length() - 1);
			roleNames.deleteCharAt(roleNames.length() - 1);
		}

		documentFlowPrivModel.setPrivRoleIds(roleIds.toString());
		documentFlowPrivModel.setPrivRoleNames(roleNames.toString());
		
		StringBuffer userIds = new StringBuffer();
		StringBuffer userNames = new StringBuffer();

		Set<TeePerson> users = documentFlowPriv.getPrivUsers();
		for (TeePerson user : users) {
			userIds.append(user.getUuid() + ",");
			userNames.append(user.getUserName() + ",");
		}

		if (userIds.length() != 0) {
			userIds.deleteCharAt(userIds.length() - 1);
			userNames.deleteCharAt(userNames.length() - 1);
		}

		documentFlowPrivModel.setPrivUserIds(userIds.toString());
		documentFlowPrivModel.setPrivUserNames(userNames.toString());
		
		
		documentFlowPrivModel.setFieldMapping(documentFlowPriv
				.getFieldMapping());

		return documentFlowPrivModel;
	}
}
