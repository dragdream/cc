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
import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonRevokePunish;
import com.tianee.webframe.dao.TeeBaseDao;

/**   
* 2019 
* @ClassName: CaseCommonRevokePunishDao.java
* @Description: 撤销原处罚决定表DAO类
*
* @author: songff
* @date: 2019年1月16日 上午12:08:07 
*
*/
@Repository
public class CaseCommonRevokePunishDao extends TeeBaseDao<CaseCommonRevokePunish> {

    /**
     * 
    * @Function: saveBatchCaseRevokePunish()
    * @Description: 批量保存撤销立案信息
    *
    * @param: caseRevokePunishs 撤销立案案件集合参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月16日 下午6:03:51 
    *
     */
    public void saveBatchCaseRevokePunish(List<CaseCommonRevokePunish> caseRevokePunishs) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for(int i = 0; i < caseRevokePunishs.size(); i++) {
            session.save(caseRevokePunishs.get(i));
        }
        tx.commit();
        session.close();
    }
    
    /**
     * 
    * @Function: deleteCaseRevokePunish()
    * @Description: 通过案件ID，删除子撤销立案信息
    *
    * @param: cBase 参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月16日 下午6:01:33 
    *
     */
    public void deleteCaseRevokePunish(CaseCommonBase cBase) {
        StringBuffer hql = new StringBuffer();
        hql.append(" delete from CaseCommonRevokePunish where caseCommonRevokePunish.id = '"+cBase.getId()+"'");
        deleteOrUpdateByQuery(hql.toString(), null);
    }
    
}
