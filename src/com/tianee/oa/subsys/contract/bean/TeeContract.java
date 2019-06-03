package com.tianee.oa.subsys.contract.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name="CONTRACT")
public class TeeContract {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CONTRACT_seq_gen")
	@SequenceGenerator(name="CONTRACT_seq_gen", sequenceName="CONTRACT_seq")
	private int sid;
	
	@Column(name="CONTRACT_NAME")
	private String contractName;//合同名称
	
	@JoinColumn(name="CAT_ID")
	@ManyToOne
	@Index(name="IDX0a4359029b6b4e608bc2e30298c")
	private TeeContractCategory category;//合同分类
	
	@JoinColumn(name="SORT_ID")
	@ManyToOne
	@Index(name="IDXb306546bc97e4df28106815a8e4")
	private TeeContractSort contractSort;//合同类型
	
	@Column(name="CONTRACT_CODE")
	private String contractCode;//合同编号
	
	@JoinColumn(name="DEPT_ID")
	@ManyToOne
	@Index(name="IDXd1bd060d1d8b46ceb7ed59d5810")
	private TeeDepartment dept;//所属部门
	
	@Column(name="BIS_USER")
	private String bisUser;//业务员
	
	@Column(name="VER_TIME")
	private Long verTime;//签订时间
	
	@Column(name="START_TIME")
	private Long startTime;//开始时间
	
	@Column(name="END_TIME")
	private Long endTime;//结束时间
	
	@Column(name="AMOUNT")
	private double amount;//合同额
	
	@JoinColumn(name="OPER_USER_ID")
	@ManyToOne
	@Index(name="IDXa13a616f439947e3b8822ce8044")
	private TeePerson operUser;//合同录入人
	
	@Column(name="PARTY_A_UNIT")
	private String partyAUnit;//甲方单位
	
	@Column(name="PARTY_A_CHARGER")
	private String partyACharger;//甲方负责人
	
	@Column(name="PARTY_A_CONTACT")
	private String partyAContact;//甲方联系方式
	
	@Column(name="PARTY_B_UNIT")
	private String partyBUnit;//乙方单位
	
	@Column(name="PARTY_B_CHARGER")
	private String partyBCharger;//乙方负责人
	
	@Column(name="PARTY_B_CONTACT")
	private String partyBContact;//乙方联系方式
	
	@Lob
	@Column(name="CONTENT")
	private String content;//合同内容
	
	@Lob
	@Column(name="REMARK")
	private String remark;//合同备注
	
	@Column(name="RUN_ID")
	private int runId;//相关流程ID
	
	@Column(name="ATTACH_IDS")
	@Lob
	private String attachIds;//附件IDS

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public TeeContractCategory getCategory() {
		return category;
	}

	public void setCategory(TeeContractCategory category) {
		this.category = category;
	}

	public TeeContractSort getContractSort() {
		return contractSort;
	}

	public void setContractSort(TeeContractSort contractSort) {
		this.contractSort = contractSort;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public TeeDepartment getDept() {
		return dept;
	}

	public void setDept(TeeDepartment dept) {
		this.dept = dept;
	}

	public String getBisUser() {
		return bisUser;
	}

	public void setBisUser(String bisUser) {
		this.bisUser = bisUser;
	}

	public Long getVerTime() {
		return verTime;
	}

	public void setVerTime(Long verTime) {
		this.verTime = verTime;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public TeePerson getOperUser() {
		return operUser;
	}

	public void setOperUser(TeePerson operUser) {
		this.operUser = operUser;
	}

	public String getPartyAUnit() {
		return partyAUnit;
	}

	public void setPartyAUnit(String partyAUnit) {
		this.partyAUnit = partyAUnit;
	}

	public String getPartyACharger() {
		return partyACharger;
	}

	public void setPartyACharger(String partyACharger) {
		this.partyACharger = partyACharger;
	}

	public String getPartyAContact() {
		return partyAContact;
	}

	public void setPartyAContact(String partyAContact) {
		this.partyAContact = partyAContact;
	}

	public String getPartyBUnit() {
		return partyBUnit;
	}

	public void setPartyBUnit(String partyBUnit) {
		this.partyBUnit = partyBUnit;
	}

	public String getPartyBCharger() {
		return partyBCharger;
	}

	public void setPartyBCharger(String partyBCharger) {
		this.partyBCharger = partyBCharger;
	}

	public String getPartyBContact() {
		return partyBContact;
	}

	public void setPartyBContact(String partyBContact) {
		this.partyBContact = partyBContact;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getRunId() {
		return runId;
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}

	public String getAttachIds() {
		return attachIds;
	}

	public void setAttachIds(String attachIds) {
		this.attachIds = attachIds;
	}
	
	
}
