package com.tianee.oa.core.workflow.workmanage.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;

@Entity
@Table(name="FLOW_QUERY_TPL")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class TeeFlowQueryTpl  implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="FLOW_QUERY_TPL_seq_gen")
	@SequenceGenerator(name="FLOW_QUERY_TPL_seq_gen", sequenceName="FLOW_QUERY_TPL_seq")
	@Column(name="SID")
	private int sid;
	
	@Column(name="TPL_NAME")
	private String tplName;
	
	@Column(name="USER_ID")
	private int userId;
	
	@Column(name="COND_FORMULA")
	private String condFormula;
	
	@ManyToOne()
	@Index(name="IDXa69492c74fb847fd958cc9f9982")
	@JoinColumn(name="FLOW_ID")
	private TeeFlowType flowType;
	
	
	@Lob 
	@Column(name="DATA_JSON", nullable=true)
	private String dataJson;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getTplName() {
		return tplName;
	}

	public void setTplName(String tplName) {
		this.tplName = tplName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCondFormula() {
		return condFormula;
	}

	public void setCondFormula(String condFormula) {
		this.condFormula = condFormula;
	}

	public String getDataJson() {
		return dataJson;
	}

	public void setDataJson(String dataJson) {
		this.dataJson = dataJson;
	}

	public TeeFlowType getFlowType() {
		return flowType;
	}

	public void setFlowType(TeeFlowType flowType) {
		this.flowType = flowType;
	}
	
	
	
}
