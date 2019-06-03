package com.tianee.oa.core.org.bean;
import org.hibernate.annotations.Index;

import com.beidasoft.zfjd.system.bean.SysBusinessRelation;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="department")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class TeeDepartment implements Serializable,Comparable<TeeDepartment>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="department_seq_gen")
	@SequenceGenerator(name="department_seq_gen", sequenceName="department_seq")
	private int uuid;
	
	@Column(name="guid",length=32)
	private String guid;//唯一编号
	
	@Column(name="DEPT_NAME",length=100)
	private String deptName;//部门名称
	
	@Column(name="DEPT_NO",length=20)
	private int deptNo;//部门排序号(用户同一级次部门排序)
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX356e782fd5b74b248c26f70be68")
	@JoinColumn(name="DEPT_PARENT")//这是双向的 要写JoinColumn 对面要写 mappedBy
	private TeeDepartment deptParent;//上级部门(对应上级部门的UUID字段，跟节点为空)
	
	@Column(name="DEPT_PARENT_LEVEL",length=1000)
	private String deptParentLevel;//部门级别，上级部门UUID字符串,主要方便查询，删除等
	
	@OneToMany(mappedBy="deptParent",fetch=FetchType.LAZY,cascade=CascadeType.ALL)//因为这里是双向的 一对多 所以要写mappedBy
	private List<TeeDepartment> children;//子部门
	
	@Column(name="MANAGER",length=2000)
	private String manager;

	@Column(name="MANAGER2",length=2000)
	private String manager2;
	
	@Column(name="LEADER1",length=2000)
	private String leader1;
	
	
	@Column(name="LEADER2",length=2000)
	private String leader2;
	
