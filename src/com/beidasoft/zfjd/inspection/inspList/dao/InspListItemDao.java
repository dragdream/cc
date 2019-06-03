package com.beidasoft.zfjd.inspection.inspList.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.inspection.inspItem.bean.InspectItem;
import com.beidasoft.zfjd.inspection.inspList.bean.InspListBase;
import com.beidasoft.zfjd.inspection.inspList.bean.InspListItem;
import com.beidasoft.zfjd.inspection.inspList.model.InspListBaseModel;
import com.tianee.webframe.dao.TeeBaseDao;

/**
 * 执法检查DAO类
 */
@Repository
public class InspListItemDao extends TeeBaseDao<InspListItem> {

    public void deleteRelateByListId(InspListBase beanInfo, String[] moduleIds) {
        StringBuffer hql = new StringBuffer();
        boolean optFlag = false;
        hql.append(" delete from InspListItem where ");
        if (beanInfo != null && beanInfo.getId() != null) {
            hql.append(" inspListId = '" + beanInfo.getId() + "' ");
            optFlag = true;
        }
        if (optFlag) {
            deleteOrUpdateByQuery(hql.toString(), null);
        }
    }
    
    /**
     * @author lrn
     * @description 创建检查单时关联项
     * @param beanInfo
     * @param itemList
     */
    public void saveListRelate(InspListBase beanInfo, List<InspectItem> itemList) {
        if(itemList != null && itemList.size() > 0){
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            Date nowDate = new Date();
            for(InspectItem item : itemList){
                InspListItem node = new InspListItem();
                //将 gistModel 赋值 gist
                node.setId(UUID.randomUUID().toString());
                node.setInspListId(beanInfo.getId());
                node.setInspModuleId(item.getModuleId());
                node.setInspItemId(item.getId());
                node.setInspItemName(item.getItemName());
                node.setCreateDate(nowDate);
                session.save(node);
            }
            tx.commit();
            session.close();
        }
    }
    /**
     * @author lrn
     * @description 通过检查单模版id获取检查项(主管理员可以获取全部，子管理员只能获取主管理员创建的以及自己创建的)
     * @param inspListBaseModel
     * @return
     */
    public List<InspListItem> getRelatedItemsByListId(InspListBaseModel inspListBaseModel) {
        List<Object> param = new ArrayList<>();
        if(inspListBaseModel != null && !"".equals(inspListBaseModel.getId())){
            String hql = "from InspListItem where inspListId = '"+inspListBaseModel.getId()+"' ";
            return find(hql, param.toArray());
        }else{
            return null;
        }
    }
    /**
     * @author lrn
     * @description 通过检查单id，以及检查项id获取检查项
     * @param id
     * @return
     */
	public InspListItem getById (String inspListItemId){
	    Session session = this.getSession();
	    String hql = " from InspListItem where id = ?";
	    Query query = session.createQuery(hql);
	    query.setParameter(0, inspListItemId);
	    InspListItem inspListItem = (InspListItem) query.uniqueResult();
	    return inspListItem;
	}
}
