package com.beidasoft.xzfy.organ.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.alibaba.druid.util.StringUtils;
import com.beidasoft.xzfy.organ.bean.OrganInfo;
import com.beidasoft.xzfy.organ.model.request.OrganListRequest;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class OrganDao extends TeeBaseDao<OrganInfo>{

	/**
	 * 分页获取组织机构列表
	 * @param OrganInfo
	 * @param curpage
	 * @return
	 */
	public List<OrganInfo> getOrganList(OrganListRequest req,String treeIds){
		
		StringBuffer str = new StringBuffer();
		str.append(" from OrganInfo where is_delete = 0 ");
		//机关层级
		if( !StringUtils.isEmpty(req.getOrganLevel()) ){
			str.append(" and org_level_code = ").append(req.getOrganLevel());
		}
		//机关名称
		if( !StringUtils.isEmpty(req.getOrganName()) ){
			str.append(" and org_name like '%").append(req.getOrganName()+"%'");
		}
		//联系人
		if( !StringUtils.isEmpty(req.getContacts()) ){
			str.append(" and contacts like '%").append(req.getContacts()+"%'");
		}
		//treeID
		if( !StringUtils.isEmpty(treeIds) ){
			str.append(" and dept_id in (").append(treeIds).append(")");
		}
		System.out.println(str.toString());
		int start = (req.getPage()-1)*req.getRows();
		List<OrganInfo> list = pageFind(str.toString(), 
				start,req.getRows(), null);
		return list;
	}
	
	
	/**
	 * 分页获取组织机构列表总记录数
	 * @param req
	 * @return
	 */
	public int getOrganListTotal(OrganListRequest req,String treeIds){
		
		StringBuffer str = new StringBuffer();
		str.append("select count(*) from FY_ORGAN where is_delete = 0 ");
		//机关层级
		if( !StringUtils.isEmpty(req.getOrganLevel()) ){
			str.append(" and org_level_code = ").append(req.getOrganLevel());
		}
		//机关名称
		if( !StringUtils.isEmpty(req.getOrganName()) ){
			str.append(" and org_name like '%").append(req.getOrganName()+"%'");
		}
		//联系人
		if( !StringUtils.isEmpty(req.getContacts()) ){
			str.append(" and contacts like '%").append(req.getContacts()+"%'");
		}
		//treeID
		if( !StringUtils.isEmpty(treeIds) ){
			str.append(" and dept_id in (").append(treeIds).append(")");
		}
		
		Long total = countSQLByList(str.toString(), null);
		int num = total.intValue();
		return num;
	}
	
	
	/**
	 * 根据组件机构部门ID获取组织机构信息
	 * @param deptId
	 * @return
	 */
	public OrganInfo getOrganInfo(String deptId){
		
		StringBuffer str = new StringBuffer();
		str.append(" from OrganInfo where is_delete = 0 ");
		str.append(" and dept_id='");
		str.append(deptId);
		str.append("'");
		List<OrganInfo> list = pageFind(str.toString(), 
				0,10, null);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 新增组织机构
	 * @param org
	 */
	public void addOrgan(OrganInfo org){
		save(org);
	}
	
	/**
	 * 批量新增组织机构
	 * @param list
	 */
	public void addOrganList(List<OrganInfo> list) {
		int BATCH_MAX_ROW = 100000;
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (int i = 0; i < list.size(); i++) {
				session.save(list.get(i));
				if (i % BATCH_MAX_ROW == 0) {
					session.flush();
					session.clear();
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	/**
	 * 更新组织机构信息
	 * @param org
	 */
	public void updateOrgan(OrganInfo org){
		update(org);
	}
	
	/**
	 * 逻辑删除,更改状态
	 * @param orgId
	 */
	public void deleteOrgan(String orgIds){
		
		String[] ids = orgIds.split(",");
		StringBuffer batchIds = new StringBuffer();
		
		for(int i=0; i<ids.length-1; i++){
			batchIds.append("'").append(ids[i]).append("'").append(",");
		}
		batchIds.append("'").append(ids[ids.length-1]).append("'");
			
		StringBuffer str = new StringBuffer();
		str.append("update FY_ORGAN set is_delete = 1 where org_id in (");
		str.append(batchIds);
		str.append(")");
		executeNativeUpdate(str.toString(), null);
	}
	
	/**
	 * 删除所有组织机构
	 */
	public void deleteAllOrgan(){
		StringBuffer str = new StringBuffer();
		str.append("delete from fy_organ");
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createSQLQuery(str.toString());
			query.executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}
