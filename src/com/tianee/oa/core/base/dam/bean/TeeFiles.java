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
 * 档案信息表
 * @author xsy
 *
 */
@Entity
@Table(name="dam_files")
public class TeeFiles implements Serializable{
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO, generator="dam_files_seq_gen")
	@SequenceGenerator(name="dam_files_seq_gen", sequenceName="dam_files_seq")
	private int sid;
	
	@Column(name="ORG_CODE")
	private String orgCode;//组织机构号	
	
	
	@Column(name="qzh")
	private String qzh;//全宗号
	
	
	@Column(name="year")
	private String year;//年份
	
	
	@Column(name="retention_period")
	private String retentionPeriod;//保管期限
	
	@Column(name="jh")
	private String jh;//件号
	
	@Column(name="dah")
	private String dah;//档案号
	
	@Column(name="title")
	private String title;//标题
	
	@Column(name="number_")
	private String number;//编号
	
	@Column(name="unit")
	private String unit;//单位
	
	@Column(name="mj")
	private String mj;//密级
	
	@Column(name="hj")
	private String hj;//缓急
	
	@Column(name="subject")
	private String subject;//主题词
	
	
	@Column(name="remark")
	private String remark;//备注
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="create_user")
	private TeePerson createUser;//创建人
	
	
	@Column(name="create_time")
	private Calendar createTime;//创建时间
	
	
	@Column(name="op_flag")
	private int opFlag;//档案状态：1=经办人整理   2=档案员整理   3=已加入卷盒    4=已归档
	
	
	@Column(name="view_flag")
	private int viewFlag;//借阅 状态    0=未借阅   1=已借阅
	
	
	@Column(name="view_total")
	private int viewTotal;//借阅次数
	
	
	@Column(name="del_flag")
	private int delFlag;//销毁标记   1=已销毁  0=未销毁
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="pre_archive_type_id")
	private TeePreArchiveType type;//预归档分类
	
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="box_id")
	private TeeDamBox box;//所属卷盒


	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="store_house_id")
	private TeeStoreHouse storeHouse;//所属卷库
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="archive_user")
	private TeePerson archiveUser;//档案管理员/归档人员
	

	@Column(name="archive_time")
	private Calendar archiveTime;//归档时间
	
	@Column(name="RUN_ID")
	private int runId;//流程流水号
	
	
	
	
	
	
	public int getRunId() {
		return runId;
	}



	public void setRunId(int runId) {
		this.runId = runId;
	}



	public TeeStoreHouse getStoreHouse() {
		return storeHouse;
	}



	public void setStoreHouse(TeeStoreHouse storeHouse) {
		this.storeHouse = storeHouse;
	}



	public TeePerson getArchiveUser() {
		return archiveUser;
	}



	public void setArchiveUser(TeePerson archiveUser) {
		this.archiveUser = archiveUser;
	}



	public Calendar getArchiveTime() {
		return archiveTime;
	}



	public void setArchiveTime(Calendar archiveTime) {
		this.archiveTime = archiveTime;
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



	public TeePreArchiveType getType() {
		return type;
	}



	public void setType(TeePreArchiveType type) {
		this.type = type;
	}



	public TeeDamBox getBox() {
		return box;
	}



	public void setBox(TeeDamBox box) {
		this.box = box;
	}
	
	
	
	
	
	
	
}
