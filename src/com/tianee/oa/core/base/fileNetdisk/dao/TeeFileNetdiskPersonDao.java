package com.tianee.oa.core.base.fileNetdisk.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("fileNetdiskPersonDao")
public class TeeFileNetdiskPersonDao extends TeeBaseDao<TeeFileNetdisk> {

	/**
	 * 获取获取个人网盘根目录
	 * 
	 * @date 2014-2-21
	 * @author
	 * @param person
	 * @return
	 */
	public List<TeeFileNetdisk> getPersonRootFolderDao(TeePerson person) {
		Object[] values = { person.getUuid() };
		String hql = "from TeeFileNetdisk where filetype=0 and fileNetdiskType=1 and creater.uuid=? and fileParent is null order by  createTime desc";

		return executeQuery(hql, values);
	}

	/**
	 * 根据上级目录sid获取所有子目录，名称排序（包括本文件夹）
	 * 
	 * @date 2014-2-21
	 * @author
	 * @param fileFullPath
	 * @param sid
	 * @return
	 */
	public List<TeeFileNetdisk> getSubFolderListByParentIdDao(String fileFullPath, int sid, TeePerson person) {
		String hql = "from TeeFileNetdisk where filetype=0 and fileNetdiskType=1 and creater.uuid=" + person.getUuid() + " and (fileFullPath like'" + fileFullPath + "%' or sid=" + sid
				+ ")  order by fileName, createTime asc";
		return executeQuery(hql, null);
	}

	/**
	 * 根据父级sid获取所有目录
	 * 
	 * @date 2014-1-5
	 * @author
	 * @return
	 */
	public List<TeeFileNetdisk> getFileFolderListByParentDao(String fileFullPath, String fileSids, String seleteSid, String optionFlag, TeePerson person) {
		if (TeeUtility.isNullorEmpty(fileSids)) {
			fileSids = "-1";
		}
		if (fileSids.endsWith(",")) {
			fileSids = fileSids.substring(0, fileSids.length() - 1);
		}
		if (TeeUtility.isNullorEmpty(seleteSid)) {
			seleteSid = "-1";
		}
		if (seleteSid.endsWith(",")) {
			seleteSid = seleteSid.substring(0, seleteSid.length() - 1);
		}

		if (TeeUtility.isNullorEmpty(fileFullPath)) {
			fileFullPath = "";
		}
		if (fileFullPath.endsWith(",")) {
			fileFullPath = fileFullPath.substring(0, fileFullPath.length() - 1);
		}
		String[] sidArray = fileFullPath.split(",");
//		System.out.println(fileFullPath);
		String hql = "from TeeFileNetdisk where filetype=0 and fileNetdiskType=1 and creater.uuid="+person.getUuid();
//		if ("1".equals(optionFlag)) {// 剪贴
//			StringBuffer seleteBuffer = new StringBuffer();
//			List<TeeFileNetdisk> selecTeeFileNetdisks = getSubFileNetdiskBySidDao(fileFullPath, person);
//			if (selecTeeFileNetdisks != null && selecTeeFileNetdisks.size() > 0) {
//				for (TeeFileNetdisk selectFileNetdisk : selecTeeFileNetdisks) {
//					if (!TeeUtility.isNullorEmpty(seleteBuffer.toString())) {
//						seleteBuffer.append(" or ");
//					}
//					seleteBuffer.append(" fileFullPath not like'" + selectFileNetdisk.getFileFullPath() + "%'");
//					
//				}
//			}
			// 如果没有被选中的文件夹，则置为true
//			if (seleteBuffer.length() == 0) {
//				seleteBuffer.append(" 1=1 ");
//			}

//			StringBuffer buffer = new StringBuffer();
//			if (sidArray.length > 0) {
//				for (String sid : sidArray) {
//					if (!TeeUtility.isNullorEmpty(buffer.toString())) {
//						buffer.append(" or ");
//					}
//					buffer.append(" fileFullPath like'" + sid + "%'");
//
//				}
//			}
//			hql = "from TeeFileNetdisk where filetype=0 and fileNetdiskType=1 and creater.uuid=" 
//			+ person.getUuid() 
//			+ " and (" + buffer.toString() +" and (sid not in(" + seleteSid + ")))" 
//			+ " and (" + seleteBuffer.toString() + ") or sid in("+ fileSids + ")  order by fileNo asc,fileName asc";
//		} else {
//			StringBuffer buffer = new StringBuffer();
//			if (sidArray.length > 0) {
//				for (String sid : sidArray) {
//					if (!TeeUtility.isNullorEmpty(buffer.toString())) {
//						buffer.append(" or ");
//					}
//					buffer.append(" fileFullPath like'" + sid + "%'");
//					
//				}
//			}
//			hql = "from TeeFileNetdisk where filetype=0 and fileNetdiskType=1 and creater.uuid=" + person.getUuid() + " and (" + buffer.toString() + " or sid in(" + fileSids
//					+ ")) order by fileNo asc,fileName asc";
//		}
		return executeQuery(hql, null);
	}

