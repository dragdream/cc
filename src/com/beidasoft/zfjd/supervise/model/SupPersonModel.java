package com.beidasoft.zfjd.supervise.model;

import java.util.Date;

/**
 * 监督人员表MODEL类
 */
public class SupPersonModel {
    // 主键id
    private String id;

    // 名称
    private String name;

    // 性别
    private String sex;

    // 身份证号
    private String personId;

    // 出生日期
    private String birthStr;

    // 删除标志（0未删除，1删除）
    private Integer isDelete;

    // 账号名
    private String username;

    // 密码
    private String password;

    // 职位
    private String job;

    // 学历
    private String education;

    // 民族
    private String nation;

    // 发布标志（0未发布，1发布）
    private Integer release;

    // 审核标志（0未审核，1审核）
    private Integer examine;

    // 部门证件号
    private String departmentCode;

    // 市级证件号
    private String cityCode;
    
    //执法证号
    private String enforcerCode;

    // 部门证件申领日期
    private String departmentGettimeStr;

    // 市级证件申领日期
    private String cityGettimeStr;

    // 职权类型
    private String powerTypes;

    // 政治面貌
    private String politive;

    // 人员类型（01执法人员，02监督人员）
    private String personType;

    // 联系电话
    private String telephone;

    // 移动电话
    private String mobile;

    // 是否具有执法证号
    private Integer isGetcode;

    // 邮箱
    private String email;

    // 审核日期
    private Date examineTime;

    // 修改日期
    private Date updateTime;

    // 创建时间
    private Date createTime;

    // 创建人id
    private String createId;

    // 修改人id
    private String updateId;

    // 审核人id
    private String examineId;

    // 取消审核时间
    private Date disExamineTime;

    // 有效截至日期
    private String effectiveTimeStr;

    // 执法类别
    private String enforceType;

    // 工作单位（委托组织人员填写）
    private String workUnit;

    // 部门证件有效截至日期
    private String deptEffectiveTimeStr;

    // 委托组织id
    private String entrustId;

    // 人员来源（1表示导入）,3表示执法证件模块插入
    private Integer resourceType;

    // 职务级别
    private String jobClass;

    // 删除时间
    private Date deleteTime;
    
    //主体名称
    private String subjectName;
    
    //所属部门
    private String departmentName;
    private String departmentId;
    
    public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	private String ids;
    
	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getBirthStr() {
        return birthStr;
    }

    public void setBirthStr(String birthStr) {
        this.birthStr = birthStr;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Integer getRelease() {
        return release;
    }

    public void setRelease(Integer release) {
        this.release = release;
    }

    public Integer getExamine() {
        return examine;
    }

    public void setExamine(Integer examine) {
        this.examine = examine;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

	public String getDepartmentGettimeStr() {
		return departmentGettimeStr;
	}

	public void setDepartmentGettimeStr(String departmentGettimeStr) {
		this.departmentGettimeStr = departmentGettimeStr;
	}

	public String getCityGettimeStr() {
		return cityGettimeStr;
	}

	public void setCityGettimeStr(String cityGettimeStr) {
		this.cityGettimeStr = cityGettimeStr;
	}

	public String getPowerTypes() {
		return powerTypes;
	}

	public void setPowerTypes(String powerTypes) {
		this.powerTypes = powerTypes;
	}

	public String getPolitive() {
		return politive;
	}

	public void setPolitive(String politive) {
		this.politive = politive;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getIsGetcode() {
		return isGetcode;
	}

	public void setIsGetcode(Integer isGetcode) {
		this.isGetcode = isGetcode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public String getExamineId() {
		return examineId;
	}

	public void setExamineId(String examineId) {
		this.examineId = examineId;
	}

	public String getEffectiveTimeStr() {
		return effectiveTimeStr;
	}

	public void setEffectiveTimeStr(String effectiveTimeStr) {
		this.effectiveTimeStr = effectiveTimeStr;
	}

	public String getEnforceType() {
		return enforceType;
	}

	public void setEnforceType(String enforceType) {
		this.enforceType = enforceType;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public String getDeptEffectiveTimeStr() {
		return deptEffectiveTimeStr;
	}

	public void setDeptEffectiveTimeStr(String deptEffectiveTimeStr) {
		this.deptEffectiveTimeStr = deptEffectiveTimeStr;
	}

	public String getEntrustId() {
		return entrustId;
	}

	public void setEntrustId(String entrustId) {
		this.entrustId = entrustId;
	}

	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public String getEnforcerCode() {
		return enforcerCode;
	}

	public void setEnforcerCode(String enforcerCode) {
		this.enforcerCode = enforcerCode;
	}

	public String getSubjectName() {
		return subjectName;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}
