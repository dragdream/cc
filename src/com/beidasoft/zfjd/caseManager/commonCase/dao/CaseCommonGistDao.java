/**   
 * 功能描述：
 * @Package: com.beidasoft.zfjd.caseManager.commonCase.dao 
 * @author: songff   
 * @date: 2019年1月16日 上午12:08:07 
 */
package com.beidasoft.zfjd.caseManager.commonCase.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonBase;
import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonGist;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonGistModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

/**   
* 2019 
* @ClassName: CaseCommonGistDao.java
* @Description: 一般案件的违法依据信息表DAO类
*
* @author: songff
* @date: 2019年1月16日 上午12:08:07 
*
*/
@Repository
public class CaseCommonGistDao extends TeeBaseDao<CaseCommonGist> {

    /**
     * 
    * @Function: saveBatchCaseGist()
    * @Description: 批量保存违法依据信息
    *
    * @param: caseGists 违法依据集合参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月16日 下午6:03:51 
    *
     */
    public void saveBatchCaseGist(List<CaseCommonGist> caseGists) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for(int i = 0; i < caseGists.size(); i++) {
            session.save(caseGists.get(i));
        }
        tx.commit();
        session.close();
    }
    /**
     * 
    * @Function: deleteCaseGist()
    * @Description: 通过案件ID，删除子表违法依据信息
    *
    * @param: cBase 参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月16日 下午6:01:33 
    *
     */
    public void deleteCaseGist(CaseCommonBase cBase) {
        StringBuffer hql = new StringBuffer();
        hql.append(" delete from CaseCommonGist where caseCommonGist.id = '"+cBase.getId()+"'");
        deleteOrUpdateByQuery(hql.toString(), null);
    }
    
    /**
     * 
    * @Function: findCommonGists()
    * @Description: 查询违法依据
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年3月9日 下午12:05:28 
    *
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<CaseCommonGist> findCommonGists(int start, int length, CaseCommonGistModel cGistModel) {
        //hql语句
        StringBuffer hql = new StringBuffer();
        //参数集合
        ArrayList list = new ArrayList();
        
        hql.append(" from CaseCommonGist where id = id");
        //依据ID
        if (!TeeUtility.isNullorEmpty(cGistModel.getGistId())) {
            hql.append(" and gistId in (?) ");
            list.add(cGistModel.getGistId().trim().replace(",", "','"));
        }
        //案件ID
        if (!TeeUtility.isNullorEmpty(cGistModel.getCaseId())) {
            hql.append(" and caseCommonGist.id = ? ");
            list.add(cGistModel.getCaseId());
        }
        Object[] params = list.toArray();
        return super.pageFind(hql.toString(), start, length, params);
    }
}
