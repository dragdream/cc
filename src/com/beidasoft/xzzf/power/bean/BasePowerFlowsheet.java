package com.beidasoft.xzzf.power.bean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 职权流程图表实体类
 */
@Entity
@Table(name="FX_BASE_POWER_FLOWSHEET")
public class BasePowerFlowsheet {
    @Id
    @Column(name = "ID")
    private String id; // 主键

    @Column(name = "POWER_ID")
    private String powerId; // 职权ID

    @Column(name = "FLOW_PICTURE_PATH")
    private String flowPicturePath; // 职权流程图路径

    @Column(name = "FILE_NAME")
    private String fileName; // 职权流程图名称

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

    public String getFlowPicturePath() {
        return flowPicturePath;
    }

    public void setFlowPicturePath(String flowPicturePath) {
        this.flowPicturePath = flowPicturePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
