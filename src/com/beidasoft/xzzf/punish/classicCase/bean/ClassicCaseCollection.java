package com.beidasoft.xzzf.punish.classicCase.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 经典案例收藏实体类
 */
@Entity
@Table(name="ZF_CLASSIC_CASE_COLLECTION")
public class ClassicCaseCollection {
    @Id
    @Column(name = "ID")
    private String id; // 收藏表主键id

    @Column(name = "CLASSIC_CASE_ID")
    private String classicCaseId; // 关联案例基础表
    
    @Column(name = "PERSON_ID")
    private int personId; // 收藏人id
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getClassicCaseId() {
		return classicCaseId;
	}

	public void setClassicCaseId(String classicCaseId) {
		this.classicCaseId = classicCaseId;
	}



 
}
