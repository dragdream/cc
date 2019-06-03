package com.beidasoft.zfjd.system.model;

/**
 * 系统部门表MODEL类
 */
public class DepartmentModel {
    // 主键
    private Integer uuid;
    private String id;
    // 
    private String address;

    // 主体id
    private String deptFullId;

    // 部门全称
    private String deptFullName;

    // 
    private String deptFunc;

    // 部门名称
    private String deptName;

    // 部门编号
    private Integer deptNo;

    // 
    private String deptParentLevel;

    // 
    private Integer dingDeptId;

    // 
    private String faxNo;

    // 
    private String guid;

    // 
    private String leader1;

    // 
    private String leader2;

    // 
    private String manager;

    // 
    private String manager2;
    
    //执法部门id，名称
    private String businessDeptId;
    private String businessDeptName;

    //监督部门id，名称
    private String businessSupDeptId;
    private String businessSupDeptName;

    //执法主体id，名称
    private String businessSubjectId;
    private String businessSubjectName;
    
    //业务部门类型
    private String orgType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBusinessDeptId() {
		return businessDeptId;
	}

	public void setBusinessDeptId(String businessDeptId) {
		this.businessDeptId = businessDeptId;
	}

	public String getBusinessDeptName() {
		return businessDeptName;
	}

	public void setBusinessDeptName(String businessDeptName) {
		this.businessDeptName = businessDeptName;
	}

	public String getBusinessSupDeptId() {
		return businessSupDeptId;
	}

	public void setBusinessSupDeptId(String businessSupDeptId) {
		this.businessSupDeptId = businessSupDeptId;
	}

	public String getBusinessSupDeptName() {
		return businessSupDeptName;
	}

	public void setBusinessSupDeptName(String businessSupDeptName) {
		this.businessSupDeptName = businessSupDeptName;
	}

	public String getBusinessSubjectId() {
		return businessSubjectId;
	}

	public void setBusinessSubjectId(String businessSubjectId) {
		this.businessSubjectId = businessSubjectId;
	}

	public String getBusinessSubjectName() {
		return businessSubjectName;
	}

	public void setBusinessSubjectName(String businessSubjectName) {
		this.businessSubjectName = businessSubjectName;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getDeptFunc() {
        return deptFunc;
    }

    public void setDeptFunc(String deptFunc) {
        this.deptFunc = deptFunc;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(Integer deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptParentLevel() {
        return deptParentLevel;
    }

    public void setDeptParentLevel(String deptParentLevel) {
        this.deptParentLevel = deptParentLevel;
    }

    public Integer getDingDeptId() {
        return dingDeptId;
    }

    public void setDingDeptId(Integer dingDeptId) {
        this.dingDeptId = dingDeptId;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
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

}
