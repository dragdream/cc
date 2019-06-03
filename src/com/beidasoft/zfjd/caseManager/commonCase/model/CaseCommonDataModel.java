package com.beidasoft.zfjd.caseManager.commonCase.model;

/**
 * @ClassName: CaseCommonDataModel.java
 * @Description:
 *
 * @author: mixue
 * @date: 2019年3月20日 上午11:28:46
 */
public class CaseCommonDataModel {

    /** 主键 */
    private String id;

    /** 表名 */
    private String tableName;

    /** 列名 */
    private String columnName;

    /** 数据类型 */
    private String dataType;

    /** 数据长度 */
    private Integer dataLength;

    /** 是否作为查询条件 */
    private Integer isSelect;

    /** 匹配方式 */
    private String matchingMode;

    /** 是否作为可查看列(可导出) */
    private Integer isShow;

    /** 是否为数据字典 */
    private Integer isDictionary;

    /** 数据字典项 */
    private String dictionaryValue;

    /** 注释 */
    private String comments;

    /** 是否固定显示列 */
    private Integer isRegular;

    /** 字段名 */
    private String field;

    /** 是否作为可排序列 */
    private Integer isSortable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getDataLength() {
        return dataLength;
    }

    public void setDataLength(Integer dataLength) {
        this.dataLength = dataLength;
    }

    public Integer getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(Integer isSelect) {
        this.isSelect = isSelect;
    }

    public String getMatchingMode() {
        return matchingMode;
    }

    public void setMatchingMode(String matchingMode) {
        this.matchingMode = matchingMode;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getIsDictionary() {
        return isDictionary;
    }

    public void setIsDictionary(Integer isDictionary) {
        this.isDictionary = isDictionary;
    }

    public String getDictionaryValue() {
        return dictionaryValue;
    }

    public void setDictionaryValue(String dictionaryValue) {
        this.dictionaryValue = dictionaryValue;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getIsRegular() {
        return isRegular;
    }

    public void setIsRegular(Integer isRegular) {
        this.isRegular = isRegular;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Integer getIsSortable() {
        return isSortable;
    }

    public void setIsSortable(Integer isSortable) {
        this.isSortable = isSortable;
    }

}
