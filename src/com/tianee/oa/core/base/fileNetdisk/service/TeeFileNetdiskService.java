package com.tianee.oa.core.base.fileNetdisk.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.bean.TeeAttachmentSpace;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileDeptPriv;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileOptRecord;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFilePingFei;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileRolePriv;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileUserPriv;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileDeptPrivDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileNetdiskDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileNetdiskReaderDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFilePingFeiDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileRolePrivDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileUserPrivDao;
import com.tianee.oa.core.base.fileNetdisk.model.TeeFileNetdiskModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.general.bean.TeeLuceneTask;
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeFileConst;
import com.tianee.oa.subsys.project.bean.TeeProject;
import com.tianee.oa.subsys.project.bean.TeeProjectFile;
import com.tianee.oa.subsys.project.bean.TeeTask;
import com.tianee.oa.subsys.project.bean.TeeTaskProjectTypeFile;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.thirdparty.newcapec.supwisdom.LoginUser;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.interceptor.TeeServiceLoggingInterceptor;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfo;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Service
public class TeeFileNetdiskService extends TeeBaseService {
	@Autowired
	@Qualifier("fileNetdiskDao")
	private TeeFileNetdiskDao fileNetdiskDao;
	@Autowired
	private TeeAttachmentDao attachmentDao;

	@Autowired
	private TeeFileNetdiskReaderDao readerDao;

	@Autowired
	private TeeFileUserPrivDao fileUserPrivDao;
	@Autowired
	private TeeFileDeptPrivDao fileDeptPrivDao;
	@Autowired
	private TeeFileRolePrivDao fileRolePrivDao;

	@Autowired
	private TeeAttachmentService attachmentService;

	@Autowired
	private TeeSmsManager smsManager;
	
	@Autowired
	private  TeePersonDao personDao;
	
	@Autowired
	private TeeFilePingFeiDao teeFilePingFeiDao;
	
	@Autowired
	TeeSimpleDaoSupport simpleDaoSupport ;
	
	/**
	 * 新建文件网盘（一级）
	 * 
	 * @date 2014-1-4
	 * @author
	 * @param person
	 * @param model
	 * @throws Exception
	 */
	public TeeJson addFileNetdiskService(TeePerson person,
			TeeFileNetdiskModel model) throws Exception {
		TeeJson json = new TeeJson();
		TeeFileNetdisk fileNetdisk = new TeeFileNetdisk();
		fileNetdisk.setFileNo(model.getFileNo());
		fileNetdisk.setFileName(model.getFileName());
		fileNetdisk.setCreater(person);
		fileNetdisk.setCreateTime(new Date());
		fileNetdiskDao.save(fileNetdisk);

		fileNetdisk.setFileFullPath("/" + fileNetdisk.getSid() + "/");

		json.setRtState(true);
		json.setRtMsg("文件网盘新建成功!");
		return json;
	}
	
	

