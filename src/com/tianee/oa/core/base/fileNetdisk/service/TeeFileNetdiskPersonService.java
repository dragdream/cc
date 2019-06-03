package com.tianee.oa.core.base.fileNetdisk.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.bean.TeeAttachmentSpace;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileUserPriv;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileDeptPrivDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileNetdiskPersonDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileNetdiskReaderDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileRolePrivDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileUserPrivDao;
import com.tianee.oa.core.base.fileNetdisk.model.TeeFileNetdiskModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeFileConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeFileNetdiskPersonService extends TeeBaseService {
	@Autowired
	private TeeFileNetdiskPersonDao fileNetdiskPersonDao;
	@Autowired
	private TeeFileNetdiskReaderDao readerDao;
	
	@Autowired
	private TeeAttachmentDao attachmentDao;

	@Autowired
	private TeeFileUserPrivDao fileUserPrivDao;
	@Autowired
	private TeeFileDeptPrivDao fileDeptPrivDao;
	@Autowired
	private TeeFileRolePrivDao fileRolePrivDao;
	@Autowired
	private TeeAttachmentService attachmentService;

	/**
	 * 获取个人文件夹
	 * 
	 * @date 2014-2-21
	 * @author
	 * @param person
	 * @return
	 */
	public TeeJson getPersonFolderTreeService(TeePerson person) {
		TeeJson json = new TeeJson();
		List<TeeZTreeModel> fileFolderTree = new ArrayList<TeeZTreeModel>();

		int parentSid = 0;
		List<TeeFileNetdisk> personRootFolder = fileNetdiskPersonDao.getPersonRootFolderDao(person);
		if (personRootFolder != null && personRootFolder.size() > 0) {
			parentSid = personRootFolder.get(0).getSid();
			for (TeeFileNetdisk privFileNetdisk : personRootFolder) {
				List<TeeFileNetdisk> fileNetdisks = fileNetdiskPersonDao.getSubFolderListByParentIdDao("/"+privFileNetdisk.getSid() + "/", privFileNetdisk.getSid(), person);
				if (fileNetdisks != null && fileNetdisks.size() > 0) {
					for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
						String iconSkin = TeeZTreeModel.FILE_FOLDER;
						String parentId = "0";
						TeeZTreeModel ztree = new TeeZTreeModel();
						if (fileNetdisk.getFileParent() != null) {//不是一级节点     
							parentId = fileNetdisk.getFileParent().getSid() + "";
							ztree.setOpen(false);
						}else{//一级节点  即根目录   此时要展开
							ztree.setOpen(true);
						}

						List<TeeFileUserPriv> fileUserPrivs = fileNetdisk.getFileUserPriv();
						if (fileUserPrivs != null && fileUserPrivs.size() > 0) {
							iconSkin = TeeZTreeModel.FILE_FOLDER_SHARE;
						}
						
						ztree.setId(String.valueOf(fileNetdisk.getSid()));
						ztree.setName(fileNetdisk.getFileName());
						
						ztree.setpId(parentId);
						ztree.setIconSkin(iconSkin);
						fileFolderTree.add(ztree);
					}
				}
			}
		} else {
			TeeFileNetdisk fileNetdisk = new TeeFileNetdisk();
			fileNetdisk.setFileName("根目录");
			fileNetdisk.setCreater(person);
			fileNetdisk.setFiletype(0);
			fileNetdisk.setFileNetdiskType(1);
			fileNetdisk.setCreateTime(new Date());
			fileNetdiskPersonDao.save(fileNetdisk);
			

			parentSid = fileNetdisk.getSid();
			fileNetdisk.setFileFullPath("/"+parentSid+"/");

			TeeZTreeModel rooTztree = new TeeZTreeModel();
			rooTztree.setId(String.valueOf(fileNetdisk.getSid()));
			rooTztree.setName(fileNetdisk.getFileName());
			rooTztree.setOpen(true);
			rooTztree.setpId("0");
			rooTztree.setIconSkin(TeeZTreeModel.FILE_FOLDER);
			fileFolderTree.add(rooTztree);
		}

		Map map = new HashMap();
		map.put("ztreeData", fileFolderTree);
		map.put("parentSid", parentSid);

		json.setRtData(map);

		// json.setRtData(fileFolderTree);
		json.setRtState(true);
		json.setRtMsg("文件目录获取成功!");
		return json;
	}

	/**
	 * 获取共享目录树
	 * 
	 * @date 2014-2-21
	 * @author
	 * @param person
	 * @return
	 */
	public TeeJson getShareFolderTreeService(TeePerson person) {
		TeeJson json = new TeeJson();

		int nodeId = 0;

		List<TeeZTreeModel> fileFolderTree = new ArrayList<TeeZTreeModel>();
		List<String> ids = new ArrayList<String>();
		List<TeeFileNetdisk> personRootFolder = fileNetdiskPersonDao.getRootPrivFolderDao(person);
		
		if (personRootFolder != null && personRootFolder.size() > 0) {
			for (TeeFileNetdisk privFileNetdisk : personRootFolder) {
				String folderParentSid = privFileNetdisk.getFileParent() == null ? "0" : privFileNetdisk.getFileParent().getSid() + "";
				TeeZTreeModel rootZtree = new TeeZTreeModel();
				rootZtree.setId(String.valueOf(privFileNetdisk.getSid()));
				rootZtree.setName(privFileNetdisk.getFileName() + "（" + TeeUtility.null2Empty(privFileNetdisk.getCreater().getUserName()) + "）");
				//rootZtree.setOpen(true);
				rootZtree.setpId(folderParentSid);
				rootZtree.setIconSkin(TeeZTreeModel.FILE_FOLDER_SHARE);
				
				if(!ids.contains(String.valueOf(privFileNetdisk.getSid()))){
					ids.add(String.valueOf(privFileNetdisk.getSid()));
					fileFolderTree.add(rootZtree);
				}
				
				
				nodeId = privFileNetdisk.getSid();

				List<TeeFileNetdisk> fileNetdisks = fileNetdiskPersonDao.getSubFoldersByParentIdDao(privFileNetdisk.getFileFullPath(), privFileNetdisk.getCreater(),person);
				if (fileNetdisks != null && fileNetdisks.size() > 0) {

					for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
						String parentId = "0";
						if (fileNetdisk.getFileParent() != null) {
							parentId = fileNetdisk.getFileParent().getSid() + "";
						}
						TeeZTreeModel ztree = new TeeZTreeModel();
						ztree.setId(String.valueOf(fileNetdisk.getSid()));
						
						ztree.setName(fileNetdisk.getFileName() + "（" + TeeUtility.null2Empty(fileNetdisk.getCreater().getUserName()) + "）");
						ztree.setpId(parentId);
						
						
						if(personRootFolder.contains(fileNetdisk)){
							
							ztree.setIconSkin(TeeZTreeModel.FILE_FOLDER_SHARE);
							ztree.setOpen(false);
						}else{
							//ztree.setName(fileNetdisk.getFileName());
							ztree.setOpen(false);
							ztree.setIconSkin(TeeZTreeModel.FILE_FOLDER);
						}
						
						
						if(!ids.contains(String.valueOf(fileNetdisk.getSid()))){
							fileFolderTree.add(ztree);
							ids.add(String.valueOf(fileNetdisk.getSid()));
							
						}
					}
				}
			}
		}

		
		Map map = new HashMap();
		map.put("ztreeData", fileFolderTree);
		map.put("nodeId", nodeId);
		json.setRtData(map);

		// json.setRtData(fileFolderTree);
		json.setRtState(true);
		json.setRtMsg("文件目录获取成功!");
		return json;
	}

	/**
	 * 获取文件/文件夹通用列表（二级）
	 * 
	 * @param dm
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getPersonFilePageService(TeeDataGridModel dm, HttpServletRequest request) {
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String folderSid = request.getParameter("folderSid");

		dataGridJson.setTotal(fileNetdiskPersonDao.getCountByFolderSid(TeeStringUtil.getInteger(folderSid, 0), person));
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置

		List<TeeFileNetdisk> fileNetdisks = fileNetdiskPersonDao.getFileNetdisksPage(firstIndex, dm.getRows(), dm, TeeStringUtil.getInteger(folderSid, 0), person);

		List<TeeFileNetdiskModel> models = new ArrayList<TeeFileNetdiskModel>();

		if (fileNetdisks != null && fileNetdisks.size() > 0) {
			for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
				TeeFileNetdiskModel model = new TeeFileNetdiskModel();
				BeanUtils.copyProperties(fileNetdisk, model);
				model.setCreateTimeStr(sdf.format(fileNetdisk.getCreateTime()));
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
				models.add(model);
			}
		}

		dataGridJson.setRows(models);
		return dataGridJson;
	}

	/**
	 * 新建个人目录
	 * 
	 * @date 2014-2-21
	 * @author
	 * @param sid
	 * @param folderName
	 * @param person
	 * @return
	 */
	public TeeJson newSubFolderByIdService(int sid, String folderName, TeePerson person) {
		TeeJson json = new TeeJson();

		int nodeId = 0;
		String nodeName = "";
		int nodeParentId = 0;

		TeeFileNetdisk dbFileNetdisk = null;
		//如果是顶级目录，则自动找到其对应的个人网盘根目录，如果不存在，则插入一个根目录
		if(sid==0){
			dbFileNetdisk = fileNetdiskPersonDao.getRootFolder(person);
			if(dbFileNetdisk==null){
				dbFileNetdisk = new TeeFileNetdisk();
				dbFileNetdisk.setFileName("根目录");
				dbFileNetdisk.setCreater(person);
				dbFileNetdisk.setFiletype(0);
				dbFileNetdisk.setFileNetdiskType(1);
				dbFileNetdisk.setCreateTime(new Date());
				fileNetdiskPersonDao.save(dbFileNetdisk);
				dbFileNetdisk.setFileFullPath("/"+dbFileNetdisk.getSid()+"/");
				fileNetdiskPersonDao.update(dbFileNetdisk);
			}
		}else{
			dbFileNetdisk = fileNetdiskPersonDao.get(sid);
		}
		
		if (dbFileNetdisk != null) {
			TeeFileNetdisk fileNetdisk = new TeeFileNetdisk();
			fileNetdisk.setFileName(folderName);
			fileNetdisk.setCreater(person);
			fileNetdisk.setFiletype(0);
			fileNetdisk.setFileNetdiskType(1);
			fileNetdisk.setCreateTime(new Date());
			fileNetdisk.setFileParent(dbFileNetdisk);
			fileNetdiskPersonDao.save(fileNetdisk);
			
			fileNetdisk.setFileFullPath(dbFileNetdisk.getFileFullPath() + fileNetdisk.getSid() + "/");
			nodeParentId = fileNetdisk.getFileParent().getSid();
			
			nodeId = fileNetdisk.getSid();
			nodeName = fileNetdisk.getFileName();
		}else{
			throw new TeeOperationException("没有找到根目录");
		}
		
		
		Map map = new HashMap();
		map.put("nodeId", nodeId);
		map.put("nodeName", nodeName);
		map.put("iconSkin", TeeZTreeModel.FILE_FOLDER);
		map.put("nodeParentId", nodeParentId);

		json.setRtData(map);
		json.setRtState(true);
		json.setRtMsg("新建文件夹成功!");
		return json;
	}

	/**
	 * 获取文件夹完整级别目录
	 * 
	 * @date 2014-2-15
	 * @author
	 * @param sid
	 * @return
	 */
	public TeeJson getFolderPathBySidService(int sid, TeePerson person) {
		TeeJson json = new TeeJson();
		int fileParentId = 0;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		TeeFileNetdisk dbFileNetdisk = fileNetdiskPersonDao.get(sid);
		if (dbFileNetdisk != null) {
			TeeFileNetdisk paraentFileNetdisk = dbFileNetdisk.getFileParent();
			if (paraentFileNetdisk != null) {// 子目录
				fileParentId = dbFileNetdisk.getFileParent().getSid();

				if (!TeeUtility.isNullorEmpty(dbFileNetdisk.getFileFullPath())) {
					List<TeeFileNetdisk> fileNetdisks = fileNetdiskPersonDao.getSubFileNetdiskBySidDao(dbFileNetdisk.getFileFullPath(), person);
					if (fileNetdisks != null && fileNetdisks.size() > 0) {
						for (int i = 0; i < fileNetdisks.size(); i++) {
							TeeFileNetdisk fileNetdisk = fileNetdisks.get(i);
							Map<String, String> map = new HashMap<String, String>();
							map.put("sid", String.valueOf(fileNetdisk.getSid()));
							map.put("folderName", fileNetdisk.getFileName());
							list.add(map);
						}
					}
				}
			} else {// 根目录
				Map<String, String> map = new HashMap<String, String>();
				map.put("sid", String.valueOf(dbFileNetdisk.getSid()));
				map.put("folderName", dbFileNetdisk.getFileName());
				list.add(map);
			}
		}
		Map map = new HashMap();
		map.put("previous", fileParentId);
		map.put("folderPath", list);
		json.setRtState(true);
		json.setRtMsg("目录路径获取成功!");
		json.setRtData(map);
		return json;
	}

	/**
	 * 获取文件夹完整级别目录（个人文件柜共享）
	 * 
	 * @date 2014-2-15
	 * @author
	 * @param sid
	 * @return
	 */
	public TeeJson getShareFolderPathBySidService(int sid, TeePerson person) {
		TeeJson json = new TeeJson();
		int fileParentId = 0;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		TeeFileNetdisk dbFileNetdisk = fileNetdiskPersonDao.get(sid);
		if (dbFileNetdisk != null) {
			TeeFileNetdisk paraentFileNetdisk = dbFileNetdisk.getFileParent();
			if (paraentFileNetdisk != null) {// 子目录
				fileParentId = dbFileNetdisk.getFileParent().getSid();

				if (!TeeUtility.isNullorEmpty(dbFileNetdisk.getFileFullPath())) {
					List<TeeFileNetdisk> fileNetdisks = fileNetdiskPersonDao.getSubFileNetdiskBySidDao(dbFileNetdisk.getFileFullPath(), person);
					if (fileNetdisks != null && fileNetdisks.size() > 0) {
						for (int i = 0; i < fileNetdisks.size(); i++) {
							TeeFileNetdisk fileNetdisk = fileNetdisks.get(i);
							Map<String, String> map = new HashMap<String, String>();
							map.put("sid", String.valueOf(fileNetdisk.getSid()));
							map.put("folderName", fileNetdisk.getFileName());
							list.add(map);
						}
					}
				}
			} else {// 根目录
				Map<String, String> map = new HashMap<String, String>();
				map.put("sid", String.valueOf(dbFileNetdisk.getSid()));
				map.put("folderName", dbFileNetdisk.getFileName());
				list.add(map);
			}
		}
		Map map = new HashMap();
		map.put("previous", fileParentId);
		map.put("folderPath", list);
		json.setRtState(true);
		json.setRtMsg("目录路径获取成功!");
		json.setRtData(map);
		return json;
	}

	/**
	 * 上传附件（二级）
	 * 
	 * @date 2014-2-13
	 * @author
	 * @param sidStr
	 * @param attachSids
	 * @param person
	 * @return
	 */
	public TeeJson uploadNetdiskFileServbice(String sidStr, String attachSids, TeePerson person) {
		TeeJson json = new TeeJson();
		if (!TeeUtility.isNullorEmpty(attachSids)) {
			TeeFileNetdisk dbFileNetdisk = fileNetdiskPersonDao.get(TeeStringUtil.getInteger(sidStr, 0));
			if (dbFileNetdisk != null) {
				/* 获取文件父级id获取同一级的所有文件夹、文件 */
				List<TeeFileNetdisk> fileNetdisks = fileNetdiskPersonDao.getFileByParentIdsDao(person, String.valueOf(dbFileNetdisk.getSid()));
				List<String> fileNameList = this.getNameToList(fileNetdisks);

				List<TeeAttachment> attachments = fileNetdiskPersonDao.getAttachmentsBySids(attachSids);
				if (attachments != null && attachments.size() > 0) {
					for (TeeAttachment attachement : attachments) {
						attachement.setModelId("0");
						TeeFileNetdisk fileNetdisk = new TeeFileNetdisk();
						// fileNetdisk.setFileName(attachement.getFileName());
						fileNetdisk.setFiletype(1);
						fileNetdisk.setFileNetdiskType(1);
						fileNetdisk.setCreater(person);
						fileNetdisk.setCreateTime(attachement.getCreateTime().getTime());
						fileNetdisk.setAttachemnt(attachement);
//						fileNetdisk.setFileFullPath(attachement.getAttachmentPath());
						fileNetdisk.setFileParent(dbFileNetdisk);
						StringBuffer fileNameBuffer = new StringBuffer(attachement.getFileName());
						this.getFileReNameStr(fileNameBuffer, fileNameList, attachement.getFileName(), true);
						fileNetdisk.setFileName(fileNameBuffer.toString());
						attachement.setFileName(fileNameBuffer.toString());
						fileNetdiskPersonDao.save(fileNetdisk);
						fileNetdisk.setFileFullPath(dbFileNetdisk.getFileFullPath() + fileNetdisk.getSid() + "/");
						fileNetdiskPersonDao.update(fileNetdisk);
					}
				}
			}
		}
		json.setRtState(true);
		json.setRtMsg("附件上传成功！");
		return json;
	}

	/**
	 * 根据Id获取文件夹信息
	 * 
	 * @date 2014-1-5
	 * @author
	 * @param sid
	 * @return
	 */
	public TeeJson getFileNetdiskByIdServbice(int sid) {
		TeeJson json = new TeeJson();
		TeeFileNetdisk fileNetdisk = fileNetdiskPersonDao.get(sid);
		TeeFileNetdiskModel model = new TeeFileNetdiskModel();
		if (fileNetdisk != null && sid > 0) {
			BeanUtils.copyProperties(fileNetdisk, model);
			json.setRtData(model);
			json.setRtState(true);
		} else {
			json.setRtState(false);
			json.setRtMsg("未找到相关数据！");
		}
		return json;
	}

	/**
	 * 重命名文件夹（二级）
	 * 
	 * @date 2014-2-14
	 * @author
	 * @param sid
	 * @param folderName
	 * @param person
	 * @return
	 */
	public TeeJson reNameFolderById(int sid, String folderName, TeePerson person) {
		TeeJson json = new TeeJson();
		int nodeId = 0;
		String nodeName = "";
		int nodeParentId = 0;
		TeeFileNetdisk dbFileNetdisk = fileNetdiskPersonDao.get(sid);
		if (dbFileNetdisk != null) {
			TeeFileNetdisk parentFileNetdisk = dbFileNetdisk.getFileParent();
			int parentSid = 0;
			if (parentFileNetdisk != null) {
				parentSid = parentFileNetdisk.getSid();
			}
			/* 获取文件父级id获取同一级的所有文件夹、文件 */
			List<TeeFileNetdisk> fileNetdisks = fileNetdiskPersonDao.getFileByParentIdsDao(person, String.valueOf(parentSid));
			List<String> fileNameList = this.getNameToList(fileNetdisks);
			StringBuffer fileNameBuffer = new StringBuffer(folderName);
			this.getFileReNameStr(fileNameBuffer, fileNameList, folderName, false);
			folderName = fileNameBuffer.toString();

			dbFileNetdisk.setFileName(folderName);
			fileNetdiskPersonDao.update(dbFileNetdisk);
			nodeId = dbFileNetdisk.getSid();
			nodeName = dbFileNetdisk.getFileName();
		}

		Map map = new HashMap();
		map.put("nodeId", nodeId);
		map.put("nodeName", nodeName);
		map.put("iconSkin", TeeZTreeModel.FILE_FOLDER);
		map.put("nodeParentId", nodeParentId);

		json.setRtData(map);
		json.setRtState(true);
		json.setRtMsg("文件夹重命名成功!");
		return json;
	}

	/**
	 * 重命名文件（二级）
	 * 
	 * @date 2014-2-14
	 * @author
	 * @param sid
	 * @param fileName
	 * @param person
	 * @return
	 */
	public TeeJson reNameFileByIdService(int sid, String fileName, TeePerson person) {
		TeeJson json = new TeeJson();
		TeeFileNetdisk dbFileNetdisk = fileNetdiskPersonDao.get(sid);
		if (dbFileNetdisk != null) {
			TeeAttachment attachment = dbFileNetdisk.getAttachemnt();
			if (attachment != null) {
				TeeFileNetdisk parentFileNetdisk = dbFileNetdisk.getFileParent();
				int parentSid = 0;
				if (parentFileNetdisk != null) {
					parentSid = parentFileNetdisk.getSid();
				}
				/* 获取文件父级id获取同一级的所有文件夹、文件 */
				List<TeeFileNetdisk> fileNetdisks = fileNetdiskPersonDao.getFileByParentIdsDao(person, String.valueOf(parentSid));
				List<String> fileNameList = this.getNameToList(fileNetdisks);
				StringBuffer fileNameBuffer = new StringBuffer(fileName);
				this.getFileReNameStr(fileNameBuffer, fileNameList, fileName, true);
				fileName = fileNameBuffer.toString();

				attachment.setFileName(fileName);
				attachmentDao.update(attachment);
				dbFileNetdisk.setFileName(fileName);

				fileNetdiskPersonDao.update(dbFileNetdisk);
			}
		}
		json.setRtState(true);
		json.setRtMsg("文件重命名成功!");
		return json;
	}

	/**
	 * 删除目录及文件(级联删除)
	 * 
	 * @date 2014-2-22
	 * @author
	 * @param sids
	 *            要删除文件或文件夹sid串
	 * @param person
	 * @return
	 */
	public TeeJson deleteFileBySid(String sids, TeePerson person) {
		TeeJson json = new TeeJson();
		List<TeeFileNetdisk> fileNetdisks = fileNetdiskPersonDao.getFileBySidsDao(sids, person);

		List<TeeFileNetdisk> deleteFileFolders = new ArrayList<TeeFileNetdisk>();

		StringBuffer deleteFilesBuffer = new StringBuffer();

		StringBuffer deleteFoldersBuffer = new StringBuffer();

		if (fileNetdisks != null && fileNetdisks.size() > 0) {
//			for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
//
//				if (fileNetdisk.getFiletype() == 1) {// 删除附件
//					if (!TeeUtility.isNullorEmpty(deleteFilesBuffer.toString())) {
//						deleteFilesBuffer.append(",");
//					}
//					deleteFilesBuffer.append(fileNetdisk.getAttachemnt().getSid());
//				} else {// 获取文件夹和文件
//
//					String fileFullPath = fileNetdisk.getFileFullPath();
//					if (!TeeUtility.isNullorEmpty(fileFullPath)) {
//						deleteFileFolders = fileNetdiskPersonDao.getFileByFileFullPathDao(fileFullPath + fileNetdisk.getSid() + "/", fileNetdisk.getSid(), person);
//					} else {
//						deleteFileFolders = fileNetdiskPersonDao.getFileByFileFullPathDao(fileNetdisk.getSid() + "/", fileNetdisk.getSid(), person);
//					}
//					if (deleteFileFolders != null && deleteFileFolders.size() > 0) {
//						for (TeeFileNetdisk netdisk : deleteFileFolders) {
//							if (netdisk.getFiletype() == 1) {
//								if (!TeeUtility.isNullorEmpty(deleteFilesBuffer.toString())) {
//									deleteFilesBuffer.append(",");
//								}
//								deleteFilesBuffer.append(netdisk.getAttachemnt().getSid());
//							} else {
//								if (!TeeUtility.isNullorEmpty(deleteFoldersBuffer.toString())) {
//									deleteFoldersBuffer.append(",");
//								}
//								deleteFoldersBuffer.append(netdisk.getSid());
//							}
//						}
//					}
//				}
//			}
			
			
			//循环遍历所有文件及文件夹
			List<TeeFileNetdisk> list = null;
			for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
				if (fileNetdisk.getFiletype() == 1) {//如果是文件，则删除文件
					attachmentService.deleteAttach(fileNetdisk.getAttachemnt());
				}else{//如果不是文件夹，则取出他的下级文件及文件夹，并且进行删除
					list = simpleDaoSupport.find("from TeeFileNetdisk where fileFullPath like '"+fileNetdisk.getFileFullPath()+"%'", null);
					for(TeeFileNetdisk tmp:list){
						if (tmp.getFiletype() == 1) {//如果是文件，则删除文件
							attachmentService.deleteAttach(tmp.getAttachemnt());
							//删除签阅信息
							readerDao.deleteInfoByFileIdDao(String.valueOf(tmp.getSid()));
						}else{//如果是文件夹，则进行删除
							tmp.getFileDeptPriv().clear();
							tmp.getFileRolePriv().clear();
							tmp.getFileUserPriv().clear();
						}
						simpleDaoSupport.update(tmp);
						simpleDaoSupport.deleteByObj(tmp);
					}
					fileNetdisk.getFileDeptPriv().clear();
					fileNetdisk.getFileRolePriv().clear();
					fileNetdisk.getFileUserPriv().clear();
				}
				//simpleDaoSupport.update(fileNetdisk);
				simpleDaoSupport.deleteByObj(fileNetdisk);
			}
			
		}
		
		json.setRtState(true);
		json.setRtMsg("文件删除成功!");
		return json;
	}

	/**
	 * 获取文件目录树
	 * 
	 * @date 2014-2-20
	 * @author
	 * @param sid
	 * @param fileName
	 * @param person
	 * @return
	 */
	public TeeJson getAllFolderTreeService(TeePerson person, String optionFlag, String seleteSid) {
		TeeJson json = new TeeJson();
		List<TeeZTreeModel> fileFolderTree = new ArrayList<TeeZTreeModel>();
		
		List<TeeFileNetdisk> personRootFolder = fileNetdiskPersonDao.getPersonRootFolderDao(person);
		if (personRootFolder != null && personRootFolder.size() > 0) {
			StringBuffer fileFullPathBuffer = new StringBuffer();
			StringBuffer sidBuffer = new StringBuffer();
			for (TeeFileNetdisk privFileNetdisk : personRootFolder) {
				if (!TeeUtility.isNullorEmpty(privFileNetdisk.getSid())) {
					if (!TeeUtility.isNullorEmpty(sidBuffer.toString())) {
						sidBuffer.append(",");
					}
					sidBuffer.append(privFileNetdisk.getSid());
				}
				if (!TeeUtility.isNullorEmpty(fileFullPathBuffer.toString())) {
					fileFullPathBuffer.append(",");
				}
				fileFullPathBuffer.append(privFileNetdisk.getFileFullPath());
			}
			List<TeeFileNetdisk> fileNetdisks = fileNetdiskPersonDao.getFileFolderListByParentDao(fileFullPathBuffer.toString(), sidBuffer.toString(), seleteSid, optionFlag, person);
			if (fileNetdisks != null && fileNetdisks.size() > 0) {
				for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
					String parentId = "0";
					if (fileNetdisk.getFileParent() != null) {
						parentId = fileNetdisk.getFileParent().getSid() + "";
					}
					TeeZTreeModel ztree = new TeeZTreeModel();
					ztree.setId(String.valueOf(fileNetdisk.getSid()));
					ztree.setName(fileNetdisk.getFileName());
					ztree.setOpen(true);
					ztree.setpId(parentId);
					ztree.setIconSkin(TeeZTreeModel.FILE_FOLDER);
					fileFolderTree.add(ztree);
				}
			}
		}
		json.setRtData(fileFolderTree);
		json.setRtState(true);
		json.setRtMsg("文件目录获取成功!");
		return json;
	}

	/**
	 * 剪贴文件到其他目录
	 * 
	 * @date 2014-2-20
	 * @author
	 * @param person
	 * @param folderSid
	 *            目标id
	 * @param fileSids
	 *            拷贝目录id串
	 * @return
	 * @throws IOException
	 */
	public TeeJson cutFileToFolderService(TeePerson person, int folderSid, String fileSids) throws IOException {
		TeeJson json = new TeeJson();
		
		//获取目标文件夹
		TeeFileNetdisk fileNetdisk = fileNetdiskPersonDao.get(folderSid);
		
		//获取选中的文件
		List<TeeFileNetdisk> selectedFiles = fileNetdiskPersonDao.getFileBySidsDao(fileSids);
		
		//遍历选中的文件，判断目标文件夹是否在选中的文件夹的子级文件
		for(TeeFileNetdisk file:selectedFiles){
			if(file.getFiletype()==0 && fileNetdisk.getFileFullPath().contains("/"+file.getSid()+"/")){//文件夹
				throw new TeeOperationException("目标文件夹为源文件夹的子文件夹，无法进行剪切");
			}
		}
		
		//遍历这三个文件
		
		for(TeeFileNetdisk file:selectedFiles){
			file.setFileFullPath(fileNetdisk.getFileFullPath()+file.getSid()+"/");
			file.setFileParent(fileNetdisk);
			fileNetdiskPersonDao.update(file);
			changeFullPathAndCut(person,file);
		}
		
		json.setRtState(true);
		json.setRtMsg("文件目录获取成功!");
		return json;
	}
	
	private void changeFullPathAndCut(TeePerson person,TeeFileNetdisk file){
		if(file.getFiletype()==0){//文件夹
			//获取当前文件夹的子级文件夹及文件
			List<TeeFileNetdisk> selectedFiles = fileNetdiskPersonDao.getFileByParentIdsDao(person,file.getSid()+"");
			
			for(TeeFileNetdisk f:selectedFiles){
				f.setFileFullPath(file.getFileFullPath()+f.getSid()+"/");
				f.setFileParent(file);
				fileNetdiskPersonDao.update(f);
				changeFullPathAndCut(person,f);
			}
		}
	}

	/**
	 * 根据选择的目录id串返回目录树（包括所有子目录）
	 * @param fileSids
	 * @return
	 */
	public List<TeeZTreeModel> getTeeZTreeModelList(String fileSids){
		List<TeeZTreeModel> fileFolderTree = new ArrayList<TeeZTreeModel>();
		
		// 根据sid串获取文件夹对象
		List<TeeFileNetdisk> dbFileNetdisks1 = fileNetdiskPersonDao.getFolderObjBySidsDao(fileSids);
		// 拼装目录路径
		StringBuffer buffer = new StringBuffer();
		for (TeeFileNetdisk dbFileNetdisk : dbFileNetdisks1) {
			if (!TeeUtility.isNullorEmpty(buffer.toString())) {
				buffer.append(",");
			}
			buffer.append(TeeUtility.isNullorEmpty(dbFileNetdisk.getFileFullPath()) == true ? "" : dbFileNetdisk.getFileFullPath() + dbFileNetdisk.getSid() + "/");
		}
		// 根据路径和id获取文件和文件夹
		List<TeeFileNetdisk> subFolderList = fileNetdiskPersonDao.getSubFolderObjByFullPathDao(buffer.toString(), fileSids);
		// 根据路径长度进行升序排序
		Collections.sort(subFolderList, new Comparator<TeeFileNetdisk>() {
			public int compare(TeeFileNetdisk arg0, TeeFileNetdisk arg1) {
				String hits0 = arg0.getFileFullPath();
				String hits1 = arg1.getFileFullPath();
				int level0 = 0;
				int level1 = 0;
				if (!TeeUtility.isNullorEmpty(hits0)) {
					level0 = hits0.split("/").length;
				}
				if (!TeeUtility.isNullorEmpty(hits1)) {
					level1 = hits1.split("/").length;
				}
				if (level0 > level1) {
					return 1;
				} else if (hits1 == hits0) {
					return 0;
				} else {
					return -1;
				}
			}
		});
		if (subFolderList != null && subFolderList.size() > 0) {
			for (TeeFileNetdisk folderObj : subFolderList) {
				TeeZTreeModel ztree = new TeeZTreeModel();
				ztree.setId(String.valueOf(folderObj.getSid()));
				ztree.setName(folderObj.getFileName());
				ztree.setOpen(true);
				ztree.setpId(String.valueOf(folderObj.getFileParent().getSid()));
				ztree.setIconSkin(TeeZTreeModel.FILE_FOLDER);
				fileFolderTree.add(ztree);

			}
		}
		return fileFolderTree;
	}

	/**
	 * 获取文件名
	 * 
	 * @date 2014-2-22
	 * @author
	 * @param dbFileNetdisks
	 * @return
	 */
	public List<String> getNameToList(List<TeeFileNetdisk> dbFileNetdisks) {
		List<String> list = new ArrayList<String>();
		if (dbFileNetdisks != null && dbFileNetdisks.size() > 0) {
			for (TeeFileNetdisk fileNetdisk : dbFileNetdisks) {
				list.add(fileNetdisk.getFileName());
			}
		}
		return list;
	}

	/**
	 * 根据Id获取文件夹信息
	 * 
	 * @date 2014-2-20
	 * @author
	 * @param sid
	 * @return
	 */
	public TeeFileNetdisk getFileNetdiskObjById(int sid) {
		TeeFileNetdisk fileNetdisk = fileNetdiskPersonDao.get(sid);
		return fileNetdisk;
	}

	/**
	 * 批量文件打包zip下载
	 * 
	 * @date 2014-2-19
	 * @author
	 * @param folderSid
	 * @param fileSid
	 * @throws Exception
	 */
	public void downFileToZipBySidService(OutputStream outputStream, String zipFileName, String fileSid, TeePerson person) throws Exception {
		if (TeeUtility.isNullorEmpty(zipFileName)) {
			zipFileName = "个人文件批量下载";
		}
		String fileUploadTempDir = TeeSysProps.getTmpPath()+File.separator+ zipFileName;

		// 获取选择的文件夹和文件
		List<TeeFileNetdisk> fileNetdisks = fileNetdiskPersonDao.getFileBySidsDao(fileSid, person);
		List<Map<String, Object>> returnList = this.getFileNetdiskList(fileNetdisks, person);
		if (returnList != null && returnList.size() > 0) {
			for (Map<String, Object> map : returnList) {
				String folderPath = (String) map.get("folderPath");
				List<TeeFileNetdisk> returnFileNetdisks = (List<TeeFileNetdisk>) map.get("fileList");
				String fileDirPath = fileUploadTempDir + "/" + folderPath;
				File fileDir = new File(fileDirPath);
				if (!fileDir.exists()) {
					fileDir.mkdirs();
				}
				if (returnFileNetdisks != null && returnFileNetdisks.size() > 0) {
					for (TeeFileNetdisk reTeeFileNetdisk : returnFileNetdisks) {
						TeeAttachment attachment = reTeeFileNetdisk.getAttachemnt();
						if (attachment != null) {
							String encryAlgo = attachment.getEncryAlgo();//加密算法
							if(!TeeUtility.isNullorEmpty(encryAlgo)){
								String destPath = fileDir.getAbsolutePath() + File.separator + reTeeFileNetdisk.getFileName();
								TeeFileUtility.copyDecryptFile(attachment, destPath);
							}else{
								TeeAttachmentSpace attachSpace = attachment.getAttachSpace();
								if (attachSpace != null) {
									String fromFilePath = (attachSpace.getSpacePath() + File.separator + attachment.getModel() + File.separator  + attachment.getAttachmentPath()).replace("\\", "/").replace("//", "/");
									String fromFile = (fromFilePath + File.separator  + attachment.getAttachmentName()).replace("\\", "/").replace("//", "/");
									String filePath = fileDir.getAbsolutePath() + File.separator + reTeeFileNetdisk.getFileName();
									
									File file=new File(fromFile);
									if(file.exists()){
										TeeApacheZipUtil.copyFile(fromFile, filePath);
									}
									
								}
							}
						}
					}
				}
			}
		}

		if (!TeeUtility.isNullorEmpty(zipFileName)) {
			TeeApacheZipUtil.doZip(fileUploadTempDir, outputStream);
			TeeFileUtility.deleteAll(fileUploadTempDir);
		}
	}

	/**
	 * 根据选择文件List对象获取文件夹下的所有子文件夹和文件（包括当前对象）
	 * 
	 * @date 2014-2-19
	 * @author
	 * @param fileNetdisks
	 * @return 返回list（文件夹sid，文件夹路径、文件夹下的文件）
	 */
	public List<Map<String, Object>> getFileNetdiskList(List<TeeFileNetdisk> fileNetdisks, TeePerson person) {
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		// 文件集合
		List<TeeFileNetdisk> fileList = new ArrayList<TeeFileNetdisk>();
		if (fileNetdisks != null && fileNetdisks.size() > 0) {
			String rootFolderName = "";
			int rootFolderSid = 0;
			TeeFileNetdisk rootFileNetdisk = fileNetdisks.get(0);
			if (rootFileNetdisk != null) {
				TeeFileNetdisk fileParent = rootFileNetdisk.getFileParent();
				if (fileParent != null) {
					rootFolderSid = fileParent.getSid();
					rootFolderName = fileParent.getFileName() + "/";
				} else {
					// rootFolderSid = rootFileNetdisk.getSid();
					// rootFolderName = rootFileNetdisk.getFileName()+ "/";
				}
			}

			for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
				if (fileNetdisk.getFiletype() == 0) {
					// 获取该文件夹的所有子文件夹（包括本文件夹）
					List<TeeFileNetdisk> folderList = fileNetdiskPersonDao.getSubFolderListBySidAscDao(TeeUtility.null2Empty(fileNetdisk.getFileFullPath()) + fileNetdisk.getSid() + "/",
							fileNetdisk.getSid(), person);
					// 获取该文件夹id串
					String folderSidStr = this.getSidsByList(folderList);
					// 根据文件夹id串获取该文件夹的所有文件
					List<TeeFileNetdisk> filesList = fileNetdiskPersonDao.getSubFilesByParentSidsDao(folderSidStr, person);
					// 循环目录返回数据
					if (folderList != null && folderList.size() > 0) {
						Map<Integer, String> folderMap = this.getFolderPathByFolder(folderList);
						Map<Integer, List<TeeFileNetdisk>> fileNetdiskMap = this.getFolderMapByList(folderList, filesList);
						for (TeeFileNetdisk folderObj : folderList) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("sid", folderObj.getSid());
							map.put("folderPath", rootFolderName + folderMap.get(folderObj.getSid()));
							map.put("fileList", fileNetdiskMap.get(folderObj.getSid()));
							returnList.add(map);
						}
					}
				} else {
					fileList.add(fileNetdisk);
				}
			}

			if (rootFolderSid != 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("sid", rootFolderSid);
				map.put("folderPath", rootFolderName);
				map.put("fileList", fileList);
				returnList.add(map);
			}
		}
		return returnList;
	}

	/**
	 * 获取目录sid串
	 * 
	 * @date 2014-2-19
	 * @author
	 * @param fileNetdisks
	 * @return
	 */
	public String getSidsByList(List<TeeFileNetdisk> fileNetdisks) {
		StringBuffer buffer = new StringBuffer();
		if (fileNetdisks != null && fileNetdisks.size() > 0) {
			for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
				if (buffer != null && !TeeUtility.isNullorEmpty(buffer.toString())) {
					buffer.append(",");
				}
				buffer.append(fileNetdisk.getSid());
			}
		}
		if (TeeUtility.isNullorEmpty(buffer.toString())) {
			buffer.append("-1");
		}
		return buffer.toString();
	}

	/**
	 * 根据sid获取目录路径
	 * 
	 * @date 2014-2-19
	 * @author
	 * @param fileNetdisks
	 * @return
	 */
	public Map<Integer, String> getFolderPathByFolder(List<TeeFileNetdisk> fileNetdisks) {
		StringBuffer buffer = new StringBuffer();
		Map<Integer, String> map = new HashMap<Integer, String>();
		if (fileNetdisks != null && fileNetdisks.size() > 0) {
			for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
				map.put(fileNetdisk.getSid(), buffer.append(fileNetdisk.getFileName() + "/").toString());
			}
		}
		return map;
	}

	/**
	 * 根据文件夹sid获取该文件夹文件
	 * 
	 * @date 2014-2-19
	 * @author
	 * @param folderList
	 *            文件夹
	 * @param fileList
	 *            文件
	 * @return
	 */
	public Map<Integer, List<TeeFileNetdisk>> getFolderMapByList(List<TeeFileNetdisk> folderList, List<TeeFileNetdisk> fileList) {
		Map<Integer, List<TeeFileNetdisk>> map = new HashMap<Integer, List<TeeFileNetdisk>>();
		if (folderList != null && folderList.size() > 0) {
			for (TeeFileNetdisk folder : folderList) {
				List<TeeFileNetdisk> listItem = new ArrayList<TeeFileNetdisk>();
				int folderSid = folder.getSid();
				if (fileList != null && fileList.size() > 0) {

					for (TeeFileNetdisk fileNetdisk : fileList) {
						TeeFileNetdisk fileParent = fileNetdisk.getFileParent();
						if (fileParent != null) {
							if (folderSid == fileParent.getSid()) {
								listItem.add(fileNetdisk);
							}
						}
					}
				}
				map.put(folderSid, listItem);
				fileList.removeAll(listItem);
			}
		}
		return map;
	}

	/**
	 * 获取文件/文件夹通用列表（共享文件柜）
	 * 
	 * @date 2014-2-23
	 * @author
	 * @param dm
	 * @param request
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getPersonFileSharePageService(TeeDataGridModel dm, HttpServletRequest request) {
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String folderSid = request.getParameter("folderSid");

		dataGridJson.setTotal(fileNetdiskPersonDao.getCountByFolderSid(TeeStringUtil.getInteger(folderSid, 0)));
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置

		List<TeeFileNetdisk> fileNetdisks = fileNetdiskPersonDao.getFileNetdisksPage(firstIndex, dm.getRows(), dm, TeeStringUtil.getInteger(folderSid, 0));

		List<TeeFileNetdiskModel> models = new ArrayList<TeeFileNetdiskModel>();

		if (fileNetdisks != null && fileNetdisks.size() > 0) {
			for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
				TeeFileNetdiskModel model = new TeeFileNetdiskModel();
				BeanUtils.copyProperties(fileNetdisk, model);
				model.setCreateTimeStr(sdf.format(fileNetdisk.getCreateTime()));
				model.setCreaterStr(fileNetdisk.getCreater().getUserName());
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
				models.add(model);
			}
		}
		dataGridJson.setRows(models);
		return dataGridJson;
	}

	/**
	 * 获取权限值（共享文件柜）
	 * 
	 * @date 2014-2-16
	 * @author
	 * @param sids
	 * @param person
	 * @return
	 */
	public TeeJson getFilePrivValueBySid(int sid, TeePerson person) {
		TeeJson json = new TeeJson();
		int rootFolderPriv = 0;
		TeeFileNetdisk dbFileNetdisk = fileNetdiskPersonDao.get(sid);
		if (dbFileNetdisk != null) {
			rootFolderPriv = this.getFileNetdiskPriv(person, dbFileNetdisk);
			/*
			 * TeeFileNetdisk paraentFileNetdisk =
			 * dbFileNetdisk.getFileParent(); if (paraentFileNetdisk != null)
			 * {// 子目录 String fileFullPath =
			 * TeeUtility.null2Empty(dbFileNetdisk.getFileFullPath()); int
			 * rootFolderSid =
			 * TeeStringUtil.getInteger(fileFullPath.split("/")[0], 0);
			 * TeeFileNetdisk fileNetdisk =
			 * fileNetdiskPersonDao.get(rootFolderSid); rootFolderPriv =
			 * this.getFileNetdiskPriv(person, fileNetdisk); } else {
			 * rootFolderPriv = this.getFileNetdiskPriv(person, dbFileNetdisk);
			 * }
			 */
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("rootFolderPriv", rootFolderPriv);
		json.setRtState(true);
		json.setRtData(map);
		json.setRtMsg("获取文件权限成功!");
		return json;
	}

	/**
	 * 获取权限值（共享文件柜）
	 * 
	 * @date 2014-2-16
	 * @author
	 * @param person
	 * @param fileNetdisk
	 * @return
	 */
	public int getFileNetdiskPriv(TeePerson person, TeeFileNetdisk fileNetdisk) {
		int rootFolderPriv = 0;
		int fileUserPrivValue = 0;
		Set<Integer> filePrivSet = new HashSet<Integer>();
		if (fileNetdisk != null) {
			List<TeeFileUserPriv> fileUserPrivs = fileUserPrivDao.getUserPrivDao(person, fileNetdisk);
			if (fileUserPrivs != null && fileUserPrivs.size() > 0) {
				fileUserPrivValue = fileUserPrivs.get(0).getPrivValue();
			}
			if (fileUserPrivValue != 0) {
				List<String> fileUserPrivList = Arrays.asList(this.getPrivValue(fileUserPrivValue).split(","));

				if (fileUserPrivList != null && fileUserPrivList.size() > 0) {
					for (String str : fileUserPrivList) {
						filePrivSet.add(TeeStringUtil.getInteger(str, 0));
					}
				}
			}
		}
		Iterator<Integer> iterator = filePrivSet.iterator();
		while (iterator.hasNext()) {
			int setValue = iterator.next();
			rootFolderPriv += setValue;
		}
		return rootFolderPriv;
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
		for (int i = 1; i <= 32; i *= 2) {
			if (!TeeUtility.isNullorEmpty(buffer.toString().trim())) {
				buffer.append(",");
			}
			buffer.append(i & dbValue);
		}
		return buffer.toString();
	}

	/**
	 * 批量文件打包zip下载（共享文件柜）
	 * 
	 * @date 2014-2-19
	 * @author
	 * @param folderSid
	 * @param fileSid
	 * @throws Exception
	 */
	public void downFileShareToZipBySidService(OutputStream outputStream, String zipFileName, String fileSid) throws Exception {
		if (TeeUtility.isNullorEmpty(zipFileName)) {
			zipFileName = "个人文件批量下载";
		}
		String fileUploadTempDir = TeeSysProps.getTmpPath()+File.separator+ zipFileName;
		// 获取选择的文件夹和文件
		List<TeeFileNetdisk> fileNetdisks = fileNetdiskPersonDao.getFileBySidsDao(fileSid);
		List<Map<String, Object>> returnList = this.getFileNetdiskList(fileNetdisks);
		if (returnList != null && returnList.size() > 0) {
			for (Map<String, Object> map : returnList) {
				String folderPath = (String) map.get("folderPath");
				List<TeeFileNetdisk> returnFileNetdisks = (List<TeeFileNetdisk>) map.get("fileList");
				String fileDirPath = fileUploadTempDir + "/" + folderPath;
				File fileDir = new File(fileDirPath);
				if (!fileDir.exists()) {
					fileDir.mkdirs();
				}
				if (returnFileNetdisks != null && returnFileNetdisks.size() > 0) {
					for (TeeFileNetdisk reTeeFileNetdisk : returnFileNetdisks) {
						TeeAttachment attachment = reTeeFileNetdisk.getAttachemnt();
						if(!TeeUtility.isNullorEmpty(attachment)){
							TeeAttachmentSpace attachSpace = attachment.getAttachSpace();
							if (attachSpace != null) {
								String fromFilePath = attachSpace.getSpacePath() + File.separator + attachment.getModel() + attachment.getAttachmentPath();
								String fromFile = fromFilePath + attachment.getAttachmentName();
								String filePath = fileDir.getAbsolutePath() + File.separator + attachment.getFileName();
								TeeApacheZipUtil.copyFile(fromFile, filePath);
							}
						}
						
					}
				}
			}
		}

		if (!TeeUtility.isNullorEmpty(zipFileName)) {
			TeeApacheZipUtil.doZip(fileUploadTempDir, outputStream);
			TeeFileUtility.deleteAll(fileUploadTempDir);
		}
	}

	/**
	 * 根据选择文件List对象获取文件夹下的所有子文件夹和文件（包括当前对象）（共享文件柜）
	 * 
	 * @date 2014-2-19
	 * @author
	 * @param fileNetdisks
	 * @return 返回list（文件夹sid，文件夹路径、文件夹下的文件）
	 */
	public List<Map<String, Object>> getFileNetdiskList(List<TeeFileNetdisk> fileNetdisks) {
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		// 文件集合
		List<TeeFileNetdisk> fileList = new ArrayList<TeeFileNetdisk>();
		if (fileNetdisks != null && fileNetdisks.size() > 0) {
			String rootFolderName = "";
			int rootFolderSid = 0;
			TeeFileNetdisk rootFileNetdisk = fileNetdisks.get(0);
			if (rootFileNetdisk != null) {
				TeeFileNetdisk fileParent = rootFileNetdisk.getFileParent();
				if (fileParent != null) {
					rootFolderSid = fileParent.getSid();
					rootFolderName = fileParent.getFileName() + "/";
				} else {
					// rootFolderSid = rootFileNetdisk.getSid();
					// rootFolderName = rootFileNetdisk.getFileName()+ "/";
				}
			}

			for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
				if (fileNetdisk.getFiletype() == 0) {
					// 获取该文件夹的所有子文件夹（包括本文件夹）
					List<TeeFileNetdisk> folderList = fileNetdiskPersonDao.getSubFolderListBySidAscDao(TeeUtility.null2Empty(fileNetdisk.getFileFullPath()) + fileNetdisk.getSid() + "/",
							fileNetdisk.getSid());
					// 获取该文件夹id串
					String folderSidStr = this.getSidsByList(folderList);
					// 根据文件夹id串获取该文件夹的所有文件
					List<TeeFileNetdisk> filesList = fileNetdiskPersonDao.getSubFilesByParentSidsDao(folderSidStr);
					// 循环目录返回数据
					if (folderList != null && folderList.size() > 0) {
						Map<Integer, String> folderMap = this.getFolderPathByFolder(folderList);
						Map<Integer, List<TeeFileNetdisk>> fileNetdiskMap = this.getFolderMapByList(folderList, filesList);
						for (TeeFileNetdisk folderObj : folderList) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("sid", folderObj.getSid());
							map.put("folderPath", rootFolderName + folderMap.get(folderObj.getSid()));
							map.put("fileList", fileNetdiskMap.get(folderObj.getSid()));
							returnList.add(map);
						}
					}
				} else {
					fileList.add(fileNetdisk);
				}
			}

			if (rootFolderSid != 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("sid", rootFolderSid);
				map.put("folderPath", rootFolderName);
				map.put("fileList", fileList);
				returnList.add(map);
			}
		}
		return returnList;
	}

	/**
	 * 复制文件到其他目录（个人文件柜）
	 * 
	 * @date 2014-2-24
	 * @author
	 * @param person
	 * @param folderSid
	 *            目标id
	 * @param fileSids
	 *            拷贝目录id串
	 * @return
	 * @throws IOException
	 */
	public TeeJson copyFileToFolderService(TeePerson person, int folderSid, String fileSids) throws IOException {
		TeeJson json = new TeeJson();
		
		//获取目标文件夹
		TeeFileNetdisk fileNetdisk = fileNetdiskPersonDao.get(folderSid);
		
		//获取选中的文件
		List<TeeFileNetdisk> selectedFiles = fileNetdiskPersonDao.getFileBySidsDao(fileSids);
		
		//遍历选中的文件，判断目标文件夹是否在选中的文件夹的子级文件
		for(TeeFileNetdisk file:selectedFiles){
			if(file.getFiletype()==0 && fileNetdisk.getFileFullPath().contains("/"+file.getSid()+"/")){//文件夹
				throw new TeeOperationException("目标文件夹为源文件夹的子文件夹，无法进行复制");
			}
		}
		
		//遍历这三个文件
		TeeFileNetdisk newFile = null;
		for(TeeFileNetdisk file:selectedFiles){
			newFile = new TeeFileNetdisk();
			if(file.getAttachemnt()!=null){
				newFile.setAttachemnt(attachmentService.clone(file.getAttachemnt(), TeeAttachmentModelKeys.FILE_NET_DISK_PERSON, person));
			}
			newFile.setContent(file.getContent());
			newFile.setCreater(person);
			newFile.setCreateTime(new Date());
			newFile.setFileName(file.getFileName());
			newFile.setFileNetdiskType(file.getFileNetdiskType());
			newFile.setFileNo(file.getFileNo());
			newFile.setFileParent(fileNetdisk);
			newFile.setFiletype(file.getFiletype());
			
			
			fileNetdiskPersonDao.save(newFile);
			newFile.setFileFullPath(fileNetdisk.getFileFullPath()+newFile.getSid()+"/");
			fileNetdiskPersonDao.update(newFile);
			
			changeFullPathAndCopy(person,newFile,file);
		}
		
		
		json.setRtState(true);
		json.setRtMsg("文件目录获取成功!");
		return json;
	}
	
	private void changeFullPathAndCopy(TeePerson person,TeeFileNetdisk nFile,TeeFileNetdisk oFile){
		if(oFile.getFiletype()==0){//文件夹
			//获取当前文件夹的子级文件夹及文件
			List<TeeFileNetdisk> selectedFiles = fileNetdiskPersonDao.getFileByParentIdsDao(person,oFile.getSid()+"");
			TeeFileNetdisk newFile = null;
			for(TeeFileNetdisk file:selectedFiles){
				newFile = new TeeFileNetdisk();
				if(file.getAttachemnt()!=null){
					newFile.setAttachemnt(attachmentService.clone(file.getAttachemnt(), TeeAttachmentModelKeys.FILE_NET_DISK, person));
				}
				newFile.setContent(file.getContent());
				newFile.setCreater(person);
				newFile.setCreateTime(new Date());
				newFile.setFileName(file.getFileName());
				newFile.setFileNetdiskType(file.getFileNetdiskType());
				newFile.setFileNo(file.getFileNo());
				newFile.setFileParent(nFile);
				newFile.setFiletype(file.getFiletype());
				
				fileNetdiskPersonDao.save(newFile);
				newFile.setFileFullPath(nFile.getFileFullPath()+newFile.getSid()+"/");
				fileNetdiskPersonDao.update(newFile);
				
				changeFullPathAndCopy(person,newFile,file);
			}
		}
	}

	/**
	 * 重命名文件，判断srcName是否在fileNameList中，如存在着重命名，并返回
	 * 
	 * @date 2014-3-2
	 * @author
	 * @param fileNameList
	 * @param srcName
	 * @param isFile
	 * @return
	 */
	public void getFileReNameStr(StringBuffer fileNameBuffer, List<String> fileNameList, String srcName, boolean isFile) {
		if (TeeUtility.isNullorEmpty(srcName)) {
			srcName = "";
		}
		List<String> newList = fileNameList;
		String fileName = srcName;
		fileNameBuffer.delete(0, fileNameBuffer.length());
		fileNameBuffer.append(fileName);
		if (fileNameList != null && fileNameList.size() > 0) {
			for (int i = 0; i < fileNameList.size(); i++) {
				String nameStr = fileNameList.get(i);
				if (nameStr.equals(srcName)) {
					newList.remove(i);
					if (isFile) {
						String privName = TeeFileUtility.getFileNameNoExt(srcName) + "-复制";
						fileName = privName + TeeFileConst.PATH_POINT + TeeFileUtility.getFileExtName(srcName);
						fileNameBuffer.append("-复制");
					} else {
						fileName = srcName + "-复制";
					}
					getFileReNameStr(fileNameBuffer, newList, fileName, isFile);
				}
			}
		}
	}

	/**
	 * 获取模块文件使用大小
	 * 
	 * @date 2014-3-1
	 * @author
	 * @param person
	 * @param module
	 * @return
	 */
	public TeeJson getModuleFileSizeService(TeePerson person, String module) {
		TeeJson json = new TeeJson();
		String fileSize = "0";
		long fileLong = fileNetdiskPersonDao.getModuleFileSizeDao(person, module);
		fileSize = TeeApacheZipUtil.getDisckSize(fileLong);

		Map map = new HashMap();
		map.put("fileSize", fileSize);
		json.setRtData(map);
		json.setRtState(true);
		json.setRtMsg("文件目录获取成功!");
		return json;
	}
	
	/**
	 * 获取当前登录人信息
	 * @function: 
	 * @author: wyw
	 * @data: 2015年9月17日
	 * @param person
	 * @return TeeJson
	 */
	public TeeJson getFolderCapacity(TeePerson person) {
		TeeJson json = new TeeJson();
		person = (TeePerson) simpleDaoSupport.get(TeePerson.class, person.getUuid());
		Map map = new HashMap();
		map.put("folderCapacity", person.getFolderCapacity());
		json.setRtData(map);
		json.setRtState(true);
		json.setRtMsg("文件目录获取成功!");
		return json;
	}

	
	/**
	 * 获取手机端共享网盘
	 * @return
	 */
	public TeeJson getMobileShareFolderTree(HttpServletRequest request) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new  TeeJson();
		//判断是一级  二级  还是三级  （一级是说明是谁共享的文件夹    二级是文件夹列表     三级是文件列表）
		int type=TeeStringUtil.getInteger(request.getParameter("type"), 0);
		if(type==1){
			String hql = " select distinct(file.creater)  from TeeFileNetdisk file  where file.fileNetdiskType=1 and file.filetype=0 and ( exists (select 1 from file.fileUserPriv fileUserPriv where fileUserPriv.user.uuid = ? and fileUserPriv.privValue>0))";
		    List<TeePerson> personList =  simpleDaoSupport.executeQuery(hql, new Object[]{loginUser.getUuid()});
		    List<Map> data=new ArrayList<Map>();
		    Map  map=null;
		    if(personList!=null&&personList.size()>0){//获取人
		    	for (TeePerson p : personList) {
		    		map=new HashMap();
			    	map.put("userId",p.getUuid() );
			    	map.put("userName", p.getUserName());
			    	data.add(map);
				}	
		    }
		    
		    json.setRtData(data);
		}else if(type==2){//获取文件夹
			//用户主键
		    int userId=TeeStringUtil.getInteger(request.getParameter("userId"), 0);
		    String hql = " select file  from TeeFileNetdisk file  where fileNetdiskType=1 and filetype=0 and file.creater.uuid=?  and( exists (select 1 from file.fileUserPriv fileUserPriv where fileUserPriv.user.uuid = ? and fileUserPriv.privValue>0))";
			List<TeeFileNetdisk> folderList=simpleDaoSupport.executeQuery(hql,new Object[]{userId,loginUser.getUuid()});
		    List<Map> data=new ArrayList<Map>();
			Map  map=null;
			if(folderList!=null&&folderList.size()>0){//获取人
		    	for (TeeFileNetdisk folder : folderList) {
		    		map=new HashMap();
			    	map.put("fileId",folder.getSid());
			    	map.put("fileName",folder.getFileName());
			    	data.add(map);
				}	
			}
			json.setRtData(data);
		
		}else if(type==3){//获取文件   （名称  id  创建人  文件大小   附件id  创建时间 ）
			//文件夹主键
		    int folderId=TeeStringUtil.getInteger(request.getParameter("folderId"), 0);
		    String hql = " select file  from TeeFileNetdisk file  where fileNetdiskType=1 and filetype=1 and file.fileParent.sid=? ";
		    List<TeeFileNetdisk> fileList=simpleDaoSupport.executeQuery(hql,new Object[]{folderId});
		    List<Map> data=new ArrayList<Map>();
			Map  map=null;
			if(fileList!=null&&fileList.size()>0){//获取人
		    	for (TeeFileNetdisk file : fileList) {
		    		map=new HashMap();
			    	map.put("fileId",file.getSid());
			    	map.put("fileName",file.getFileName());
			    	if(file.getCreater()!=null){
			    		map.put("createUserName",file.getCreater().getUserName());
				    	map.put("createUserId",file.getCreater().getUuid());
			    	}
			    	if(file.getCreateTime()!=null){
			    		map.put("createTime", sdf.format(file.getCreateTime()));
			    	}
			    	if(file.getAttachemnt()!=null){
			    		map.put("attachId", file.getAttachemnt().getSid());
			    		map.put("attachName", file.getAttachemnt().getAttachmentName());
			    	    map.put("attachSize", file.getAttachemnt().getSize());
			    	}else{
			    		map.put("attachId", 0);
			    		map.put("attachName", "");
			    	    map.put("attachSize", 0);
			    	}
			    	data.add(map);
				}	
			}
		
			json.setRtData(data);
		}
		
		json.setRtState(true);
		return json;
	}
	

}
