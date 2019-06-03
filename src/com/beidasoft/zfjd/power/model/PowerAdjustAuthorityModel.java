package com.beidasoft.zfjd.power.model;

public class PowerAdjustAuthorityModel {
    // 主键
    private String id;
    // 调整批次ID
    private String adjustId;
    
    // 职权ID
    public String powerId;
    
    // 职权名称
    public String powerName;
    
    //职权类型
    public String powerType;
    
    // 职权调整方式
    public String powerOptType;
    
    // 职权审核状态
    public String examineState;
    
    // 职权审核状态值
    public String examineStateStr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdjustId() {
        return adjustId;
    }

    public void setAdjustId(String adjustId) {
        this.adjustId = adjustId;
    }

    public String getPowerId() {
        return powerId;
    }

    public void setPowerId(String powerId) {
        this.powerId = powerId;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public String getPowerType() {
        return powerType;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }

    public String getPowerOptType() {
        return powerOptType;
    }

    public void setPowerOptType(String powerOptType) {
        this.powerOptType = powerOptType;
    }

    public String getExamineState() {
        return examineState;
    }

    public void setExamineState(String examineState) {
        this.examineState = examineState;
    }

    public String getExamineStateStr() {
        return examineStateStr;
    }

    public void setExamineStateStr(String examineStateStr) {
        this.examineStateStr = examineStateStr;
    }
    
    
}
