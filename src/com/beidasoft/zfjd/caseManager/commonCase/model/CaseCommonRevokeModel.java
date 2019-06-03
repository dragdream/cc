/**   
 * 功能描述：
 * @Package: com.beidasoft.zfjd.caseManager.commonCase.model 
 * @author: songff   
 * @date: 2018年12月26日 下午2:33:58 
 */
package com.beidasoft.zfjd.caseManager.commonCase.model;

/**   
* 2018 
* @ClassName: CaseCommonRevokeModel.java
* @Description: 撤销立案表MODEL类
*
* @author: songff
* @date: 2018年12月26日 下午2:33:58 
*
*/

public class CaseCommonRevokeModel {
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

    // 撤销立案日期
    private String revokeRegisterDateStr;

    // 撤销立案原因
    private String revokeRegisterReason;

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

    public String getRevokeRegisterDateStr() {
        return revokeRegisterDateStr;
    }

    public void setRevokeRegisterDateStr(String revokeRegisterDateStr) {
        this.revokeRegisterDateStr = revokeRegisterDateStr;
    }

    public String getRevokeRegisterReason() {
        return revokeRegisterReason;
    }

    public void setRevokeRegisterReason(String revokeRegisterReason) {
        this.revokeRegisterReason = revokeRegisterReason;
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
