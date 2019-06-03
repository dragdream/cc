package com.tianee.oa.webframe.httpModel.core.workflow;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;

public class TeeFlowProcessModel extends TeeBaseModel{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int sid;
	private int prcsId;//步骤号
	private String prcsName;//步骤名称
	private String prcsDesc;//步骤说明
	private String triggerUrl;
	private int x;//横坐标
	private int y;//纵坐标
	private int prcsType;//步骤类型
	private int viewPriv;//是否允许传阅 0：不允许 1：允许主办人传阅 2：允许所有人传阅
	private int flowTypeId;//所属流程
	private int oFlowTypeId;//所属流程
	private int opFlag;//主办人选项 1：明确主办人 2：无主办会签 3：先接收为主办人
	private int userLock;//是否允许选择0：不允许 1：允许
	private int feedback;//会签选项 1：允许会签 0：不允许会签 2：强制会签
	private int feedbackViewType;//会签可见类型 1：总是可见 2：本步骤经办人之间不可看 3：针对其他步骤不可见
	private int forceTurn;//强制转交 1：允许  0：不允许
	private int goBack;//回退类型 0：不允许 1：回退上一步骤 2：回退之前步骤
	private int backTo;//回退到指定步骤 
	private int nextPrcsAlert;//下一步骤提醒
	private int beginUserAlert;//发起人提醒
	private String sealRules;//盖章规则
	private int allPrcsUserAlert;//所有经办人提醒
	private double timeout;//办理时限
	private int timeoutFlag;//允许转交设置办理时限 1:允许 0：不允许
	private int timeoutType;//超时计算方法，1：本步骤接收后开始计时 2：上一步骤转交后开始计时
	private int ignoreType;//是否排除双休日  0：否 1：是
	private int sortNo;//步骤排序号
	private int attachPriv;//公共流程附件权限 	1：允许	0：不允许
	private int attachOtherPriv;
	private int runNamePriv;//流程标题权限	1：允许	0：不允许
	//office文档权限	1-新建 2-编辑 4-删除 8-下载 16-打印
	private int officePriv;
	//office文档内部操作权限详细 1-保存 2-另存为 4-开启与关闭修订
	private int officePrivDetail;
	private int flowPrcsId;
	private int childFlowId;//子流程ID
	private String fieldMapping = "{}";//子流程正向字段映射，即父流程的数据在发起时可以带入子流程中
	private String fieldReverseMapping = "{}";//子流程反向字段映射，子流程的信息可以影响到父流程
	private int shareAttaches;//是否共享附件
	private int multiInst;//是否允许多实例
	private String conditionModel="[]";//默认条件模型
	private int forceParallel;//强制并发
	private int forceAggregation;//强制合并
	private String pluginClass = "";//插件类路径
	private String selectAutoRules1;//高级自动选人规则
	private String prcsEventDef;//处理事件定义，逗号分隔
	private int timeoutHandable;//超时是否可办理
	private long timeoutAlarm;//超时预警，0为不警报
	private int archivesPriv;//归档权限
	private String formValidModel;//表单校验模型
	private int shareDoc;//是否共享正文
	private int prcsWait;//是否等待子流程办理完毕
	private Map params = new HashMap();//附加数据
	private int autoTurn;//自动流转，无需选人  0：关闭   1:开启
	private String alarmUserIds;//提醒用户
	private String alarmDeptIds;//提醒部门
	private String alarmRoleIds;//提醒角色
	private int seqOper = 0;//串签
	private int autoSelect;
	private String outerPage;
	private String innerPage;
	private int autoSelectFirst;//是否能选择第一个人
	
	private int addPrcsUserPriv;//加签权限
	
	private int formSelect;// 1：全局表单     2：独立表单
	
	private String formShort;// 独立表单内容
	
	private String smsTpl;//短信提醒模板
	
	
	
	
	
