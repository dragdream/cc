package com.tianee.oa.core.base.dam.model;



public class TeeDamBoxModel{
	
	private int sid;
	private String boxNo;//盒号
	private String year;//年份
	private String retentionPeriod;//保管期限
	private String retentionPeriodStr;//保管期限
	
	private String mj;//密级
	private String remark;//备注
	
	private int createUserId;//创建人主键
	private String createUserName;//创建人名称
	
	private String createTimeStr;//创建时间
	
	private int storeHouseId;//卷库主键
	private String storeHouseName;//卷库名称
	
	private  String archiveTimeStr;//归档时间
	
	
	private int flag;//归档状态  0未归档   1已归档

    private int fileNum;//文件数
	
    
    
    
	public String getRetentionPeriodStr() {
		return retentionPeriodStr;
	}


	public void setRetentionPeriodStr(String retentionPeriodStr) {
		this.retentionPeriodStr = retentionPeriodStr;
	}


	public int getFileNum() {
		return fileNum;
	}


	public void setFileNum(int fileNum) {
		this.fileNum = fileNum;
	}


	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	

	public String getBoxNo() {
		return boxNo;
	}


	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
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


	public String getMj() {
		return mj;
	}


	public void setMj(String mj) {
		this.mj = mj;
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


	public String getArchiveTimeStr() {
		return archiveTimeStr;
	}


	public void setArchiveTimeStr(String archiveTimeStr) {
		this.archiveTimeStr = archiveTimeStr;
	}


	public int getFlag() {
		return flag;
	}


	public void setFlag(int flag) {
		this.flag = flag;
	}

	
}