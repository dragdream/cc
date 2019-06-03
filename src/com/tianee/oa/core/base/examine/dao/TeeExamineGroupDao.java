package com.tianee.oa.core.base.examine.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.examine.bean.TeeExamineGroup;
import com.tianee.oa.core.base.examine.model.TeeExamineGroupModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;


@Repository("TeeExamineGroupDao")
public class TeeExamineGroupDao  extends TeeBaseDao<TeeExamineGroup>{
	/**
	 * @author syl
	 * 增加
	 * @param TeeExamineGroup
	 */
	public void add(TeeExamineGroup attendOut) {
		save(attendOut);
	}
	
	/**
	 * @author syl
	 * 更新
	 * @param TeeVehicle
	 */
	public void updateObj(TeeExamineGroup attendOut) {
		update(attendOut);
	}
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeExamineGroup loadById(int id) {
		TeeExamineGroup intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeExamineGroup getById(int id) {
		TeeExamineGroup intf = get(id);
		return intf;
	}
	
	
	/**
	 * @author syl
	 * byId 删除
	 * @param 
	 */
	public void delById(int id) {
		delete(id);
	}
	
	
	/**
	 * 删除 所有记录
	 * 
	 * @author syl
	 * @date 2014-1-6
	 * @param ids
	 */
	public void delAll(){
		String hql = "delete from TeeExamineGroup ";
		deleteOrUpdateByQuery(hql, null);
	}
	/**
	 * 删除 by Ids
	 * 
	 * @author syl
	 * @date 2014-1-6
	 * @param ids
	 */
	public void delByIds(String ids){
		
		if(!TeeUtility.isNullorEmpty(ids)){
			if(ids.endsWith(",")){
				ids= ids.substring(0, ids.length() -1 );
			}
			String hql = "delete from TeeExamineGroup where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	/**
	 * 查询所有记录 
	 * @author syl
	 * @date 2014-5-24
	 * @param person
	 * @param  type 是否获取数据   true:获取数据  false:获取记录数
	 * @return
	 */
	public Map getAll(TeePerson person  , boolean type ,int firstResult,int pageSize,TeeDataGridModel dm) {
		Map map = new HashMap();
		Object[] values = { };
		String hql = "from TeeExamineGroup";
		if(!type){
			hql = " select count(sid) "  + hql;
			long count = count(hql, null);
			map.put("count", count);
		}else{
			if(TeeUtility.isNullorEmpty(dm.getSort())){
				dm.setSort("sid");
			}
			hql = hql + " order by " + dm.getSort() + " " + dm.getOrder();
			List<TeeExamineGroup> list =  pageFind(hql, firstResult, pageSize, values);
			map.put("data", list);
		}
		return map;
	}	
	
	
	
	/**
	 * 查询所有记录  ---  带申请权限
	 * @author syl
	 * @date 2014-5-24
	 * @param person  人员对象
	 * @param model  模型
	 * @param type 是否获取数据   true:获取数据  false:获取记录数
	 * @return
	 */
	public  Map selectPostExamineGroup(TeePerson person , TeeExamineGroupModel model , boolean type) {
		List<TeeExamineGroup> list = new ArrayList<TeeExamineGroup>();
		Map map  = new HashMap();
		if(TeePersonService.checkIsSuperAdmin(person, "")){//是否超级管理员
			String hql = "from TeeExamineGroup order by sid";
			
			if(type){
				list = (List<TeeExamineGroup>) executeQuery(hql,null);
				map.put("data", list);
			}else{
				hql = " select count(sid) "  + hql;
				long count = count(hql, null);
				map.put("count", count);
			}
			
		}else{
			//获取人员部门、角色、人员 sid
			int temp[] = TeeUtility.parsePersonDeptId_UserId_UserRoleId(person);
			Object[] values = { temp[0] , temp[1] ,temp[2]};
			String hql = "from TeeExamineGroup  group where  ( exists (select 1 from group.postDept dept where dept.uuid =?)" +
					" or exists (select 1 from group.postUser user where user.uuid= ? ) " +
					" or exists (select 1 from group.postUserRole role where role.uuid= ? )  )" +
					"order by group.sid)";
			if(type){
				list = (List<TeeExamineGroup>) executeQuery(hql,values);
				map.put("data", list);
			}else{
				hql = " select count(sid) "  + hql;
				long count = count(hql, values);
				map.put("count", count);
			}
		}
		return map;
	}	
}