	public String getSmsTpl() {
		return smsTpl;
	}
	public void setSmsTpl(String smsTpl) {
		this.smsTpl = smsTpl;
	}
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
	
	
	public int getAddPrcsUserPriv() {
		return addPrcsUserPriv;
	}
	public void setAddPrcsUserPriv(int addPrcsUserPriv) {
		this.addPrcsUserPriv = addPrcsUserPriv;
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
	private String alarmUserNames;//提醒用户
	private String alarmDeptNames;//提醒部门
	private String alarmRoleNames;//提醒角色
	public String getAlarmUserNames() {
		return alarmUserNames;
	}
	public void setAlarmUserNames(String alarmUserNames) {
		this.alarmUserNames = alarmUserNames;
	}
	public String getAlarmDeptNames() {
		return alarmDeptNames;
	}
	public void setAlarmDeptNames(String alarmDeptNames) {
		this.alarmDeptNames = alarmDeptNames;
	}
	public String getAlarmRoleNames() {
		return alarmRoleNames;
	}
	public void setAlarmRoleNames(String alarmRoleNames) {
		this.alarmRoleNames = alarmRoleNames;
	}
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
	public String getPrcsName() {
		return prcsName;
	}
	public void setPrcsName(String prcsName) {
		this.prcsName = prcsName;
	}
	public String getPrcsDesc() {
		return prcsDesc;
	}
	public void setPrcsDesc(String prcsDesc) {
		this.prcsDesc = prcsDesc;
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
	public int getPrcsType() {
		return prcsType;
	}
	public void setPrcsType(int prcsType) {
		this.prcsType = prcsType;
	}
	public int getViewPriv() {
		return viewPriv;
	}
	public void setViewPriv(int viewPriv) {
		this.viewPriv = viewPriv;
	}
	public int getFlowTypeId() {
		return flowTypeId;
	}
	public void setFlowTypeId(int flowTypeId) {
		this.flowTypeId = flowTypeId;
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
	public double getTimeout() {
		return timeout;
	}
	public void setTimeout(double timeout) {
		this.timeout = timeout;
	}
	public int getTimeoutFlag() {
		return timeoutFlag;
	}
	public void setTimeoutFlag(int timeoutFlag) {
		this.timeoutFlag = timeoutFlag;
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
	public int getSortNo() {
		return sortNo;
	}
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
	public Map getParams() {
		return params;
	}
	public void setParams(Map params) {
		this.params = params;
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
	public int getFlowPrcsId() {
		return flowPrcsId;
	}
	public void setFlowPrcsId(int flowPrcsId) {
		this.flowPrcsId = flowPrcsId;
	}
	public int getChildFlowId() {
		return childFlowId;
	}
	public void setChildFlowId(int childFlowId) {
		this.childFlowId = childFlowId;
	}
	public String getFieldMapping() {
		return fieldMapping;
	}
	public void setFieldMapping(String fieldMapping) {
		this.fieldMapping = fieldMapping;
	}
	public String getFieldReverseMapping() {
		return fieldReverseMapping;
	}
	public void setFieldReverseMapping(String fieldReverseMapping) {
		this.fieldReverseMapping = fieldReverseMapping;
	}
	public int getShareAttaches() {
		return shareAttaches;
	}
	public void setShareAttaches(int shareAttaches) {
		this.shareAttaches = shareAttaches;
	}
	public void setMultiInst(int multiInst) {
		this.multiInst = multiInst;
	}
	public int getMultiInst() {
		return multiInst;
	}
	public void setConditionModel(String conditionModel) {
		this.conditionModel = conditionModel;
	}
	public String getConditionModel() {
		return conditionModel;
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
	public int getoFlowTypeId() {
		return oFlowTypeId;
	}
	public void setoFlowTypeId(int oFlowTypeId) {
		this.oFlowTypeId = oFlowTypeId;
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
	public int getBackTo() {
		return backTo;
	}
	public void setBackTo(int backTo) {
		this.backTo = backTo;
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