	/**
	 * 根据上级目录sid获取所有子目录，sid排序（包括本文件夹）
	 * 
	 * @date 2014-2-21
	 * @author
	 * @param fileFullPath
	 * @param sid
	 * @return
	 */
	public List<TeeFileNetdisk> getSubFolderListBySidAscDao(String fileFullPath, int sid, TeePerson person) {

		String hql = "from TeeFileNetdisk where filetype=0 and fileNetdiskType=1 and creater.uuid=" + person.getUuid() + " and (fileFullPath like'" + fileFullPath + "%' or sid=" + sid
				+ ")  order by sid asc";
		return executeQuery(hql, null);
	}

	/**
	 * 根据上级目录sid获取所有子目录，sid排序（包括本文件夹）（共享文件柜）
	 * 
	 * @date 2014-2-21
	 * @author
	 * @param fileFullPath
	 * @param sid
	 * @return
	 */
	public List<TeeFileNetdisk> getSubFolderListBySidAscDao(String fileFullPath, int sid) {

		String hql = "from TeeFileNetdisk where filetype=0 and fileNetdiskType=1 and (fileFullPath like'" + fileFullPath + "%' or sid=" + sid + ")  order by sid asc";
		return executeQuery(hql, null);
	}

	/**
	 * 查询目录下文件总数
	 * 
	 * @date 2014-2-21
	 * @author
	 * @param folderSid
	 * @param person
	 * @return
	 */
	public long getCountByFolderSid(int folderSid, TeePerson person) {
		Object[] values = { person.getUuid() };
		String hql = "";
		if (folderSid != 0) {
			hql = " select count(sid)  from TeeFileNetdisk where fileNetdiskType=1 and fileParent.sid=" + folderSid + "  and creater.uuid=?";
		} else {
			hql = " select count(sid)  from TeeFileNetdisk where fileNetdiskType=1 and fileParent is null  and creater.uuid=?";
		}
		return count(hql, values);
	}

	/**
	 * 获取个人文件通用列表数据
	 * 
	 * @date 2014-2-21
	 * @author
	 * @param firstResult
	 * @param pageSize
	 * @param dataGridModel
	 * @param folderSid
	 * @param person
	 * @return
	 */
	public List<TeeFileNetdisk> getFileNetdisksPage(int firstResult, int pageSize, TeeDataGridModel dataGridModel, int folderSid, TeePerson person) {
		Object[] param = { person.getUuid() };
		String hql = "";
		if (folderSid != 0) {
			hql = " from TeeFileNetdisk where fileNetdiskType=1 and fileParent.sid = " + folderSid + "  and creater.uuid=?";
		} else {
			hql = " from TeeFileNetdisk where fileNetdiskType=1 and fileParent is null  and creater.uuid=?";
		}

		if (TeeUtility.isNullorEmpty(dataGridModel.getSort())) {
			dataGridModel.setSort("fileName");
			dataGridModel.setOrder("asc");
		}
		if ("createTimeStr".equals(dataGridModel.getSort())) {
			dataGridModel.setSort("createTime");
		}
		hql += " order by filetype asc, " + dataGridModel.getSort() + " " + dataGridModel.getOrder();
       
		return pageFind(hql, firstResult, pageSize, param);
	}

