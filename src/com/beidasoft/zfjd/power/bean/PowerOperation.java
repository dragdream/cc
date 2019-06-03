package com.beidasoft.zfjd.power.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.alibaba.druid.stat.TableStat.Name;

@Entity
@Table(name = "TBL_POWER_OPERATION")
public class PowerOperation {
    
    @Id
    @Column(name = "ID")
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "POWER_TEMP_ID") // 这是双向的 要写JoinColumn 对面要写 mappedBy
    private PowerTemp powerOptTemp;
    
    @ManyToOne
    @JoinColumn(name = "POWER_FORMAL_ID") // 这是双向的 要写JoinColumn 对面要写 mappedBy
    private Power powerOpt;
    
    @Column(name = "OPT_TYPE")
    private String optType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PowerTemp getPowerOptTemp() {
        return powerOptTemp;
    }

    public void setPowerOptTemp(PowerTemp powerOptTemp) {
        this.powerOptTemp = powerOptTemp;
    }

    public Power getPowerOpt() {
        return powerOpt;
    }

    public void setPowerOpt(Power powerOpt) {
        this.powerOpt = powerOpt;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }
    
    
}
