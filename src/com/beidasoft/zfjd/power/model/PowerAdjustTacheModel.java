package com.beidasoft.zfjd.power.model;

public class PowerAdjustTacheModel {
    private String id;
    
    private String adjustId;
    
    private String closedDateStr;
    
    private Integer examinePersonId;
    
    private String examinePersonName;
    
    private String examineDateStr;
    
    private String examineView;
    
    // 环节序号
    private Integer tacheId;
    
    // 环节名称
    private String tacheName;

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

    public String getClosedDateStr() {
        return closedDateStr;
    }

    public void setClosedDateStr(String closedDateStr) {
        this.closedDateStr = closedDateStr;
    }

    public Integer getExaminePersonId() {
        return examinePersonId;
    }

    public void setExaminePersonId(Integer examinePersonId) {
        this.examinePersonId = examinePersonId;
    }

    public String getExaminePersonName() {
        return examinePersonName;
    }

    public void setExaminePersonName(String examinePersonName) {
        this.examinePersonName = examinePersonName;
    }

    public Integer getTacheId() {
        return tacheId;
    }

    public void setTacheId(Integer tacheId) {
        this.tacheId = tacheId;
    }

    public String getTacheName() {
        return tacheName;
    }

    public void setTacheName(String tacheName) {
        this.tacheName = tacheName;
    }

    public String getExamineDateStr() {
        return examineDateStr;
    }

    public void setExamineDateStr(String examineDateStr) {
        this.examineDateStr = examineDateStr;
    }

    public String getExamineView() {
        return examineView;
    }

    public void setExamineView(String examineView) {
        this.examineView = examineView;
    }
}
