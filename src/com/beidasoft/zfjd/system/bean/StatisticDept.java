package com.beidasoft.zfjd.system.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 部门信息数量统计表实体类
 */
@Entity
@Table(name="JD_STATISTIC_DEPT_HIGH_SEARCH")
public class StatisticDept {
    // 主键
	@Id
    @Column(name = "id")
    private String id;

    // 部门id
    @Column(name = "dept_id")
    private String deptId;

    // 职权数量
    @Column(name = "power_no")
    private Integer powerNo;

    // 执法人员数量
    @Column(name = "person_no")
    private Integer personNo;

    // 执法主体数量
    @Column(name = "sub_no")
    private Integer subNo;

    // 委托组织数量
    @Column(name = "org_no")
    private Integer orgNo;

    // 删除标志0未删除1已删除
    @Column(name = "is_delete")
    private Integer isDelete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public Integer getPowerNo() {
        return powerNo;
    }

    public void setPowerNo(Integer powerNo) {
        this.powerNo = powerNo;
    }

    public Integer getPersonNo() {
        return personNo;
    }

    public void setPersonNo(Integer personNo) {
        this.personNo = personNo;
    }

    public Integer getSubNo() {
        return subNo;
    }

    public void setSubNo(Integer subNo) {
        this.subNo = subNo;
    }

    public Integer getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(Integer orgNo) {
        this.orgNo = orgNo;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

}
