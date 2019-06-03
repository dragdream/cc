package com.tianee.oa.core.workflow.flowrun.bean;
import org.hibernate.annotations.Index;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.subsys.bisengin.bean.BusinessModel;

@Entity
@Table(name="FLOW_RUN")
public class TeeFlowRun implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="FLOW_RUN_seq_gen")
	@SequenceGenerator(name="FLOW_RUN_seq_gen", sequenceName="FLOW_RUN_seq")
	@Column(name="RUN_ID")
	private int runId;//流水号
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDXa71f94493e424f349e9389ff78d")
	@JoinColumn(name="BEGIN_PERSON")
	private TeePerson beginPerson;//发起人
	
	@Column(name="RUN_NAME")
	private String runName;//流程名称
	
	@Column(name="O_RUN_NAME")
	private String oRunName;//存的是流程发起后的runName
	
	public String getoRunName() {
		return oRunName;
	}

	public void setoRunName(String oRunName) {
		this.oRunName = oRunName;
	}

	@Column(name="BEGIN_TIME")
	private Calendar beginTime;//发起时间
	
	@Column(name="END_TIME")
	private Calendar endTime;//结束时间
	
	@Column(name="DEL_FLAG")
	private int delFlag ;//销毁标记
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX5c6d7d2221ea4f218dbed75d538")
	@JoinColumn(name="FORM_ID")
	private TeeForm form;//所属表单
	
	@Column(name="FORM_VERSION")
	private int formVersion;//表单版本
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDXb68edbf943ef4e7486080bfe9e1")
	@JoinColumn(name="FLOW_ID")
	private TeeFlowType flowType;//所属流程定义
	
	@Column(name="P_RUN_ID")
	private int pRunId;//父流程实例ID
	
	@Column(name="P_SOURCE_FRP_SId")
	private int pSrouceFrpSid;//父流程的原触发步骤实例ID
	
	@Column(name="P_FP_ID")
	private int pFlowPrcsId;//父流程的触发子流程步骤定义ID
	
	@Column(name="P_C_F_P")
	private int pChildFlowPrcs;//子流程步骤实例ID
	
	/*
	 * 流程变量
	 */
	@OneToMany(mappedBy="flowRun",fetch=FetchType.LAZY)
	private List<TeeFlowRunVars> flowRunVars = new ArrayList<TeeFlowRunVars>(0);//流程变量
	
	@Column(name="LEVEL_")
	private int level;//流程级别  1:普通  2：紧急  3：加急
	
	@Column(name="SEND_FLAG")
	private int sendFlag;//是否下发，用于公文   0：未分发   1：已分发
	
	
	/**
	 * 是否已经保存
	 */
	@Column(name="IS_SAVE")
	private int isSave;//0=未保存  1=已保存
	
	
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BISKEY")
	private BusinessModel businessModel;//所属业务建模
	
	
	
	
	public BusinessModel getBusinessModel() {
		return businessModel;
	}

	public void setBusinessModel(BusinessModel businessModel) {
		this.businessModel = businessModel;
	}

	public int getIsSave() {
		return isSave;
	}

	public void setIsSave(int isSave) {
		this.isSave = isSave;
	}

	public int getRunId() {
		return runId;
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}

	public TeePerson getBeginPerson() {
		return beginPerson;
	}

	public void setBeginPerson(TeePerson beginPerson) {
		this.beginPerson = beginPerson;
	}

	public String getRunName() {
		return runName;
	}

	public void setRunName(String runName) {
		this.runName = runName;
	}

	public Calendar getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Calendar beginTime) {
		this.beginTime = beginTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public TeeForm getForm() {
		return form;
	}

	public void setForm(TeeForm form) {
		this.form = form;
	}

	public void setFlowType(TeeFlowType flowType) {
		this.flowType = flowType;
	}

	public TeeFlowType getFlowType() {
		return flowType;
	}

	public void setFormVersion(int formVersion) {
		this.formVersion = formVersion;
	}

	public int getFormVersion() {
		return formVersion;
	}

	public int getPRunId() {
		return pRunId;
	}

	public void setPRunId(int runId) {
		pRunId = runId;
	}

	public int getPSrouceFrpSid() {
		return pSrouceFrpSid;
	}

	public void setPSrouceFrpSid(int srouceFrpSid) {
		pSrouceFrpSid = srouceFrpSid;
	}

	public int getPFlowPrcsId() {
		return pFlowPrcsId;
	}

	public void setPFlowPrcsId(int flowPrcsId) {
		pFlowPrcsId = flowPrcsId;
	}

	public void setFlowRunVars(List<TeeFlowRunVars> flowRunVars) {
		this.flowRunVars = flowRunVars;
	}

	public List<TeeFlowRunVars> getFlowRunVars() {
		return flowRunVars;
	}

	public void setPChildFlowPrcs(int pChildFlowPrcs) {
		this.pChildFlowPrcs = pChildFlowPrcs;
	}

	public int getPChildFlowPrcs() {
		return pChildFlowPrcs;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(int sendFlag) {
		this.sendFlag = sendFlag;
	}
	
}
