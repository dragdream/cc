package com.beidasoft.zfjd.inspection.inspList.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 执法检查实体类
 */
@Entity
@Table(name="TBL_INSP_LIST_BASE")
public class InspListBase {
    // 数据唯一标识
    @Id
    @Column(name = "ID")
    private String id;

    // 创建日期
    @Column(name = "CREATE_DATE")
    private Date createDate;

    // 创建部门id
    @Column(name = "CREATE_ORGANIZATION_ID")
    private String createOrganizationId;

    // 创建主体id
    @Column(name = "CREATE_SUBJECT_ID")
    private String createSubjectId;

    // 所属执法系统代码
    @Column(name = "ORG_SYS")
    private String orgSys;

    // 项类型（10主管理创建  20子管理员创建）
    @Column(name = "CREATE_TYPE")
    private String createType;

    // 检查单名称
    @Column(name = "LIST_NAME")
    private String listName;

    // 适用层级
    @Column(name = "APPLY_HIERARCHY")
    private String applyHierarchy;

    // 检查单分类
    @Column(name = "LIST_CLASSIFY")
    private String listClassify;

    // 删除标识
    @Column(name = "IS_DELETE")
    private int isDelete;
    
    @Column(name = "CURRENT_STATE")
    private Integer currentState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateOrganizationId() {
        return createOrganizationId;
    }

    public void setCreateOrganizationId(String createOrganizationId) {
        this.createOrganizationId = createOrganizationId;
    }

    public String getCreateSubjectId() {
        return createSubjectId;
    }

    public void setCreateSubjectId(String createSubjectId) {
        this.createSubjectId = createSubjectId;
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

	public Integer getCurrentState() {
		return currentState;
	}

	public void setCurrentState(Integer currentState) {
		this.currentState = currentState;
	}
    

}
