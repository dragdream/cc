package com.tianee.oa.core.workflow.workmanage.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;

@Entity
@Table(name="FLOW_NTKO_PRINT_TEMPLATE")
public class TeeFlowNtkoPrintTemplate {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="FLOW_NTKO_PRINT_TEMPLATE_seq_gen")
	@SequenceGenerator(name="FLOW_NTKO_PRINT_TEMPLATE_seq_gen", sequenceName="FLOW_NTKO_PRINT_TPL_seq")
	@Column(name="SID")
	private int sid;
	
	@ManyToOne()
	@Index(name="IDX7c2f227c2216405fuckea1823d0")
	@JoinColumn(name="FLOW_ID")
	private TeeFlowType flowType;
	
	@Column(name="MODUL_NAME")
	private String modulName;//模版名称
	
	@ManyToOne()
	@JoinColumn(name="ATTACH_ID")
	private TeeAttachment attach;//所属附件

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeeFlowType getFlowType() {
		return flowType;
	}

	public void setFlowType(TeeFlowType flowType) {
		this.flowType = flowType;
	}

	public String getModulName() {
		return modulName;
	}

	public void setModulName(String modulName) {
		this.modulName = modulName;
	}

	public TeeAttachment getAttach() {
		return attach;
	}

	public void setAttach(TeeAttachment attach) {
		this.attach = attach;
	}
}
