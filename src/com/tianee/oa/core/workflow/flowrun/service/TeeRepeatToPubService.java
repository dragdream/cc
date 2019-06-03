package com.tianee.oa.core.workflow.flowrun.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileNetdiskDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileRolePrivDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileUserPrivDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeSaveFileToPublicFolderDao;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workFlowFrame.dataloader.TeeSimpleDataLoaderInterface;
import com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunDoc;
import com.tianee.oa.core.workflow.flowrun.dao.TeeRepeatToPubDao;
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
public class TeeRepeatToPubService extends TeeBaseService implements TeeRepeatToPubServiceInterface{
	@Autowired
	private TeeFileNetdiskDao fileNetdiskDao;

	@Autowired
	private TeeRepeatToPubDao repeatToPubDao;

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
	private TeeSimpleDataLoaderInterface simpleDataLoader;
	
	@Autowired
	private TeeBaseDownloadService baseDownloadService;
	
	@Autowired
	private TeeWorkFlowServiceContextInterface flowServiceContext;

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeRepeatToPubServiceInterface#getFlowServiceContext()
	 */
	@Override
	public TeeWorkFlowServiceContextInterface getFlowServiceContext() {
		return flowServiceContext;
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeRepeatToPubServiceInterface#setFlowServiceContext(com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface)
	 */
	@Override
	public void setFlowServiceContext(
			TeeWorkFlowServiceContextInterface flowServiceContext) {
		this.flowServiceContext = flowServiceContext;
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeRepeatToPubServiceInterface#getPublicFolderTree(java.util.Map, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public TeeJson getPublicFolderTree(Map requestMap, TeePerson loginPerson) {
		TeeJson json = new TeeJson();
		List<TeeZTreeModel> fileFolderTree = new ArrayList<TeeZTreeModel>();
		int parentSid = 0;
		
		//获取所有有权限的文件夹,一次性加载，包含有权限的父级文件夹
		List<TeeFileNetdisk> fileNetdisks = fileNetdiskDao.getNetdiskFolders4PrivZC(loginPerson, 0);
		Collections.sort(fileNetdisks, new Comparator<TeeFileNetdisk>() {
            public int compare(TeeFileNetdisk arg0, TeeFileNetdisk arg1) {
            	if(arg0.getFileNo()==arg1.getFileNo()){
            		return 0;
            	}else if(arg0.getFileNo()>arg1.getFileNo()){
            		return 1;
            	}else{
            		return -1;
            	}
            }
        });
		
		for (TeeFileNetdisk fileNetdisk : fileNetdisks) {
			if(parentSid==0){
				parentSid = fileNetdisk.getSid();
			}
			TeeZTreeModel ztree = new TeeZTreeModel();
			ztree.setId(String.valueOf(fileNetdisk.getSid()));
			ztree.setName(fileNetdisk.getFileName());
			if(fileNetdisk.getFileParent()!=null){
				ztree.setpId(fileNetdisk.getFileParent().getSid() + "");
			}else{
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

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeRepeatToPubServiceInterface#saveToPersonFolder(java.util.Map, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public TeeJson saveToPersonFolder(Map requestMap, TeePerson loginPerson) {
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
			TeeAttachment att=attachmentService.createFile(nameDesc + ".zip", inputStream,"fileNetdisk", loginPerson);
			//设置文件的大小
			
			att.setSize(zipFile.length());
			attachmentService.updateAttachment(att);
			try {
				//转存
				if (!("").equals(folderSid)) {
					//System.out.println("ccc");
					TeeFileNetdisk fileParent = fileNetdiskDao.get(TeeStringUtil
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
								fileNetdisk.setFileNetdiskType(0);
								fileNetdisk.setCreateTime(new Date());
								fileNetdiskDao.save(fileNetdisk);
								
								if (!TeeUtility.isNullorEmpty(fileParent
										.getFileFullPath())) {
									fileNetdisk.setFileFullPath(fileParent
											.getFileFullPath() +  fileNetdisk.getSid()+ "/");
								} else {
									fileNetdisk.setFileFullPath(fileParent + "/");
								}
								
								fileNetdiskDao.update(fileNetdisk);
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

/*	*//**
	 * @function: 获取文件信息
	 * @author: wyw
	 * @data: 2014年9月15日
	 * @param requestMap
	 * @param loginPerson
	 * @return TeeJson
	 *//*
	public TeeJson getAttachmentInfo(Map requestMap, TeePerson loginPerson) {
		TeeJson json = new TeeJson();
		String attachId = (String) requestMap.get("attachId");

		String fileName = "";
		if (!TeeUtility.isNullorEmpty(attachId)) {
			TeeAttachment attachments = attachmentDao.get(TeeStringUtil.getInteger(attachId, 0));
			if (attachments != null) {
				fileName = attachments.getFileName();
			}
		}
		Map map = new HashMap();
		map.put("fileName", fileName);
		json.setRtData(map);
		json.setRtState(true);
		json.setRtMsg("文件获取成功!");
		return json;
	}*/

}
