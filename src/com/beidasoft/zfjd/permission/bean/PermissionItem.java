package com.beidasoft.zfjd.permission.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @ClassName: PermissionItem.java
 * @Description: 许可事项
 *
 * @author: mixue
 * @date: 2019年2月26日 下午8:11:37
 */
@Entity
@Table(name = "JD_XK_PERMISSION_ITEM")
public class PermissionItem {

    /** 数据唯一标识 */
    @Column(name = "ID")
    @Id
    private String id;

    /** 事项编码 */
    @Column(name = "XK_SXBM")
    private String xkSxbm;

    /** 事项名称 */
    @Column(name = "XK_SXMC")
    private String xkSxmc;

    /** 大项名称 */
    @Column(name = "XK_DXMC")
    private String xkDxmc;

    /** 归属单位 */
    @Column(name = "XK_GSDW")
    private String xkGsdw;

    /** 办件类型 code：01-即办件 02-承诺件 */
    @Column(name = "XK_BJLX")
    private String xkBjlx;

    /** 法定期限 */
    @Column(name = "XK_FDQX")
    private Integer xkFdqx;

    /** 承诺期限 */
    @Column(name = "XK_CNQX")
    private Integer xkCnqx;

    /** 受理标准 */
    @Column(name = "XK_SLBZ")
    private String xkSlbz;

    /** 审批流程 */
    @Column(name = "XK_SPLC")
    private String xkSplc;

    /** 审查内容及标准 */
    @Column(name = "XK_SCNRBZ")
    private String xkScnrbz;

    /** 是否进驻大厅 1-是 0-否 */
    @Column(name = "XK_SFJZDT")
    private String xkSfjzdt;

    /** 是否是固定资产 1-是 0-否 */
    @Column(name = "XK_SFGDZC")
    private String xkSfgdzc;

    /** 事项结果类型 code：01通知书 02证照 03 批复 04批文 05其他 */
    @Column(name = "XK_SXJGLX")
    private String xkSxjglx;

    /** 行政相对人类型（code:01-自然人，02-法人，03-非法人组织，04-个体工商户） */
    @Column(name = "XK_XDR_TYPE")
    private String xkXdrType;

    /** 备注 */
    @Column(name = "XK_BZ")
    private String xkBz;

    /** 创建时间 */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /** 创建人ID */
    @Column(name = "CREATE_PERSON_ID")
    private Integer createPersonId;

    /** 创建人姓名 */
    @Column(name = "CREATE_PERSON_NAME")
    private String createPersonName;

    /** 修改人ID */
    @Column(name = "UPDATE_PERSON_ID")
    private Integer updatePersonId;

    /** 修改人姓名 */
    @Column(name = "UPDATE_PERSON_NAME")
    private String updatePersonName;

    /** 是否删除 1-已删除 0-未删除 */
    @Column(name = "IS_DELETE")
    private Integer isDelete;

    /** 删除时间 */
    @Column(name = "DELETE_TIME")
    private Date deleteTime;

    /** 事项关联职权子表 */
    @OneToMany(mappedBy = "permissionItem", fetch = FetchType.LAZY) // 双向,目标写@JoinColumn
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<PermissionItemPower> permissionItemPowers;

    /** 事项办理依据子表 */
    @OneToMany(mappedBy = "permissionItem", fetch = FetchType.LAZY) // 双向,目标写@JoinColumn
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<PermissionItemGist> permissionItemGists;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getXkSxbm() {
        return xkSxbm;
    }

    public void setXkSxbm(String xkSxbm) {
        this.xkSxbm = xkSxbm;
    }

    public String getXkSxmc() {
        return xkSxmc;
    }

    public void setXkSxmc(String xkSxmc) {
        this.xkSxmc = xkSxmc;
    }

    public String getXkDxmc() {
        return xkDxmc;
    }

    public void setXkDxmc(String xkDxmc) {
        this.xkDxmc = xkDxmc;
    }

    public String getXkGsdw() {
        return xkGsdw;
    }

    public void setXkGsdw(String xkGsdw) {
        this.xkGsdw = xkGsdw;
    }

    public String getXkBjlx() {
        return xkBjlx;
    }

    public void setXkBjlx(String xkBjlx) {
        this.xkBjlx = xkBjlx;
    }

    public Integer getXkFdqx() {
        return xkFdqx;
    }

    public void setXkFdqx(Integer xkFdqx) {
        this.xkFdqx = xkFdqx;
    }

    public Integer getXkCnqx() {
        return xkCnqx;
    }

    public void setXkCnqx(Integer xkCnqx) {
        this.xkCnqx = xkCnqx;
    }

    public String getXkSlbz() {
        return xkSlbz;
    }

    public void setXkSlbz(String xkSlbz) {
        this.xkSlbz = xkSlbz;
    }

    public String getXkSplc() {
        return xkSplc;
    }

    public void setXkSplc(String xkSplc) {
        this.xkSplc = xkSplc;
    }

    public String getXkScnrbz() {
        return xkScnrbz;
    }

    public void setXkScnrbz(String xkScnrbz) {
        this.xkScnrbz = xkScnrbz;
    }

    public String getXkSfjzdt() {
        return xkSfjzdt;
    }

    public void setXkSfjzdt(String xkSfjzdt) {
        this.xkSfjzdt = xkSfjzdt;
    }

    public String getXkSfgdzc() {
        return xkSfgdzc;
    }

    public void setXkSfgdzc(String xkSfgdzc) {
        this.xkSfgdzc = xkSfgdzc;
    }

    public String getXkSxjglx() {
        return xkSxjglx;
    }

    public void setXkSxjglx(String xkSxjglx) {
        this.xkSxjglx = xkSxjglx;
    }

    public String getXkBz() {
        return xkBz;
    }

    public void setXkBz(String xkBz) {
        this.xkBz = xkBz;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(Integer createPersonId) {
        this.createPersonId = createPersonId;
    }

    public String getCreatePersonName() {
        return createPersonName;
    }

    public void setCreatePersonName(String createPersonName) {
        this.createPersonName = createPersonName;
    }

    public Integer getUpdatePersonId() {
        return updatePersonId;
    }

    public void setUpdatePersonId(Integer updatePersonId) {
        this.updatePersonId = updatePersonId;
    }

    public String getUpdatePersonName() {
        return updatePersonName;
    }

    public void setUpdatePersonName(String updatePersonName) {
        this.updatePersonName = updatePersonName;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getXkXdrType() {
        return xkXdrType;
    }

    public void setXkXdrType(String xkXdrType) {
        this.xkXdrType = xkXdrType;
    }

    public List<PermissionItemPower> getPermissionItemPowers() {
        return permissionItemPowers;
    }

    public void setPermissionItemPowers(List<PermissionItemPower> permissionItemPowers) {
        this.permissionItemPowers = permissionItemPowers;
    }

    public List<PermissionItemGist> getPermissionItemGists() {
        return permissionItemGists;
    }

    public void setPermissionItemGists(List<PermissionItemGist> permissionItemGists) {
        this.permissionItemGists = permissionItemGists;
    }

}
