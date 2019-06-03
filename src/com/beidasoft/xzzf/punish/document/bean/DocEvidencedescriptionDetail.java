package com.beidasoft.xzzf.punish.document.bean;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ZF_DOC_ECIDENCE_DESC_DETAIL")
public class DocEvidencedescriptionDetail { //取证情况及证据说明实体类子表
	
	//主键ID
	@Id
    @Column(name = "ID")
    private String id;
	
	//关联的主表的主键ID
	@Column(name = "MAIN_ID")
	private String mainId;
	
	// 取证内容
    @Column(name = "EVIDENCE")
    private String evidence;

    // 取证方法及过程证据
    @Column(name = "METHOD")
    private String method;

    // 证明对象证据
    @Column(name = "CERTIFIER")
    private String certifier;

    // 页数证据
    @Column(name = "PAGE")
    private String page;
    
    //附件字符串
    @Column(name = "ATTACHES")
    private String attaches;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	public String getEvidence() {
		return evidence;
	}

	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getCertifier() {
		return certifier;
	}

	public void setCertifier(String certifier) {
		this.certifier = certifier;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getAttaches() {
		return attaches;
	}

	public void setAttaches(String attaches) {
		this.attaches = attaches;
	}
    
}
