package com.tianee.oa.core.priv.bean;
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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.base.applicationSystem.bean.TeeApplicationSystemMaintain;
import com.tianee.oa.core.org.bean.TeeDepartment;

/*import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;*/

@Entity
@Table(name="MENU_GROUP")
/*@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)*/
public class TeeMenuGroup implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="MENU_GROUP_seq_gen")
	@SequenceGenerator(name="MENU_GROUP_seq_gen", sequenceName="MENU_GROUP_seq")
	private int uuid;
	
	@Column(name="MENU_GROUP_NAME",length=200)
	private String menuGroupName;//菜单权限名称 ，MENU_ROLE_NAME 

	@Column(name="MENU_GROUP_NO",length=20)
	private int menuGroupNo;//排序号
	
	@Column(name="MENU_GROUP_TYPE",length=10)
	private String menuGroupType;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DEPT_UUID")
	private TeeDepartment dept;

	@ManyToMany(targetEntity=TeeDepartment.class,          
			fetch = FetchType.LAZY  ) 
	@JoinTable(  
	name="menu_group_dept",
	joinColumns={@JoinColumn(name="GROUP_UUID")},       
	inverseJoinColumns={@JoinColumn(name="DEPT_UUID")}  ) 
	private List<TeeDepartment> depts;	
		
	@ManyToMany(targetEntity=TeeSysMenu.class,          
			fetch = FetchType.LAZY  ) 
	@JoinTable(      
	joinColumns={@JoinColumn(name="GROUP_UUID")},       
	inverseJoinColumns={@JoinColumn(name="SYS_MENU_UUID")}  ) 
	private List<TeeSysMenu> sysMenus;//菜单List
	
	@ManyToMany(targetEntity=TeeMenuButton.class,          
			fetch = FetchType.LAZY  ) 
	@JoinTable(      
	joinColumns={@JoinColumn(name="GROUP_UUID")},       
	inverseJoinColumns={@JoinColumn(name="BUTTON_ID")}  ) 
	private List<TeeMenuButton> menuButtons;//按钮List	
		
	@ManyToMany(targetEntity=TeeApplicationSystemMaintain.class,          
			fetch = FetchType.LAZY  ) 
	@JoinTable(
	name="menu_group_app_system",
	joinColumns={@JoinColumn(name="GROUP_UUID")},       
	inverseJoinColumns={@JoinColumn(name="SYSTEM_ID")}  ) 
	private List<TeeApplicationSystemMaintain> appSystems;//系统List
	
	
	public int getUuid() {
		return uuid;
	}

	public List<TeeSysMenu> getSysMenus() {
		return sysMenus;
	}

	public void setSysMenus(List<TeeSysMenu> sysMenus) {
		this.sysMenus = sysMenus;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	public String getMenuGroupName() {
		return menuGroupName;
	}

	public void setMenuGroupName(String menuGroupName) {
		this.menuGroupName = menuGroupName;
	}

	public int getMenuGroupNo() {
		return menuGroupNo;
	}

	public void setMenuGroupNo(int menuGroupNo) {
		this.menuGroupNo = menuGroupNo;
	}

	public TeeDepartment getDept() {
		return dept;
	}

	public void setDept(TeeDepartment dept) {
		this.dept = dept;
	}

	public String getMenuGroupType() {
		return menuGroupType;
	}

	public void setMenuGroupType(String menuGroupType) {
		this.menuGroupType = menuGroupType;
	}

	public List<TeeMenuButton> getMenuButtons() {
		return menuButtons;
	}

	public void setMenuButtons(List<TeeMenuButton> menuButtons) {
		this.menuButtons = menuButtons;
	}

	public List<TeeDepartment> getDepts() {
		return depts;
	}

	public void setDepts(List<TeeDepartment> depts) {
		this.depts = depts;
	}

	public List<TeeApplicationSystemMaintain> getAppSystems() {
		return appSystems;
	}

	public void setAppSystems(List<TeeApplicationSystemMaintain> appSystems) {
		this.appSystems = appSystems;
	}
	
}
