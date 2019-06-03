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
import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonStaff;
import com.tianee.webframe.dao.TeeBaseDao;

/**   
* 2019 
* @ClassName: CaseCommonStaffDao.java
* @Description: 一般案件的执法人员信息表DAO类
*
* @author: songff
* @date: 2019年1月16日 上午12:08:07 
*
*/
@Repository
public class CaseCommonStaffDao extends TeeBaseDao<CaseCommonStaff> {

    /**
     * 
    * @Function: saveBatchCaseStaff()
    * @Description: 批量保存案件执法人员信息
    *
    * @param: caseStaffs 执法人员集合参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月16日 下午6:03:51 
    *
     */
    public void saveBatchCaseStaff(List<CaseCommonStaff> caseStaffs) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for(int i = 0; i < caseStaffs.size(); i++) {
            session.save(caseStaffs.get(i));
        }
        tx.commit();
        session.close();
    }
    /**
     * 
    * @Function: deleteCaseStaff()
    * @Description: 通过案件ID，删除子表，执法人员信息
    *
    * @param: cBase 参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月16日 下午6:01:33 
    *
     */
    public void deleteCaseStaff(CaseCommonBase cBase) {
        StringBuffer hql = new StringBuffer();
        hql.append(" delete from CaseCommonStaff where caseCommonStaff.id = '"+cBase.getId()+"'");
        deleteOrUpdateByQuery(hql.toString(), null);
    }
    
}
