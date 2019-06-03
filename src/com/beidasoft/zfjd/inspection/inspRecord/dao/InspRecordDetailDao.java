package com.beidasoft.zfjd.inspection.inspRecord.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.inspection.inspRecord.bean.InspRecordDetail;
import com.beidasoft.zfjd.inspection.inspRecord.model.InspRecordMainModel;
import com.tianee.webframe.dao.TeeBaseDao;

/**
 * 职权基础信息表DAO类
 */
@Repository
public class InspRecordDetailDao extends TeeBaseDao<InspRecordDetail> {
    /**
     * @author lrn
     * @description 
     * @param beanInfo
     */
    public void deleteDetailsByMainId(InspRecordMainModel beanInfo) {
        StringBuffer hql = new StringBuffer();
        boolean optFlag = false;
        hql.append(" delete from InspRecordDetail where ");
        if (beanInfo != null && beanInfo.getId() != null) {
            hql.append(" recordMainId = '" + beanInfo.getId() + "' ");
            optFlag = true;
        }
        if (optFlag) {
            deleteOrUpdateByQuery(hql.toString(), null);
        }
    }
    /**
     * @author lrn
     * @description 存储检查单项
     * @param beanInfo
     * @param details
     */
    public void saveDetails(InspRecordMainModel beanInfo, List<InspRecordDetail> details) {
        if(details != null && details.size() > 0){
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
//            Date nowDate = new Date();
            for(InspRecordDetail item : details){
                session.save(item);
            }
            tx.commit();
            session.close();
        }
    }
    /**
     * @author lrn
     * @description 通过检查单id获取检查项
     * @return
     */
    public List<InspRecordDetail> getById(String id){
        List<Object> param = new ArrayList<>();
        String hql = "from InspRecordDetail where recordMainId = ?";
        param.add(id);
        return find(hql,param.toArray());
    }
//    public List<?> getById(String id){
//        List<Object> param = new ArrayList<>();
//        String hql = "select ird.inspItemId,ird.isInspectionPass,ili.inspItemName from InspRecordDetail ird left join InspListItem ili on ird.inspItemId = ili.inspItemId where recordMainId = ?";
//        param.add(id);
//        return find(hql,param.toArray());
//    }
    
}
