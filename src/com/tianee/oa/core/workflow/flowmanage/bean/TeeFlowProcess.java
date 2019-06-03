package com.tianee.oa.core.workflow.flowmanage.bean;

import org.hibernate.annotations.Index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowPrintTemplate;

@Entity
@Table(name = "FLOW_PROCESS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TeeFlowProcess implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "FLOW_PROCESS_seq_gen")
	@SequenceGenerator(name = "FLOW_PROCESS_seq_gen", sequenceName = "FLOW_PROCESS_seq")
	@Column(name = "SID")
	private int sid;

	@Column(name = "PRCS_ID")
	private int prcsId;// 步骤号

	@Column(name = "PRCS_NAME")
	private String prcsName = "";// 步骤名称

	@Column(name = "PRCS_DESC")
	private String prcsDesc = "";// 步骤说明

	@Column(name = "X")
	private int x;// 横坐标

	@Column(name = "Y")
	private int y;// 纵坐标

	/**
	 * 1-开始 2-结束 3-普通步骤节点 4-并发节点 5-聚合节点 6-子流程节点 7-柔性节点
	 */
	@Column(name = "PRCS_TYPE")
	private int prcsType;// 步骤类型

	@Column(name = "force_Para")
	private int forceParallel;// 强制并发

	@Column(name = "force_Aggre")
	private int forceAggregation;// 强制合并

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "FP_PRCS_USER", joinColumns = { @JoinColumn(name = "FLOW_PRCS_ID") }, inverseJoinColumns = { @JoinColumn(name = "PRCS_USER") })
	private Set<TeePerson> prcsUser = new HashSet();// 经办人

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "FP_PRCS_DEPT", joinColumns = { @JoinColumn(name = "FLOW_PRCS_ID") }, inverseJoinColumns = { @JoinColumn(name = "PRCS_DEPT") })
	private Set<TeeDepartment> prcsDept = new HashSet();// 经办部门

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "FP_PRCS_ROLE", joinColumns = { @JoinColumn(name = "FLOW_PRCS_ID") }, inverseJoinColumns = { @JoinColumn(name = "PRCS_ROLE") })
	private Set<TeeUserRole> prcsRole = new HashSet();// 经办角色

	@OneToMany(fetch = FetchType.LAZY,orphanRemoval = true,  cascade = { CascadeType.ALL }, mappedBy = "process")
	private List<TeeSelectUserRule> selectRules = new ArrayList();// 过滤选人规则

	@OneToMany(fetch = FetchType.LAZY,orphanRemoval = true,  cascade = { CascadeType.ALL }, mappedBy = "process")
	private List<TeeSelectAutoUserRule> selectAutoRules = new ArrayList();// 自动选人规则

	@Lob
	@Column(name = "SELECT_AUTO_RULES1")
	private String selectAutoRules1;// 高级自动选人规则

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "flowPrcs")
	private Set<TeeFlowPrintTemplate> printTemplate = new HashSet();// 打印模版

	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name = "IDXa0f83e09e77f42479d47d3aea6e")
	@JoinColumn(name = "FLOW_ID")
	private TeeFlowType flowType;// 所属流程

	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name = "IDXa0f83e09efuck2479d47d3aea6e")
	@JoinColumn(name = "O_FLOW_ID")
	private TeeFlowType oFlowType;// 原所属流程，逻辑删除用，为了记录该步骤之前所属的流程

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "FP_NEXT_PROCESS", joinColumns = { @JoinColumn(name = "FLOW_PRCS_ID") }, inverseJoinColumns = { @JoinColumn(name = "NEXT_PRCS") })
	private Set<TeeFlowProcess> nextProcess = new HashSet<TeeFlowProcess>(0);// 下一步骤节点

	@Column(name = "OP_FLAG")
	private int opFlag = 3;// 主办人选项 1：明确主办人 2：无主办会签 3：先接收为主办人

	@Column(name = "USER_LOCK")
	private int userLock = 1;// 是否允许选择0：锁定 1：不锁定

	@Column(name = "FEEDBACK")
	private int feedback = 1;// 会签选项 1：允许会签 0：不允许会签 2：强制会签

	@Column(name = "FEEDBACK_VIEW_TYPE")
	private int feedbackViewType = 1;// 会签可见类型 1：总是可见 2：本步骤经办人之间不可看 3：针对其他步骤不可见

	@Column(name = "FORCE_TURN")
	private int forceTurn = 1;// 强制转交 1：允许 0：不允许

	@Column(name = "GO_BACK")
	private int goBack;// 回退类型 0：不允许 1：回退上一步骤 2：回退之前步骤 3：回退到指定步骤

	@Column(name = "BACK_TO")
	private int backTo;// 回退到指定步骤

	/**
	 * 附件权限控制   1=查看  2=下载  4=删除  8=编辑  16=上传
	 */
	private int attachPriv = 0;

	private int attachOtherPriv = 0;

	private int runNamePriv;// 流程标题权限 1：允许 0：不允许

	// office文档权限 1-新建 2-编辑 4-删除 8-下载 16-打印
	private int officePriv;

	// office文档内部操作权限详细 1-保存 2-另存为 4-开启与关闭修订
	private int officePrivDetail;

	@Column(name = "NEXT_PRCS_ALERT")
	private int nextPrcsAlert = 3;// 下一步骤提醒

	@Column(name = "BEGIN_USER_ALERT")
	private int beginUserAlert = 0;// 发起人提醒

	@Column(name = "ALL_PRCS_USER_ALERT")
	private int allPrcsUserAlert = 0;// 所有经办人提醒

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinTable(name = "FP_EMAIL_TO_USER", joinColumns = { @JoinColumn(name = "FLOW_PRCS_ID") }, inverseJoinColumns = { @JoinColumn(name = "EMAIL_TO_USER") })
	private Set<TeePerson> emailToUsers = new HashSet();// 转交时邮件通知以下人员

	@Column(name = "TIMEOUT")
	private double timeout;// 办理时限

	@Column(name = "TIMEOUT_FLAG")
	private int timeoutFlag;// 允许转交设置办理时限 1:允许 0：不允许

	@Column(name = "TIMEOUT_TYPE")
	private int timeoutType = 1;// 超时计算方法，1：本步骤接收后开始计时 2：上一步骤转交后开始计时

	@Column(name = "IGNORE_TYPE")
	private int ignoreType;// 是否排除双休日 0：否 1：是

	@Column(name = "TM_HANDLEABLE", nullable = false)
	private int timeoutHandable;// 超时是否可办理

	@Column(name = "TIMEOUT_ALARM", nullable = false)
	private long timeoutAlarm;// 超时预警，0为不警报

	@Column(name = "SORT_NO")
	private int sortNo;// 步骤排序号

	// [{'itemId':'1','writable':'1','required':'1','visible':'1'},{'itemId':'2','writable':'1','required':'1','visible':'1'}]
	@Lob
	@Column(name = "FIELD_CTRL_MODEL")
	private String fieldCtrlModel;// 字段控制模型

	
	@Lob
	@Column(name = "ATTACH_CTRL_MODEL")
	private String attachCtrlModel;//附件权限
	
	//外接模块页面
	@Column(name = "OUTER_PAGE")
	private String outerPage;
	
	//内置模块页面
	@Column(name = "INNER_PAGE")
	private String innerPage;
	
	public String getAttachCtrlModel() {
		return attachCtrlModel;
	}

	public void setAttachCtrlModel(String attachCtrlModel) {
		this.attachCtrlModel = attachCtrlModel;
	}

	/**
	 * [{prcsTo:1,condition:[{itemId:
	 * '交办人任务3',value:'',oper:'1'},{itemId:'交办人任务',value:'',oper:'1'}],exp:'[1]
	 * AND [2]'}]
	 */
	@Lob
	@Column(name = "CONDITION_MODEL")
	private String conditionModel;// 流转条件模型

	@Column(name = "CHILD_FLOW_ID")
	private int childFlowId;// 子流程ID

	@Column(name = "FIELD_MAPPING")
	@Lob
	private String fieldMapping = "{}";// 子流程正向字段映射，即父流程的数据在发起时可以带入子流程中

	@Column(name = "FIELD_REVERSE_MAPPING")
	@Lob
	private String fieldReverseMapping = "{}";// 子流程反向字段映射，子流程的信息可以影响到父流程

	@Column(name = "SHARE_ATTACHES")
	private int shareAttaches;// 是否共享附件

	@Column(name = "SHARE_DOC")
	private int shareDoc;// 是否共享正文

	@Column(name = "MULTIINST")
	private int multiInst;// 是否允许多实例

	@Column(name = "PRCS_WAIT")
	private int prcsWait;// 是否等待子流程办理完毕

	@Column(name = "PLUGIN_CLASS")
	private String pluginClass = "";// 插件类路径

	@Column(name = "PRCS_EVENT_DEF")
	private String prcsEventDef;// 处理事件定义

	@Column(name = "ARCHIVES_PRIV", nullable = false)
	private int archivesPriv;// 归档权限

	
	@Column(name = "ADD_PRCS_USER_PRIV")
	private int addPrcsUserPriv;// 加签权限     1=本步骤办理人员    2=全部人员
	
	
	@Column(name = "FORM_SELECT")
	private int formSelect=1;// 1：全局表单     2：独立表单
	

    @Lob
	@Column(name = "FORM_SHORT")
	private String formShort;// 独立表单内容
    
    
    @Column(name = "AUTO_SELECT_FIRST")
	private int autoSelectFirst;//是否能选择第一个人
    
    
	public int getFormSelect() {
		return formSelect;
	}

	public void setFormSelect(int formSelect) {
		this.formSelect = formSelect;
	}

	public String getFormShort() {
		return formShort;
	}

	public void setFormShort(String formShort) {
		this.formShort = formShort;
	}

	/**
	 * 
	 * itemId 控件ID tType 1：输入值 2：表单字段 oType 1: 字符串 2：数字 3：日期 oper: <option
	 * value="1">等于</option> <option value="2">不等于</option> <option
	 * value="3">大于</option> <option value="4">大于等于</option> <option
	 * value="5">小于</option> <option value="6">小于等于</option> <option
	 * value="7">以字符开头</option> <option value="8">以字符结尾</option> <option
	 * value="9">包含</option> <option value="10">不包含</option>
	 * 
	 * val: 值 info: 提示信息
	 * [{itemId:'5',type:'1',tType:'',oType:'',oper:'',val:'',info:'提示信息'}]
	 */
	
	
	
	
	@Lob
	@Column(name = "FORM_VALID_MODEL")
	private String formValidModel;// 表单校验模型

	

	@Column(name = "AUTO_TURN")
	private int autoTurn;// 自动流转，无需选人 0：关闭 1:开启

	@Lob
	@Column(name = "ALARM_USER_IDS")
	private String alarmUserIds;// 提醒用户

	@Lob
	@Column(name = "ALARM_DEPT_IDS")
	private String alarmDeptIds;// 提醒部门

	@Lob
	@Column(name = "ALARM_ROLE_IDS")
	private String alarmRoleIds;// 提醒角色

	// [{'itemId':'1','writable':'1','required':'1','visible':'1'},{'itemId':'2','writable':'1','required':'1','visible':'1'}]
	//手机端字段是否显示
	@Lob
	@Column(name = "mobile_filed_ctr_model")
	private String mobileFieldCtrlModel;// 手机端字段显示控制模型

	//是否开启移动表单模板
	@Column(name = "is_open_mobile_ctr")
	private int isOpenMobileCtr;
	
	//流程信息控制模型
	@Lob
	@Column(name = "work_flow_ctr_model")
	private String workFlowCtrlModel;
	
	//开启自动选人
	@Column(name = "AUTO_SELECT")
	private int autoSelect;
	
	//是否开启串签功能  0  不开启  1  开启
	@Column(name = "SEQ_OPER")
	private int seqOper = 0;
	
	//盖章规则
	@Column(name = "SEAL_RULES")
	private String sealRules;
	
	//触发器http地址
	@Column(name = "TRIGGER_URL")
	private String triggerUrl;
	
	
	//短信提醒模板
	@Column(name = "SMS_TPL")
	private String smsTpl;
	
	
	
	
	
	public String getSmsTpl() {
		return smsTpl;
	}

	public void setSmsTpl(String smsTpl) {
		this.smsTpl = smsTpl;
	}

	public String getWorkFlowCtrlModel() {
		return workFlowCtrlModel;
	}

	public void setWorkFlowCtrlModel(String workFlowCtrlModel) {
		this.workFlowCtrlModel = workFlowCtrlModel;
	}

	public int getIsOpenMobileCtr() {
		return isOpenMobileCtr;
	}

	public void setIsOpenMobileCtr(int isOpenMobileCtr) {
		this.isOpenMobileCtr = isOpenMobileCtr;
	}

	

	public String getMobileFieldCtrlModel() {
		return mobileFieldCtrlModel;
	}

	public void setMobileFieldCtrlModel(String mobileFieldCtrlModel) {
		this.mobileFieldCtrlModel = mobileFieldCtrlModel;
	}

	public String getAlarmUserIds() {
		return alarmUserIds;
	}

	public void setAlarmUserIds(String alarmUserIds) {
		this.alarmUserIds = alarmUserIds;
	}

	public String getAlarmDeptIds() {
		return alarmDeptIds;
	}

	public void setAlarmDeptIds(String alarmDeptIds) {
		this.alarmDeptIds = alarmDeptIds;
	}

	public String getAlarmRoleIds() {
		return alarmRoleIds;
	}

	public void setAlarmRoleIds(String alarmRoleIds) {
		this.alarmRoleIds = alarmRoleIds;
	}

	public int getPrcsId() {
		return prcsId;
	}

	public void setPrcsId(int prcsId) {
		this.prcsId = prcsId;
	}

	public String getPrcsName() {
		return prcsName;
	}

	public void setPrcsName(String prcsName) {
		this.prcsName = prcsName;
	}

	public int getPrcsType() {
		return prcsType;
	}

	public void setPrcsType(int prcsType) {
		this.prcsType = prcsType;
	}

	public TeeFlowType getFlowType() {
		return flowType;
	}

	public void setFlowType(TeeFlowType flowType) {
		this.flowType = flowType;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getSid() {
		return sid;
	}

	public Set<TeePerson> getPrcsUser() {
		return prcsUser;
	}

	public void setPrcsUser(Set<TeePerson> prcsUser) {
		this.prcsUser = prcsUser;
	}

	public Set<TeeDepartment> getPrcsDept() {
		return prcsDept;
	}

	public void setPrcsDept(Set<TeeDepartment> prcsDept) {
		this.prcsDept = prcsDept;
	}

	public Set<TeeUserRole> getPrcsRole() {
		return prcsRole;
	}

	public void setPrcsRole(Set<TeeUserRole> prcsRole) {
		this.prcsRole = prcsRole;
	}

	public List<TeeSelectUserRule> getSelectRules() {
		return selectRules;
	}

	public void setSelectRules(List<TeeSelectUserRule> selectRules) {
		this.selectRules = selectRules;
	}

	public List<TeeSelectAutoUserRule> getSelectAutoRules() {
		return selectAutoRules;
	}

	public void setSelectAutoRules(List<TeeSelectAutoUserRule> selectAutoRules) {
		this.selectAutoRules = selectAutoRules;
	}

	public Set<TeeFlowProcess> getNextProcess() {
		return nextProcess;
	}

	public void setNextProcess(Set<TeeFlowProcess> nextProcess) {
		this.nextProcess = nextProcess;
	}

	public int getFeedback() {
		return feedback;
	}

	public void setFeedback(int feedback) {
		this.feedback = feedback;
	}

	public int getFeedbackViewType() {
		return feedbackViewType;
	}

	public void setFeedbackViewType(int feedbackViewType) {
		this.feedbackViewType = feedbackViewType;
	}

	public int getForceTurn() {
		return forceTurn;
	}

	public void setForceTurn(int forceTurn) {
		this.forceTurn = forceTurn;
	}

	public int getGoBack() {
		return goBack;
	}

	public void setGoBack(int goBack) {
		this.goBack = goBack;
	}

	public int getNextPrcsAlert() {
		return nextPrcsAlert;
	}

	public void setNextPrcsAlert(int nextPrcsAlert) {
		this.nextPrcsAlert = nextPrcsAlert;
	}

	public int getBeginUserAlert() {
		return beginUserAlert;
	}

	public void setBeginUserAlert(int beginUserAlert) {
		this.beginUserAlert = beginUserAlert;
	}

	public int getAllPrcsUserAlert() {
		return allPrcsUserAlert;
	}

	public void setAllPrcsUserAlert(int allPrcsUserAlert) {
		this.allPrcsUserAlert = allPrcsUserAlert;
	}

	public Set<TeePerson> getEmailToUsers() {
		return emailToUsers;
	}

	public void setEmailToUsers(Set<TeePerson> emailToUsers) {
		this.emailToUsers = emailToUsers;
	}

	public double getTimeout() {
		return timeout;
	}

	public void setTimeout(double timeout) {
		this.timeout = timeout;
	}

	public int getTimeoutType() {
		return timeoutType;
	}

	public void setTimeoutType(int timeoutType) {
		this.timeoutType = timeoutType;
	}

	public int getIgnoreType() {
		return ignoreType;
	}

	public void setIgnoreType(int ignoreType) {
		this.ignoreType = ignoreType;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public int getSortNo() {
		return sortNo;
	}

	public void setPrcsDesc(String prcsDesc) {
		this.prcsDesc = prcsDesc;
	}

	public String getPrcsDesc() {
		return prcsDesc;
	}

	public int getOpFlag() {
		return opFlag;
	}

	public void setOpFlag(int opFlag) {
		this.opFlag = opFlag;
	}

	public int getUserLock() {
		return userLock;
	}

	public void setUserLock(int userLock) {
		this.userLock = userLock;
	}

	public int getTimeoutFlag() {
		return timeoutFlag;
	}

	public void setTimeoutFlag(int timeoutFlag) {
		this.timeoutFlag = timeoutFlag;
	}

	public void setFieldCtrlModel(String fieldCtrlModel) {
		this.fieldCtrlModel = fieldCtrlModel;
	}

	public String getFieldCtrlModel() {
		if (fieldCtrlModel == null || "".equals(fieldCtrlModel)) {
			fieldCtrlModel = "[]";
		}
		return fieldCtrlModel;
	}

	public void setConditionModel(String conditionModel) {
		this.conditionModel = conditionModel;
	}

	public String getConditionModel() {
		return conditionModel;
	}

	public void setBackTo(int backTo) {
		this.backTo = backTo;
	}

	public int getBackTo() {
		return backTo;
	}

	public Set<TeeFlowPrintTemplate> getPrintTemplate() {
		return printTemplate;
	}

	public void setPrintTemplate(Set<TeeFlowPrintTemplate> printTemplate) {
		this.printTemplate = printTemplate;
	}

	public int getAttachPriv() {
		return attachPriv;
	}

	public void setAttachPriv(int attachPriv) {
		this.attachPriv = attachPriv;
	}

	public int getRunNamePriv() {
		return runNamePriv;
	}

	public void setRunNamePriv(int runNamePriv) {
		this.runNamePriv = runNamePriv;
	}

	public int getOfficePriv() {
		return officePriv;
	}

	public void setOfficePriv(int officePriv) {
		this.officePriv = officePriv;
	}

	public int getOfficePrivDetail() {
		return officePrivDetail;
	}

	public void setOfficePrivDetail(int officePrivDetail) {
		this.officePrivDetail = officePrivDetail;
	}

	public void setFieldMapping(String fieldMapping) {
		this.fieldMapping = fieldMapping;
	}

	public String getFieldMapping() {
		return fieldMapping;
	}

	public void setFieldReverseMapping(String fieldReverseMapping) {
		this.fieldReverseMapping = fieldReverseMapping;
	}

	public String getFieldReverseMapping() {
		return fieldReverseMapping;
	}

	public void setChildFlowId(int childFlowId) {
		this.childFlowId = childFlowId;
	}

	public int getChildFlowId() {
		return childFlowId;
	}

	public void setShareAttaches(int shareAttaches) {
		this.shareAttaches = shareAttaches;
	}

	public int getShareAttaches() {
		return shareAttaches;
	}

	public void setMultiInst(int multiInst) {
		this.multiInst = multiInst;
	}

	public int getMultiInst() {
		return multiInst;
	}

	public void setForceParallel(int forceParallel) {
		this.forceParallel = forceParallel;
	}

	public int getForceParallel() {
		return forceParallel;
	}

	public void setForceAggregation(int forceAggregation) {
		this.forceAggregation = forceAggregation;
	}

	public int getForceAggregation() {
		return forceAggregation;
	}

	public String getPluginClass() {
		return pluginClass;
	}

	public void setPluginClass(String pluginClass) {
		this.pluginClass = pluginClass;
	}

	public String getSelectAutoRules1() {
		return selectAutoRules1;
	}

	public void setSelectAutoRules1(String selectAutoRules1) {
		this.selectAutoRules1 = selectAutoRules1;
	}

	public String getPrcsEventDef() {
		return prcsEventDef;
	}

	public void setPrcsEventDef(String prcsEventDef) {
		this.prcsEventDef = prcsEventDef;
	}

	public int getTimeoutHandable() {
		return timeoutHandable;
	}

	public void setTimeoutHandable(int timeoutHandable) {
		this.timeoutHandable = timeoutHandable;
	}

	public long getTimeoutAlarm() {
		return timeoutAlarm;
	}

	public void setTimeoutAlarm(long timeoutAlarm) {
		this.timeoutAlarm = timeoutAlarm;
	}

	public int getArchivesPriv() {
		return archivesPriv;
	}

	public void setArchivesPriv(int archivesPriv) {
		this.archivesPriv = archivesPriv;
	}

	public String getFormValidModel() {
		if (formValidModel == null || "".equals(formValidModel)) {
			formValidModel = "[]";
		}
		return formValidModel;
	}

	public void setFormValidModel(String formValidModel) {
		this.formValidModel = formValidModel;
	}

	public int getAttachOtherPriv() {
		return attachOtherPriv;
	}

	public void setAttachOtherPriv(int attachOtherPriv) {
		this.attachOtherPriv = attachOtherPriv;
	}

	public int getShareDoc() {
		return shareDoc;
	}

	public void setShareDoc(int shareDoc) {
		this.shareDoc = shareDoc;
	}

	public TeeFlowType getoFlowType() {
		return oFlowType;
	}

	public void setoFlowType(TeeFlowType oFlowType) {
		this.oFlowType = oFlowType;
	}

	public int getPrcsWait() {
		return prcsWait;
	}

	public void setPrcsWait(int prcsWait) {
		this.prcsWait = prcsWait;
	}

	public int getAutoTurn() {
		return autoTurn;
	}

	public void setAutoTurn(int autoTurn) {
		this.autoTurn = autoTurn;
	}

	public int getAutoSelect() {
		return autoSelect;
	}

	public void setAutoSelect(int autoSelect) {
		this.autoSelect = autoSelect;
	}

	public int getSeqOper() {
		return seqOper;
	}

	public void setSeqOper(int seqOper) {
		this.seqOper = seqOper;
	}

	public String getOuterPage() {
		return outerPage;
	}

	public void setOuterPage(String outerPage) {
		this.outerPage = outerPage;
	}

	public String getInnerPage() {
		return innerPage;
	}

	public void setInnerPage(String innerPage) {
		this.innerPage = innerPage;
	}
	
	public int getAddPrcsUserPriv() {
		return addPrcsUserPriv;
	}

	public void setAddPrcsUserPriv(int addPrcsUserPriv) {
		this.addPrcsUserPriv = addPrcsUserPriv;
	}

	public String getSealRules() {
		return sealRules;
	}

	public void setSealRules(String sealRules) {
		this.sealRules = sealRules;
	}

	public String getTriggerUrl() {
		return triggerUrl;
	}

	public void setTriggerUrl(String triggerUrl) {
		this.triggerUrl = triggerUrl;
	}

	public int getAutoSelectFirst() {
		return autoSelectFirst;
	}

	public void setAutoSelectFirst(int autoSelectFirst) {
		this.autoSelectFirst = autoSelectFirst;
	}
	
	
}
