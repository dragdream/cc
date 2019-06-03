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

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 
 * @author syl
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "FILE_USER_PRIV")
public class TeeFileUserPriv {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="FILE_USER_PRIV_seq_gen")
	@SequenceGenerator(name="FILE_USER_PRIV_seq_gen", sequenceName="FILE_USER_PRIV_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@ManyToOne
	@Index(name="IDX9eb94ba774604f7ba5d6a0b9628")
	@JoinColumn(name="FILE_ID")//这是双向的 要写JoinColumn 对面要写 mappedBy
	private TeeFileNetdisk fileNetdisk;
	
	
	@ManyToOne
	@Index(name="IDXd236c8dc128042bca05fbdeed76")
	@JoinColumn(name="USER_ID")//这是双向的 要写JoinColumn 对面要写 mappedBy
	private TeePerson user;
	
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

	public TeePerson getUser() {
		return user;
	}

	public void setUser(TeePerson user) {
		this.user = user;
	}

	public int getPrivValue() {
		return privValue;
	}

	public void setPrivValue(int privValue) {
		this.privValue = privValue;
	}
	
}



	
