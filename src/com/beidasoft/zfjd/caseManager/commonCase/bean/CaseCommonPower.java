/**   
 * 功能描述：一般案件的违法行为信息表实体类
 * @Package: com.beidasoft.zfjd.caseManager.bean 
 * @author: songff   
 * @date: 2018年12月26日 上午11:37:33 
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
* @ClassName: CaseCommonPower.java
* @Description: 一般案件的违法行为信息表实体类
*
* @author: songff
* @date: 2018年12月26日 上午11:37:33 
*
*/

@Entity
@Table(name="TBL_CASE_COMMON_POWER")
public class CaseCommonPower {
    // 主键
    @Id
    @Column(name = "ID")
    private String id;

    // 案件ID
    @ManyToOne
    @JoinColumn(name = "CASE_ID")
    private CaseCommonBase caseCommonPower;

    // 职权ID
    @Column(name = "POWER_ID")
    private String powerId;

    // 职权编号
    @Column(name = "POWER_CODE")
    private String powerCode;

    // 职权名称
    @Column(name = "POWER_NAME")
    private String powerName;

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
    public CaseCommonBase getCaseCommonPower() {
        return caseCommonPower;
    }

    public void setCaseCommonPower(CaseCommonBase caseCommonPower) {
        this.caseCommonPower = caseCommonPower;
    }

    public String getPowerId() {
        return powerId;
    }

    public void setPowerId(String powerId) {
        this.powerId = powerId;
    }

    public String getPowerCode() {
        return powerCode;
    }

    public void setPowerCode(String powerCode) {
        this.powerCode = powerCode;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
