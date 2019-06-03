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
import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonRevoke;
import com.tianee.webframe.dao.TeeBaseDao;

/**   
* 2019 
* @ClassName: CaseCommonRevokeDao.java
* @Description: 撤销立案表DAO类
*
* @author: songff
* @date: 2019年1月16日 上午12:08:07 
*
*/
@Repository
public class CaseCommonRevokeDao extends TeeBaseDao<CaseCommonRevoke> {
    
    /**
     * 
    * @Function: saveBatchCaseRevoke()
    * @Description: 批量保存撤销立案信息
    *
    * @param: caseRevokes 撤销立案案件集合参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月16日 下午6:03:51 
    *
     */
    public void saveBatchCaseRevoke(List<CaseCommonRevoke> caseRevokes) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for(int i = 0; i < caseRevokes.size(); i++) {
            session.save(caseRevokes.get(i));
        }
        tx.commit();
        session.close();
    }
    
    /**
     * 
    * @Function: deleteCaseRevoke()
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
    public void deleteCaseRevoke(CaseCommonBase cBase) {
        StringBuffer hql = new StringBuffer();
        hql.append(" delete from CaseCommonRevoke where caseCommonRevoke.id = '"+cBase.getId()+"'");
        deleteOrUpdateByQuery(hql.toString(), null);
    }
}
