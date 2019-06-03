package com.beidasoft.zfjd.permission.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.permission.bean.PermissionItemGist;
import com.tianee.webframe.dao.TeeBaseDao;

/**
 * @ClassName: PermissionItemGistDao.java
 * @Description: 许可事项关联依据DAO层
 *
 * @author: mixue
 * @date: 2019年3月6日 下午4:25:01
 */
@Repository
public class PermissionItemGistDao extends TeeBaseDao<PermissionItemGist> {

    /**
     * 
     * @Function: savePermissionItemGists
     * @Description: 批量新增许可事项关联依据
     *
     * @param permissionItemGists
     *
     * @author: mixue
     * @date: 2019年3月7日 下午5:34:12
     */
    public void savePermissionItemGists(List<PermissionItemGist> permissionItemGists) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for (int i = 0; i < permissionItemGists.size(); i++) {
            session.save(permissionItemGists.get(i));
        }
        tx.commit();
        session.close();
    }

    /**
     * 
     * @Function: deletePermissionItemGist
     * @Description: 删除单个事项关联依据
     *
     * @param id
     *
     * @author: mixue
     * @date: 2019年3月7日 下午5:34:51
     */
    public void deletePermissionItemGist(String id) {
        StringBuffer hql = new StringBuffer();
        hql.append(" delete from PermissionItemGist where permissionItem.id = '" + id + "'");
        deleteOrUpdateByQuery(hql.toString(), null);
    }
}
