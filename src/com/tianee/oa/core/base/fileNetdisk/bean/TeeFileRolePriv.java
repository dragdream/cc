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

import com.tianee.oa.core.org.bean.TeeUserRole;

/**
 * 
 * @author syl
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "FILE_ROLE_PRIV")
public class TeeFileRolePriv {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="FILE_ROLE_PRIV_seq_gen")
	@SequenceGenerator(name="FILE_ROLE_PRIV_seq_gen", sequenceName="FILE_ROLE_PRIV_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@ManyToOne
	@Index(name="IDXe04b275f62834167aaccc896a60")
	@JoinColumn(name="FILE_ID")
	private TeeFileNetdisk fileNetdisk;
	
	
	@ManyToOne
	@Index(name="IDXea8ea72d7b5e490bb52b6804c4c")
	@JoinColumn(name="USER_ROLE_ID")
	private TeeUserRole userRole;
	
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

	public TeeUserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(TeeUserRole userRole) {
		this.userRole = userRole;
	}

	public int getPrivValue() {
		return privValue;
	}

	public void setPrivValue(int privValue) {
		this.privValue = privValue;
	}
	
}



	
