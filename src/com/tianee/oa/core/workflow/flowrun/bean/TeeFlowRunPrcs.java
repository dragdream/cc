package com.tianee.oa.core.workflow.flowrun.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;

@Entity
@Table(name="FLOW_RUN_PRCS")
public class TeeFlowRunPrcs  implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="FLOW_RUN_PRCS_seq_gen")
	@SequenceGenerator(name="FLOW_RUN_PRCS_seq_gen", sequenceName="FLOW_RUN_PRCS_seq")
	@Column(name="SID")
	private int sid;
	
	@Column(name="PRCS_ID")
	private int prcsId;//流程序号(累加)
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX1bde48114ed74a5d8e51c4f3216")
	@JoinColumn(name="RUN_ID")
	private TeeFlowRun flowRun;//所属流水流程
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX1bde48114xxxxx5d8e51c4f3216")
	@JoinColumn(name="FLOW_ID")
	private TeeFlowType flowType;//所属流程
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX71f0d949cfd04f4fb8259c23fa0")
	@JoinColumn(name="FLOW_PRCS")
	private TeeFlowProcess flowPrcs;//所属设计步骤
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDXf7dbfff157464c0ba8a4b4a74f8")
	@JoinColumn(name="PRCS_USER")
	private TeePerson prcsUser;//流程处理人
	
	@Column(name="BEGIN_TIME")
	private Calendar beginTime;//开始接收时间
	
	@Column(name="BEGIN_TIME_STAMP")
	private long beginTimeStamp;//开始时间戳
	
	@Column(name="END_TIME")
	private Calendar endTime;//办理完成时间
	
	@Column(name="END_TIME_STAMP")
	private long endTimeStamp;//完成时间戳
	
	@Column(name="CREATE_TIME")
	private Calendar createTime;//创建时间
	
	@Column(name="CREATE_TIME_STAMP")
	private long createTimeStamp;//创建时间
	
	@Column(name="FLAG")
	private int flag;//状态标识  1-未接收  2-办理中  3-转交下一步  4-已办结  5-预设步骤
	
	@Column(name="OP_FLAG")
	private int opFlag;//办理类型 1：指定主办人 2：无主办会签 3：先接受为主办人
	
	@Column(name="TOP_FLAG")
	private int topFlag;//主办标识  0-经办  1-主办
	
	@Column(name="DEL_FLAG")
	private int delFlag;//删除标识   0-未删除  1-已删除
	
	@Column(name="TIMEOUT_FLAG")
	private int timeoutFlag;//超时标记
	
	@Column(name="TIME_OUT")
	private double timeout;//超时时限
	
	@Column(name="TIMEOUT_TYPE")
	private int timeoutType=1;//超时计算方法，1：本步骤接收后开始计时 2：上一步骤转交后开始计时
	
	@Column(name="IGNORE_TYPE")
	private int ignoreType=0;//排除双休日
	
	@Column(name="TIMEOUT_ALARM",nullable=false)
	private long timeoutAlarm;//超时预警，0为不警报
	
	@Column(name="TIMEOUT_ALARM_FLAG",nullable=false)
	private int timeoutAlarmFlag;//超时预警标记，0=没有预警  1=已预警
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDXbf80e22b48454c71b79407def1f")
	@JoinColumn(name="OTHER_USER")
	private TeePerson otherUser;//自动委托人
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDXb08ff4f053a64d068d5b7eb15f6")
	@JoinColumn(name="FROM_USER")
	private TeePerson fromUser;//手动委托人
	
	@Column(name="FREE_TURN_MODEL")
	/**
	 * {opUser:'1',prcsUsers:'1,2,3,4,5',topFlag:'1'}
	 */
	@Lob
	private String freeTurnModel;//自由流程数据转交模型
	
	@Column(name="FREE_CTRL_MODEL")
	@Lob
	private String freeCtrlModel;//自由流程表单控制模型
	
	@Column(name="ATTACH_CTRL_MODEL")
	@Lob
	private String attachCtrlModel;//自由流程附件控制模型
	
	public String getAttachCtrlModel() {
		return attachCtrlModel;
	}

	public void setAttachCtrlModel(String attachCtrlModel) {
		this.attachCtrlModel = attachCtrlModel;
	}

	@Column(name="PARALLEL_PRCS_ID")
	private int parallelPrcsId=0;//并发节点步骤ID
	
	@Column(name="PARENT")
	private String parent="";//父节点步骤ID
	
	@Column(name="SUSPEND",nullable=false)
	private int suspend;//挂起标记
	
	@Column(name="PRCS_EVENT")
	private String prcsEvent;
	
	@Column(name="BACK_FLAG",nullable=false)
	private int backFlag;//回退标记  1：回退   0:未回退
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getPrcsId() {
		return prcsId;
	}

	public void setPrcsId(int prcsId) {
		this.prcsId = prcsId;
	}

	public TeeFlowRun getFlowRun() {
		return flowRun;
	}

	public void setFlowRun(TeeFlowRun flowRun) {
		this.flowRun = flowRun;
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

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getOpFlag() {
		return opFlag;
	}

	public void setOpFlag(int opFlag) {
		this.opFlag = opFlag;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public void setFlowPrcs(TeeFlowProcess flowPrcs) {
		this.flowPrcs = flowPrcs;
	}

	public TeeFlowProcess getFlowPrcs() {
		return flowPrcs;
	}
	
	public int getTopFlag() {
		return topFlag;
	}

	public void setTopFlag(int topFlag) {
		this.topFlag = topFlag;
	}

	public int getTimeoutFlag() {
		return timeoutFlag;
	}

	public void setTimeoutFlag(int timeoutFlag) {
		this.timeoutFlag = timeoutFlag;
	}

	public double getTimeout() {
		return timeout;
	}

	public void setTimeout(double timeout) {
		this.timeout = timeout;
	}

	public TeePerson getFromUser() {
		return fromUser;
	}

	public void setFromUser(TeePerson fromUser) {
		this.fromUser = fromUser;
	}

	public void setPrcsUser(TeePerson prcsUser) {
		this.prcsUser = prcsUser;
	}

	public TeePerson getPrcsUser() {
		return prcsUser;
	}

	public void setFreeTurnModel(String freeTurnModel) {
		this.freeTurnModel = freeTurnModel;
	}

	public String getFreeTurnModel() {
		return freeTurnModel;
	}

	public void setFreeCtrlModel(String freeCtrlModel) {
		this.freeCtrlModel = freeCtrlModel;
	}

	public String getFreeCtrlModel() {
		if(freeCtrlModel==null){
			freeCtrlModel = "[]";
		}
		return freeCtrlModel;
	}

	public TeePerson getOtherUser() {
		return otherUser;
	}

	public void setOtherUser(TeePerson otherUser) {
		this.otherUser = otherUser;
	}

	public void setParallelPrcsId(int parallelPrcsId) {
		this.parallelPrcsId = parallelPrcsId;
	}

	public int getParallelPrcsId() {
		return parallelPrcsId;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getParent() {
		return parent;
	}

	public void setTimeoutType(int timeoutType) {
		this.timeoutType = timeoutType;
	}

	public int getTimeoutType() {
		return timeoutType;
	}

	public void setIgnoreType(int ignoreType) {
		this.ignoreType = ignoreType;
	}

	public int getIgnoreType() {
		return ignoreType;
	}

	public int getSuspend() {
		return suspend;
	}

	public void setSuspend(int suspend) {
		this.suspend = suspend;
	}

	public long getBeginTimeStamp() {
		return beginTimeStamp;
	}

	public void setBeginTimeStamp(long beginTimeStamp) {
		this.beginTimeStamp = beginTimeStamp;
	}

	public long getEndTimeStamp() {
		return endTimeStamp;
	}

	public void setEndTimeStamp(long endTimeStamp) {
		this.endTimeStamp = endTimeStamp;
	}

	public long getCreateTimeStamp() {
		return createTimeStamp;
	}

	public void setCreateTimeStamp(long createTimeStamp) {
		this.createTimeStamp = createTimeStamp;
	}

	public long getTimeoutAlarm() {
		return timeoutAlarm;
	}

	public void setTimeoutAlarm(long timeoutAlarm) {
		this.timeoutAlarm = timeoutAlarm;
	}

	public int getTimeoutAlarmFlag() {
		return timeoutAlarmFlag;
	}

	public void setTimeoutAlarmFlag(int timeoutAlarmFlag) {
		this.timeoutAlarmFlag = timeoutAlarmFlag;
	}

	public String getPrcsEvent() {
		return prcsEvent;
	}

	public void setPrcsEvent(String prcsEvent) {
		this.prcsEvent = prcsEvent;
	}

	public int getBackFlag() {
		return backFlag;
	}

	public void setBackFlag(int backFlag) {
		this.backFlag = backFlag;
	}

	public TeeFlowType getFlowType() {
		return flowType;
	}

	public void setFlowType(TeeFlowType flowType) {
		this.flowType = flowType;
	}
	
}