	/**
	 * 根据sid串获取文件夹目录
	 * 
	 * @date 2014-2-15
	 * @author
	 * @param sids
	 * @return
	 */
	public List<TeeFileNetdisk> getSubFileNetdiskBySidDao(String sids, TeePerson person) {
		if (TeeUtility.isNullorEmpty(sids)) {
			return new ArrayList<TeeFileNetdisk>();
		}
		sids = sids.substring(1);
		sids = sids.replaceAll("/", ",");
		if (sids.endsWith(",")) {
			sids = sids.substring(0, sids.length() - 1);
		}
		// Object[] param = { person.getUuid() };
		String hql = "from TeeFileNetdisk where filetype=0 and fileNetdiskType=1  and sid in (" + sids + ")";
		return executeQuery(hql, null);
	}

	/**
	 * 根据sid获取文件和文件夹
	 * 
	 * @date 2014-2-15
	 * @author
	 * @param sids
	 * @return
	 */
	public List<TeeFileNetdisk> getFileBySidsDao(String sids, TeePerson person) {
		if (TeeUtility.isNullorEmpty(sids)) {
			return new ArrayList<TeeFileNetdisk>();
		}
		if (sids.endsWith(",")) {
			sids = sids.substring(0, sids.length() - 1);
		}
		Object[] param = { person.getUuid() };
		String hql = "from TeeFileNetdisk where fileNetdiskType=1  and creater.uuid=? and  sid in (" + sids + ")";
		return executeQuery(hql, param);
	}

	/**
	 * 根据sid获取文件和文件夹（共享文件柜）
	 * 
	 * @date 2014-2-15
	 * @author
	 * @param sids
	 * @return
	 */
	public List<TeeFileNetdisk> getFileBySidsDao(String sids) {
		if (TeeUtility.isNullorEmpty(sids)) {
			return new ArrayList<TeeFileNetdisk>();
		}
		if (sids.endsWith(",")) {
			sids = sids.substring(0, sids.length() - 1);
		}
		String hql = "from TeeFileNetdisk where fileNetdiskType=1  and  sid in (" + sids + ")";
		return executeQuery(hql, null);
	}

	/**
	 * 查询 附件
	 * 
	 * @date 2014-2-21
	 * @author
	 * @param attachSids
	 * @return
	 */
	public List<TeeAttachment> getAttachmentsBySids(String attachSids) {
		if (TeeUtility.isNullorEmpty(attachSids)) {
			return new ArrayList<TeeAttachment>();
		}
		if (attachSids.endsWith(",")) {
			attachSids = attachSids.substring(0, attachSids.length() - 1);
		}
		Session session = this.getSession();
		String hql = "from TeeAttachment  where sid in (" + attachSids + ")";
		Query query = session.createQuery(hql);
		return query.list();
	}

	/**
	 * 删除目录及文件(级联删除)
	 * 
	 * @date 2014-2-22
	 * @author
	 * @param fileFullPath
	 * @param sid
	 * @param person
	 */
	public void deleteFileBySidDao(String fileFullPath, int sid, TeePerson person) {
		if (!TeeUtility.isNullorEmpty(fileFullPath)) {
			Object[] param = { person.getUuid() };
			String hql = " delete from TeeFileNetdisk where fileNetdiskType=1 and creater.uuid=? and fileFullPath like'" + fileFullPath + "%' or sid=" + sid;
			deleteOrUpdateByQuery(hql, param);
		}
	}

	/**
	 * 获取文件父级id获取同一级的所有文件夹、文件
	 * 
	 * @date 2014-2-21
	 * @author
	 * @param person
	 * @return
	 */
	public List<TeeFileNetdisk> getFileByParentIdsDao(TeePerson person, String sids) {
		if (TeeUtility.isNullorEmpty(sids)) {
			return new ArrayList<TeeFileNetdisk>();
		}
		if (sids.endsWith(",")) {
			sids = sids.substring(0, sids.length() - 1);
		}
		Object[] values = { person.getUuid() };
		String hql = "from TeeFileNetdisk where  fileNetdiskType=1 and creater.uuid=? and fileParent.sid in(" + sids + ")";

		return executeQuery(hql, values);
	}

