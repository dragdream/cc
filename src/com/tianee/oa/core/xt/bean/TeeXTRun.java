package com.tianee.oa.core.xt.bean;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;
/**
 * 协同主类
 * @author xsy
 *
 */
@Entity
@Table(name="XT_RUN")
public class TeeXTRun {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="XT_RUN_seq_gen")
	@SequenceGenerator(name="XT_RUN_seq_gen", sequenceName="XT_RUN_seq")
	private int sid;//主键
	
	@Column(name="SUBJECT")
	private String subject;//标题
	
	
	@Column(name="IMPORTANT_LEVEL")
	private int importantLevel;//事项级别
	
	@Column(name="SMS_REMIND")
	private int smsRemind;//是否短信提醒
	
	@Column(name="USER_IDS")
	private String userIds;//办理人员id字符串
	
	@Column(name="DEAD_LINE")
	private int deadLine;//事项期限
	
	
	@Column(name="DEAD_LINE_TIME")
	private Calendar deadLineTime;//事项到期时间
	
	@Column(name="ADVANCE_REMIND")
	private int advanceRemind;//提前提醒
	
	@Column(name="AUTO_STOP")
	private int autoStop;//是否到期自动终止
	
	@Column(name="OPT_PRIV")
	private int optPriv;//允许操作权限
	
	@Column(name="CREATE_TIME")
	private Calendar createTime;//创建时间
	
	@Column(name="SEND_TIME")
	private Calendar sendTime;//发起时间
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CREATE_USER_ID")
	private TeePerson createUser;//创建人
	
	
	@Column(name="STATUS")
	private int status;//状态   0=保存未发送   1=已发送未终止   2=已发送已中终止
	
	@Column(name="REMARK")
	private String remark;//备注
	
	
	@Column(name="DOC_TYPE")
	private int docType;//正文类型  1=标准正文   2=word正文  3=excel正文   4=pdf正文
	
	
	@Column(name="CONTENT")
	private String content;//标准正文内容
	
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="DOC_ID")
	private TeeAttachment doc;//word正文   excel正文  pdf正文 所对应的附件


	@Column(name="REPEAT_FY")
	private int repeatFy;//转发原附言
	
	
	@Column(name="REPEAT_YJ")
	private int repeatYj;//转发原意见
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PARENT_ID")
	private TeeXTRun parent;//父级事项
	
	
	
	
	
	public int getRepeatFy() {
		return repeatFy;
	}


	public void setRepeatFy(int repeatFy) {
		this.repeatFy = repeatFy;
	}


	public int getRepeatYj() {
		return repeatYj;
	}


	public void setRepeatYj(int repeatYj) {
		this.repeatYj = repeatYj;
	}


	public TeeXTRun getParent() {
		return parent;
	}


	public void setParent(TeeXTRun parent) {
		this.parent = parent;
	}


	public Calendar getSendTime() {
		return sendTime;
	}


	public void setSendTime(Calendar sendTime) {
		this.sendTime = sendTime;
	}


	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public int getImportantLevel() {
		return importantLevel;
	}


	public void setImportantLevel(int importantLevel) {
		this.importantLevel = importantLevel;
	}


	public int getSmsRemind() {
		return smsRemind;
	}


	public void setSmsRemind(int smsRemind) {
		this.smsRemind = smsRemind;
	}


	public String getUserIds() {
		return userIds;
	}


	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}


	public int getDeadLine() {
		return deadLine;
	}


	public void setDeadLine(int deadLine) {
		this.deadLine = deadLine;
	}


	public Calendar getDeadLineTime() {
		return deadLineTime;
	}


	public void setDeadLineTime(Calendar deadLineTime) {
		this.deadLineTime = deadLineTime;
	}


	public int getAdvanceRemind() {
		return advanceRemind;
	}


	public void setAdvanceRemind(int advanceRemind) {
		this.advanceRemind = advanceRemind;
	}


	public int getAutoStop() {
		return autoStop;
	}


	public void setAutoStop(int autoStop) {
		this.autoStop = autoStop;
	}


	public int getOptPriv() {
		return optPriv;
	}


	public void setOptPriv(int optPriv) {
		this.optPriv = optPriv;
	}


	public Calendar getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}


	public TeePerson getCreateUser() {
		return createUser;
	}


	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
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


	public int getDocType() {
		return docType;
	}


	public void setDocType(int docType) {
		this.docType = docType;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public TeeAttachment getDoc() {
		return doc;
	}


	public void setDoc(TeeAttachment doc) {
		this.doc = doc;
	}
	
	
	
	
	
	
}
