package com.tianee.oa.core.workflow.workmanage.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;

@Entity
@Table(name="FLOW_PRINT_TEMPLATE")
public class TeeFlowPrintTemplate implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="FLOW_PRINT_TEMPLATE_seq_gen")
	@SequenceGenerator(name="FLOW_PRINT_TEMPLATE_seq_gen", sequenceName="FLOW_PRINT_TEMPLATE_seq")
	@Column(name="SID")
	private int sid;
	
	@ManyToOne()
	@Index(name="IDX7c2f227c2216405da81ea1823d0")
	@JoinColumn(name="FLOW_ID")
	private TeeFlowType flowType;
	
	@Column(name="MODEL_TYPE")
	private int modulType;//1-打印模板   2-手写成批单
	
	@Column(name="MODUL_NAME")
	private String modulName;//模版名称
	
	@Column(name="IS_UPDATE")
	private int isUpdate;//是否更换模版
	
	
	@Lob
	@Column(name="MODUL_CONTENT")
	private String modulContent;
	
	@Lob
	@Column(name="MODUL_FIELD")
	private String modulField;
	
	@ManyToMany(targetEntity=TeeFlowProcess.class,   //多对一
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="FLOW_FROCESS_PRINT_TEMPLATE",        
			joinColumns={@JoinColumn(name="MODEL_ID")},       
			inverseJoinColumns={@JoinColumn(name="PRCS_ID")}  ) 	
	private Set<TeeFlowProcess> flowPrcs;//流程步骤

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
	
	public int getModulType() {
		return modulType;
	}

	public void setModulType(int modulType) {
		this.modulType = modulType;
	}

	public String getModulName() {
		return modulName;
	}

	public void setModulName(String modulName) {
		this.modulName = modulName;
	}

	public String getModulContent() {
		return modulContent;
	}

	public void setModulContent(String modulContent) {
		this.modulContent = modulContent;
	}

	public String getModulField() {
		return modulField;
	}

	public void setModulField(String modulField) {
		this.modulField = modulField;
	}

	public Set<TeeFlowProcess> getFlowPrcs() {
		return flowPrcs;
	}

	public void setFlowPrcs(Set<TeeFlowProcess> flowPrcs) {
		this.flowPrcs = flowPrcs;
	}

	public int getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(int isUpdate) {
		this.isUpdate = isUpdate;
	}
	
	
	
}
