package com.beidasoft.zfjd.permission.model;

import java.util.Date;

/**
 * @ClassName: PermissionItemGistModel.java
 * @Description:许可事项关联依据MODEL层
 *
 * @author: mixue
 * @date: 2019年3月6日 下午4:25:11
 */
public class PermissionItemGistModel {

    // 主键
    private String id;

    // 事项ID
    private String itemId;

    // 依据ID
    private String gistId;

    // 法律名称
    private String lawName;

    // 条
    private Integer strip;

    // 款
    private Integer fund;

    // 项
    private Integer item;

    // 目
    private Integer gistCatalog;

    // 内容
    private String content;

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

    public String getGistId() {
        return gistId;
    }

    public void setGistId(String gistId) {
        this.gistId = gistId;
    }

    public String getLawName() {
        return lawName;
    }

    public void setLawName(String lawName) {
        this.lawName = lawName;
    }

    public Integer getStrip() {
        return strip;
    }

    public void setStrip(Integer strip) {
        this.strip = strip;
    }

    public Integer getFund() {
        return fund;
    }

    public void setFund(Integer fund) {
        this.fund = fund;
    }

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    public Integer getGistCatalog() {
        return gistCatalog;
    }

    public void setGistCatalog(Integer gistCatalog) {
        this.gistCatalog = gistCatalog;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
