package com.beidasoft.zfjd.officials.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 执法人员实体类
 */
@Entity
@Table(name="TBL_BASE_PERSON")
public class TblOfficials {
    // 主键id
	@Id
    @Column(name = "id")
    private String id;

    // 名称
    @Column(name = "name")
    private String name;

    // 性别
    @Column(name = "sex")
    private String sex;

    // 身份证号
    @Column(name = "person_id")
    private String personId;

    // 出生日期
    @Column(name = "birth")
    private Date birth;

    // 删除标志（0未删除，1删除）
    @Column(name = "is_delete")
    private Integer isDelete;

    // 账号名
    @Column(name = "username")
    private String username;

    // 密码
    @Column(name = "password")
    private String password;

    // 职位
    @Column(name = "job")
    private String job;

    // 学历
    @Column(name = "education")
    private String education;

    // 民族
    @Column(name = "nation")
    private String nation;

    // 发布标志（0未发布，1发布）
    @Column(name = "release")
    private Integer release;

    // 审核标志（0未审核，1审核）
    @Column(name = "examine")
    private Integer examine;

    // 部门证件号
    @Column(name = "department_code")
    private String departmentCode;

    // 市级证件号
    @Column(name = "city_code")
    private String cityCode;

    // 部门证件申领日期
    @Column(name = "department_gettime")
    private Date departmentGettime;

    // 市级证件申领日期
    @Column(name = "city_gettime")
    private Date cityGettime;

    // 职权类型
    @Column(name = "power_types")
    private String powerTypes;

    // 政治面貌
    @Column(name = "politive")
    private String politive;

    // 人员类型（01执法人员，02监督人员）
    @Column(name = "person_type")
    private String personType;

    // 联系电话
    @Column(name = "telephone")
    private String telephone;

    // 移动电话
    @Column(name = "mobile")
    private String mobile;

    // 是否具有执法证号
    @Column(name = "is_getcode")
    private Integer isGetcode;

    // 邮箱
    @Column(name = "email")
    private String email;

    // 审核日期
    @Column(name = "examine_time")
    private Date examineTime;

    // 修改日期
    @Column(name = "update_time")
    private Date updateTime;

    // 创建时间
    @Column(name = "create_time")
    private Date createTime;

    // 创建人id
    @Column(name = "create_id")
    private String createId;

    // 修改人id
    @Column(name = "update_id")
    private String updateId;

    // 审核人id
    @Column(name = "examine_id")
    private String examineId;

    // 取消审核时间
    @Column(name = "dis_examine_time")
    private Date disExamineTime;

    // 有效截至日期
    @Column(name = "effective_time")
    private Date effectiveTime;

    // 执法类别
    @Column(name = "enforce_type")
    private String enforceType;

    // 工作单位（委托组织人员填写）
    @Column(name = "work_unit")
    private String workUnit;

    // 部门证件有效截至日期
    @Column(name = "dept_effective_time")
    private Date deptEffectiveTime;

    // 委托组织id
    @Column(name = "entrust_id")
    private String entrustId;

    // 人员来源（1表示导入）,3表示执法证件模块插入
    @Column(name = "resource_type")
    private Integer resourceType;

    // 职务级别
    @Column(name = "job_class")
    private String jobClass;

    // 删除时间
    @Column(name = "delete_time")
    private Date deleteTime;
    
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

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
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

    public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
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

	public Date getDepartmentGettime() {
		return departmentGettime;
	}

	public void setDepartmentGettime(Date departmentGettime) {
		this.departmentGettime = departmentGettime;
	}

	public Date getCityGettime() {
		return cityGettime;
	}

	public void setCityGettime(Date cityGettime) {
		this.cityGettime = cityGettime;
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

	public Date getDisExamineTime() {
		return disExamineTime;
	}

	public void setDisExamineTime(Date disExamineTime) {
		this.disExamineTime = disExamineTime;
	}

	public Date getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
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

	public Date getDeptEffectiveTime() {
		return deptEffectiveTime;
	}

	public void setDeptEffectiveTime(Date deptEffectiveTime) {
		this.deptEffectiveTime = deptEffectiveTime;
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

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

    
}
