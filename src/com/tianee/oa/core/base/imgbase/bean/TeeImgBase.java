package com.tianee.oa.core.base.imgbase.bean;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;

@Entity
@Table(name="IMG_BASE")
public class TeeImgBase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="IMGBASE_seq_gen")
	@SequenceGenerator(name="IMGBASE_seq_gen", sequenceName="IMGBASE_seq")
	@Column(name = "SID")
	private int sid;
	
	@Column(name = "IMG_DIR", nullable = true, length = 200)
	private String imgDir;
	
	@Column(name = "IMG_DIRNAME", nullable = true, length = 200)
	private String imgDirName;
	
	@Column(name = "CREATE_TIME", nullable = true, length = 200)
	private Calendar createTime;
	
	@ManyToMany(targetEntity=TeeDepartment.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="IMGBASE_DEPT_PRIV",        
			joinColumns={@JoinColumn(name="IMGBASE_ID")},       
			inverseJoinColumns={@JoinColumn(name="DEPT_UUID")}  ) 	
	@Index(name="IMGBASE_DEPT_PRIV_INDEX")
	private List<TeeDepartment> postDept;//发布权限 -部门
	
	
	@ManyToMany(targetEntity=TeePerson.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="IMGBASE_PERSON_PRIV",        
			joinColumns={@JoinColumn(name="IMGBASE_ID")},       
			inverseJoinColumns={@JoinColumn(name="USER_UUID")}  ) 	
	@Index(name="IMGBASE_PERSON_PRIV_INDEX")
	private List<TeePerson> postUser;//发布权限--人员
	
	
	@ManyToMany(targetEntity=TeeUserRole.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="IMGBASE_USER_ROLE_PRIV",        
			joinColumns={@JoinColumn(name="IMGBASE_ID")},       
			inverseJoinColumns={@JoinColumn(name="ROLE_UUID")}  ) 	
	@Index(name="IMGBASE_USER_ROLE_PRIV_INDEX")
	private List<TeeUserRole> postUserRole;//发布权限--角色
	
	@Lob
	@Column(name = "UPLOAD_DEPT_IDS", nullable = true)
	private String uploadDeptIds;
	
	@Lob
	@Column(name = "UPLOAD_DEPT_NAMES", nullable = true)
	private String uploadDeptNames;
	
	@Lob
	@Column(name = "UPLOAD_USER_IDS", nullable = true)
	private String uploadUserIds;
	
	@Lob
	@Column(name = "UPLOAD_USER_NAMES", nullable = true)
	private String uploadUserNames;
	
	@Lob
	@Column(name = "UPLOAD_ROLE_IDS", nullable = true)
	private String uploadRoleIds;
	
	@Lob
	@Column(name = "UPLOAD_ROLE_NAMES", nullable = true)
	private String uploadRoleNames;
	
	@Lob
	@Column(name = "MANAGER_DEPT_IDS", nullable = true)
	private String managerDeptIds;
	
	@Lob
	@Column(name = "MANAGER_DEPT_NAMES", nullable = true)
	private String managerDeptNames;
	
	@Lob
	@Column(name = "MANAGER_USER_IDS", nullable = true)
	private String managerUserIds;
	
	@Lob
	@Column(name = "MANAGER_USER_NAMES", nullable = true)
	private String managerUserNames;
	
	@Lob
	@Column(name = "MANAGER_ROLE_IDS", nullable = true)
	private String managerRoleIds;
	
	@Lob
	@Column(name = "MANAGER_ROLE_NAMES", nullable = true)
	private String managerRoleNames;

	@Column(name = "ALL_PRIV")
	private int allPriv=0;
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getImgDir() {
		return imgDir;
	}

	public void setImgDir(String imgDir) {
		this.imgDir = imgDir;
	}

	public String getImgDirName() {
		return imgDirName;
	}

	public void setImgDirName(String imgDirName) {
		this.imgDirName = imgDirName;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public List<TeeDepartment> getPostDept() {
		return postDept;
	}

	public void setPostDept(List<TeeDepartment> postDept) {
		this.postDept = postDept;
	}

	public List<TeePerson> getPostUser() {
		return postUser;
	}

	public void setPostUser(List<TeePerson> postUser) {
		this.postUser = postUser;
	}

	public List<TeeUserRole> getPostUserRole() {
		return postUserRole;
	}

	public void setPostUserRole(List<TeeUserRole> postUserRole) {
		this.postUserRole = postUserRole;
	}

	public String getUploadDeptIds() {
		return uploadDeptIds;
	}

	public void setUploadDeptIds(String uploadDeptIds) {
		this.uploadDeptIds = uploadDeptIds;
	}

	public String getUploadDeptNames() {
		return uploadDeptNames;
	}

	public void setUploadDeptNames(String uploadDeptNames) {
		this.uploadDeptNames = uploadDeptNames;
	}

	public String getUploadUserIds() {
		return uploadUserIds;
	}

	public void setUploadUserIds(String uploadUserIds) {
		this.uploadUserIds = uploadUserIds;
	}

	public String getUploadUserNames() {
		return uploadUserNames;
	}

	public void setUploadUserNames(String uploadUserNames) {
		this.uploadUserNames = uploadUserNames;
	}

	public String getUploadRoleIds() {
		return uploadRoleIds;
	}

	public void setUploadRoleIds(String uploadRoleIds) {
		this.uploadRoleIds = uploadRoleIds;
	}

	public String getUploadRoleNames() {
		return uploadRoleNames;
	}

	public void setUploadRoleNames(String uploadRoleNames) {
		this.uploadRoleNames = uploadRoleNames;
	}

	public String getManagerDeptIds() {
		return managerDeptIds;
	}

	public void setManagerDeptIds(String managerDeptIds) {
		this.managerDeptIds = managerDeptIds;
	}

	public String getManagerDeptNames() {
		return managerDeptNames;
	}

	public void setManagerDeptNames(String managerDeptNames) {
		this.managerDeptNames = managerDeptNames;
	}

	public String getManagerUserIds() {
		return managerUserIds;
	}

	public void setManagerUserIds(String managerUserIds) {
		this.managerUserIds = managerUserIds;
	}

	public String getManagerUserNames() {
		return managerUserNames;
	}

	public void setManagerUserNames(String managerUserNames) {
		this.managerUserNames = managerUserNames;
	}

	public String getManagerRoleIds() {
		return managerRoleIds;
	}

	public void setManagerRoleIds(String managerRoleIds) {
		this.managerRoleIds = managerRoleIds;
	}

	public String getManagerRoleNames() {
		return managerRoleNames;
	}

	public void setManagerRoleNames(String managerRoleNames) {
		this.managerRoleNames = managerRoleNames;
	}

	public int getAllPriv() {
		return allPriv;
	}

	public void setAllPriv(int allPriv) {
		this.allPriv = allPriv;
	}
	
}
