package com.beidasoft.zfjd.inspection.inspList.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.inspection.inspList.bean.InspListBase;
import com.beidasoft.zfjd.inspection.inspList.model.InspListBaseModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 执法检查DAO类
 */
@Repository
public class InspListBaseDao extends TeeBaseDao<InspListBase> {

    /**
     * 
     * @Function: PowerDao.java
     * @Description: 分页查询职权列表
     *
     * @param: 职权列表信息
     * @return：返回结果描述
     * @throws：异常描述
     *
     * @author: hoax
     * @date: 2019年1月3日 下午3:57:56
     *
     */
    public List<InspListBase> listByPage(int start, int length, InspListBaseModel inspListBaseModel) {
        String hql = "from InspListBase where isDelete = 0  ";
        // 检查项名称查询条件
        if (inspListBaseModel.getListName() != null) {
            hql = hql + "and listName like '%" + inspListBaseModel.getListName() + "%' ";
        }
     // 确认执法系统
        if (inspListBaseModel.getOrgSys() != null) {
            String[] orgSysBuff = inspListBaseModel.getOrgSys().split(",");
            if (orgSysBuff.length > 0) {
                hql = hql + "and orgSys in ('empty'";
                for (String orgSys : orgSysBuff) {
                    hql = hql + ", '" + orgSys + "' ";
                }
                hql = hql + ") ";
            } else {
                return null;
            }
        }
        if (inspListBaseModel.getCtrlType() != null) {
            if ("10".equals(inspListBaseModel.getCtrlType())) {
                // 主管理员查询
                // 不可看到子管理员未提交的检查单模版
                hql = hql + "and (createType = '10' or (createType = '20' and currentState = 1))";
                
            } else if ("20".equals(inspListBaseModel.getCtrlType())) {
                // 子管理员查询(暂定子管理员必须为执法主体)能看到自己创建的检查的模版，同
                if (inspListBaseModel.getLoginSubId() != null) {
                    hql = hql + "and ((createType = '10' and currentState = 1) or (createType = '20' ";
                    hql = hql + "and createSubjectId = '" + inspListBaseModel.getLoginSubId() + "' ))";
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }

        return pageFind(hql, start, length, null);
    }

    /**
     * @author lrn
     * @description 获取检查单模版详情
     * @param inspListBaseModel
     * @return
     */

    public List<InspListBase> getValidInspLists(InspListBaseModel inspListBaseModel) {
        List<Object> param = new ArrayList<>();
        String hql = "from InspListBase where isDelete = 0 and currentState = 1";
        // 检查项名称查询条件
//        if (inspListBaseModel.getListName() != null) {
//            hql = hql + "and listName like '%" + inspListBaseModel.getListName() + "%' ";
//        }
//        // (执法主体)
//        if (inspListBaseModel.getLoginSubId() != null) {
//            hql = hql + "and (createType = '10' or (createType = '20' ";
//            hql = hql + "and createSubjectId = '" + inspListBaseModel.getLoginSubId() + "' ))";
//        } else {
//            return null;
//        }

        // 确认执法系统
        if (inspListBaseModel.getOrgSys() != null) {
            String[] orgSysBuff = inspListBaseModel.getOrgSys().split(",");
            if (orgSysBuff.length > 0) {
                hql = hql + " and orgSys in ('empty'";
                for (String orgSys : orgSysBuff) {
                    hql = hql + ", '" + orgSys + "' ";
                }
                hql = hql + ") ";
            } else {
                return null;
            }
        }
        return find(hql, param.toArray());
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
    public long listCount(InspListBaseModel inspListBaseModel) {
        String hql = "select count(id) from InspListBase where isDelete = 0 ";
        // 检查项名称查询条件
        if (inspListBaseModel.getListName() != null) {
            hql = hql + "and listName like '%" + inspListBaseModel.getListName() + "%' ";
        }
        
        if (inspListBaseModel.getCtrlType() != null) {
            if ("10".equals(inspListBaseModel.getCtrlType())) {
                // 主管理员查询
                // 不可看到子管理员未提交的检查单模版
                hql = hql + "and (createType = '10' or (createType = '20' and currentState = 1))";
            } else if ("20".equals(inspListBaseModel.getCtrlType())) {
                // 子管理员查询(暂定子管理员必须为执法主体)
                if (inspListBaseModel.getLoginSubId() != null) {
                    hql = hql + "and ((createType = '10' and currentState = 1) or (createType = '20' ";
                    hql = hql + "and createSubjectId = '" + inspListBaseModel.getLoginSubId() + "' ))";
                } else {
                    return 0L;
                }
            } else {
                return 0L;
            }
        }
        // 确认执法系统
        if (inspListBaseModel.getOrgSys() != null) {
            String[] orgSysBuff = inspListBaseModel.getOrgSys().split(",");
            if (orgSysBuff.length > 0) {
                hql = hql + "and orgSys in ('empty'";
                for (String orgSys : orgSysBuff) {
                    hql = hql + ", '" + orgSys + "' ";
                }
                hql = hql + ") ";
            } else {
                return 0L;
            }
        }
        return count(hql, null);
    }
    /**
     * @author lrn
     * @description 更新检查单模版状态
     * @param ids
     * @param currentState
     * @return
     */
	public Boolean updateListState(String[] ids, Integer currentState) {
		Boolean flag = false;
		String hql = " update InspListBase set currentState = :currentState where id in (:ids)";
		try {
			Session session = this.getSession();
			Query query = session.createQuery(hql);
			query.setParameter("currentState", currentState);
			query.setParameterList("ids", ids);
			query.executeUpdate();
			flag = true;
		} catch (HibernateException e) {
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	/**
	 * @author lrn
	 * @description 批量删除已提交的检查单模版
	 * @param id
	 * @param isDelete
	 */
    public void inspListDel(String[] idArray, Integer isDelete) {
        // TODO Auto-generated method stub
        Session session = this.getSession();
        StringBuffer hql = new StringBuffer("update InspListBase set isDelete = :isDelete where currentState = 1 and id in(:ids)");
        try {
            Query query = session.createQuery(hql.toString());
            query.setParameter("isDelete", isDelete);
            query.setParameterList("ids", idArray);
            query.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * @author lrn
     * @description 获取未提交的检查单模版的id，并删除检查单模版 
     * @param ids
     */
    @SuppressWarnings("unchecked")
    public List<String> inspListDelete(String[] idArray) {
        Session session = this.getSession();
        List<String> deleteIds= new ArrayList<>();
        String hql1 = "select id from InspListBase where currentState = 0 and id in(:ids)";
        try {
            Query query1 = session.createQuery(hql1);
            deleteIds = query1.setParameterList("ids", idArray).list();
        } catch (Exception e) {
        }
        if(deleteIds.size()>0){
            StringBuffer hql = new StringBuffer("delete from InspListBase where currentState = 0 and id in(:ids)");
            try {
                Query query = session.createQuery(hql.toString());
                query.setParameterList("ids", idArray);
                query.executeUpdate();
            } catch (HibernateException e) {
                System.out.println(e.getMessage());
            }
        }
        return deleteIds;
    }
    /**
     * @author lrn
     * @description id控制权限
     * @param inspListBaseModel
     * @return
     */
    public List<?> idsCtrl(InspListBaseModel inspListBaseModel) {
        List<?> idList = new ArrayList<>();
        Session session = this.getSession();
        String [] idArray = inspListBaseModel.getId().split(",");
        StringBuilder hql = new StringBuilder(" select id from InspListBase where id in (:ids) and id not in ("
                + " select id from InspListBase where isDelete = 0 ");
        if (!TeeUtility.isNullorEmpty(inspListBaseModel.getOrgSys())) {
            
            hql.append( "and orgSys in (:orgSysBuff)");
        }
        if (inspListBaseModel.getCtrlType() != null) {
            if ("10".equals(inspListBaseModel.getCtrlType())) {
                // 主管理员查询
                // 主管理员只能对自己创建的检查单进行操作
                hql.append("and createType = '10'");
                
            } else if ("20".equals(inspListBaseModel.getCtrlType())) {
                // 子管理员查询(暂定子管理员必须为执法主体)只能对自己创建的检查的模版操作
                if (inspListBaseModel.getLoginSubId() != null) {
                    hql.append( "and createType = '20' and createSubjectId = '" + inspListBaseModel.getLoginSubId() + "'");
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
            
        hql.append(")");  
        try {
            Query query = session.createQuery(hql.toString());
            query.setParameterList("ids", idArray);
            if (!TeeUtility.isNullorEmpty(inspListBaseModel.getOrgSys())) {
                String[] orgSysBuff = inspListBaseModel.getOrgSys().split(",");
                query.setParameterList("orgSysBuff", orgSysBuff);
            }
            idList = query.list();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
            
        return idList;
    }
}
