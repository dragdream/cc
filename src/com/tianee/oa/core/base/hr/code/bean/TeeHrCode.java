package com.tianee.oa.core.base.hr.code.bean;
import org.hibernate.annotations.Index;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name = "HR_CODE")
public class TeeHrCode {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="HR_CODE_seq_gen")
	@SequenceGenerator(name="HR_CODE_seq_gen", sequenceName="HR_CODE_seq")
	@Column(name="SID")
	private int sid;
	
	@Column(name="CODE_NAME",length=200)
	private String codeName;//代码名称
	
	@Column(name="CODE_NO",length=100)
	private String codeNo;//代码编号
	

	@Column(name="CODE_ORDER",columnDefinition="int default 0")
	private int  codeOrder;//排序
	

	@Column(name="CODE_FLAG",columnDefinition="int default 0")
	private  String  codeFlag;//是否删除  0 - 可以删除   1-系统，不可删除
	
	@Column(name="PARENT_ID" ,columnDefinition="int default 0")
	private int  parentId;//父节点编号

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getCodeNo() {
		return codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	public int getCodeOrder() {
		return codeOrder;
	}

	public void setCodeOrder(int codeOrder) {
		this.codeOrder = codeOrder;
	}

	public String getCodeFlag() {
		return codeFlag;
	}

	public void setCodeFlag(String codeFlag) {
		this.codeFlag = codeFlag;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	

	
}
