package com.tianee.oa.core.base.dam.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

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

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;

@Entity
@Table(name="dam_store_house")
public class TeeStoreHouse implements Serializable{
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO, generator="dam_store_house_seq_gen")
	@SequenceGenerator(name="dam_store_house_seq_gen", sequenceName="dam_store_house_seq")
	private int sid;
	
	
	
	@Column(name="IS_DOC_DIR")
	private int isDocDir;//是否是系统内置归档文件夹   1=是  0=否
	
	
	@Column(name="ORDER_NUM")
	private int orderNum;//排序号
	
	@Column(name="ROOM_CODE")
	private String roomCode;//卷库号
	
	
	@Column(name="ROOM_NAME")
	private String roomName;//卷库名称
	
/*	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="BORROW_RAND")
	@Index(name="STORE_HOUSE_BORROW_RAND_INDEX")
	public Set<TeeDepartment> borrowRand=new HashSet<TeeDepartment>(0);//借阅范围
*/	
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="dam_store_house_user_priv",joinColumns={@JoinColumn(name="DAM_STORE_HOUSE_SID")},       
			inverseJoinColumns={@JoinColumn(name="USER_UUID")})
	private Set<TeePerson> userPriv=new HashSet<TeePerson>(0);//所属人员  
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="dam_store_house_dept_priv",joinColumns={@JoinColumn(name="DAM_STORE_HOUSE_SID")},       
			inverseJoinColumns={@JoinColumn(name="DEPT_UUID")})
	private Set<TeeDepartment> deptPriv=new HashSet<TeeDepartment>(0);//所属部门 
	
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="dam_store_house_role_priv",joinColumns={@JoinColumn(name="DAM_STORE_HOUSE_SID")},       
			inverseJoinColumns={@JoinColumn(name="ROLE_UUID")})
	private Set<TeeUserRole> rolePriv=new HashSet<TeeUserRole>(0);//所属角色
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDXab73c54a7c6a40569fed6ea3993")
	@JoinColumn(name="create_user")
	private TeePerson createUser;//创建人
	
	
	@Column(name="create_time")
	private Calendar createTime;//添加时间
	
	
	@Column(name="REMARK",length=2000)
	public String remark;//备注
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parent_id")
	private TeeStoreHouse parent;//父卷库
	

	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDXab73c54a7c6a40569fed6ea3993")
	@JoinColumn(name="borrow_manager")
	private TeePerson borrowManager;//借閲管理员 
	
	
	
	
	public int getIsDocDir() {
		return isDocDir;
	}
	public void setIsDocDir(int isDocDir) {
		this.isDocDir = isDocDir;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public TeePerson getBorrowManager() {
		return borrowManager;
	}
	public void setBorrowManager(TeePerson borrowManager) {
		this.borrowManager = borrowManager;
	}
	public int getSid() {
		return sid;
	}
	public Set<TeePerson> getUserPriv() {
		return userPriv;
	}
	public void setUserPriv(Set<TeePerson> userPriv) {
		this.userPriv = userPriv;
	}
	public Set<TeeDepartment> getDeptPriv() {
		return deptPriv;
	}
	public void setDeptPriv(Set<TeeDepartment> deptPriv) {
		this.deptPriv = deptPriv;
	}
	public Set<TeeUserRole> getRolePriv() {
		return rolePriv;
	}
	public void setRolePriv(Set<TeeUserRole> rolePriv) {
		this.rolePriv = rolePriv;
	}
	public TeePerson getCreateUser() {
		return createUser;
	}
	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}
	public Calendar getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getRoomCode() {
		return roomCode;
	}
	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public TeeStoreHouse getParent() {
		return parent;
	}
	public void setParent(TeeStoreHouse parent) {
		this.parent = parent;
	}
	
	
	
}
