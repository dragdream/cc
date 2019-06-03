package com.tianee.oa.mobile.fileNetdisk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileNetdiskPersonDao;
import com.tianee.oa.core.base.fileNetdisk.model.TeeFileNetdiskModel;
import com.tianee.oa.core.base.fileNetdisk.service.TeeApacheZipUtil;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
@Service
public class TeeMobileFileNetdiskService extends TeeBaseService {
	@Autowired
	TeeFileNetdiskPersonDao fileNetdiskPersonDao;
	
	/**
	 * 获取隔个人文件/文件夹通用列表（二级）
	 * 
	 * @param dm
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getPersonFilePage(TeeDataGridModel dm, HttpServletRequest request) {
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		int  folderSid = TeeStringUtil.getInteger(request.getParameter("folderSid"), 0);
		//int parentSid = 0;
		if(folderSid == 0){//第一次、
			List<TeeFileNetdisk> personRootFolder = fileNetdiskPersonDao.getPersonRootFolderDao(person);
			if (personRootFolder != null && personRootFolder.size() > 0) {
				folderSid = personRootFolder.get(0).getSid();
			}
		}
		dataGridJson.setTotal(fileNetdiskPersonDao.getCountByFolderSid(TeeStringUtil.getInteger(folderSid, 0), person));
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置

		dm.setSort("createTime");
		dm.setOrder("desc");
		List<TeeFileNetdisk> fileNetdisks = fileNetdiskPersonDao.getFileNetdisksPage(firstIndex, dm.getRows(), dm,folderSid , person);

		List<TeeFileNetdiskModel> models = new ArrayList<TeeFileNetdiskModel>();

		if (fileNetdisks != null && fileNetdisks.size() > 0) {
			for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
				TeeFileNetdiskModel model = parseModel(fileNetdisk  , true);
				models.add(model);
			}
		}

		dataGridJson.setRows(models);
		return dataGridJson;
	}
	
	
	/**
	 * 个人网盘  按照内容检索文件
	 * 
	 * @param dm
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson searchPersonFiles(TeeDataGridModel dm, HttpServletRequest request) {
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String keyWords = TeeStringUtil.getString(request.getParameter("keyWords"));
		//过滤keyWord防止注入
		keyWords = TeeDbUtility.formatString(keyWords);
		String hql = "from TeeFileNetdisk f where f.fileNetdiskType=1 and f.filetype=1 and f.creater.uuid= "+person.getUuid();
		if("".equals(keyWords)){
			hql+=" and 1!=1";
		}else{
			hql+=" and (f.fileName like '%"+keyWords+"%' or f.content like '%"+keyWords+"%')";
		}
		List<TeeFileNetdisk> files = fileNetdiskPersonDao.find(hql+" order by f.sid desc", null);
		
		dataGridJson.setTotal(Long.parseLong(String.valueOf(files.size())));

		List<TeeFileNetdiskModel> models = new ArrayList<TeeFileNetdiskModel>();

		if (files != null && files.size() > 0) {
			for (TeeFileNetdisk fileNetdisk : files) {
				TeeFileNetdiskModel model = parseModel(fileNetdisk  , true);
				models.add(model);
			}
		}

		dataGridJson.setRows(models);
		return dataGridJson;
	}
	
	
	
	/**
	 * 获取公共网盘通用列表（二级）
	 * 
	 * @param dm
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getPublicFilePage(TeeDataGridModel dm, HttpServletRequest request) {
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		int  folderSid = TeeStringUtil.getInteger(request.getParameter("folderSid"), 0);
		Object[] values = { person.getUuid(), person.getDept().getUuid(), person.getUserRole().getUuid() };
		String hql = "  from TeeFileNetdisk file  where fileNetdiskType=0 and ( exists (select 1 from file.fileUserPriv fileUserPriv where fileUserPriv.user.uuid = ? and fileUserPriv.privValue>0)"
				+ " or exists (select 1 from file.fileDeptPriv fileDeptPriv where fileDeptPriv.dept.uuid = ? and fileDeptPriv.privValue>0)"
				+ " or exists (select 1 from file.fileRolePriv fileRolePriv where fileRolePriv.userRole.uuid = ? and fileRolePriv.privValue>0)) ";
		if (TeePersonService.checkIsSuperAdmin(person, null)) {
			values = null;
			hql = "  from TeeFileNetdisk file  where fileNetdiskType=0 ";
		}
		
		if (folderSid > 0) {
			values = null;
			hql = "  from TeeFileNetdisk file  where fileNetdiskType=0 ";
			hql += " and file.fileParent.sid=" + folderSid;
		}else{
			hql += " and fileParent is null and filetype=0";
		}
		hql += "  order by filetype asc,createTime desc ";
		

		long count = fileNetdiskPersonDao.count("select count(*) " + hql, values);
		dataGridJson.setTotal(count);
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置

		List<TeeFileNetdisk> fileNetdisks = fileNetdiskPersonDao.pageFind(hql,dm.getFirstResult(),dm.getRows(), values);

		List<TeeFileNetdiskModel> models = new ArrayList<TeeFileNetdiskModel>();

		if (fileNetdisks != null && fileNetdisks.size() > 0) {
			for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
				TeeFileNetdiskModel model = parseModel(fileNetdisk  , true);
				models.add(model);
			}
		}

		dataGridJson.setRows(models);
		return dataGridJson;
	}
	
	
	/**
	 * 获取公共网盘通用列表（二级）
	 * 
	 * @param dm
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson searchPublicFiles(TeeDataGridModel dm, HttpServletRequest request) {
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

		String keyWords = TeeStringUtil.getString(request.getParameter("keyWords"));
		//过滤keyWord防止注入
		keyWords = TeeDbUtility.formatString(keyWords);
		
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		Object[] values = { person.getUuid(), person.getDept().getUuid(), person.getUserRole().getUuid() };
		String hql = "  from TeeFileNetdisk file  where file.filetype=1 and ( exists (select 1 from file.fileParent.fileUserPriv fileUserPriv where fileUserPriv.user.uuid = ? and fileUserPriv.privValue>0)"
				+ " or exists (select 1 from file.fileParent.fileDeptPriv fileDeptPriv where fileDeptPriv.dept.uuid = ? and fileDeptPriv.privValue>0)"
				+ " or exists (select 1 from file.fileParent.fileRolePriv fileRolePriv where fileRolePriv.userRole.uuid = ? and fileRolePriv.privValue>0)) ";
		
		if("".equals(keyWords)){
			hql+=" and 1!=1";
		}else{
			hql+=" and (file.fileName like '%"+keyWords+"%' or file.content like '%"+keyWords+"%')";
		}
		
		hql += "  order by file.sid desc ";
		

		List<TeeFileNetdisk> fileNetdisks = fileNetdiskPersonDao.pageFind(hql,0,Integer.MAX_VALUE, values);
		dataGridJson.setTotal(Long.parseLong(String.valueOf(fileNetdisks.size())));
		
		List<TeeFileNetdiskModel> models = new ArrayList<TeeFileNetdiskModel>();

		if (fileNetdisks != null && fileNetdisks.size() > 0) {
			for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
				TeeFileNetdiskModel model = parseModel(fileNetdisk  , true);
				models.add(model);
			}
		}

		dataGridJson.setRows(models);
		return dataGridJson;
	}

	/**
	 * 转换模型
	 * @param fileNetdisk
	 * @param isSimple
	 * @return
	 */
	public TeeFileNetdiskModel parseModel(TeeFileNetdisk fileNetdisk , boolean isSimple){
		
		TeeFileNetdiskModel model = new TeeFileNetdiskModel();
		BeanUtils.copyProperties(fileNetdisk, model);
		model.setCreateTimeStr(TeeUtility.getDateTimeStr(fileNetdisk.getCreateTime()));
		TeeAttachment attaches = fileNetdisk.getAttachemnt();
		if (attaches != null) {
			TeeAttachmentModel attachmentModel = new TeeAttachmentModel();
			BeanUtils.copyProperties(attaches, attachmentModel);
			model.setAttacheModels(attachmentModel);
			model.setFileSize(TeeApacheZipUtil.getDisckSize(attachmentModel.getSize()));
			model.setFileTypeExt(attachmentModel.getExt());
			model.setAttachSid(attachmentModel.getSid());
		} else if (fileNetdisk.getFiletype() == 0) {
			model.setFileTypeExt("0");
		}
		TeeFileNetdisk parentDisk = fileNetdisk.getFileParent();
		int parentFileSid  = 0;
		String parentFileName = "";
		if(parentDisk != null){
			parentFileSid = parentDisk.getSid();
			parentFileName = parentDisk.getFileName();
		}
		model.setParentFileSid(parentFileSid);
		model.setParentFileName(parentFileName);
		return model;
	}
	
	
}
