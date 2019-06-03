package com.tianee.oa.core.workflow.flowmanage.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="LIST_CTRL_EXTEND")
public class TeeListCtrlExtend  implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="LIST_CTRL_EXTEND_seq_gen")
	@SequenceGenerator(name="LIST_CTRL_EXTEND_seq_gen", sequenceName="LIST_CTRL_EXTEND_seq")
	@Column(name="SID")
	private int sid;
	
	@Column(name="FLOW_PRCS_ID")
	private int flowPrcsId;
	
	@Column(name="FORM_ITEM_ID")
	private int formItemId;
	
	//列控制模型
	@Column(name="COLUMN_CTRL_MODEL")
	@Lob
	private String columnCtrlModel;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getColumnCtrlModel() {
		return columnCtrlModel;
	}

	public void setColumnCtrlModel(String columnCtrlModel) {
		this.columnCtrlModel = columnCtrlModel;
	}

	public void setFlowPrcsId(int flowPrcsId) {
		this.flowPrcsId = flowPrcsId;
	}

	public int getFlowPrcsId() {
		return flowPrcsId;
	}

	public void setFormItemId(int formItemId) {
		this.formItemId = formItemId;
	}

	public int getFormItemId() {
		return formItemId;
	}
}
