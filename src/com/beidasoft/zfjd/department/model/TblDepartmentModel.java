package com.beidasoft.zfjd.department.model;

import java.util.Date;

public class TblDepartmentModel  {
	
		// 删除时间
		private Date deleteTime;

		// 01城乡建设管理领域，02经济和市场监管领域，03民生和社会管理领域，04农村和专业管理领域，05国家垂直管理部门
		private String field;

		// 区分组
		private String groupArea;
		
		// 法定代表人
		private String representative;

		// 邮编
		private String postCode;

		// 传真
		 private String fax;

		// 联系电话
		private String phone;

		// 地址
		private String address;

		// 发布状态（0：未发布，1：已发布）
		private Integer isRelease;

		// 审核状态（0：未审核，1：已审核）
		private Integer isExamine;

		//执法主体
		private String orgSys;
		
		//部门层级
		private String deptLevel;
		
		//垂管部门ID
		private String droopId;
		
		// 行政区划
		 private String administrativeDivision;

		// 部门编号
		private String departmentCode;

		// 部门简称
		private String simpleName;

		// 监督部门ID
		 private String superviceDepartmentId;

		// 发布时间
		 private String releaseTime;

		// 审核时间
		 private Date examineTime;

		// 修改时间
		 private Date updateTime;

		// 发布人
		 private String releasePersonId;

		// 审核人
		 private String examinePersonId;

		// 创建人名称
		 private String creatorName;

		// 是否是监督部门（1表示监督部门）
		 private Integer isSupervise;

		// 取消审核时间
		 private Date disExamineTime;

		// 取消发布时间
		 private Date disReleaseTime;

		// 修改人ID
		 private String updatePersonId;

		// 0市级，1二级局
		 private Integer type;

		// 1垂管，0非垂管
		private Integer isManubrium;
		
		//是否人民政府1是2否
		private Integer isGovernment;
		
		//部门账号
		private String userName;

		//部门密码
		private String password;
		
		private String id;
		
		private String ids;
		
		private Integer sid;
		
		private String parentId;
	
		private String name;
		
		private String icon;
		
		private String nature;
		
		private Integer indexOrder;
		
		private Integer isDelete;
	
		private Integer isStop;
	
		private String creatorId;
	
		private Date createTime;
		
		//综合查询字段
		private String subName;
		
		private String orgName;
		
		private String powerName;
		
		private Integer personNo;
		
		private Integer orgNo;
		
		private Integer powerNo;
		
		private Integer subNo;
		
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

		public Integer getPowerNo() {
			return powerNo;
		}

		public void setPowerNo(Integer powerNo) {
			this.powerNo = powerNo;
		}

		public Integer getSubNo() {
			return subNo;
		}

		public void setSubNo(Integer subNo) {
			this.subNo = subNo;
		}

	public String getSubName() {
			return subName;
		}

		public void setSubName(String subName) {
			this.subName = subName;
		}

		public String getOrgName() {
			return orgName;
		}

		public void setOrgName(String orgName) {
			this.orgName = orgName;
		}

		public String getPowerName() {
			return powerName;
		}

		public void setPowerName(String powerName) {
			this.powerName = powerName;
		}

	public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

	public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

	public Integer getIsGovernment() {
			return isGovernment;
		}

	public void setIsGovernment(Integer isGovernment) {
		this.isGovernment = isGovernment;
	}

	public String getDroopId() {
		return droopId;
	}

	public void setDroopId(String droopId) {
		this.droopId = droopId;
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

	public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getRepresentative() {
		return representative;
	}

	public void setRepresentative(String representative) {
		this.representative = representative;
	}

	public Integer getIsRelease() {
		return isRelease;
	}

	public void setIsRelease(Integer isRelease) {
		this.isRelease = isRelease;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
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

	
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getIsExamine() {
		return isExamine;
	}

	public void setIsExamine(Integer isExamine) {
		this.isExamine = isExamine;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAdministrativeDivision() {
		return administrativeDivision;
	}

	public void setAdministrativeDivision(String administrativeDivision) {
		this.administrativeDivision = administrativeDivision;
	}

	public String getSuperviceDepartmentId() {
		return superviceDepartmentId;
	}

	public void setSuperviceDepartmentId(String superviceDepartmentId) {
		this.superviceDepartmentId = superviceDepartmentId;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
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

	public Integer getIsManubrium() {
		return isManubrium;
	}

	public void setIsManubrium(Integer isManubrium) {
		this.isManubrium = isManubrium;
	}

}
