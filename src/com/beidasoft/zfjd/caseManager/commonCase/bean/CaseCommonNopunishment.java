/**   
 * 功能描述：不予处罚案件表实体类
 * @Package: com.beidasoft.zfjd.caseManager.bean 
 * @author: songff   
 * @date: 2018年12月26日 上午11:36:50 
 */
package com.beidasoft.zfjd.caseManager.commonCase.bean;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;

/**   
* 2018 
* @ClassName: CaseCommonNopunishment.java
* @Description: 该类的功能描述
*
* @author: songff
* @date: 2018年12月26日 上午11:36:50 
*
*/

@Entity
@Table(name="TBL_CASE_COMMON_NOPUNISHMENT")
public class CaseCommonNopunishment {
    // 主键ID
    @Id
    @Column(name = "ID")
    private String id;

    // 案件ID
    @ManyToOne
    @JoinColumn(name = "CASE_ID") // 这是双向的 要写JoinColumn 对面要写 mappedBy
    private CaseCommonBase caseCommonNopunishment;

    // 案件名称
    @Column(name = "CASE_NAME")
    private String caseName;

    // 立案号
    @Column(name = "REGISTER_CODE")
    private String registerCode;

    // 案件编号
    @Column(name = "CASE_CODE")
    private String caseCode;

    // 不予处罚日期
    @Column(name = "NO_PUNISHMENT_DATE")
    private Date noPunishmentDate;

    // 不予处罚原因
    @Column(name = "NO_PUNISHMENT_REASON")
    private String noPunishmentReason;

    // 批准人
    @Column(name = "APPROVE_PERSON")
    private String approvePerson;

    // 批准日期
    @Column(name = "APPROVE_DATE")
    private Date approveDate;

    //不予处罚文件路径
    @Column(name = "NO_PUNISHMENT_FILE")
    private String noPunishmentFile;
    
    //不予处罚文号
    @Column(name = "NO_PUNISHMENT_CODE")
    private String noPunishmentCode;
    
    //不予处罚决定书送达日期
    @Column(name = "NO_PUNISHMENT_SENT_WAY")
    private String noPunishmentSentWay;
    
    //不予处罚决定书送达方式
    @Column(name = "NO_PUNISHMENT_SENT_DATE")
    private Date noPunishmentSentDate;
    
    // 入库日期
    @Column(name = "CREATE_DATE")
    private Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonBackReference
    public CaseCommonBase getCaseCommonNopunishment() {
        return caseCommonNopunishment;
    }

    public void setCaseCommonNopunishment(CaseCommonBase caseCommonNopunishment) {
        this.caseCommonNopunishment = caseCommonNopunishment;
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

    public Date getNoPunishmentDate() {
        return noPunishmentDate;
    }

    public void setNoPunishmentDate(Date noPunishmentDate) {
        this.noPunishmentDate = noPunishmentDate;
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

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
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

    public Date getNoPunishmentSentDate() {
        return noPunishmentSentDate;
    }

    public void setNoPunishmentSentDate(Date noPunishmentSentDate) {
        this.noPunishmentSentDate = noPunishmentSentDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
