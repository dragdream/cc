package com.beidasoft.zfjd.adminCoercion.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beidasoft.zfjd.adminCoercion.bean.CoercionCaseBasic;
import com.beidasoft.zfjd.adminCoercion.model.CoercionCaseBasicModel;
import com.tianee.webframe.dao.TeeBaseDao;

/**
 * 强制案件基础表DAO类
 */
@Repository
public class CoercionCaseBasicDao extends TeeBaseDao<CoercionCaseBasic> {

    @RequestMapping
    public List<CoercionCaseBasic> getCoercionBaseByOtherSrc(CoercionCaseBasicModel coercionCaseBasicModel){
        String hql = " from CoercionCaseBasic where isDelete = 0 ";
        if(coercionCaseBasicModel.getCaseSourceType() != 0) {
            hql = hql + " and caseSourceType = '" + coercionCaseBasicModel.getCaseSourceType() + "' ";
        }
        if(coercionCaseBasicModel.getCaseSourceId() != null && !"".equals(coercionCaseBasicModel.getCaseSourceId())) {
            hql = hql + " and caseSourceId = '" + coercionCaseBasicModel.getCaseSourceId() + "' ";
        }
        return find(hql);
    }
    
    /**
     * 
     * @Function: CoercionCaseBasicDao.java
     * @Description: 分页查询强制案件列表（对填报）
     *
     * @param: subjectId 执法主体id
     * @return：强制案件列表
     * @throws：异常描述
     *
     * @author: hoax
     * @date: 2019年1月3日 下午3:57:56
     *
     */
    public List<CoercionCaseBasic> listByPage(int start, int length, CoercionCaseBasicModel coercionCaseBasicModel) {
        String hql = "from CoercionCaseBasic where ";
        if (coercionCaseBasicModel.getSubjectId() != null && !"".equals(coercionCaseBasicModel.getSubjectId())) {
            hql = hql + "  subjectId = '" + coercionCaseBasicModel.getSubjectId() + "' ";
        }else{
            return null;
        }

        return pageFind(hql, start, length, null);
    }

    /**
     * 
     * @Function: CoercionCaseBasicDao.java
     * @Description: 分页查询强制案件符合条件总数（对填报）
     *
     * @param: subjectId 执法主体id
     * @return：符合查询条件的案件数量
     * @throws：异常描述
     *
     * @author: hoax
     * @date: 2019年1月3日 下午3:58:29
     *
     */
    public long listCount(CoercionCaseBasicModel coercionCaseBasicModel) {
        String hql = "select count(*) from CoercionCaseBasic where ";
        if (coercionCaseBasicModel.getSubjectId() != null && !"".equals(coercionCaseBasicModel.getSubjectId())) {
            hql = hql + " subjectId = '" + coercionCaseBasicModel.getSubjectId() + "' ";
        }else{
            return 0L;
        }
        return count(hql, null);
    }
    
    /**
     * 
     * @Function: CoercionCaseBasicDao.java
     * @Description: 分页查询强制案件列表（对查询）
     *
     * @param: subjectId 执法主体id
     * @param: organizationId 执法部门id
     * @return：强制案件列表
     * @throws：异常描述
     *
     * @author: hoax
     * @date: 2019年1月3日 下午3:57:56
     *
     */
    public List<CoercionCaseBasic> coercionSearchListByPage(int start, int length, CoercionCaseBasicModel coercionCaseBasicModel) {
        String hql = "from CoercionCaseBasic where ";
        //是否执法主体账号
        if (coercionCaseBasicModel.getSubjectId() != null && !"".equals(coercionCaseBasicModel.getSubjectId())) {
            hql = hql + "  subjectId = '" + coercionCaseBasicModel.getSubjectId() + "' ";
        }else{
            //是否执法部门账号
            if(coercionCaseBasicModel.getDepartmentId() != null && !"".equals(coercionCaseBasicModel.getDepartmentId())){
                hql = hql + "  departmentId = '" + coercionCaseBasicModel.getDepartmentId() + "' ";
            }else{
                //是否监督部门账号
                return null;
            }
        }
        return pageFind(hql, start, length, null);
    }
    
    /**
     * 
     * @Function: CoercionCaseBasicDao.java
     * @Description: 分页查询强制案件符合条件总数（对查询）
     *
     * @param: subjectId 执法主体id
     * @return：符合查询条件的案件数量
     * @throws：异常描述
     *
     * @author: hoax
     * @date: 2019年1月3日 下午3:58:29
     *
     */
    public long coercionSearchListCount(CoercionCaseBasicModel coercionCaseBasicModel) {
        String hql = "select count(*) from CoercionCaseBasic where ";
      //是否执法主体账号
        if (coercionCaseBasicModel.getSubjectId() != null && !"".equals(coercionCaseBasicModel.getSubjectId())) {
            hql = hql + "  subjectId = '" + coercionCaseBasicModel.getSubjectId() + "' ";
        }else{
            //是否执法部门账号
            if(coercionCaseBasicModel.getDepartmentId() != null && !"".equals(coercionCaseBasicModel.getDepartmentId())){
                hql = hql + "  departmentId = '" + coercionCaseBasicModel.getDepartmentId() + "' ";
            }else{
                //是否监督部门账号
                return 0L;
            }
        }
//        testselectB(CoercionCaseBasic.class,"0300","522600","02", 0 , 5);
        return count(hql, null);
    }
    
    public void testSelect(){}
}
