package com.beidasoft.zfjd.power.model;

public class PowerSubjectTempModel {
    // 主键
    private String id;
    
    // 实施主体信息
    private String subjectId;
    
    //实施主体名称
    private String subjectName;
    
    //创建日期
    private String createDateStr;
    
    //删除标志
    private int isDelete;
    
    //是否委托组织
    private String deputeType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getDeputeType() {
        return deputeType;
    }

    public void setDeputeType(String deputeType) {
        this.deputeType = deputeType;
    }
}
