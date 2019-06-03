package com.beidasoft.zfjd.caseManager.commonCase.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName: CaseCommonData.java
 * @Description:
 *
 * @author: mixue
 * @date: 2019年3月20日 上午11:28:13
 */
@Entity
@Table(name = "TBL_CASE_COMMON_DATA")
public class CaseCommonData {

    /** 主键 */
    @Id
    @Column(name = "ID")
    private String id;

    /** 表名 */
    @Column(name = "TABLE_NAME")
    private String tableName;

    /** 列名 */
    @Column(name = "COLUMN_NAME")
    private String columnName;

    /** 数据类型 */
    @Column(name = "DATA_TYPE")
    private String dataType;

    /** 数据长度 */
    @Column(name = "DATA_LENGTH")
    private Integer dataLength;

    /** 是否作为查询条件 */
    @Column(name = "IS_SELECT")
    private Integer isSelect;

    /** 匹配方式 */
    @Column(name = "MATCHING_MODE")
    private String matchingMode;

    /** 是否作为可查看列(可导出) */
    @Column(name = "IS_SHOW")
    private Integer isShow;

    /** 是否为数据字典 */
    @Column(name = "IS_DICTIONARY")
    private Integer isDictionary;

    /** 数据字典项 */
    @Column(name = "DICTIONARY_VALUE")
    private String dictionaryValue;

    /** 注释 */
    @Column(name = "COMMENTS")
    private String comments;

    /** 是否固定显示列 */
    @Column(name = "IS_REGULAR")
    private Integer isRegular;

    /** 字段名 */
    @Column(name = "FIELD")
    private String field;

    /** 是否作为可排序列 */
    @Column(name = "IS_SORTABLE")
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