	/**
	 * 更新目录（一级）
	 * 
	 * @date 2014-1-5
	 * @author
	 * @param model
	 * @throws Exception
	 */
	public TeeJson updateFileNetdiskService(TeeFileNetdiskModel model)
			throws Exception {
		TeeJson json = new TeeJson();
		try {
			TeeFileNetdisk fileNetdisk = fileNetdiskDao
					.getFileNetdiskByIdDao(model.getSid());
			if (fileNetdisk != null) {
				fileNetdisk.setFileNo(model.getFileNo());
				fileNetdisk.setFileName(model.getFileName());
				fileNetdiskDao.update(fileNetdisk);
			}
			json.setRtState(true);
			json.setRtMsg("文件网盘更新成功!");
			return json;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 获取文件网盘列表,无权限（一级）
	 * 
	 * @date 2014-1-5
	 * @author
	 * @return
	 */
	public TeeJson getFileNetdiskListServbice() {
		TeeJson json = new TeeJson();
		List<TeeFileNetdiskModel> list = new ArrayList<TeeFileNetdiskModel>();
		List<TeeFileNetdisk> fileNetdisks = fileNetdiskDao
				.getFileNetdiskListDao();
		for (int i = 0; i < fileNetdisks.size(); i++) {
			TeeFileNetdiskModel model = new TeeFileNetdiskModel();
			BeanUtils.copyProperties(fileNetdisks.get(i), model);
			list.add(model);
		}

		json.setRtData(list);
		json.setRtState(true);
		return json;
	}

	/**
	 * 获取文件网盘列表,有权限（一级）
	 * 
	 * @date 2014年3月30日
	 * @author
	 * @param person
	 * @param menuSid
	 * @return
	 */
	public TeeJson getRootPrivFolderList(TeePerson person, int menuSid) {
		TeeJson json = new TeeJson();
		List<TeeFileNetdiskModel> list = new ArrayList<TeeFileNetdiskModel>();
		List<TeeFileNetdisk> fileNetdisks = fileNetdiskDao
				.getRootPrivFolderDao(person, menuSid);
		for (int i = 0; i < fileNetdisks.size(); i++) {
			TeeFileNetdiskModel model = new TeeFileNetdiskModel();
			BeanUtils.copyProperties(fileNetdisks.get(i), model);
			list.add(model);
		}

		json.setRtData(list);
		json.setRtState(true);
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
	public TeeJson getFileNetdiskByIdServbice(Map requestMap) {
		int sid = TeeStringUtil.getInteger(requestMap.get("sid"), 0);
		TeePerson person = (TeePerson) requestMap.get(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		TeeFileNetdisk fileNetdisk = fileNetdiskDao.getFileNetdiskByIdDao(sid);
		TeeFileNetdiskModel model = new TeeFileNetdiskModel();
		if (fileNetdisk != null && sid > 0) {
			BeanUtils.copyProperties(fileNetdisk, model);

			TeeAttachment attaches = fileNetdisk.getAttachemnt();
			if (attaches != null) {
				TeeAttachmentModel attachmentModel = new TeeAttachmentModel();
				BeanUtils.copyProperties(attaches, attachmentModel);

				attachmentModel.setUserId(attaches.getUser().getUuid() + "");
				attachmentModel.setUserName(attaches.getUser().getUserName());
				attachmentModel.setPriv(1 + 2);// 一共五个权限好像
				attachmentModel.setSizeDesc(TeeFileUtility.getFileSizeDesc(attaches.getSize()));								// 1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment

				model.setAttacheModels(attachmentModel);
				model.setFileSize(TeeApacheZipUtil.getDisckSize(attachmentModel
						.getSize()));
				model.setFileTypeExt(attachmentModel.getExt());
				model.setAttachSid(attachmentModel.getSid());
                
			} else if (fileNetdisk.getFiletype() == 0) {
				model.setFileTypeExt("0");
			}
			model.setContent(TeeUtility.null2Empty(fileNetdisk.getContent()));
			boolean isSignReadFlag = readerDao.isSignReadDao(
					fileNetdisk.getSid(), person.getUuid());
			int isSignRead = 0;
			if (isSignReadFlag) {
				isSignRead = 1;
			}
			model.setIsSignRead(isSignRead);
			model.setCreaterId(fileNetdisk.getCreater().getUuid());
			List<TeeFilePingFei> find2 = teeFilePingFeiDao.find("from TeeFilePingFei where fileId=? and user.uuid=?", new Object[]{fileNetdisk.getSid(),person.getUuid()});
			if(find2!=null && find2.size()>0){
				TeeFilePingFei fei = find2.get(0);
				model.setPicCount(fei.getPicCount());
			}
			json.setRtData(model);
			json.setRtState(true);
		} else {
			json.setRtState(false);
			json.setRtMsg("未找到相关数据！");
		}
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
	public TeeJson uploadNetdiskFileServbice(String sidStr, String attachSids,
			String rootFolderPriv,String isRemind,TeePerson person,int taskId) {
		// 给有权限的人员发送消息
		String personIds = "";
		if (TeeStringUtil.getInteger(sidStr, 0) != 0) {
			int sid = TeeStringUtil.getInteger(sidStr, 0);
			// 获取满足条件的人员Ids
			String userIds = "";
			String hql = "from TeeFileUserPriv up where up.fileNetdisk.sid="
					+ sid + " and privValue > 0";
			List<TeeFileUserPriv> list1 = simpleDaoSupport.executeQuery(hql,null);
			for (TeeFileUserPriv teeFileUserPriv : list1) {
				if(teeFileUserPriv.getUser()!=null){
					userIds = userIds + teeFileUserPriv.getUser().getUuid() + ",";
				}
			}
			if (userIds.endsWith(",")) {
				userIds = userIds.substring(0, userIds.length() - 1);
			}
			if(("").equals(userIds)){
				userIds = "(" + 0 + ")";
			}else{
				userIds = "(" + userIds + ")";
			}
			
			// 获取满足部门的Ids
			String deptIds = "";
			String hql2 = "from TeeFileDeptPriv dp where dp.fileNetdisk.sid="
					+ sid + " and privValue > 0";
			List<TeeFileDeptPriv> list2 = simpleDaoSupport.executeQuery(hql2,
					null);
			for (TeeFileDeptPriv teeFileDeptPriv : list2) {
				if(teeFileDeptPriv.getDept()!=null){
					deptIds = deptIds + teeFileDeptPriv.getDept().getUuid() + ",";
				}
			}
			if (deptIds.endsWith(",")) {
				deptIds = deptIds.substring(0, deptIds.length() - 1);
			}
			if(("").equals(deptIds)){
				deptIds = "(" + 0 + ")";
			}else{
				deptIds = "(" + deptIds + ")";
			}
			
			// 获取满足角色的Ids
			String roleIds = "";
			String hql3 = "from TeeFileRolePriv rp where rp.fileNetdisk.sid="
					+ sid + " and privValue > 0";
			List<TeeFileRolePriv> list3 = simpleDaoSupport.executeQuery(hql3,
					null);
			for (TeeFileRolePriv teeFileRolePriv : list3) {
				if(teeFileRolePriv.getUserRole()!=null){
					roleIds = roleIds + teeFileRolePriv.getUserRole().getUuid()
							+ ",";
				}
			}
			if (roleIds.endsWith(",")) {
				roleIds = roleIds.substring(0, roleIds.length() - 1);
			}
			if(("").equals(roleIds)){
				roleIds = "(" + 0 + ")";
			}else{
				roleIds = "(" + roleIds + ")";
			}
			
			// 获取满足条件的所有的人员
			String h = " from TeePerson p  where p.uuid in " + userIds
					+ " or p.dept.uuid in " + deptIds
					+ " or p.userRole.uuid in " + roleIds;
			
			List<TeePerson> personList = simpleDaoSupport.executeQuery(h, null);
			for (TeePerson teePerson : personList) {
				personIds = personIds + teePerson.getUuid() + ",";
			}
			if (personIds.endsWith(",")) {
				personIds = personIds.substring(0, personIds.length() - 1);
			}
		}

		TeeSysLog sysLog = TeeSysLog.newInstance();
		sysLog.setType("024B");
		StringBuffer sb = new StringBuffer("上传文件 [");
		TeeJson json = new TeeJson();
		if (!TeeUtility.isNullorEmpty(attachSids)) {
			TeeFileNetdisk dbFileNetdisk = fileNetdiskDao.get(TeeStringUtil
					.getInteger(sidStr, 0));
			if (dbFileNetdisk != null) {
				/* 获取文件父级id获取同一级的所有文件夹、文件 */
				List<TeeFileNetdisk> fileNetdisks = fileNetdiskDao
						.getFileByParentIdsDao(dbFileNetdisk.getSid());
				List<String> fileNameList = this.getNameToList(fileNetdisks);

				List<TeeAttachment> attachments = fileNetdiskDao
						.getAttachmentsBySids(attachSids);

				if (attachments != null && attachments.size() > 0) {
					for (TeeAttachment attachement : attachments) {
						sb.append(attachement.getFileName() + ",");
						attachement.setModelId("0");
						TeeFileNetdisk fileNetdisk = new TeeFileNetdisk();
						// fileNetdisk.setFileName(attachement.getFileName());
						fileNetdisk.setFiletype(1);
						fileNetdisk.setCreater(person);
						fileNetdisk.setCreateTime(attachement.getCreateTime()
								.getTime());
						fileNetdisk.setAttachemnt(attachement);
						fileNetdisk.setFileFullPath(attachement
								.getAttachmentPath());
						fileNetdisk.setFileParent(dbFileNetdisk);

						StringBuffer fileNameBuffer = new StringBuffer(
								attachement.getFileName());
						this.getFileReNameStr(fileNameBuffer, fileNameList,
								attachement.getFileName(), true);
						fileNetdisk.setFileName(fileNameBuffer.toString());
						fileNetdiskDao.save(fileNetdisk);

						if(taskId>0){
							Map map = simpleDaoSupport.executeNativeUnique("select FILE_FULL_PATH as fileFullPath,SID as sid from FILE_NETDISK where SID = (select FILE_NETDISK_ID as fileId from task_projectype_file where TASK_ID=?)", new Object[]{taskId});
							if(map!=null){
								String parentPath=TeeStringUtil.getString(map.get("fileFullPath"));
								int parentId=TeeStringUtil.getInteger(map.get("sid"), 0);
								int fileId=fileNetdisk.getSid();
								fileNetdisk.setFileFullPath(parentPath+fileId+"/");
								TeeFileNetdisk fileObject=(TeeFileNetdisk)simpleDaoSupport.get(TeeFileNetdisk.class,parentId);
								fileNetdisk.setFileParent(fileObject);
							}
						}else{
							if (!TeeUtility.isNullorEmpty(dbFileNetdisk
									.getFileFullPath())) {
								fileNetdisk.setFileFullPath(dbFileNetdisk
										.getFileFullPath()
										+ fileNetdisk.getSid()
										+ "/");
							} else {
								fileNetdisk.setFileFullPath("/"
										+ fileNetdisk.getSid() + "/");
							}
							Map map2 = simpleDaoSupport.executeNativeUnique("select project_id as projectId,task_id as taskId from task_projectype_file where file_netdisk_id=?", new Object[]{fileNetdisk.getFileParent().getSid()});
							TeeProjectFile projectFile=new TeeProjectFile();
							projectFile.setCreater(person);
						    projectFile.setCreateTime(new Date());
						    projectFile.setFile(fileNetdisk);
						    if(map2!=null){
						    	projectFile.setProjectId(TeeStringUtil.getString(map2.get("projectId")));
						    	projectFile.setTaskId(TeeStringUtil.getInteger(map2.get("taskId"), 0));
						    	TeeProject p=(TeeProject)simpleDaoSupport.get(TeeProject.class,TeeStringUtil.getString(map2.get("projectId")));
						    	if(p!=null && p.getStatus()==3){
						    		simpleDaoSupport.save(projectFile);
						    	}
						    }
						}
						fileNetdiskDao.update(fileNetdisk);
						 json.setRtData(fileNetdisk.getSid());
						if("1".equals(isRemind)){
							//进行消息提醒
							if (!TeeUtility.isNullorEmpty(personIds)) {
								Map requestData = new HashMap();
								requestData.put("content", person.getUserName() + "上传文件：["+attachement.getFileName()+"]"+" 至文件夹 [" + dbFileNetdisk.getFileName() + "]");
								requestData.put("userListIds", personIds);
								requestData.put("moduleNo", "024");
								requestData.put(
										"remindUrl",
										"/system/core/base/fileNetdisk/fileManage/showContent.jsp?sid="
												+ fileNetdisk.getSid()+"&&folderSid="+sidStr+"&&rootFolderPriv="+rootFolderPriv);
								//手机端事务提醒
								requestData.put(
										"remindUrl1",
										"/system/mobile/phone/fileNetdisk/public/viewInfo.jsp?sid="
												+ fileNetdisk.getSid()+"&&folderSid="+sidStr+"&&rootFolderPriv="+rootFolderPriv);
								smsManager.sendSms(requestData, person);
							}	
						}
						
						
						// 创建全文检索
						// System.out.println(fileNetdisk);
						String fid = "";
						String ffid = "";
						String[] ljStr = fileNetdisk.getFileFullPath().split(
								"/");
						// System.out.println(fileNetdisk.getFileFullPath());
						fid = ljStr[1];
						ffid = ljStr[2];
						// System.out.println(ffid);
						// System.out.println(fid);
						TeeFileNetdisk tf = (TeeFileNetdisk) simpleDaoSupport
								.get(TeeFileNetdisk.class,
										Integer.parseInt(fid));
						// System.out.println(tf.getAutoIndex());
						if (tf.getAutoIndex() == 1) {
							TeeLuceneTask luceneTask = new TeeLuceneTask();
							luceneTask.setModelId(String.valueOf(ffid));
							luceneTask.setModelNo("024");
							luceneTask.setOpType(1);
							simpleDaoSupport.save(luceneTask);
						}
						
						//往操作表中插入操作记录 数据
						TeeFileOptRecord record=new TeeFileOptRecord();
						record.setCreateTime(Calendar.getInstance());
						record.setFileId(fileNetdisk.getSid());
						record.setFileName(fileNetdisk.getFileName());
						record.setIp(TeeRequestInfoContext.getRequestInfo().getIpAddress());
						record.setOptContent(person.getUserName()+"在["+getFullPathName(dbFileNetdisk.getSid())+"]下上传了文件["+fileNetdisk.getFileName()+"]");
						record.setOptType(1);//新建文件夹
						record.setUserId(person.getUuid());
						record.setUserName(person.getUserName());
						
						simpleDaoSupport.save(record);
						
					}
					sb.append("]");
				}

				sb.append(" 至文件夹 [" + dbFileNetdisk.getFileName() + "]");
			}
		}

		sysLog.setRemark(sb.toString());
		TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);

		
       
		json.setRtState(true);
		json.setRtMsg("附件上传成功！");
		return json;
	}

	/**
	 * 获取文件/文件夹通用列表（二级）
	 * 
	 * @param dm
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getFileNetdiskPage(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String folderSid = request.getParameter("folderSid");
		String uuid = request.getParameter("uuid");
		int projectManageId=0;
		if(uuid!=null && !"".equals(uuid)){
			TeeProject project = (TeeProject)simpleDaoSupport.get(TeeProject.class,uuid);
		    if(project!=null){
		    	projectManageId = project.getProjectManageId();
		    }
		}
		dataGridJson.setTotal(fileNetdiskDao.getCountByFolderSid(folderSid,
				person,projectManageId));
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置

		List<TeeFileNetdisk> fileNetdisks = fileNetdiskDao.getFileNetdisksPage(
				firstIndex, dm.getRows(), dm,
				TeeStringUtil.getInteger(folderSid, 0), person,projectManageId);

		List<TeeFileNetdiskModel> models = new ArrayList<TeeFileNetdiskModel>();

		if (fileNetdisks != null && fileNetdisks.size() > 0) {
			for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
				TeeFileNetdiskModel model = new TeeFileNetdiskModel();
				BeanUtils.copyProperties(fileNetdisk, model);
				/*model.setCreateTimeStr(TeeUtility.getDateTimeStr(fileNetdisk
						.getCreateTime()));*/
				model.setCreateTimeStr(sdf.format(fileNetdisk
						.getCreateTime()));
				TeeAttachment attaches = fileNetdisk.getAttachemnt();
				if (attaches != null) {
					TeeAttachmentModel attachmentModel = new TeeAttachmentModel();
					BeanUtils.copyProperties(attaches, attachmentModel);
					model.setAttacheModels(attachmentModel);
					model.setFileSize(TeeApacheZipUtil
							.getDisckSize(attachmentModel.getSize()));
					model.setFileTypeExt(attachmentModel.getExt());
					model.setAttachSid(attachmentModel.getSid());
				} else if (fileNetdisk.getFiletype() == 0) {
					model.setFileTypeExt("0");
				}
				if (fileNetdisk.getFiletype() == 1) {
					boolean isSignReadFlag = readerDao.isSignReadDao(
							fileNetdisk.getSid(), person.getUuid());
					int isSignRead = 0;
					if (isSignReadFlag) {
						isSignRead = 1;
					}
					model.setIsSignRead(isSignRead);
				}
				if (fileNetdisk.getCreater() != null) {
					model.setCreaterId(fileNetdisk.getCreater().getUuid());
					model.setCreaterStr(fileNetdisk.getCreater().getUserName());
				}
				models.add(model);
			}
		}

		dataGridJson.setRows(models);
		return dataGridJson;
	}
	
	
	
	/**
	 * 获取文件/文件夹通用列表（二级）
	 * 
	 * @param dm
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getFileNetdiskPage2(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String status = request.getParameter("status");
        String status2 = request.getParameter("status2");
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		//String folderSid = request.getParameter("folderSid");

		dataGridJson.setTotal(fileNetdiskDao.getCountByFolderSid2(person,status,status2));
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置

		List<TeeFileNetdisk> fileNetdisks = fileNetdiskDao.getFileNetdisksPage2(
				firstIndex, dm.getRows(), dm, person,status,status2);

		List<TeeFileNetdiskModel> models = new ArrayList<TeeFileNetdiskModel>();

		if (fileNetdisks != null && fileNetdisks.size() > 0) {
			for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
				TeeFileNetdiskModel model = new TeeFileNetdiskModel();
				BeanUtils.copyProperties(fileNetdisk, model);
				model.setParentFileSid(fileNetdisk.getFileParent().getSid());
				if("0".equals(status2)){
					int huiFuCount = fileNetdisk.getHuiFuCount();
					model.setCountNum(huiFuCount);
				}else if("1".equals(status2)){
					model.setCountNum(fileNetdisk.getReadCount());
				}else if("2".equals(status2)){
					model.setCountNum(fileNetdisk.getXiaZaiCount());
				}else if("3".equals(status2)){
					model.setCountNum(fileNetdisk.getPicCount());
				}else{
					
				}
				/*model.setCreateTimeStr(TeeUtility.getDateTimeStr(fileNetdisk
						.getCreateTime()));*/
				model.setCreateTimeStr(sdf.format(fileNetdisk
						.getCreateTime()));
				TeeAttachment attaches = fileNetdisk.getAttachemnt();
				if (attaches != null) {
					TeeAttachmentModel attachmentModel = new TeeAttachmentModel();
					BeanUtils.copyProperties(attaches, attachmentModel);
					model.setAttacheModels(attachmentModel);
					model.setFileSize(TeeApacheZipUtil
							.getDisckSize(attachmentModel.getSize()));
					model.setFileTypeExt(attachmentModel.getExt());
					model.setAttachSid(attachmentModel.getSid());
				} else if (fileNetdisk.getFiletype() == 0) {
					model.setFileTypeExt("0");
				}
				if (fileNetdisk.getFiletype() == 1) {
					boolean isSignReadFlag = readerDao.isSignReadDao(
							fileNetdisk.getSid(), person.getUuid());
					int isSignRead = 0;
					if (isSignReadFlag) {
						isSignRead = 1;
					}
					model.setIsSignRead(isSignRead);
				}
				if (fileNetdisk.getCreater() != null) {
					model.setCreaterId(fileNetdisk.getCreater().getUuid());
					model.setCreaterStr(fileNetdisk.getCreater().getUserName());
				}
				models.add(model);
			}
		}

		dataGridJson.setRows(models);
		return dataGridJson;
	}

	/**
	 * 新建文件夹（二级）
	 * 
	 * @date 2014-2-14
	 * @author
	 * @param sid
	 * @param folderName
	 * @param person
	 * @return
	 */
	public TeeJson newFileFolderById(int sid, String folderName,
			TeePerson person) {
		TeeSysLog sysLog = TeeSysLog.newInstance();
		sysLog.setType("024C");
		StringBuffer sb = new StringBuffer("新建文件夹 " + folderName);
		sysLog.setRemark(sb.toString());
		TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);

		TeeJson json = new TeeJson();
		int nodeId = 0;
		String nodeName = "";
		int nodeParentId = 0;

		TeeFileNetdisk dbFileNetdisk = fileNetdiskDao.get(sid);

		List<TeeFileUserPriv> userPrivs = null;
		List<TeeFileDeptPriv> deptPrivs = null;
		List<TeeFileRolePriv> rolePrivs = null;

		TeeFileUserPriv userPriv = null;
		TeeFileDeptPriv deptPriv = null;
		TeeFileRolePriv rolePriv = null;

		if (dbFileNetdisk != null) {
			TeeFileNetdisk fileNetdisk = new TeeFileNetdisk();
			fileNetdisk.setFileName(folderName);
			fileNetdisk.setCreater(person);
			fileNetdisk.setFiletype(0);
			fileNetdisk.setCreateTime(new Date());
			fileNetdisk.setFileParent(dbFileNetdisk);

			userPrivs = dbFileNetdisk.getFileUserPriv();
			deptPrivs = dbFileNetdisk.getFileDeptPriv();
			rolePrivs = dbFileNetdisk.getFileRolePriv();

			for (TeeFileUserPriv userPrivs0 : userPrivs) {
				userPriv = new TeeFileUserPriv();
				userPriv.setFileNetdisk(fileNetdisk);
				userPriv.setPrivValue(userPrivs0.getPrivValue());
				userPriv.setUser(userPrivs0.getUser());
				fileNetdisk.getFileUserPriv().add(userPriv);
			}
			for (TeeFileDeptPriv deptPrivs0 : deptPrivs) {
				deptPriv = new TeeFileDeptPriv();
				deptPriv.setFileNetdisk(fileNetdisk);
				deptPriv.setPrivValue(deptPrivs0.getPrivValue());
				deptPriv.setDept(deptPrivs0.getDept());
				fileNetdisk.getFileDeptPriv().add(deptPriv);
			}
			for (TeeFileRolePriv rolePrivs0 : rolePrivs) {
				rolePriv = new TeeFileRolePriv();
				rolePriv.setFileNetdisk(fileNetdisk);
				rolePriv.setPrivValue(rolePrivs0.getPrivValue());
				rolePriv.setUserRole(rolePrivs0.getUserRole());
				fileNetdisk.getFileRolePriv().add(rolePriv);
			}

			fileNetdiskDao.save(fileNetdisk);

			fileNetdisk.setFileFullPath(dbFileNetdisk.getFileFullPath()
					+ fileNetdisk.getSid() + "/");
			fileNetdiskDao.update(fileNetdisk);

			nodeId = fileNetdisk.getSid();
			nodeName = fileNetdisk.getFileName();
			//上级文件夹
			int sid2 = fileNetdisk.getFileParent().getSid();

            Map map = simpleDaoSupport.executeNativeUnique("select TASK_ID as taskId,PROJECT_ID as projectId from task_projectype_file where FILE_NETDISK_ID=?", new Object[]{sid2});
			if(map!=null){
				String projectId=TeeStringUtil.getString(map.get("projectId"));
				int taskIdS=TeeStringUtil.getInteger(map.get("taskId"), 0);
				TeeTask task=(TeeTask)simpleDaoSupport.get(TeeTask.class,taskIdS);
				TeeProject project=(TeeProject)simpleDaoSupport.get(TeeProject.class,projectId);
                if(project!=null && project.getStatus()==3){
                	if(task==null || (task!=null && task.getStatus()!=1)){
                		int taskId=addTask(nodeName,1,project.getUuid(),task==null?0:task.getSid(),0);
            			TeeTaskProjectTypeFile ty=new TeeTaskProjectTypeFile();
            			ty.setFileId(nodeId);
            			ty.setProjectId(project.getUuid());
            			ty.setTaskId(taskId);
            			simpleDaoSupport.save(ty);
                	}
                }
				
			}
			
			//往操作表中插入操作记录 数据
			TeeFileOptRecord record=new TeeFileOptRecord();
			record.setCreateTime(Calendar.getInstance());
			record.setFileId(fileNetdisk.getSid());
			record.setFileName(fileNetdisk.getFileName());
			record.setIp(TeeRequestInfoContext.getRequestInfo().getIpAddress());
			record.setOptContent(person.getUserName()+"在["+getFullPathName(dbFileNetdisk.getSid())+"]下创建了子文件夹["+fileNetdisk.getFileName()+"]");
			record.setOptType(1);//新建文件夹
			record.setUserId(person.getUuid());
			record.setUserName(person.getUserName());
			
			simpleDaoSupport.save(record);
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
	
	//新建任务
	public Integer addTask(String typeName,int orderNum,String proId,int shangJiTask,int qianZhiTask){
		TeeTask task=new TeeTask();
		TeeProject project=(TeeProject)simpleDaoSupport.get(TeeProject.class,proId);
		//任务名称
		task.setTaskName(typeName);
		//任务序号
		task.setTaskNo(orderNum+"");
		//项目
		task.setProject(project);
		//创建人
		task.setCreater(project.getProjectCreateId());
		//状态
		task.setStatus(2);
		//创建时间
		task.setCreateTime(new Date());
		//任务进度
		//上级任务
		TeeTask task2 = (TeeTask)simpleDaoSupport.get(TeeTask.class,shangJiTask);
		task.setHigherTask(task2);
		task.setTaskLevel("次要");
		//前置任务
		TeeTask task3 = (TeeTask)simpleDaoSupport.get(TeeTask.class,qianZhiTask);
		task.setPreTask(task3);
		task.setProgress(0);
		Serializable save=simpleDaoSupport.save(task);
		return TeeStringUtil.getInteger(TeeStringUtil.getString(save), 0);
	}
	//新建文件夹
	public Serializable newFileFolderById2(int sid, String folderName,
			TeePerson person) {
		TeeSysLog sysLog = TeeSysLog.newInstance();
		sysLog.setType("024C");
		StringBuffer sb = new StringBuffer("新建文件夹 " + folderName);
		sysLog.setRemark(sb.toString());
		TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);

		int nodeId = 0;
		String nodeName = "";
		int nodeParentId = 0;
		Serializable save=0;

		TeeFileNetdisk dbFileNetdisk = fileNetdiskDao.get(sid);

		List<TeeFileUserPriv> userPrivs = null;
		List<TeeFileDeptPriv> deptPrivs = null;
		List<TeeFileRolePriv> rolePrivs = null;

		TeeFileUserPriv userPriv = null;
		TeeFileDeptPriv deptPriv = null;
		TeeFileRolePriv rolePriv = null;

		if (dbFileNetdisk != null) {
			TeeFileNetdisk fileNetdisk = new TeeFileNetdisk();
			fileNetdisk.setFileName(folderName);
			fileNetdisk.setCreater(person);
			fileNetdisk.setFiletype(0);
			fileNetdisk.setCreateTime(new Date());
			fileNetdisk.setFileParent(dbFileNetdisk);

			userPrivs = dbFileNetdisk.getFileUserPriv();
			deptPrivs = dbFileNetdisk.getFileDeptPriv();
			rolePrivs = dbFileNetdisk.getFileRolePriv();

			for (TeeFileUserPriv userPrivs0 : userPrivs) {
				userPriv = new TeeFileUserPriv();
				userPriv.setFileNetdisk(fileNetdisk);
				userPriv.setPrivValue(userPrivs0.getPrivValue());
				userPriv.setUser(userPrivs0.getUser());
				fileNetdisk.getFileUserPriv().add(userPriv);
			}
			for (TeeFileDeptPriv deptPrivs0 : deptPrivs) {
				deptPriv = new TeeFileDeptPriv();
				deptPriv.setFileNetdisk(fileNetdisk);
				deptPriv.setPrivValue(deptPrivs0.getPrivValue());
				deptPriv.setDept(deptPrivs0.getDept());
				fileNetdisk.getFileDeptPriv().add(deptPriv);
			}
			for (TeeFileRolePriv rolePrivs0 : rolePrivs) {
				rolePriv = new TeeFileRolePriv();
				rolePriv.setFileNetdisk(fileNetdisk);
				rolePriv.setPrivValue(rolePrivs0.getPrivValue());
				rolePriv.setUserRole(rolePrivs0.getUserRole());
				fileNetdisk.getFileRolePriv().add(rolePriv);
			}

			save = fileNetdiskDao.save(fileNetdisk);

			fileNetdisk.setFileFullPath(dbFileNetdisk.getFileFullPath()
					+ fileNetdisk.getSid() + "/");
			fileNetdiskDao.update(fileNetdisk);

			nodeId = fileNetdisk.getSid();
			nodeName = fileNetdisk.getFileName();
			
			
			
			//往操作表中插入操作记录 数据
			TeeFileOptRecord record=new TeeFileOptRecord();
			record.setCreateTime(Calendar.getInstance());
			record.setFileId(fileNetdisk.getSid());
			record.setFileName(fileNetdisk.getFileName());
			record.setIp(TeeRequestInfoContext.getRequestInfo().getIpAddress());
			record.setOptContent(person.getUserName()+"在["+getFullPathName(dbFileNetdisk.getSid())+"]下创建了子文件夹["+fileNetdisk.getFileName()+"]");
			record.setOptType(1);//新建文件夹
			record.setUserId(person.getUuid());
			record.setUserName(person.getUserName());
			
			simpleDaoSupport.save(record);
		}
		return save;
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
        
		
		TeeFileNetdisk dbFileNetdisk = fileNetdiskDao.get(sid);
		String fileOldName=dbFileNetdisk.getFileName();
		if (dbFileNetdisk != null) {
			TeeFileNetdisk parentFileNetdisk = dbFileNetdisk.getFileParent();
			int parentSid = 0;
			if (parentFileNetdisk != null) {
				parentSid = parentFileNetdisk.getSid();
			}
			/* 获取文件父级id获取同一级的所有文件夹、文件 */
			List<TeeFileNetdisk> fileNetdisks = fileNetdiskDao
					.getFileByParentIdsDao(parentSid);
			List<String> fileNameList = this.getNameToList(fileNetdisks);
			StringBuffer fileNameBuffer = new StringBuffer(folderName);
			this.getFileReNameStr(fileNameBuffer, fileNameList, folderName,
					false);
			folderName = fileNameBuffer.toString();

			dbFileNetdisk.setFileName(folderName);
			fileNetdiskDao.update(dbFileNetdisk);
			nodeId = dbFileNetdisk.getSid();
			nodeName = dbFileNetdisk.getFileName();
			
			Map map = simpleDaoSupport.executeNativeUnique("select TASK_ID as taskId from task_projectype_file where FILE_NETDISK_ID=?", new Object[]{dbFileNetdisk.getSid()});
			if(map!=null){
				int taskId=TeeStringUtil.getInteger(map.get("taskId"),0);
				simpleDaoSupport.executeNativeUpdate("update project_task set task_name=? where SID=? and status!=1", new Object[]{dbFileNetdisk.getFileName(),taskId});
			}
			
			
			//往操作表中插入操作记录 数据
			TeeFileOptRecord record=new TeeFileOptRecord();
			record.setCreateTime(Calendar.getInstance());
			record.setFileId(dbFileNetdisk.getSid());
			record.setFileName(fileOldName);
			record.setIp(TeeRequestInfoContext.getRequestInfo().getIpAddress());
			if(parentSid!=0){
				record.setOptContent(TeeRequestInfoContext.getRequestInfo().getUserName()+"将["+getFullPathName(parentSid)+"]下的文件夹["+fileOldName+"]重命名为["+dbFileNetdisk.getFileName()+"]");
			}else{
				record.setOptContent(TeeRequestInfoContext.getRequestInfo().getUserName()+"将文件夹["+fileOldName+"]重命名为["+dbFileNetdisk.getFileName()+"]");
			}
			
			record.setOptType(3);//下载文件/文件夹
			record.setUserId(TeeRequestInfoContext.getRequestInfo().getUserSid());
			record.setUserName(TeeRequestInfoContext.getRequestInfo().getUserName());
			
			simpleDaoSupport.save(record);
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
	public TeeJson reNameFileByIdService(int sid, String fileName,
			TeePerson person) {
		TeeJson json = new TeeJson();
		TeeFileNetdisk dbFileNetdisk = fileNetdiskDao.get(sid);
		String fileOldName=dbFileNetdisk.getFileName();
		if (dbFileNetdisk != null) {

			TeeAttachment attachment = dbFileNetdisk.getAttachemnt();
			if (attachment != null) {
				TeeFileNetdisk parentFileNetdisk = dbFileNetdisk
						.getFileParent();
				int parentSid = 0;
				if (parentFileNetdisk != null) {
					parentSid = parentFileNetdisk.getSid();
				}
				/* 获取文件父级id获取同一级的所有文件夹、文件 */
				List<TeeFileNetdisk> fileNetdisks = fileNetdiskDao
						.getFileByParentIdsDao(parentSid);
				List<String> fileNameList = this.getNameToList(fileNetdisks);
				StringBuffer fileNameBuffer = new StringBuffer(fileName);
				this.getFileReNameStr(fileNameBuffer, fileNameList, fileName,
						true);
				fileName = fileNameBuffer.toString();

				attachment.setFileName(fileName);
				attachmentDao.update(attachment);
				dbFileNetdisk.setFileName(fileName);
				fileNetdiskDao.update(dbFileNetdisk);
			}
			
			//往操作表中插入操作记录 数据
			TeeFileOptRecord record=new TeeFileOptRecord();
			record.setCreateTime(Calendar.getInstance());
			record.setFileId(dbFileNetdisk.getSid());
			record.setFileName(fileOldName);
			record.setIp(TeeRequestInfoContext.getRequestInfo().getIpAddress());
			if(dbFileNetdisk.getFileParent()!=null){
				record.setOptContent(TeeRequestInfoContext.getRequestInfo().getUserName()+"将["+getFullPathName(dbFileNetdisk.getFileParent().getSid())+"]下的文件["+fileOldName+"]重命名为["+dbFileNetdisk.getFileName()+"]");
			}else{
				record.setOptContent(TeeRequestInfoContext.getRequestInfo().getUserName()+"将文件["+fileOldName+"]重命名为["+dbFileNetdisk.getFileName()+"]");
			}
			
			record.setOptType(3);//重命名文件
			record.setUserId(TeeRequestInfoContext.getRequestInfo().getUserSid());
			record.setUserName(TeeRequestInfoContext.getRequestInfo().getUserName());
			
			simpleDaoSupport.save(record);
			
			
		}
		json.setRtState(true);
		json.setRtMsg("重命名文件成功!");
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
	public TeeJson getFolderPathBySid(int sid) {
		TeeJson json = new TeeJson();
		int fileParentId = 0;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		TeeFileNetdisk dbFileNetdisk = fileNetdiskDao.get(sid);
		if (dbFileNetdisk != null) {
			TeeFileNetdisk paraentFileNetdisk = dbFileNetdisk.getFileParent();
			if (paraentFileNetdisk != null) {// 子目录
				fileParentId = dbFileNetdisk.getFileParent().getSid();

				if (!TeeUtility.isNullorEmpty(dbFileNetdisk.getFileFullPath())) {
					List<TeeFileNetdisk> fileNetdisks = fileNetdiskDao
							.getFileNetdiskBySids(dbFileNetdisk
									.getFileFullPath()
									+ dbFileNetdisk.getSid()
									+ "/");
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
	 * 删除目录及文件
	 * 
	 * @date 2014-2-15
	 * @author
	 * @param sids
	 * @return
	 */
	public TeeJson deleteFileBySid(String sids) {
		TeeSysLog sysLog = TeeSysLog.newInstance();
		sysLog.setType("024D");
		StringBuffer sb = new StringBuffer();
		sb.append("删除文件 ");

		TeeJson json = new TeeJson();
		List<TeeFileNetdisk> fileNetdisks = fileNetdiskDao
				.getFileBySidsDao(sids);
		if (fileNetdisks != null && fileNetdisks.size() > 0) {
			for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
				sb.append(fileNetdisk.getFileName() + ",");
				String fileFullPath = fileNetdisk.getFileFullPath();
				// if (!TeeUtility.isNullorEmpty(fileFullPath)) {
				// fileNetdiskDao.deleteFileBySidDao(fileFullPath,
				// fileNetdisk.getSid());
				// } else {
				// fileNetdiskDao.deleteFileBySidDao(fileNetdisk.getSid() + "/",
				// fileNetdisk.getSid());
				// }
				fileNetdiskDao.deleteFileBySidDao(fileFullPath,
						fileNetdisk.getSid());
				readerDao.deleteInfoByFileIdDao(String.valueOf(fileNetdisk
						.getSid()));
				
				
				//往操作表中插入操作记录 数据
				TeeFileOptRecord record=new TeeFileOptRecord();
				record.setCreateTime(Calendar.getInstance());
				record.setFileId(fileNetdisk.getSid());
				record.setFileName(fileNetdisk.getFileName());
				record.setIp(TeeRequestInfoContext.getRequestInfo().getIpAddress());
				if(fileNetdisk.getFileParent()!=null){
					if(fileNetdisk.getFiletype()==0){//文件夹
						record.setOptContent(TeeRequestInfoContext.getRequestInfo().getUserName()+"删除了["+getFullPathName(fileNetdisk.getFileParent().getSid())+"]下的文件夹["+fileNetdisk.getFileName()+"]");
					}else{//文件
						record.setOptContent(TeeRequestInfoContext.getRequestInfo().getUserName()+"删除了["+getFullPathName(fileNetdisk.getFileParent().getSid())+"]下的文件["+fileNetdisk.getFileName()+"]");
					}
				}
				record.setOptType(4);//下载文件/文件夹
				record.setUserId(TeeRequestInfoContext.getRequestInfo().getUserSid());
				record.setUserName(TeeRequestInfoContext.getRequestInfo().getUserName());
				
				simpleDaoSupport.save(record);
			}
		}

		sysLog.setRemark(sb.toString());
		TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);

		json.setRtState(true);
		json.setRtMsg("文件删除成功!");
		return json;
	}

	/**
	 * 获取权限值
	 * 
	 * @date 2014-2-16
	 * @author
	 * @param sids
	 * @param person
	 * @return
	 */
	public TeeJson getFilePrivValueBySid(int sid, TeePerson person) {
		person=(TeePerson) simpleDaoSupport.get(TeePerson.class,person.getUuid());
		TeeJson json = new TeeJson();

		int rootFolderPriv = 0;

		TeeFileNetdisk dbFileNetdisk = fileNetdiskDao.get(sid);
		rootFolderPriv = this.getFileNetdiskPriv(person, dbFileNetdisk);
		// if (dbFileNetdisk != null) {
		// TeeFileNetdisk paraentFileNetdisk = dbFileNetdisk.getFileParent();
		// if (paraentFileNetdisk != null) {// 子目录
		// String fileFullPath =
		// TeeUtility.null2Empty(dbFileNetdisk.getFileFullPath());
		// int rootFolderSid =
		// TeeStringUtil.getInteger(fileFullPath.split("/")[0], 0);
		// TeeFileNetdisk fileNetdisk = fileNetdiskDao.get(rootFolderSid);
		// rootFolderPriv = this.getFileNetdiskPriv(person, fileNetdisk);
		// } else {
		// rootFolderPriv = this.getFileNetdiskPriv(person, dbFileNetdisk);
		// }
		// }
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("rootFolderPriv", rootFolderPriv);
		json.setRtState(true);
		json.setRtData(map);
		json.setRtMsg("获取文件权限成功!");
		return json;
	}

	/**
	 * 获取权限值
	 * 
	 * @date 2014-2-16
	 * @author
	 * @param person
	 * @param fileNetdisk
	 * @return
	 */
	public int getFileNetdiskPriv(TeePerson person, TeeFileNetdisk fileNetdisk) {
		int rootFolderPriv = 0;
		if (TeePersonService.checkIsSuperAdmin(person, null)) {
			return 127;// 1+2+4+8+16+32+64
		}
		int fileUserPrivValue = 0;
		int fileDeptPrivValue = 0;
		int fileRolePrivValue = 0;
		Set<Integer> filePrivSet = new HashSet<Integer>();
		if (fileNetdisk != null) {
			List<TeeFileUserPriv> fileUserPrivs = fileUserPrivDao
					.getUserPrivDao(person, fileNetdisk);
			if (fileUserPrivs != null && fileUserPrivs.size() > 0) {
				fileUserPrivValue = fileUserPrivs.get(0).getPrivValue();
			}
			List<TeeFileDeptPriv> fileDeptPrivs = fileDeptPrivDao
					.getDeptPrivDao(person, fileNetdisk);
			if (fileDeptPrivs != null && fileDeptPrivs.size() > 0) {
				fileDeptPrivValue = fileDeptPrivs.get(0).getPrivValue();
			}
			List<TeeFileRolePriv> fileRolePrivs = fileRolePrivDao
					.getRolePrivDao(person, fileNetdisk);
			if (fileRolePrivs != null && fileRolePrivs.size() > 0) {
				fileRolePrivValue = fileRolePrivs.get(0).getPrivValue();
			}
			if (fileUserPrivValue != 0 || fileDeptPrivValue != 0
					|| fileRolePrivValue != 0) {

				List<String> fileUserPrivList = Arrays.asList(fileNetdiskDao
						.getPrivValue(fileUserPrivValue).split(","));
				List<String> fileDeptPrivList = Arrays.asList(fileNetdiskDao
						.getPrivValue(fileDeptPrivValue).split(","));
				List<String> fileRolePrivList = Arrays.asList(fileNetdiskDao
						.getPrivValue(fileRolePrivValue).split(","));

				if (fileUserPrivList != null && fileUserPrivList.size() > 0) {
					for (String str : fileUserPrivList) {
						filePrivSet.add(TeeStringUtil.getInteger(str, 0));
					}
				}
				if (fileDeptPrivList != null && fileDeptPrivList.size() > 0) {
					for (String str : fileDeptPrivList) {
						filePrivSet.add(TeeStringUtil.getInteger(str, 0));
					}
				}
				if (fileRolePrivList != null && fileRolePrivList.size() > 0) {
					for (String str : fileRolePrivList) {
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
	 * 批量文件打包zip下载
	 * 
	 * @date 2014-2-19
	 * @author
	 * @param folderSid
	 * @param fileSid
	 * @throws Exception
	 */

	public void downFileToZipBySidService(OutputStream outputStream,
			String zipFileName, String fileSid) throws Exception {
		String fileUploadTempDir = TeeSysProps.getTmpPath();
		// 日志记录
		TeeSysLog sysLog = TeeSysLog.newInstance();
		sysLog.setType("024A");
		StringBuffer logSb = new StringBuffer("批量下载文件  一级文件及目录[");
		// 获取选择的文件夹和文件
		List<TeeFileNetdisk> fileNetdisks = fileNetdiskDao
				.getFileBySidsDao(fileSid);
		for (TeeFileNetdisk file : fileNetdisks) {
			logSb.append(file.getFileName() + ",");
			
			//往操作表中插入操作记录 数据
			TeeFileOptRecord record=new TeeFileOptRecord();
			record.setCreateTime(Calendar.getInstance());
			record.setFileId(file.getSid());
			record.setFileName(file.getFileName());
			record.setIp(TeeRequestInfoContext.getRequestInfo().getIpAddress());
			if(file.getFiletype()==0){//文件夹
				record.setOptContent(TeeRequestInfoContext.getRequestInfo().getUserName()+"下载了["+getFullPathName(file.getSid())+"]文件夹");
			}else{//文件
				record.setOptContent(TeeRequestInfoContext.getRequestInfo().getUserName()+"下载了["+getFullPathName(file.getSid())+"]文件");
			}
			
			record.setOptType(2);//下载文件/文件夹
			record.setUserId(TeeRequestInfoContext.getRequestInfo().getUserSid());
			record.setUserName(TeeRequestInfoContext.getRequestInfo().getUserName());
			
			simpleDaoSupport.save(record);
			
			int xiaZaiCount = file.getXiaZaiCount();
			xiaZaiCount=xiaZaiCount+1;
			file.setXiaZaiCount(xiaZaiCount);
			fileNetdiskDao.update(file);
			
		}
		logSb.append("]");
		sysLog.setRemark(logSb.toString());
		TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);

		List<Map<String, Object>> returnList = this
				.getFileNetdiskList(fileNetdisks);
		if (returnList != null && returnList.size() > 0) {
			for (Map<String, Object> map : returnList) {
				String folderPath = (String) map.get("folderPath");
				List<TeeFileNetdisk> returnFileNetdisks = (List<TeeFileNetdisk>) map
						.get("fileList");
				String fileDirPath = fileUploadTempDir + "/" + folderPath;
				File fileDir = new File(fileDirPath);
				if (!fileDir.exists()) {
					fileDir.mkdirs();
				}
				if (returnFileNetdisks != null && returnFileNetdisks.size() > 0) {
					for (TeeFileNetdisk reTeeFileNetdisk : returnFileNetdisks) {
						TeeAttachment attachment = reTeeFileNetdisk
								.getAttachemnt();
						if (attachment != null) {
							String encryAlgo = attachment.getEncryAlgo();// 加密算法
							if (!TeeUtility.isNullorEmpty(encryAlgo)) {
								String destPath = fileDir.getAbsolutePath()
										+ File.separator
										+ reTeeFileNetdisk.getFileName();
								TeeFileUtility.copyDecryptFile(attachment,
										destPath);
							} else {
								TeeAttachmentSpace attachSpace = attachment
										.getAttachSpace();
								if (attachSpace != null) {
									String fromFilePath = attachSpace
											.getSpacePath()
											+ File.separator
											+ attachment.getModel()
											+ File.separator
											+ attachment.getAttachmentPath();
									String fromFile = fromFilePath
											+ File.separator
											+ attachment.getAttachmentName();
									String filePath = fileDir.getAbsolutePath()
											+ File.separator
											+ reTeeFileNetdisk.getFileName();
									TeeApacheZipUtil.copyFile(fromFile,
											filePath);
								}
							}
						}
					}
				}
			}
		}
		if (!TeeUtility.isNullorEmpty(zipFileName)) {
			String srcFilePath = fileUploadTempDir + File.separator
					+ zipFileName;
			TeeApacheZipUtil.doZip(srcFilePath, outputStream);
			TeeFileUtility.deleteAll(srcFilePath);
		}
	}

	/**
	 * 根据文件List对象获取文件夹下的所有子文件夹和文件（包括当前对象）
	 * 
	 * @date 2014-2-19
	 * @author
	 * @param fileNetdisks
	 * @return 返回list（文件夹sid，文件夹路径、文件夹下的文件）
	 */
	public List<Map<String, Object>> getFileNetdiskList(
			List<TeeFileNetdisk> fileNetdisks) {
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
				}
			}

			for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
				if (fileNetdisk.getFiletype() == 0) {
					// 获取该文件夹的所有子文件夹（包括本文件夹）
					List<TeeFileNetdisk> folderList = fileNetdiskDao
							.getFileListBySidsDao(
									fileNetdisk.getFileFullPath(),
									fileNetdisk.getSid());
					// 获取该文件夹id串
					String folderSidStr = this.getSidsByList(folderList);
					// 根据文件夹id串获取该文件夹的所有文件
					List<TeeFileNetdisk> filesList = fileNetdiskDao
							.getFilesBySidsDao(folderSidStr);
					// 循环目录返回数据
					if (folderList != null && folderList.size() > 0) {
						Map<Integer, String> folderMap = this
								.getFolderPathByFolder(folderList);
						Map<Integer, List<TeeFileNetdisk>> fileNetdiskMap = this
								.getFolderMapByList(folderList, filesList);
						for (TeeFileNetdisk folderObj : folderList) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("sid", folderObj.getSid());
							map.put("folderPath",
									rootFolderName
											+ folderMap.get(folderObj.getSid()));
							map.put("fileList",
									fileNetdiskMap.get(folderObj.getSid()));
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
				if (buffer != null
						&& !TeeUtility.isNullorEmpty(buffer.toString())) {
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
	public Map<Integer, String> getFolderPathByFolder(
			List<TeeFileNetdisk> fileNetdisks) {
		StringBuffer buffer = new StringBuffer();
		Map<Integer, String> map = new HashMap<Integer, String>();
		if (fileNetdisks != null && fileNetdisks.size() > 0) {
			for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
				map.put(fileNetdisk.getSid(),
						buffer.append(fileNetdisk.getFileName() + "/")
								.toString());
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
	public Map<Integer, List<TeeFileNetdisk>> getFolderMapByList(
			List<TeeFileNetdisk> folderList, List<TeeFileNetdisk> fileList) {
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
	 * 根据Id获取文件夹信息
	 * 
	 * @date 2014-2-20
	 * @author
	 * @param sid
	 * @return
	 */
	public TeeFileNetdisk getFileNetdiskObjById(int sid) {
		TeeFileNetdisk fileNetdisk = fileNetdiskDao.getFileNetdiskByIdDao(sid);
		return fileNetdisk;
	}

	/**
	 * 获取复制、剪贴目录树
	 * 
	 * @date 2014-3-2
	 * @author
	 * @param person
	 * @param optionFlag
	 * @param seleteSid
	 * @return
	 */
	public TeeJson getAllFolderTreeService(TeePerson person, String optionFlag,
			String seleteSid) {
		TeeJson json = new TeeJson();
		List<TeeZTreeModel> fileFolderTree = new ArrayList<TeeZTreeModel>();
		int parentSid = 0;

		// 获取所有有权限的文件夹,一次性加载，包含有权限的父级文件夹
		List<TeeFileNetdisk> fileNetdisks = fileNetdiskDao
				.getNetdiskFolders4Priv(person, 0);
		Collections.sort(fileNetdisks, new Comparator<TeeFileNetdisk>() {
			public int compare(TeeFileNetdisk arg0, TeeFileNetdisk arg1) {
				if (arg0.getFileNo() == arg1.getFileNo()) {
					return 0;
				} else if (arg0.getFileNo() > arg1.getFileNo()) {
					return 1;
				} else {
					return -1;
				}
			}
		});

		for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
			if (parentSid == 0) {
				parentSid = fileNetdisk.getSid();
			}
			TeeZTreeModel ztree = new TeeZTreeModel();
			ztree.setId(String.valueOf(fileNetdisk.getSid()));
			ztree.setName(fileNetdisk.getFileName());
			if (fileNetdisk.getFileParent() != null) {
				ztree.setpId(fileNetdisk.getFileParent().getSid() + "");
			} else {
				ztree.setpId("0");
			}
			ztree.setIconSkin(TeeZTreeModel.FILE_FOLDER);
			fileFolderTree.add(ztree);
		}

		json.setRtData(fileFolderTree);
		json.setRtState(true);
		json.setRtMsg("文件目录获取成功!");
		return json;
	}

	/**
	 * 复制文件到其他目录
	 * 
	 * @date 2014-2-20
	 * @author
	 * @param person
	 * @param folderSid
	 *            复制到的目录
	 * @param fileSids
	 *            选择的文件
	 * @return
	 * @throws IOException
	 */
	public TeeJson copyFileToFolderService(TeePerson person, int folderSid,
			String fileSids) throws IOException {
		TeeJson json = new TeeJson();

		// 获取目标文件夹
		TeeFileNetdisk fileNetdisk = fileNetdiskDao.get(folderSid);
		int rootFolderPriv = this.getFileNetdiskPriv(person, fileNetdisk);
		if ((rootFolderPriv & 64) != 64) {
			throw new TeeOperationException("您没有目标文件夹的管理权限！");
		}

		// 获取选中的文件
		List<TeeFileNetdisk> selectedFiles = fileNetdiskDao
				.getFileBySidsDao(fileSids);

		// 遍历选中的文件，判断目标文件夹是否在选中的文件夹的子级文件
		for (TeeFileNetdisk file : selectedFiles) {
			if (file.getFiletype() == 0
					&& fileNetdisk.getFileFullPath().contains(
							"/" + file.getSid() + "/")) {// 文件夹
				throw new TeeOperationException("目标文件夹为源文件夹的子文件夹，无法进行复制");
			}
		}

		// 遍历这三个文件
		TeeFileNetdisk newFile = null;
		List<TeeFileUserPriv> userPrivs = null;
		List<TeeFileDeptPriv> deptPrivs = null;
		List<TeeFileRolePriv> rolePrivs = null;

		TeeFileUserPriv userPriv = null;
		TeeFileDeptPriv deptPriv = null;
		TeeFileRolePriv rolePriv = null;

		for (TeeFileNetdisk file : selectedFiles) {
			newFile = new TeeFileNetdisk();
			if (file.getAttachemnt() != null) {
				newFile.setAttachemnt(attachmentService.clone(
						file.getAttachemnt(),
						TeeAttachmentModelKeys.FILE_NET_DISK, person));
			}
			newFile.setContent(file.getContent());
			newFile.setCreater(person);
			newFile.setCreateTime(new Date());
			newFile.setFileName(file.getFileName());
			newFile.setFileNetdiskType(file.getFileNetdiskType());
			newFile.setFileNo(file.getFileNo());
			newFile.setFileParent(fileNetdisk);
			newFile.setFiletype(file.getFiletype());

			userPrivs = file.getFileUserPriv();
			deptPrivs = file.getFileDeptPriv();
			rolePrivs = file.getFileRolePriv();

			fileNetdiskDao.save(newFile);
			newFile.setFileFullPath(fileNetdisk.getFileFullPath()
					+ newFile.getSid() + "/");
			for (TeeFileUserPriv userPrivs0 : userPrivs) {
				userPriv = new TeeFileUserPriv();
				userPriv.setFileNetdisk(newFile);
				userPriv.setPrivValue(userPrivs0.getPrivValue());
				userPriv.setUser(userPrivs0.getUser());
				newFile.getFileUserPriv().add(userPriv);
			}
			for (TeeFileDeptPriv deptPrivs0 : deptPrivs) {
				deptPriv = new TeeFileDeptPriv();
				deptPriv.setFileNetdisk(newFile);
				deptPriv.setPrivValue(deptPrivs0.getPrivValue());
				deptPriv.setDept(deptPrivs0.getDept());
				newFile.getFileDeptPriv().add(deptPriv);
			}
			for (TeeFileRolePriv rolePrivs0 : rolePrivs) {
				rolePriv = new TeeFileRolePriv();
				rolePriv.setFileNetdisk(newFile);
				rolePriv.setPrivValue(rolePrivs0.getPrivValue());
				rolePriv.setUserRole(rolePrivs0.getUserRole());
				newFile.getFileRolePriv().add(rolePriv);
			}
			fileNetdiskDao.update(newFile);

			changeFullPathAndCopy(person, newFile, file);
			
			//往操作表中插入操作记录 数据
			TeeFileOptRecord record=new TeeFileOptRecord();
			record.setCreateTime(Calendar.getInstance());
			record.setFileId(file.getSid());
			record.setFileName(file.getFileName());
			record.setIp(TeeRequestInfoContext.getRequestInfo().getIpAddress());
			if(file.getFileParent()!=null){
				if(file.getFiletype()==0){//文件夹
					record.setOptContent(TeeRequestInfoContext.getRequestInfo().getUserName()+"将["+getFullPathName(file.getFileParent().getSid())+"]下的文件夹["+file.getFileName()+"]复制到了["+getFullPathName(fileNetdisk.getSid())+"]路径下");
				}else{
					record.setOptContent(TeeRequestInfoContext.getRequestInfo().getUserName()+"将["+getFullPathName(file.getFileParent().getSid())+"]下的文件["+file.getFileName()+"]复制到了["+getFullPathName(fileNetdisk.getSid())+"]路径下");
				}
				
			}
			record.setOptType(5);//复制
			record.setUserId(TeeRequestInfoContext.getRequestInfo().getUserSid());
			record.setUserName(TeeRequestInfoContext.getRequestInfo().getUserName());
			
			simpleDaoSupport.save(record);
		}

		json.setRtState(true);
		json.setRtMsg("文件目录获取成功!");
		return json;
	}

	private void changeFullPathAndCopy(TeePerson person, TeeFileNetdisk nFile,
			TeeFileNetdisk oFile) {
		if (oFile.getFiletype() == 0) {// 文件夹
			// 获取当前文件夹的子级文件夹及文件
			List<TeeFileNetdisk> selectedFiles = fileNetdiskDao
					.getFileByParentIdsDao(oFile.getSid());
			TeeFileNetdisk newFile = null;
			List<TeeFileUserPriv> userPrivs = null;
			List<TeeFileDeptPriv> deptPrivs = null;
			List<TeeFileRolePriv> rolePrivs = null;

			TeeFileUserPriv userPriv = null;
			TeeFileDeptPriv deptPriv = null;
			TeeFileRolePriv rolePriv = null;
			for (TeeFileNetdisk file : selectedFiles) {
				newFile = new TeeFileNetdisk();
				if (file.getAttachemnt() != null) {
					newFile.setAttachemnt(attachmentService.clone(
							file.getAttachemnt(),
							TeeAttachmentModelKeys.FILE_NET_DISK, person));
				}
				newFile.setContent(file.getContent());
				newFile.setCreater(person);
				newFile.setCreateTime(new Date());
				newFile.setFileName(file.getFileName());
				newFile.setFileNetdiskType(file.getFileNetdiskType());
				newFile.setFileNo(file.getFileNo());
				newFile.setFileParent(nFile);
				newFile.setFiletype(file.getFiletype());

				userPrivs = file.getFileUserPriv();
				deptPrivs = file.getFileDeptPriv();
				rolePrivs = file.getFileRolePriv();

				fileNetdiskDao.save(newFile);
				newFile.setFileFullPath(nFile.getFileFullPath()
						+ newFile.getSid() + "/");
				for (TeeFileUserPriv userPrivs0 : userPrivs) {
					userPriv = new TeeFileUserPriv();
					userPriv.setFileNetdisk(newFile);
					userPriv.setPrivValue(userPrivs0.getPrivValue());
					userPriv.setUser(userPrivs0.getUser());
					newFile.getFileUserPriv().add(userPriv);
				}
				for (TeeFileDeptPriv deptPrivs0 : deptPrivs) {
					deptPriv = new TeeFileDeptPriv();
					deptPriv.setFileNetdisk(newFile);
					deptPriv.setPrivValue(deptPrivs0.getPrivValue());
					deptPriv.setDept(deptPrivs0.getDept());
					newFile.getFileDeptPriv().add(deptPriv);
				}
				for (TeeFileRolePriv rolePrivs0 : rolePrivs) {
					rolePriv = new TeeFileRolePriv();
					rolePriv.setFileNetdisk(newFile);
					rolePriv.setPrivValue(rolePrivs0.getPrivValue());
					rolePriv.setUserRole(rolePrivs0.getUserRole());
					newFile.getFileRolePriv().add(rolePriv);
				}
				fileNetdiskDao.update(newFile);

				changeFullPathAndCopy(person, newFile, file);
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
	public void getFileReNameStr(StringBuffer fileNameBuffer,
			List<String> fileNameList, String srcName, boolean isFile) {
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
						String privName = TeeFileUtility
								.getFileNameNoExt(srcName) + "-复制";
						fileName = privName + TeeFileConst.PATH_POINT
								+ TeeFileUtility.getFileExtName(srcName);
					} else {
						fileName = srcName + "-复制";
					}
					getFileReNameStr(fileNameBuffer, newList, fileName, isFile);
				}
			}
		}
	}

	/**
	 * 剪贴文件到其他目录
	 * 
	 * @date 2014-2-20
	 * @author
	 * @param person
	 * @param folderSid
	 * @param fileSids
	 * @return
	 * @throws IOException
	 */
	public TeeJson cutFileToFolderService(TeePerson person, int folderSid,
			String fileSids) throws IOException {
		TeeJson json = new TeeJson();

		// 获取目标文件夹
		TeeFileNetdisk fileNetdisk = fileNetdiskDao.get(folderSid);
		int rootFolderPriv = this.getFileNetdiskPriv(person, fileNetdisk);
		if ((rootFolderPriv & 64) != 64) {
			throw new TeeOperationException("您没有目标文件夹的管理权限！");
		}

		// 获取选中的文件
		List<TeeFileNetdisk> selectedFiles = fileNetdiskDao
				.getFileBySidsDao(fileSids);

		// 遍历选中的文件，判断目标文件夹是否在选中的文件夹的子级文件
		for (TeeFileNetdisk file : selectedFiles) {
			if (file.getFiletype() == 0
					&& fileNetdisk.getFileFullPath().contains(
							"/" + file.getSid() + "/")) {// 文件夹
				throw new TeeOperationException("目标文件夹为源文件夹的子文件夹，无法进行剪切");
			}
		}

		// 遍历这三个文件

		for (TeeFileNetdisk file : selectedFiles) {
			
			TeeFileNetdisk oldParent=file.getFileParent();
			file.setFileFullPath(fileNetdisk.getFileFullPath() + file.getSid()
					+ "/");
			file.setFileParent(fileNetdisk);
			fileNetdiskDao.update(file);
			changeFullPathAndCut(person, file);
			
			
			//往操作表中插入操作记录 数据
			TeeFileOptRecord record=new TeeFileOptRecord();
			record.setCreateTime(Calendar.getInstance());
			record.setFileId(file.getSid());
			record.setFileName(file.getFileName());
			record.setIp(TeeRequestInfoContext.getRequestInfo().getIpAddress());
			if(oldParent!=null){
				if(file.getFiletype()==0){//文件夹
					record.setOptContent(TeeRequestInfoContext.getRequestInfo().getUserName()+"将["+getFullPathName(oldParent.getSid())+"]下的文件夹["+file.getFileName()+"]移动到了["+getFullPathName(fileNetdisk.getSid())+"]路径下");
				}else{
					record.setOptContent(TeeRequestInfoContext.getRequestInfo().getUserName()+"将["+getFullPathName(oldParent.getSid())+"]下的文件["+file.getFileName()+"]移动到了["+getFullPathName(fileNetdisk.getSid())+"]路径下");
				}
				
			}
			record.setOptType(6);//剪切/移动
			record.setUserId(TeeRequestInfoContext.getRequestInfo().getUserSid());
			record.setUserName(TeeRequestInfoContext.getRequestInfo().getUserName());
			
			simpleDaoSupport.save(record);
			
			
		}

		json.setRtState(true);
		json.setRtMsg("文件目录获取成功!");
		return json;
	}

	private void changeFullPathAndCut(TeePerson person, TeeFileNetdisk file) {
		if (file.getFiletype() == 0) {// 文件夹
			// 获取当前文件夹的子级文件夹及文件
			List<TeeFileNetdisk> selectedFiles = fileNetdiskDao
					.getFileByParentIdsDao(file.getSid());

			for (TeeFileNetdisk f : selectedFiles) {
				f.setFileFullPath(file.getFileFullPath() + f.getSid() + "/");
				f.setFileParent(file);
				fileNetdiskDao.update(f);
				changeFullPathAndCut(person, f);
			}
		}
	}

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
	 * 根据名称关键字检索公共网盘
	 * 
	 * @date 2014-3-2
	 * @author
	 * @param fileName
	 * @return
	 */
	public TeeJson queryFileNetdiskByName(TeePerson person, String fileName) {
		TeeJson json = new TeeJson();
		List<Map> list = new ArrayList<Map>();

		List<TeeFileNetdisk> getPrivFolder = fileNetdiskDao
				.getRootPrivFolderDao(person, 0);

		StringBuffer buffer = new StringBuffer();
		for (TeeFileNetdisk privFolder : getPrivFolder) {
			if (!TeeUtility.isNullorEmpty(buffer.toString())) {
				buffer.append(",");
			}
			buffer.append(privFolder.getSid() + "/");
		}

		List<TeeFileNetdisk> fileNetdisks = fileNetdiskDao
				.queryFileNetdiskByNameDao(buffer.toString(), fileName);
		for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
			TeeAttachment attachment = fileNetdisk.getAttachemnt();
			if (attachment != null) {
				Map map = new HashMap();
				map.put("sid", fileNetdisk.getSid());
				map.put("attachSid", attachment.getSid());
				map.put("fileName", fileNetdisk.getFileName());
				map.put("fileExt", attachment.getExt());
				map.put("model", attachment.getModel());
				map.put("createUser", fileNetdisk.getCreater().getUserName());
				map.put("createTime",
						TeeUtility.getDateTimeStr(fileNetdisk.getCreateTime()));
				list.add(map);
			}
		}
		json.setRtData(list);
		json.setRtState(true);
		return json;
	}

	/**
	 * 搜索某目录下的文件
	 * 
	 * @date 2014-3-2
	 * @author
	 * @param fileName
	 * @return
	 * @throws ParseException
	 */
	public TeeEasyuiDataGridJson getManageInfoList(TeeDataGridModel dm,
			Map requestDatas, TeePerson loginPerson) throws ParseException {
		loginPerson=(TeePerson) simpleDaoSupport.get(TeePerson.class,loginPerson.getUuid());
		//获取当前登陆人  辅助部门的ids
		String otherDeptIds="";
		List<TeeDepartment> otherDeptList=loginPerson.getDeptIdOther();
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
		List<TeeUserRole> otherRoleList=loginPerson.getUserRoleOther();
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
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		int folderId = TeeStringUtil
				.getInteger(requestDatas.get("folderId"), 0);

		String fileName = (String) requestDatas.get("fileName");
		int createrId = TeeStringUtil.getInteger(requestDatas.get("createrId"),
				0);
		String createTimeStrMin = (String) requestDatas.get("createTimeStrMin");
		String createTimeStrMax = (String) requestDatas.get("createTimeStrMax");

		TeeFileNetdisk dbFileNetdisk = fileNetdiskDao.get(folderId);
		String path = dbFileNetdisk.getFileFullPath();
		// int rootFolderSid = 0;
		// if (dbFileNetdisk != null) {
		// TeeFileNetdisk paraentFileNetdisk = dbFileNetdisk.getFileParent();
		// if (paraentFileNetdisk != null) {// 子目录
		// String fileFullPath =
		// TeeUtility.null2Empty(dbFileNetdisk.getFileFullPath());
		// rootFolderSid = TeeStringUtil.getInteger(fileFullPath.split("/")[0],
		// 0);
		// } else {
		// rootFolderSid = dbFileNetdisk.getSid();
		// }
		// }
		//
		// List<Map> list = new ArrayList<Map>();
		// List<TeeFileNetdisk> getPrivFolder =
		// fileNetdiskDao.getRootPrivFolderDao(loginPerson, rootFolderSid);
		//
		// StringBuffer buffer = new StringBuffer();
		// for (TeeFileNetdisk privFolder : getPrivFolder) {
		// if (!TeeUtility.isNullorEmpty(buffer.toString())) {
		// buffer.append(",");
		// }
		// buffer.append(privFolder.getSid() + "/");
		// }
		// String fileFullPath = buffer.toString();
		//
		// if (fileFullPath.endsWith(",")) {
		// fileFullPath = fileFullPath.substring(0, fileFullPath.length() - 1);
		// }
		// String[] sidArray = fileFullPath.split(",");
		// StringBuffer buffer2 = new StringBuffer();
		// if (sidArray.length > 0) {
		// for (String sid : sidArray) {
		// if (!TeeUtility.isNullorEmpty(buffer2.toString())) {
		// buffer2.append(" or ");
		// }
		// buffer2.append(" fileFullPath like'" + sid + "%'");
		//
		// }
		// }

		String hql = "  from TeeFileNetdisk f where f.fileNetdiskType=0 and f.filetype=1 and f.fileFullPath like '"
				+ path + "%' ";
		hql += " and (exists (select 1 from f.fileParent.fileUserPriv fileUserPriv where fileUserPriv.user.uuid="
				+ loginPerson.getUuid()
				+ " and fileUserPriv.privValue!=0)"
				+ " or exists (select 1 from f.fileParent.fileDeptPriv fileDeptPriv where fileDeptPriv.dept.uuid="
				+ loginPerson.getDept().getUuid()
				+ " and fileDeptPriv.privValue!=0)"
				+ " or exists (select 1 from f.fileParent.fileDeptPriv fileDeptPriv where "
				+ TeeDbUtility.IN("fileDeptPriv.dept.uuid", otherDeptIds)
				+ " and fileDeptPriv.privValue!=0)"
				+ " or exists (select 1 from f.fileParent.fileRolePriv fileRolePriv where "
				+ TeeDbUtility.IN("fileRolePriv.userRole.uuid", otherRoleIds)
				+ " and fileRolePriv.privValue!=0)"
				+ " or exists (select 1 from f.fileParent.fileRolePriv fileRolePriv where fileRolePriv.userRole.uuid="
				+ loginPerson.getUserRole().getUuid()
				+ " and fileRolePriv.privValue!=0))";
		List param = new ArrayList();
		if (!TeeUtility.isNullorEmpty(fileName)) {
			hql += " and f.fileName like ? ";
			param.add("%" + fileName + "%");
		}
		if (createrId > 0) {
			hql += " and f.creater.uuid = ? ";
			param.add(createrId);
		}

		if (!TeeUtility.isNullorEmpty(createTimeStrMin)) {
			hql += " and f.createTime>=?";
			param.add(TeeUtility.parseDate(createTimeStrMin));
		}
		if (!TeeUtility.isNullorEmpty(createTimeStrMax)) {
			hql += " and f.createTime<?";
			param.add(TeeUtility.parseDate(createTimeStrMax));
		}
		if (TeeUtility.isNullorEmpty(dm.getSort())) {
			dm.setSort("fileName");
			dm.setOrder("asc");
		}
		if ("createTimeStr".equals(dm.getSort())) {
			dm.setSort("createTime");
		}
		dataGridJson.setTotal(fileNetdiskDao.countByList("select count(*) "
				+ hql, param));// 设置总记录数

		hql += " order by f.filetype, " + dm.getSort() + " " + dm.getOrder();
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置

		List<TeeFileNetdisk> fileNetdisks = fileNetdiskDao.pageFindByList(hql,
				(dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);

		List<TeeFileNetdiskModel> models = new ArrayList<TeeFileNetdiskModel>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (fileNetdisks != null && fileNetdisks.size() > 0) {
			for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
				TeeFileNetdiskModel model = new TeeFileNetdiskModel();
				BeanUtils.copyProperties(fileNetdisk, model);
				/*model.setCreateTimeStr(TeeUtility.getDateTimeStr(fileNetdisk
						.getCreateTime()));*/
				model.setCreateTimeStr(sdf.format(fileNetdisk
						.getCreateTime()));
				TeeAttachment attaches = fileNetdisk.getAttachemnt();
				if (attaches != null) {
					TeeAttachmentModel attachmentModel = new TeeAttachmentModel();
					BeanUtils.copyProperties(attaches, attachmentModel);
					model.setAttacheModels(attachmentModel);
					model.setFileSize(TeeApacheZipUtil
							.getDisckSize(attachmentModel.getSize()));
					model.setFileTypeExt(attachmentModel.getExt());
					model.setAttachSid(attachmentModel.getSid());
				} else if (fileNetdisk.getFiletype() == 0) {
					model.setFileTypeExt("0");
				}
				StringBuffer filePathBuffer = new StringBuffer();
				if (!TeeUtility.isNullorEmpty(fileNetdisk.getFileFullPath())) {
					List<TeeFileNetdisk> fileNetdisks2 = fileNetdiskDao
							.getFileNetdiskBySids(fileNetdisk.getFileFullPath());
					if (fileNetdisks2 != null && fileNetdisks2.size() > 0) {
						for (int i = 0; i < fileNetdisks2.size(); i++) {
							TeeFileNetdisk fileNetdisk3 = fileNetdisks2.get(i);
							if (filePathBuffer.length() > 0) {
								filePathBuffer.append("/");
							}
							filePathBuffer.append(fileNetdisk3.getFileName());
						}
					}
				} else {// 根目录
					filePathBuffer.append(dbFileNetdisk.getFileName());
				}
				if (fileNetdisk.getFiletype() == 1) {
					boolean isSignReadFlag = readerDao.isSignReadDao(
							fileNetdisk.getSid(), loginPerson.getUuid());
					int isSignRead = 0;
					if (isSignReadFlag) {
						isSignRead = 1;
					}
					model.setIsSignRead(isSignRead);
				}
				model.setParentFileName(filePathBuffer.toString());
				model.setParentFileSid(fileNetdisk.getFileParent().getSid());
				models.add(model);
			}
		}
		dataGridJson.setRows(models);
		return dataGridJson;
	}

	/**
	 * 获取公共文件夹目录树
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年7月20日
	 * @param requestMap
	 * @param model
	 * @return TeeJson
	 */
	public TeeJson getNetdiskFolderTree(Map requestMap,
			TeeFileNetdiskModel model) {

		List<TeeZTreeModel> fileFolderTree = new ArrayList<TeeZTreeModel>();
		int menuSid = TeeStringUtil.getInteger(requestMap.get("menuSid"), 0);
		int openFolderSid = TeeStringUtil.getInteger(
				requestMap.get("openFolderSid"), 0);// 剪贴时展开选中目录
		TeePerson person = (TeePerson) requestMap.get(TeeConst.LOGIN_USER);
		person=(TeePerson) simpleDaoSupport.get(TeePerson.class,person.getUuid());
		int parentSid = 0;

		// 获取所有有权限的文件夹,一次性加载，包含有权限的父级文件夹
		List<TeeFileNetdisk> fileNetdisks = fileNetdiskDao
				.getNetdiskFolders4Priv(person, menuSid);
		Collections.sort(fileNetdisks, new Comparator<TeeFileNetdisk>() {
			public int compare(TeeFileNetdisk arg0, TeeFileNetdisk arg1) {
				if (arg0.getFileNo() == arg1.getFileNo()) {
					return 0;
				} else if (arg0.getFileNo() > arg1.getFileNo()) {
					return 1;
				} else {
					return -1;
				}
			}
		});

		for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
			if (parentSid == 0) {
				parentSid = fileNetdisk.getSid();
			}
			TeeZTreeModel ztree = new TeeZTreeModel();
			ztree.setId(String.valueOf(fileNetdisk.getSid()));
			ztree.setName(fileNetdisk.getFileName());
			if (fileNetdisk.getFileParent() != null) {
				ztree.setpId(fileNetdisk.getFileParent().getSid() + "");
			} else {
				ztree.setpId("0");
			}
			ztree.setIconSkin(TeeZTreeModel.FILE_FOLDER);
			fileFolderTree.add(ztree);
		}

		// List<TeeFileNetdisk> fileNetdisks =
		// fileNetdiskDao.getRootPrivFolderDao(person, menuSid);
		//
		// int parentSid = 0;
		// if (fileNetdisks != null && fileNetdisks.size() > 0) {
		// parentSid = fileNetdisks.get(0).getSid();
		// for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
		// List<TeeFileNetdisk> files =
		// fileNetdiskDao.getFileListBySidsDao(fileNetdisk.getSid() + "/",
		// fileNetdisk.getSid());
		//
		// if (files != null && files.size() > 0) {
		// for (int i=0;i<files.size();i++) {
		// TeeFileNetdisk file = files.get(i);
		// int parentId = 0;
		// if (file.getFileParent() != null) {
		// parentId = file.getFileParent().getSid();
		// }
		//
		// boolean openFlag = false;
		// TeeZTreeModel ztree = new TeeZTreeModel();
		// ztree.setId(String.valueOf(file.getSid()));
		// ztree.setName(file.getFileName());
		// if(file.getSid() == openFolderSid){
		// openFlag = true;
		// }else{
		// if(file.getSid() == parentSid){
		// openFlag = true;
		// }
		// }
		// ztree.setOpen(openFlag);
		// ztree.setpId(parentId + "");
		// ztree.setIconSkin(TeeZTreeModel.FILE_FOLDER);
		// fileFolderTree.add(ztree);
		// }
		// }
		// }
		// }

		TeeJson json = new TeeJson();
		Map map = new HashMap();
		map.put("ztreeData", fileFolderTree);
		map.put("parentSid", parentSid);
		json.setRtData(map);
		json.setRtState(true);
		json.setRtMsg("文件目录获取成功!");
		return json;

	}

	/**
	 * 更新文件备注
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年7月21日
	 * @param request
	 * @param model
	 * @return TeeJson
	 */
	public TeeJson updateContent(HttpServletRequest request,
			TeeFileNetdiskModel model) {

		if (model.getSid() > 0) {
			TeeFileNetdisk fileNetdisk = fileNetdiskDao.get(model.getSid());
			if (fileNetdisk != null) {
				fileNetdisk.setContent(model.getContent());
				fileNetdiskDao.update(fileNetdisk);
			}
			
			//往操作表中插入操作记录 数据
			TeeFileOptRecord record=new TeeFileOptRecord();
			record.setCreateTime(Calendar.getInstance());
			record.setFileId(fileNetdisk.getSid());
			record.setFileName(fileNetdisk.getFileName());
			record.setIp(TeeRequestInfoContext.getRequestInfo().getIpAddress());
			record.setOptContent(TeeRequestInfoContext.getRequestInfo().getUserName()+"更新了["+getFullPathName(fileNetdisk.getSid())+"]文件的文件备注");
			record.setOptType(8);//添加文件备注
			record.setUserId(TeeRequestInfoContext.getRequestInfo().getUserSid());
			record.setUserName(TeeRequestInfoContext.getRequestInfo().getUserName());
			
			simpleDaoSupport.save(record);
		}
		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtMsg("数据保存成功!");
		return json;
	}

	// 开启
	public void updateAutoIndex(TeeFileNetdiskModel tfnm) {
		TeeFileNetdisk tfn = (TeeFileNetdisk) simpleDaoSupport.get(
				TeeFileNetdisk.class, tfnm.getSid());

		// 再往实体类中set对应的属性值
		if (tfnm.getAutoIndex() == 1 || tfnm.getAutoIndex() == 0) {
			tfnm.setAutoIndex(1);
			tfn.setAutoIndex(tfnm.getAutoIndex());
		}
		// 执行更新
		fileNetdiskDao.update(tfn);
	}

	// 关闭
	public void updateAutoIndexgb(TeeFileNetdiskModel tfnm) {
		TeeFileNetdisk tfn = (TeeFileNetdisk) simpleDaoSupport.get(
				TeeFileNetdisk.class, tfnm.getSid());
		// 再往实体类中set对应的属性值

		tfnm.setAutoIndex(0);
		tfn.setAutoIndex(tfnm.getAutoIndex());
		// 执行更新
		fileNetdiskDao.update(tfn);
		System.out.println(tfn.getAutoIndex());
	}

	
	
	//获取文件夹的全路径名
	public String getFullPathName(int sid){
		
		TeeFileNetdisk disk=(TeeFileNetdisk) simpleDaoSupport.get(TeeFileNetdisk.class,sid);
		String fullPathName="";
		String fileFullPath=disk.getFileFullPath();
		if(!TeeUtility.isNullorEmpty(fileFullPath)){
			if(fileFullPath.startsWith("/")){
				fileFullPath=fileFullPath.substring(1,fileFullPath.length());
			}
			if(fileFullPath.endsWith("/")){
				fileFullPath=fileFullPath.substring(0,fileFullPath.length()-1);
			}
			String[]array=fileFullPath.split("/");
			if(array.length>0){
				for (String str : array) {
					int id=Integer.parseInt(str);
					TeeFileNetdisk d=(TeeFileNetdisk) simpleDaoSupport.get(TeeFileNetdisk.class,id);
					fullPathName+=d.getFileName()+"/";
				}
			}
		}
		if(fullPathName.endsWith("/")){
			fullPathName=fullPathName.substring(0,fullPathName.length()-1);
		}
		return fullPathName;
	}
	
	
	/**
	 * 新建公共网盘目录
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
		
		dbFileNetdisk = fileNetdiskDao.get(sid);
		
		if (dbFileNetdisk != null) {
			TeeFileNetdisk fileNetdisk = new TeeFileNetdisk();
			fileNetdisk.setFileName(folderName);
			fileNetdisk.setCreater(person);
			fileNetdisk.setFiletype(0);
			fileNetdisk.setFileNetdiskType(0);
			fileNetdisk.setCreateTime(new Date());
			fileNetdisk.setFileParent(dbFileNetdisk);
			fileNetdiskDao.save(fileNetdisk);
			
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
	 * 添加星
	 * */
	public TeeJson addPicCount(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid  = TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeFileNetdisk netdisk = fileNetdiskDao.get(sid);
		if(netdisk!=null){
			int picCount = netdisk.getPicCount();
			picCount=picCount+1;
			if(picCount<=5){
				netdisk.setPicCount(picCount);
				fileNetdiskDao.update(netdisk);
			}
			json.setRtState(true);
		}else{
			json.setRtState(false);
		}	
		return json;
	}
	

	/**
	 * 删除星
	 * */
	public TeeJson deletePicCount(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid  = TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeFileNetdisk netdisk = fileNetdiskDao.get(sid);
		if(netdisk!=null){
			int picCount = netdisk.getPicCount();
			picCount=picCount-1;
			if(picCount>0){
				netdisk.setPicCount(picCount);
				fileNetdiskDao.update(netdisk);
			}
			json.setRtState(true);
		}else{
			json.setRtState(false);
		}	
		return json;
	}

	public TeeJson updatePicCount(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int sid  = TeeStringUtil.getInteger(request.getParameter("sid"),0);
		int picCount  = TeeStringUtil.getInteger(request.getParameter("picCount"),0);
		String adviseFile = request.getParameter("adviseFile");
		//评分
		List<TeeFilePingFei> find2 = teeFilePingFeiDao.find("from TeeFilePingFei where fileId=? and user.uuid=?", new Object[]{sid,person.getUuid()});
		if(find2!=null && find2.size()>0){
			TeeFilePingFei pf = find2.get(0);
			pf.setPicCount(picCount);
			pf.setContent(adviseFile);
			teeFilePingFeiDao.update(pf);
		}else{
			TeeFilePingFei pf=new TeeFilePingFei();
			pf.setFileId(sid);
			pf.setPicCount(picCount);
			pf.setContent(adviseFile);
			pf.setUser(person);
			teeFilePingFeiDao.save(pf);
		}
		List<TeeFilePingFei> find = teeFilePingFeiDao.find("from TeeFilePingFei where fileId=?", new Object[]{sid});
		int picNumber=0;
		if(find!=null && find.size()>0){
			for(TeeFilePingFei f:find){
				picNumber+=f.getPicCount();
			}
			picNumber=picNumber/find.size();
		}
		TeeFileNetdisk netdisk = fileNetdiskDao.get(sid);
		if(netdisk!=null){
			netdisk.setPicCount(picNumber);
			fileNetdiskDao.update(netdisk);
			json.setRtState(true);
		}else{
			json.setRtState(false);
		}	
		return json;
	}
	
	public TeeFileNetdisk findFileByFileName(String fullPath,String fileName){
		List<TeeFileNetdisk> find = fileNetdiskDao.find("from TeeFileNetdisk where filetype=0 and fileFullPath like '"+fullPath+"%' and fileName='"+fileName+"'", null);
		TeeFileNetdisk disk=new TeeFileNetdisk();
		if(find!=null && find.size()>0){
			disk=find.get(0);
		}
		return disk;
	}
	
	/**
	 * 新建文件网盘（一级）
	 * 
	 * @date 2014-1-4
	 * @author
	 * @param person
	 * @param model
	 * @throws Exception
	 */
	public void addFndService(TeePerson person,
			TeeFileNetdisk disk) {
		fileNetdiskDao.save(disk);

		
	}
	
	/**
	 * 数据迁移
	 * @param disk添加
	 */
	public void save(TeeFileNetdisk disk) {
		fileNetdiskDao.save(disk);

		
	}
	
	/**
	 * 修改
	 * @param disk
	 */
	public void update(TeeFileNetdisk disk) {
		fileNetdiskDao.update(disk);
		
	}
	
	/**
	 * 查询
	 * @param strArray 
	 * @return 
	 */
	public TeeFileNetdisk selectFileName(String parentFile,TeeFileNetdisk fileNetdisk){
		String hql="";
		if(fileNetdisk==null){//一级文件夹
			hql = "from TeeFileNetdisk where fileName='"+parentFile+"' and fileParent is null";//查询名称为strArray[i]得一级文件夹对象   
		}else{//
			hql = "from TeeFileNetdisk where fileName='"+parentFile+"' and fileParent.sid="+fileNetdisk.getSid();//查询名称为strArray[i]的文件夹 且 上级文件目录为parentFile的    文件夹     
		}
		return (TeeFileNetdisk)simpleDaoSupport.executeQuery(hql, null).get(0);
		
	}



	/**
	 * 根据当前登陆人和文件夹主键   获取文件夹下文件和文件夹的集合
	 * @param request
	 * @return
	 */
	public TeeJson getFileNetdiskListByFolderIdAndUser(
			HttpServletRequest request) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeeJson json=new TeeJson();
		//获取前台页面传来的用户的登录账号
		String userId=TeeStringUtil.getString(request.getParameter("userId"));
		//获取前台页面传来的folderSid
		int folderSid=TeeStringUtil.getInteger(request.getParameter("folderSid"),0);
		String hql = "";
		List<TeeFileNetdisk> fileNetdisks=null;
		hql = " select file  from TeeFileNetdisk file  where fileNetdiskType=0 and fileParent.sid in(" + folderSid + ")";
		hql += " order by filetype, createTime  desc ";
		fileNetdisks =simpleDaoSupport.executeQuery(hql, null);
		
		List<TeeFileNetdiskModel> models = new ArrayList<TeeFileNetdiskModel>();

		if (fileNetdisks != null && fileNetdisks.size() > 0) {
			for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
				TeeFileNetdiskModel model = new TeeFileNetdiskModel();
				BeanUtils.copyProperties(fileNetdisk, model);
				model.setCreateTimeStr(sdf.format(fileNetdisk
						.getCreateTime()));
				TeeAttachment attaches = fileNetdisk.getAttachemnt();
				if (attaches != null) {
					TeeAttachmentModel attachmentModel = new TeeAttachmentModel();
					BeanUtils.copyProperties(attaches, attachmentModel);
					model.setAttacheModels(attachmentModel);
					model.setFileSize(TeeApacheZipUtil
							.getDisckSize(attachmentModel.getSize()));
					model.setFileTypeExt(attachmentModel.getExt());
					model.setAttachSid(attachmentModel.getSid());
				} else if (fileNetdisk.getFiletype() == 0) {//文件夹
					model.setFileTypeExt("0");
				}
				if (fileNetdisk.getFiletype() == 1) {//文件
//					boolean isSignReadFlag = readerDao.isSignReadDao(
//							fileNetdisk.getSid(), p.getUuid());
//					int isSignRead = 0;
//					if (isSignReadFlag) {
//						isSignRead = 1;
//					}
//					model.setIsSignRead(isSignRead);
				}
				if (fileNetdisk.getCreater() != null) {
					model.setCreaterId(fileNetdisk.getCreater().getUuid());
					model.setCreaterStr(fileNetdisk.getCreater().getUserName());
				}
				
				//判断当前文件 或者  文件夹下  是否存在当天发布的文件
				int hasTodayCreated=checkIsHasTodayCreatedFiles(fileNetdisk);
				model.setHasTodayCreated(hasTodayCreated);
				models.add(model);
			}
		}
		
		json.setRtState(true);
		json.setRtData(models);
		
		return json;
	}



	
	/**
	 * 判断当前文件夹下  是否有当天新建的文件  有权限控制
	 * @param fileNetdisk
	 * @param p
	 * @return
	 */
	private int checkIsHasTodayCreatedFiles(TeeFileNetdisk fileNetdisk) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//获取当前日期
		Date today=new Date();
		String todayStr=sdf.format(today);
		Date createTime=null;
		String createTimeStr="";
		if(fileNetdisk.getFiletype()==0){//文件夹
			String hql = " select file  from TeeFileNetdisk file  where fileParent.sid in(" + fileNetdisk.getSid() + ") and fileNetdiskType=0 and filetype=1" ;
			
			hql += " order by  createTime  desc ";
			List<TeeFileNetdisk> fileNetdisks =simpleDaoSupport.executeQuery(hql,null);
			if(fileNetdisks!=null&&fileNetdisks.size()>0){
				for (TeeFileNetdisk teeFileNetdisk : fileNetdisks) {
					createTime=teeFileNetdisk.getCreateTime();
					createTimeStr=sdf.format(createTime);
					if(todayStr.equals(createTimeStr)){//是当天
						return 1;
					}
				}
			}
		}else if(fileNetdisk.getFiletype()==1){//文件
			//文件的话直接判断  该文件是否是当天新建的
			createTime=fileNetdisk.getCreateTime();
			createTimeStr=sdf.format(createTime);
			if(todayStr.equals(createTimeStr)){//是当天
				return 1;
			}else{//不是当天
				return 0;
			}
			
		}
		return 0;
	}
}





