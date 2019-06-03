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
import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonPower;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonPowerModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

/**   
* 2019 
* @ClassName: CaseCommonPowerDao.java
* @Description: 一般案件的违法行为信息表DAO类
*
* @author: songff
* @date: 2019年1月16日 上午12:08:07 
*
*/
@Repository
public class CaseCommonPowerDao extends TeeBaseDao<CaseCommonPower> {

    /**
     * 
    * @Function: saveBatchCasePower()
    * @Description: 批量保存违法行为信息
    *
    * @param: casePowers 依据违法行为集合参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月16日 下午6:03:51 
    *
     */
    public void saveBatchCasePower(List<CaseCommonPower> casePowers) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for(int i = 0; i < casePowers.size(); i++) {
            session.save(casePowers.get(i));
        }
        tx.commit();
        session.close();
    }
    /**
     * 
    * @Function: deleteCasePower()
    * @Description: 通过案件ID，删除子表违法行为信息
    *
    * @param: cBase 参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月16日 下午6:01:33 
    *
     */
    public void deleteCasePower(CaseCommonBase cBase) {
        StringBuffer hql = new StringBuffer();
        hql.append(" delete from CaseCommonPower where caseCommonPower.id = '"+cBase.getId()+"'");
        deleteOrUpdateByQuery(hql.toString(), null);
    }
    
    /**
     * 
    * @Function: findCasePowers()
    * @Description: 查询案件职权
    *
    * @param: cPowerModel
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年3月9日 上午11:41:42 
    *
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<CaseCommonPower> findCasePowers(int start, int length, CaseCommonPowerModel cPowerModel) {
        StringBuffer hql = new StringBuffer();
        //参数集合
        ArrayList list = new ArrayList();
        hql.append(" from CaseCommonPower where id = id ");
        
        if (!TeeUtility.isNullorEmpty(cPowerModel.getCaseId())) {
            hql.append(" and caseCommonPower.id = ? ");
            list.add(cPowerModel.getCaseId());
        }
        if (!TeeUtility.isNullorEmpty(cPowerModel.getPowerId())) {
            hql.append(" and powerId in (?) ");
            list.add(cPowerModel.getPowerId().trim().replace(",", "','"));
        }
        Object[] params = list.toArray();
        return super.pageFind(hql.toString(), start, length, params);
    }
}
