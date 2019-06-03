/**   
 * 功能描述：撤销原处罚决定表实体类
 * @Package: com.beidasoft.zfjd.caseManager.bean 
 * @author: songff   
 * @date: 2018年12月26日 上午11:38:23 
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
* @ClassName: CaseCommonRevokePunish.java
* @Description: 撤销原处罚决定表实体类
*
* @author: songff
* @date: 2018年12月26日 上午11:38:23 
*
*/

@Entity
@Table(name="TBL_CASE_COMMON_REVOKE_PUNISH")
public class CaseCommonRevokePunish {
    // 主键ID
    @Id
    @Column(name = "ID")
    private String id;

    // 案件ID
    @ManyToOne
    @JoinColumn(name = "CASE_ID")
    private CaseCommonBase caseCommonRevokePunish;

    // 案件名称
    @Column(name = "CASE_NAME")
    private String caseName;

    // 案件编号
    @Column(name = "CASE_CODE")
    private String caseCode;

    // 立案号
    @Column(name = "REGISTER_CODE")
    private String registerCode;

    // 撤销原处罚决定日期
    @Column(name = "REVOKE_PUNISHMENT_DATE")
    private Date revokePunishmentDate;

    // 撤销原处罚决定原因
    @Column(name = "REVOKE_PUNISHMENT_REASON")
    private String revokePunishmentReason;

    //撤销原处罚决定类型
    @Column(name = "REVOKE_PUNISH_TYPE")
    private String revokePunishType;
    
    // 批准人
    @Column(name = "APPROVE_PERSON")
    private String approvePerson;

    // 批准日期
    @Column(name = "APPROVE_DATE")
    private Date approveDate;

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
    public CaseCommonBase getCaseCommonRevokePunish() {
        return caseCommonRevokePunish;
    }

    public void setCaseCommonRevokePunish(CaseCommonBase caseCommonRevokePunish) {
        this.caseCommonRevokePunish = caseCommonRevokePunish;
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

    public Date getRevokePunishmentDate() {
        return revokePunishmentDate;
    }

    public void setRevokePunishmentDate(Date revokePunishmentDate) {
        this.revokePunishmentDate = revokePunishmentDate;
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

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
