package com.tianee.oa.webservice.impl;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.webservice.ZtWebService;
import com.tianee.oa.webservice.model.Attachment;
import com.tianee.webframe.util.dynamic.TeeClassRunner;
import com.tianee.webframe.util.secure.Base64Private;

@Service("ztWebService")
public class ZtWebServiceImpl implements ZtWebService{

	@Autowired
	private TeeBaseUpload baseUpload;
	
	@Override
	public Attachment Attachment_Upload(Attachment attach) {
		// TODO Auto-generated method stub
		ByteArrayInputStream inputStream = null;
		TeeAttachment attachmentEntity = null;
		Attachment returnAttach = new Attachment();
		
		if(attach.getFileByte()==null || attach.getFileByte().length==0){
			return null;
		}
		try {
			returnAttach = new Attachment();
			inputStream = new ByteArrayInputStream(attach.getFileByte());
			attachmentEntity = baseUpload.singleAttachUpload(inputStream, attach.getFileSize(), attach.getFileName(), "", attach.getModel(),attach.getModelId(), null);
//			attachmentEntity.setModel(attach.getModel());
//			attachmentEntity.setModelId(attach.getModelId());
			
			returnAttach.setFileName(attachmentEntity.getFileName());
			returnAttach.setFileExt(attachmentEntity.getExt());
			returnAttach.setFileSize(attachmentEntity.getSize());
			returnAttach.setModel(attachmentEntity.getModel());
			returnAttach.setModelId(attachmentEntity.getModelId());
			returnAttach.setSid(attachmentEntity.getSid());
			returnAttach.setAttachmentName(attachmentEntity.getAttachmentName());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				inputStream.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return returnAttach;
	}

	@Override
	public boolean System_AuthMachineCode(String encodedMachineCode) {
		// TODO Auto-generated method stub
		try {
			//获取本机机器码
			String currentMachineCode = (String) TeeClassRunner.exec("com.tianee.webframe.util.auth.TeeAuthUtil", "getMachineCode", null);
			//对传过来加密后的机器码做解密操作
			String targetMachineCode = new String(Base64Private.decode(encodedMachineCode.getBytes()));
			//如果两个机器码相等，则校验成功
			if(targetMachineCode.equals(currentMachineCode)){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}catch (Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
