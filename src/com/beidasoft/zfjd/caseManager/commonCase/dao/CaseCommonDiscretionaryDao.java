/**   
 * 功能描述：
 * @Package: com.beidasoft.zfjd.caseManager.commonCase.dao 
 * @author: songff   
 * @date: 2019年1月11日 上午9:22:43 
 */
package com.beidasoft.zfjd.caseManager.commonCase.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonBase;
import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonDiscretionary;
import com.tianee.webframe.dao.TeeBaseDao;

/**   
* 2019 
* @ClassName: CaseCommonDiscretionaryDao.java
* @Description: 一般案件自由裁量基准表DAO类
*
* @author: songff
* @date: 2019年1月11日 上午9:22:43 
*
*/

@Repository
public class CaseCommonDiscretionaryDao extends TeeBaseDao<CaseCommonDiscretionary> {

    /**
     * 
    * @Function: saveDiscretionary()
    * @Description: 保存案件来源信息
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月11日 上午9:26:36 
    *
     */
    public void saveDiscretionary(CaseCommonDiscretionary ccdDiscretionary) {
        save(ccdDiscretionary);
    }
    
    /**
     * 
    * @Function: saveBatchDiscretionary()
    * @Description: 批量保存案件资源信息
    *
    * @param: ccSources资源信息
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月11日 上午10:57:19 
    *
     */
    public void saveBatchDiscretionary(List<CaseCommonDiscretionary> ccdList) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for(int i = 0; i < ccdList.size(); i++) {
            session.save(ccdList.get(i));
        }
        tx.commit();
        session.close();
    }
    
    /**
     * 
    * @Function: deleteDiscretionaryByCaseId()
    * @Description: 删除自由裁量基准
    *
    * @param: cBase案件信息
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月11日 下午2:19:17 
    *
     */
    public void deleteDiscretionaryByCaseId(CaseCommonBase cBase) {
        StringBuffer hql = new StringBuffer();
        hql.append(" delete from CaseCommonDiscretionary where caseCommonDiscretionary.id = '"+cBase.getId()+"'");
        deleteOrUpdateByQuery(hql.toString(), null);
    }
    
}
