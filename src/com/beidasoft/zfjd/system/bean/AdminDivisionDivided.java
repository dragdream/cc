package com.beidasoft.zfjd.system.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 行政区划管理信息实体类
 */
@Entity
@Table(name="TBL_ADMIN_DIVISION_DIVIDED")
public class AdminDivisionDivided {
    // 系统字典表administrative_division行政区划字典
    @Id
    @Column(name = "ID")
    private String id;
    
    @Column(name = "ADMIN_DIVISION_CODE")
    private String adminDivisionCode;

    // 省級层级编码code
    @Column(name = "PROVINCIAL_CODE")
    private String provincialCode;

    // 市州級层级编码code
    @Column(name = "CITY_CODE")
    private String cityCode;

    // 区县层级code
    @Column(name = "DISTRICT_CODE")
    private String districtCode;

    // 乡镇街道层级code
    @Column(name = "STREET_CODE")
    private String streetCode;

    // 层级代码
    @Column(name = "LEVEL_CODE")
    private String levelCode;

    // 国家部委级code
    @Column(name = "COUNTRY_CODE")
    private String countryCode;

    // 省级层级名称
    @Column(name = "PROVINCIAL_NAME")
    private String provincialName;

    // 市级层级名称
    @Column(name = "CITY_NAME")
    private String cityName;

    // 区县层级名称
    @Column(name = "DISTRICT_NAME")
    private String districtName;

    // 乡镇街道名称
    @Column(name = "STREET_NAME")
    private String streetName;

    // 完整全称
    @Column(name = "FULL_NAME")
    private String fullName;

    // 区划名称
    @Column(name = "ADMIN_DIVISION_NAME")
    private String adminDivisionName;

    // 区划名称
    @Column(name = "IS_DELETE")
    private Integer isDelete;
    
    // 分配的分级管理员帐号
    @Column(name = "USER_ACCOUNT")
    private String userAccount;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdminDivisionCode() {
        return adminDivisionCode;
    }

    public void setAdminDivisionCode(String adminDivisionCode) {
        this.adminDivisionCode = adminDivisionCode;
    }

    public String getProvincialCode() {
        return provincialCode;
    }

    public void setProvincialCode(String provincialCode) {
        this.provincialCode = provincialCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getStreetCode() {
        return streetCode;
    }

    public void setStreetCode(String streetCode) {
        this.streetCode = streetCode;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getProvincialName() {
        return provincialName;
    }

    public void setProvincialName(String provincialName) {
        this.provincialName = provincialName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAdminDivisionName() {
        return adminDivisionName;
    }

    public void setAdminDivisionName(String adminDivisionName) {
        this.adminDivisionName = adminDivisionName;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
}
