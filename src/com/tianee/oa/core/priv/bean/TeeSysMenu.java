package com.tianee.oa.core.priv.bean;
import org.hibernate.annotations.Index;

import com.tianee.oa.core.base.applicationSystem.bean.TeeApplicationSystemMaintain;
import com.tianee.oa.core.org.bean.TeeUserRole;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name="SYS_MENU")
/*@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)*/
public class TeeSysMenu implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="SYS_MENU_seq_gen")
	@SequenceGenerator(name="SYS_MENU_seq_gen", sequenceName="SYS_MENU_seq")
	private int uuid;
	
	@Column(name="MENU_ID",length=32)
	private String menuId;// MENU_ID 菜单编号例：001002003004
	@Column(length=50)
	private String menuName;// varchar(100)功能名称
	@Column(length=200)
	private String menuCode;// 功能指向路径
	@Column(length=200)
	private String icon;// 图片路径
	@Column(length=200)
	private String openFlag;// 打开状态
	@Column(length=200)
	private String menuResPrivNo;//菜单资源权限编号
	@Column(name="MENU_MODULE_NO",length=100)
	private String menuModuleNo;//MENU_MODULE_NO  模块编码
	
	 
	 
	@ManyToMany(fetch = FetchType.LAZY,
			mappedBy="sysMenus" ,
			targetEntity = TeeMenuGroup.class ) 
	private List<TeeMenuGroup> menuGroup;//菜单组
	
	
	
	@ManyToOne(fetch=FetchType.LAZY)  //EAGER
	@JoinColumn(name="SYS_ID")
	private TeeApplicationSystemMaintain sys;//所属应用系统
	

	public List<TeeMenuGroup> getMenuGroup() {
		return menuGroup;
	}

	public void setMenuGroup(List<TeeMenuGroup> menuGroup) {
		this.menuGroup = menuGroup;
	}

	public int getUuid() {
		return uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuCode() {
		if(menuCode==null){
			return "";
		}
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getOpenFlag() {
		return openFlag;
	}

	public void setOpenFlag(String openFlag) {
		this.openFlag = openFlag;
	}

	public String getMenuResPrivNo() {
		return menuResPrivNo;
	}

	public void setMenuResPrivNo(String menuResPrivNo) {
		this.menuResPrivNo = menuResPrivNo;
	}

	public String getMenuModuleNo() {
		return menuModuleNo;
	}

	public void setMenuModuleNo(String menuModuleNo) {
		this.menuModuleNo = menuModuleNo;
	}

	public TeeApplicationSystemMaintain getSys() {
		return sys;
	}

	public void setSys(TeeApplicationSystemMaintain sys) {
		this.sys = sys;
	}



	
}
