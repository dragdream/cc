package com.tianee.oa.core.base.fileNetdisk.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileDeptPrivDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileNetdiskPersonDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileRolePrivDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileUserPrivDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeSaveFileToPersonDao;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeSaveFileToPersonService extends TeeBaseService {

	@Autowired
	private TeeFileNetdiskPersonDao fileNetdiskPersonDao;
	
	@Autowired
	private TeeSaveFileToPersonDao saveFileToPersonDao ;
	
	
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
	 * @function: 获取转存个人网盘目录树
	 * @author: wyw
	 * @data: 2014年9月14日
	 * @param requestMap
	 * @param loginPerson
	 * @return TeeJson
	 */
	public TeeJson getPersonFolderTree(Map requestMap, TeePerson loginPerson) {
		TeeJson json = new TeeJson();
		List<TeeZTreeModel> fileFolderTree = new ArrayList<TeeZTreeModel>();
		
		List<TeeFileNetdisk> personRootFolder = fileNetdiskPersonDao.getPersonRootFolderDao(loginPerson);
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
			
			List<TeeFileNetdisk> folderList = saveFileToPersonDao.getPersonFolderTreeDao(fileFullPathBuffer.toString(), sidBuffer.toString(), loginPerson);
			
			if(folderList != null && folderList.size()>0){
				for(TeeFileNetdisk fileNetdisk : folderList){
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
	 * @function: 转存至个人网盘
	 * @author: wyw
	 * @data: 2014年9月14日
	 * @param requestMap
	 * @param loginPerson
	 * @return TeeJson
	 */
	public TeeJson saveToPersonFolder(Map requestMap, TeePerson loginPerson) {
		TeeJson json = new TeeJson();
		int resultFlag = 0;
		String attachId = (String)requestMap.get("attachId");
		String folderSid = (String)requestMap.get("folderSid");
		if(!TeeUtility.isNullorEmpty(attachId) && !TeeUtility.isNullorEmpty(folderSid)){
			
			TeeFileNetdisk fileParent = fileNetdiskPersonDao.get(TeeStringUtil.getInteger(folderSid, 0));
			if(fileParent != null){
				List<TeeAttachment> attachments = fileNetdiskPersonDao.getAttachmentsBySids(attachId);
				if (attachments != null && attachments.size() > 0) {
					for (TeeAttachment attachment : attachments) {
						TeeFileNetdisk fileNetdisk = new TeeFileNetdisk();
						fileNetdisk.setFiletype(1);
						// 复制附件
						TeeAttachment newAttachment = attachmentService.clone(attachment, TeeAttachmentModelKeys.FILE_NET_DISK_PERSON, loginPerson);
						newAttachment.setModelId("0");
						fileNetdisk.setAttachemnt(newAttachment);
						
						fileNetdisk.setFileNo(0);
						fileNetdisk.setFileName(attachment.getFileName());
						if (!TeeUtility.isNullorEmpty(fileParent.getFileFullPath())) {
							fileNetdisk.setFileFullPath(fileParent.getFileFullPath() + folderSid + "/");
						} else {
							fileNetdisk.setFileFullPath(fileParent + "/");
						}
						fileNetdisk.setCreater(loginPerson);
						fileNetdisk.setFileParent(fileParent);
						fileNetdisk.setFileNetdiskType(1);
						fileNetdisk.setCreateTime(new Date());
						fileNetdiskPersonDao.save(fileNetdisk);
						
						if (!TeeUtility.isNullorEmpty(fileParent.getFileFullPath())) {
							fileNetdisk.setFileFullPath(fileParent.getFileFullPath() + fileNetdisk.getSid() + "/");
						} else {
							fileNetdisk.setFileFullPath(fileParent + "/");
						}
						
						fileNetdiskPersonDao.update(fileNetdisk);
						resultFlag = 1;
					}
				}
			}
		}
		Map map = new HashMap();
		map.put("resultFlag", resultFlag);
		json.setRtData(map);
		json.setRtState(true);
		json.setRtMsg("文件目录获取成功!");
		return json;
	}
	
	
	
	/**
	 * @function: 获取文件信息
	 * @author: wyw
	 * @data: 2014年9月15日
	 * @param requestMap
	 * @param loginPerson
	 * @return TeeJson
	 */
	public TeeJson getAttachmentInfo(Map requestMap, TeePerson loginPerson) {
		TeeJson json = new TeeJson();
		String attachId = (String)requestMap.get("attachId");
		
		String fileName = "";
		if(!TeeUtility.isNullorEmpty(attachId)){
			TeeAttachment attachments = attachmentDao.get(TeeStringUtil.getInteger(attachId, 0));
			if(attachments != null){
				fileName = attachments.getFileName();
			}
		}
		Map map = new HashMap();
		map.put("fileName", fileName);
		json.setRtData(map);
		json.setRtState(true);
		json.setRtMsg("文件获取成功!");
		return json;
	}
	
	
	
	
	
	
	

}
