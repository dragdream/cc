package com.tianee.oa.subsys.bisengin.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 业务引擎
 * @author kakalion
 *
 */
@Entity
@Table(name="BIS_TABLE_ENGINE")
public class BisTableEngine {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="BIS_CONFIG_seq_gen")
	@SequenceGenerator(name="BIS_CONFIG_seq_gen", sequenceName="BIS_CONFIG_seq")
	private int sid;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX3e764cfbca714021800e1cd3cca")
	@JoinColumn(name="BIS_TABLE_ID")
	private BisTable bisTable;
	
	@Column(name="FLOW_ID")
	private int flowId;
	
	@Column(name="TYPE")
	private int type;//引擎类别   1：表单   2：明细表  3：字段分隔
	
	@Column(name="LIST_TITLE")
	private String listTitle;//明细表title
	
	@Lob
	@Column(name="BIS_MODEL")
	private String bisModel;
	
	@Lob
	@Column(name="REMARK")
	private String remark;
	
	@Column(name="STATUS")
	private int status;//状态    0：关闭 1：开启

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public BisTable getBisTable() {
		return bisTable;
	}

	public void setBisTable(BisTable bisTable) {
		this.bisTable = bisTable;
	}

	public int getFlowId() {
		return flowId;
	}

	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}

	public String getBisModel() {
		return bisModel;
	}

	public void setBisModel(String bisModel) {
		this.bisModel = bisModel;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getListTitle() {
		return listTitle;
	}

	public void setListTitle(String listTitle) {
		this.listTitle = listTitle;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
