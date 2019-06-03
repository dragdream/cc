package com.tianee.oa.core.workflow.flowrun.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("repeatToPubDao")
public class TeeRepeatToPubDao extends TeeBaseDao<TeeFileNetdisk>{

	/**
	 * @function: 根据父级sid获取所有目录
	 * @author: wyw
	 * @data: 2014年9月15日
	 * @param fileFullPath
	 * @param fileSids
	 * @return List<TeeFileNetdisk>
	 */
	public List<TeeFileNetdisk> getPublicFolderTreeDao(String fileFullPath, String fileSids) {

		if (TeeUtility.isNullorEmpty(fileSids)) {
			fileSids = "-1";
		}
		if (fileSids.endsWith(",")) {
			fileSids = fileSids.substring(0, fileSids.length() - 1);
		}

		if (TeeUtility.isNullorEmpty(fileFullPath)) {
			fileFullPath = "";
		}
		if (fileFullPath.endsWith(",")) {
			fileFullPath = fileFullPath.substring(0, fileFullPath.length() - 1);
		}
		String[] fileFullPathArray = fileFullPath.split(",");

		StringBuffer buffer = new StringBuffer();
		if (fileFullPathArray.length > 0) {
			for (String fileFullPathStr : fileFullPathArray) {
				if (!TeeUtility.isNullorEmpty(buffer.toString())) {
					buffer.append(" or ");
				}
				buffer.append(" fileFullPath like'" + fileFullPathStr + "%'");
			}
		}
		String hql = "from TeeFileNetdisk where filetype=0 and fileNetdiskType=0 and (" + buffer.toString() + " or sid in(" + fileSids + ")) order by fileNo asc,fileName asc";

		return executeQuery(hql, null);
	}

}
