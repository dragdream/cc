package com.tianee.oa.core.base.dam.bean;

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

import com.tianee.oa.core.attachment.bean.TeeAttachment;
/**
 * 档案附件表
 * @author xsy
 *
 */
@Entity
@Table(name="dam_file_attch")
public class TeeFileAttch {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO, generator="dam_file_attch_seq_gen")
	@SequenceGenerator(name="dam_file_attch_seq_gen", sequenceName="dam_file_attch_seq")
	private int sid;
	
	@Column(name="sort_no")
	private int sortNo;//顺序号
	
	@Column(name="manager")
	private String manager;//责任人
	
	@Column(name="wjz")
	private String wjz;//文件字
	
	@Column(name="title")
	private String title;//标题
	
	@Column(name="page_num")
	private int pageNum;//页数
	
	@Column(name="pub_Time")
	private Calendar pubTime;//附件生成日期
	
	@Column(name="remark")
	private String remark;//备注
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="file_id")
	private TeeFiles file;//所属档案
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="att_id")
	private TeeAttachment attch;//所属附件


	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public int getSortNo() {
		return sortNo;
	}


	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}


	public String getManager() {
		return manager;
	}


	public void setManager(String manager) {
		this.manager = manager;
	}


	public String getWjz() {
		return wjz;
	}


	public void setWjz(String wjz) {
		this.wjz = wjz;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public int getPageNum() {
		return pageNum;
	}


	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}


	public Calendar getPubTime() {
		return pubTime;
	}


	public void setPubTime(Calendar pubTime) {
		this.pubTime = pubTime;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public TeeFiles getFile() {
		return file;
	}


	public void setFile(TeeFiles file) {
		this.file = file;
	}


	public TeeAttachment getAttch() {
		return attch;
	}


	public void setAttch(TeeAttachment attch) {
		this.attch = attch;
	}
	
	
	
	
}
