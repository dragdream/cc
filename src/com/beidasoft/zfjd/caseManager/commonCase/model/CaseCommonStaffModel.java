/**   
 * 功能描述：
 * @Package: com.beidasoft.zfjd.caseManager.commonCase.model 
 * @author: songff   
 * @date: 2018年12月26日 下午2:35:10 
 */
package com.beidasoft.zfjd.caseManager.commonCase.model;

/**   
* 2018 
* @ClassName: CaseCommonStaffModel.java
* @Description: 一般案件的执法人员信息表MODEL类
*
* @author: songff
* @date: 2018年12月26日 下午2:35:10 
*
*/

public class CaseCommonStaffModel {
    // 主键
    private String id;

    // 案件ID
    private String caseId;

    // 执法人员ID
    private String identityId;

    // 执法人员姓名
    private String officeName;

    // 执法证号
    private String cardCode;

    // 所属主体D
    private String subjectId;

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

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

}
