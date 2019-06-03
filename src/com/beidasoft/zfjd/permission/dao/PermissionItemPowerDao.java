package com.beidasoft.zfjd.permission.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.permission.bean.PermissionItemPower;
import com.tianee.webframe.dao.TeeBaseDao;

/**
 * @ClassName: PermissionItemPowerDao.java
 * @Description: 许可事项关联职权DAO层
 *
 * @author: mixue
 * @date: 2019年3月6日 下午4:24:34
 */
@Repository
public class PermissionItemPowerDao extends TeeBaseDao<PermissionItemPower> {

    /**
     * 
     * @Function: savePermissionItemPowers
     * @Description: 批量新增许可事项关联职权
     *
     * @param permissionItemPowers
     *
     * @author: mixue
     * @date: 2019年3月7日 下午5:38:29
     */
    public void savePermissionItemPowers(List<PermissionItemPower> permissionItemPowers) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for(int i = 0; i < permissionItemPowers.size(); i++) {
            session.save(permissionItemPowers.get(i));
        }
        tx.commit();
        session.close();
    }

    /**
     * 
     * @Function: deletePermissionItemPower
     * @Description: 删除单个事项关联职权
     *
     * @param id
     *
     * @author: mixue
     * @date: 2019年3月7日 下午5:39:00
     */
    public void deletePermissionItemPower(String id) {
        StringBuffer hql = new StringBuffer();
        hql.append(" delete from PermissionItemPower where permissionItem.id = '" + id + "'");
        deleteOrUpdateByQuery(hql.toString(), null);
    }
}
