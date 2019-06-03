package com.beidasoft.zfjd.subject.model;

import java.util.Date;

public class SubjectModel {
    // 主键
    private String id;

    // 上级主体
    private String parentId;
    private String parentName;

    // 主体名称
    private String subName;

    // 主体性质
    private String nature;

    // 主体层级
    private String subLevel;

    // 0未删除，1已删除
    private Integer isDelete;

    // 创建者
    private String createId;

    // 创建时间
    private Date createTime;
    private String  createTimeStr;
    // 审核状态
    private Integer examine;

    // 主体编号
    private String code;

    // 所属地区
    private String area;

    // 所属部门编号
    private String departmentCode;

    // 是否是委托组织（0主体，1委托组织）
    private Integer isDepute;

    // 修改时间
    private Date updateTime;

    // 修改人ID
    private String updateId;

    // 审核时间
    private Date examineTime;

    // 审核人ID
    private String examineId;

    // 取消审核时间
    private Date disExamineTime;

    // 机构代码
    private String organizationCode;

    // 法定代表人
    private String representative;

    // 性质（01行政机关、02事业单位）（单选）
    private String entrustNature;

    // 联系电话
    private String telephone;

    // 邮编
    private String postCode;

    // 传真
    private String fax;

    // 地址
    private String address;

    // 删除时间
    private Date deleteTime;

    // 01城乡建设管理领域，02经济和市场监管领域，03民生和社会管理领域，04农村和专业管理领域，05国家垂直管理部门
    private String field;

    // 区分组
    private String groupArea;
    
    // 主体ID组
    private String ids;

    //职权类别
    private String subjectPower;
    
    //执法系统
    private String orgSys;
    
    private String type;
    
    //账号
    private String userName;
    
    //密码
    private String password;
    
    //综合查询参数
    private Integer isUser;
    private Integer personNo;
    private Integer orgNo;
    private Integer powerNoStart;
    private Integer powerNoEnd;
    private Integer punishNoStart;
    private Integer punishNoEnd;
    private Integer inspectNoStart;
    private Integer inspectNoEnd;
    private Integer permitNoStart;
    private Integer permitNoEnd;
    private Integer forceNoStart;
    private Integer forceNoEnd;
    
	public Integer getIsUser() {
		return isUser;
	}

	public void setIsUser(Integer isUser) {
		this.isUser = isUser;
	}

	public Integer getPersonNo() {
		return personNo;
	}

	public void setPersonNo(Integer personNo) {
		this.personNo = personNo;
	}

	public Integer getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(Integer orgNo) {
		this.orgNo = orgNo;
	}

	public Integer getPowerNoStart() {
		return powerNoStart;
	}

	public void setPowerNoStart(Integer powerNoStart) {
		this.powerNoStart = powerNoStart;
	}

	public Integer getPowerNoEnd() {
		return powerNoEnd;
	}

	public void setPowerNoEnd(Integer powerNoEnd) {
		this.powerNoEnd = powerNoEnd;
	}

	public Integer getPunishNoStart() {
		return punishNoStart;
	}

	public void setPunishNoStart(Integer punishNoStart) {
		this.punishNoStart = punishNoStart;
	}

	public Integer getPunishNoEnd() {
		return punishNoEnd;
	}

	public void setPunishNoEnd(Integer punishNoEnd) {
		this.punishNoEnd = punishNoEnd;
	}

	public Integer getInspectNoStart() {
		return inspectNoStart;
	}

	public void setInspectNoStart(Integer inspectNoStart) {
		this.inspectNoStart = inspectNoStart;
	}

	public Integer getInspectNoEnd() {
		return inspectNoEnd;
	}

	public void setInspectNoEnd(Integer inspectNoEnd) {
		this.inspectNoEnd = inspectNoEnd;
	}

	public Integer getPermitNoStart() {
		return permitNoStart;
	}

	public void setPermitNoStart(Integer permitNoStart) {
		this.permitNoStart = permitNoStart;
	}

	public Integer getPermitNoEnd() {
		return permitNoEnd;
	}

	public void setPermitNoEnd(Integer permitNoEnd) {
		this.permitNoEnd = permitNoEnd;
	}

	public Integer getForceNoStart() {
		return forceNoStart;
	}

	public void setForceNoStart(Integer forceNoStart) {
		this.forceNoStart = forceNoStart;
	}

	public Integer getForceNoEnd() {
		return forceNoEnd;
	}

	public void setForceNoEnd(Integer forceNoEnd) {
		this.forceNoEnd = forceNoEnd;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

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

    public String getUpdateId() {
        return updateId;
    }

    public String getExamineId() {
        return examineId;
    }

    public void setExamineId(String examineId) {
        this.examineId = examineId;
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
    
    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getExamineTime() {
		return examineTime;
	}

	public void setExamineTime(Date examineTime) {
		this.examineTime = examineTime;
	}

	public Date getDisExamineTime() {
		return disExamineTime;
	}

	public void setDisExamineTime(Date disExamineTime) {
		this.disExamineTime = disExamineTime;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
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

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return parentName;
	}

	/**
	 * @param parentName the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    
}
