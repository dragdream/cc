package com.tianee.oa.subsys.crm.core.product.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.crm.core.product.bean.TeeCrmProductsType;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository("teeCrmProductsTypeDao")
public class TeeCrmProductsTypeDao  extends TeeBaseDao<TeeCrmProductsType>{
	
	/**
	 * 获取所有产品 分类
	 * @author syl
	 * @date 2014-3-1
	 * @return
	 */
	public List<TeeCrmProductsType> getCrmProductsType() {
	    String hql = " from TeeCrmProductsType order by typeOrder";
		List<TeeCrmProductsType> list = (List<TeeCrmProductsType>) executeQuery(hql,null);
		return list;
	}
	
	/**
	 * 删除类型 -- 级联删除下级
	 * @return
	 */
	public int deleteType (int sid , String parentPath){
		int count = 0;
		Object[] values = {sid ,parentPath + "%"};
		String hql = "delete from TeeCrmProductsType where (sid = ? or parentPath like ?)";
		count = deleteOrUpdateByQuery(hql, values);
		return count;
	}
		
	
	/**
	 * 获取不包含下级和本身的  产品分类
	 * @author syl
	 * @date 2014-3-1
	 * @return
	 */
	public List<TeeCrmProductsType> getCrmProductsTypeNoChildAndSelf(int sid , String parentPath) {
		Object[] values = {sid ,parentPath + "%"};
		String hql = " from TeeCrmProductsType where sid <> ? and  parentPath not like ?  order by typeOrder";
		List<TeeCrmProductsType> list = (List<TeeCrmProductsType>) executeQuery(hql,values);
		return list;
	}

	/* 更新 下级产品类型 父节点路径
	* @param oldMenuId : 更改之前编号
	* @param newMenuId: 新编号
	*/
	public void updateProductsTypeParentPath(String oldMenuId , String currDeptParentId,int uuid ) {
		int newMenuIdLength = currDeptParentId.length();
		int oldMenuIdLength = oldMenuId.length();

		String hql = " update TeeCrmProductsType t set t.parentPath = case  when 1=1 then  concat('" +currDeptParentId + "',substring(t.parentPath," + (oldMenuIdLength + 1 ) + ")) end   where  t.parentPath like ?  and sid != ? ";
		Object[] values1 = { oldMenuId + uuid + "/%",uuid};
		int count  = 0;
		//if(oldMenuIdLength == 0){//如果是第一级
			/*Object[] values2 = { oldMenuId + "%",uuid};
			hql = " update TeeCrmProductsType t set t.parentPath = case  when 1=1 then  concat('" +currDeptParentId + "',t.parentPath) end   where  t.parentPath like ? and sid != ? ";		
			count = deleteOrUpdateByQuery(hql, values2);
			return  ;*/
		//}
		count = deleteOrUpdateByQuery(hql, values1);
	}

}
