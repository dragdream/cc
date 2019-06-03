package com.beidasoft.zfjd.subjectPerson.model;

/**
 * 主体人员关联表MODEL类
 */
public class TblSubjectPersonModel {
    // 主键
    private String id;

    // 人员id
    private String personId;

    // 主体id
    private String subjectId;

    //执法人员姓名
    private String name;
    
    //执法证件号
    private String enforceCode;
    
    //组织id
    private String organizationId;
    
    public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnforceCode() {
        return enforceCode;
    }

    public void setEnforceCode(String enforceCode) {
        this.enforceCode = enforceCode;
    }

}
