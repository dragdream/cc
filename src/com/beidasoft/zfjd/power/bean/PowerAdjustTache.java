package com.beidasoft.zfjd.power.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name = "TBL_POWER_ADJUST_TACHE")
public class PowerAdjustTache {
    
    @Id
    @Column(name = "ID")
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "ADJUST_ID")
    private PowerAdjust powerAdjustTache;
    
    @Column(name = "CLOSED_DATE")
    private Date closedDate;
    
    @ManyToOne
    @JoinColumn(name = "EXAMINE_USER")
    private TeePerson examinePerson;
    
    // 环节序号
    @Column(name = "TACHE_ID")
    private Integer tacheId;
    
    // 环节名称
    @Column(name = "TACHE_NAME")
    private String tacheName;
    
    // 审核日期
    @Column(name = "EXAMINE_DATE")
    private Date examineDate;
    
    // 审核意见
    @Column(name = "EXAMINE_VIEW")
    private String examineView;
    
    @OneToMany(mappedBy = "adjustTache", fetch = FetchType.LAZY) // 双向,  目标写@JoinColumn 
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<PowerAdjustTacheInfo> adjustTacheInfos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PowerAdjust getPowerAdjustTache() {
        return powerAdjustTache;
    }

    public void setPowerAdjustTache(PowerAdjust powerAdjustTache) {
        this.powerAdjustTache = powerAdjustTache;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public TeePerson getExaminePerson() {
        return examinePerson;
    }

    public void setExaminePerson(TeePerson examinePerson) {
        this.examinePerson = examinePerson;
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

    public List<PowerAdjustTacheInfo> getAdjustTacheInfos() {
        return adjustTacheInfos;
    }

    public void setAdjustTacheInfos(List<PowerAdjustTacheInfo> adjustTacheInfos) {
        this.adjustTacheInfos = adjustTacheInfos;
    }

    public Date getExamineDate() {
        return examineDate;
    }

    public void setExamineDate(Date examineDate) {
        this.examineDate = examineDate;
    }

    public String getExamineView() {
        return examineView;
    }

    public void setExamineView(String examineView) {
        this.examineView = examineView;
    }

    
}
