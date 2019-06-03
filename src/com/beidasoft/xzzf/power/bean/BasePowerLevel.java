package com.beidasoft.xzzf.power.bean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 职权层级表实体类
 */
@Entity
@Table(name="FX_BASE_POWER_LEVEL ")
public class BasePowerLevel  {
    @Id
    @Column(name = "ID")
    private String id; // 关联表主键ID

    @Column(name = "POWER_ID")
    private String powerId; // 职权ID

    @Column(name = "POWER_LEVEL")
    private String powerLevel; // 职权层级

    @Column(name = "REMARK")
    private String remark;  // 职权分级权限划分标准

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

    public String getPowerLevel() {
        return powerLevel;
    }

    public void setPowerLevel(String powerLevel) {
        this.powerLevel = powerLevel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
