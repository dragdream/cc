/**   
 * 功能描述：
 * @Package: com.beidasoft.zfjd.caseManager.commonCase.model 
 * @author: songff   
 * @date: 2019年1月2日 下午2:54:37 
 */
package com.beidasoft.zfjd.caseManager.commonCase.model;

/**   
* 2019 
* @ClassName: CaseCommonCaseSourceModel.java
* @Description: 一般案件自由裁量基准表MODEL类
*
* @author: songff
* @date: 2019年1月2日 下午2:54:37 
*
*/
public class CaseCommonDiscretionaryModel {
    // 主键
    private String id;

    // 案件ID
    private String caseId;

    // 自由裁量基准ID
    private String discretionaryId;

    // 违法事实
    private String illegalFact;

    // 处罚标准
    private String punishStandard;

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

    public String getDiscretionaryId() {
        return discretionaryId;
    }

    public void setDiscretionaryId(String discretionaryId) {
        this.discretionaryId = discretionaryId;
    }

    public String getIllegalFact() {
        return illegalFact;
    }

    public void setIllegalFact(String illegalFact) {
        this.illegalFact = illegalFact;
    }

    public String getPunishStandard() {
        return punishStandard;
    }

    public void setPunishStandard(String punishStandard) {
        this.punishStandard = punishStandard;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

}
