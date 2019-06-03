package com.tianee.oa.subsys.timeline.bean;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;

@Entity
@Table(name="TIMELINE")
public class TeeTimeline {
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String uuid;//自增id
	
	@Column(name="TITLE_")
	private String title;//标题
	
	@Column(name="TYPE_")
	private String type;//大事记分类     参考系统代码表sys_code中的“大事记分类”
	
	@Column(name="START_TIME")
	private Calendar startTime;//开始时间  时间部分默认为00:00:00
	
	@Column(name="END_TIME") 
	private Calendar endTime;//开始时间  时间部分默认为23:59:59
	
	@Column(name="CR_TIME")
	private Calendar crTime;//创建时间
	
	@Column(name="UPDATE_TIME")
	private Calendar updateTime;//最后修改时间
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="TIMELINE_CR_UID")
	@JoinColumn(name="CR_USER_ID")
	private TeePerson crUser;//创建人
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="TIMELINE_UPDATE_UID")
	@JoinColumn(name="UPDATE_USER_ID")
	private TeePerson updateUser;//更新人
	
	@Column(name="TAG_")
	private String tag;//标签    标签格式    /标签1/标签2/标签3/     模糊查询的话直接就可以用   like '%/标签1/%'
	
	@Lob
	@Column(name="CONTENT_")
	private String content;//大事记内容
	
	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.PERSIST},fetch=FetchType.LAZY)
	@JoinTable(name="TIMELINE_VIEW_USER",
			joinColumns={@JoinColumn(name="TIMELINE_ID")},
			inverseJoinColumns={@JoinColumn(name="USER_ID")})
	private Set<TeePerson> viewUser = new HashSet();//人员查看权限
	
	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.PERSIST},fetch=FetchType.LAZY)
	@JoinTable(name="TIMELINE_VIEW_DEPT",
			joinColumns={@JoinColumn(name="TIMELINE_ID")},
			inverseJoinColumns={@JoinColumn(name="DEPT_ID")})
	private Set<TeeDepartment> viewDept = new HashSet();//部门查看权限
	
	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.PERSIST},fetch=FetchType.LAZY)
	@JoinTable(name="TIMELINE_VIEW_ROLE",
			joinColumns={@JoinColumn(name="TIMELINE_ID")},
			inverseJoinColumns={@JoinColumn(name="ROLE_ID")})
	private Set<TeeUserRole> viewRole = new HashSet();//角色查看权限
	
	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.PERSIST},fetch=FetchType.LAZY)
	@JoinTable(name="TIMELINE_POST_USER",
			joinColumns={@JoinColumn(name="TIMELINE_ID")},
			inverseJoinColumns={@JoinColumn(name="USER_ID")})
	private Set<TeePerson> postUser = new HashSet();//人员管理权限
	
	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.PERSIST},fetch=FetchType.LAZY)
	@JoinTable(name="TIMELINE_POST_DEPT",
			joinColumns={@JoinColumn(name="TIMELINE_ID")},
			inverseJoinColumns={@JoinColumn(name="DEPT_ID")})
	private Set<TeeDepartment> postDept = new HashSet();//部门管理权限
	
	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.PERSIST},fetch=FetchType.LAZY)
	@JoinTable(name="TIMELINE_POST_ROLE",
			joinColumns={@JoinColumn(name="TIMELINE_ID")},
			inverseJoinColumns={@JoinColumn(name="ROLE_ID")})
	private Set<TeeUserRole> postRole = new HashSet();//角色管理权限

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public Calendar getCrTime() {
		return crTime;
	}

	public void setCrTime(Calendar crTime) {
		this.crTime = crTime;
	}

	public Calendar getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Calendar updateTime) {
		this.updateTime = updateTime;
	}

	public TeePerson getCrUser() {
		return crUser;
	}

	public void setCrUser(TeePerson crUser) {
		this.crUser = crUser;
	}

	public TeePerson getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(TeePerson updateUser) {
		this.updateUser = updateUser;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<TeePerson> getViewUser() {
		return viewUser;
	}

	public void setViewUser(Set<TeePerson> viewUser) {
		this.viewUser = viewUser;
	}

	public Set<TeeDepartment> getViewDept() {
		return viewDept;
	}

	public void setViewDept(Set<TeeDepartment> viewDept) {
		this.viewDept = viewDept;
	}

	public Set<TeeUserRole> getViewRole() {
		return viewRole;
	}

	public void setViewRole(Set<TeeUserRole> viewRole) {
		this.viewRole = viewRole;
	}

	public Set<TeePerson> getPostUser() {
		return postUser;
	}

	public void setPostUser(Set<TeePerson> postUser) {
		this.postUser = postUser;
	}

	public Set<TeeDepartment> getPostDept() {
		return postDept;
	}

	public void setPostDept(Set<TeeDepartment> postDept) {
		this.postDept = postDept;
	}

	public Set<TeeUserRole> getPostRole() {
		return postRole;
	}

	public void setPostRole(Set<TeeUserRole> postRole) {
		this.postRole = postRole;
	}
	
	
	
	
}
