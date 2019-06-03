package com.beidasoft.zfjd.subjectPerson.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 主体用户关联表实体类
 */
@Entity
@Table(name="TBL_BASE_SUBJECT_PERSON")
public class TblSubjectPerson {
    // 主键
	@Id
    @Column(name = "id")
    private String id;

    // 人员id
    @Column(name = "person_id")
    private String personId;

    // 主体id
    @Column(name = "subject_id")
    private String subjectId;

    // 组织id
    @Column(name = "organization_id")
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

}
