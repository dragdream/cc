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
import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonPunish;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonPunishModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

/**   
* 2019 
* @ClassName: CaseCommonPunishDao.java
* @Description: 一般案件的处罚依据信息表DAO类
*
* @author: songff
* @date: 2019年1月16日 上午12:08:07 
*
*/
@Repository
public class CaseCommonPunishDao extends TeeBaseDao<CaseCommonPunish> {
    
    /**
     * 
    * @Function: saveBatchCasePunish()
    * @Description: 批量保存作出处罚案件信息
    *
    * @param: caseNopunishments 不予处罚案件集合参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月16日 下午6:03:51 
    *
     */
    public void saveBatchCasePunish(List<CaseCommonPunish> casePunishs) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for(int i = 0; i < casePunishs.size(); i++) {
            session.save(casePunishs.get(i));
        }
        tx.commit();
        session.close();
    }
    /**
     * 
    * @Function: deleteCasePunish()
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
    public void deleteCasePunish(CaseCommonBase cBase) {
        StringBuffer hql = new StringBuffer();
        hql.append(" delete from CaseCommonPunish where caseCommonPunish.id = '"+cBase.getId()+"'");
        deleteOrUpdateByQuery(hql.toString(), null);
    }
    
    /**
     * 
    * @Function: findCasePunishs();
    * @Description: 查询处罚依据
    *
    * @param: cPunishModel
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年3月9日 上午11:44:57 
    *
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<CaseCommonPunish> findCasePunishs(int start, int length, CaseCommonPunishModel cPunishModel) {
        //hql语句
        StringBuffer hql = new StringBuffer();
        //参数集合
        ArrayList list = new ArrayList();
        
        hql.append(" from CaseCommonPunish where id = id");
        //依据ID
        if (!TeeUtility.isNullorEmpty(cPunishModel.getGistId())) {
            hql.append(" and gistId in (?) ");
            list.add(cPunishModel.getGistId().trim().replace(",", "','"));
        }
        //案件ID
        if (!TeeUtility.isNullorEmpty(cPunishModel.getCaseId())) {
            hql.append(" and caseCommonPunish.id = ? ");
            list.add(cPunishModel.getCaseId());
        }
        Object[] params = list.toArray();
        return super.pageFind(hql.toString(), start, length, params);
    }
}
