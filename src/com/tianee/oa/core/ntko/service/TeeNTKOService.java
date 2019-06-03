package com.tianee.oa.core.ntko.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.file.TeeUploadHelper;
import com.tianee.webframe.util.str.TeeUtility;
/**
 * 这个主要封装了 ntko对文件的 读写 更新等方法
 * @author zhp
 * @createTime 2013-10-17
 * @desc
 */
@Service
public class TeeNTKOService extends TeeBaseService{

	@Autowired
	private TeeAttachmentDao attachmentDao;

	public String updateAttachFile(File file,File file2){
		
		return "";
	}
	/**
	 * 获取附件存储路径
	 * @author zhp
	 * @attachName 不需要加后缀
	 * @createTime 2013-10-17
	 * @editTime 下午04:17:29
	 * @desc
	 */
	public String getAttachFilePath(String model,int id){
		String attachName  = null;
		String sFilePath = null;
		TeeAttachment attachment =  attachmentDao.get(id);
		attachName = attachment.getAttachmentName();
		sFilePath = attachment.getAttachmentPath();
		
		return attachment.getAttachSpace().getSpacePath()+File.separator+attachment.getModel()+File.separator+sFilePath+File.separator+ attachName;
	}
	/**
	 * 更新文件
	 * @author zhp
	 * @createTime 2013-10-18
	 * @editTime 下午05:34:47
	 * @desc
	 */
	public boolean updateFile(MultipartFile file ,String model,int id){
		
		/**
		 * 获取上传附件路径 如果为空 更新文件失败
		 */
		String updateFilepath  = getAttachFilePath(model, id);
		if(TeeUtility.isNullorEmpty(updateFilepath)){
			System.out.println("您获取id为"+id+"的附件不存在!");
			return false;
		}
		
		try {
			TeeUploadHelper.saveFile(file.getInputStream(), updateFilepath);
			TeeAttachment attachment =  attachmentDao.get(id);
			attachment.setSize(file.getSize());
			attachmentDao.update(attachment);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public TeeAttachment loadAttachById(int id){
		return attachmentDao.get(id);
	}

	public TeeAttachmentDao getAttachmentDao() {
		return attachmentDao;
	}

	public void setAttachmentDao(TeeAttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}
	
	
}
