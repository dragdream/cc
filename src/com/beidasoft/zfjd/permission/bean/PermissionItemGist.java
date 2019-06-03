package com.beidasoft.zfjd.permission.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @ClassName: PermissionItemGist.java
 * @Description: 许可事项关联依据BEAN层
 *
 * @author: mixue
 * @date: 2019年3月6日 下午4:24:52
 */
@Entity
@Table(name = "JD_XK_PERMISSION_ITEM_GIST")
public class PermissionItemGist {

    // 主键
    @Id
    @Column(name = "ID")
    private String id;

    // 事项ID
    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private PermissionItem permissionItem;

    // 依据ID
    @Column(name = "GIST_ID")
    private String gistId;

    // 法律名称
    @Column(name = "LAW_NAME")
    private String lawName;

    // 条
    @Column(name = "STRIP")
    private Integer strip;

    // 款
    @Column(name = "FUND")
    private Integer fund;

    // 项
    @Column(name = "ITEM")
    private Integer item;

    // 目
    @Column(name = "GIST_CATALOG")
    private Integer gistCatalog;

    // 内容
    @Column(name = "CONTENT")
    private String content;

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

}
