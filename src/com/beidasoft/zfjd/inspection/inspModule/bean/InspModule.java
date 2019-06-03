package com.beidasoft.zfjd.inspection.inspModule.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 检查模块实体类
 */
@Entity
@Table(name="TBL_INSP_MODULE")
public class InspModule {
	
	// 主键
    @Id
    @Column(name = "ID")
    private String id;

    // 创建者部门id
    @Column(name = "ORGANIZATION_ID")
    private String organizationId;

    // 模块名称
    @Column(name = "MODULE_NAME")
    private String moduleName;

    // 删除标志
    @Column(name = "IS_DELETE")
    private Integer isDelete;

    // 创建时间
    @Column(name = "CREATE_TIME")
    private Date createTime;

    // 执法系统
    @Column(name = "ORG_SYS")
    private String orgSys;

    // 创建者主体id
    @Column(name = "SUBJECT_ID")
    private String subject;
    
    /*@Column(name = "ADMINISTRATIVEDIVISION")
    private String administrativeDivision;*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOrgSys() {
        return orgSys;
    }

    public void setOrgSys(String orgSys) {
        this.orgSys = orgSys;
    }

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

    
    
}
