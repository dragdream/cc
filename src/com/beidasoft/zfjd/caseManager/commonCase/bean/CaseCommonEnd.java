/**   
 * 功能描述：案件终结表实体类
 * @Package: com.beidasoft.zfjd.caseManager.bean 
 * @author: songff   
 * @date: 2018年12月26日 上午11:35:32 
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
* @ClassName: CaseCommonEnd.java
* @Description: 案件终结表实体类
*
* @author: songff
* @date: 2018年12月26日 上午11:35:32 
*
*/

@Entity
@Table(name="TBL_CASE_COMMON_END")
public class CaseCommonEnd {
    // 主键ID
    @Id
    @Column(name = "ID")
    private String id;

    // 案件ID
    @ManyToOne
    @JoinColumn(name = "CASE_ID") // 这是双向的 要写JoinColumn 对面要写 mappedBy
    private CaseCommonBase caseCommonEnd;

    // 案件名称
    @Column(name = "CASE_NAME")
    private String caseName;

    // 案件编号
    @Column(name = "CASE_CODE")
    private String caseCode;

    // 立案号
    @Column(name = "REGISTER_CODE")
    private String registerCode;

    // 终结日期
    @Column(name = "END_DATE")
    private Date endDate;

    // 终结原因
    @Column(name = "END_REASON")
    private String endReason;

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
    public CaseCommonBase getCaseCommonEnd() {
        return caseCommonEnd;
    }

    public void setCaseCommonEnd(CaseCommonBase caseCommonEnd) {
        this.caseCommonEnd = caseCommonEnd;
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getEndReason() {
        return endReason;
    }

    public void setEndReason(String endReason) {
        this.endReason = endReason;
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

