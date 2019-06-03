package com.beidasoft.zfjd.inspection.inspItem.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.inspection.inspItem.bean.InspectItem;
import com.beidasoft.zfjd.inspection.inspItem.model.InspectItemModel;
import com.beidasoft.zfjd.inspection.inspModule.bean.InspModule;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

/**
 */
@Repository
public class InspectItemDao extends TeeBaseDao<InspectItem> {
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
    public List<InspectItem> listByPage(int start, int length, InspectItemModel inspectitemModel) {
        String hql = "from InspectItem where isDelete = 0  ";
        // 检查项名称查询条件
        if (!TeeUtility.isNullorEmpty(inspectitemModel.getItemName())) {
            hql = hql + "and itemName like '%" + inspectitemModel.getItemName() + "%' ";
        }
        
        if (inspectitemModel.getCreateType() != null) {
            if ("10".equals(inspectitemModel.getCtrlType())) {
                // 主管理员查询
                // 暂时不做特殊处理
            } else if ("20".equals(inspectitemModel.getCtrlType())) {
                // 子管理员查询(暂定子管理员必须为执法主体)
                if (inspectitemModel.getLoginSubId() != null) {
                    hql = hql + "and (createType = '10' or (createType = '20' ";
                    hql = hql + "and createSubjectId = '" + inspectitemModel.getLoginSubId() + "' ))";
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
        // 确认执法系统
        if (inspectitemModel.getOrgSys() != null) {
            String[] orgSysBuff = inspectitemModel.getOrgSys().split(",");
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
        List<InspectItem>  inspectItem= pageFind(hql, start, length, null);
        return inspectItem;
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
    public long listCount(InspectItemModel inspectitemModel) {
        String hql = "select count(id) from InspectItem where isDelete = 0 ";
        // 检查项名称查询条件
        if (!TeeUtility.isNullorEmpty(inspectitemModel.getItemName())) {
            hql = hql + "and itemName like '%" + inspectitemModel.getItemName() + "%' ";
        }

        if (inspectitemModel.getCreateType() != null) {
            if ("10".equals(inspectitemModel.getCtrlType())) {
                // 主管理员查询
                // 暂时不做特殊处理
            } else if ("20".equals(inspectitemModel.getCtrlType())) {
                // 子管理员查询(暂定子管理员必须为执法主体)
                if (inspectitemModel.getLoginSubId() != null) {
                    hql = hql + "and (createType = '10' or (createType = '20' ";
                    hql = hql + "and createSubjectId = '" + inspectitemModel.getLoginSubId() + "' ))";
                } else {
                    return 0L;
                }
            } else {
                return 0L;
            }
        }
        // 确认执法系统
        if (inspectitemModel.getOrgSys() != null) {
            String[] orgSysBuff = inspectitemModel.getOrgSys().split(",");
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
    
    public List<InspectItem> listValidByModuleIds(String[] moduleIds) {
        String hql = "from InspectItem where isDelete = 0 ";
        // 检查项名称查询条件
        if (moduleIds != null && moduleIds.length > 0) {
            hql = hql + "and moduleId in ('empty'";
            for(String id : moduleIds){
                hql = hql + ",'" + id + "' ";
            }
            hql = hql + ") ";
        }else{
            return null;
        }
        return find(hql, null);
    }
//    /**
//     * 通过id获取检查项
//     * @param id
//     * @return
//     */
//    public InspectItem getById(String id){
//    	Session session = this.getSession();
//    	String hql = " from InspectItem where id = ?";
//    	Query query = session.createQuery(hql);
//    	InspectItem inspectItem = (InspectItem) query.setParameter(0,id);
//		return inspectItem;
//    }
    /**
     * @author lrn
     * @description 修改检查项的执法系统
     * @param beanInfo
     */
	public void updateItem(InspModule beanInfo) {
		Session session = this.getSession();
		String hql = "update InspectItem set orgSys = :orgSys where moduleId = :moduleId";
		Query query = session.createQuery(hql);
		query.setParameter("orgSys", beanInfo.getOrgSys());
		query.setParameter("moduleId", beanInfo.getId());
		query.executeUpdate();
	}
	
	/**
	 * @author lrn
	 * @description 修改检查项：isDelete
	 * @param beanInfo
	 */
	public void deteleItem(String ids) {
	    String[] idArray = ids.split(",");
        Session session = this.getSession();
        String hql = "update InspectItem set isDelete = 1 where moduleId in(:moduleId)";
        Query query = session.createQuery(hql);
        query.setParameterList("moduleId", idArray);
        query.executeUpdate();
    }
	/**
	 * @author lrn
	 * @description 通过id对检查
	 * @param inspItemModel
	 */
    public void inspListDel(InspectItemModel inspItemModel) {
        String[] idArray = null;
        if(!TeeUtility.isNullorEmpty(inspItemModel.getId())){
            idArray = inspItemModel.getId().split(",");
        }
        String hql = " update InspectItem set isDelete = 1 where id in (:idArray)";
        Session session = this.getSession();
        Query query = session.createQuery(hql);
        query.setParameterList("idArray", idArray);
        query.executeUpdate();
    }
    
    /**
     * @author lrn
     * @description 控制检查项权限
     * @param inspListBaseModel
     * @return
     */
    public List<?> idsCtrl(InspectItemModel inspectItemModel) {
        List<?> idList = new ArrayList<>();
        Session session = this.getSession();
        String [] idArray = inspectItemModel.getId().split(",");
        StringBuilder hql = new StringBuilder(" select id from InspectItem where id in (:ids) and id not in ("
                + " select id from InspectItem where isDelete = 0 ");
        if (!TeeUtility.isNullorEmpty(inspectItemModel.getOrgSys())) {
            
            hql.append( "and orgSys in (:orgSysBuff)");
        }
        if (inspectItemModel.getCtrlType() != null) {
            if ("10".equals(inspectItemModel.getCtrlType())) {
                // 主管理员查询
                // 主管理员只能对自己创建的检查单进行操作
                hql.append("and createType = '10'");
                
            } else if ("20".equals(inspectItemModel.getCtrlType())) {
                // 子管理员查询(暂定子管理员必须为执法主体)只能对自己创建的检查的模版操作
                if (inspectItemModel.getLoginSubId() != null) {
                    hql.append( "and createType = '20' and createSubjectId = '" + inspectItemModel.getLoginSubId() + "'");
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
            if (!TeeUtility.isNullorEmpty(inspectItemModel.getOrgSys())) {
                String[] orgSysBuff = inspectItemModel.getOrgSys().split(",");
                query.setParameterList("orgSysBuff", orgSysBuff);
            }
            idList = query.list();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
            
        return idList;
    }
}

