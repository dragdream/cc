package com.beidasoft.zfjd.system.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonBase;
import com.beidasoft.zfjd.system.bean.SysBusinessRelation;
import com.mysql.fabric.jdbc.JDBC4FabricMySQLConnection;
import com.tianee.webframe.dao.TeeBaseDao;

/**
 * 监督业务管理表DAO类
 */
@Repository
public class SysBusinessRelationDao extends TeeBaseDao<SysBusinessRelation> {
	
    public SysBusinessRelation getRelationByBussDeptId(String businessDeptId){
//        Session session = super.getSession();
        //主表tbl_case_common_base 取别名 cBase
//        Criteria crit = session.createCriteria(SysBusinessRelation.class, "relation");
//        crit.add(Restrictions.isNull("relation.businessSubjectId"));
//        crit.add(Restrictions.isNull("relation.businessSupDeptId"));
//        crit.add(Restrictions.eq("relation.businessDeptId", businessDeptId));
        if(businessDeptId != null){
            String hql = "from SysBusinessRelation where businessSubjectId is null ";
            hql = hql + " and businessSupDeptId is null ";
            hql = hql + " and businessDeptId = '"+businessDeptId+"' ";
            List<SysBusinessRelation> results = find(hql, null);
            if(results != null && results.size() > 0){
                return results.get(0);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }
}
