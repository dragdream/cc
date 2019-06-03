package com.beidasoft.zfjd.permission.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName: PermissionList.java
 * @Description: 许可清单
 *
 * @author: mixue
 * @date: 2019年2月21日 下午3:23:15
 */
@Entity
@Table(name = "JD_XK_PERMISSION_LIST")
public class PermissionList {

    /** 数据唯一标识 */
    @Column(name = "ID")
    @Id
    private String id;

    /** 行政相对人名称 */
    @Column(name = "XK_XDR_MC")
    private String xkXdrMc;

    /** 行政相对人类型（code:01-自然人，02-法人，03-非法人组织，04-个体工商户） */
    @Column(name = "XK_XDR_TYPE")
    private String xkXdrType;

    /** 行政相对人代码_1(统一社会信用代码) */
    @Column(name = "XK_XDR_SHXYM")
    private String xkXdrShxym;

    /** 行政相对人代码_2 (工商注册号) */
    @Column(name = "XK_XDR_GSZC")
    private String xkXdrGszc;

    /** 行政相对人代码_3(组织机构代码) */
    @Column(name = "XK_XDR_ZZJG")
    private String xkXdrZzjg;

    /** 行政相对人代码_4(税务登记号) */
    @Column(name = "XK_XDR_SWDJ")
    private String xkXdrSwdj;

    /** 行政相对人代码_5(事业单位证书号) */
    @Column(name = "XK_XDR_SYDW")
    private String xkXdrSydw;

    /** 行政相对人代码_6(社会组织登记证号) */
    @Column(name = "XK_XDR_SHZZ")
    private String xkXdrShzz;

    /** 法定代表人 */
    @Column(name = "XK_FRDB")
    private String xkFrdb;

    /** 法定代表人身份证号 */
    @Column(name = "XK_FR_SFZH")
    private String xkFrSfzh;

    /**
     * 自然人证件类型（code:01-身份证 02-护照 03-港澳居民来往内地通行证 04-台湾居民来往大陆通行证 05-外国人永久居留身份证）
     */
    @Column(name = "XK_XDR_ZJLX")
    private String xkXdrZjlx;

    /** 证件号码 */
    @Column(name = "XK_XDR_ZJHM")
    private String xkXdrZjhm;

    /** 行政许可决定文书名称 */
    @Column(name = "XK_XKWS")
    private String xkXkws;

    /** 行政许可决定文书号 */
    @Column(name = "XK_WSH")
    private String xkWsh;

    /** 许可类别（code:01-普通 02-特许 03-认可 04-核准 05-登记 06-其他） */
    @Column(name = "XK_XKLB")
    private String xkXklb;

    /** 许可证书名称 */
    @Column(name = "XK_XKZS")
    private String xkXkzs;

    /** 许可编号 */
    @Column(name = "XK_XKBH")
    private String xkXkbh;

    /** 许可内容 */
    @Column(name = "XK_NR")
    private String xkNr;

    /** 许可决定日期 */
    @Column(name = "XK_JDRQ")
    private Date xkJdrq;

    /** 有效期自 */
    @Column(name = "XK_YXQZ")
    private Date xkYxqz;

    /** 有效期至 */
    @Column(name = "XK_YXQZI")
    private Date xkYxqzi;

    /** 许可部门id */
    @Column(name = "XK_XKJG")
    private String xkXkjg;

    /** 许可部门统一社会信用代码 */
    @Column(name = "XK_XKJGDM")
    private String xkXkjgdm;

    /** 当前状态 */
    @Column(name = "XK_ZT")
    private String xkZt;

    /** 数据创建时间（yyyy/mm/dd hh24:mi:ss） */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /** 数据更新时间（yyyy/mm/dd hh24:mi:ss） */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /** 数据创建用户ID */
    @Column(name = "CREATE_PERSON_ID")
    private Integer createPersonId;

    /** 数据更新用户名称 */
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

    /** 事项ID */
    @Column(name = "XK_ITEM_ID")
    private String xkItemId;

    /** 事项编码 */
    @Column(name = "XK_ITEM_BM")
    private String xkItemBm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getXkXdrMc() {
        return xkXdrMc;
    }

    public void setXkXdrMc(String xkXdrMc) {
        this.xkXdrMc = xkXdrMc;
    }

    public String getXkXdrType() {
        return xkXdrType;
    }

    public void setXkXdrType(String xkXdrType) {
        this.xkXdrType = xkXdrType;
    }

