package com.beidasoft.zfjd.power.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.beidasoft.zfjd.power.bean.Power;

@Entity
@Table(name = "TBL_POWER_DISCRETIONARY")
public class PowerDiscretionary {
    // 主键
    @Id
    @Column(name = "ID")
    private String id;
    
    // 职权ID
    @ManyToOne
    @JoinColumn(name = "POWER_ID")
    private Power powerDeiscretionary;
    
    // 违法事实
    @Column(name = "ILLEGAL_FACT")
    private String illegalFact;
    
    // 处罚标准
    @Column(name = "PUNISH_STANDARD")
    private String punishStandard;
    
    // 创建日期
    @Column(name = "CREATE_DATE")
    private Date createDate;
    
    // 更新日期
    @Column(name = "UPDATE_DATE")
    private Date updateDate;
    
    // 删除标志
    @Column(name = "IS_DELETE")    
    private int isDelete;
    
    // 管理主体
    @Column(name = "SUBJECT_ID")
    private String subjectId;
    
    // 实施主体
    @Column(name = "SUBJECT_ACT_ID")
    private String subjectActId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Power getPowerDeiscretionary() {
        return powerDeiscretionary;
    }

    public void setPowerDeiscretionary(Power powerDeiscretionary) {
        this.powerDeiscretionary = powerDeiscretionary;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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
