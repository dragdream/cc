package com.tianee.oa.core.base.fileNetdisk.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileUserPriv;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository("fileUserPrivDao")
public class TeeFileUserPrivDao extends TeeBaseDao<TeeFileUserPriv> {

	/**
	 * 获取人员权限
	 * 
	 * @date 2014-2-16
	 * @author
	 * @param person
	 * @param disk
	 * @return
	 */
	public List<TeeFileUserPriv> getUserPrivDao(TeePerson person, TeeFileNetdisk disk) {
		Object[] values = { person.getUuid(), disk.getSid() };
		String hql = " from TeeFileUserPriv where user.uuid = ? and fileNetdisk.sid = ? ";
		return executeQuery(hql, values);
	}

	/**
	 * 删除人员权限
	 * 
	 * @date 2014-2-15
	 * @author
	 * @param sidStr
	 */
	public void deleteFileUserPrivDao(int sid) {
		String hql = " delete from TeeFileUserPriv where sid=" + sid;
		deleteOrUpdateByQuery(hql, null);
	}
	
	/**
	 * 根据文件夹删除整个权限
	 * @param folderId
	 */
	public void deleteFileUserPrivByFolder(int folderId) {
		String hql = " delete from TeeFileUserPriv where fileNetdisk.sid=" + folderId;
		deleteOrUpdateByQuery(hql, null);
	}

	/**
	 * @function: 根据文件Id获取人员权限列表
	 * @author: wyw
	 * @data: 2015年7月24日
	 * @param fileSid
	 * @return List<TeeFileUserPriv>
	 */
	public List<TeeFileUserPriv> getUserPrivListDao(TeePerson person, int fileSid) {
		/*
		String queryStr = "";
		if (!TeePersonService.checkIsSuperAdmin(person, null)) {
			queryStr = " and privValue > 0";
		}
		*/
		Object[] values = { fileSid };
		String hql = " from TeeFileUserPriv where fileNetdisk.sid = ? ";
		return executeQuery(hql, values);
	}

}