	/**
	 * 文件夹、文件剪贴
	 * 
	 * @date 2014-2-20
	 * @author
	 * @param folderSid
	 * @param sidStr
	 */
	public void updateFileParentBySidDao(TeeFileNetdisk disk, List<TeeFileNetdisk> list, String sidStr, TeePerson person) {
		if (!TeeUtility.isNullorEmpty(sidStr)) {
			if (sidStr.endsWith(",")) {
				sidStr = sidStr.substring(0, sidStr.length() - 1);
			}
			String currFullPathString = TeeStringUtil.getString(disk.getFileFullPath(), "") + disk.getSid() + "/";

			String hql = " update TeeFileNetdisk  set fileFullPath = case  ";

			String thenStr = "";
			String oldFullPathParentStr = "";
			for (int i = 0; i < list.size(); i++) {
				oldFullPathParentStr = list.get(i).getFileFullPath();
				String oldFullPathString = oldFullPathParentStr + list.get(i).getSid() + "/";
				int oldMenuIdLength = oldFullPathString.length();
				thenStr = thenStr + "when  fileFullPath like  '" + oldFullPathString + "%' then " + " concat('" + currFullPathString + list.get(i).getSid() + "/" + "',substring(fileFullPath,"
						+ (oldMenuIdLength + 1) + "))";
			}

			thenStr = thenStr + " when  sid in (" + sidStr + ") then '" + currFullPathString + "'";
			hql = hql + thenStr + " else fileFullPath ";

			hql = hql + " end ";

			hql = hql + " ,fileParent.sid =   case  when sid in (" + sidStr + ") then " + disk.getSid() + " else fileParent.sid end ";

			hql = hql + "  where  fileFullPath like '" + oldFullPathParentStr + "%' and fileNetdiskType=1 and creater.uuid=" + person.getUuid();
			int count = deleteOrUpdateByQuery(hql, null);
		}
	}

	/**
	 * 根据 文件夹sid串获取该文件夹的所有文件
	 * 
	 * @date 2014-2-15
	 * @author
	 * @param sids
	 * @return
	 */
	public List<TeeFileNetdisk> getSubFilesByParentSidsDao(String sids, TeePerson person) {
		if (TeeUtility.isNullorEmpty(sids)) {
			return new ArrayList<TeeFileNetdisk>();
		}
		if (sids.endsWith(",")) {
			sids = sids.substring(0, sids.length() - 1);
		}
		Object[] values = { person.getUuid() };

		String hql = "from TeeFileNetdisk where filetype=1 and fileNetdiskType=1 and creater.uuid=?  and fileParent.sid in (" + sids + ")";
		return executeQuery(hql, values);
	}

	/**
	 * 根据 文件夹sid串获取该文件夹的所有文件（共享文件柜）
	 * 
	 * @date 2014-2-15
	 * @author
	 * @param sids
	 * @return
	 */
	public List<TeeFileNetdisk> getSubFilesByParentSidsDao(String sids) {
		if (TeeUtility.isNullorEmpty(sids)) {
			return new ArrayList<TeeFileNetdisk>();
		}
		if (sids.endsWith(",")) {
			sids = sids.substring(0, sids.length() - 1);
		}

		String hql = "from TeeFileNetdisk where filetype=1 and fileNetdiskType=1 and  fileParent.sid in (" + sids + ")";
		return executeQuery(hql, null);
	}

	/**
	 * 获取共享文件柜（ 带权限）
	 * 
	 * @date 2014-2-15
	 * @author
	 * @param sids
	 * @return
	 */
	public List<TeeFileNetdisk> getRootPrivFolderDao(TeePerson person) {
		Object[] values = { person.getUuid() };
		String hql = " select file  from TeeFileNetdisk file  where fileNetdiskType=1 and filetype=0 and ( exists (select 1 from file.fileUserPriv fileUserPriv where fileUserPriv.user.uuid = ? and fileUserPriv.privValue>0))";
		return executeQuery(hql, values);
	}
	
	/**
	 * 获取个人网盘根文件夹
	 * 
	 * @date 2014-2-15
	 * @author
	 * @param sids
	 * @return
	 */
	public TeeFileNetdisk getRootFolder(TeePerson person) {
		Session session = this.getSession();
		String hql = "from TeeFileNetdisk file where file.fileNetdiskType=1 and file.creater.uuid="+person.getUuid()+" and file.filetype=0 and file.fileParent is null";
		Query query = session.createQuery(hql);
		return (TeeFileNetdisk) query.uniqueResult();
	}

