package com.tianee.oa.core.workflow.flowrun.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("repeatToPersonDao")
public class TeeRepeatToPersonDao extends TeeBaseDao<TeeFileNetdisk>  {

	/**
	 * 根据父级sid获取所有目录
	 * 
	 * @date 2014-1-5
	 * @author
	 * @return
	 */
	public List<TeeFileNetdisk> getPersonFolderTreeDao(String fileFullPath, String fileSids, TeePerson person) {
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
		String[] sidArray = fileFullPath.split(",");
		String hql = "";
		StringBuffer buffer = new StringBuffer();
		if (sidArray.length > 0) {
			for (String sid : sidArray) {
				if (!TeeUtility.isNullorEmpty(buffer.toString())) {
					buffer.append(" or ");
				}
				buffer.append(" fileFullPath like'" + sid + "%'");

			}
		}
		
		hql = "from TeeFileNetdisk where filetype=0 and fileNetdiskType=1 and creater.uuid=" + person.getUuid() 
				+ " and (" + buffer.toString() + " or sid in(" + fileSids
				+ ")) order by fileNo asc,fileName asc";
		return executeQuery(hql, null);
	}

}
