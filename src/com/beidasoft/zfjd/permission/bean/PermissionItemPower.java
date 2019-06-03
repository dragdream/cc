package com.beidasoft.zfjd.permission.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @ClassName: PermissionItemPower.java
 * @Description: 许可事项关联职权BEAN层
 *
 * @author: mixue
 * @date: 2019年3月6日 下午4:23:33
 */
@Entity
@Table(name = "JD_XK_PERMISSION_ITEM_POWER")
public class PermissionItemPower {

    // 主键
    @Id
    @Column(name = "ID")
    private String id;

    // 事项ID
    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private PermissionItem permissionItem;

    // 职权ID
    @Column(name = "POWER_ID")
    private String powerId;

    // 职权编号
    @Column(name = "POWER_CODE")
    private String powerCode;

    // 职权名称
    @Column(name = "POWER_NAME")
    private String powerName;

    // 入库日期
    @Column(name = "CREATE_DATE")
    private Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PermissionItem getPermissionItem() {
        return permissionItem;
    }

    public void setPermissionItem(PermissionItem permissionItem) {
        this.permissionItem = permissionItem;
    }

    public String getPowerId() {
        return powerId;
    }

    public void setPowerId(String powerId) {
        this.powerId = powerId;
    }

    public String getPowerCode() {
        return powerCode;
    }

    public void setPowerCode(String powerCode) {
        this.powerCode = powerCode;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
