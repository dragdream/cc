package com.beidasoft.zfjd.power.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_POWER_ADJUST_TACHE_INFO")
public class PowerAdjustTacheInfo {
    
    @Id
    @Column(name = "ID")
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "TACHE_ID")
    private PowerAdjustTache adjustTache;
    
    @ManyToOne
    @JoinColumn(name = "ADJUST_ID")
    private PowerAdjust adjustTacheInfo;
    
    @ManyToOne
    @JoinColumn(name = "POWER_ID")
    private PowerTemp powerTemp;
    
    @Column(name = "EXAMINE_STATE")
    private String examineState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public PowerAdjustTache getAdjustTache() {
        return adjustTache;
    }

    public void setAdjustTache(PowerAdjustTache adjustTache) {
        this.adjustTache = adjustTache;
    }

    public PowerAdjust getAdjustTacheInfo() {
        return adjustTacheInfo;
    }

    public void setAdjustTacheInfo(PowerAdjust adjustTacheInfo) {
        this.adjustTacheInfo = adjustTacheInfo;
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
