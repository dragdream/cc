package com.beidasoft.zfjd.law.bean;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 法律法规表实体类
 */
@Entity
@Table(name="TBL_BASE_LAW")
public class TblLawInfo{
    // 法律法规id
    @Id
    @Column(name = "ID")
    private String id;

    
    // 名称
    @Column(name = "NAME")
    private String name;

 // 时效性 01现行有效 02失效
    @Column(name = "TIMELINESS")
    private String timeliness;

    // 法律法规类别
    @Column(name = "SUBMITLAW_LEVEL")
    private String submitlawLevel;

    // 发文字号
    @Column(name = "WORD")
    private String word;

    // 发布机关
    @Column(name = "ORGAN")
    private String organ;

    // 颁布日期
    @Column(name = "PROMULGATION",length=10)
    private Date promulgation;

    // 实施日期
    @Column(name = "IMPLEMENTATION",length=10)
    private Date implementation;

    // 备注
    @Column(name = "REMARK")
    private String remark;

    // 审核状态
    @Column(name = "EXAMINE")
    private Integer examine;

    // 是否删除（停用）
    @Column(name = "IS_DELETE")
    private Integer isDelete;

    // 创建者
    @Column(name = "CREATE_ID")
    private String createId;

    // 创建时间
    @Column(name = "CREATE_DATE")
    private Date createDate;

    // 法律原文文件存储路径
    @Column(name = "LAW_PATH")
    private String lawPath;

    // 法律编号
    @Column(name = "LAW_NUM")
    private String lawNum;
    
    // 法律编号
    @Column(name = "ORDER_INEDX")
    private String orderInedx;
    
    // 法律编号
    @Column(name = "CONTROL_TYPE")
    private String controlType;
    
	// 法律编号
    @Column(name = "UPDATE_LAW_ID")
    private String updateLawId;
    

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

	public String getSubmitlawLevel() {
		return submitlawLevel;
	}

	public void setSubmitlawLevel(String submitlawLevel) {
		this.submitlawLevel = submitlawLevel;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOrderInedx() {
		return orderInedx;
	}

	public void setOrderInedx(String orderInedx) {
		this.orderInedx = orderInedx;
	}

	public String getControlType() {
		return controlType;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	public String getUpdateLawId() {
		return updateLawId;
	}

	public void setUpdateLawId(String updateLawId) {
		this.updateLawId = updateLawId;
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

	public Integer getExamine() {
		return examine;
	}

	public void setExamine(Integer examine) {
		this.examine = examine;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

}
