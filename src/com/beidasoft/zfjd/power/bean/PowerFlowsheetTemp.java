package com.beidasoft.zfjd.power.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *  职权流程表实体类
* 2018 
* @ClassName: PowerFlowsheet.java
* @Description: 该类的功能描述
*
* @author: chenq
* @date: 2018年12月24日 下午3:20:35 
*
 */
@Entity
@Table(name="TBL_POWER_FLOWSHEET_TEMP")
public class PowerFlowsheetTemp {
    // 主键
    @Id
    @Column(name = "ID")
    private String id;

    // 职权ID
    @ManyToOne
    @JoinColumn(name = "POWER_ID") // 这是双向的 要写JoinColumn 对面要写 mappedBy
    private PowerTemp powerFlowsheetTemp;
    
    @Column(name = "FLOWSHEET_TYPE")
    private String flowsheetType;

    // 流程图文件名
    @Column(name = "FILE_NAME")
    private String fileName;
    
    @Column(name = "FILE_ID")
    private Integer fileId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PowerTemp getPowerFlowsheetTemp() {
        return powerFlowsheetTemp;
    }

    public void setPowerFlowsheetTemp(PowerTemp powerFlowsheetTemp) {
        this.powerFlowsheetTemp = powerFlowsheetTemp;
    }

    public String getFlowsheetType() {
        return flowsheetType;
    }

    public void setFlowsheetType(String flowsheetType) {
        this.flowsheetType = flowsheetType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

   
}
