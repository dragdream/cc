package com.beidasoft.zfjd.lawManage.model;

import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

/**
 * 法律法规调整MODEL类
 */
public class LawAdjustReportModel {
    // 数据唯一标识
    private String id;

    // 法律法规名称
    private String name;

    // 时效性01现行有效 02失效
    private String timeliness;
    
    // 时效性
    private String timelinessStr;

    // 法律法规类别
    private String submitlawLevel;

    // 法律法规类别
    private String submitlawLevelStr;
    
    // 发文字号
    private String word;

    // 发布机关
    private String organ;

    // 颁布日期
    private String promulgationStr;

    // 实施日期
    private String implementationStr;

    // 备注
    private String remark;

    // 审核状态（1待上报 2待审核 3待导入 4已导入 5已提交入库9退回）
    private Integer examine;

    // 是否删除
    private Integer isDelete;

    // 创建人id
    private String createId;

    // 创建日期
    private String createDateStr;

    // 法律原文文件存储路径
    private String lawPath;

    // 法律编号（暂无用）
    private Integer lawNum;

    // 申请调整类型（01新法02修订03废止）
    private String controlType;

    // 申请调整类型
    private String controlTypeStr;
    
    // 调整法律id
    private String updateLawId;
    
    // 调整法律名称
    private String updateLawName;

    // 创建者所属执法部门id
    private String createDeptId;

    // 创建者所属执法主体id
    private String createSubjectId;

    // 创建者所属监督部门id
    private String createSupDeptId;

    // 创建者所属机关类型（1监督部门 2执法部门 3 执法主体）
    private Integer createOrgType;

    // 上报审核日期
    private String submitDateStr;

    // 审核通过后新增法律数据的ID
    private String newLawId;

    // 退回原因
    private String backReason;

    // 数据退回日期
    private String backDateStr;
    
    //文件路径
    private String filename;

	List<TeeAttachmentModel> attachModels;
	
	//待提交ids
    private String submitIds;
    
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

    public String getPromulgationStr() {
        return promulgationStr;
    }

    public void setPromulgationStr(String promulgationStr) {
        this.promulgationStr = promulgationStr;
    }

    public String getImplementationStr() {
        return implementationStr;
    }

    public void setImplementationStr(String implementationStr) {
        this.implementationStr = implementationStr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
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

    public Integer getCreateOrgType() {
        return createOrgType;
    }

    public void setCreateOrgType(Integer createOrgType) {
        this.createOrgType = createOrgType;
    }

    public String getSubmitDateStr() {
		return submitDateStr;
	}

	public void setSubmitDateStr(String submitDateStr) {
		this.submitDateStr = submitDateStr;
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

    public String getBackDateStr() {
        return backDateStr;
    }

    public void setBackDateStr(String backDateStr) {
        this.backDateStr = backDateStr;
    }

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public List<TeeAttachmentModel> getAttachModels() {
		return attachModels;
	}

	public void setAttachModels(List<TeeAttachmentModel> attachModels) {
		this.attachModels = attachModels;
	}

	public String getSubmitIds() {
		return submitIds;
	}

	public void setSubmitIds(String submitIds) {
		this.submitIds = submitIds;
	}

	public String getTimelinessStr() {
		return timelinessStr;
	}

	public void setTimelinessStr(String timelinessStr) {
		this.timelinessStr = timelinessStr;
	}

	public String getSubmitlawLevelStr() {
		return submitlawLevelStr;
	}

	public void setSubmitlawLevelStr(String submitlawLevelStr) {
		this.submitlawLevelStr = submitlawLevelStr;
	}

	public String getUpdateLawName() {
		return updateLawName;
	}

	public void setUpdateLawName(String updateLawName) {
		this.updateLawName = updateLawName;
	}

	public String getControlTypeStr() {
		return controlTypeStr;
	}

	public void setControlTypeStr(String controlTypeStr) {
		this.controlTypeStr = controlTypeStr;
	}
	
}
