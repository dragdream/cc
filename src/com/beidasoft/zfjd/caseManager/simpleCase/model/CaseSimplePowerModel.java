/**   
 * 功能描述：
 * @Package: com.beidasoft.zfjd.caseManager.simpleCase.model 
 * @author: songff   
 * @date: 2018年12月26日 下午2:36:49 
 */
package com.beidasoft.zfjd.caseManager.simpleCase.model;

/**   
* 2018 
* @ClassName: CaseSimplePowerModel.java
* @Description: 简易案件职权表MODEL类
*
* @author: songff
* @date: 2018年12月26日 下午2:36:49 
*
*/

public class CaseSimplePowerModel {
    //多个主键
    private String ids;
    
    // 主键
    private String id;

    // 案件ID
    private String caseId;

    // 职权ID
    private String powerId;

    // 职权编号
    private String powerCode;

    // 职权名称
    private String powerName;

    // 入库日期
    private String createDateStr;

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

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

}
