/**   
 * 功能描述：简易案件执法人员表实体类
 * @Package: com.beidasoft.zfjd.caseManager.simpleCase.bean 
 * @author: songff   
 * @date: 2018年12月26日 下午1:01:32 
 */
package com.beidasoft.zfjd.caseManager.simpleCase.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**   
* 2018 
* @ClassName: CaseSimpleStaff.java
* @Description: 简易案件执法人员表实体类
*
* @author: songff
* @date: 2018年12月26日 下午1:01:32 
*
*/

@Entity
@Table(name="TBL_CASE_SIMPLE_STAFF")
public class CaseSimpleStaff {
    // 主键
    @Id
    @Column(name = "ID")
    private String id;

    // 案件ID
    @Column(name = "CASE_ID")
    private String caseId;

    // 执法人员ID
    @Column(name = "IDENTITY_ID")
    private String identityId;

    // 执法人员姓名
    @Column(name = "OFFICE_NAME")
    private String officeName;

    // 执法证号
    @Column(name = "CARD_CODE")
    private String cardCode;

    // 所属主体ID
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
