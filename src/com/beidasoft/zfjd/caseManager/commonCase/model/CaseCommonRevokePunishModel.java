/**   
 * 功能描述：
 * @Package: com.beidasoft.zfjd.caseManager.commonCase.model 
 * @author: songff   
 * @date: 2018年12月26日 下午2:34:41 
 */
package com.beidasoft.zfjd.caseManager.commonCase.model;

/**   
* 2018 
* @ClassName: CaseCommonRevokePunishModel.java
* @Description: 撤销原处罚决定表MODEL类
*
* @author: songff
* @date: 2018年12月26日 下午2:34:41 
*
*/

public class CaseCommonRevokePunishModel {
    // 主键ID
    private String id;

    // 案件ID
    private String caseId;

    // 案件名称
    private String caseName;

    // 案件编号
    private String caseCode;

    // 立案号
    private String registerCode;

    // 撤销原处罚决定日期
    private String revokePunishmentDateStr;

    // 撤销原处罚决定原因
    private String revokePunishmentReason;

    //撤销原处罚决定类型 
    private String revokePunishType;
    
    // 批准人
    private String approvePerson;

    // 批准日期
    private String approveDateStr;

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

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode;
    }

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }

    public String getRevokePunishmentDateStr() {
        return revokePunishmentDateStr;
    }

    public void setRevokePunishmentDateStr(String revokePunishmentDateStr) {
        this.revokePunishmentDateStr = revokePunishmentDateStr;
    }

    public String getRevokePunishmentReason() {
        return revokePunishmentReason;
    }

    public void setRevokePunishmentReason(String revokePunishmentReason) {
        this.revokePunishmentReason = revokePunishmentReason;
    }

    public String getRevokePunishType() {
        return revokePunishType;
    }

    public void setRevokePunishType(String revokePunishType) {
        this.revokePunishType = revokePunishType;
    }

    public String getApprovePerson() {
        return approvePerson;
    }

    public void setApprovePerson(String approvePerson) {
        this.approvePerson = approvePerson;
    }

    public String getApproveDateStr() {
        return approveDateStr;
    }

    public void setApproveDateStr(String approveDateStr) {
        this.approveDateStr = approveDateStr;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

}
