/**   
 * 功能描述：简易案件职权表实体类
 * @Package: com.beidasoft.zfjd.caseManager.simpleCase.bean 
 * @author: songff   
 * @date: 2018年12月26日 下午1:01:03 
 */
package com.beidasoft.zfjd.caseManager.simpleCase.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**   
* 2018 
* @ClassName: CaseSimplePower.java
* @Description: 简易案件职权表实体类
*
* @author: songff
* @date: 2018年12月26日 下午1:01:03 
*
*/

@Entity
@Table(name="TBL_CASE_SIMPLE_POWER")
public class CaseSimplePower {
    // 主键
    @Id
    @Column(name = "ID")
    private String id;

    // 案件ID
    @Column(name = "CASE_ID")
    private String caseId;

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

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
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
