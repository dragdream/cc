package com.tianee.oa.core.base.dam.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tianee.oa.core.base.dam.bean.TeeStoreHouse;
import com.tianee.oa.core.org.bean.TeePerson;


public class TeeFilesModel{
	private int sid;
	private String orgCode;//组织机构号	
	private String qzh;//全宗号
	private String year;//年份
	private String retentionPeriod;//保管期限
	private String retentionPeriodStr;//保管期限
	
	private String jh;//件号
	private String dah;//档案号
	private String title;//标题
	private String number;//编号
	private String unit;//单位
	private String mj;//密级
	private String hj;//缓急
	private String subject;//主题词
	private String remark;//备注
	
	

	//private TeePerson createUser;//创建人
	private int createUserId;
	private String createUserName;
	

	//private Calendar createTime;//创建时间
	private String createTimeStr;
	
	private int opFlag;//档案状态：1=经办人整理   2=档案员整理   3=已加入卷盒    4=已归档
	private int viewFlag;//借阅 状态    0=未借阅   1=已借阅
	private int viewTotal;//借阅次数
	private int delFlag;//销毁标记   1=已销毁  0=未销毁
	
	

	//private TeePreArchiveType type;//预归档分类
	private int typeId;//分类id
	private String typeName;//分类名称
	
	//private TeeDamBox box;//所属卷盒
	private int boxId;//卷盒主键
	private String boxNo;//盒号
	
	
	//private TeeStoreHouse storeHouse;//所属卷库
	private  int storeHouseId;//所属卷库主键
	private String storeHouseName;//所属卷库名称
	
	//private TeePerson archiveUser;//档案管理员/归档人员
	private int archiveUserId;//归档人员主键
	private String archiveUserName;//归档人员名字

	//private Calendar archiveTime;//归档时间
	private String archiveTimeStr;//归档时间
	
	
	private int runId;//流程流水号
	
	
	
	
	public int getRunId() {
		return runId;
	}
	public void setRunId(int runId) {
		this.runId = runId;
	}
	public String getRetentionPeriodStr() {
		return retentionPeriodStr;
	}
	public void setRetentionPeriodStr(String retentionPeriodStr) {
		this.retentionPeriodStr = retentionPeriodStr;
	}
	public int getStoreHouseId() {
		return storeHouseId;
	}
	public void setStoreHouseId(int storeHouseId) {
		this.storeHouseId = storeHouseId;
	}
	public String getStoreHouseName() {
		return storeHouseName;
	}
	public void setStoreHouseName(String storeHouseName) {
		this.storeHouseName = storeHouseName;
	}
	public int getArchiveUserId() {
		return archiveUserId;
	}
	public void setArchiveUserId(int archiveUserId) {
		this.archiveUserId = archiveUserId;
	}
	
	public String getArchiveUserName() {
		return archiveUserName;
	}
	public void setArchiveUserName(String archiveUserName) {
		this.archiveUserName = archiveUserName;
	}
	public String getArchiveTimeStr() {
		return archiveTimeStr;
	}
	public void setArchiveTimeStr(String archiveTimeStr) {
		this.archiveTimeStr = archiveTimeStr;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getQzh() {
		return qzh;
	}
	public void setQzh(String qzh) {
		this.qzh = qzh;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getRetentionPeriod() {
		return retentionPeriod;
	}
	public void setRetentionPeriod(String retentionPeriod) {
		this.retentionPeriod = retentionPeriod;
	}
	public String getJh() {
		return jh;
	}
	public void setJh(String jh) {
		this.jh = jh;
	}
	public String getDah() {
		return dah;
	}
	public void setDah(String dah) {
		this.dah = dah;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getMj() {
		return mj;
	}
	public void setMj(String mj) {
		this.mj = mj;
	}
	public String getHj() {
		return hj;
	}
	public void setHj(String hj) {
		this.hj = hj;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public int getOpFlag() {
		return opFlag;
	}
	public void setOpFlag(int opFlag) {
		this.opFlag = opFlag;
	}
	public int getViewFlag() {
		return viewFlag;
	}
	public void setViewFlag(int viewFlag) {
		this.viewFlag = viewFlag;
	}
	public int getViewTotal() {
		return viewTotal;
	}
	public void setViewTotal(int viewTotal) {
		this.viewTotal = viewTotal;
	}
	public int getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getBoxId() {
		return boxId;
	}
	public void setBoxId(int boxId) {
		this.boxId = boxId;
	}
	public String getBoxNo() {
		return boxNo;
	}
	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}
	
	
	
	
}