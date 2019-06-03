package com.tianee.oa.core.base.fileNetdisk.dao;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("fileNetdiskDao")
public class TeeFileNetdiskDao extends TeeBaseDao<TeeFileNetdisk> {

	/**
	 * 获取文件网盘列表(一级目录)
	 * 
	 * @date 2014-1-5
	 * @author
	 * @return
	 */
	public List<TeeFileNetdisk> getFileNetdiskListDao() {
		String hql = "from TeeFileNetdisk where filetype=0 and fileNetdiskType=0 and fileParent is null order by fileNo";
		return executeQuery(hql, null);
	}

	/**
	 * 获取所有文件目录(父级、子级目录)
	 * 
	 * @date 2014-1-5
	 * @author
	 * @return
	 */
	public List<TeeFileNetdisk> getFileFolderListDao() {
		String hql = "from TeeFileNetdisk where filetype=0  order by fileNo";
		return executeQuery(hql, null);
	}

	/**
	 * 根据Id查询
	 * 
	 * @date 2014-1-5
	 * @author
	 * @param sid
	 * @return
	 */
	public TeeFileNetdisk getFileNetdiskByIdDao(int sid) {
		TeeFileNetdisk fileNetdisk = get(sid);
		return fileNetdisk;
	}

	/**
	 * 删除文件（不级联删除）
	 * 
	 * @date 2014-1-5
	 * @author
	 * @param sidStr
	 */
	public void delFileNetdiskByIdDao(String sidStr) {
		if (!TeeUtility.isNullorEmpty(sidStr)) {
			if (sidStr.endsWith(",")) {
				sidStr = sidStr.substring(0, sidStr.length() - 1);
			}
			String hql = " delete from TeeFileNetdisk where sid in(" + sidStr + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}

	/**
	 * 查询 附件
	 * 
	 * @author zhp
	 * @createTime 2013-10-6
	 * @editTime 下午01:56:19
	 * @desc
	 */
	public List<TeeAttachment> getAttachmentsBySids(String attachSids) {
		if (TeeUtility.isNullorEmpty(attachSids)) {
			return new ArrayList<TeeAttachment>();
		}
		if (attachSids.endsWith(",")) {
			attachSids = attachSids.substring(0, attachSids.length() - 1);
		}
		Session session = this.getSession();
		String hql = "from TeeAttachment ta where ta.sid in (" + attachSids + ")";
		Query query = session.createQuery(hql);
		return query.list();

	}

	/**
	 * 查询总数量
	 * 
	 * @date 2014-2-13
	 * @author
	 * @param folderSid
	 * @return
	 */
	public long getCountByFolderSid(String folderSid, TeePerson person,int projectManageId) {
//		if (TeeUtility.isNullorEmpty(folderSid)) {
//			folderSid = "0";
//		}
//		if (folderSid.endsWith(",")) {
//			folderSid = folderSid.substring(0, folderSid.length() - 1);
//		}
//		String hql = " select count(sid)  from TeeFileNetdisk where fileNetdiskType=0 and fileParent <> null and fileParent.sid in(" + folderSid + ")";
		
		
		Object[] values = { person.getUuid(), person.getDept().getUuid(), person.getUserRole().getUuid() };
		String hql = " select count(file.sid)  from TeeFileNetdisk file  where fileParent.sid in(" + folderSid + ") and fileNetdiskType=0 and ((filetype=0 and ( exists (select 1 from file.fileUserPriv fileUserPriv where fileUserPriv.user.uuid = ? and fileUserPriv.privValue>0)"
				+ " or exists (select 1 from file.fileDeptPriv fileDeptPriv where fileDeptPriv.dept.uuid = ? and fileDeptPriv.privValue>0)"
				+ " or exists (select 1 from file.fileRolePriv fileRolePriv where fileRolePriv.userRole.uuid = ? and fileRolePriv.privValue>0))) or filetype=1)";
		if (TeePersonService.checkIsSuperAdmin(person, null) || projectManageId==person.getUuid()) {
			values = null;
			hql = " select count(file.sid)  from TeeFileNetdisk file  where fileNetdiskType=0 and fileParent.sid in(" + folderSid + ")";
		}
		
		hql += "  order by fileNo asc";
		
		return count(hql, values);
	}
	
	/**
	 * 查询总数量
	 * 
	 * @date 2014-2-13
	 * @author
	 * @param folderSid
	 * @return
	 */
	public long getCountByFolderSid2(TeePerson person,String status,String status2) {
//		if (TeeUtility.isNullorEmpty(folderSid)) {
//			folderSid = "0";
//		}
//		if (folderSid.endsWith(",")) {
//			folderSid = folderSid.substring(0, folderSid.length() - 1);
//		}
//		String hql = " select count(sid)  from TeeFileNetdisk where fileNetdiskType=0 and fileParent <> null and fileParent.sid in(" + folderSid + ")";
		
		
		Object[] values = { person.getUuid(), person.getDept().getUuid(), person.getUserRole().getUuid() };
//		String hql = " select count(file.sid)  from TeeFileNetdisk file  where fileNetdiskType=0 and ((filetype=0 and ( exists (select 1 from file.fileUserPriv fileUserPriv where fileUserPriv.user.uuid = ? and fileUserPriv.privValue>0)"
//				+ " or exists (select 1 from file.fileDeptPriv fileDeptPriv where fileDeptPriv.dept.uuid = ? and fileDeptPriv.privValue>0)"
//				+ " or exists (select 1 from file.fileRolePriv fileRolePriv where fileRolePriv.userRole.uuid = ? and fileRolePriv.privValue>0))) or filetype=1)";
		List params = new ArrayList();
		params.addAll(params);
		String hql="select count(*) from TeeFileNetdisk where fileNetdiskType=0 and filetype=1 and "
				+ "(fileParent.sid in (select fileNetdisk.sid from TeeFileUserPriv where user.uuid=? and privValue>0) "
				+ "or fileParent.sid in (select fileNetdisk.sid from TeeFileDeptPriv where dept.uuid=? and privValue>0) "
				+ "or fileParent.sid in (select fileNetdisk.sid from TeeFileRolePriv where userRole.uuid=? and privValue>0))";
		if (TeePersonService.checkIsSuperAdmin(person, null)) {
			values = null;
			params.clear();
			hql = " select count(file.sid)  from TeeFileNetdisk file  where fileNetdiskType=0 and filetype=1 ";
		}
		Date date=new Date();
		if("1".equals(status)){//今天
			String str = TeeDateUtil.format(date, "yyyy-MM-dd");
			String str1=str+" 00:00:00";
			String str2=str+" 23:59:59";
			hql+=" and createTime>=? and createTime<=?";
			params.add(TeeDateUtil.parseDate(str1));
			params.add(TeeDateUtil.parseDate(str2));
		}else if("2".equals(status)){//本周
			Calendar cal = Calendar.getInstance();
			int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 2;
			cal.add(Calendar.DATE, -day_of_week);
			String mon = TeeDateUtil.format(cal.getTime(), "yyyy-MM-dd");
			cal.add(Calendar.DATE, 6);
			String sun = TeeDateUtil.format(cal.getTime(), "yyyy-MM-dd");
			hql+=" and createTime>=? and createTime<=?";
			
			params.add(TeeDateUtil.parseDate(mon+" 00:00:00"));
			params.add(TeeDateUtil.parseDate(sun+" 23:59:59"));
			
		}else if("3".equals(status)){//本月
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
			Calendar c = Calendar.getInstance();    
		    c.add(Calendar.MONTH, 0);
		    c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		    String first = format.format(c.getTime());
		    //获取当前月最后一天
		    Calendar ca = Calendar.getInstance();    
		    ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
		    String last = format.format(ca.getTime());
			hql+=" and createTime>=? and createTime<=?";
			
			params.add(TeeDateUtil.parseDate(first+" 00:00:00"));
			params.add(TeeDateUtil.parseDate(last+" 23:59:59"));
			
		}else if("4".equals(status)){//本季
			String year = TeeDateUtil.format(date, "yyyy");
			String month = TeeDateUtil.format(date, "MM");
			if("01".equals(month) || "02".equals(month) || "03".equals(month)){
				hql+=" and createTime>=? and createTime<=?";
				params.add(TeeDateUtil.parseDate(year+"-01-01 00:00:00"));
				params.add(TeeDateUtil.parseDate(year+"-03-31 23:59:59"));
			}else if("04".equals(month) || "05".equals(month) || "06".equals(month)){
				hql+=" and createTime>=? and createTime<=?";
				params.add(TeeDateUtil.parseDate(year+"-04-01 00:00:00"));
				params.add(TeeDateUtil.parseDate(year+"-06-30 23:59:59"));
			}else if("07".equals(month) || "08".equals(month) || "09".equals(month)){
				hql+=" and createTime>=? and createTime<=?";
				params.add(TeeDateUtil.parseDate(year+"-07-01 00:00:00"));
				params.add(TeeDateUtil.parseDate(year+"-09-30 23:59:59"));
			}else if("10".equals(month) || "11".equals(month) || "12".equals(month)){
				hql+=" and createTime>=? and createTime<=?";
				params.add(TeeDateUtil.parseDate(year+"-10-01 00:00:00"));
				params.add(TeeDateUtil.parseDate(year+"-12-31 23:59:59"));
			}
		}else if("5".equals(status)){//本年
			String year = TeeDateUtil.format(date, "yyyy");
			hql+=" and createTime>=? and createTime<=?";
			params.add(TeeDateUtil.parseDate(year+"-01-01 00:00:00"));
			params.add(TeeDateUtil.parseDate(year+"-12-31 23:59:59"));
		}
		
		
		hql += "";
		
		return count(hql, params.toArray());
	}
	
	/**
	 * 获取通用列表数据
	 * 
	 * @date 2014-2-15
	 * @author
	 * @param firstResult
	 * @param pageSize
	 * @param dataGridModel
	 * @param folderSid
	 * @param person
	 * @return
	 */
	public List<TeeFileNetdisk> getFileNetdisksPage(int firstResult, int pageSize, TeeDataGridModel dataGridModel, int folderSid, TeePerson person,int projectManageId) {
//		Object[] param = { folderSid };
//		String hql = " from TeeFileNetdisk t where t.fileParent.sid = ? and t.fileNetdiskType=0 ";
//		if (TeeUtility.isNullorEmpty(dataGridModel.getSort())) {
//			dataGridModel.setSort("fileName");
//			dataGridModel.setOrder("asc");
//		}
//		if ("createTimeStr".equals(dataGridModel.getSort())) {
//			dataGridModel.setSort("createTime");
//		}
//		hql += " order by filetype, " + dataGridModel.getSort() + " " + dataGridModel.getOrder();
		
		Object[] param = { person.getUuid(), person.getDept().getUuid(), person.getUserRole().getUuid() };
		String hql = " select file  from TeeFileNetdisk file  where fileParent.sid in(" + folderSid + ") and fileNetdiskType=0 and ((filetype=0 and ( exists (select 1 from file.fileUserPriv fileUserPriv where fileUserPriv.user.uuid = ? and fileUserPriv.privValue>0)"
				+ " or exists (select 1 from file.fileDeptPriv fileDeptPriv where fileDeptPriv.dept.uuid = ? and fileDeptPriv.privValue>0)"
				+ " or exists (select 1 from file.fileRolePriv fileRolePriv where fileRolePriv.userRole.uuid = ? and fileRolePriv.privValue>0))) or filetype=1) and fileParent.sid in(" + folderSid + ")";
		if (TeePersonService.checkIsSuperAdmin(person, null) || projectManageId==person.getUuid()) {
			param = null;
			hql = " select file  from TeeFileNetdisk file  where fileNetdiskType=0 and fileParent.sid in(" + folderSid + ")";
		}
		
		if (TeeUtility.isNullorEmpty(dataGridModel.getSort())) {
			dataGridModel.setSort("createTime");
			dataGridModel.setOrder("desc");
		}
		if ("createTimeStr".equals(dataGridModel.getSort())) {
			dataGridModel.setSort("createTime");
		}
		hql += " order by filetype, " + dataGridModel.getSort() + " " + dataGridModel.getOrder();

		return pageFind(hql, firstResult, pageSize, param);
	}
	
	
	/**
	 * 获取通用列表数据
	 * 
	 * @date 2014-2-15
	 * @author
	 * @param firstResult
	 * @param pageSize
	 * @param dataGridModel
	 * @param folderSid
	 * @param person
	 * @return
	 */
	public List<TeeFileNetdisk> getFileNetdisksPage2(int firstResult, int pageSize, TeeDataGridModel dataGridModel,TeePerson person,String status,String status2) {
		Object[] param = { person.getUuid(), person.getDept().getUuid(), person.getUserRole().getUuid() };
//		String hql = " select file  from TeeFileNetdisk file  where fileNetdiskType=0 and ((( exists (select 1 from file.fileUserPriv fileUserPriv where fileUserPriv.user.uuid = ? and fileUserPriv.privValue>0)"
//				+ " and exists (select 1 from file.fileDeptPriv fileDeptPriv where fileDeptPriv.dept.uuid = ? and fileDeptPriv.privValue>0)"
//				+ " or exists (select 1 from file.fileRolePriv fileRolePriv where fileRolePriv.userRole.uuid = ? and fileRolePriv.privValue>0))) or filetype=1) and filetype=1";
		List params = new ArrayList();
		params.addAll(params);
		String hql="from TeeFileNetdisk where fileNetdiskType=0 and filetype=1 and "
				+ "(fileParent.sid in (select fileNetdisk.sid from TeeFileUserPriv where user.uuid=? and privValue>0) "
				+ "or fileParent.sid in (select fileNetdisk.sid from TeeFileDeptPriv where dept.uuid=? and privValue>0) "
				+ "or fileParent.sid in (select fileNetdisk.sid from TeeFileRolePriv where userRole.uuid=? and privValue>0))";
		if (TeePersonService.checkIsSuperAdmin(person, null)) {
			param = null;
			params.clear();
			hql = " select file  from TeeFileNetdisk file  where fileNetdiskType=0 and filetype=1";
		}
		Date date=new Date();
		if("1".equals(status)){//今天
			String str = TeeDateUtil.format(date, "yyyy-MM-dd");
			String str1=str+" 00:00:00";
			String str2=str+" 23:59:59";
			hql+=" and createTime>=? and createTime<=?";
			params.add(TeeDateUtil.parseDate(str1));
			params.add(TeeDateUtil.parseDate(str2));
		}else if("2".equals(status)){//本周
			Calendar cal = Calendar.getInstance();
			int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 2;
			cal.add(Calendar.DATE, -day_of_week);
			String mon = TeeDateUtil.format(cal.getTime(), "yyyy-MM-dd");
			cal.add(Calendar.DATE, 6);
			String sun = TeeDateUtil.format(cal.getTime(), "yyyy-MM-dd");
			hql+=" and createTime>=? and createTime<=?";
			
			params.add(TeeDateUtil.parseDate(mon+" 00:00:00"));
			params.add(TeeDateUtil.parseDate(sun+" 23:59:59"));
			
		}else if("3".equals(status)){//本月
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
			Calendar c = Calendar.getInstance();    
		    c.add(Calendar.MONTH, 0);
		    c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		    String first = format.format(c.getTime());
		    //获取当前月最后一天
		    Calendar ca = Calendar.getInstance();    
		    ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
		    String last = format.format(ca.getTime());
			hql+=" and createTime>=? and createTime<=?";
			
			params.add(TeeDateUtil.parseDate(first+" 00:00:00"));
			params.add(TeeDateUtil.parseDate(last+" 23:59:59"));
			
		}else if("4".equals(status)){//本季
			String year = TeeDateUtil.format(date, "yyyy");
			String month = TeeDateUtil.format(date, "MM");
			if("01".equals(month) || "02".equals(month) || "03".equals(month)){
				hql+=" and createTime>=? and createTime<=?";
				params.add(TeeDateUtil.parseDate(year+"-01-01 00:00:00"));
				params.add(TeeDateUtil.parseDate(year+"-03-31 23:59:59"));
			}else if("04".equals(month) || "05".equals(month) || "06".equals(month)){
				hql+=" and createTime>=? and createTime<=?";
				params.add(TeeDateUtil.parseDate(year+"-04-01 00:00:00"));
				params.add(TeeDateUtil.parseDate(year+"-06-30 23:59:59"));
			}else if("07".equals(month) || "08".equals(month) || "09".equals(month)){
				hql+=" and createTime>=? and createTime<=?";
				params.add(TeeDateUtil.parseDate(year+"-07-01 00:00:00"));
				params.add(TeeDateUtil.parseDate(year+"-09-30 23:59:59"));
			}else if("10".equals(month) || "11".equals(month) || "12".equals(month)){
				hql+=" and createTime>=? and createTime<=?";
				params.add(TeeDateUtil.parseDate(year+"-10-01 00:00:00"));
				params.add(TeeDateUtil.parseDate(year+"-12-31 23:59:59"));
			}
		}else if("5".equals(status)){//本年
			String year = TeeDateUtil.format(date, "yyyy");
			hql+=" and createTime>=? and createTime<=?";
			params.add(TeeDateUtil.parseDate(year+"-01-01 00:00:00"));
			params.add(TeeDateUtil.parseDate(year+"-12-31 23:59:59"));
		}
		if (TeeUtility.isNullorEmpty(dataGridModel.getSort())) {
			dataGridModel.setSort("createTime");
			dataGridModel.setOrder("desc");
		}
		if ("createTimeStr".equals(dataGridModel.getSort())) {
			dataGridModel.setSort("createTime");
		}
		if("0".equals(status2)){
			hql+=" order by huiFuCount desc";
		}else if("1".equals(status2)){
			hql+=" order by readCount desc";
		}else if("2".equals(status2)){
			hql+=" order by xiaZaiCount desc";
		}else if("3".equals(status2)){
			hql+=" order by picCount desc";
		}else{
			hql += " order by filetype, " + dataGridModel.getSort() + " " + dataGridModel.getOrder();
		}

		return pageFind(hql, firstResult, pageSize, params.toArray());
	}
	
	/**
	 * 根据sid串获取文件夹目录
	 * 
	 * @date 2014-2-15
	 * @author
	 * @param sids
	 * @return
	 */
	public List<TeeFileNetdisk> getFileNetdiskBySids(String sids) {
		if (TeeUtility.isNullorEmpty(sids)) {
			return new ArrayList<TeeFileNetdisk>();
		}
		sids = sids.substring(1);
		sids = sids.replaceAll("/", ",");
		if (sids.endsWith(",")) {
			sids = sids.substring(0, sids.length() - 1);
		}
		String hql = "from TeeFileNetdisk where filetype=0 and fileNetdiskType=0 and sid in (" + sids + ")";
		return executeQuery(hql, null);
	}

	/**
	 * 根据父级sid获取文件和文件夹
	 * 
	 * @date 2014-2-15
	 * @author
	 * @param sids
	 * @return
	 */
	public List<TeeFileNetdisk> getFileByParentIdsDao(int sid) {
		String hql = "from TeeFileNetdisk t where t.fileNetdiskType=0 and t.fileParent.sid =" + sid;
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
	public List<TeeFileNetdisk> getFileBySidsDao(String sids) {
		if (TeeUtility.isNullorEmpty(sids)) {
			return new ArrayList<TeeFileNetdisk>();
		}
		if (sids.endsWith(",")) {
			sids = sids.substring(0, sids.length() - 1);
		}
		String hql = "from TeeFileNetdisk where fileNetdiskType=0 and  sid in (" + sids + ")";
		return executeQuery(hql, null);
	}

	/**
	 * 获取该文件夹的所有子文件夹（包括本文件夹）
	 * 
	 * @date 2014-2-19
	 * @author
	 * @param fileFullPath
	 * @param sid
	 * @return
	 */
	public List<TeeFileNetdisk> getFileListBySidsDao(String fileFullPath, int sid) {
		if (TeeUtility.isNullorEmpty(fileFullPath)) {
			return new ArrayList<TeeFileNetdisk>();
		}
		String hql = "from TeeFileNetdisk where file_type=0 and fileFullPath like'" + fileFullPath + "%'  order by sid asc";
		return executeQuery(hql, null);
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
		String hql = "  from TeeFileNetdisk where fileNetdiskType=0  and (" + buffer.toString() + ") or sid in(" + fileSids + ")";
		return executeQuery(hql, null);
	}

	/**
	 * 根据父级sid获取所有目录
	 * 
	 * @date 2014-1-5
	 * @author
	 * @return
	 */
	public List<TeeFileNetdisk> getFileFolderListByParentDao(String fileFullPath, String fileSids, String seleteSid, String optionFlag) {

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

		String hql = "";
		if ("1".equals(optionFlag)) {// 剪贴
			List<TeeFileNetdisk> selecTeeFileNetdisks = getFileNetdiskBySids(seleteSid);
			// 过滤掉选择的目录和子目录
			StringBuffer seleteBuffer = new StringBuffer();
			if (selecTeeFileNetdisks != null && selecTeeFileNetdisks.size() > 0) {
				for (TeeFileNetdisk selectFileNetdisk : selecTeeFileNetdisks) {
					if (!TeeUtility.isNullorEmpty(seleteBuffer.toString())) {
						seleteBuffer.append(" or ");
					}
					seleteBuffer.append(" fileFullPath not like'" + selectFileNetdisk.getFileFullPath() + selectFileNetdisk.getSid() + "/" + "%' and sid not in(" + seleteSid + ") ");
				}
			}
			// 如果没有被选中的文件夹，则置为true
			if (seleteBuffer.length() == 0) {
				seleteBuffer.append(" 1=1 ");
			}

			//有权限的目录
			StringBuffer buffer = new StringBuffer();
			if (sidArray.length > 0) {
				for (String sid : sidArray) {
					if (!TeeUtility.isNullorEmpty(buffer.toString())) {
						buffer.append(" or ");
					}
					buffer.append(" fileFullPath like'" + sid + "%'");

				}
			}

			hql = "from TeeFileNetdisk where filetype=0 " 
			+ "and fileNetdiskType=0 and (" + buffer.toString() + " and (sid not in(" + seleteSid + "))) " 
			+ "and (" + seleteBuffer.toString() + ") or sid in("+ fileSids + ") "
			+ "order by fileNo asc,fileName asc ";
		} else {
			StringBuffer buffer = new StringBuffer();
			if (sidArray.length > 0) {
				for (String sid : sidArray) {
					if (!TeeUtility.isNullorEmpty(buffer.toString())) {
						buffer.append(" or ");
					}
					buffer.append(" fileFullPath like'" + sid + "%'");
				}
			}
			hql = "from TeeFileNetdisk where filetype=0 and fileNetdiskType=0 and (" + buffer.toString() + " or sid in(" + fileSids + ")) order by fileNo asc,fileName asc";
		}
		return executeQuery(hql, null);
	}

	/**
	 * 根据文件夹id串获取该文件夹的所有文件
	 * 
	 * @date 2014-2-15
	 * @author
	 * @param sids
	 * @return
	 */
	public List<TeeFileNetdisk> getFilesBySidsDao(String sids) {
		if (TeeUtility.isNullorEmpty(sids)) {
			return new ArrayList<TeeFileNetdisk>();
		}
		if (sids.endsWith(",")) {
			sids = sids.substring(0, sids.length() - 1);
		}

		String hql = "from TeeFileNetdisk where filetype=1 and fileParent.sid in (" + sids + ")";
		return executeQuery(hql, null);
	}

	/**
	 * 删除文件（级联删除子文件）
	 * 
	 * @date 2014-2-15
	 * @author
	 * @param sidStr
	 */
	public void deleteFileBySidDao(String fileFullPath, int sid) {
		if (!TeeUtility.isNullorEmpty(fileFullPath)) {
			String hql = " delete from TeeFileNetdisk where fileFullPath like'" + fileFullPath + "%' or sid=" + sid;
			deleteOrUpdateByQuery(hql, null);
		}
	}

	/**
	 * 获取文件 带权限的
	 * 
	 * @date 2014年3月30日
	 * @author
	 * @param person
	 * @param menuSid
	 * @return
	 */
	public List<TeeFileNetdisk> getRootPrivFolderDao(TeePerson person, int menuSid) {
		Object[] values = { person.getUuid(), person.getDept().getUuid(), person.getUserRole().getUuid() };
		String hql = " select file  from TeeFileNetdisk file  where fileNetdiskType=0 and ( exists (select 1 from file.fileUserPriv fileUserPriv where fileUserPriv.user.uuid = ? and fileUserPriv.privValue>0)"
				+ " or exists (select 1 from file.fileDeptPriv fileDeptPriv where fileDeptPriv.dept.uuid = ? and fileDeptPriv.privValue>0)"
				+ " or exists (select 1 from file.fileRolePriv fileRolePriv where fileRolePriv.userRole.uuid = ? and fileRolePriv.privValue>0)) ";
		if (TeePersonService.checkIsSuperAdmin(person, null)) {
			values = null;
			hql = " select file  from TeeFileNetdisk file  where fileNetdiskType=0 and filetype=0 and fileParent is null ";
		}
		if (menuSid > 0) {
			hql += " and file.sid=" + menuSid;
		}
		hql += "  order by fileNo ";

		return executeQuery(hql, values);
	}
	
	/**
	 * 获取有权限的文件夹，包含父级文件夹 转存
	 * @param person
	 * @param menuSid
	 * @return
	 */
	public List<TeeFileNetdisk> getNetdiskFolders4PrivZC(TeePerson person, int menuSid) {
		Object[] values = { person.getUuid(), person.getDept().getUuid(), person.getUserRole().getUuid() };
		String hql = " select file  from TeeFileNetdisk file  where fileNetdiskType=0 and filetype=0 and ( exists (select 1 from file.fileUserPriv fileUserPriv where fileUserPriv.user.uuid = ? and  bitand(fileUserPriv.privValue,32)=32)"
				+ " or exists (select 1 from file.fileDeptPriv fileDeptPriv where fileDeptPriv.dept.uuid = ? and bitand(fileDeptPriv.privValue,32)=32)"
				+ " or exists (select 1 from file.fileRolePriv fileRolePriv where fileRolePriv.userRole.uuid = ? and bitand(fileRolePriv.privValue,32)=32)) ";
		if (TeePersonService.checkIsSuperAdmin(person, null)) {
			values = null;
			hql = " select file  from TeeFileNetdisk file  where fileNetdiskType=0 and filetype=0 ";
		}
		if (menuSid > 0) {
			hql += " and file.sid=" + menuSid;
		}
		hql += "  order by fileNo asc";
		
		List<TeeFileNetdisk> list = executeQuery(hql, values);
		List<TeeFileNetdisk> otherList = new ArrayList();
		otherList.addAll(list);
		if (menuSid <= 0) {
			//将父网盘也同时取出来
			for(TeeFileNetdisk disk:list){
				filterSameDisk(otherList,disk);
			}
		}
		
		return otherList;
	}
	
	/**
	 * 获取有权限的文件夹，包含父级文件夹 转存
	 * @param person
	 * @param menuSid
	 * @return
	 */
	public List<TeeFileNetdisk> getNetdiskFolders4Priv(TeePerson person, int menuSid) {
		//获取当前登陆人  辅助部门的ids
		String otherDeptIds="";
		List<TeeDepartment> otherDeptList=person.getDeptIdOther();
		if(otherDeptList!=null&&otherDeptList.size()>0){
			for (TeeDepartment teeDepartment : otherDeptList) {
				otherDeptIds+=teeDepartment.getUuid()+",";
			}
			if(otherDeptIds.endsWith(",")){
				otherDeptIds=otherDeptIds.substring(0,otherDeptIds.length()-1);
			}
		}else{
			otherDeptIds="0";
		}
		
		
		//获取当前登陆人  辅助角色的ids
		String otherRoleIds="";
		List<TeeUserRole> otherRoleList=person.getUserRoleOther();
		if(otherRoleList!=null&&otherRoleList.size()>0){
			for (TeeUserRole role: otherRoleList) {
				otherRoleIds+=role.getUuid()+",";
			}
			if(otherRoleIds.endsWith(",")){
				otherRoleIds=otherRoleIds.substring(0,otherRoleIds.length()-1);
			}
		}else{
			otherRoleIds="0";
		}
		Object[] values = { person.getUuid(), person.getDept().getUuid(), person.getUserRole().getUuid()};
		String hql = " select file  from TeeFileNetdisk file  where fileNetdiskType=0 and filetype=0 and ( exists (select 1 from file.fileUserPriv fileUserPriv where fileUserPriv.user.uuid = ? and  fileUserPriv.privValue>0)"
				+ " or exists (select 1 from file.fileDeptPriv fileDeptPriv where fileDeptPriv.dept.uuid = ? and fileDeptPriv.privValue>0)"
				+ " or exists (select 1 from file.fileDeptPriv fileDeptPriv where "+TeeDbUtility.IN("fileDeptPriv.dept.uuid",otherDeptIds )+" and fileDeptPriv.privValue>0)"
				+ " or exists (select 1 from file.fileRolePriv fileRolePriv where "+TeeDbUtility.IN("fileRolePriv.userRole.uuid",otherRoleIds )+" and fileRolePriv.privValue>0) "
				+ " or exists (select 1 from file.fileRolePriv fileRolePriv where fileRolePriv.userRole.uuid = ? and fileRolePriv.privValue>0)) ";
		if (TeePersonService.checkIsSuperAdmin(person, null)) {
			values = null;
			hql = " select file  from TeeFileNetdisk file  where fileNetdiskType=0 and filetype=0 ";
		}
		if (menuSid > 0) {
			hql += " and file.sid=" + menuSid;
		}
		hql += "  order by fileNo asc";
		
		List<TeeFileNetdisk> list = executeQuery(hql, values);
		List<TeeFileNetdisk> otherList = new ArrayList();
		otherList.addAll(list);
		if (menuSid <= 0) {
			//将父网盘也同时取出来
			for(TeeFileNetdisk disk:list){
				filterSameDisk(otherList,disk);
			}
		}
		
		return otherList;
	}
	
	
	/**
	 * 循环判断是否存在重复的文件夹
	 * @param otherList
	 * @param disk
	 */
	private void filterSameDisk(List<TeeFileNetdisk> otherList,TeeFileNetdisk disk){
		if(disk.getFileParent()!=null && !otherList.contains(disk.getFileParent())){
//			boolean exists = false;
//			for(TeeFileNetdisk other:otherList){
//				if(other.getSid()==disk.getFileParent().getSid()){
//					exists = true;
//					break;
//				}
//			}
//			if(!exists){
				otherList.add(disk.getFileParent());
//			}
			filterSameDisk(otherList,disk.getFileParent());
		}
	}

	/**
	 * 获取权限原始值
	 * 
	 * @date 2014-1-15
	 * @author
	 * @param dbValue
	 * @return
	 */
	public String getPrivValue(int dbValue) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 1; i <= 64; i *= 2) {
			if (!TeeUtility.isNullorEmpty(buffer.toString().trim())) {
				buffer.append(",");
			}
			buffer.append(i & dbValue);
		}
		return buffer.toString();
	}

