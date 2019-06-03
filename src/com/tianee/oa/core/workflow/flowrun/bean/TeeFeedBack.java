package com.tianee.oa.core.workflow.flowrun.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;


@Entity
@Table(name="feed_back")
public class TeeFeedBack  implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="feed_back_seq_gen")
	@SequenceGenerator(name="feed_back_seq_gen", sequenceName="feed_back_seq")
	private int sid;
	
	@JoinColumn(name="run_Id")
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX75cbf473cbb04965885e3ed3be4")
	private TeeFlowRun flowRun;
	
	@Column(name="prcs_Id")
	private int prcsId;
	
	@ManyToOne
	@Index(name="IDXebaca4e9175d4f428f09835cefd")
	@JoinColumn(name="FLOW_PRCS")
	private TeeFlowProcess flowPrcs;
	
	@ManyToOne
	@Index(name="IDX8de300c91d2c4da0b30e9cf5d33")
	private TeePerson userPerson;
	
	@Lob
	@Column(name="content")
	private String content;
	
	@Column(name="edit_Time")
	private Calendar editTime;
	
	@Column(name="feed_Flag")
	private int feedFlag;
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name="sign_Data")
	private String signData;
	
	@Column(name="replay_Id")
	private int replayId;
	
	@Column(name="voice_id")
	private String voiceId;
	
//	@OneToMany(mappedBy="feedback",cascade=CascadeType.ALL)
//	private List<TeeFeedBackAttach> attachments = new ArrayList<TeeFeedBackAttach>(0);
	@Column(name="BACK_FLAG",nullable=false)
	private int backFlag;//回退标记
	
	@Column(name="user_name")
	private String userName;

	@Column(name="dept_name")
	private String deptName;
	
	@Column(name="dept_full_path")
	private String deptFullPath;
	
	@Column(name="role_name")
	private String roleName;
	
	@Column(name="SIGNATURE")
	private String signature;
	
	
	public String getUserName() {
		return userName;
	}

	public String getDeptName() {
		return deptName;
	}

	public String getDeptFullPath() {
		return deptFullPath;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setDeptFullPath(String deptFullPath) {
		this.deptFullPath = deptFullPath;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Calendar getEditTime() {
		return editTime;
	}

	public void setEditTime(Calendar editTime) {
		this.editTime = editTime;
	}

	
	public int getFeedFlag() {
		return feedFlag;
	}

	public void setFeedFlag(int feedFlag) {
		this.feedFlag = feedFlag;
	}

	public String getSignData() {
		return signData;
	}

	public void setSignData(String signData) {
		this.signData = signData;
	}

	public int getReplayId() {
		return replayId;
	}

	public void setReplayId(int replayId) {
		this.replayId = replayId;
	}

	public TeePerson getUserPerson() {
		return userPerson;
	}

	public void setUserPerson(TeePerson userPerson) {
		this.userPerson = userPerson;
	}

	public TeeFlowProcess getFlowPrcs() {
		return flowPrcs;
	}

	public void setFlowPrcs(TeeFlowProcess flowPrcs) {
		this.flowPrcs = flowPrcs;
	}

	public void setFlowRun(TeeFlowRun flowRun) {
		this.flowRun = flowRun;
	}

	public TeeFlowRun getFlowRun() {
		return flowRun;
	}

	public String getVoiceId() {
		return voiceId;
	}

	public void setVoiceId(String voiceId) {
		this.voiceId = voiceId;
	}

	public int getBackFlag() {
		return backFlag;
	}

	public void setBackFlag(int backFlag) {
		this.backFlag = backFlag;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	
}
