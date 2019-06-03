package com.beidasoft.zfjd.subject.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_BASE_SUBJECT")
public class Subject {
    // 主键
    @Id
    @Column(name = "ID")
    private String id;

    // 上级主体
    @Column(name = "PARENT_ID")
    private String parentId;

    // 主体名称
    @Column(name = "SUB_NAME")
    private String subName;

    // 主体性质
    @Column(name = "NATURE")
    private String nature;

    // 主体层级
    @Column(name = "SUB_LEVEL")
    private String subLevel;

    // 0未删除，1已删除
    @Column(name = "IS_DELETE")
    private Integer isDelete;

    // 创建者
    @Column(name = "CREATE_ID")
    private String createId;

    // 创建时间
    @Column(name = "CREATE_TIME")
    private Date createTime;

    // 审核状态
    @Column(name = "EXAMINE")
    private Integer examine;

    // 主体编号
    @Column(name = "CODE")
    private String code;

    // 所属地区
    @Column(name = "AREA")
    private String area;

    // 所属部门编号
    @Column(name = "DEPARTMENT_CODE")
    private String departmentCode;

    // 是否是委托组织（0主体，1委托组织）
    @Column(name = "IS_DEPUTE")
    private Integer isDepute;

    // 修改时间
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    // 修改人ID
    @Column(name = "UPDATE_ID")
    private String updateId;

    // 审核时间
    @Column(name = "EXAMINE_TIME")
    private Date examineTime;

    // 审核人ID
    @Column(name = "EXAMINE_ID")
    private String examineId;

    // 取消审核时间
    @Column(name = "DIS_EXAMINE_TIME")
    private Date disExamineTime;

    // 机构代码
    @Column(name = "ORGANIZATION_CODE")
    private String organizationCode;

    // 法定代表人
    @Column(name = "REPRESENTATIVE")
    private String representative;

    // 性质（01行政机关、02事业单位）（单选）
    @Column(name = "ENTRUST_NATURE")
    private String entrustNature;

    // 联系电话
    @Column(name = "TELEPHONE")
    private String telephone;

    // 邮编
    @Column(name = "POST_CODE")
    private String postCode;

    // 传真
    @Column(name = "FAX")
    private String fax;

    // 地址
    @Column(name = "ADDRESS")
    private String address;

    // 删除时间
    @Column(name = "DELETE_TIME")
    private Date deleteTime;

    // 01城乡建设管理领域，02经济和市场监管领域，03民生和社会管理领域，04农村和专业管理领域，05国家垂直管理部门
    @Column(name = "FIELD")
    private String field;

    // 区分组
    @Column(name = "GROUP_AREA")
    private String groupArea;

    //职权类别
    @Column(name = "SUBJECT_POWER")
    private String subjectPower;
    
    //执法系统
    @Column(name = "ORG_SYS")
    private String orgSys;
    
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

	public String getOrgSys() {
		return orgSys;
	}

	public void setOrgSys(String orgSys) {
		this.orgSys = orgSys;
	}

	public Subject(){
		
	}
	
	public Subject(String id,String subName){
		this.id=id;
		this.subName = subName;
	}
    
    public String getSubjectPower() {
		return subjectPower;
	}

	public void setSubjectPower(String subjectPower) {
		this.subjectPower = subjectPower;
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

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getSubLevel() {
        return subLevel;
    }

    public void setSubLevel(String subLevel) {
        this.subLevel = subLevel;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getExamine() {
        return examine;
    }

    public void setExamine(Integer examine) {
        this.examine = examine;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public Integer getIsDepute() {
        return isDepute;
    }

    public void setIsDepute(Integer isDepute) {
        this.isDepute = isDepute;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public Date getExamineTime() {
        return examineTime;
    }

    public void setExamineTime(Date examineTime) {
        this.examineTime = examineTime;
    }

    public String getExamineId() {
        return examineId;
    }

    public void setExamineId(String examineId) {
        this.examineId = examineId;
    }

    public Date getDisExamineTime() {
        return disExamineTime;
    }

    public void setDisExamineTime(Date disExamineTime) {
        this.disExamineTime = disExamineTime;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getRepresentative() {
        return representative;
    }

    public void setRepresentative(String representative) {
        this.representative = representative;
    }

    public String getEntrustNature() {
        return entrustNature;
    }

    public void setEntrustNature(String entrustNature) {
        this.entrustNature = entrustNature;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
