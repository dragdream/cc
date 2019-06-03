package com.tianee.oa.core.workflow.flowrun.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.tools.ant.filters.StringInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.bean.TeeBaseStream;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseDownloadService;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileDeptPrivDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileNetdiskPersonDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileRolePrivDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileUserPrivDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeSaveFileToPersonDao;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workFlowFrame.dataloader.TeeSimpleDataLoaderInterface;
import com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunDoc;
import com.tianee.oa.core.workflow.flowrun.dao.TeeRepeatToPersonDao;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.file.TeeZipUtil;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
@Service
public class TeeRepeatToPersonService extends TeeBaseService implements TeeRepeatToPersonServiceInterface{
	@Autowired
	private TeeFileNetdiskPersonDao fileNetdiskPersonDao;

	@Autowired
	private TeeRepeatToPersonDao repeatToPersonDao;

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
	
	@Autowired
	private TeeBaseDownloadService baseDownloadService;

	@Autowired
	private TeeSimpleDataLoaderInterface simpleDataLoader;
	
	@Autowired
	private TeeWorkFlowServiceContextInterface flowServiceContext;

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeRepeatToPersonServiceInterface#getFlowServiceContext()
	 */
	@Override
	public TeeWorkFlowServiceContextInterface getFlowServiceContext() {
		return flowServiceContext;
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeRepeatToPersonServiceInterface#setFlowServiceContext(com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface)
	 */
	@Override
	public void setFlowServiceContext(
			TeeWorkFlowServiceContextInterface flowServiceContext) {
		this.flowServiceContext = flowServiceContext;
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeRepeatToPersonServiceInterface#getPersonFolderTree(java.util.Map, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public TeeJson getPersonFolderTree(Map requestMap, TeePerson loginPerson) {
		TeeJson json = new TeeJson();
		List<TeeZTreeModel> fileFolderTree = new ArrayList<TeeZTreeModel>();

		List<TeeFileNetdisk> personRootFolder = fileNetdiskPersonDao
				.getPersonRootFolderDao(loginPerson);
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

			List<TeeFileNetdisk> folderList = repeatToPersonDao
					.getPersonFolderTreeDao(fileFullPathBuffer.toString(),
							sidBuffer.toString(), loginPerson);

			if (folderList != null && folderList.size() > 0) {
				for (TeeFileNetdisk fileNetdisk : folderList) {
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

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeRepeatToPersonServiceInterface#saveToPersonFolder(java.util.Map, javax.servlet.http.HttpServletResponse, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public TeeJson saveToPersonFolder(Map requestMap,HttpServletResponse response, TeePerson loginPerson) {
		TeeJson json = new TeeJson();
		try {
			int resultFlag = 0;
			String folderSid = (String) requestMap.get("folderSid");
			String runId = (String) requestMap.get("runId");
			int repeatForm = TeeStringUtil.getInteger(requestMap.get("repeatForm"),
					0);
			int repeatText = TeeStringUtil.getInteger(requestMap.get("repeatText"),
					0);
			int repeatFormatText = TeeStringUtil.getInteger(
					requestMap.get("repeatFormatText"), 0);
			int repeatAttach = TeeStringUtil.getInteger(
					requestMap.get("repeatAttach"), 0);

			//根据runId获取flowRun
			TeeFlowRun run=flowServiceContext.getFlowRunService().get(Integer.parseInt(runId));
			// 创建临时下载文件夹
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String nameDesc = "[" + runId + "]"
					+ run.getRunName().toString().replace(":", "_");


			// 获取流程正文
			TeeFlowRunDoc doc = flowServiceContext.getFlowRunDocService()
					.getFlowRunDocByRunId(Integer.parseInt(runId));
			if(repeatText==1&&doc != null){
					TeeBaseStream baseStream = baseDownloadService.getTeeBaseStream(doc
							.getDocAttach());
					InputStream in = baseStream.getFileInputStream();
					TeeFileUtility
							.readStreamToFile(in, new File(TeeSysProps.getTmpPath()
									+ "/" + uuid + "/正文.doc"));
					baseStream.close();
			}
			
			
			// 获取公共附件
			List<TeeAttachment> attaches = attachmentService.getAttaches(
					TeeAttachmentModelKeys.workFlow, runId);
			if(repeatAttach==1&&attaches.size()>0){
				for (TeeAttachment attachment : attaches) {
					TeeBaseStream baseStream = baseDownloadService
							.getTeeBaseStream(attachment);
					InputStream in = baseStream.getFileInputStream();
					TeeFileUtility.readStreamToFile(in,
							new File(TeeSysProps.getTmpPath() + "/" + uuid + "/公共附件/"
									+ attachment.getFileName()));
					baseStream.close();
				}		
			}
			
			
			//获取版式正文
			List<TeeAttachment> Apiattach = attachmentService.getAttaches(
				TeeAttachmentModelKeys.workFlowDocAip, runId);
			if(repeatFormatText==1 && Apiattach.size()>0){
				TeeAttachment attachment =Apiattach.get(0);
				TeeBaseStream baseStream = baseDownloadService
						.getTeeBaseStream(attachment);
				InputStream in = baseStream.getFileInputStream();
				TeeFileUtility.readStreamToFile(in,
						new File(TeeSysProps.getTmpPath() + "/" + uuid + "/版式正文.aip"));
				baseStream.close();	
			}
			
			    
			if(repeatForm==1){
				requestMap.put("view", 1);
				String sb = simpleDataLoader.getFormPrintDataStream(requestMap, loginPerson);
				// 复制表单
				StringInputStream sis = new StringInputStream(sb.toString(), "UTF-8");
				TeeFileUtility.readStreamToFile(sis, new File(TeeSysProps.getTmpPath()
						+ "/" + uuid + "/表单.html"));
				
				//复制表单附件（附件上传控件）
				String hql=" from TeeAttachment where model=? and modelId like ? ";
				List<TeeAttachment> attCtrlList=simpleDaoSupport.executeQuery(hql, new Object[]{"workFlowUploadCtrl","%"+"_"+runId});
				if(attCtrlList.size()>0){
					for (TeeAttachment attachment : attCtrlList) {
						TeeBaseStream baseStream = baseDownloadService
								.getTeeBaseStream(attachment);
						InputStream in = baseStream.getFileInputStream();
						TeeFileUtility.readStreamToFile(in,
								new File(TeeSysProps.getTmpPath() + "/" + uuid + "/表单附件/"
										+ attachment.getFileName()));
						baseStream.close();
					}		
				}
				sis.close(); 	
			}
			
			
			//判断文件是否存在
			File file=new File(TeeSysProps.getTmpPath() + "/" + uuid);
            if(!file.exists()){
            	json.setRtMsg("转存内容为0字节!请重新选择！");
    			throw new TeeOperationException("转存内容为0字节!请重新选择！");	
            }else{
			
			
			// 生成zip文件
			TeeZipUtil.zip(TeeSysProps.getTmpPath() + "/" + uuid + "_/" + nameDesc
					+ ".zip", TeeSysProps.getTmpPath() + "/" + uuid);

            }
			File zipFile = new File(TeeSysProps.getTmpPath() + "/" + uuid + "_/"
					+ nameDesc + ".zip");
             
			
			
			

			FileInputStream inputStream=new FileInputStream(zipFile);
			TeeAttachment att=attachmentService.createFile(nameDesc + ".zip", inputStream,"fileNetdiskPerson", loginPerson);
			//设置文件的大小
			
			att.setSize(zipFile.length());
			attachmentService.updateAttachment(att);
			try {
				//转存
				if (!("").equals(folderSid)) {
					//System.out.println("ccc");
					TeeFileNetdisk fileParent = fileNetdiskPersonDao.get(TeeStringUtil
							.getInteger(folderSid, 0));
					if (fileParent != null) {
								TeeFileNetdisk fileNetdisk = new TeeFileNetdisk();
								fileNetdisk.setFiletype(1);
								// 复制附件
								fileNetdisk.setAttachemnt(att);

								fileNetdisk.setFileNo(0);
								fileNetdisk.setFileName(nameDesc+".zip");
								
								fileNetdisk.setCreater(loginPerson);
								fileNetdisk.setFileParent(fileParent);
								fileNetdisk.setFileNetdiskType(1);
								fileNetdisk.setCreateTime(new Date());
								fileNetdiskPersonDao.save(fileNetdisk);
								
								if (!TeeUtility.isNullorEmpty(fileParent
										.getFileFullPath())) {
									fileNetdisk.setFileFullPath(fileParent
											.getFileFullPath() + fileNetdisk.getSid() + "/");
								} else {
									fileNetdisk.setFileFullPath(fileParent + "/");
								}
								
								fileNetdiskPersonDao.update(fileNetdisk);
								
								resultFlag = 1;
								
								// 将临时文件夹删除
							    TeeFileUtility.deleteAll(TeeSysProps.getTmpPath() + "/" + uuid);
							}
						}
	
				
				Map map = new HashMap();
				map.put("resultFlag", resultFlag);
				json.setRtData(map);
				json.setRtState(true);
				json.setRtMsg("文件目录获取成功!");
			} catch (Exception e) {

			} finally {
				//zipInputStream.close();
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return json;
	}

	/*	
	*//**
	 * @function: 获取文件信息
	 * @author: wyw
	 * @data: 2014年9月15日
	 * @param requestMap
	 * @param loginPerson
	 * @return TeeJson
	 */
	/*
	 * public TeeJson getAttachmentInfo(Map requestMap, TeePerson loginPerson) {
	 * TeeJson json = new TeeJson(); String attachId =
	 * (String)requestMap.get("attachId");
	 * 
	 * String fileName = ""; if(!TeeUtility.isNullorEmpty(attachId)){
	 * TeeAttachment attachments =
	 * attachmentDao.get(TeeStringUtil.getInteger(attachId, 0)); if(attachments
	 * != null){ fileName = attachments.getFileName(); } } Map map = new
	 * HashMap(); map.put("fileName", fileName); json.setRtData(map);
	 * json.setRtState(true); json.setRtMsg("文件获取成功!"); return json; }
	 */

}