    public String getXkXdrShxym() {
        return xkXdrShxym;
    }

    public void setXkXdrShxym(String xkXdrShxym) {
        this.xkXdrShxym = xkXdrShxym;
    }

    public String getXkXdrGszc() {
        return xkXdrGszc;
    }

    public void setXkXdrGszc(String xkXdrGszc) {
        this.xkXdrGszc = xkXdrGszc;
    }

    public String getXkXdrZzjg() {
        return xkXdrZzjg;
    }

    public void setXkXdrZzjg(String xkXdrZzjg) {
        this.xkXdrZzjg = xkXdrZzjg;
    }

    public String getXkXdrSwdj() {
        return xkXdrSwdj;
    }

    public void setXkXdrSwdj(String xkXdrSwdj) {
        this.xkXdrSwdj = xkXdrSwdj;
    }

    public String getXkXdrSydw() {
        return xkXdrSydw;
    }

    public void setXkXdrSydw(String xkXdrSydw) {
        this.xkXdrSydw = xkXdrSydw;
    }

    public String getXkXdrShzz() {
        return xkXdrShzz;
    }

    public void setXkXdrShzz(String xkXdrShzz) {
        this.xkXdrShzz = xkXdrShzz;
    }

    public String getXkFrdb() {
        return xkFrdb;
    }

    public void setXkFrdb(String xkFrdb) {
        this.xkFrdb = xkFrdb;
    }

    public String getXkFrSfzh() {
        return xkFrSfzh;
    }

    public void setXkFrSfzh(String xkFrSfzh) {
        this.xkFrSfzh = xkFrSfzh;
    }

    public String getXkXdrZjlx() {
        return xkXdrZjlx;
    }

    public void setXkXdrZjlx(String xkXdrZjlx) {
        this.xkXdrZjlx = xkXdrZjlx;
    }

    public String getXkXdrZjhm() {
        return xkXdrZjhm;
    }

    public void setXkXdrZjhm(String xkXdrZjhm) {
        this.xkXdrZjhm = xkXdrZjhm;
    }

    public String getXkXkws() {
        return xkXkws;
    }

    public void setXkXkws(String xkXkws) {
        this.xkXkws = xkXkws;
    }

    public String getXkWsh() {
        return xkWsh;
    }

    public void setXkWsh(String xkWsh) {
        this.xkWsh = xkWsh;
    }

    public String getXkXklb() {
        return xkXklb;
    }

    public void setXkXklb(String xkXklb) {
        this.xkXklb = xkXklb;
    }

    public String getXkXkzs() {
        return xkXkzs;
    }

    public void setXkXkzs(String xkXkzs) {
        this.xkXkzs = xkXkzs;
    }

    public String getXkXkbh() {
        return xkXkbh;
    }

    public void setXkXkbh(String xkXkbh) {
        this.xkXkbh = xkXkbh;
    }

    public String getXkNr() {
        return xkNr;
    }

    public void setXkNr(String xkNr) {
        this.xkNr = xkNr;
    }

    public Date getXkJdrq() {
        return xkJdrq;
    }

    public void setXkJdrq(Date xkJdrq) {
        this.xkJdrq = xkJdrq;
    }

    public Date getXkYxqz() {
        return xkYxqz;
    }

    public void setXkYxqz(Date xkYxqz) {
        this.xkYxqz = xkYxqz;
    }

    public Date getXkYxqzi() {
        return xkYxqzi;
    }

    public void setXkYxqzi(Date xkYxqzi) {
        this.xkYxqzi = xkYxqzi;
    }

    public String getXkXkjg() {
        return xkXkjg;
    }

    public void setXkXkjg(String xkXkjg) {
        this.xkXkjg = xkXkjg;
    }

    public String getXkXkjgdm() {
        return xkXkjgdm;
    }

    public void setXkXkjgdm(String xkXkjgdm) {
        this.xkXkjgdm = xkXkjgdm;
    }

    public String getXkZt() {
        return xkZt;
    }

    public void setXkZt(String xkZt) {
        this.xkZt = xkZt;
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

    public String getXkItemId() {
        return xkItemId;
    }

    public void setXkItemId(String xkItemId) {
        this.xkItemId = xkItemId;
    }

    public String getXkItemBm() {
        return xkItemBm;
    }

    public void setXkItemBm(String xkItemBm) {
        this.xkItemBm = xkItemBm;
    }

}