/*	
	@ManyToMany(targetEntity=TeePerson.class,   //多对多       
	fetch = FetchType.LAZY  ) 
	@JoinTable(name="DEPARTMENT_PERSON",         
	joinColumns={@JoinColumn(name="person_manager_uuid")},       
	inverseJoinColumns={@JoinColumn(name="DEPT_MANAGER_UUID")}  ) 	
	private List<TeePerson> manager;//部门主管（选填）(多个)
	

	
	@ManyToMany(targetEntity=TeePerson.class,   //多对多       
	fetch = FetchType.LAZY  ) 
	@JoinTable(name="DEPARTMENT_PERSON",         
	joinColumns={@JoinColumn(name="person_leader1_uuid")},       
	inverseJoinColumns={@JoinColumn(name="DEPT_LEADER1_UUID")}  ) 
	private List<TeePerson> leader1;//上级主管领导（选填）
	
	@ManyToMany(targetEntity=TeePerson.class,   //多对多       
	fetch = FetchType.LAZY  ) 
	@JoinTable(name="DEPARTMENT_PERSON",         
	joinColumns={@JoinColumn(name="person_leader2_uuid")},       
	inverseJoinColumns={@JoinColumn(name="DEPT_LEADER2_UUID")}  ) 
	private List<TeePerson> leader2;
	*/
	@Column(name="DEPT_FUNC",length=1000)
	private String deptFunc;//部门职能
	
	@Column(name="TEL_NO",length=50)
	private String telNo;//电话号码

	@Column(name="FAX_NO",length=50)
	private String faxNo;//传真号码

	@Column(name="ADDRESS",length=200)
	private String address;//部门地址

	@Column(name="DEPT_FULL_ID")
	private String deptFullId;//部门路径全ID
	
	@Column(name="DEPT_FULL_NAME")
	private String deptFullName;//部门路径全称

	@Column(name="UNIQUE_ID",length=100)
	private String uniqueId;//唯一标示，做系统对接用
	
	@Column(name="DING_DEPT_ID")
	private long dingdingDeptId;//对应钉钉的部门ID
	
	@Column(name="WEIXIN_DEPT_ID")
	private long weixinDeptId;//对应微信的部门ID
	
	@Column(name="DEPT_TYPE",length=20)
	private int deptType = 1;//部门类型  1：部门   2：分支机构  3：全局部门
	
	@Column(name="SUBORDINATE_UNITS_CODE")
	private String subordinateUnitsCode; //所属单位(存储代码表CODE值)
	
	@Column(name="SUBORDINATE_UNITS")
	private String subordinateUnits; //所属单位(存储代码表VALUE值)
	
	@ManyToOne
	@JoinColumn(name = "info_rep_user_id")
	private TeePerson infoReportUser;//部门信息上报员
	
    // 系统部门与业务部门关系
    @OneToMany(mappedBy = "deptRelation", cascade = CascadeType.ALL) // 双向,  目标写@JoinColumn 
    private List<SysBusinessRelation> relations;
	
    //所属单位ID
	@ManyToOne(fetch = FetchType.LAZY  ) 
	@JoinColumn(name = "UNIT_UUID")
	private TeeDepartment unit;
	
	public TeePerson getInfoReportUser() {
		return infoReportUser;
	}

	public void setInfoReportUser(TeePerson infoReportUser) {
		this.infoReportUser = infoReportUser;
	}

	public TeeDepartment(){
		
	}
	
	public TeeDepartment(int uuid){
		this.uuid = uuid;
	}
	
	public TeeDepartment(int uuid,String deptName,String deptFullId){
		this.uuid = uuid;
		this.deptName = deptName;
		this.deptFullId = deptFullId;
	}
	
	public TeeDepartment(int uuid,String deptName,String deptFullId,int deptNo){
		this.uuid = uuid;
		this.deptName = deptName;
		this.deptFullId = deptFullId;
		this.deptNo = deptNo;
	}
	
	public TeeDepartment(int uuid,String deptName,String deptFullId,TeeDepartment deptParent){
		this.uuid = uuid;
		this.deptName = deptName;
		this.deptFullId = deptFullId;
		this.deptParent = deptParent;
	}
	
	public TeeDepartment(int uuid,String deptName,String deptFullId,TeeDepartment deptParent,int deptNo){
		this.uuid = uuid;
		this.deptName = deptName;
		this.deptFullId = deptFullId;
		this.deptParent = deptParent;
		this.deptNo = deptNo;
	}
	
	public List<SysBusinessRelation> getRelations() {
        return relations;
    }

    public void setRelations(List<SysBusinessRelation> relations) {
        this.relations = relations;
    }
	
	public int getUuid() {
		return uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public int getDeptNo() {
		return deptNo;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}
	
	public TeeDepartment getDeptParent() {
		return deptParent;
	}

	public void setDeptParent(TeeDepartment deptParent) {
		this.deptParent = deptParent;
	}

	public List<TeeDepartment> getChildren() {
		return children;
	}

	public void setChildren(List<TeeDepartment> children) {
		this.children = children;
	}
	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getManager2() {
		return manager2;
	}

	public void setManager2(String manager2) {
		this.manager2 = manager2;
	}

	public String getLeader1() {
		return leader1;
	}

	public void setLeader1(String leader1) {
		this.leader1 = leader1;
	}

	public String getLeader2() {
		return leader2;
	}

	public void setLeader2(String leader2) {
		this.leader2 = leader2;
	}

	public String getDeptFunc() {
		return deptFunc;
	}

	public void setDeptFunc(String deptFunc) {
		this.deptFunc = deptFunc;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDeptParentLevel() {
		return deptParentLevel;
	}

	public void setDeptParentLevel(String deptParentLevel) {
		this.deptParentLevel = deptParentLevel;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getDeptFullId() {
		return deptFullId;
	}

	public void setDeptFullId(String deptFullId) {
		this.deptFullId = deptFullId;
	}

	public String getDeptFullName() {
		return deptFullName;
	}

	public void setDeptFullName(String deptFullName) {
		this.deptFullName = deptFullName;
	}

	public long getDingdingDeptId() {
		return dingdingDeptId;
	}

	public void setDingdingDeptId(long dingdingDeptId) {
		this.dingdingDeptId = dingdingDeptId;
	}

	public long getWeixinDeptId() {
		return weixinDeptId;
	}

	public void setWeixinDeptId(long weixinDeptId) {
		this.weixinDeptId = weixinDeptId;
	}

	public int getDeptType() {
		return deptType;
	}

	public void setDeptType(int deptType) {
		this.deptType = deptType;
	}

	public String getSubordinateUnitsCode() {
		return subordinateUnitsCode;
	}

	public void setSubordinateUnitsCode(String subordinateUnitsCode) {
		this.subordinateUnitsCode = subordinateUnitsCode;
	}

	public String getSubordinateUnits() {
		return subordinateUnits;
	}

	public void setSubordinateUnits(String subordinateUnits) {
		this.subordinateUnits = subordinateUnits;
	}

	public TeeDepartment getUnit() {
		return unit;
	}

	public void setUnit(TeeDepartment unit) {
		this.unit = unit;
	}

	@Override
	public int compareTo(TeeDepartment arg0) {
		// TODO Auto-generated method stub
		if(this.getDeptNo()==arg0.getDeptNo()){
			return 0;
		}else if(this.getDeptNo()>arg0.getDeptNo()){
			return 1;
		}else{
			return -1;
		}
	}
	
}
