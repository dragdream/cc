package com.beidasoft.xzzf.punish.classicCase.dao;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.punish.classicCase.bean.ClassicCaseCollection;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.dao.TeeBaseDao;

/**
 * 经典案例收藏表DAO类
 */
@Repository
public class ClassicCaseCollectionDao extends TeeBaseDao<ClassicCaseCollection> {
	   
	/**
	 * 保存
	 */
	public void saveClassicCaseCollection(ClassicCaseCollection classicCaseCollection) {
		super.save(classicCaseCollection);
	}
	
	/**
	 * 删除
	 */
	public void deleteClassicCaseCollection(String id) {
		super.delete(id);
	}
	
	
	/**
	 * 根据id查询
	 */
	public ClassicCaseCollection loadById(String id) {
		ClassicCaseCollection classicCaseCollection = super.get(id);
		return classicCaseCollection;
	}
	
	/**
	 * 根据案件id查询
	 */
	public ClassicCaseCollection loadByCaseId(String id, HttpServletRequest request) {
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<ClassicCaseCollection> list = super.find("from ClassicCaseCollection where classicCaseId='"+id+"' and personId="+user.getUuid());
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
		
}
