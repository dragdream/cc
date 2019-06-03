package com.tianee.oa.core.base.fixedAssets.bean;
import org.hibernate.annotations.Index;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name="FIXED_ASSETS_RECORD")
public class TeeFixedAssetsRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="FIXED_ASSETS_RECORD_seq_gen")
	@SequenceGenerator(name="FIXED_ASSETS_RECORD_seq_gen", sequenceName="FIXED_ASSETS_RECORD_seq")
	@Column(name="SID")
	private int sid;
	
	@ManyToOne()
	@Index(name="IDXf9b1838fb0514d0ca2126ff2c5d")
	@JoinColumn(name="DEPT_ID")
	private TeeDepartment dept;//申请部门
	
	@ManyToOne()
	@Index(name="IDX2b3b7c8fce0e4a18845909cdae6")
	@JoinColumn(name="USER_ID")
	private TeePerson user;//申请人
	
	@ManyToOne()
	@Index(name="IDX901a97e096b744fe9261f81b99f")
	@JoinColumn(name="FIXED_ASSETS_ID")
	private TeeFixedAssetsInfo fixedAssets;//固定资产
	
	@Column(name="OPT_DATE" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date optDate;//领用、返库时间
	
	@Column(name="OPT_ADDRESS",length=500)
	private String optAddress;//报废存档位置  --- 报废字段专用
	
	@Column(name="OPT_REASON",length=500)
	private String optReason;//操作原因
	
	@Lob
	@Column(name="OPT_REMARK")
	private String optRemark;//操作备注
	
	@Column(name="OPT_TYPE" ,columnDefinition="char(1) default 0")
	private String optType;//操作类型  0 - 领用   1- 返库 2-报修  3-报修返库  - 4 报废
	
	@Column(name="RUN_ID")
	private  int  runId;//流程ID
	
	@Column(name="REPAIR_UNIT")
	private String repairUnit;//维修单位
	
	@Column(name="REPAIR_USER")
	private String repairUser;//维修负责人
	
	@Column(name="TELPHONE")
	private String telphone;//联系电话
	
	@Column(name="REPAIR_COST",nullable=false)
	private double repairCost;//维修费用
	
	@Column(name="REPAIR_CONFIRM",nullable=false)
	private int repairConfirm;//维修确认

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeeDepartment getDept() {
		return dept;
	}

	public void setDept(TeeDepartment dept) {
		this.dept = dept;
	}

	public TeePerson getUser() {
		return user;
	}

	public void setUser(TeePerson user) {
		this.user = user;
	}

	public TeeFixedAssetsInfo getFixedAssets() {
		return fixedAssets;
	}

	public void setFixedAssets(TeeFixedAssetsInfo fixedAssets) {
		this.fixedAssets = fixedAssets;
	}

	public Date getOptDate() {
		return optDate;
	}

	public void setOptDate(Date optDate) {
		this.optDate = optDate;
	}

	public String getOptReason() {
		return optReason;
	}

	public void setOptReason(String optReason) {
		this.optReason = optReason;
	}

	public String getOptRemark() {
		return optRemark;
	}

	public void setOptRemark(String optRemark) {
		this.optRemark = optRemark;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public int getRunId() {
		return runId;
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}

	public String getOptAddress() {
		return optAddress;
	}

	public void setOptAddress(String optAddress) {
		this.optAddress = optAddress;
	}

	public String getRepairUnit() {
		return repairUnit;
	}

	public void setRepairUnit(String repairUnit) {
		this.repairUnit = repairUnit;
	}

	public String getRepairUser() {
		return repairUser;
	}

	public void setRepairUser(String repairUser) {
		this.repairUser = repairUser;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public double getRepairCost() {
		return repairCost;
	}

	public void setRepairCost(double repairCost) {
		this.repairCost = repairCost;
	}

	public int getRepairConfirm() {
		return repairConfirm;
	}

	public void setRepairConfirm(int repairConfirm) {
		this.repairConfirm = repairConfirm;
	}

}
