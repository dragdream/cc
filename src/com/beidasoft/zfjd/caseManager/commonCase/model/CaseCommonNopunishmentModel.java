/**   
 * 功能描述：
 * @Package: com.beidasoft.zfjd.caseManager.commonCase.model 
 * @author: songff   
 * @date: 2018年12月26日 下午2:29:46 
 */
package com.beidasoft.zfjd.caseManager.commonCase.model;

/**   
* 2018 
* @ClassName: CaseCommonNopunishmentModel.java
* @Description: 不予处罚案件表MODEL类
*
* @author: songff
* @date: 2018年12月26日 下午2:29:46 
*
*/

public class CaseCommonNopunishmentModel {
    // 主键ID
    private String id;

    // 案件ID
    private String caseId;

    // 案件名称
    private String caseName;

    // 立案号
    private String registerCode;

    // 案件编号
    private String caseCode;

    // 不予处罚日期
    private String noPunishmentDateStr;

    // 不予处罚原因
    private String noPunishmentReason;

    // 批准人
    private String approvePerson;

    // 批准日期
    private String approveDateStr;
    
    //不予处罚文件路径
    private String noPunishmentFile;
    
    //不予处罚文号
    private String noPunishmentCode;
    
    //不予处罚决定书送达日期
    private String noPunishmentSentWay;
    
    //不予处罚决定书送达方式
    private String noPunishmentSentDateStr;
    
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

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode;
    }

    public String getNoPunishmentDateStr() {
        return noPunishmentDateStr;
    }

    public void setNoPunishmentDateStr(String noPunishmentDateStr) {
        this.noPunishmentDateStr = noPunishmentDateStr;
    }

    public String getNoPunishmentReason() {
        return noPunishmentReason;
    }

    public void setNoPunishmentReason(String noPunishmentReason) {
        this.noPunishmentReason = noPunishmentReason;
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

    public String getNoPunishmentFile() {
        return noPunishmentFile;
    }

    public void setNoPunishmentFile(String noPunishmentFile) {
        this.noPunishmentFile = noPunishmentFile;
    }

    public String getNoPunishmentCode() {
        return noPunishmentCode;
    }

    public void setNoPunishmentCode(String noPunishmentCode) {
        this.noPunishmentCode = noPunishmentCode;
    }

    public String getNoPunishmentSentWay() {
        return noPunishmentSentWay;
    }

    public void setNoPunishmentSentWay(String noPunishmentSentWay) {
        this.noPunishmentSentWay = noPunishmentSentWay;
    }

    public String getNoPunishmentSentDateStr() {
        return noPunishmentSentDateStr;
    }

    public void setNoPunishmentSentDateStr(String noPunishmentSentDateStr) {
        this.noPunishmentSentDateStr = noPunishmentSentDateStr;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

}
