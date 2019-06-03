package com.tianee.oa.core.base.email.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;

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

@Entity
@Table(name = "MAIL_COLOR")
public class TeeMailColor implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="MAIL_COLOR_seq_gen")
	@SequenceGenerator(name="MAIL_COLOR_seq_gen", sequenceName="MAIL_COLOR_seq")
	private int sid;//自增id
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX332fa9ca4d574500912ec490d8e")
	@JoinColumn(name = "COLOR_USER")
	private TeePerson user;
	
	@Column(name = "COLOR_VALUE")
	private String colorValue;//颜色值
	
	@Column(name = "MODULAR_NAME")
	private String modularName;//模块名

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeePerson getUser() {
		return user;
	}

	public void setUser(TeePerson user) {
		this.user = user;
	}

	public String getColorValue() {
		return colorValue;
	}

	public void setColorValue(String colorValue) {
		this.colorValue = colorValue;
	}

	public String getModularName() {
		return modularName;
	}

	public void setModularName(String modularName) {
		this.modularName = modularName;
	}
	

}
