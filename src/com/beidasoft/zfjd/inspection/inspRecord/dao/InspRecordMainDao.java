package com.beidasoft.zfjd.inspection.inspRecord.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.inspection.inspRecord.bean.InspRecordMain;
import com.beidasoft.zfjd.inspection.inspRecord.model.InspRecordMainModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 职权基础信息表DAO类
 */
@Repository
public class InspRecordMainDao extends TeeBaseDao<InspRecordMain> {
    /**
     * @author lrn
     * @description 检查单管理分页查询
     * @param start
     * @param length
     * @param inspRecordMainModel
     * @return
     */
    public List<InspRecordMain> listByPage(int start, int length, InspRecordMainModel inspRecordMainModel) {
        String hql = "from InspRecordMain where isDelete = 0 ";
        if (!TeeUtility.isNullorEmpty(inspRecordMainModel.getSubjectId())) {
            // 执法主体账号
            hql = hql + "and subjectId = '" + inspRecordMainModel.getSubjectId() + "' ";
        } else {
            // 执法部门账号
            if (!TeeUtility.isNullorEmpty(inspRecordMainModel.getDepartmentId())) {
                hql = hql + "and departmentId = '" + inspRecordMainModel.getDepartmentId() + "' ";
            } else {
                return null;
            }
        }
        // 确认执法系统
//        if (inspRecordMainModel.getOrgSys() != null) {
//            String[] orgSysBuff = inspRecordMainModel.getOrgSys().split(",");
//            if (orgSysBuff.length > 0) {
//                hql = hql + "and orgSys in ('empty'";
//                for (String orgSys : orgSysBuff) {
//                    hql = hql + ", '" + orgSys + "' ";
//                }
//                hql = hql + ") ";
//            } else {
//                return null;
//            }
//        }
        List<InspRecordMain> inspRecordMain =pageFind(hql, start, length, null);
        return inspRecordMain;
    }

    /**
     * 
     * @Function: PowerDao.java
     * @Description: 分页查询总数
     *
     * @param: 分页查询的总数
     * @return：返回结果描述
     * @throws：异常描述
     *
     * @author: hoax
     * @date: 2019年1月3日 下午3:58:29
     *
     */
    public long listCount(InspRecordMainModel inspRecordMainModel) {
        String hql = "select count(id) from InspRecordMain where isDelete = 0 ";
        if (!TeeUtility.isNullorEmpty(inspRecordMainModel.getSubjectId())) {
            // 执法主体账号
            hql = hql + " and subjectId = '" + inspRecordMainModel.getSubjectId() + "' ";
        } else {
            // 执法部门账号
            if (!TeeUtility.isNullorEmpty(inspRecordMainModel.getDepartmentId())) {
                hql = hql + " and departmentId = '" + inspRecordMainModel.getDepartmentId() + "' ";
            } else {
                return 0L;
            }
        }
        return count(hql, null);
    }
    /**
     * @author lrn
     * @description 删除检查单（修改isDelete状态）
     * @param idArray
     * @param isDelete
     */
    public void inspRecordDel(String[] idArray, Integer isDelete) {
        Session session = this.getSession();
        StringBuffer hql = new StringBuffer("update InspRecordMain set isDelete = :isDelete where id in (:ids)");
        try {
            Query query = session.createQuery(hql.toString());
            query.setParameter("isDelete", isDelete);
            query.setParameterList("ids", idArray);
            query.executeUpdate();
        } catch (HibernateException e) {
        }
    }
}