	/**
	 * 文件夹、文件剪贴
	 * 
	 * @date 2014-2-20
	 * @author
	 * @param folderSid
	 * @param sidStr
	 */
	public void updateFileParentBySidDao(TeeFileNetdisk disk, List<TeeFileNetdisk> list, String sidStr) {
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

			hql = hql + "  where  fileFullPath like '" + oldFullPathParentStr + "%'";
			int count = deleteOrUpdateByQuery(hql, null);
		}
	}

	/**
	 * 根据名称关键字检索公共网盘
	 * 
	 * @date 2014-3-2
	 * @author
	 * @param fileName
	 * @return
	 */
	public List<TeeFileNetdisk> queryFileNetdiskByNameDao(String fileFullPath, String fileName) {
		if (TeeUtility.isNullorEmpty(fileFullPath)) {
			fileFullPath = "";
		}
		if (TeeUtility.isNullorEmpty(fileName)) {
			fileName = "";
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
		Object value[] = { "%" + fileName + "%" };
		String hql = "  from TeeFileNetdisk where fileNetdiskType=0 and filetype=1  and (" + buffer.toString() + ") and fileName like ? ";
		return executeQuery(hql, value);

	}
	
	
	
	

	/**
	 * 检索文件总数
	 * 
	 * @date 2014-3-2
	 * @author
	 * @param fileName
	 * @return
	 * @throws ParseException 
	 */
	public List<TeeFileNetdisk> getCountByFolderSid(String fileFullPath, Map requestDatas) throws ParseException {
		String fileName = (String) requestDatas.get("fileName");
		int createrId = TeeStringUtil.getInteger(requestDatas.get("createrId"),0);
		String createTimeStrMin = (String) requestDatas.get("createTimeStrMin");
		String createTimeStrMax = (String) requestDatas.get("createTimeStrMax");
		
		
		if (TeeUtility.isNullorEmpty(fileFullPath)) {
			fileFullPath = "";
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
		String hql = " select count(sid)  from TeeFileNetdisk where fileNetdiskType=0 and filetype=1  and (" + buffer.toString() + ") ";
		List param = new ArrayList();
		if(!TeeUtility.isNullorEmpty(fileName)){
			hql += " and fileName like ? ";
			param.add("%" + fileName + "%");
		}
		if(createrId>0){
			hql += " and creater.uuid = ? ";
			param.add(createrId);
		}
		
		if(!TeeUtility.isNullorEmpty(createTimeStrMin)){
			hql += " and createTime>=?";
			param.add(TeeUtility.parseDate(createTimeStrMin));
		}
		if(!TeeUtility.isNullorEmpty(createTimeStrMax)){
			hql += " and createTime<?";
			param.add(TeeUtility.parseDate(createTimeStrMax));
		}
		return executeQueryByList(hql, param);
	}
	
	//获取当前文件夹下的所有多级子文件夹
	public List<TeeFileNetdisk> getChildFoldersByPath(int diskId){
		TeeFileNetdisk curFileNetdisk = get(TeeStringUtil.getInteger(diskId, 0));
		List<TeeFileNetdisk> list = null;
		if(curFileNetdisk!=null && curFileNetdisk.getFileFullPath()!=null){
			list = find("from TeeFileNetdisk where fileFullPath like '"+curFileNetdisk.getFileFullPath()+"%' and filetype=0", null);
		}else if(curFileNetdisk!=null && curFileNetdisk.getFileFullPath()==null){
			list = find("from TeeFileNetdisk where fileFullPath like '"+diskId+"/%' and filetype=0", null);
		}
		return list;
	}
}
