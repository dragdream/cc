package com.beidasoft.zfjd.lawManage.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 法律法规调整实体类
 */
@Entity
@Table(name="TBL_BASE_LAW_ADJUST_REPORT")
public class LawAdjustReport {
    // 数据唯一标识
    @Id
    @Column(name = "ID")
    private String id;

    // 法律法规名称
    @Column(name = "NAME")
    private String name;

    // 时效性01现行有效 02失效
    @Column(name = "TIMELINESS")
    private String timeliness;

    // 法律法规类别
    @Column(name = "SUBMITLAW_LEVEL")
    private String submitlawLevel;

    // 发文字号
    @Column(name = "WORD")
    private String word;

    // 发布机关
    @Column(name = "ORGAN")
    private String organ;

    // 颁布日期
    @Column(name = "PROMULGATION")
    private Date promulgation;

    // 实施日期
    @Column(name = "IMPLEMENTATION")
    private Date implementation;

    // 备注
    @Column(name = "REMARK")
    private String remark;

    // 审核状态（1待上报 2待审核 3待导入 4已导入 5已提交入库9退回）
    @Column(name = "EXAMINE")
    private Integer examine;

    // 是否删除
    @Column(name = "IS_DELETE")
    private Integer isDelete;

    // 创建人id
    @Column(name = "CREATE_ID")
    private String createId;

    // 创建日期
    @Column(name = "CREATE_DATE")
    private Date createDate;

    // 法律原文文件存储路径
    @Column(name = "LAW_PATH")
    private String lawPath;

    // 法律编号（暂无用）
    @Column(name = "LAW_NUM")
    private Integer lawNum;

    // 申请调整类型（01新法02修订03废止）
    @Column(name = "CONTROL_TYPE")
    private String controlType;

    // 调整法律id
    @Column(name = "UPDATE_LAW_ID")
    private String updateLawId;

    // 创建者所属执法部门id
    @Column(name = "CREATE_DEPT_ID")
    private String createDeptId;

    // 创建者所属执法主体id
    @Column(name = "CREATE_SUBJECT_ID")
    private String createSubjectId;

    // 创建者所属监督部门id
    @Column(name = "CREATE_SUP_DEPT_ID")
    private String createSupDeptId;

    // 创建者所属机关类型（1监督部门 2执法部门 3 执法主体）
    @Column(name = "CREATE_ORG_TYPE")
    private Integer createOrgType;

    // 上报审核日期
    @Column(name = "SUBMIT_DATE")
    private Date submitDate;

    // 审核通过后新增法律数据的ID
    @Column(name = "NEW_LAW_ID")
    private String newLawId;

    // 退回原因
    @Column(name = "BACK_REASON")
    private String backReason;

    // 数据退回日期
    @Column(name = "BACK_DATE")
    private Date backDate;

    // 是否退回
    @Column(name = "IS_BACK")
    private Integer isBack;
    
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

    public String getTimeliness() {
        return timeliness;
    }

    public void setTimeliness(String timeliness) {
        this.timeliness = timeliness;
    }

    public String getSubmitlawLevel() {
        return submitlawLevel;
    }

    public void setSubmitlawLevel(String submitlawLevel) {
        this.submitlawLevel = submitlawLevel;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    public Date getPromulgation() {
        return promulgation;
    }

    public void setPromulgation(Date promulgation) {
        this.promulgation = promulgation;
    }

    public Date getImplementation() {
        return implementation;
    }

    public void setImplementation(Date implementation) {
        this.implementation = implementation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLawPath() {
        return lawPath;
    }

    public void setLawPath(String lawPath) {
        this.lawPath = lawPath;
    }

    public Integer getLawNum() {
		return lawNum;
	}

	public void setLawNum(Integer lawNum) {
		this.lawNum = lawNum;
	}

	public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public String getUpdateLawId() {
        return updateLawId;
    }

    public void setUpdateLawId(String updateLawId) {
        this.updateLawId = updateLawId;
    }

    public String getCreateDeptId() {
        return createDeptId;
    }

    public void setCreateDeptId(String createDeptId) {
        this.createDeptId = createDeptId;
    }

    public String getCreateSubjectId() {
        return createSubjectId;
    }

    public void setCreateSubjectId(String createSubjectId) {
        this.createSubjectId = createSubjectId;
    }

    public String getCreateSupDeptId() {
        return createSupDeptId;
    }

    public void setCreateSupDeptId(String createSupDeptId) {
        this.createSupDeptId = createSupDeptId;
    }

    public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public String getNewLawId() {
        return newLawId;
    }

    public void setNewLawId(String newLawId) {
        this.newLawId = newLawId;
    }

    public String getBackReason() {
        return backReason;
    }

    public void setBackReason(String backReason) {
        this.backReason = backReason;
    }

    public Date getBackDate() {
        return backDate;
    }

    public void setBackDate(Date backDate) {
        this.backDate = backDate;
    }

	public Integer getExamine() {
		return examine;
	}

	public void setExamine(Integer examine) {
		this.examine = examine;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getCreateOrgType() {
		return createOrgType;
	}

	public void setCreateOrgType(Integer createOrgType) {
		this.createOrgType = createOrgType;
	}

	public Integer getIsBack() {
		return isBack;
	}

	public void setIsBack(Integer isBack) {
		this.isBack = isBack;
	}
}
