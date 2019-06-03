package com.tianee.oa.core.base.dam.bean;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 卷盒
 * @author xsy
 *
 */
@Entity
@Table(name="dam_box")
public class TeeDamBox implements Serializable{
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO, generator="dam_box_seq_gen")
	@SequenceGenerator(name="dam_box_seq_gen", sequenceName="dam_box_seq")
	private int sid;
	
	
	@Column(name="box_no")
	private String boxNo;//盒号
	
	
	@Column(name="year")
	private String year;//年份
	
	
	@Column(name="retention_period")
	private String retentionPeriod;//保管期限
	
	
	@Column(name="mj")
	private String mj;//密级
	
	
	@Column(name="REMARK")
	private String remark;//备注
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="create_user")
	private TeePerson createUser;//创建人
	
	
	@Column(name="create_time")
	private Calendar createTime;//创建时间
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="store_house_sid")
	private TeeStoreHouse storeHouse;//所属卷库
	
	@Column(name="archive_time")
	private Calendar archiveTime;//归档时间
	
	
	@Column(name="flag")
	private int flag;//归档状态  0未归档   1已归档


	@Column(name="file_num")
	private int fileNum;//文件数
	
	
	
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


	public TeePerson getCreateUser() {
		return createUser;
	}


	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}


	public Calendar getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}


	public TeeStoreHouse getStoreHouse() {
		return storeHouse;
	}


	public void setStoreHouse(TeeStoreHouse storeHouse) {
		this.storeHouse = storeHouse;
	}


	public Calendar getArchiveTime() {
		return archiveTime;
	}


	public void setArchiveTime(Calendar archiveTime) {
		this.archiveTime = archiveTime;
	}


	public int getFlag() {
		return flag;
	}


	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
	
}
