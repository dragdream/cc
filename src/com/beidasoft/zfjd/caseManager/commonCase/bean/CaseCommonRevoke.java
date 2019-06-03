/**   
 * 功能描述：撤销立案表实体类
 * @Package: com.beidasoft.zfjd.caseManager.bean 
 * @author: songff   
 * @date: 2018年12月26日 上午11:38:10 
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
* @ClassName: CaseCommonRevoke.java
* @Description: 撤销立案表实体类
*
* @author: songff
* @date: 2018年12月26日 上午11:38:10 
*
*/

@Entity
@Table(name="TBL_CASE_COMMON_REVOKE")
public class CaseCommonRevoke {
    // 主键ID
    @Id
    @Column(name = "ID")
    private String id;

    // 案件ID
    @ManyToOne
    @JoinColumn(name = "CASE_ID")
    private CaseCommonBase caseCommonRevoke;

    // 案件名称
    @Column(name = "CASE_NAME")
    private String caseName;

    // 立案号
    @Column(name = "REGISTER_CODE")
    private String registerCode;

    // 案件编号
    @Column(name = "CASE_CODE")
    private String caseCode;

    // 撤销立案日期
    @Column(name = "REVOKE_REGISTER_DATE")
    private Date revokeRegisterDate;

    // 撤销立案原因
    @Column(name = "REVOKE_REGISTER_REASON")
    private String revokeRegisterReason;

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
    public CaseCommonBase getCaseCommonRevoke() {
        return caseCommonRevoke;
    }

    public void setCaseCommonRevoke(CaseCommonBase caseCommonRevoke) {
        this.caseCommonRevoke = caseCommonRevoke;
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

    public Date getRevokeRegisterDate() {
        return revokeRegisterDate;
    }

    public void setRevokeRegisterDate(Date revokeRegisterDate) {
        this.revokeRegisterDate = revokeRegisterDate;
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
