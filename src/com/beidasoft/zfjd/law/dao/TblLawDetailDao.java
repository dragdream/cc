package com.beidasoft.zfjd.law.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.law.bean.TblLawDetail;
import com.beidasoft.zfjd.law.model.TblLawDetailModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 职权基础信息表DAO类
 */
//@Repository
@Repository("detailDao")
public class TblLawDetailDao extends TeeBaseDao<TblLawDetail> {
    
    @Autowired
    private TeeDeptDao dao;
        
     public List<TblLawDetail>  findUsers(){
          return super.find("from TblLawDetail", null);      
     }
          
      public List<TblLawDetail> listByPage(int firstResult,int rows,TblLawDetailModel queryModel){
          String hql = " from TblLawDetail where 1=1 and isDelete = 0";
          
          if(!TeeUtility.isNullorEmpty(queryModel.getId()))
          {
              hql +="and law_id = '"+queryModel.getId()+"'";  
          }
          
          if(!TeeUtility.isNullorEmpty(queryModel.getLawName()))
          {
              hql +="and lawName like '%"+queryModel.getLawName()+"%'";  
          }
          if(!TeeUtility.isNullorEmpty(queryModel.getDetailStrip())) {
              hql +="and detailStrip = '"+queryModel.getDetailStrip() + "'";  
          }
          if(!TeeUtility.isNullorEmpty(queryModel.getDetailFund())) {
              hql +="and detailFund = '"+queryModel.getDetailFund() + "'";  
          }
          if(!TeeUtility.isNullorEmpty(queryModel.getDetailItem())) {
              hql +="and detailItem = '"+queryModel.getDetailItem() + "'";  
          }
          if(!TeeUtility.isNullorEmpty(queryModel.getContent())) {
              hql +="and content like '%"+queryModel.getContent() + "%'";  
          }
          
        return super.pageFind(hql, firstResult, rows, null);
          
      }
      
      public long getTotal(){
          return super.count("select count(id) from TblLawDetail",null);
      }
      
      public long getTotal(TblLawDetailModel queryModel){
          String hql = "select count(id) from TblLawDetail where 1=1 and isDelete = 0";
          
          if(!TeeUtility.isNullorEmpty(queryModel.getId()))
          {
              hql +="and law_id = '"+queryModel.getId()+"'";  
          }
          
          if(!TeeUtility.isNullorEmpty(queryModel.getLawName()))
          {
              hql +="and lawName like '%"+queryModel.getLawName()+"%'";  
          }

          if(!TeeUtility.isNullorEmpty(queryModel.getDetailStrip())) {
              hql +="and detailStrip = '"+queryModel.getDetailStrip() + "'";  
          }
          if(!TeeUtility.isNullorEmpty(queryModel.getDetailFund())) {
              hql +="and detailFund = '"+queryModel.getDetailFund() + "'";  
          }
          if(!TeeUtility.isNullorEmpty(queryModel.getDetailItem())) {
              hql +="and detailItem = '"+queryModel.getDetailItem() + "'";  
          }
          if(!TeeUtility.isNullorEmpty(queryModel.getContent())) {
              hql +="and content like '%"+queryModel.getContent() + "%'";  
          }
           return super.count(hql,null);
      }
      
      /**
         * 根据部门名称判断部门是否存在 ---可扩展至上级部门条件--- 可扩展多级，以斜杠分割
         * 
         * @author syl
         * @date 2013-11-14
         * @param deptName
         * @param parentDeptName
         * @return
         */
      public Map<String, Object> checkExistedDept(String deptName, String parentDeptName) {
          String hql = "from TeeDepartment where deptName = ? and isDelete = 0";
          // String[] values = {deptName.trim()};
          List<Object> list = new ArrayList<Object>();
          list.add(deptName);
          TblLawDetail dept = null;
          Map<String, Object> map = new HashMap<String, Object>();
          long count = 0;
          if (!TeeUtility.isNullorEmpty(parentDeptName.trim())) {
              /*
               * String parentDeptNameArray[] = parentDeptName.split("/");//多级查询
               * for (int i = 0; i < parentDeptNameArray.length; i++) { dept =
               * getDeptByName(parentDeptNameArray[i]); if(dept == null){ break; }
               * }
               */
              dept = getParentDeptByFullName(parentDeptName);
              if (dept != null) {
                  hql = hql + " and deptParent.uuid = ? ";
                  list.add(dept.getId());
              }
          } else {
              hql = hql + " and deptParent is null ";
          }
          hql = "select count(*) " + hql;
          count = countByList(hql, list);
          map.put("parentDept", dept);
          if (count > 0) {
              map.put("exist", true);
          } else {
              map.put("exist", false);
          }
          return map;
      }
      public TblLawDetail getParentDeptByFullName(String deptFullName) {
          if (!deptFullName.startsWith("/")) {
              deptFullName = "/" + deptFullName;
          }
          String hql = "from TeeDepartment where deptFullName = ? and isDelete = 0";
          String[] values = {deptFullName.trim()};
          List<TblLawDetail> list = executeQuery(hql, values);
          if (list.size() > 0) {
              return list.get(0);
          }
          return null;
      }
      public List getDeptFullIdsByDeptIds(String deptIds) {
          return executeQuery("select deptFullId from TeeDepartment where " + TeeDbUtility.IN("uuid", deptIds), null);
      }

      /**
       * 
      * @Function: TblLawDetailDao.java
      * @Description: 通过依据ID查询依据
      *
      * @param:描述1描述
      * @return：返回结果描述
      * @throws：异常描述
      *
      * @author: chenq
      * @date: 2019年1月2日 下午2:50:57 
      *
       */
      public List<TblLawDetail> getLawDetailByIds(TblLawDetailModel lawDetailModel) {
          String hql = " from TblLawDetail where isDelete = 0 " ;
          
          if(lawDetailModel.getIds() != null && !"".equals(lawDetailModel.getIds())) {
              hql = hql + "and id in (" + lawDetailModel.getIds() + ")";
          }
          
          return super.find(hql + " order by lawDetailIndex ", null);
      }
}
