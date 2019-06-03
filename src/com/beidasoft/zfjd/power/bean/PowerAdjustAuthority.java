package com.beidasoft.zfjd.power.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_POWER_ADJUST_AUTHORITY")
public class PowerAdjustAuthority {
    
    // 主键
    @Id
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "ADJUST_ID")
    public PowerAdjust powerAdjust;
    
    @ManyToOne
    @JoinColumn(name = "POWER_ID")
    public PowerTemp powerTemp;
    
    @Column(name = "EXAMINE_STATE")
    public String examineState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PowerAdjust getPowerAdjust() {
        return powerAdjust;
    }

    public void setPowerAdjust(PowerAdjust powerAdjust) {
        this.powerAdjust = powerAdjust;
    }

    public PowerTemp getPowerTemp() {
        return powerTemp;
    }

    public void setPowerTemp(PowerTemp powerTemp) {
        this.powerTemp = powerTemp;
    }

    public String getExamineState() {
        return examineState;
    }

    public void setExamineState(String examineState) {
        this.examineState = examineState;
    }
    
    
}