	/**
	 * 根据上级目录sid获取所有子目录（共享文件柜），名称排序（不包括本文件夹）
	 * 
	 * @date 2014-2-23
	 * @author
	 * @param fileFullPath
	 * @param person
	 * @return
	 */
	public List<TeeFileNetdisk> getSubFoldersByParentIdDao(String fileFullPath, TeePerson person,TeePerson loginUser) {
		String hql = "from TeeFileNetdisk file where file.filetype=0 and file.fileNetdiskType=1 and file.creater.uuid=" + person.getUuid() + " and (file.fileFullPath like'" + fileFullPath
				+ "%' )  and ( exists (select 1 from file.fileUserPriv fileUserPriv where fileUserPriv.user.uuid = "+loginUser.getUuid()+" and fileUserPriv.privValue>0)) order by file.fileName, file.createTime asc";
		return executeQuery(hql, null);
	}

	/**
	 * 查询目录下文件总数（共享文件柜）只取文件
	 * 
	 * @date 2014-2-21
	 * @author
	 * @param folderSid
	 * @param person
	 * @return
	 */
	public long getCountByFolderSid(int folderSid) {
		String hql = "";
		if (folderSid != 0) {
			hql = " select count(sid)  from TeeFileNetdisk where fileNetdiskType=1 and filetype=1 and  fileParent.sid=" + folderSid;
		} else {
			hql = " select count(sid)  from TeeFileNetdisk where fileNetdiskType=1 and  filetype=1  and fileParent is null ";
		}
		return count(hql, null);
	}

	/**
	 * 获取个人文件通用列表数据（共享文件柜）只取文件
	 * 
	 * @date 2014-2-21
	 * @author
	 * @param firstResult
	 * @param pageSize
	 * @param dataGridModel
	 * @param folderSid
	 * @param person
	 * @return
	 */
	public List<TeeFileNetdisk> getFileNetdisksPage(int firstResult, int pageSize, TeeDataGridModel dataGridModel, int folderSid) {
		String hql = "";
		if (folderSid != 0) {
			hql = " from TeeFileNetdisk where fileNetdiskType=1 and filetype=1 and fileParent.sid = " + folderSid;
		} else {
			hql = " from TeeFileNetdisk where fileNetdiskType=1 and filetype=1 and fileParent is null ";
		}

		if (TeeUtility.isNullorEmpty(dataGridModel.getSort())) {
			dataGridModel.setSort("fileName");
			dataGridModel.setOrder("asc");
		}
		if ("createTimeStr".equals(dataGridModel.getSort())) {
			dataGridModel.setSort("createTime");
		}
		hql += " order by  filetype asc," + dataGridModel.getSort() + " " + dataGridModel.getOrder();

		return pageFind(hql, firstResult, pageSize, null);
	}

	/**
	 * 根据路径和id获取子集文件文件夹
	 * 
	 * @date 2014-2-24
	 * @author
	 * @param fileFullPath
	 * @param sid
	 * @param person
	 */
	public List<TeeFileNetdisk> getSubFileObjBySidDao(String fileFullPath, String fileSids) {
		if (TeeUtility.isNullorEmpty(fileFullPath)) {
			fileFullPath = "";
		}
		if (TeeUtility.isNullorEmpty(fileSids)) {
			fileSids = "-1";
		}
		if (fileSids.endsWith(",")) {
			fileSids = fileSids.substring(0, fileSids.length() - 1);
		}

		if (fileFullPath.endsWith(",")) {
			fileFullPath = fileFullPath.substring(0, fileFullPath.length() - 1);
		}
		String[] sidArray = fileFullPath.split(",");
		StringBuffer buffer = new StringBuffer();
		if (sidArray.length > 0) {
			for (String sid : sidArray) {
				if (!TeeUtility.isNullorEmpty(buffer.toString())) {
					buffer.append(" or ");
				}
				buffer.append(" fileFullPath like'" + sid + "%'");

			}
		}
		String hql = "  from TeeFileNetdisk where fileNetdiskType=1  and (" + buffer.toString() + ") or sid in(" + fileSids + ")";
		return executeQuery(hql, null);
	}

