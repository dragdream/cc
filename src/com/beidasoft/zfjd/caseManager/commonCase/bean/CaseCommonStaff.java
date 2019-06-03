/**   
 * 功能描述：一般案件的执法人员信息表实体类
 * @Package: com.beidasoft.zfjd.caseManager.bean 
 * @author: songff   
 * @date: 2018年12月26日 上午11:38:35 
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
* @ClassName: CaseCommonStaff.java
* @Description: 一般案件的执法人员信息表实体类
*
* @author: songff
* @date: 2018年12月26日 上午11:38:35 
*
*/

@Entity
@Table(name="TBL_CASE_COMMON_STAFF")
public class CaseCommonStaff {
    // 主键
    @Id
    @Column(name = "ID")
    private String id;

    // 案件ID
    @ManyToOne
    @JoinColumn(name = "CASE_ID")
    private CaseCommonBase caseCommonStaff;

    // 执法人员ID
    @Column(name = "IDENTITY_ID")
    private String identityId;

    // 执法人员姓名
    @Column(name = "OFFICE_NAME")
    private String officeName;

    // 执法证号
    @Column(name = "CARD_CODE")
    private String cardCode;

    // 所属主体D
    @Column(name = "SUBJECT_ID")
    private String subjectId;

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
    public CaseCommonBase getCaseCommonStaff() {
        return caseCommonStaff;
    }

    public void setCaseCommonStaff(CaseCommonBase caseCommonStaff) {
        this.caseCommonStaff = caseCommonStaff;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
