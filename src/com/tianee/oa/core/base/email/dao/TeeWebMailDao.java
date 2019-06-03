package com.tianee.oa.core.base.email.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.email.bean.TeeWebMail;
import com.tianee.oa.core.base.email.model.TeeWebMailModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;
@Repository
public class TeeWebMailDao extends TeeBaseDao<TeeWebMail>{

	public List<TeeWebMailModel> setWebMailIndex(TeePerson person){
		List<TeeWebMail> list = new ArrayList<TeeWebMail>();
		List<TeeWebMailModel> modelList = new ArrayList<TeeWebMailModel>();
		String hql = "select webMail from TeeWebMail webMail where webMail.user.uuid = '"+person.getUuid()+"'";
		list = find(hql, null);
		for(TeeWebMail webMail : list){
			TeeWebMailModel model = new TeeWebMailModel();
			try {
				BeanUtils.copyProperties(model, webMail);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.setUser(null);
			modelList.add(model);
		}
		return modelList;
	}
	
	public List<TeeWebMailModel> getWebMailDefault(TeePerson person){
		List<TeeWebMail> list = new ArrayList<TeeWebMail>();
		List<TeeWebMailModel> modelList = new ArrayList<TeeWebMailModel>();
		String hql = "select webMail from TeeWebMail webMail where webMail.user.uuid = "+person.getUuid()+" and webMail.isDefault =1";
		list = find(hql, null);
		for(TeeWebMail webMail : list){
			TeeWebMailModel model = new TeeWebMailModel();
			try {
				BeanUtils.copyProperties(model, webMail);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.setUser(null);
			modelList.add(model);
		}
		return modelList;
	}
	
	

	/**
	 * 多个删除
	 * 
	 * @date 2014-3-8
	 * @author
	 * @param ids
	 */
	public void delObjByIds(String ids) {

		if (!TeeUtility.isNullorEmpty(ids)) {
			if (ids.endsWith(",")) {
				ids = ids.substring(0, ids.length() - 1);
			}
			String hql = "delete from TeeWebMail where sid in ("
					+ ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}

}