	/**
	 * 查询 附件大小
	 * 
	 * @date 2014-2-21
	 * @author
	 * @param attachSids
	 * @return
	 */
	public long getModuleFileSizeDao(TeePerson person, String module) {
		long size = 0;
		if (TeeUtility.isNullorEmpty(module)) {
			return size;
		}
		Session session = this.getSession();

		List<TeeAttachment> list = new ArrayList<TeeAttachment>();
		String hql = "select attachment from TeeAttachment attachment where attachment.model = '" + module + "' and attachment.user.uuid = '" + person.getUuid() + "'";
		Query query = session.createQuery(hql);
		list = query.list();
		for (TeeAttachment a : list) {
			size += a.getSize();
		}
		return size;
	}

	/**
	 * 根据路径获取文件和文件(级联删除)
	 * 
	 * @date 2014-3-17
	 * @author
	 * @param fileFullPath
	 * @param sid
	 * @param person
	 * @return
	 */
	public List<TeeFileNetdisk> getFileByFileFullPathDao(String fileFullPath, int sid, TeePerson person) {
		if (!TeeUtility.isNullorEmpty(fileFullPath)) {
			Object[] param = { person.getUuid() };
			String hql = "  from TeeFileNetdisk where fileNetdiskType=1 and creater.uuid=? and fileFullPath like'" + fileFullPath + "%' or sid=" + sid;
			return executeQuery(hql, param);
		} else {
			return new ArrayList<TeeFileNetdisk>();
		}
	}

	/**
	 * 删除附件
	 * 
	 * @date 2014-3-17
	 * @author
	 * @param attachSids
	 */
	public void deleteAttachByIds(String attachSids) {
		if (TeeUtility.isNullorEmpty(attachSids)) {
			attachSids = "0";
		}
		if (attachSids.endsWith(",")) {
			attachSids = attachSids.substring(0, attachSids.length() - 1);
		}
		Session session = this.getSession();
		String hql = "delete from TeeAttachment  where sid in (" + attachSids + ")";
		Query query = session.createQuery(hql);
		query.executeUpdate();
	}

	/**
	 * 删除文件夹
	 * 
	 * @date 2014-3-17
	 * @author
	 * @param attachSids
	 */
	public void deleteFolderByIds(String attachSids) {
		if (TeeUtility.isNullorEmpty(attachSids)) {
			attachSids = "0";
		}
		if (attachSids.endsWith(",")) {
			attachSids = attachSids.substring(0, attachSids.length() - 1);
		}
		String hql = "delete from TeeFileNetdisk  where sid in (" + attachSids + ")";
		deleteOrUpdateByQuery(hql, null);
	}

	
	

	/**
	 * 根据全部路径获取所有子文件夹（个人网盘）
	 * @param fileFullPath 目录全部路径，多个用逗号隔开
	 * @param fileSids 选择目录
	 * @return
	 */
	public List<TeeFileNetdisk> getSubFolderObjByFullPathDao(String fileFullPath,String fileSids) {
		if (TeeUtility.isNullorEmpty(fileFullPath)) {
			fileFullPath = "";
		}
		if (fileFullPath.endsWith(",")) {
			fileFullPath = fileFullPath.substring(0, fileFullPath.length() - 1);
		}
		if (TeeUtility.isNullorEmpty(fileSids)) {
			fileSids = "-1";
		}
		if (fileSids.endsWith(",")) {
			fileSids = fileSids.substring(0, fileSids.length() - 1);
		}
		String[] sidArray = fileFullPath.split(",");
		StringBuffer buffer = new StringBuffer();
		if (sidArray.length > 0) {
			for (String sid : sidArray) {
				if (!TeeUtility.isNullorEmpty(buffer.toString())) {
					buffer.append(" or ");
				}
				buffer.append(" fileFullPath like'" + sid + "%'");

			}
		}
		String hql = "  from TeeFileNetdisk where filetype=0 and fileNetdiskType=1  and (" + buffer.toString() + ")  or sid in(" + fileSids + ")";
		return executeQuery(hql, null);
	}
	/**
	 * 根据sid串获取文件夹对象（个人网盘）
	 * @param sids
	 * @return
	 */
	public List<TeeFileNetdisk> getFolderObjBySidsDao(String sids) {
		if (TeeUtility.isNullorEmpty(sids)) {
			return new ArrayList<TeeFileNetdisk>();
		}
		if (sids.endsWith(",")) {
			sids = sids.substring(0, sids.length() - 1);
		}
		String hql = "from TeeFileNetdisk where fileNetdiskType=1 and filetype=0 and sid in (" + sids + ")";
		return executeQuery(hql, null);
	}

	
}
