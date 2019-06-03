/**   
 * 功能描述：一般案件来源子表实体类
 * @Package: com.beidasoft.zfjd.caseManager.commonCase.bean 
 * @author: songff   
 * @date: 2019年1月2日 下午2:53:05 
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
* 2019 
* @ClassName: CaseCommonCaseSource.java
* @Description: 一般案件来源子表实体类
*
* @author: songff
* @date: 2019年1月2日 下午2:53:05 
*
*/

@Entity
@Table(name="TBL_CASE_COMMON_CASE_SOURCE")
public class CaseCommonCaseSource {
    // 主键
    @Id
    @Column(name = "ID")
    private String id;

    // 案件ID
    @ManyToOne
    @JoinColumn(name = "CASE_ID") // 这是双向的 要写JoinColumn 对面要写 mappedBy
    private CaseCommonBase caseCommonCaseSource;

    // 检查对象（上级机关名称、移送机构名称、媒体名称）
    @Column(name = "COMMON_CASE_OBJECT")
    private String commonCaseObject;

    // 移送机构ID
    @Column(name = "COMMON_CASE_ORGMOVE_ID")
    private String commonCaseOrgmoveId;

    // 接收部门
    @Column(name = "COMMON_CASE_RECEIVE_ID")
    private String commonCaseReceiveId;

    // 承办部门
    @Column(name = "COMMON_CASE_REGISTER_ID")
    private String commonCaseRegisterId;

    // 检查单号（投诉、举报工单号、新闻标题)
    @Column(name = "COMMON_CASE_CODE")
    private String commonCaseCode;

    // 检查日期（投诉、举报日期、交办日期、移送日期、新闻日期）
    @Column(name = "COMMON_CASE_DATE")
    private Date commonCaseDate;

    // 检查事项（投诉、举报事项、交办说明、移送信息描述、移送说明、新闻事项、其他事项)
    @Column(name = "COMMON_CASE_ITEM")
    private String commonCaseItem;

    // 检查地址
    @Column(name = "COMMON_CASE_ADDRESS")
    private String commonCaseAddress;

    // 投诉人（举报人)
    @Column(name = "COMMON_CASE_PERSON")
    private String commonCasePerson;

    // 投诉人（举报人）联系方式
    @Column(name = "COMMON_CASE_PHONE")
    private String commonCasePhone;

    // 检查类型【（code: 01日常检查，02双随机检查，03专项检查，04勘验，99其他）、投诉、举报来源(code: 01代表13245，02内部热线)】
    @Column(name = "COMMON_CASE_TYPE")
    private String commonCaseType;

    // 案件来源（code:1现场检查,2投诉,3举报,4上级机关交办,5其他机关移送,6媒体曝光9其他）
    @Column(name = "COMMON_CASE_SOURCE")
    private String commonCaseSource;

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
    public CaseCommonBase getCaseCommonCaseSource() {
        return caseCommonCaseSource;
    }

    public void setCaseCommonCaseSource(CaseCommonBase caseCommonCaseSource) {
        this.caseCommonCaseSource = caseCommonCaseSource;
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

    public Date getCommonCaseDate() {
        return commonCaseDate;
    }

    public void setCommonCaseDate(Date commonCaseDate) {
        this.commonCaseDate = commonCaseDate;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
