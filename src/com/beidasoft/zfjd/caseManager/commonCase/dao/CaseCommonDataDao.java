package com.beidasoft.zfjd.caseManager.commonCase.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonData;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonDataModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * @ClassName: CaseCommonDataDao.java
 * @Description: 
 *
 * @author: mixue
 * @date: 2019年3月20日 上午11:28:35
 */
@Repository("caseCommonDataDao")
public class CaseCommonDataDao extends TeeBaseDao<CaseCommonData> {

    public List<CaseCommonData> findListByPage(CaseCommonDataModel caseCommonDataModel) {
        // TODO Auto-generated method stub
        Object[] object = getHql(caseCommonDataModel);
        @SuppressWarnings("unchecked")
        List<CaseCommonData> caseCommonDatas = pageFind(object[0].toString(), 0, 9999, ((List<Object>)object[1]).toArray());
        return caseCommonDatas;
    }
    
    private Object[] getHql(CaseCommonDataModel caseCommonDataModel) {
        // TODO Auto-generated method stub
        Object[] object = new Object[2];
        // 创建HQL查询语句
        StringBuffer hql = new StringBuffer("from CaseCommonData where 1 = 1 ");
        // 创建参数集合
        List<Object> params = new ArrayList<Object>();
        // 判断是否添加查询条件：表名
        if(!TeeUtility.isNullorEmpty(caseCommonDataModel.getTableName())){
            hql.append(" and tableName = ? ");
            params.add(caseCommonDataModel.getTableName().trim());
        }
        // 判断是否添加查询条件：是否固定列
        if(!TeeUtility.isNullorEmpty(caseCommonDataModel.getIsRegular())){
            hql.append(" and isRegular = ? ");
            params.add(caseCommonDataModel.getIsRegular());
        }
        // 判断是否添加查询条件：列名
        if(!TeeUtility.isNullorEmpty(caseCommonDataModel.getColumnName())){
            hql.append(" and columnName = ? ");
            params.add(caseCommonDataModel.getColumnName().trim());
        }
        // 判断是否添加查询条件：是否可查看列
        if(!TeeUtility.isNullorEmpty(caseCommonDataModel.getIsShow())){
            hql.append(" and isShow = ? ");
            params.add(caseCommonDataModel.getIsShow());
        }
        // 判断是否添加查询条件：是否作为查询条件
        if(!TeeUtility.isNullorEmpty(caseCommonDataModel.getIsSelect())){
            hql.append(" and isSelect = ? ");
            params.add(caseCommonDataModel.getIsSelect());
        }
        object[0] = hql;
        object[1] = params;
        return object;
    }
}
