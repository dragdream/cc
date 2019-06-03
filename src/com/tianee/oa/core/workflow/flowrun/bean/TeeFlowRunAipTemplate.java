package com.tianee.oa.core.workflow.flowrun.bean;

import java.io.Serializable;

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

import org.hibernate.annotations.Index;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowPrintTemplate;

@Entity
@Table(name="FLOW_RUN_AIP_TEMPLATE")
public class TeeFlowRunAipTemplate implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="FLOW_RUN_AIP_TEMPLATE_seq_gen")
	@SequenceGenerator(name="FLOW_RUN_AIP_TEMPLATE_seq_gen", sequenceName="FLOW_RUN_AIP_TEMPLATE_seq")
	@Column(name="SID")
	private int sid;//主键
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="RUN_ID")
	private TeeFlowRun flowRun;//关联流程
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TEMPLATE_ID")
	private TeeFlowPrintTemplate template;//关联模板
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ATTACH_ID")
	private TeeAttachment attachment;//关联附件


	public int getSid() {
		return sid;
	}


	public TeeFlowRun getFlowRun() {
		return flowRun;
	}


	public TeeFlowPrintTemplate getTemplate() {
		return template;
	}


	public TeeAttachment getAttachment() {
		return attachment;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public void setFlowRun(TeeFlowRun flowRun) {
		this.flowRun = flowRun;
	}


	public void setTemplate(TeeFlowPrintTemplate template) {
		this.template = template;
	}


	public void setAttachment(TeeAttachment attachment) {
		this.attachment = attachment;
	}
	
	
	
	
}
