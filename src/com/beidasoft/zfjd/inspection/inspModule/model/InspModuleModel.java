package com.beidasoft.zfjd.inspection.inspModule.model;

/**
 * 职权基础信息表MODEL类
 */
public class InspModuleModel {
    // 主键
    private String id;

    // 创建者部门id
    private String organizationId;
    
    // 创建者部门名称
    private String organizationName;

    // 模块名称
    private String moduleName;

    // 删除标志
    private Integer isDelete;

    // 创建时间
    private String createTimeStr;

    // 执法系统
    private String orgSys;

    // 创建者主体id
    private String subjectId;
    
    // 创建者主体名称
    private String subjectName;
    
    // 权限控制
    private String ctrlType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getOrgSys() {
        return orgSys;
    }

    public void setOrgSys(String orgSys) {
        this.orgSys = orgSys;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

	public String getCtrlType() {
		return ctrlType;
	}

	public void setCtrlType(String ctrlType) {
		this.ctrlType = ctrlType;
	}
}
