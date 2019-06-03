package com.beidasoft.zfjd.power.model;

public class PowerOperationModel {
    // 操作方式
    private String optType;
    
    // 调整职权ID
    private String powerTempId;
    
    // 调整职权
    private String powerTempName;
    
    // 被调整职权ID
    private String powerFormalId;
    
    // 被调整职权
    private String powerFormalName;

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public String getPowerTempId() {
        return powerTempId;
    }

    public void setPowerTempId(String powerTempId) {
        this.powerTempId = powerTempId;
    }

    public String getPowerTempName() {
        return powerTempName;
    }

    public void setPowerTempName(String powerTempName) {
        this.powerTempName = powerTempName;
    }

    public String getPowerFormalId() {
        return powerFormalId;
    }

    public void setPowerFormalId(String powerFormalId) {
        this.powerFormalId = powerFormalId;
    }

    public String getPowerFormalName() {
        return powerFormalName;
    }

    public void setPowerFormalName(String powerFormalName) {
        this.powerFormalName = powerFormalName;
    }

    
}
