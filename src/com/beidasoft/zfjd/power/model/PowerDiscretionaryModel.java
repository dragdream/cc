package com.beidasoft.zfjd.power.model;

public class PowerDiscretionaryModel {
    // 主键
    private String id;
    
    // 职权ID
    private String powerId;
    
    // 职权名称
    private String powerName;
    
    // 违法事实
    private String illegalFact;
    
    // 处罚标准
    private String punishStandard;
    
    // 创建日期
    private String createDateStr;
    
    // 更新日期
    private String updateDateStr;
    
    // 删除标志
    private int isDelete;
    
    // 管理主体
    private String subjectId;
    
    // 实施主体
    private String subjectActId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getIllegalFact() {
        return illegalFact;
    }

    public void setIllegalFact(String illegalFact) {
        this.illegalFact = illegalFact;
    }

    public String getPunishStandard() {
        return punishStandard;
    }

    public void setPunishStandard(String punishStandard) {
        this.punishStandard = punishStandard;
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

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectActId() {
        return subjectActId;
    }

    public void setSubjectActId(String subjectActId) {
        this.subjectActId = subjectActId;
    }
    
    
}
