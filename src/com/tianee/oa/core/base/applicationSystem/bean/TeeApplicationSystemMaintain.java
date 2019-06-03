package com.tianee.oa.core.base.applicationSystem.bean;

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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.priv.bean.TeeMenuGroup;

/**
 * 应用系统维护
 * @author xsy
 *
 */
@Entity
@Table(name="APPLICATION_SYSTEM_MAINTAIN")
public class TeeApplicationSystemMaintain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="APPLICATION_S_M_seq_gen")
	@SequenceGenerator(name="APPLICATION_S_M_seq_gen", sequenceName="APPLICATION_S_M_SEQ")
	private int sid;//主键
	
	@Column(name="SYSTEM_NAME")
	private String systemName;//应用系统名称
	
	
	@Column(name="URL")
	private String url;//访问地址
	
	@ManyToMany(targetEntity=TeeUserRole.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable(name="APPLICATION_SYSTEM_ROLE",       
			joinColumns={@JoinColumn(name="APPLICATION_SYSTEM_SID")},       
			inverseJoinColumns={@JoinColumn(name="ROLE_UUID")}  ) 	
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	private List<TeeUserRole> roleList;//角色权限
	
	
	@ManyToMany(targetEntity=TeePerson.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable(name="APPLICATION_SYSTEM_USER",       
			joinColumns={@JoinColumn(name="APPLICATION_SYSTEM_SID")},       
			inverseJoinColumns={@JoinColumn(name="USER_UUID")}  ) 	
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	private List<TeePerson> userList;//人员权限
	
	
	
	
	@ManyToMany(targetEntity=TeeDepartment.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable(name="APPLICATION_SYSTEM_DEPT",       
			joinColumns={@JoinColumn(name="APPLICATION_SYSTEM_SID")},       
			inverseJoinColumns={@JoinColumn(name="DEPT_UUID")}  ) 	
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	private List<TeeDepartment> deptList;//部门权限

	@ManyToMany(fetch = FetchType.LAZY,
			mappedBy="appSystems" ,
			targetEntity = TeeMenuGroup.class ) 
	private List<TeeMenuGroup> menuGroup;//菜单组	


	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getSystemName() {
		return systemName;
	}




	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}




	public String getUrl() {
		return url;
	}




	public void setUrl(String url) {
		this.url = url;
	}




	public List<TeeUserRole> getRoleList() {
		return roleList;
	}




	public void setRoleList(List<TeeUserRole> roleList) {
		this.roleList = roleList;
	}




	public List<TeePerson> getUserList() {
		return userList;
	}




	public void setUserList(List<TeePerson> userList) {
		this.userList = userList;
	}




	public List<TeeDepartment> getDeptList() {
		return deptList;
	}




	public void setDeptList(List<TeeDepartment> deptList) {
		this.deptList = deptList;
	}

	public List<TeeMenuGroup> getMenuGroup() {
		return menuGroup;
	}

	public void setMenuGroup(List<TeeMenuGroup> menuGroup) {
		this.menuGroup = menuGroup;
	}
	
	
}
