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
import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonCaseSource;
import com.tianee.webframe.dao.TeeBaseDao;

/**   
* 2019 
* @ClassName: CaseCommonCaseSourceDao.java
* @Description: 案件来源dao
*
* @author: songff
* @date: 2019年1月11日 上午9:22:43 
*
*/
@Repository("caseCommonCaseSourceDao")
public class CaseCommonCaseSourceDao extends TeeBaseDao<CaseCommonCaseSource>{
    
    /**
     * 
    * @Function: saveCaseSource()
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
    public void saveCaseSource(CaseCommonCaseSource ccSource) {
        save(ccSource);
    }
    
    /**
     * 
    * @Function: saveBatchCaseSource()
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
    public void saveBatchCaseSource(List<CaseCommonCaseSource> ccSources) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for(int i = 0; i < ccSources.size(); i++) {
            session.save(ccSources.get(i));
        }
        tx.commit();
        session.close();
    }
    
    /**
     * 
    * @Function: deleteCaseSourceByCaseId()
    * @Description: 该函数的功能描述
    *
    * @param: cBase案件信息
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月11日 下午2:19:17 
    *
     */
    public void deleteCaseSourceByCaseId(CaseCommonBase cBase) {
        StringBuffer hql = new StringBuffer();
        hql.append(" delete from CaseCommonCaseSource where caseCommonCaseSource.id = '"+cBase.getId()+"'");
        deleteOrUpdateByQuery(hql.toString(), null);
    }
}
