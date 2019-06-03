package com.tianee.oa.core.base.fileNetdisk.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdiskReader;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("fileNetdiskReaderDao")
public class TeeFileNetdiskReaderDao extends TeeBaseDao<TeeFileNetdiskReader> {



	/**
	 * 根据Id查询
	 * 
	 * @date 2014-1-5
	 * @author
	 * @param sid
	 * @return
	 */
	public TeeFileNetdiskReader getInfoByIdDao(int sid) {
		TeeFileNetdiskReader fileNetdisk = get(sid);
		return fileNetdisk;
	}

	/**
	 * 根据sid删除
	 * 
	 * @date 2014-1-5
	 * @author
	 * @param sidStr
	 */
	public void deleteInfoByIdDao(String sidStr) {
		if (!TeeUtility.isNullorEmpty(sidStr)) {
			if (sidStr.endsWith(",")) {
				sidStr = sidStr.substring(0, sidStr.length() - 1);
			}
			String hql = " delete from TeeFileNetdiskReader where sid in(" + sidStr + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	/**
	 * 根据文件Id删除签阅情况
	 * 
	 * @date 2014-1-5
	 * @author
	 * @param sidStr
	 */
	public void deleteInfoByFileIdDao(String sidStr) {
		if (!TeeUtility.isNullorEmpty(sidStr)) {
			if (sidStr.endsWith(",")) {
				sidStr = sidStr.substring(0, sidStr.length() - 1);
			}
			String hql = " delete from TeeFileNetdiskReader where fileNetdisk.sid in(" + sidStr + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	
	/**
	 * 是否已阅读
	 * @function: 
	 * @author: wyw
	 * @data: 2015年7月23日
	 * @param fileSid
	 * @param userId
	 * @return boolean
	 */
	public boolean isSignReadDao(int fileSid, int userId){
		Object[] param = {fileSid,userId};
		String hql = " select count(sid) from TeeFileNetdiskReader where fileNetdisk.sid=? and reader.uuid=?";
		long counter = this.count(hql, param);
		boolean isSignRead = false;
		if(counter>0){
			isSignRead = true;
		}
		return isSignRead;
	}
	
	
	/**
	 * 根据vote Sid获取已投票数据
	 * @function: 
	 * @author: wyw
	 * @data: 2015年6月26日
	 * @param voteId
	 * @return List<TeeVotePerson>
	 */
	public List<TeeFileNetdiskReader> getSignReaderList(int fileSid){
		Object[] values = {fileSid };
		String hql = " from TeeFileNetdiskReader where fileNetdisk.sid=?" ;
		return  executeQuery(hql,values);
	}
	

}
