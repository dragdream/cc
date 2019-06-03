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
* @Description: 一般案件来源子表MODEL类
*
* @author: songff
* @date: 2019年1月2日 下午2:54:37 
*
*/
public class CaseCommonCaseSourceModel {
    // 主键
    private String id;

    // 案件ID
    private String caseId;

    // 检查对象（上级机关名称、移送机构名称、媒体名称）
    private String commonCaseObject;

    // 移送机构ID
    private String commonCaseOrgmoveId;

    // 接收部门
    private String commonCaseReceiveId;

    // 承办部门
    private String commonCaseRegisterId;

    // 检查单号（投诉、举报工单号、新闻标题)
    private String commonCaseCode;

    // 检查日期（投诉、举报日期、交办日期、移送日期、新闻日期）
    private String commonCaseDateStr;

    // 检查事项（投诉、举报事项、交办说明、移送信息描述、移送说明、新闻事项、其他事项)
    private String commonCaseItem;

    // 检查地址
    private String commonCaseAddress;

    // 投诉人（举报人)
    private String commonCasePerson;

    // 投诉人（举报人）联系方式
    private String commonCasePhone;

    // 检查类型【（code: 01日常检查，02双随机检查，03专项检查，04勘验，99其他）、投诉、举报来源(code: 01代表13245，02内部热线)】
    private String commonCaseType;

    // 案件来源（code:1现场检查,2投诉,3举报,4上级机关交办,5其他机关移送,6媒体曝光9其他）
    private String commonCaseSource;

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

    public String getCommonCaseObject() {
        return commonCaseObject;
    }

    public void setCommonCaseObject(String commonCaseObject) {
        this.commonCaseObject = commonCaseObject;
    }

    public String getCommonCaseOrgmoveId() {
        return commonCaseOrgmoveId;
    }

    public void setCommonCaseOrgmoveId(String commonCaseOrgmoveId) {
        this.commonCaseOrgmoveId = commonCaseOrgmoveId;
    }

    public String getCommonCaseReceiveId() {
        return commonCaseReceiveId;
    }

    public void setCommonCaseReceiveId(String commonCaseReceiveId) {
        this.commonCaseReceiveId = commonCaseReceiveId;
    }

    public String getCommonCaseRegisterId() {
        return commonCaseRegisterId;
    }

    public void setCommonCaseRegisterId(String commonCaseRegisterId) {
        this.commonCaseRegisterId = commonCaseRegisterId;
    }

    public String getCommonCaseCode() {
        return commonCaseCode;
    }

    public void setCommonCaseCode(String commonCaseCode) {
        this.commonCaseCode = commonCaseCode;
    }

    public String getCommonCaseDateStr() {
        return commonCaseDateStr;
    }

    public void setCommonCaseDateStr(String commonCaseDateStr) {
        this.commonCaseDateStr = commonCaseDateStr;
    }

    public String getCommonCaseItem() {
        return commonCaseItem;
    }

    public void setCommonCaseItem(String commonCaseItem) {
        this.commonCaseItem = commonCaseItem;
    }

    public String getCommonCaseAddress() {
        return commonCaseAddress;
    }

    public void setCommonCaseAddress(String commonCaseAddress) {
        this.commonCaseAddress = commonCaseAddress;
    }

    public String getCommonCasePerson() {
        return commonCasePerson;
    }

    public void setCommonCasePerson(String commonCasePerson) {
        this.commonCasePerson = commonCasePerson;
    }

    public String getCommonCasePhone() {
        return commonCasePhone;
    }

    public void setCommonCasePhone(String commonCasePhone) {
        this.commonCasePhone = commonCasePhone;
    }

    public String getCommonCaseType() {
        return commonCaseType;
    }

    public void setCommonCaseType(String commonCaseType) {
        this.commonCaseType = commonCaseType;
    }

    public String getCommonCaseSource() {
        return commonCaseSource;
    }

    public void setCommonCaseSource(String commonCaseSource) {
        this.commonCaseSource = commonCaseSource;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

}
