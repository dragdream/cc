package com.beidasoft.zfjd.permission.model;

import java.util.Date;

/**
 * @ClassName: PermissionItemPowerModel.java
 * @Description: 许可事项关联职权MODEL层
 *
 * @author: mixue
 * @date: 2019年3月6日 下午4:24:04
 */
public class PermissionItemPowerModel {

    // 主键
    private String id;

    // 事项ID
    private String itemId;

    // 职权ID
    private String powerId;

    // 职权编号
    private String powerCode;

    // 职权名称
    private String powerName;

    // 入库日期
    private Date createDate;

    // 入库日期
    private String createDateStr;

    private PermissionItemModel permissionItemModel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public PermissionItemModel getPermissionItemModel() {
        return permissionItemModel;
    }

    public void setPermissionItemModel(PermissionItemModel permissionItemModel) {
        this.permissionItemModel = permissionItemModel;
    }

}
