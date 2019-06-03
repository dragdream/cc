/**   
 * 功能描述：
 * @Package: com.beidasoft.zfjd.caseManager.simpleCase.model 
 * @author: songff   
 * @date: 2018年12月26日 下午2:36:23 
 */
package com.beidasoft.zfjd.caseManager.simpleCase.model;

/**   
* 2018 
* @ClassName: CaseSimpleGistModel.java
* @Description: 简易案件违法依据表MODEL类
*
* @author: songff
* @date: 2018年12月26日 下午2:36:23 
*
*/

public class CaseSimpleGistModel {
    //多主键（，分隔）
    private String ids;
    
    // 主键
    private String id;

    // 案件ID
    private String caseId;

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
    
    //目
    private Integer gistCatalog;

    // 内容
    private String content;

    // 入库日期
    private String createDateStr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
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

    public void setLawsName(String lawName) {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public void setLawName(String lawName) {
        this.lawName = lawName;
    }

    public Integer getGistCatalog() {
        return gistCatalog;
    }

    public void setGistCatalog(Integer gistCatalog) {
        this.gistCatalog = gistCatalog;
    }

}
