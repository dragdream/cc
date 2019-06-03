package com.beidasoft.zfjd.power.model;

import net.sf.json.JSONArray;

public class PowerModel {
    // 主键
    private String id;

    // 职权名称
    private String name;

    // 职权类型
    private String powerType;

    // 部门ID
    private String departmentId;

    // 是否删除
    private int isDelete;

    // 职权编号
    private String code;

    // 法定执法主体
    private String subjectDesc;

    // 主体ID
    private String subjectId;
    
    // 主体名称
    private String subjectName;

    // 创建日期
    private String createDateStr;
    
    // 更新日期
    private String updateDateStr;

    // 流程图类型
    private String flowPictureType;

    // 是否涉刑，0 否，1 是
    private int isCriminal;

    // 撤销时间
    private String revokeDateStr;

    // 批次号
    private String batchCode;

    // 删除时间
    private String deleteDateStr;

    // 职权领域
    private String powerMold;
    
    //职权类型
    private String powerDetail;
    
    //职权层级
    private String powerLevel;
    
    //职权层级json
    private String powerLevelJson;
    
    //职权流程图
    private String powerFlowsheet;
    
    //职权依据
    private JSONArray gistArray;
    
    //职权流程图
    private JSONArray flowsheetArray;
    
    //职权层级
    private JSONArray levelArray;
    
    // 违法依据ID
    private String gistIds;
    
    // 处罚依据ID
    private String punishIds;
    
    // 设定依据ID
    private String settingIds;
    
    // 实施主体ID
    private String subjectIds;
    
    // 职权ID列表
    private String ids;
    
    // 状态
    private String currentState;
    
    // 是否包含自由裁量权
    private String isDeiscretionary;
    
    private String actSubject;
    
    // 调整标志
    private String adjustFlag;

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

    public String getPowerType() {
        return powerType;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSubjectDesc() {
        return subjectDesc;
    }

    public void setSubjectDesc(String subjectDesc) {
        this.subjectDesc = subjectDesc;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getUpdateDateStr() {
        return updateDateStr;
    }

    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
    }

    public String getFlowPictureType() {
        return flowPictureType;
    }

    public void setFlowPictureType(String flowPictureType) {
        this.flowPictureType = flowPictureType;
    }

    public int getIsCriminal() {
        return isCriminal;
    }

    public void setIsCriminal(int isCriminal) {
        this.isCriminal = isCriminal;
    }

    public String getRevokeDateStr() {
        return revokeDateStr;
    }

    public void setRevokeDateStr(String revokeDateStr) {
        this.revokeDateStr = revokeDateStr;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getDeleteDateStr() {
        return deleteDateStr;
    }

    public void setDeleteDateStr(String deleteDateStr) {
        this.deleteDateStr = deleteDateStr;
    }

    public String getPowerMold() {
        return powerMold;
    }

    public void setPowerMold(String powerMold) {
        this.powerMold = powerMold;
    }

    public String getPowerDetail() {
        return powerDetail;
    }

    public void setPowerDetail(String powerDetail) {
        this.powerDetail = powerDetail;
    }

    public String getPowerLevel() {
        return powerLevel;
    }

    public void setPowerLevel(String powerLevel) {
        this.powerLevel = powerLevel;
    }
    
    public String getPowerLevelJson() {
        return powerLevelJson;
    }

    public void setPowerLevelJson(String powerLevelJson) {
        this.powerLevelJson = powerLevelJson;
    }
    
    public String getPowerFlowsheet() {
        return powerFlowsheet;
    }

    public void setPowerFlowsheet(String powerFlowsheet) {
        this.powerFlowsheet = powerFlowsheet;
    }

    public JSONArray getGistArray() {
        return gistArray;
    }

    public void setGistArray(JSONArray gistArray) {
        this.gistArray = gistArray;
    }

    public JSONArray getFlowsheetArray() {
        return flowsheetArray;
    }

    public void setFlowsheetArray(JSONArray flowsheetArray) {
        this.flowsheetArray = flowsheetArray;
    }

    public JSONArray getLevelArray() {
        return levelArray;
    }

    public void setLevelArray(JSONArray levelArray) {
        this.levelArray = levelArray;
    }

    public String getGistIds() {
        return gistIds;
    }

    public void setGistIds(String gistIds) {
        this.gistIds = gistIds;
    }

    public String getPunishIds() {
        return punishIds;
    }

    public void setPunishIds(String punishIds) {
        this.punishIds = punishIds;
    }

    public String getSettingIds() {
        return settingIds;
    }

    public void setSettingIds(String settingIds) {
        this.settingIds = settingIds;
    }

    public String getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(String subjectIds) {
        this.subjectIds = subjectIds;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getIsDeiscretionary() {
        return isDeiscretionary;
    }

    public void setIsDeiscretionary(String isDeiscretionary) {
        this.isDeiscretionary = isDeiscretionary;
    }

    public String getActSubject() {
        return actSubject;
    }

    public void setActSubject(String actSubject) {
        this.actSubject = actSubject;
    }

    public String getAdjustFlag() {
        return adjustFlag;
    }

    public void setAdjustFlag(String adjustFlag) {
        this.adjustFlag = adjustFlag;
    }
    
    
}
