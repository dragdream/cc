package com.tianee.oa.core.general.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

@SuppressWarnings("serial")
@Entity
@Table(name = "SHORTCUT_MENU")
public class TeeShortcutMenu {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="SHORTCUT_MENU_seq_gen")
	@SequenceGenerator(name="SHORTCUT_MENU_seq_gen", sequenceName="SHORTCUT_MENU_seq")
	@Column(name="SID")
	private int sid;//自增id
	

	@OneToOne()
	@JoinColumn(name="PERSON_ID")
	private TeePerson user;//主办人
	
	@Column(name="MENU_IDS" ,length = 1000)
	private String menuIds;

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

	public String getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}
	
	
}
