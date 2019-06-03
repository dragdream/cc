package com.tianee.oa.core.priv.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="MENU_BUTTON")
public class TeeMenuButton implements Serializable {

	private static final long serialVersionUID = 5453927398478475867L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="MENU_BUTTON_seq_gen")
	@SequenceGenerator(name="MENU_BUTTON_seq_gen", sequenceName="MENU_BUTTON_seq")
	private int id;
	
	@Column(name="MENU_ID")
	private Integer menuId; 
	
	@Column(name="BUTTON_NO",length=30)
	private String buttonNo;
	
	@Column(name="BUTTON_NAME",length=20)
	private String buttonName;
	
	@Column(name="BUTTON_PROP",length=2)
	private String buttonProp;

	@Column(name="BUTTON_URL",length=200)
	private String buttonUrl;

	@Column(name="SORT_NO")
	private Integer sortNo;
	
	@Column(name="REMARK",length=200)
	private String remark;	
	
	@ManyToMany(fetch = FetchType.LAZY,
			mappedBy="menuButtons" ,
			targetEntity = TeeMenuGroup.class ) 
	private List<TeeMenuGroup> menuGroup;//菜单组	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getButtonNo() {
		return buttonNo;
	}

	public void setButtonNo(String buttonNo) {
		this.buttonNo = buttonNo;
	}

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public String getButtonProp() {
		return buttonProp;
	}

	public void setButtonProp(String buttonProp) {
		this.buttonProp = buttonProp;
	}

	public String getButtonUrl() {
		return buttonUrl;
	}

	public void setButtonUrl(String buttonUrl) {
		this.buttonUrl = buttonUrl;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<TeeMenuGroup> getMenuGroup() {
		return menuGroup;
	}

	public void setMenuGroup(List<TeeMenuGroup> menuGroup) {
		this.menuGroup = menuGroup;
	}
	
}
