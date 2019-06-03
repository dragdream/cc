package com.beidasoft.zfjd.system.model;

/**
 * 部门信息数量统计表MODEL类
 */
public class StatisticDeptModel {
    // 主键
    private String id;

    // 部门id
    private String deptId;

    // 职权数量
    private Integer powerNo;

    // 执法人员数量
    private Integer personNo;

    // 执法主体数量
    private Integer subNo;

    // 委托组织数量
    private Integer orgNo;

    // 删除标志0未删除1已删除
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

    public int getPowerNo() {
        return powerNo;
    }

    public void setPowerNo(int powerNo) {
        this.powerNo = powerNo;
    }

    public int getPersonNo() {
        return personNo;
    }

    public void setPersonNo(int personNo) {
        this.personNo = personNo;
    }

    public int getSubNo() {
        return subNo;
    }

    public void setSubNo(int subNo) {
        this.subNo = subNo;
    }

    public int getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(int orgNo) {
        this.orgNo = orgNo;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

}
