package com.beidasoft.zfjd.supervise.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 监督部门表实体类
 */
@Entity
@Table(name = "TBL_BASE_SUPERVISE")
public class Supervise {
	// 主键
	@Id
    @Column(name = "ID")
	private String id;

	// 父ID
	@Column(name = "PARENT_ID")
	private String parentId;

	// 组织名称
	@Column(name = "NAME")
	private String name;

	// 组织图标
	@Column(name = "ICON")
	private String icon;

	// 组织性质
	@Column(name = "NATURE")
	private String nature;

	// 组织排序
	@Column(name = "INDEX_ORDER")
	private Integer indexOrder;

	// 删除标识（0：未删除，1：已删除）
	@Column(name = "IS_DELETE")
	private Integer isDelete;

	// 是否停用（0：未停用，1：已停用）
	@Column(name = "IS_STOP")
	private Integer isStop;

	// 创建者ID
	@Column(name = "CREATOR_ID")
	private String creatorId;

	// 创建日期
	@Column(name = "CREATE_TIME")
	private Date createTime;

	// 法定代表人
	@Column(name = "REPRESENTATIVE")
	private String representative;

	// 邮编
	@Column(name = "POST_CODE")
	private String postCode;

	// 传真
	@Column(name = "FAX")
	private String fax;

	// 联系电话
	@Column(name = "PHONE")
	private String phone;

	// 地址
	@Column(name = "ADDRESS")
	private String address;

	// 发布状态（0：未发布，1：已发布）
	@Column(name = "IS_RELEASE")
	private Integer isRelease;

	// 审核状态（0：未审核，1：已审核）
	@Column(name = "IS_EXAMINE")
	private Integer isExamine;

	// 行政区划
	@Column(name = "ADMINISTRATIVE_DIVISION")
	private String administrativeDivision;

	// 部门编号
	@Column(name = "DEPARTMENT_CODE")
	private String departmentCode;

	// 部门简称
	@Column(name = "SIMPLE_NAME")
	private String simpleName;

	// 监督部门ID
	@Column(name = "SUPERVICE_DEPARTMENT_ID")
	private String superviceDepartmentId;

	// 发布时间
	@Column(name = "RELEASE_TIME")
	private Date releaseTime;

	// 审核时间
	@Column(name = "EXAMINE_TIME")
	private Date examineTime;

	// 修改时间
	@Column(name = "UPDATE_TIME")
	private Date updateTime;

	// 发布人
	@Column(name = "RELEASE_PERSON_ID")
	private String releasePersonId;

	// 审核人
	@Column(name = "EXAMINE_PERSON_ID")
	private String examinePersonId;

	// 创建人名称
	@Column(name = "CREATOR_NAME")
	private String creatorName;

	// 是否是监督部门（1表示监督部门）
	@Column(name = "IS_SUPERVISE")
	private Integer isSupervise;

	// 取消审核时间
	@Column(name = "DIS_EXAMINE_TIME")
	private Date disExamineTime;

	// 取消发布时间
	@Column(name = "DIS_RELEASE_TIME")
	private Date disReleaseTime;

	// 修改人ID
	@Column(name = "UPDATE_PERSON_ID")
	private String updatePersonId;

	// 0市级，1二级局
	@Column(name = "TYPE")
	private Integer type;

	// 1垂管，0非垂管
	@Column(name = "IS_MANUBRIUM")
	private Integer isManubrium;

	// 删除时间
	@Column(name = "DELETE_TIME")
	private Date deleteTime;

	// 01城乡建设管理领域，02经济和市场监管领域，03民生和社会管理领域，04农村和专业管理领域，05国家垂直管理部门
	@Column(name = "FIELD")
	private String field;

	// 区分组
	@Column(name = "GROUP_AREA")
	private String groupArea;
	
	// 执法主体
	@Column(name = "ORG_SYS")
	private String orgSys;
	
	//部门层级
	@Column(name = "DEPT_LEVEL")
	private String deptLevel;
	
	//账号
	@Column(name = "USERNAME")
	private String userName;
	
	//密码
	@Column(name = "PASSWORD")
	private String password;
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Supervise(){
		
	}
	
	public Supervise(String id,String name){
		this.id=id;
		this.name = name;
	}

	public String getDeptLevel() {
		return deptLevel;
	}

	public void setDeptLevel(String deptLevel) {
		this.deptLevel = deptLevel;
	}

	public String getOrgSys() {
		return orgSys;
	}

	public void setOrgSys(String orgSys) {
		this.orgSys = orgSys;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public Integer getIndexOrder() {
		return indexOrder;
	}

	public void setIndexOrder(Integer indexOrder) {
		this.indexOrder = indexOrder;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getIsStop() {
		return isStop;
	}

	public void setIsStop(Integer isStop) {
		this.isStop = isStop;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getRepresentative() {
		return representative;
	}

	public void setRepresentative(String representative) {
		this.representative = representative;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getIsRelease() {
		return isRelease;
	}

	public void setIsRelease(Integer isRelease) {
		this.isRelease = isRelease;
	}

	public Integer getIsExamine() {
		return isExamine;
	}

	public void setIsExamine(Integer isExamine) {
		this.isExamine = isExamine;
	}

	public String getAdministrativeDivision() {
		return administrativeDivision;
	}

	public void setAdministrativeDivision(String administrativeDivision) {
		this.administrativeDivision = administrativeDivision;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public String getSuperviceDepartmentId() {
		return superviceDepartmentId;
	}

	public void setSuperviceDepartmentId(String superviceDepartmentId) {
		this.superviceDepartmentId = superviceDepartmentId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Date getExamineTime() {
		return examineTime;
	}

	public void setExamineTime(Date examineTime) {
		this.examineTime = examineTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getReleasePersonId() {
		return releasePersonId;
	}

	public void setReleasePersonId(String releasePersonId) {
		this.releasePersonId = releasePersonId;
	}

	public String getExaminePersonId() {
		return examinePersonId;
	}

	public void setExaminePersonId(String examinePersonId) {
		this.examinePersonId = examinePersonId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Integer getIsSupervise() {
		return isSupervise;
	}

	public void setIsSupervise(Integer isSupervise) {
		this.isSupervise = isSupervise;
	}

	public Date getDisExamineTime() {
		return disExamineTime;
	}

	public void setDisExamineTime(Date disExamineTime) {
		this.disExamineTime = disExamineTime;
	}

	public Date getDisReleaseTime() {
		return disReleaseTime;
	}

	public void setDisReleaseTime(Date disReleaseTime) {
		this.disReleaseTime = disReleaseTime;
	}

	public String getUpdatePersonId() {
		return updatePersonId;
	}

	public void setUpdatePersonId(String updatePersonId) {
		this.updatePersonId = updatePersonId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIsManubrium() {
		return isManubrium;
	}

	public void setIsManubrium(Integer isManubrium) {
		this.isManubrium = isManubrium;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getGroupArea() {
		return groupArea;
	}

	public void setGroupArea(String groupArea) {
		this.groupArea = groupArea;
	}

}
