package com.beidasoft.xzzf.law.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 法律法规ID实体类
 */
@Entity
@Table(name="FX_BASE_LAW")
public class BaseLaw {
    @Id
    @Column(name = "ID")
    private String id; // 法律法规ID

    @Column(name = "NAME")
    private String name; // 名称

    @Column(name = "TIMELINESS")
    private String timeliness; // 时效性

    @Column(name = "SUBMITLAW_LEVEL")
    private String submitlawLevel; // 法律法规类别

    @Column(name = "WORD")
    private String word; // 发文字号

    @Column(name = "ORGAN")
    private String organ; // 发布机关

    @Column(name = "PROMULGATION")
    private Date promulgation; // 颁布日期

    @Column(name = "IMPLEMENTATION")
    private Date implementation; // 实施日期

    @Column(name = "REMARK")
    private String remark; // 备注

    @Column(name = "EXAMINE")
    private String examine; // 审核状态

    @Column(name = "IS_DELETE")
    private String isDelete; // 是否删除

    @Column(name = "CREATE_ID")
    private String createId; // 创建者

    @Column(name = "CREATE_TIME")
    private String createTime; // 创建时间

    @Column(name = "LAW_PATH")
    private String lawPath; // 法律原文文件路径

    @Column(name = "LAW_NUM")
    private String lawNum; // 法律编号

    @Column(name = "ANNUL_TIME")
    private Date annulTime; // 废止日期

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeliness() {
        return timeliness;
    }

    public void setTimeliness(String timeliness) {
        this.timeliness = timeliness;
    }

    public String getSubmitlawLevel () {
        return submitlawLevel ;
    }

    public void setSubmitlawLevel (String submitlawLevel ) {
        this.submitlawLevel  = submitlawLevel ;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getOrgan () {
        return organ ;
    }

    public void setOrgan (String organ ) {
        this.organ  = organ ;
    }

    public Date getPromulgation() {
        return promulgation;
    }

    public void setPromulgation(Date promulgation) {
        this.promulgation = promulgation;
    }

    public Date getImplementation() {
        return implementation;
    }

    public void setImplementation(Date implementation) {
        this.implementation = implementation;
    }

    public String getRemark () {
        return remark ;
    }

    public void setRemark (String remark ) {
        this.remark  = remark ;
    }

    public String getExamine() {
        return examine;
    }

    public void setExamine(String examine) {
        this.examine = examine;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLawPath() {
        return lawPath;
    }

    public void setLawPath(String lawPath) {
        this.lawPath = lawPath;
    }

    public String getLawNum() {
        return lawNum;
    }

    public void setLawNum(String lawNum) {
        this.lawNum = lawNum;
    }

    public Date getAnnulTime() {
        return annulTime;
    }

    public void setAnnulTime(Date annulTime) {
        this.annulTime = annulTime;
    }

}
