/**   
 * 功能描述：
 * @Package: com.beidasoft.zfjd.caseManager.commonCase.dao 
 * @author: songff   
 * @date: 2019年1月16日 上午12:08:07 
 */
package com.beidasoft.zfjd.caseManager.commonCase.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonBase;
import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonNopunishment;
import com.tianee.webframe.dao.TeeBaseDao;

/**   
* 2019 
* @ClassName: CaseCommonNopunishmentDao.java
* @Description: 不予处罚案件表DAO类
*
* @author: songff
* @date: 2019年1月16日 上午12:08:07 
*
*/
@Repository
public class CaseCommonNopunishmentDao extends TeeBaseDao<CaseCommonNopunishment> {

    /**
     * 
    * @Function: saveBatchCaseNoPunishment()
    * @Description: 批量保存不予处罚案件信息
    *
    * @param: caseNopunishments 不予处罚案件集合参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月16日 下午6:03:51 
    *
     */
    public void saveBatchCaseNoPunishment(List<CaseCommonNopunishment> caseNopunishments) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for(int i = 0; i < caseNopunishments.size(); i++) {
            session.save(caseNopunishments.get(i));
        }
        tx.commit();
        session.close();
    }
    /**
     * 
    * @Function: deleteCaseNoPunishment()
    * @Description: 通过案件ID，删除子表不予处罚案件信息
    *
    * @param: cBase 参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月16日 下午6:01:33 
    *
     */
    public void deleteCaseNoPunishment(CaseCommonBase cBase) {
        StringBuffer hql = new StringBuffer();
        hql.append(" delete from CaseCommonNopunishment where caseCommonNopunishment.id = '"+cBase.getId()+"'");
        deleteOrUpdateByQuery(hql.toString(), null);
    }
    
}
