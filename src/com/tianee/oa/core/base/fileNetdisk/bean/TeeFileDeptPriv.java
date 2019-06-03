package com.tianee.oa.core.base.fileNetdisk.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeeDepartment;

/**
 * 
 * @author syl
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "FILE_DEPT_PRIV")
public class TeeFileDeptPriv {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="FILE_DEPT_PRIV_seq_gen")
	@SequenceGenerator(name="FILE_DEPT_PRIV_seq_gen", sequenceName="FILE_DEPT_PRIV_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@ManyToOne
	@Index(name="IDX4e254a5ac2ea4101988d21ec2ea")
	@JoinColumn(name="FILE_ID")
	private TeeFileNetdisk fileNetdisk;
	
	@ManyToOne
	@Index(name="IDXd9edf975f5de416db7148fa5cb8")
	@JoinColumn(name="DEPT_ID")
	private TeeDepartment dept;
	
	@Column(name="PRIV_VALUE")
	private int privValue;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}
	
	public TeeFileNetdisk getFileNetdisk() {
		return fileNetdisk;
	}

	public void setFileNetdisk(TeeFileNetdisk fileNetdisk) {
		this.fileNetdisk = fileNetdisk;
	}

	public TeeDepartment getDept() {
		return dept;
	}

	public void setDept(TeeDepartment dept) {
		this.dept = dept;
	}

	public int getPrivValue() {
		return privValue;
	}

	public void setPrivValue(int privValue) {
		this.privValue = privValue;
	}
	
}



	
