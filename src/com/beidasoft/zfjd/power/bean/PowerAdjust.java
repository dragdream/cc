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
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.beidasoft.zfjd.subject.bean.Subject;
import com.beidasoft.zfjd.department.bean.TblDepartmentInfo;
import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name = "TBL_POWER_ADJUST")
public class PowerAdjust {
    // 主键
    @Id
    @Column(name = "ID")
    private String id;
    
    // 批次号
    @Column(name = "BATCH_CODE")
    private String batchCode;
    
    // 创建人
    @ManyToOne
    @JoinColumn(name = "CREATE_ID")
    private TeePerson user;
    
    // 创建日期
    @Column(name = "CREATE_DATE")
    private Date createDate;
    
    // 提交主体
    @ManyToOne
    @JoinColumn(name = "SUBMIT_DEPARTMENT")
    private TblDepartmentInfo submitDepartment;
    
    // 本批次调整总数
    @Column(name = "EXAMINE_SUM")
    private Integer examineSum;
    
    // 调整原因
    @Column(name = "ADJUST_REASON")
    private String adjustReason;
    
    // 绑定流程ID
    @Column(name = "RUN_ID")
    private int runId;
    
    // 当前状态
    @Column(name = "CURRENT_STATUS")
    private String currentStatus;
    
    @OneToMany(mappedBy = "powerAdjust", fetch = FetchType.LAZY) // 双向,  目标写@JoinColumn 
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<PowerAdjustAuthority> adjustAuthorities;
    
    @OneToMany(mappedBy = "powerAdjustTache", fetch = FetchType.LAZY) // 双向,  目标写@JoinColumn 
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OrderBy("tacheId DESC")
    private List<PowerAdjustTache> adjustTaches;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public TeePerson getUser() {
        return user;
    }

    public void setUser(TeePerson user) {
        this.user = user;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public TblDepartmentInfo getSubmitDepartment() {
        return submitDepartment;
    }

    public void setSubmitDepartment(TblDepartmentInfo submitDepartment) {
        this.submitDepartment = submitDepartment;
    }

    public Integer getExamineSum() {
        return examineSum;
    }

    public void setExamineSum(Integer examineSum) {
        this.examineSum = examineSum;
    }

    public String getAdjustReason() {
        return adjustReason;
    }

    public void setAdjustReason(String adjustReason) {
        this.adjustReason = adjustReason;
    }

    public List<PowerAdjustAuthority> getAdjustAuthorities() {
        return adjustAuthorities;
    }

    public void setAdjustAuthorities(List<PowerAdjustAuthority> adjustAuthorities) {
        this.adjustAuthorities = adjustAuthorities;
    }

    public List<PowerAdjustTache> getAdjustTaches() {
        return adjustTaches;
    }

    public void setAdjustTaches(List<PowerAdjustTache> adjustTaches) {
        this.adjustTaches = adjustTaches;
    }

    public int getRunId() {
        return runId;
    }

    public void setRunId(int runId) {
        this.runId = runId;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }
    
}
