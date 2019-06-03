package com.beidasoft.zfjd.system.model;

import javax.management.loading.PrivateClassLoader;

/**
 * 行政区划管理信息MODEL类
 */
public class AdminDivisionDividedModel {
    
    private String id;
    // 系统字典表administrative_division行政区划字典
    private String adminDivisionCode;

    // 省級层级编码code
    private String provincialCode;

    // 市州級层级编码code
    private String cityCode;

    // 区县层级code
    private String districtCode;

    // 乡镇街道层级code
    private String streetCode;

    // 层级代码
    private String levelCode;
    
    // 层级代码名称
    private String levelCodeStr;

    // 国家部委级code
    private String countryCode;

    // 省级层级名称
    private String provincialName;

    // 市级层级名称
    private String cityName;

    // 区县层级名称
    private String districtName;

    // 乡镇街道名称
    private String streetName;

    // 完整全称
    private String fullName;

    // 区划名称
    private String adminDivisionName;
    
    // 查询条件-基础数据层级
    private String baseLevelCode;
    
    // 查询条件-基础数据行政区划代码
    private String baseAdminDivisionCode;
    
    private Integer isHaveGov;
    
    private String userAccount;
    
    private String userName;
    
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

    public String getBaseLevelCode() {
        return baseLevelCode;
    }

    public void setBaseLevelCode(String baseLevelCode) {
        this.baseLevelCode = baseLevelCode;
    }

    public String getBaseAdminDivisionCode() {
        return baseAdminDivisionCode;
    }

    public void setBaseAdminDivisionCode(String baseAdminDivisionCode) {
        this.baseAdminDivisionCode = baseAdminDivisionCode;
    }

    public Integer getIsHaveGov() {
        return isHaveGov;
    }

    public void setIsHaveGov(Integer isHaveGov) {
        this.isHaveGov = isHaveGov;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLevelCodeStr() {
        return levelCodeStr;
    }

    public void setLevelCodeStr(String levelCodeStr) {
        this.levelCodeStr = levelCodeStr;
    }

}
