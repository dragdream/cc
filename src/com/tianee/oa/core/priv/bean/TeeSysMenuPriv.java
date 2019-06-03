package com.tianee.oa.core.priv.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
@Entity
@Table(name="SYS_MENU_PRIV")
public class TeeSysMenuPriv implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="SYS_MENU_PRIV_seq_gen")
	@SequenceGenerator(name="SYS_MENU_PRIV_seq_gen", sequenceName="SYS_MENU_PRIV_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@Column(name="PRIV_URL")
	private String privUrl;//PRIV_URL 页面路径（jsp/action）
	
	@Column(name="PRIV_FLAG" ,columnDefinition="char(1) default 0")
	private String privFlag;//PRIV_FLAG  权限是否校验0-不校验1-校验

	
	@Column(name="PRIV_NAME")
	private String privName;//PRIV_NAME 权限名称（描述
	

	@ManyToMany(targetEntity=TeeSysMenu.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="SYS_MENU_MENU_PRIV",        
			joinColumns={@JoinColumn(name="PRIV_ID")},       
			inverseJoinColumns={@JoinColumn(name="MENU_ID")}  ) 	
	@Index(name="SYS_MENU_MENU_PRIV_INDEX")
	private List<TeeSysMenu> menuPriv;//菜单权限中间表


	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public String getPrivUrl() {
		return privUrl;
	}


	public void setPrivUrl(String privUrl) {
		this.privUrl = privUrl;
	}


	public String getPrivFlag() {
		return privFlag;
	}


	public void setPrivFlag(String privFlag) {
		this.privFlag = privFlag;
	}


	public String getPrivName() {
		return privName;
	}


	public void setPrivName(String privName) {
		this.privName = privName;
	}


	public List<TeeSysMenu> getMenuPriv() {
		return menuPriv;
	}


	public void setMenuPriv(List<TeeSysMenu> menuPriv) {
		this.menuPriv = menuPriv;
	}
	
	
	
}
