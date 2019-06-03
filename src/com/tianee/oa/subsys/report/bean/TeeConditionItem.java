package com.tianee.oa.subsys.report.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;

@Entity
@Table(name="REPORT_C_ITEM")
public class TeeConditionItem {
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy=GenerationType.AUTO,generator="REPORT_C_ITEM_seq_gen")
	@SequenceGenerator(name="REPORT_C_ITEM_seq_gen", sequenceName="REPORT_C_ITEM_seq")
	private int sid;
	
	@Column(name="ITEM_ID")
	private int itemId;
	
	@Column(name="OPER")
	private String oper;
	
	@Column(name="VAL")
	private String val;
	
	@JoinColumn(name="CONDITION_ID")
	@ManyToOne
	@Index(name="IDX027a30c6f008453c875b4ca5b7d")
	private TeeReportCondition condition;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public TeeReportCondition getCondition() {
		return condition;
	}

	public void setCondition(TeeReportCondition condition) {
		this.condition = condition;
	}
	
	
}
