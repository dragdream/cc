package com.beidasoft.zfjd.power.bean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.beidasoft.zfjd.subject.bean.Subject;

@Entity
@Table(name = "TBL_POWER_SUBJECT_TEMP")
public class PowerSubjectTemp {
    // 主键
    @Id
    private String id;
    
    // 职权ID
    @ManyToOne
    @JoinColumn(name = "POWER_ID") // 这是双向的 要写JoinColumn 对面要写 mappedBy
    private PowerTemp powerSubjectTemp;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBJECT_ID")
    private Subject subject;        // 实施主体信息
    
    @Column(name = "SUBJECT_NAME")
    private String subjectName;     //实施主体名称
    
    @Column(name = "CREATE_DATE")
    private Date createDate;    //创建日期
    
    @Column(name = "IS_DELETE")
    private int isDelete;           //删除标志
    
    @Column(name = "IS_DEPUTE")
    private Integer isDepute;       //是否委托组织
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PowerTemp getPowerSubjectTemp() {
        return powerSubjectTemp;
    }

    public void setPowerSubjectTemp(PowerTemp powerSubjectTemp) {
        this.powerSubjectTemp = powerSubjectTemp;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getIsDepute() {
        return isDepute;
    }

    public void setIsDepute(Integer isDepute) {
        this.isDepute = isDepute;
    }

    
}
