package com.tianee.oa.core.workflow.flowrun.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 流程变量
 * @author kakalion
 *
 */
@Entity
@Table(name="FLOW_RUN_VARS")
public class TeeFlowRunVars {
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String sid;
	
	@ManyToOne
	@Index(name="IDX55e6755295c946b794a46cd4d72")
	@JoinColumn(name="RUN_ID")
	private TeeFlowRun flowRun;
	
	@Column(name="VAR_KEY")
	private String varKey;
	
	@Column(name="VAR_VALUE")
	@Lob
	private String varValue;
	
	/**
	 * 1、字符串
	 * 2、数字
	 * 3、日期时间
	 */
	@Column(name="VAR_TYPE")
	private int varType;//类型

	
	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public TeeFlowRun getFlowRun() {
		return flowRun;
	}

	public void setFlowRun(TeeFlowRun flowRun) {
		this.flowRun = flowRun;
	}

	public String getVarKey() {
		return varKey;
	}

	public void setVarKey(String varKey) {
		this.varKey = varKey;
	}

	public String getVarValue() {
		return varValue;
	}

	public void setVarValue(String varValue) {
		this.varValue = varValue;
	}

	public void setVarType(int varType) {
		this.varType = varType;
	}

	public int getVarType() {
		return varType;
	}
	
}
