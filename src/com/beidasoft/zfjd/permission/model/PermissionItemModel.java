package com.beidasoft.zfjd.permission.model;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: PermissionItemModel.java
 * @Description: 许可事项MODEL层
 *
 * @author: mixue
 * @date: 2019年2月26日 下午8:21:55
 */
public class PermissionItemModel {

    /** 数据唯一标识 */
    private String id;

    /** 事项编码 */
    private String xkSxbm;

    /** 事项名称 */
    private String xkSxmc;

    /** 大项名称 */
    private String xkDxmc;

    /** 归属单位 */
    private String xkGsdw;

    /** 归属单位 */
    private String xkGsdwName;

    /** 办件类型 code：01-即办件 02-承诺件 */
    private String xkBjlx;

    /** 办件类型 code：01-即办件 02-承诺件 */
    private String xkBjlxValue;

    /** 法定期限 */
    private Integer xkFdqx;

    /** 承诺期限 */
    private Integer xkCnqx;

    /** 受理标准 */
    private String xkSlbz;

    /** 审批流程 */
    private String xkSplc;

    /** 审查内容及标准 */
    private String xkScnrbz;

    /** 是否进驻大厅 1-是 0-否 */
    private String xkSfjzdt;

    /** 是否是固定资产 1-是 0-否 */
    private String xkSfgdzc;

    /** 事项结果类型 code：01通知书 02证照 03 批复 04批文 05其他 */
    private String xkSxjglx;

    /** 事项结果类型 code：01通知书 02证照 03 批复 04批文 05其他 */
    private String xkSxjglxValue;

    /** 行政相对人类型（code:01-自然人，02-法人，03-非法人组织，04-个体工商户） */
    private String xkXdrType;

    /** 行政相对人类型（code:01-自然人，02-法人，03-非法人组织，04-个体工商户） */
    private String xkXdrTypeValue;

    /** 备注 */
    private String xkBz;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 创建人ID */
    private Integer createPersonId;

    /** 创建人姓名 */
    private String createPersonName;

    /** 修改人ID */
    private Integer updatePersonId;

    /** 修改人姓名 */
    private String updatePersonName;

    /** 是否删除 1-已删除 0-未删除 */
    private Integer isDelete;

    /** 删除时间 */
    private Date deleteTime;
    
    /** 许可职权 */
    private String powerJsonStr;
    
    private List<PermissionItemPowerModel> powerList;
    
    /** 设定依据 */
    private String gistJsonStr;
    
    private List<PermissionItemGistModel> gistList;

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

    public String getXkGsdwName() {
        return xkGsdwName;
    }

    public void setXkGsdwName(String xkGsdwName) {
        this.xkGsdwName = xkGsdwName;
    }

    public String getXkBjlx() {
        return xkBjlx;
    }

    public void setXkBjlx(String xkBjlx) {
        this.xkBjlx = xkBjlx;
    }

    public String getXkBjlxValue() {
        return xkBjlxValue;
    }

    public void setXkBjlxValue(String xkBjlxValue) {
        this.xkBjlxValue = xkBjlxValue;
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

    public String getXkSxjglxValue() {
        return xkSxjglxValue;
    }

    public void setXkSxjglxValue(String xkSxjglxValue) {
        this.xkSxjglxValue = xkSxjglxValue;
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

    public String getXkXdrTypeValue() {
        return xkXdrTypeValue;
    }

    public void setXkXdrTypeValue(String xkXdrTypeValue) {
        this.xkXdrTypeValue = xkXdrTypeValue;
    }

    public String getPowerJsonStr() {
        return powerJsonStr;
    }

    public void setPowerJsonStr(String powerJsonStr) {
        this.powerJsonStr = powerJsonStr;
    }

    public String getGistJsonStr() {
        return gistJsonStr;
    }

    public void setGistJsonStr(String gistJsonStr) {
        this.gistJsonStr = gistJsonStr;
    }

    public List<PermissionItemPowerModel> getPowerList() {
        return powerList;
    }

    public void setPowerList(List<PermissionItemPowerModel> powerList) {
        this.powerList = powerList;
    }

    public List<PermissionItemGistModel> getGistList() {
        return gistList;
    }

    public void setGistList(List<PermissionItemGistModel> gistList) {
        this.gistList = gistList;
    }
    
    

}
