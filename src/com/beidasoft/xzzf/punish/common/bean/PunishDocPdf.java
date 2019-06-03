package com.beidasoft.xzzf.punish.common.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZF_PUNISH_DOC_PDF")
public class PunishDocPdf {
	
	//业务文书ID
	@Id
	@Column(name = "DOC_ID")
	private String docId;
	
	//生成PDF文书ID
	@Column(name = "PDF_ID")
	private int pdfId;
	
	//PDF模板ID
	@Column(name = "TMP_ID")
	private int tmpId;

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public int getPdfId() {
		return pdfId;
	}

	public void setPdfId(int pdfId) {
		this.pdfId = pdfId;
	}

	public int getTmpId() {
		return tmpId;
	}

	public void setTmpId(int tmpId) {
		this.tmpId = tmpId;
	}
	
	

}
