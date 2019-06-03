package com.beidasoft.zfjd.inspection.inspList.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.inspection.inspList.bean.InspListBase;
import com.beidasoft.zfjd.inspection.inspList.bean.InspListModule;
import com.tianee.webframe.dao.TeeBaseDao;

/**
 * 执法检查DAO类
 */
@Repository
public class InspListModuleDao extends TeeBaseDao<InspListModule> {

    /**
     * @author lrn
     * @description 删除的检查单关联的删除模块
     * @param id
     * @return
     */
    public Boolean deleteRelateByListId(String id) {
        Boolean flag = false;
        StringBuffer hql = new StringBuffer("delete from InspListModule where inspListId = :id");
        try {
            Session session = this.getSession();
            Query query = session.createQuery(hql.toString()).setParameter("id", id);
            query.executeUpdate();
            flag = true;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return flag;
    }
    /**
     * @author lrn
     * @description 保存编辑后的检查单模版
     * @param beanInfo
     * @param moduleIds
     */
    public void saveListRelate(InspListBase beanInfo, String[] moduleIds) {
        if(moduleIds != null && moduleIds.length > 0){
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            Date nowDate = new Date();
            for(String moduleId : moduleIds){
                InspListModule node = new InspListModule();
                //将 gistModel 赋值 gist
                node.setId(UUID.randomUUID().toString());
                node.setInspListId(beanInfo.getId());
                node.setInspModuleId(moduleId);
                node.setCreateDate(nowDate);
                session.save(node);
            }
            tx.commit();
            session.close();
        }
    }
    /**
     * 通过检查单id获取检查模块
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<InspListModule> getByListId(String id){
    	StringBuilder hql =  new StringBuilder(" from InspListModule where inspListId = ?");
    	Session session = this.getSession();
    	Query query = session.createQuery(hql.toString()).setParameter(0, id) ;
    	List<InspListModule> listModule = query.list();
    	return listModule;
    }
    /**
     * @author lrn
     * @description 批量删除检查项关联的检查模块
     * @param id
     * @return
     */
    public Boolean deleteRelateByListId(String[] idArray) {
        Boolean flag = false;
        StringBuffer hql = new StringBuffer("delete from InspListModule where inspListId in(:id)");
        try {
            Session session = this.getSession();
            Query query = session.createQuery(hql.toString()).setParameterList("id", idArray);
            query.executeUpdate();
            flag = true;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return flag;
    }
    /**
     * @author lrn
     * @description 通过检查模块id获取关联的检查单模版id
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<String> getByModelId(String moduleIds) {
        String [] moduleIdArray = moduleIds.split(",");
        String hql = "select inspListId from InspListModule where inspModuleId in(:moduleIdArray)";
        Session session = this.getSession();
        Query query = session.createQuery(hql.toString()).setParameterList("moduleIdArray", moduleIdArray);
        List<String> inspListIds = query.list();
        return inspListIds;
    }
    
}
