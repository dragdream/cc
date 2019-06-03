package com.beidasoft.zfjd.inspection.inspList.model;

import java.util.List;

/**
 * 执法检查MODEL类
 */
public class InspListBaseModel {
    // 数据唯一标识
    private String id;

    // 创建日期
    private String createDateStr;

    // 创建部门id
    private String createOrganizationId;

    // 创建主体id
    private String createSubjectId;

    // 所属执法系统代码
    private String orgSys;
    
    private String orgSysStr;

    // 项类型（10主管理创建  20子管理员创建）
    private String createType;

    // 检查单名称
    private String listName;

    // 适用层级
    private String applyHierarchy;

    // 适用层级
    private String applyHierarchyStr;
    
    // 检查单分类
    private String listClassify;

    // 检查单分类
    private String listClassifyStr;
    
    // 删除标识
    private int isDelete;
    
    // 执法系统
    private String orgSysName;
    
    // 用户部门id
    private String loginDeptId;
    
    // 项类型（10主管理创建  20子管理员创建）
    private String loginSubId;
    
    // 项类型（10主管理创建  20子管理员创建）
    private String ctrlType;
    
    private String moduleIds;
    //模块id
    private String moduleIdsStr;
    
    //检查单模版状态
    private Integer currentState;
    
    private Integer currentStateStr;
    
    private List<String> moduleIdList;

    public String getOrgSysStr() {
        return orgSysStr;
    }

    public void setOrgSysStr(String orgSysStr) {
        this.orgSysStr = orgSysStr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getCreateOrganizationId() {
        return createOrganizationId;
    }

    public void setCreateOrganizationId(String createOrganizationId) {
        this.createOrganizationId = createOrganizationId;
    }

    public String getOrgSys() {
        return orgSys;
    }

    public void setOrgSys(String orgSys) {
        this.orgSys = orgSys;
    }

    public String getCreateType() {
        return createType;
    }

    public void setCreateType(String createType) {
        this.createType = createType;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getApplyHierarchy() {
        return applyHierarchy;
    }

    public void setApplyHierarchy(String applyHierarchy) {
        this.applyHierarchy = applyHierarchy;
    }

    public String getListClassify() {
        return listClassify;
    }

    public void setListClassify(String listClassify) {
        this.listClassify = listClassify;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreateSubjectId() {
        return createSubjectId;
    }

    public void setCreateSubjectId(String createSubjectId) {
        this.createSubjectId = createSubjectId;
    }

    public String getOrgSysName() {
        return orgSysName;
    }

    public void setOrgSysName(String orgSysName) {
        this.orgSysName = orgSysName;
    }

    public String getLoginDeptId() {
        return loginDeptId;
    }

    public void setLoginDeptId(String loginDeptId) {
        this.loginDeptId = loginDeptId;
    }

    public String getLoginSubId() {
        return loginSubId;
    }

    public void setLoginSubId(String loginSubId) {
        this.loginSubId = loginSubId;
    }

    public String getCtrlType() {
        return ctrlType;
    }

    public void setCtrlType(String ctrlType) {
        this.ctrlType = ctrlType;
    }

    public String getApplyHierarchyStr() {
        return applyHierarchyStr;
    }

    public void setApplyHierarchyStr(String applyHierarchyStr) {
        this.applyHierarchyStr = applyHierarchyStr;
    }

    public String getListClassifyStr() {
        return listClassifyStr;
    }

    public void setListClassifyStr(String listClassifyStr) {
        this.listClassifyStr = listClassifyStr;
    }

    public String getModuleIdsStr() {
        return moduleIdsStr;
    }

    public void setModuleIdsStr(String moduleIdsStr) {
        this.moduleIdsStr = moduleIdsStr;
    }

	public List<String> getModuleIdList() {
		return moduleIdList;
	}

	public void setModuleIdList(List<String> moduleIdList) {
		this.moduleIdList = moduleIdList;
	}

	public Integer getCurrentState() {
		return currentState;
	}

	public void setCurrentState(Integer currentState) {
		this.currentState = currentState;
	}

    public String getModuleIds() {
        return moduleIds;
    }

    public void setModuleIds(String moduleIds) {
        this.moduleIds = moduleIds;
    }

    public Integer getCurrentStateStr() {
        return currentStateStr;
    }

    public void setCurrentStateStr(Integer currentStateStr) {
        this.currentStateStr = currentStateStr;
    }

}
