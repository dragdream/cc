package com.beidasoft.zfjd.caseManager.simpleCase.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.caseManager.simpleCase.bean.CaseSimpleStaff;
import com.beidasoft.zfjd.caseManager.simpleCase.model.CaseSimpleStaffModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class CaseSimpleStaffDao extends TeeBaseDao<CaseSimpleStaff> {

    /**
     ** 分页查询列表
     * 
     * @param start
     * @param length
     * @param cbModel
     * @return
     */
    public List<CaseSimpleStaff> findListByPage(int start, int length, CaseSimpleStaffModel simpleStaffModel) {
        // 查询SQL字符串
        StringBuffer hql = new StringBuffer();
        hql.append(" from CaseSimpleStaff where 1=1 ");
        if (!TeeUtility.isNullorEmpty(simpleStaffModel.getCaseId())) {
            hql.append(" and caseId = '" + simpleStaffModel.getCaseId() + "' ");
        }
        return super.pageFind(hql.toString(), start, length, null);
    }

    /**
     ** 分页查询记录数
     * 
     * @param simpleBaseModel
     * @return
     */
    public long listCount(CaseSimpleStaffModel simpleStaffModel) {
        // 查询SQL字符串
        StringBuffer hql = new StringBuffer();
        hql.append("select count(*) from CaseSimpleStaff where 1=1");
        if (!TeeUtility.isNullorEmpty(simpleStaffModel.getCaseId())) {
            hql.append(" and caseId = '" + simpleStaffModel.getCaseId() + "' ");
        }
        return super.count(hql.toString(), null);
    }

    /**
     ** 根据案件主表id查询
     * 
     * @param caseId
     * @return
     */
    public List<CaseSimpleStaff> getListByCaseId(String caseId) {
        return super.find("from CaseSimpleStaff where caseId = '" + caseId + "' ");
    }

    /**
     ** 根据主表id删除
     * 
     * @param caseId
     */
    public void deleteByCaseId(String caseId) {
        String hql = "delete from CaseSimpleStaff where caseId = '" + caseId + "'";
        executeUpdate(hql, null);
    }
}
