package com.beidasoft.zfjd.inspection.inspItem.model;

/**
 * 职权基础信息表MODEL类
 */
public class InspectItemModel {
    // 主键
    private String id;

    // 模块ID
    private String moduleId;
    
    // 模块名称
    private String moduleName;

    // 创建者机构ID
    private String createOrganizationId;

    // 事项名称
    private String itemName;

    // 删除标志
    private int isDelete;

    // 创建时间
    private String createTimeStr;

    // 执法系统
    private String orgSys;

    // 执法系统
    private String orgSysName;

    // 创建者主体ID
    private String createSubjectId;

    // 项类型（10主管理创建  20子管理员创建）
    private String createType;
    
    // 项类型（10主管理创建  20子管理员创建）
    private String loginDeptId;
    
    // 项类型（10主管理创建  20子管理员创建）
    private String loginSubId;
    
    private String ctrlType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getCreateOrganizationId() {
        return createOrganizationId;
    }

    public void setCreateOrganizationId(String createOrganizationId) {
        this.createOrganizationId = createOrganizationId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
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

    public String getCreateSubjectId() {
        return createSubjectId;
    }

    public void setCreateSubjectId(String createSubjectId) {
        this.createSubjectId = createSubjectId;
    }

    public String getCreateType() {
        return createType;
    }

    public void setCreateType(String craeteType) {
        this.createType = craeteType;
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
    
    
}
