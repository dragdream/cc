package com.tianee.oa.core.workflow.flowrun.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;

/**
 * 流程会签控件数据
 * @author kakalion
 *
 */
@Entity
@Table(name="FLOW_RUN_CTRL_FB")
public class TeeFlowRunCtrlFeedback {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="FLOW_RUN_CTRL_FB_seq_gen")
	@SequenceGenerator(name="FLOW_RUN_CTRL_FB_seq_gen", sequenceName="FLOW_RUN_CTRL_FB_seq")
	private int sid;
	
	@Column(name="ITEM_ID")
	private int itemId;
	
	@Lob
	@Column(name="CONTENT")
	private String content;
	
	@Lob
	@Column(name="SIGN_DATA")
	private String signData;
	
	@Lob
	@Column(name="SEAL_DATA")
	private String sealData;//盖章数据

	@Lob
	@Column(name="HW_DATA")
	private String hwData;//手写数据
	
	@Lob
	@Column(name="PIC_DATA")
	private String picData;//图片盖章数据
	
	@Lob
	@Column(name="H5_DATA")
	private String h5Data;//H5手写数据
	
	@Lob
	@Column(name="MOBI_DATA")
	private String mobiData;//移动签批数据
	
	@ManyToOne
	@Index(name="IDX4a02eeb2b3fb4613bfad156252d")
	@JoinColumn(name="RUN_ID")
	private TeeFlowRun flowRun;
	
	@Column(name="CREATE_TIME")
	private Calendar createTime = Calendar.getInstance();
	
	@ManyToOne
	@Index(name="IDX7c37d98a84734a7781faf58448e")
	@JoinColumn(name="CREATE_USER")
	private TeePerson createUser;
	
	@Column(name="RAND0")
	private String rand;
	
	@Column(name="USER_NAME")
	private String userName;
	
	@Column(name="DEPT_NAME")
	private String deptName;
	
	@Column(name="DEPT_NAME_PATH")
	private String deptNamePath;
	
	@Column(name="ROLE_NAME")
	private String roleName;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX_FLOW_RUN_CTRL_FB_FLOW_PRCS")
	@JoinColumn(name="FLOW_PRCS")
	private TeeFlowProcess flowPrcs;//所属设计步骤
	
	@Column(name="PRCS_ID")
	private int prcsId;
	
	@Column(name="SIGNATURE")
	private String signature;

	public TeeFlowProcess getFlowPrcs() {
		return flowPrcs;
	}

	public void setFlowPrcs(TeeFlowProcess flowPrcs) {
		this.flowPrcs = flowPrcs;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSignData() {
		return signData;
	}

	public void setSignData(String signData) {
		this.signData = signData;
	}

	public TeeFlowRun getFlowRun() {
		return flowRun;
	}

	public void setFlowRun(TeeFlowRun flowRun) {
		this.flowRun = flowRun;
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

	public void setRand(String rand) {
		this.rand = rand;
	}

	public String getRand() {
		return rand;
	}

	public String getSealData() {
		return sealData;
	}

	public void setSealData(String sealData) {
		this.sealData = sealData;
	}

	public String getHwData() {
		return hwData;
	}

	public void setHwData(String hwData) {
		this.hwData = hwData;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptNamePath() {
		return deptNamePath;
	}

	public void setDeptNamePath(String deptNamePath) {
		this.deptNamePath = deptNamePath;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getPicData() {
		return picData;
	}

	public void setPicData(String picData) {
		this.picData = picData;
	}

	public String getH5Data() {
		return h5Data;
	}

	public void setH5Data(String h5Data) {
		this.h5Data = h5Data;
	}

	public String getMobiData() {
		return mobiData;
	}

	public void setMobiData(String mobiData) {
		this.mobiData = mobiData;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public int getPrcsId() {
		return prcsId;
	}

	public void setPrcsId(int prcsId) {
		this.prcsId = prcsId;
	}
	
	
}
