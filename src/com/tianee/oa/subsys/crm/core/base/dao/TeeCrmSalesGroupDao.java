package com.tianee.oa.subsys.crm.core.base.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.crm.core.base.bean.TeeCrmSalesGroup;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository("teeCrmSalesGroupDao")
public class TeeCrmSalesGroupDao  extends TeeBaseDao<TeeCrmSalesGroup>{
	
	/**
	 * 获取所有产品 分类
	 * @author syl
	 * @date 2014-3-1
	 * @return
	 */
	public List<TeeCrmSalesGroup> getCrmAllGroup() {
	    String hql = " from TeeCrmSalesGroup order  by parentPath , groupOrder";
		List<TeeCrmSalesGroup> list = (List<TeeCrmSalesGroup>) executeQuery(hql,null);
		return list;
	}
	
	/**
	 * 删除类型 -- 级联删除下级
	 * @return
	 */
	public int deleteType (int sid , String parentPath){
		int count = 0;
		Object[] values = {sid ,parentPath + "%"};
		String hql = "delete from TeeCrmSalesGroup where (sid = ? or parentPath like ?)";
		count = deleteOrUpdateByQuery(hql, values);
		return count;
	}
		
	
	/**
	 * 获取不包含下级和本身的  产品分类
	 * @author syl
	 * @date 2014-3-1
	 * @return
	 */
	public List<TeeCrmSalesGroup> getCrmProductsTypeNoChildAndSelf(int sid , String parentPath) {
		Object[] values = {sid ,parentPath + "%"};
		String hql = " from TeeCrmSalesGroup where sid <> ? and  parentPath not like ?  order by groupOrder";
		List<TeeCrmSalesGroup> list = (List<TeeCrmSalesGroup>) executeQuery(hql,values);
		return list;
	}

	/* 更新 下级产品类型 父节点路径
	* @param oldMenuId : 更改之前编号
	* @param newMenuId: 新编号
	*/
	public void updateProductsTypeParentPath(String oldMenuId , String currDeptParentId,int uuid ) {
		int newMenuIdLength = currDeptParentId.length();
		int oldMenuIdLength = oldMenuId.length();

		String hql = " update TeeCrmSalesGroup t set t.parentPath = case  when 1=1 then  concat('" +currDeptParentId + "',substring(t.parentPath," + (oldMenuIdLength + 1 ) + ")) end   where  t.parentPath like ?  and sid != ? ";
		Object[] values1 = { oldMenuId + uuid + "/%",uuid};
		int count  = 0;
		//if(oldMenuIdLength == 0){//如果是第一级
			/*Object[] values2 = { oldMenuId + "%",uuid};
			hql = " update TeeCrmSalesGroup t set t.parentPath = case  when 1=1 then  concat('" +currDeptParentId + "',t.parentPath) end   where  t.parentPath like ? and sid != ? ";		
			count = deleteOrUpdateByQuery(hql, values2);
			return  ;*/
		//}
		count = deleteOrUpdateByQuery(hql, values1);
	}

}
