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
* @Description: 一般案件自由裁量基准表实体类
*
* @author: songff
* @date: 2019年1月21日 下午17:39:05 
*
*/
@Entity
@Table(name="TBL_CASE_COMMON_DISCRETIONARY")
public class CaseCommonDiscretionary {
    // 主键
    @Id
    @Column(name = "ID")
    private String id;

    // 案件ID
    @ManyToOne
    @JoinColumn(name = "CASE_ID")
    private CaseCommonBase caseCommonDiscretionary;

    // 自由裁量基准ID
    @Column(name = "DISCRETIONARY_ID")
    private String discretionaryId;

    // 违法事实
    @Column(name = "ILLEGAL_FACT")
    private String illegalFact;

    // 处罚标准
    @Column(name = "PUNISH_STANDARD")
    private String punishStandard;

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
    public CaseCommonBase getCaseCommonDiscretionary() {
        return caseCommonDiscretionary;
    }

    public void setCaseCommonDiscretionary(CaseCommonBase caseCommonDiscretionary) {
        this.caseCommonDiscretionary = caseCommonDiscretionary;
    }

    public String getDiscretionaryId() {
        return discretionaryId;
    }

    public void setDiscretionaryId(String discretionaryId) {
        this.discretionaryId = discretionaryId;
    }

    public String getIllegalFact() {
        return illegalFact;
    }

    public void setIllegalFact(String illegalFact) {
        this.illegalFact = illegalFact;
    }

    public String getPunishStandard() {
        return punishStandard;
    }

    public void setPunishStandard(String punishStandard) {
        this.punishStandard = punishStandard;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
