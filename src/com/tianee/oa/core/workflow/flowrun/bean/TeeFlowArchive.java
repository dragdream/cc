package com.tianee.oa.core.workflow.flowrun.bean;

import java.util.Date;

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

import com.tianee.oa.subsys.bisengin.bean.BisDataSource;

@Entity
@Table(name="flow_archive")
public class TeeFlowArchive {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="flow_archive_seq_gen")
	@SequenceGenerator(name="flow_archive_seq_gen", sequenceName="flow_archive_seq")
	private int sid;
	
	@Column(name="archive_desc")
	private String archiveDesc;//归档描述
	
	@Column(name="cr_time")
	private Date crTime;//创建时间
	
	/*@Column(name="ds_id")
	private int dataSourceId;//归档数据源id
    */	
	
/*	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ds_id")
	private BisDataSource dataSource;//关联的数据源
*/	
	
	@Column(name="version")
	private String version;//归档版本
	
	
	@Column(name="status")
	private int status;//1.归档成功！  0 归档失败
	
	@Column(name="archive_date")
	private Date archiveDate;//归档日期范围
	
    
	@Column(name="delete_status")
	private int deleteStatus;//1.已删除！  0 未删除！

	
	
	public int getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(int deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}



	public String getArchiveDesc() {
		return archiveDesc;
	}

	public void setArchiveDesc(String archiveDesc) {
		this.archiveDesc = archiveDesc;
	}

	public Date getCrTime() {
		return crTime;
	}

	public void setCrTime(Date crTime) {
		this.crTime = crTime;
	}

	

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getArchiveDate() {
		return archiveDate;
	}

	public void setArchiveDate(Date archiveDate) {
		this.archiveDate = archiveDate;
	}

	
}
